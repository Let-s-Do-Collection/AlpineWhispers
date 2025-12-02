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
    private final ModelPart mini_sheep;
    private final ModelPart head;

    public AlpineSheepModel(ModelPart root) {
        this.mini_sheep = root.getChild("mini_sheep");
        this.head = this.mini_sheep.getChild("head");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition mini_sheep = partdefinition.addOrReplaceChild("mini_sheep", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));


        PartDefinition head = mini_sheep.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -2.24F, -7.04F));

        PartDefinition h_horn = head.addOrReplaceChild("h_horn", CubeListBuilder.create(), PartPose.offset(-0.24F, -2.16F, -0.48F));

        PartDefinition cube_r1 = h_horn.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(39, 9).mirror().addBox(-1.44F, -0.6F, -4.32F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(39, 9).addBox(-8.24F, -0.6F, -4.32F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.08F, -2.4F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition h_ear = head.addOrReplaceChild("h_ear", CubeListBuilder.create(), PartPose.offset(0.48F, -1.68F, -1.92F));

        PartDefinition right_ear = h_ear.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(3.84F, 0.0F, 0.0F));

        PartDefinition cube_r2 = right_ear.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(28, 27).addBox(-7.22F, -1.48F, -0.04F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.4F, 5.4F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition left_ear = h_ear.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(-4.8F, 0.0F, 0.0F));

        PartDefinition cube_r3 = left_ear.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(28, 27).mirror().addBox(4.22F, -1.48F, -0.04F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.4F, 5.4F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition h_head2 = head.addOrReplaceChild("h_head2", CubeListBuilder.create().texOffs(0, 27).mirror().addBox(-3.0F, -2.2F, -3.84F, 6.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -1.2F, 0.0F));

        PartDefinition cube_r4 = h_head2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(17, 27).mirror().addBox(-2.0F, -4.76F, 0.84F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.2F, -4.8F, 0.3927F, 0.0F, 0.0F));

        PartDefinition body = mini_sheep.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.28F, -5.8F, -7.62F, 12.0F, 12.0F, 15.0F, new CubeDeformation(0.8F))
                .texOffs(1, 33).addBox(-5.28F, -5.8F, -7.62F, 12.0F, 12.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.72F, -1.0F, 1.12F));

        PartDefinition right_front_leg = mini_sheep.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(-1.08F, -0.2F, -1.16F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.6F, 5.2F, -4.88F));

        PartDefinition left_front_leg = mini_sheep.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(-1.56F, -0.2F, -1.16F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.6F, 5.2F, -4.88F));

        PartDefinition right_hind_leg = mini_sheep.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(-1.08F, -0.2F, -1.2F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.6F, 5.2F, 6.12F));

        PartDefinition left_hind_leg = mini_sheep.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 7).addBox(-1.56F, -0.2F, -2.2F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.6F, 5.2F, 7.12F));

        PartDefinition tail = mini_sheep.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(4, 2).addBox(-0.96F, -0.6F, -1.52F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.12F, 1.84F, 9.76F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(AlpineSheepEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);

        this.animateWalk(AlpineSheepAnimation.walk, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(entity.idleAnimationState, AlpineSheepAnimation.idle, ageInTicks, 1f);
        this.animate(entity.eatAnimationState, AlpineSheepAnimation.eat, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float) Math.PI / 180F);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int alpha) {
        mini_sheep.render(poseStack, vertexConsumer, packedLight, packedOverlay, alpha);
    }

    @Override
    public @NotNull ModelPart root() {
        return mini_sheep;
    }
}
