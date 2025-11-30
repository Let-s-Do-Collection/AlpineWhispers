package net.satisfy.alpinewhispers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.alpinewhispers.core.block.entity.TreeBaublesBlockEntity;

public class TreeBaublesRenderer implements BlockEntityRenderer<TreeBaublesBlockEntity> {
    @Override
    public void render(TreeBaublesBlockEntity ropeKnotBlockEntity, float pt, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int overlay) {
        if (ropeKnotBlockEntity.getLevel() == null) return;
        BlockState held = ropeKnotBlockEntity.getHeldBlock();
        if (held == null || held.isAir()) return;

        BlockRenderDispatcher brd = Minecraft.getInstance().getBlockRenderer();

        poseStack.pushPose();
        brd.renderBatched(held, ropeKnotBlockEntity.getBlockPos(), ropeKnotBlockEntity.getLevel(), poseStack, multiBufferSource.getBuffer(RenderType.cutout()), false, RandomSource.create());
        poseStack.popPose();
    }
}
