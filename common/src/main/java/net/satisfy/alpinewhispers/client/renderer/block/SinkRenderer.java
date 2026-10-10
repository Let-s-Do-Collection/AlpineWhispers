package net.satisfy.alpinewhispers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import org.joml.Matrix4f;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.core.block.SinkBlock;
import net.satisfy.alpinewhispers.core.block.entity.SinkBlockEntity;

public class SinkRenderer implements BlockEntityRenderer<SinkBlockEntity> {
    private static final ResourceLocation PARTS_TEXTURE = AlpineWhispers.identifier("block/arolla_pine_sink_parts");
    private static final float PIXEL = 1.0F / 16.0F;
    private static final float KNOB_TURN = 90.0F;
    private static final ResourceLocation WATER_STILL = ResourceLocation.withDefaultNamespace("block/water_still");
    // Basin water surface on a north facing sink, in block units.
    private static final float WATER_X0 = 2 * PIXEL, WATER_X1 = 14 * PIXEL, WATER_Z0 = 3 * PIXEL, WATER_Z1 = 13 * PIXEL;
    private static final int WATER_GRID = 5;
    private static final float SLOSH_AMPLITUDE = 0.006F;
    private static final float RIPPLE_AMPLITUDE = 0.006F;
    // Where the tap stream hits the water, matches SinkBlock#spout.
    private static final float SPOUT_X = 8.0F / 16.0F, SPOUT_Z = 11.5F / 16.0F;
    // Two tap knobs on the upper half of a north facing sink, in pixels: x0, z0 (each knob is 2x1x2).
    private static final float[][] KNOBS = {{4, 13}, {10, 13}};

