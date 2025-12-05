package net.satisfy.alpinewhispers.core.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.satisfy.alpinewhispers.core.registry.EntityTypeRegistry;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class AlpineSheepEntity extends Animal implements Shearable, ItemSteerable {
    private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(AlpineSheepEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState eatAnimationState = new AnimationState();
    public final AnimationState shakeAnimationState = new AnimationState();
    private int eatAnimationTick;
    private int eatAnimationTimeout;
    private int shakeAnimationTick;
    private int boostTime;
    private int breadEffectTicks;
    private boolean breadEffectActive;
    private int breadFeedCooldownTicks;

    public AlpineSheepEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        AlpineSheepEatBlockGoal eatBlockGoal = new AlpineSheepEatBlockGoal(this);
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.1, Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, eatBlockGoal);
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
    }

    private void setupAnimationStates() {
        if (!this.idleAnimationState.isStarted()) {
            this.idleAnimationState.start(this.tickCount);
        }

        if (this.eatAnimationTick > 0) {
            if (this.eatAnimationTimeout <= 0) {
                this.eatAnimationState.start(this.tickCount);
                this.eatAnimationTimeout = 48;
            } else {
                this.eatAnimationTimeout--;
            }
        } else {
            this.eatAnimationState.stop();
            this.eatAnimationTimeout = 0;
        }

        if (this.shakeAnimationTick > 0) {
            if (!this.shakeAnimationState.isStarted()) {
                this.shakeAnimationState.start(this.tickCount);
            }
        } else {
            this.shakeAnimationState.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            this.updateBreadEffect();
            if (this.breadFeedCooldownTicks > 0) {
                this.breadFeedCooldownTicks--;
            }
        }
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
        if (this.eatAnimationTick > 0) {
            this.eatAnimationTick--;
            if (this.eatAnimationTick == 0) {
                this.eatAnimationState.stop();
            }
        }
        if (this.shakeAnimationTick > 0) {
            this.shakeAnimationTick--;
            if (this.shakeAnimationTick == 0) {
                this.shakeAnimationState.stop();
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHEARED, false);
    }

    @Override
    protected void updateWalkAnimation(float movementAmount) {
        float value = this.getPose() == Pose.STANDING ? Math.min(movementAmount * 6.0F, 1.0F) : 0.0F;
        this.walkAnimation.update(value, 0.2F);
    }

    public void setSheared(boolean sheared) {
        this.entityData.set(SHEARED, sheared);
    }

    public boolean isSheared() {
        return this.entityData.get(SHEARED);
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 14.0).add(Attributes.MOVEMENT_SPEED, 0.23);
    }

    @Override
    public void handleEntityEvent(byte event) {
        if (event == 10) {
            this.eatAnimationTick = 48;
        } else if (event == 11) {
            this.shakeAnimationTick = 55;
        } else if (event == 12) {
            if (this.level().isClientSide()) {
                this.shakeAnimationTick = 55;
                for (int index = 0; index < 6; index++) {
                    double offsetX = (this.random.nextDouble() - 0.5) * this.getBbWidth();
                    double offsetY = this.random.nextDouble() * this.getBbHeight();
                    double offsetZ = (this.random.nextDouble() - 0.5) * this.getBbWidth();
                    this.level().addParticle(ParticleTypes.ANGRY_VILLAGER, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 0.0, 0.0, 0.0);
                }
            }
        } else {
            super.handleEntityEvent(event);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SHEEP_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SHEEP_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.SHEEP_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.SHEEP_STEP, 0.15F, 1.0F);
    }

    @Override
    public float getVoicePitch() {
        return this.isBaby() ? 1.5F : 1.1F;
    }

    @Nullable
    @Override
    public AlpineSheepEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return EntityTypeRegistry.ALPINE_SHEEP_ENTITY.get().create(serverLevel);
    }

    public void ate() {
        super.ate();
        this.setSheared(false);
        if (this.isBaby()) {
            this.ageUp(60);
        }
        this.level().broadcastEntityEvent(this, (byte) 10);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(Items.BREAD)) {
            if (!this.level().isClientSide()) {
                if (this.breadFeedCooldownTicks > 0 || this.breadEffectActive) {
                    return InteractionResult.CONSUME;
                }
                this.level().playSound(null, this, SoundEvents.CAMEL_EAT, SoundSource.NEUTRAL, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                this.startBreadEffect();
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        if (itemStack.getItem() instanceof ShearsItem && this.readyForShearing()) {
            this.shear(SoundSource.PLAYERS);
            itemStack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemStack));
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        if (!this.level().isClientSide() && !player.isSecondaryUseActive() && !this.isBaby() && !player.isShiftKeyDown()) {
            player.startRiding(this);
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        return super.mobInteract(player, interactionHand);
    }

    private void startBreadEffect() {
        this.breadEffectActive = true;
        this.breadEffectTicks = 0;
        this.breadFeedCooldownTicks = 80;
    }

    private void updateBreadEffect() {
        if (!this.breadEffectActive) {
            return;
        }
        this.breadEffectTicks++;
        if (this.breadEffectTicks == 200) {
            this.level().broadcastEntityEvent(this, (byte) 12);
        }
        if (this.breadEffectTicks >= 300) {
            this.hurt(this.damageSources().mobAttack(this), 2.0F);
            this.playSound(SoundEvents.SHEEP_HURT, 1.0F, 1.0F);
            this.breadEffectActive = false;
            this.breadEffectTicks = 0;
        }
    }

    @Override
    public void shear(@NotNull SoundSource shearedSoundCategory) {
        this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        this.setSheared(true);
        int woolCount = 1 + this.random.nextInt(3);
        for (int index = 0; index < woolCount; index++) {
            ItemEntity itemEntity = this.spawnAtLocation(ObjectRegistry.HOMESPUN_WOOL.get(), 1);
            if (itemEntity != null) {
                itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
            }
        }
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 11);
        }
    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive() && !this.isSheared() && !this.isBaby();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Sheared", this.isSheared());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSheared(tag.getBoolean("Sheared"));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.WHEAT);
    }

    @Override
    public @Nullable LivingEntity getControllingPassenger() {
        Entity firstPassenger = this.getFirstPassenger();
        if (firstPassenger instanceof Player player) {
            if (player.isHolding(ObjectRegistry.BREAD_ON_A_STICK.get())) {
                return player;
            }
        }
        return null;
    }

    @Override
    public boolean boost() {
        if (this.boostTime > 0) {
            return false;
        }
        this.boostTime = 80;
        return true;
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        LivingEntity controllingPassenger = this.getControllingPassenger();
        if (this.isAlive() && this.isVehicle() && controllingPassenger != null) {
            this.setYRot(controllingPassenger.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(controllingPassenger.getXRot() * 0.5F);
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.yBodyRot;
            float strafe = 0.0F;
            float forward = 1.0F;
            if (controllingPassenger instanceof Player player && player.isShiftKeyDown()) {
                forward = 0.0F;
            }
            double baseSpeed = this.getAttributeValue(Attributes.MOVEMENT_SPEED);
            float speed = (float) baseSpeed * 0.9F;
            if (this.boostTime > 0) {
                speed *= 1.5F;
                this.boostTime--;
            }
            this.setSpeed(speed);
            super.travel(new Vec3(strafe, travelVector.y, forward));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (!this.level().isClientSide() && passenger instanceof Player) {
            this.level().broadcastEntityEvent(this, (byte) 11);
        }
    }

    public static class AlpineSheepEatBlockGoal extends net.minecraft.world.entity.ai.goal.Goal {
        private static final int EAT_ANIMATION_TICKS = 48;

        private final Mob mob;
        private final Level level;
        private int eatAnimationTick;

        public AlpineSheepEatBlockGoal(Mob mob) {
            this.mob = mob;
            this.level = mob.level();
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
                return false;
            }
            BlockPos blockPos = this.mob.blockPosition();
            BlockState stateAtFeet = this.level.getBlockState(blockPos);
            BlockState stateBelow = this.level.getBlockState(blockPos.below());

            if (this.isEdibleFoliage(stateAtFeet) || this.isEdibleTerrain(stateAtFeet)) {
                return true;
            }
            return this.isEdibleTerrain(stateBelow);
        }

        private boolean isEdibleFoliage(BlockState blockState) {
            return blockState.is(Blocks.SHORT_GRASS)
                    || blockState.is(ObjectRegistry.HOARFROST_GRASS.get())
                    || blockState.is(ObjectRegistry.TALL_HOARFROST_GRASS.get());
        }

        private boolean isEdibleTerrain(BlockState blockState) {
            return blockState.is(Blocks.GRASS_BLOCK)
                    || blockState.is(Blocks.SNOW)
                    || blockState.is(Blocks.SNOW_BLOCK)
                    || blockState.is(Blocks.POWDER_SNOW)
                    || blockState.is(ObjectRegistry.FROZEN_DIRT.get());
        }

        @Override
        public void start() {
            this.eatAnimationTick = this.adjustedTickDelay(EAT_ANIMATION_TICKS);
            this.level.broadcastEntityEvent(this.mob, (byte) 10);
            this.mob.getNavigation().stop();
        }

        @Override
        public void stop() {
            this.eatAnimationTick = 0;
        }

        @Override
        public boolean canContinueToUse() {
            return this.eatAnimationTick > 0;
        }

        @Override
        public void tick() {
            this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
            if (this.eatAnimationTick == this.adjustedTickDelay(4)) {
                BlockPos blockPos = this.mob.blockPosition();
                BlockState stateAtFeet = this.level.getBlockState(blockPos);

                if (this.isEdibleFoliage(stateAtFeet)) {
                    if (this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                        this.level.destroyBlock(blockPos, false);
                    }
                    this.mob.ate();
                } else {
                    BlockPos targetPos;
                    BlockState targetState;

                    if (this.isEdibleTerrain(stateAtFeet)) {
                        targetPos = blockPos;
                        targetState = stateAtFeet;
                    } else {
                        targetPos = blockPos.below();
                        targetState = this.level.getBlockState(targetPos);
                    }

                    if (this.isEdibleTerrain(targetState)) {
                        if (this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && targetState.is(Blocks.GRASS_BLOCK)) {
                            this.level.levelEvent(2001, targetPos, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                            this.level.setBlock(targetPos, Blocks.DIRT.defaultBlockState(), 2);
                        }
                        this.mob.ate();
                    }
                }
            }
        }
    }
}
