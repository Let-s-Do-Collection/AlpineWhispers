package net.satisfy.alpinewhispers.core.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.satisfy.alpinewhispers.core.entity.ai.AnimationAttackGoal;
import net.satisfy.alpinewhispers.core.entity.ai.EntityWithAttackAnimation;
import net.satisfy.alpinewhispers.core.registry.EntityTypeRegistry;
import net.satisfy.alpinewhispers.core.registry.SoundEventRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.List;

public class ReindeerEntity extends Donkey implements EntityWithAttackAnimation {

    public ReindeerEntity(EntityType<? extends ReindeerEntity> entityType, Level level) {
        super(entityType, level);
    }

    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(ReindeerEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
            updateAttackAnimationState();
            updateDeathAnimationState();
        }
    }

    private void updateAttackAnimationState() {
        boolean attacking = this.entityData.get(ATTACKING);
        if (attacking) {
            if (!this.attackAnimationState.isStarted()) {
                this.attackAnimationState.start(this.tickCount);
            }
        } else {
            if (this.attackAnimationState.isStarted()) {
                this.attackAnimationState.stop();
            }
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    private void updateDeathAnimationState() {
        if (this.isDeadOrDying()) {
            if (!this.deathAnimationState.isStarted()) {
                this.deathAnimationState.start(this.tickCount);
            }
        } else {
            if (this.deathAnimationState.isStarted()) {
                this.deathAnimationState.stop();
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACKING, false);
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return AbstractHorse.createBaseHorseAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.18)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.ATTACK_DAMAGE, 7.0)
                .add(Attributes.ATTACK_SPEED, 0.5)
                .add(Attributes.ATTACK_KNOCKBACK, 1.6)
                .add(Attributes.JUMP_STRENGTH, 0.5);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AnimationAttackGoal(this, 1.0, true, ReindeerAnimationDurations.ATTACK_TICKS, 8));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Wolf.class, 14.0F, 1.2, 1.6));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PolarBear.class, 12.0F, 1.1, 1.5));
        this.goalSelector.addGoal(4, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(5, new RunAroundLikeCrazyGoal(this, 1.0));
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(7, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(8, new ReindeerHerdGoal(this, 1.05, 18.0, 4.0));
        this.goalSelector.addGoal(9, new RandomStrollGoal(this, 0.9));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.SWEET_BERRIES);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return EntityTypeRegistry.REINDEER_ENTITY.get().create(serverLevel);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void travel(Vec3 travelVector) {
        if (this.isAlive()) {
            Vec3 usedVector = travelVector;
            BlockPos blockPosBelow = this.getOnPosLegacy();
            BlockState blockStateBelow = this.level().getBlockState(blockPosBelow.below());

            if (isSnowBlock(blockStateBelow)) {
                usedVector = new Vec3(travelVector.x * 1.125, travelVector.y, travelVector.z * 1.125);
            }

            super.travel(usedVector);

            if (isIceBlock(blockStateBelow) && this.onGround()) {
                Vec3 deltaMovement = this.getDeltaMovement();
                this.setDeltaMovement(deltaMovement.x * 0.6, deltaMovement.y, deltaMovement.z * 0.6);
            }
        } else {
            super.travel(travelVector);
        }
    }

    private boolean isSnowBlock(BlockState blockState) {
        return blockState.is(Blocks.SNOW) || blockState.is(Blocks.SNOW_BLOCK) || blockState.is(Blocks.POWDER_SNOW);
    }

    private boolean isIceBlock(BlockState blockState) {
        return blockState.is(Blocks.ICE) || blockState.is(Blocks.PACKED_ICE) || blockState.is(Blocks.FROSTED_ICE) || blockState.is(Blocks.BLUE_ICE);
    }

    @Override
    public LivingEntity getTarget_() {
        return this.getTarget();
    }

    @Override
    public double getMeleeAttackRangeSqr_(LivingEntity entity) {
        double width = this.getBbWidth() + entity.getBbWidth();
        return width * width;
    }

    public void setAttacking_(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }


    @Override
    public Vec3 getPosition_(int index) {
        return this.position();
    }

    @Override
    public void doHurtTarget_(LivingEntity targetEntity) {
        this.doHurtTarget(targetEntity);
    }

    public static class ReindeerHerdGoal extends Goal {
        private final ReindeerEntity reindeer;
        private final double speedModifier;
        private final double maxDistance;
        private final double minDistance;
        private ReindeerEntity herdLeader;

        public ReindeerHerdGoal(ReindeerEntity reindeer, double speedModifier, double maxDistance, double minDistance) {
            this.reindeer = reindeer;
            this.speedModifier = speedModifier;
            this.maxDistance = maxDistance;
            this.minDistance = minDistance;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (this.reindeer.isBaby()) {
                return false;
            }
            if (this.reindeer.isVehicle()) {
                return false;
            }

            List<ReindeerEntity> nearbyReindeer = this.reindeer.level().getEntitiesOfClass(
                    ReindeerEntity.class,
                    this.reindeer.getBoundingBox().inflate(this.maxDistance, 4.0, this.maxDistance),
                    other -> other != this.reindeer && !other.isBaby() && !other.isVehicle()
            );

            if (nearbyReindeer.isEmpty()) {
                return false;
            }

            ReindeerEntity closest = null;
            double closestDistance = Double.MAX_VALUE;

            for (ReindeerEntity candidate : nearbyReindeer) {
                double distance = this.reindeer.distanceToSqr(candidate);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closest = candidate;
                }
            }

            if (closest == null) {
                return false;
            }

            if (closestDistance <= this.minDistance * this.minDistance) {
                return false;
            }

            this.herdLeader = closest;
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            if (this.herdLeader == null || !this.herdLeader.isAlive()) {
                return false;
            }
            if (this.reindeer.isBaby()) {
                return false;
            }
            if (this.reindeer.isVehicle()) {
                return false;
            }

            double distance = this.reindeer.distanceToSqr(this.herdLeader);
            return distance > this.minDistance * this.minDistance && distance < this.maxDistance * this.maxDistance;
        }

        @Override
        public void tick() {
            if (this.herdLeader != null) {
                this.reindeer.getNavigation().moveTo(this.herdLeader, this.speedModifier);
            }
        }

        @Override
        public void stop() {
            this.herdLeader = null;
        }
    }

    public static class ReindeerAnimationDurations {
        public static final int ATTACK_TICKS = 21;
    }

    @Override
    public boolean isSaddleable() {
        return false;
    }

    @Override
    public boolean isSaddled() {
        return this.isTamed() && !this.isBaby();
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);

        if (itemStack.is(Items.SWEET_BERRIES)) {
            if (!this.level().isClientSide) {
                if (this.isBaby()) {
                    this.ageUp(getSpeedUpSecondsWhenFeeding(-this.getAge()));
                } else if (this.isTamed() && this.canFallInLove()) {
                    this.usePlayerItem(player, interactionHand, itemStack);
                    this.setInLove(player);
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, interactionHand);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEventRegistry.REINDEER_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEventRegistry.REINDEER_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEventRegistry.REINDEER_DEATH.get();
    }

    protected SoundEvent getAngrySound() {
        return SoundEventRegistry.REINDEER_HURT.get();
    }

    @Override
    public float getVoicePitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.05F + 0.8F;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < 2 && !passenger.isPassenger();
    }

    @Override
    protected @NotNull Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float partialTick) {
        Vec3 basePos = super.getPassengerAttachmentPoint(passenger, dimensions, partialTick);
        if (!this.hasPassenger(passenger)) {
            return basePos;
        }

        int passengerIndex = this.getPassengers().indexOf(passenger);
        if (passengerIndex < 0) {
            return basePos;
        }

        float rotation = -this.getYRot() * ((float) Math.PI / 180F);
        Vec3 localOffset;

        if (passengerIndex == 0) {
            localOffset = new Vec3(0.0, 0.0, -0.3);
        } else {
            localOffset = new Vec3(0.0, 0.0, 0.3);
        }

        Vec3 worldOffset = localOffset.yRot(rotation);
        return basePos.add(worldOffset);
    }
}