    public SinkRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SinkBlockEntity sink, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay) {
        Level level = sink.getLevel();
        if (level == null || !(sink.getBlockState().getBlock() instanceof SinkBlock)) {
            return;
        }
        Direction facing = sink.getBlockState().getValue(SinkBlock.FACING);
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.0F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-facing.toYRot() + 180.0F));
        poseStack.translate(-0.5F, 0.0F, -0.5F);

        drawWater(poseStack, buffers, level, sink, partialTick);

        float turn = Mth.clamp((level.getGameTime() + partialTick - sink.getToggleTime()) / SinkBlockEntity.TURN_TICKS, 0.0F, 1.0F);
        float eased = 1.0F - (1.0F - turn) * (1.0F - turn);
        float angle = (sink.isOpen() ? eased : 1.0F - eased) * KNOB_TURN;

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(PARTS_TEXTURE);
        VertexConsumer consumer = buffers.getBuffer(RenderType.cutout());
        int knobLight = LevelRenderer.getLightColor(level, sink.getBlockPos().above());
        poseStack.translate(0.0F, 1.0F, 0.0F);
        for (int index = 0; index < KNOBS.length; index++) {
            float cx = (KNOBS[index][0] + 1) * PIXEL;
            float cz = (KNOBS[index][1] + 1) * PIXEL;
            poseStack.pushPose();
            poseStack.translate(cx, 0.0F, cz);
            // Hot and cold knob turn in opposite directions, like a real two-handle tap.
            poseStack.mulPose(Axis.YP.rotationDegrees(index == 0 ? angle : -angle));
            poseStack.translate(-cx, 0.0F, -cz);
            drawKnob(poseStack.last(), consumer, sprite, KNOBS[index][0] * PIXEL, KNOBS[index][1] * PIXEL, knobLight);
            poseStack.popPose();
        }
        poseStack.popPose();
    }

    private static void drawWater(PoseStack poseStack, MultiBufferSource buffers, Level level, SinkBlockEntity sink, float partialTick) {
        float waterLevel = sink.getDisplayLevel(partialTick);
        if (waterLevel <= 0.01F) {
            return;
        }
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(WATER_STILL);
        int rgb = BiomeColors.getAverageWaterColor(level, sink.getBlockPos());
        int r = rgb >> 16 & 0xFF, g = rgb >> 8 & 0xFF, b = rgb & 0xFF;
        int light = LevelRenderer.getLightColor(level, sink.getBlockPos().above());
        float y = SinkBlockEntity.surfaceHeight(waterLevel);
        float time = level.getGameTime() + partialTick;
        float slosh = sink.getSlosh(partialTick);
        float ripple = sink.isOpen() && sink.getWaterLevel() < SinkBlockEntity.MAX_LEVEL ? 1.0F : 0.0F;

        VertexConsumer consumer = FluidRenderer.getFluidBuilder(buffers);
        Matrix4f matrix = poseStack.last().pose();
        // Only the surface is visible inside the basin; a small grid lets it wave.
        for (int iz = 0; iz < WATER_GRID; iz++) {
            float z0 = Mth.lerp(iz / (float) WATER_GRID, WATER_Z0, WATER_Z1);
            float z1 = Mth.lerp((iz + 1) / (float) WATER_GRID, WATER_Z0, WATER_Z1);
            for (int ix = 0; ix < WATER_GRID; ix++) {
                float x0 = Mth.lerp(ix / (float) WATER_GRID, WATER_X0, WATER_X1);
                float x1 = Mth.lerp((ix + 1) / (float) WATER_GRID, WATER_X0, WATER_X1);
                waterVertex(consumer, matrix, sprite, x0, y + wave(x0, z0, time, slosh, ripple), z0, r, g, b, light);
                waterVertex(consumer, matrix, sprite, x0, y + wave(x0, z1, time, slosh, ripple), z1, r, g, b, light);
                waterVertex(consumer, matrix, sprite, x1, y + wave(x1, z1, time, slosh, ripple), z1, r, g, b, light);
                waterVertex(consumer, matrix, sprite, x1, y + wave(x1, z0, time, slosh, ripple), z0, r, g, b, light);
            }
        }
    }

    // Gentle swell across the basin plus rings spreading from where the stream hits the water.
    private static float wave(float x, float z, float time, float slosh, float ripple) {
        float height = 0.0F;
        if (slosh > 0.001F) {
            height += SLOSH_AMPLITUDE * slosh * (0.6F * Mth.sin(time * 0.45F + z * 14.0F + x * 4.0F) + 0.4F * Mth.sin(time * 0.6F - x * 15.0F + z * 5.0F));
        }
        if (ripple > 0.0F) {
            float dx = x - SPOUT_X, dz = z - SPOUT_Z;
            float dist = Mth.sqrt(dx * dx + dz * dz);
            height += RIPPLE_AMPLITUDE * Mth.sin(dist * 40.0F - time * 1.0F) * (float) Math.exp(-dist * 6.0F);
        }
        return height;
    }

    private static void waterVertex(VertexConsumer consumer, Matrix4f matrix, TextureAtlasSprite sprite, float x, float y, float z, int r, int g, int b, int light) {
        consumer.addVertex(matrix, x, y, z).setColor(r, g, b, 200).setUv(sprite.getU(x), sprite.getV(z)).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(0.0F, 1.0F, 0.0F);
    }

    private static void drawKnob(PoseStack.Pose pose, VertexConsumer consumer, TextureAtlasSprite sprite, float x0, float z0, int light) {
        float x1 = x0 + 2 * PIXEL, z1 = z0 + 2 * PIXEL, y0 = 0.0F, y1 = PIXEL;
        // UVs match the knob elements of arolla_pine_sink_top in pixels: u0, v0, u1, v1.
        float[] side = {12, 2, 14, 3};
        float[] top = {12, 0, 14, 2};
        face(pose, consumer, sprite, Direction.UP, top, light, new float[][]{{x0, y1, z0}, {x0, y1, z1}, {x1, y1, z1}, {x1, y1, z0}});
        face(pose, consumer, sprite, Direction.NORTH, side, light, new float[][]{{x1, y1, z0}, {x1, y0, z0}, {x0, y0, z0}, {x0, y1, z0}});
        face(pose, consumer, sprite, Direction.SOUTH, side, light, new float[][]{{x0, y1, z1}, {x0, y0, z1}, {x1, y0, z1}, {x1, y1, z1}});
        face(pose, consumer, sprite, Direction.WEST, side, light, new float[][]{{x0, y1, z0}, {x0, y0, z0}, {x0, y0, z1}, {x0, y1, z1}});
        face(pose, consumer, sprite, Direction.EAST, side, light, new float[][]{{x1, y1, z1}, {x1, y0, z1}, {x1, y0, z0}, {x1, y1, z0}});
    }

    private static void face(PoseStack.Pose pose, VertexConsumer consumer, TextureAtlasSprite sprite, Direction direction, float[] uv, int light, float[][] points) {
        float[][] uvs = {{uv[0], uv[1]}, {uv[0], uv[3]}, {uv[2], uv[3]}, {uv[2], uv[1]}};
        float shade = switch (direction) {
            case UP -> 1.0F;
            case NORTH, SOUTH -> 0.8F;
            default -> 0.6F;
        };
        for (int corner = 0; corner < 4; corner++) {
            consumer.addVertex(pose, points[corner][0], points[corner][1], points[corner][2])
                    .setColor(shade, shade, shade, 1.0F)
                    .setUv(sprite.getU(uvs[corner][0] / 16.0F), sprite.getV(uvs[corner][1] / 16.0F))
                    .setLight(light)
                    .setNormal(pose, direction.getStepX(), direction.getStepY(), direction.getStepZ());
        }
    }

    @Override
    public boolean shouldRenderOffScreen(SinkBlockEntity sink) {
        return true;
    }
}
