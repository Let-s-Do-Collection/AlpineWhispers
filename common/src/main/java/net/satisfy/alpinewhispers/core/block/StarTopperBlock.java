package net.satisfy.alpinewhispers.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StarTopperBlock extends Block {
    private static final VoxelShape SHAPE = Block.box(4.0, 6.0, 4.0, 12.0, 16.0, 12.0);

    public StarTopperBlock(BlockBehaviour.Properties properties) {
        super(properties.lightLevel(state -> 14));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {
        if (randomSource.nextFloat() < 0.15F) {
            double baseX = pos.getX() + 0.5;
            double baseY = pos.getY() + 1.15;
            double baseZ = pos.getZ() + 0.5;

            double angle = randomSource.nextDouble() * Math.PI * 2;
            double radius = 0.25 + randomSource.nextDouble() * 0.15;
            double x = baseX + Math.cos(angle) * radius;
            double z = baseZ + Math.sin(angle) * radius;
            double y = baseY + randomSource.nextDouble() * 0.08;

            double velocityX = (randomSource.nextDouble() - 0.5) * 0.01;
            double velocityY = 0.03 + randomSource.nextDouble() * 0.02;
            double velocityZ = (randomSource.nextDouble() - 0.5) * 0.01;

            level.addParticle(ParticleTypes.END_ROD, x, y, z, velocityX, velocityY, velocityZ);
        }
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        int beige = 0xF5DEB3;
        list.add(Component.translatable("tooltip.alpinewhispers.canbeplaced").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
    }
}