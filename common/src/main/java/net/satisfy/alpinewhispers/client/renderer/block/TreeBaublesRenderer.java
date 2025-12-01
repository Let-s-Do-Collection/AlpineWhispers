package net.satisfy.alpinewhispers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.alpinewhispers.core.block.entity.TreeBaublesBlockEntity;

public class TreeBaublesRenderer implements BlockEntityRenderer<TreeBaublesBlockEntity> {
    @Override
    public void render(TreeBaublesBlockEntity treeBaublesBlockEntity, float pt, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int overlay) {
        if (treeBaublesBlockEntity.getLevel() == null) return;

        BlockState held = treeBaublesBlockEntity.getHeldBlock();
        if (held == null || held.isAir()) return;

        BlockRenderDispatcher blockRenderDispatcher = Minecraft.getInstance().getBlockRenderer();
        RenderType renderType = ItemBlockRenderTypes.getChunkRenderType(held);
        RandomSource randomSource = RandomSource.create(treeBaublesBlockEntity.getBlockPos().asLong());

        poseStack.pushPose();
        blockRenderDispatcher.renderBatched(held, treeBaublesBlockEntity.getBlockPos(), treeBaublesBlockEntity.getLevel(), poseStack, multiBufferSource.getBuffer(renderType), false, randomSource);
        poseStack.popPose();
    }
}
