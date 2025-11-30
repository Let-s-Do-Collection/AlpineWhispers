package net.satisfy.alpinewhispers.core.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class AnimationAttackGoal extends MeleeAttackGoal {
    private final EntityWithAttackAnimation animationEntity;
    private int counter;
    private final int attackDelay;
    private final int attackTick;
    private int timeout = 0;

    public AnimationAttackGoal(EntityWithAttackAnimation pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen, int attackDelay, int attackTick) {
        super((PathfinderMob) pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.attackDelay = attackDelay;
        this.attackTick = attackTick;
        animationEntity = pMob;
    }

    @Override
    public void start() {
        timeout = 0;
        super.start();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && timeout < 20 * 3;
    }

    @Override
    public void tick() {
        super.tick();
        var target = animationEntity.getTarget_();
        if (target != null) {
            checkAndPerformAttack(target);
        }
        animationEntity.setAttacking_(counter != 0);

        if (counter != 0)
            counter++;
        if (counter >= attackDelay) {
            counter = 0;
        }
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity targetEntity) {
        if (this.isTimeToAttack() && this.mob.isWithinMeleeAttackRange(targetEntity) && this.mob.getSensing().hasLineOfSight(targetEntity)) {
            if (counter == 0) {
                counter++;
            }
            if (counter == attackTick) {
                this.animationEntity.doHurtTarget_(targetEntity);
            }
            timeout = 0;
        } else {
            timeout++;
        }
    }

    @Override
    public void stop() {
        animationEntity.setAttacking_(false);
        super.stop();
    }
}