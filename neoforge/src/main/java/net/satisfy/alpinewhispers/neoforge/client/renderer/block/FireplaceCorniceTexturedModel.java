package net.satisfy.alpinewhispers.neoforge.client.renderer.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.IQuadTransformer;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import net.satisfy.alpinewhispers.core.block.FireplaceCorniceBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

@SuppressWarnings("deprecation")
public class FireplaceCorniceTexturedModel implements BakedModel {
    public static final ModelProperty<BlockState> MIMIC = new ModelProperty<>();
    private final BakedModel original;
    private final BiPredicate<BakedQuad, BlockState> remapFilter;

    public FireplaceCorniceTexturedModel(BakedModel original, BiPredicate<BakedQuad, BlockState> remapFilter) {
        this.original = original;
        this.remapFilter = remapFilter;
    }

    private static boolean isMissing(TextureAtlasSprite sprite) {
        if (sprite == null) return true;
        sprite.contents();
        return "missingno".equals(sprite.contents().name().getPath());
    }

    @Override
    public @NotNull ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource random, @NotNull ModelData data) {
        return original.getRenderTypes(state, random, data);
    }

    private static TextureAtlasSprite targetSprite(@Nullable BlockState mimic, TextureAtlasSprite fallback) {
        if (mimic == null || mimic.isAir()) return fallback;
        BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(mimic);
        TextureAtlasSprite sprite = model.getParticleIcon();
        return isMissing(sprite) ? fallback : sprite;
    }

    private static BakedQuad remapQuad(BakedQuad quad, TextureAtlasSprite dst) {
        TextureAtlasSprite src = quad.getSprite();
        if (src == dst) return quad;

        int[] vertices = quad.getVertices().clone();
        int stride = IQuadTransformer.STRIDE;
        int uvBase = IQuadTransformer.UV0;

        float wScale = dst.contents().width() / (float) src.contents().width();
        float hScale = dst.contents().height() / (float) src.contents().height();

        for (int i = 0; i < 4; i++) {
            int off = i * stride + uvBase;
            float uOrig = Float.intBitsToFloat(vertices[off]);
            float vOrig = Float.intBitsToFloat(vertices[off + 1]);

            float uRemap = (uOrig - src.getU0()) * wScale + dst.getU0();
            float vRemap = (vOrig - src.getV0()) * hScale + dst.getV0();

            vertices[off] = Float.floatToRawIntBits(uRemap);
            vertices[off + 1] = Float.floatToRawIntBits(vRemap);
        }

        return new BakedQuad(vertices, quad.getTintIndex(), quad.getDirection(), dst, quad.isShade(), quad.hasAmbientOcclusion());
    }

    private List<BakedQuad> remapFiltered(List<BakedQuad> input, TextureAtlasSprite dst, @Nullable BlockState state) {
        if (input.isEmpty()) return input;
        List<BakedQuad> out = new ArrayList<>(input.size());
        for (BakedQuad quad : input) {
            if (state != null && remapFilter.test(quad, state)) {
                out.add(remapQuad(quad, dst));
            } else {
                out.add(quad);
            }
        }
        return out;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource random) {
        return original.getQuads(state, side, random);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource random, @NotNull ModelData data, @Nullable RenderType layer) {
        List<BakedQuad> base = original.getQuads(state, side, random, data, layer);
        if (base.isEmpty()) return base;

        if (state == null) return base;
        if (state.getBlock() instanceof FireplaceCorniceBlock) {
            if (!state.getValue(FireplaceCorniceBlock.APPLIED)) {
                return base;
            }
        }

        if (!data.has(MIMIC)) return base;
        BlockState mimic = data.get(MIMIC);
        if (mimic == null || mimic.isAir()) return base;

        TextureAtlasSprite dst = targetSprite(mimic, original.getParticleIcon());
        if (isMissing(dst)) return base;

        return remapFiltered(base, dst, state);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return original.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return original.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return original.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return original.isCustomRenderer();
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return original.getParticleIcon();
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return original.getOverrides();
    }

    @Override
    public @NotNull ItemTransforms getTransforms() {
        return original.getTransforms();
    }
}