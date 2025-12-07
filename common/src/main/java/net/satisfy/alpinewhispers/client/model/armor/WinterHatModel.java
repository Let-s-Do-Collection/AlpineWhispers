package net.satisfy.alpinewhispers.client.model.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.core.registry.ObjectRegistry;

import java.util.HashMap;
import java.util.Map;

public class WinterHatModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(AlpineWhispers.identifier("winter_hat"), "main");
    private static final Map<Item, WinterHatModel<?>> MODELS = new HashMap<>();

    private final ModelPart winter_hat;

    public WinterHatModel(ModelPart root) {
        this.winter_hat = root.getChild("winter_hat");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition winter_hat = partdefinition.addOrReplaceChild("winter_hat",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -9.0F, -4.5F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(0, 11).addBox(-3.5F, -11.0F, -3.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(21, 11).addBox(-1.5F, -12.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F)
        );

        return LayerDefinition.create(meshdefinition, 48, 48);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int k) {
        poseStack.pushPose();
        winter_hat.render(poseStack, buffer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    @Override
    public void setupAnim(T entity, float f, float g, float h, float i, float j) {
    }

    public void copyHead(ModelPart model) {
        winter_hat.copyFrom(model);
    }

    public static Model getHatModel(Item item, ModelPart baseHead) {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        WinterHatModel<?> model = MODELS.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.WINTER_HAT.get()) {
                return new WinterHatModel<>(modelSet.bakeLayer(LAYER_LOCATION));
            }
            return null;
        });

        if (model != null) {
            model.copyHead(baseHead);
        }

        return model;
    }
}