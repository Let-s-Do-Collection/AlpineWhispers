package net.satisfy.alpinewhispers.core.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public interface EntityWithAttackAnimation {
    LivingEntity getTarget_();

    double getMeleeAttackRangeSqr_(LivingEntity target);

    void setAttacking_(boolean b);

    Vec3 getPosition_(int i);

    void doHurtTarget_(LivingEntity targetEntity);
}
