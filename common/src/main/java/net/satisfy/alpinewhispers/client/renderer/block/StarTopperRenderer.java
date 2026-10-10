package net.satisfy.alpinewhispers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.satisfy.alpinewhispers.client.renderer.AWRenderTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.alpinewhispers.core.block.entity.StarTopperBlockEntity;
import org.joml.Matrix4f;

public class StarTopperRenderer implements BlockEntityRenderer<StarTopperBlockEntity> {
    private static final float SPIN_DEGREES_PER_TICK = 0.8F;
    private static final float BOB_AMPLITUDE = 0.03F;
    private static final int HALO_SEGMENTS = 20;
    private static final float HALO_RADIUS = 0.42F;
    // Star centre in the block, the cross model's texture is centred on the upper part of the block.
    private static final float CENTER_Y = 0.68F;

    public StarTopperRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(StarTopperBlockEntity star, float partial, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
        if (star.getLevel() == null) return;
        float time = star.getLevel().getGameTime() + partial;
        // Offset per position so neighbouring stars don't spin in lockstep.
        float seed = (star.getBlockPos().hashCode() & 255) * 1.4F;
        BlockState state = star.getBlockState();

        pose.pushPose();
        pose.translate(0.5F, BOB_AMPLITUDE * Mth.sin((time + seed) * 0.06F), 0.5F);
        pose.mulPose(Axis.YP.rotationDegrees((time + seed) * SPIN_DEGREES_PER_TICK));
        pose.translate(-0.5F, 0.0F, -0.5F);
        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(pose.last(), buffers.getBuffer(Sheets.cutoutBlockSheet()), state,
                Minecraft.getInstance().getBlockRenderer().getBlockModel(state), 1.0F, 1.0F, 1.0F, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        pose.popPose();

        renderHalo(star, pose, buffers, time, seed);
    }

    private static void renderHalo(StarTopperBlockEntity star, PoseStack pose, MultiBufferSource buffers, float time, float seed) {
        float breathing = 0.5F + 0.5F * Mth.sin((time + seed) * 0.08F);
        float alpha = 0.18F + 0.12F * breathing;
        float radius = HALO_RADIUS * (0.9F + 0.15F * breathing);
        if (star.isCrowning()) {
            // Flash as each wave of lights starts on the tree below.
            float sinceWave = (time % StarTopperBlockEntity.WAVE_TICKS);
            float flash = (float) Math.exp(-sinceWave * 0.12F);
            alpha += 0.4F * flash;
            radius *= 1.0F + 0.5F * flash;
        }

        pose.pushPose();
        pose.translate(0.5F, CENTER_Y, 0.5F);
        pose.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());
        Matrix4f matrix = pose.last().pose();
        VertexConsumer consumer = buffers.getBuffer(AWRenderTypes.GLOW);
        int center = color(alpha);
        int edge = color(0.0F);
        for (int i = 0; i < HALO_SEGMENTS; i++) {
            float a0 = i * Mth.TWO_PI / HALO_SEGMENTS;
            float a1 = (i + 1) * Mth.TWO_PI / HALO_SEGMENTS;
            float x0 = Mth.cos(a0) * radius, y0 = Mth.sin(a0) * radius;
            float x1 = Mth.cos(a1) * radius, y1 = Mth.sin(a1) * radius;
            consumer.addVertex(matrix, 0.0F, 0.0F, 0.0F).setColor(center);
            consumer.addVertex(matrix, 0.0F, 0.0F, 0.0F).setColor(center);
            consumer.addVertex(matrix, x0, y0, 0.0F).setColor(edge);
            consumer.addVertex(matrix, x1, y1, 0.0F).setColor(edge);
        }
        pose.popPose();
    }

    private static int color(float alpha) {
        int a = Mth.clamp((int) (alpha * 255), 0, 255);
        return a << 24 | 0xFFE7A0;
    }

    @Override
    public boolean shouldRenderOffScreen(StarTopperBlockEntity star) {
        return true;
    }
}
