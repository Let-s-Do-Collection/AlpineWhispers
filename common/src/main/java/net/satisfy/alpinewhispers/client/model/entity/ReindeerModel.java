package net.satisfy.alpinewhispers.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.core.entity.ReindeerEntity;
import net.satisfy.alpinewhispers.core.entity.animation.ReindeerAnimation;
import org.jetbrains.annotations.NotNull;

public class ReindeerModel<T extends Entity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(AlpineWhispers.identifier("reindeer"), "main");
    private final ModelPart reindeer;
    private final ModelPart torso;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart bags;
    private boolean baby;

    public ReindeerModel(ModelPart root) {
        ModelPart reindeerRoot = root.getChild("reindeer");
        this.reindeer = reindeerRoot;
        this.torso = reindeerRoot.getChild("torso");
        this.neck = this.torso.getChild("neck");
        this.head = this.neck.getChild("head");
        this.bags = this.torso.getChild("bags");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition reindeer = partdefinition.addOrReplaceChild("reindeer", CubeListBuilder.create(), PartPose.offset(0.0F, 5.9369F, 0.2883F));



        PartDefinition torso = reindeer.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -12.5F, -23.0F, 12.0F, 13.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.5631F, 9.7117F));

        PartDefinition bags = torso.addOrReplaceChild("bags", CubeListBuilder.create().texOffs(36, 64).addBox(-6.0F, -25.0F, 1.0F, 12.0F, 13.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 12.5F, -10.0F));
        PartDefinition cube_r1 = bags.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(8, 60).addBox(-7.0F, -10.0F, -1.0F, 8.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -16.0F, 6.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r2 = bags.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(8, 60).addBox(-7.0F, -10.0F, -1.0F, 8.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -16.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition neck = torso.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(16, 39).addBox(-3.025F, -15.6056F, -2.5381F, 6.0F, 14.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(50, 11).addBox(-2.025F, -15.6056F, -6.5381F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.025F, -4.5F, -18.0F, 1.1781F, 0.0F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(42, 39).addBox(-4.0F, -6.026F, -7.483F, 8.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(50, 0).addBox(-3.0F, -4.026F, -13.483F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.025F, -12.3668F, 0.4279F, -1.0036F, 0.0F, 0.0F));

        PartDefinition ear_cube_r1 = head.addOrReplaceChild("ear_cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0143F, -3.3235F, -4.9132F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5143F, -3.18F, 2.9301F, 0.0F, 0.0F, -0.3927F));

        PartDefinition ear_cube_r2 = head.addOrReplaceChild("ear_cube_r2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.856F, -3.3235F, -4.9132F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.4857F, -2.18F, 2.9301F, 0.0F, 0.0F, 0.3927F));

        PartDefinition rightHorn = head.addOrReplaceChild("rightHorn", CubeListBuilder.create().texOffs(66, 12).mirror().addBox(1.6122F, -13.8565F, -3.9132F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(1, 58).addBox(1.6122F, -6.8565F, -7.9132F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 57).addBox(1.6122F, -10.8565F, -8.9132F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(42, 55).addBox(1.6122F, -13.8565F, -1.9132F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 0).mirror().addBox(6.6122F, -20.8565F, -1.9132F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(1, 58).mirror().addBox(6.6122F, -20.8565F, 0.0868F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(3, 60).mirror().addBox(8.6122F, -20.8565F, 2.0868F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.6064F, -1.9068F, 1.4171F, 0.0F, 0.0F, -0.9163F));

        PartDefinition leftHorn = head.addOrReplaceChild("leftHorn", CubeListBuilder.create().texOffs(66, 12).addBox(-3.6122F, -13.8565F, -3.9132F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(1, 58).mirror().addBox(-3.6122F, -6.8565F, -7.9132F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 57).mirror().addBox(-3.6122F, -10.8565F, -8.9132F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(42, 55).mirror().addBox(-13.6122F, -13.8565F, -1.9132F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(18, 0).mirror().addBox(-8.6122F, -20.8565F, -1.9132F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(1, 58).addBox(-8.6122F, -20.8565F, 0.0868F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(3, 60).addBox(-10.6122F, -20.8565F, 2.0868F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.6064F, -1.9068F, 1.4171F, 0.0F, 0.0F, 0.9163F));

        PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(16, 12).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 3.0F));

        PartDefinition rightArm = reindeer.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(0, 39).addBox(-2.0F, 0.75F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 3.3131F, -9.2883F));

        PartDefinition leftArm = reindeer.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(0, 39).mirror().addBox(-2.0F, 0.75F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.5F, 3.3131F, -9.2883F));

        PartDefinition rightLeg = reindeer.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 6).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, 2.0631F, 9.7117F));

        PartDefinition leftLeg = reindeer.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 6).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.5F, 2.0631F, 9.7117F));

        return LayerDefinition.create(meshdefinition, 96, 96);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.reindeer.getAllParts().forEach(ModelPart::resetPose);

        ReindeerEntity reindeerEntity = (ReindeerEntity) entity;
        this.baby = reindeerEntity.isBaby();
        this.bags.visible = reindeerEntity.hasChest();

        ModelPart rightHorn = this.head.getChild("rightHorn");
        ModelPart leftHorn = this.head.getChild("leftHorn");
        rightHorn.visible = !this.baby;
        leftHorn.visible = !this.baby;

        float clampedSwingAmount = Math.min(limbSwingAmount, 1.0F);

        if (reindeerEntity.deathAnimationState.isStarted()) {
            this.animate(reindeerEntity.deathAnimationState, ReindeerAnimation.death, ageInTicks, 1.0F);
        } else if (reindeerEntity.attackAnimationState.isStarted()) {
            this.animate(reindeerEntity.attackAnimationState, ReindeerAnimation.attack, ageInTicks, 1.0F);
        } else if (clampedSwingAmount > 0.01F) {
            this.animateWalk(ReindeerAnimation.walk, limbSwing, clampedSwingAmount, 2.0F, 2.5F);
        } else {
            this.animate(reindeerEntity.idleAnimationState, ReindeerAnimation.idle, ageInTicks, 1.0F);
        }
    }

    public ModelPart head() {
        return this.head;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int light, int overlay, int alpha) {
        if (this.baby) {
            boolean visible = this.head.visible;
            this.head.visible = false;
            this.reindeer.render(poseStack, vertexConsumer, light, overlay, alpha);
            this.head.visible = visible;

            poseStack.pushPose();
            this.reindeer.translateAndRotate(poseStack);
            this.torso.translateAndRotate(poseStack);
            this.neck.translateAndRotate(poseStack);
            poseStack.translate(0F, 0.5F, -0.1F);
            poseStack.scale(1.85F, 1.85F, 1.85F);
            this.head.render(poseStack, vertexConsumer, light, overlay, alpha);
            poseStack.popPose();
            return;
        }
        this.reindeer.render(poseStack, vertexConsumer, light, overlay, alpha);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.reindeer;
    }
}
