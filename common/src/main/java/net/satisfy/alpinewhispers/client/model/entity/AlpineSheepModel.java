package net.satisfy.alpinewhispers.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
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
    private final ModelPart bodyWool;
    private final ModelPart legWoolFrontLeft;
    private final ModelPart legWoolFrontRight;
    private final ModelPart legWoolBackLeft;
    private final ModelPart legWoolBackRight;
    private boolean baby;

    public AlpineSheepModel(ModelPart root) {
        this.alpine_sheep = root.getChild("alpine_sheep");
        this.head = this.alpine_sheep.getChild("head");
        ModelPart body = this.alpine_sheep.getChild("body");
        this.bodyWool = body.getChild("bodywool");
        this.legWoolFrontLeft = this.alpine_sheep.getChild("leg0").getChild("legwool");
        this.legWoolFrontRight = this.alpine_sheep.getChild("leg1").getChild("legwool2");
        this.legWoolBackLeft = this.alpine_sheep.getChild("leg2").getChild("legwool3");
        this.legWoolBackRight = this.alpine_sheep.getChild("leg3").getChild("legwool4");
    }

    @Override
    public void setupAnim(AlpineSheepEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.baby = entity.isBaby();
        this.applyHeadRotation(netHeadYaw, headPitch);
        this.updateWoolVisibility(entity);
        this.animateWalk(AlpineSheepAnimation.walk, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(entity.idleAnimationState, AlpineSheepAnimation.idle, ageInTicks, 1f);
        this.animate(entity.eatAnimationState, AlpineSheepAnimation.eat, ageInTicks, 1f);
        this.animate(entity.shakeAnimationState, AlpineSheepAnimation.shake, ageInTicks, 1f);
    }

    private void applyHeadRotation(float netHeadYaw, float headPitch) {
        netHeadYaw = Mth.clamp(netHeadYaw, -30.0F, 30.0F);
        headPitch = Mth.clamp(headPitch, -25.0F, 45.0F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
    }

    private void updateWoolVisibility(AlpineSheepEntity entity) {
        boolean woolVisible = !entity.isSheared();
        this.bodyWool.visible = woolVisible;
        this.legWoolFrontLeft.visible = woolVisible;
        this.legWoolFrontRight.visible = woolVisible;
        this.legWoolBackLeft.visible = woolVisible;
        this.legWoolBackRight.visible = woolVisible;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int light, int overlay, int alpha) {
        if (this.baby) {
            boolean previousHeadVisible = this.head.visible;
            this.head.visible = false;
            this.alpine_sheep.render(poseStack, vertexConsumer, light, overlay, alpha);
            this.head.visible = previousHeadVisible;

            poseStack.pushPose();
            this.alpine_sheep.translateAndRotate(poseStack);
            poseStack.translate(0.0F, 0.9F, 0.5F);
            poseStack.scale(1.8F, 1.8F, 1.8F);
            this.head.render(poseStack, vertexConsumer, light, overlay, alpha);
            poseStack.popPose();
            return;
        }
        this.alpine_sheep.render(poseStack, vertexConsumer, light, overlay, alpha);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.alpine_sheep;
    }

    @SuppressWarnings("unused")
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition alpineSheep = partDefinition.addOrReplaceChild("alpine_sheep", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = alpineSheep.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -2.0F, 8.0F, 16.0F, 6.0F, new CubeDeformation(1.4F)), PartPose.offsetAndRotation(0.0F, -14.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition bodywool = body.addOrReplaceChild("bodywool", CubeListBuilder.create().texOffs(0, 22).addBox(-4.0F, -8.0F, -2.0F, 8.0F, 16.0F, 6.0F, new CubeDeformation(1.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(28, 21).addBox(-1.0F, 0.0F, -5.75F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 4.75F));

        PartDefinition head = alpineSheep.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 0).addBox(-4.0F, -5.0F, -5.0F, 8.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, -9.0F));

        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(28, 13).addBox(-5.0F, -1.0F, -3.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(1.0F, -5.0F, -2.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(28, 29).addBox(-2.0F, -1.75F, -1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.75F, -6.0F));

        PartDefinition ear1 = head.addOrReplaceChild("ear1", CubeListBuilder.create(), PartPose.offset(3.75F, -2.25F, -1.5F));

        PartDefinition ear_0_r1 = ear1.addOrReplaceChild("ear_0_r1", CubeListBuilder.create().texOffs(22, 22).addBox(-0.567F, -1.0996F, -0.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition ear2 = head.addOrReplaceChild("ear2", CubeListBuilder.create(), PartPose.offset(-3.75F, -3.0F, -1.5F));

        PartDefinition ear_1_r1 = ear2.addOrReplaceChild("ear_1_r1", CubeListBuilder.create().texOffs(22, 22).mirror().addBox(-3.8796F, -0.5853F, -0.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition leg0 = alpineSheep.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(16, 44).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -10.0F, 7.0F));

        PartDefinition legwool = leg0.addOrReplaceChild("legwool", CubeListBuilder.create().texOffs(0, 44).addBox(-2.0F, -0.5F, -2.25F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.25F));

        PartDefinition leg1 = alpineSheep.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(16, 44).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -10.0F, 7.0F));

        PartDefinition legwool2 = leg1.addOrReplaceChild("legwool2", CubeListBuilder.create().texOffs(0, 44).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg2 = alpineSheep.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(16, 44).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -10.0F, -6.0F));

        PartDefinition legwool3 = leg2.addOrReplaceChild("legwool3", CubeListBuilder.create().texOffs(0, 44).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg3 = alpineSheep.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(16, 44).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -10.0F, -6.0F));

        PartDefinition legwool4 = leg3.addOrReplaceChild("legwool4", CubeListBuilder.create().texOffs(0, 44).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }
}
