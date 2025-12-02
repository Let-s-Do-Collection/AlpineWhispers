package net.satisfy.alpinewhispers.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.alpinewhispers.core.util.GeneralUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class WallWreathBlock extends FairyLightsBlock {
    public static final MapCodec<WallWreathBlock> CODEC = simpleCodec(WallWreathBlock::new);
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES;

    public WallWreathBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull MapCodec<WallWreathBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return BOUNDING_SHAPES.get(state.getValue(FACING));
    }

    static {
        EnumMap<Direction, VoxelShape> shapes = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            shapes.put(direction, createShape(direction));
        }
        BOUNDING_SHAPES = Collections.unmodifiableMap(shapes);
    }

    private static VoxelShape createShape(Direction direction) {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.75, 0.875, 0.875, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.375, 0.75, 0.625, 0.625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.625, 0.6875, 0.5625, 0.75, 0.75), BooleanOp.OR);
        return GeneralUtil.rotateShape(Direction.NORTH, direction, shape);
    }
}
