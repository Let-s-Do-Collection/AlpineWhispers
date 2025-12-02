package net.satisfy.alpinewhispers.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.client.model.entity.AlpineSheepModel;
import net.satisfy.alpinewhispers.core.entity.AlpineSheepEntity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class AlpineSheepRenderer extends MobRenderer<AlpineSheepEntity, AlpineSheepModel<AlpineSheepEntity>> {
    private static final ResourceLocation TEXTURE = AlpineWhispers.identifier("textures/entity/alpine_sheep.png");

    public AlpineSheepRenderer(EntityRendererProvider.Context context) {
        super(context, new AlpineSheepModel<>(context.bakeLayer(AlpineSheepModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(AlpineSheepEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(AlpineSheepEntity entity, float entityYaw, float partialTicks, PoseStack stack,
                       MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby()) {
            stack.scale(0.4F, 0.4F, 0.4F);
        }
        super.render(entity, entityYaw, partialTicks, stack, buffer, packedLight);
    }
}