package net.satisfy.alpinewhispers.core.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class SnowyFlowerBlock extends FlowerBlock {
    public static final MapCodec<SnowyFlowerBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(EFFECTS_FIELD.forGetter(SnowyFlowerBlock::getSuspiciousEffects), propertiesCodec()).apply(instance, SnowyFlowerBlock::new)
    );

    public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;

    public SnowyFlowerBlock(Holder<MobEffect> effect, float duration, BlockBehaviour.Properties properties) {
        this(makeEffectList(effect, duration), properties);
    }

    public SnowyFlowerBlock(SuspiciousStewEffects suspiciousStewEffects, BlockBehaviour.Properties properties) {
        super(suspiciousStewEffects, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SNOWY, false));
    }

    @Override
    public @NotNull MapCodec<? extends FlowerBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SNOWY);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockState blockState = super.getStateForPlacement(blockPlaceContext);
        if (blockState == null) {
            return null;
        }
        boolean hasSnow = hasAdjacentSnow(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos());
        return blockState.setValue(SNOWY, hasSnow);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState blockState, Direction direction, BlockState neighborState, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos neighborPos) {
        BlockState updatedState = super.updateShape(blockState, direction, neighborState, levelAccessor, blockPos, neighborPos);
        boolean hasSnow = hasAdjacentSnow(levelAccessor, blockPos);
        return updatedState.setValue(SNOWY, hasSnow);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
        super.randomTick(blockState, level, blockPos, randomSource);
        boolean hasSnow = hasAdjacentSnow(level, blockPos);
        if (blockState.getValue(SNOWY) != hasSnow) {
            level.setBlock(blockPos, blockState.setValue(SNOWY, hasSnow), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        boolean isShovel = itemStack.getItem() instanceof ShovelItem;
        boolean isSnowball = itemStack.is(Items.SNOWBALL);

        if (!isShovel && !isSnowball) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        boolean snowy = blockState.getValue(SNOWY);

        if (level.isClientSide) {
            if (isShovel && snowy) {
                return ItemInteractionResult.SUCCESS;
            }
            if (isSnowball && !snowy) {
                return ItemInteractionResult.SUCCESS;
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (isShovel && snowy) {
            BlockState updatedState = blockState.setValue(SNOWY, false);
            level.setBlock(blockPos, updatedState, Block.UPDATE_CLIENTS);
            return ItemInteractionResult.CONSUME;
        }

        if (isSnowball && !snowy) {
            BlockState updatedState = blockState.setValue(SNOWY, true);
            level.setBlock(blockPos, updatedState, Block.UPDATE_CLIENTS);
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            return ItemInteractionResult.CONSUME;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private boolean hasAdjacentSnow(LevelAccessor levelAccessor, BlockPos blockPos) {
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = blockPos.relative(direction);
            BlockState neighborState = levelAccessor.getBlockState(neighborPos);
            if (neighborState.is(Blocks.SNOW) || neighborState.is(Blocks.SNOW_BLOCK)) {
                return true;
            }
        }
        return false;
    }
}