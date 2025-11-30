package net.satisfy.alpinewhispers.core.entity.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class ReindeerAnimation {
public static final AnimationDefinition idle = AnimationDefinition.Builder.withLength(3.5F).looping()
        .addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.55F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.5F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.1F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.9F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.45F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.9F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(3.45F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .build();

public static final AnimationDefinition walk = AnimationDefinition.Builder.withLength(2.8F).looping()
        .addAnimation("reindeer", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("reindeer", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -1.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.4F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.9F, KeyframeAnimations.posVec(0.0F, -1.0F, -1.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.4976F, 0.109F, 2.4976F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.4834F, 0.3266F, -1.5045F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.4F, KeyframeAnimations.degreeVec(-2.4976F, -0.109F, -2.4976F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.9F, KeyframeAnimations.degreeVec(1.4834F, -0.3266F, 1.5045F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.degreeVec(-2.4976F, 0.109F, 2.4976F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.4F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-4.9777F, 1.1541F, 2.2178F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.8F, KeyframeAnimations.degreeVec(0.0064F, -0.7592F, -2.4019F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.5F, KeyframeAnimations.degreeVec(-4.9777F, -1.1541F, -2.2178F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.2F, KeyframeAnimations.degreeVec(0.0064F, 0.7592F, 2.4019F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.degreeVec(-4.9777F, 1.1541F, 2.2178F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.4764F, 2.1649F, 1.2506F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.9F, KeyframeAnimations.degreeVec(4.9951F, 0.1171F, -0.1839F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.6F, KeyframeAnimations.degreeVec(-2.4764F, -2.1649F, -1.2506F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.3F, KeyframeAnimations.degreeVec(4.9951F, -0.1171F, 0.1839F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.degreeVec(-2.4764F, 2.1649F, 1.2506F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.6F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.25F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.95F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.5F, -1.25F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, -0.25F, -1.75F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.3F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 1.75F, -1.2F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.posVec(0.0F, 1.5F, -1.25F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.25F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.95F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, 1.75F, -1.2F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.3F, KeyframeAnimations.posVec(0.0F, 1.5F, -1.25F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -0.25F, -1.75F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.5F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.6F, KeyframeAnimations.degreeVec(-8.71F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.3F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.05F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.65F, KeyframeAnimations.posVec(0.0F, 2.0F, -0.25F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.35F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.25F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.1F, KeyframeAnimations.posVec(0.0F, -0.25F, -2.75F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.75F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.65F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.3F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.05F, KeyframeAnimations.degreeVec(-8.71F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.25F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.7F, KeyframeAnimations.posVec(0.0F, -0.25F, -2.75F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.4F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.1F, KeyframeAnimations.posVec(0.0F, 2.0F, -0.25F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.25F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(2.8F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .build();

public static final AnimationDefinition death = AnimationDefinition.Builder.withLength(0.9F)
        .addAnimation("reindeer", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.45F, KeyframeAnimations.degreeVec(-7.438F, 1.5028F, -97.7601F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.6F, KeyframeAnimations.degreeVec(-7.1807F, -2.5025F, -89.6919F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.75F, KeyframeAnimations.degreeVec(-12.1738F, -0.0221F, -90.0043F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("reindeer", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.45F, KeyframeAnimations.posVec(-38.0F, -14.0F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.6F, KeyframeAnimations.posVec(-39.0F, -12.4F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.75F, KeyframeAnimations.posVec(-40.0F, -12.7F, -6.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-6.1759F, -15.6869F, 25.9603F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.4F, KeyframeAnimations.degreeVec(-5.2565F, 8.6724F, 12.9991F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.6F, KeyframeAnimations.degreeVec(-4.1308F, 0.513F, 13.9994F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.75F, KeyframeAnimations.degreeVec(-7.7208F, 10.3788F, 8.5289F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.3F, KeyframeAnimations.degreeVec(-1.0748F, -22.3156F, 2.0614F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5F, KeyframeAnimations.degreeVec(3.4685F, 29.021F, 29.2663F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.65F, KeyframeAnimations.degreeVec(-5.2788F, 14.1468F, 19.3419F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.8F, KeyframeAnimations.degreeVec(-8.799F, 17.1921F, 0.7706F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.25F, KeyframeAnimations.degreeVec(17.4777F, 3.7821F, -21.5492F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.4F, KeyframeAnimations.degreeVec(6.1026F, -12.808F, -28.0959F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5F, KeyframeAnimations.degreeVec(-4.3534F, 4.837F, -13.0184F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.6F, KeyframeAnimations.degreeVec(-2.6817F, 0.9268F, 4.761F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.25F, KeyframeAnimations.posVec(-0.8309F, -0.5559F, 0.0249F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.4F, KeyframeAnimations.posVec(0.7319F, -0.1615F, 0.0262F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5F, KeyframeAnimations.posVec(-1.0183F, -0.1598F, 0.107F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.6F, KeyframeAnimations.posVec(0.23F, -0.1602F, 0.1095F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.25F, KeyframeAnimations.degreeVec(35.2316F, 6.1378F, 4.3184F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.45F, KeyframeAnimations.degreeVec(27.6953F, 23.4756F, -24.2681F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.65F, KeyframeAnimations.degreeVec(33.6788F, -12.592F, 31.0426F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.75F, KeyframeAnimations.degreeVec(35.3246F, -5.4917F, 20.6179F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.8F, KeyframeAnimations.posVec(-2.2497F, -0.0083F, 0.0378F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -25.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -35.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -12.5F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.35F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5F, KeyframeAnimations.posVec(1.4992F, -0.0212F, 0.0439F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.65F, KeyframeAnimations.posVec(1.4992F, -0.0212F, 0.0439F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.75F, KeyframeAnimations.posVec(2.2494F, -0.0135F, 0.0117F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.25F, KeyframeAnimations.degreeVec(-15.3472F, -12.0675F, 10.784F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.55F, KeyframeAnimations.degreeVec(-7.0549F, -13.126F, -20.3411F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.75F, KeyframeAnimations.degreeVec(-10.6857F, 10.4035F, 23.6752F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.9F, KeyframeAnimations.degreeVec(-9.7422F, 11.2907F, 28.6855F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.75F, KeyframeAnimations.posVec(0.2604F, -0.7393F, 0.1029F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.9F, KeyframeAnimations.posVec(0.46F, -0.9355F, 0.1421F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 25.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.7F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 17.5F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 22.5F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .build();

public static final AnimationDefinition attack = AnimationDefinition.Builder.withLength(0.95F)
        .addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.3F, KeyframeAnimations.degreeVec(-4.9953F, 0.2178F, 2.4905F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.55F, KeyframeAnimations.degreeVec(7.4859F, 0.435F, -4.9763F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.95F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.3F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.95F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.3F, KeyframeAnimations.degreeVec(-1.8803F, -6.6782F, -10.5906F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.45F, KeyframeAnimations.degreeVec(15.2324F, 2.1539F, 12.3159F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.6F, KeyframeAnimations.degreeVec(39.511F, -0.0957F, 40.8757F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.95F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("neck", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.45F, KeyframeAnimations.posVec(-0.75F, 0.25F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.95F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.4F, KeyframeAnimations.degreeVec(-14.9162F, -4.5305F, -2.1175F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.7F, KeyframeAnimations.degreeVec(25.4704F, -0.4762F, -4.9744F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.95F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.95F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.3F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 15.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.95F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.POSITION,
        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
        new Keyframe(0.95F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .build();
        }