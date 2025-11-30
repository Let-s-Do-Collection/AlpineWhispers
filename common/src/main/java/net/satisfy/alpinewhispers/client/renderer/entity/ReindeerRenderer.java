package net.satisfy.alpinewhispers.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.client.model.entity.ReindeerModel;
import net.satisfy.alpinewhispers.core.entity.ReindeerEntity;
import org.jetbrains.annotations.NotNull;

public class ReindeerRenderer extends MobRenderer<ReindeerEntity, ReindeerModel<ReindeerEntity>> {
    private static final ResourceLocation TEXTURE = AlpineWhispers.identifier("textures/entity/reindeer.png");

    public ReindeerRenderer(EntityRendererProvider.Context context) {
        super(context, new ReindeerModel<>(context.bakeLayer(ReindeerModel.LAYER_LOCATION)), 0.9f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ReindeerEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(ReindeerEntity entity, float yaw, float partialTicks, PoseStack pose, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby()) {
            this.shadowRadius = 0.3f;
            pose.pushPose();
            pose.scale(0.5f, 0.5f, 0.5f);
            super.render(entity, yaw, partialTicks, pose, buffer, packedLight);
            pose.popPose();
            return;
        }
        this.shadowRadius = 0.9f;
        super.render(entity, yaw, partialTicks, pose, buffer, packedLight);
    }
}
