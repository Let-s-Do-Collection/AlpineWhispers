package net.satisfy.alpinewhispers.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SnowGlobeBlock extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final MapCodec<SnowGlobeBlock> CODEC = simpleCodec(SnowGlobeBlock::new);

    private static final VoxelShape SHAPE = Shapes.or(
            Shapes.box(1.0 / 16.0, 0.0, 1.0 / 16.0, 15.0 / 16.0, 2.0 / 16.0, 15.0 / 16.0),
            Shapes.box(2.0 / 16.0, 2.0 / 16.0, 2.0 / 16.0, 14.0 / 16.0, 14.0 / 16.0, 14.0 / 16.0)
    );

    public SnowGlobeBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWER, 0));
    }

    @Override
    public @NotNull MapCodec<SnowGlobeBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(POWER, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWER);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        int currentPower = blockState.getValue(POWER);
        int nextPower = Math.min(15, currentPower + 5);

        if (nextPower != currentPower) {
            level.setBlock(blockPos, blockState.setValue(POWER, nextPower), Block.UPDATE_ALL);
            level.scheduleTick(blockPos, this, 4);
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void tick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
        int currentPower = blockState.getValue(POWER);
        if (currentPower <= 0) {
            return;
        }
        int nextPower = currentPower - 1;
        level.setBlock(blockPos, blockState.setValue(POWER, nextPower), Block.UPDATE_ALL);
        if (nextPower > 0) {
            level.scheduleTick(blockPos, this, 4);
        }
    }

    @Override
    public boolean isSignalSource(BlockState blockState) {
        return blockState.getValue(POWER) > 0;
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
        return blockState.getValue(POWER);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (!level.isClientSide) return;
        int power = blockState.getValue(POWER);
        if (power <= 0) return;
        if (!level.getBlockState(blockPos.below()).isSolid()) return;

        double intensity = Math.min(1.0, power / 15.0);
        int particleCount = 1 + (int)(intensity * 4.0);

        double minX = blockPos.getX() + 4.0 / 16.0;
        double maxX = blockPos.getX() + 12.0 / 16.0;
        double minY = blockPos.getY() + 4.0 / 16.0;
        double maxY = blockPos.getY() + 12.0 / 16.0;
        double minZ = blockPos.getZ() + 4.0 / 16.0;
        double maxZ = blockPos.getZ() + 12.0 / 16.0;

        double baseSpeed = 0.002 + intensity * 0.003;

        for (int i = 0; i < particleCount; i++) {
            double x = minX + randomSource.nextDouble() * (maxX - minX);
            double y = minY + randomSource.nextDouble() * (maxY - minY);
            double z = minZ + randomSource.nextDouble() * (maxZ - minZ);
            double motionX = (randomSource.nextDouble() - 0.5) * baseSpeed;
            double motionZ = (randomSource.nextDouble() - 0.5) * baseSpeed;
            double motionY = -0.002 - randomSource.nextDouble() * baseSpeed * 0.5;
            level.addParticle(ParticleTypes.SNOWFLAKE, x, y, z, motionX, motionY, motionZ);
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        int beige = 0xF5DEB3;
        list.add(Component.translatable("tooltip.alpinewhispers.canbeplaced").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
    }
}
