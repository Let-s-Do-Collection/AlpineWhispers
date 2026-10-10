package net.satisfy.alpinewhispers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.client.renderer.model.BathtubModel;
import net.satisfy.alpinewhispers.core.block.BathtubBlock;
import net.satisfy.alpinewhispers.core.block.entity.BathtubBlockEntity;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class BathtubRenderer implements BlockEntityRenderer<BathtubBlockEntity> {
    private static final ResourceLocation TEXTURE = AlpineWhispers.identifier("textures/entity/bathtub.png");
    private static final ResourceLocation WATER_STILL = ResourceLocation.withDefaultNamespace("block/water_still");

    // Water surface in tub-local space: x across the tub, z along it (head block = z 0..1, foot block = z 1..2).
    private static final float WATER_X_MIN = 3f / 16f;
    private static final float WATER_X_MAX = 13f / 16f;
    private static final float WATER_Z_MIN = 3f / 16f;
    private static final float WATER_Z_MAX = 29f / 16f;
    private static final float WATER_BASE = 0.475f;
    private static final float WATER_HEIGHT = 0.46f;
    private static final int GRID_X = 5;
    private static final int GRID_Z = 8;
    private static final float SLOSH_AMPLITUDE = 0.012f;
    private static final float RIPPLE_AMPLITUDE = 0.01f;
    // Where the faucet stream hits the water, in tub-local space.
    private static final float SPOUT_X = 0.5f;
    private static final float SPOUT_Z = 0.1875f;

    private final BathtubModel model;

    public BathtubRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new BathtubModel(context.bakeLayer(BathtubModel.LAYER_LOCATION));
    }

    @Override
    public void render(BathtubBlockEntity be, float partial, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
        if (be.getBlockState().getValue(BathtubBlock.PART) != BedPart.HEAD) return;
        Direction f = be.getBlockState().getValue(BathtubBlock.FACING);

        pose.pushPose();
        switch (f) {
            case EAST -> pose.translate(-0.5, 1.5, 0.5);
            case WEST -> pose.translate(1.5, 1.5, 0.5);
            case NORTH -> pose.translate(0.5, 1.5, 1.5);
            case SOUTH -> pose.translate(0.5, 1.5, -0.5);
        }
        pose.mulPose(Axis.YN.rotationDegrees(f.toYRot()));
        pose.mulPose(Axis.XP.rotationDegrees(180));
        model.setKnobs(be.getKnob(partial));
        model.renderToBuffer(pose, buffers.getBuffer(model.renderType(TEXTURE)), light, OverlayTexture.NO_OVERLAY, -1);
        pose.popPose();

        float ratio = be.getFillRatio(partial);
        if (ratio <= 0f) return;

        // Tub-local frame: origin at the head block's outer corner, +z running from head towards foot.
        pose.pushPose();
        pose.translate(0.5, 0.0, 0.5);
        pose.mulPose(Axis.YP.rotationDegrees(180f - f.toYRot()));
        pose.translate(-0.5, 0.0, -0.5);
        renderWaterSurface(be, pose, buffers, WATER_BASE + WATER_HEIGHT * ratio, partial);
        pose.popPose();
    }

    private void renderWaterSurface(BathtubBlockEntity be, PoseStack pose, MultiBufferSource buffers, float y, float partial) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(WATER_STILL);
        int rgb = be.getLevel() != null ? BiomeColors.getAverageWaterColor(be.getLevel(), be.getBlockPos()) : 0x3F76E4;
        int r = rgb >> 16 & 0xFF, g = rgb >> 8 & 0xFF, b = rgb & 0xFF;
        int light = be.getLevel() != null ? LevelRenderer.getLightColor(be.getLevel(), be.getBlockPos().above()) : 0xF000F0;
        float time = be.getLevel() != null ? be.getLevel().getGameTime() + partial : 0f;
        float slosh = be.getSlosh(partial);
        float ripple = be.isFilling() ? 1f : 0f;

        VertexConsumer vc = FluidRenderer.getFluidBuilder(buffers);
        Matrix4f m = pose.last().pose();

        // A grid instead of one quad so the surface can wave; cells never cross a block edge so the sprite tiles per block.
        List<Float> zCuts = new ArrayList<>();
        zCuts.add(WATER_Z_MIN);
        for (float z = (float) Math.ceil(WATER_Z_MIN * GRID_Z) / GRID_Z; z < WATER_Z_MAX; z += 1f / GRID_Z) {
            if (z > WATER_Z_MIN + 1e-4f) zCuts.add(z);
        }
        zCuts.add(WATER_Z_MAX);

        for (int iz = 0; iz < zCuts.size() - 1; iz++) {
            float z0 = zCuts.get(iz), z1 = zCuts.get(iz + 1);
            float block = (float) Math.floor(z0 + 1e-4f);
            float v0 = sprite.getV(z0 - block), v1 = sprite.getV(z1 - block);
            for (int ix = 0; ix < GRID_X; ix++) {
                float x0 = Mth.lerp(ix / (float) GRID_X, WATER_X_MIN, WATER_X_MAX);
                float x1 = Mth.lerp((ix + 1) / (float) GRID_X, WATER_X_MIN, WATER_X_MAX);
                float u0 = sprite.getU(x0), u1 = sprite.getU(x1);
                vertex(vc, m, x0, y + wave(x0, z0, time, slosh, ripple), z0, u0, v0, r, g, b, light);
                vertex(vc, m, x0, y + wave(x0, z1, time, slosh, ripple), z1, u0, v1, r, g, b, light);
                vertex(vc, m, x1, y + wave(x1, z1, time, slosh, ripple), z1, u1, v1, r, g, b, light);
                vertex(vc, m, x1, y + wave(x1, z0, time, slosh, ripple), z0, u1, v0, r, g, b, light);
            }
        }
    }

    // Rolling swell along the tub plus rings spreading from where the stream hits the water.
    private static float wave(float x, float z, float time, float slosh, float ripple) {
        float height = 0f;
        if (slosh > 0.001f) {
            height += SLOSH_AMPLITUDE * slosh * (0.6f * Mth.sin(time * 0.35f + z * 9f + x * 3f) + 0.4f * Mth.sin(time * 0.5f - x * 11f + z * 4f));
        }
        if (ripple > 0f) {
            float dx = x - SPOUT_X, dz = z - SPOUT_Z;
            float dist = Mth.sqrt(dx * dx + dz * dz);
            height += RIPPLE_AMPLITUDE * Mth.sin(dist * 28f - time * 0.9f) * (float) Math.exp(-dist * 3.5f);
        }
        return height;
    }

    private static void vertex(VertexConsumer vc, Matrix4f m, float x, float y, float z, float u, float v, int r, int g, int b, int light) {
        vc.addVertex(m, x, y, z).setColor(r, g, b, 200).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0f, 1f, 0f);
    }

    @Override
    public boolean shouldRenderOffScreen(BathtubBlockEntity be) {
        return true;
    }
}
