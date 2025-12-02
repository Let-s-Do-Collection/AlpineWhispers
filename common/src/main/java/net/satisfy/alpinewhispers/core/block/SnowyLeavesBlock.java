package net.satisfy.alpinewhispers.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.alpinewhispers.core.item.TreeBaublesItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SnowyLeavesBlock extends LeavesBlock {
    public static final MapCodec<SnowyLeavesBlock> CODEC = simpleCodec(SnowyLeavesBlock::new);
    public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;

    public SnowyLeavesBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(DISTANCE, 7)
                .setValue(PERSISTENT, false)
                .setValue(WATERLOGGED, false)
                .setValue(SNOWY, false));
    }

    @Override
    public @NotNull MapCodec<? extends LeavesBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SNOWY);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        if (mainHand.getItem() instanceof TreeBaublesItem || offHand.getItem() instanceof TreeBaublesItem) {
            return InteractionResult.PASS;
        }

        if (!mainHand.isEmpty()) {
            return InteractionResult.PASS;
        }

        if (!state.getValue(SNOWY)) return InteractionResult.PASS;
        if (!level.isClientSide && !player.isShiftKeyDown()) {
            level.setBlock(pos, state.setValue(SNOWY, false), Block.UPDATE_ALL);
            if (level instanceof ServerLevel serverLevel) {
                double x = pos.getX() + 0.5;
                double y = pos.getY() + 0.5;
                double z = pos.getZ() + 0.5;
                serverLevel.sendParticles(
                        new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SNOW_BLOCK.defaultBlockState()),
                        x, y, z, 8, 0.25, 0.25, 0.25, 0.15
                );
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }


    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!stack.is(Items.SNOWBALL)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (!level.isClientSide && !state.getValue(SNOWY)) {
            level.setBlock(pos, state.setValue(SNOWY, true), Block.UPDATE_ALL);
            if (!player.isCreative()) stack.shrink(1);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        super.animateTick(blockState, level, blockPos, randomSource);
        if (!level.isClientSide) return;
        if (!blockState.getValue(SNOWY)) return;
        if (!level.getBlockState(blockPos.below()).isAir()) return;

        long timeOfDay = level.getDayTime() % 24000L;
        int spawnChance;
        int maxParticleCount;

        if (timeOfDay < 4000L) {
            spawnChance = 40;
            maxParticleCount = 2;
        } else if (timeOfDay < 8000L) {
            spawnChance = 28;
            maxParticleCount = 3;
        } else if (timeOfDay < 12000L) {
            spawnChance = 22;
            maxParticleCount = 3;
        } else {
            spawnChance = 55;
            maxParticleCount = 2;
        }

        if (randomSource.nextInt(spawnChance) != 0) return;

        int particleCount = 1 + randomSource.nextInt(maxParticleCount);
        for (int index = 0; index < particleCount; index++) {
            double particleX = blockPos.getX() + 0.2 + randomSource.nextDouble() * 0.6;
            double particleY = blockPos.getY() + 0.9;
            double particleZ = blockPos.getZ() + 0.2 + randomSource.nextDouble() * 0.6;

            double velocityX = (randomSource.nextDouble() - 0.5) * 0.02;
            double velocityY = -0.04 - randomSource.nextDouble() * 0.02;
            double velocityZ = (randomSource.nextDouble() - 0.5) * 0.02;

            level.addParticle(ParticleTypes.SNOWFLAKE, particleX, particleY, particleZ, velocityX, velocityY, velocityZ);
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag
            tooltipFlag) {
        int beige = 0xF5DEB3;
        int gold = 0xFFD700;
        if (!Screen.hasShiftDown()) {
            Component key = Component.literal("[SHIFT]").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(gold)));
            list.add(Component.translatable("tooltip.alpinewhispers.tooltip_information.hold", key).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
            return;
        }
        list.add(Component.translatable("tooltip.alpinewhispers.snowy_leaves.info_0").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
        list.add(Component.empty());
        list.add(Component.translatable("tooltip.alpinewhispers.snowy_leaves.info_1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(beige))));
    }
}