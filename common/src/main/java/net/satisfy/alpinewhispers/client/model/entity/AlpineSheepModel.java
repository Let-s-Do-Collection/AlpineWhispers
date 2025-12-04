package net.satisfy.alpinewhispers.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.core.entity.AlpineSheepEntity;
import net.satisfy.alpinewhispers.core.entity.animation.AlpineSheepAnimation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class AlpineSheepModel<T extends AlpineSheepEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(AlpineWhispers.identifier("alpine_sheep"), "main");
    private final ModelPart alpine_sheep;
    private final ModelPart head;

    public AlpineSheepModel(ModelPart root) {
        this.alpine_sheep = root.getChild("alpine_sheep");
        this.head = this.alpine_sheep.getChild("head");
    }

    @Override
    public void setupAnim(AlpineSheepEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);
        this.animateWalk(AlpineSheepAnimation.walk, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(entity.idleAnimationState, AlpineSheepAnimation.idle, ageInTicks, 1f);
        this.animate(entity.eatAnimationState, AlpineSheepAnimation.eat, ageInTicks, 1f);
        this.animate(entity.shakeAnimationState, AlpineSheepAnimation.shake, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);
        this.head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float) Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int alpha) {
        this.alpine_sheep.render(poseStack, vertexConsumer, packedLight, packedOverlay, alpha);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.alpine_sheep;
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition alpine_sheep = partdefinition.addOrReplaceChild("alpine_sheep", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = alpine_sheep.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 22).addBox(-4.0F, -8.0F, -2.0F, 8.0F, 16.0F, 6.0F, new CubeDeformation(1.5F)), PartPose.offsetAndRotation(0.0F, -14.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(28, 21).addBox(-1.0F, 0.0F, -5.75F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 4.75F));

        PartDefinition head = alpine_sheep.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 0).addBox(-4.0F, -5.0F, -5.0F, 8.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, -9.0F));

        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(28, 13).addBox(-5.0F, -1.0F, -3.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(1.0F, -5.0F, -2.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(28, 29).addBox(-2.0F, -1.75F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.75F, -6.0F));

        PartDefinition ear1 = head.addOrReplaceChild("ear1", CubeListBuilder.create(), PartPose.offset(3.75F, -2.25F, -1.5F));

        PartDefinition ear_0_r1 = ear1.addOrReplaceChild("ear_0_r1", CubeListBuilder.create().texOffs(22, 22).addBox(-0.567F, -1.0996F, -0.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition ear2 = head.addOrReplaceChild("ear2", CubeListBuilder.create(), PartPose.offset(-3.75F, -3.0F, -1.5F));

        PartDefinition ear_1_r1 = ear2.addOrReplaceChild("ear_1_r1", CubeListBuilder.create().texOffs(22, 22).mirror().addBox(-3.8796F, -0.5853F, -0.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition leg0 = alpine_sheep.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(16, 44).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -10.0F, 7.0F));

        PartDefinition legwool = leg0.addOrReplaceChild("legwool", CubeListBuilder.create().texOffs(0, 44).addBox(-2.0F, -0.5F, -2.25F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.25F));

        PartDefinition leg1 = alpine_sheep.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(16, 44).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -10.0F, 7.0F));

        PartDefinition legwool2 = leg1.addOrReplaceChild("legwool2", CubeListBuilder.create().texOffs(0, 44).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg2 = alpine_sheep.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(16, 44).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -10.0F, -6.0F));

        PartDefinition legwool3 = leg2.addOrReplaceChild("legwool3", CubeListBuilder.create().texOffs(0, 44).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg3 = alpine_sheep.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(16, 44).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -10.0F, -6.0F));

        PartDefinition legwool4 = leg3.addOrReplaceChild("legwool4", CubeListBuilder.create().texOffs(0, 44).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
}