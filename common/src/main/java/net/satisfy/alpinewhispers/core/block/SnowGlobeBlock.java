package net.satisfy.alpinewhispers.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.satisfy.alpinewhispers.core.registry.ParticleTypeRegistry;
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
            level.scheduleTick(blockPos, this, decayDelay(nextPower));
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
            level.scheduleTick(blockPos, this, decayDelay(nextPower));
        }
    }

    private static int decayDelay(int power) {
        return power > 10 ? 10 : 4;
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

        double intensity = power / 15.0;
        boolean storm = power >= 12;
        int particleCount = storm ? 6 + randomSource.nextInt(4) : power >= 8 ? 2 : (randomSource.nextFloat() < 0.4F + intensity ? 1 : 0);
        double centerX = blockPos.getX() + 0.5;
        double centerZ = blockPos.getZ() + 0.5;

        for (int i = 0; i < particleCount; i++) {
            double angle = randomSource.nextDouble() * Math.PI * 2.0;
            double radius = Math.sqrt(randomSource.nextDouble()) * ((storm ? 4.5 : 4.0) / 16.0);
            double x = centerX + Math.cos(angle) * radius;
            double z = centerZ + Math.sin(angle) * radius;
            double y = blockPos.getY() + (storm ? 3.5 + randomSource.nextDouble() * 9.0 : 8.0 + randomSource.nextDouble() * 4.0) / 16.0;
            double lift = storm ? 0.01 + randomSource.nextDouble() * 0.03 : 0.0;
            double tangential = storm ? 0.03 * intensity : 0.0;
            double motionX = -Math.sin(angle) * tangential + (randomSource.nextDouble() - 0.5) * 0.02 * intensity;
            double motionZ = Math.cos(angle) * tangential + (randomSource.nextDouble() - 0.5) * 0.02 * intensity;
            level.addParticle(ParticleTypeRegistry.SNOW_GLOBE_FLAKE.get(), x, y, z, motionX, lift, motionZ);
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        int beige = 0xF5DEB3;
        list.add(Component.translatable("tooltip.alpinewhispers.canbeplaced").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
    }
}
