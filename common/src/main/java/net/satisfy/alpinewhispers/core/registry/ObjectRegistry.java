package net.satisfy.alpinewhispers.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.core.block.*;
import net.satisfy.alpinewhispers.core.block.BedBlock;
import net.satisfy.alpinewhispers.core.util.GeneralUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ObjectRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(AlpineWhispers.MOD_ID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(AlpineWhispers.MOD_ID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();


    public static final RegistrySupplier<Block> ALPINE_GNEISS_BRICKS = registerWithItem("alpine_gneiss_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)));
    public static final RegistrySupplier<Block> ALPINE_GNEISS_BRICK_STAIRS = registerWithItem("alpine_gneiss_brick_stairs", () -> new StairBlock(ALPINE_GNEISS_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));
    public static final RegistrySupplier<Block> ALPINE_GNEISS_BRICK_SLAB = registerWithItem("alpine_gneiss_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final RegistrySupplier<Block> ALPINE_GNEISS_BRICK_WALL = registerWithItem("alpine_gneiss_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> MOSSY_ALPINE_GNEISS_BRICKS = registerWithItem("mossy_alpine_gneiss_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)));
    public static final RegistrySupplier<Block> MOSSY_ALPINE_GNEISS_BRICK_STAIRS = registerWithItem("mossy_alpine_gneiss_brick_stairs", () -> new StairBlock(MOSSY_ALPINE_GNEISS_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));
    public static final RegistrySupplier<Block> MOSSY_ALPINE_GNEISS_BRICK_SLAB = registerWithItem("mossy_alpine_gneiss_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final RegistrySupplier<Block> MOSSY_ALPINE_GNEISS_BRICK_WALL = registerWithItem("mossy_alpine_gneiss_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> LAYERED_ALPINE_GNEISS_BRICKS = registerWithItem("layered_alpine_gneiss_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)));
    public static final RegistrySupplier<Block> LAYERED_ALPINE_GNEISS_BRICK_STAIRS = registerWithItem("layered_alpine_gneiss_brick_stairs", () -> new StairBlock(LAYERED_ALPINE_GNEISS_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));
    public static final RegistrySupplier<Block> LAYERED_ALPINE_GNEISS_BRICK_SLAB = registerWithItem("layered_alpine_gneiss_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final RegistrySupplier<Block> LAYERED_ALPINE_GNEISS_BRICK_WALL = registerWithItem("layered_alpine_gneiss_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> MOSSY_LAYERED_ALPINE_GNEISS_BRICKS = registerWithItem("mossy_layered_alpine_gneiss_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)));
    public static final RegistrySupplier<Block> MOSSY_LAYERED_ALPINE_GNEISS_BRICK_STAIRS = registerWithItem("mossy_layered_alpine_gneiss_brick_stairs", () -> new StairBlock(MOSSY_LAYERED_ALPINE_GNEISS_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));
    public static final RegistrySupplier<Block> MOSSY_LAYERED_ALPINE_GNEISS_BRICK_SLAB = registerWithItem("mossy_layered_alpine_gneiss_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final RegistrySupplier<Block> MOSSY_LAYERED_ALPINE_GNEISS_BRICK_WALL = registerWithItem("mossy_layered_alpine_gneiss_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> AROLLA_PINE_TIMBER_FLOOR = registerWithItem("arolla_pine_timber_floor", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS).sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
    public static final RegistrySupplier<Block> AROLLA_PINE_BED = registerWithItem("arolla_pine_bed", () -> new BedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAY_BED).sound(SoundType.WOOD).strength(0.2F).noOcclusion()));
    public static final RegistrySupplier<Block> AROLLA_PINE_SOFA = registerWithItem("arolla_pine_sofa", () -> new SofaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final RegistrySupplier<Block> AROLLA_PINE_DRESSER = registerWithItem("arolla_pine_dresser", () -> new DresserBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), () -> SoundEvents.WOODEN_TRAPDOOR_OPEN, () -> SoundEvents.WOODEN_TRAPDOOR_CLOSE));
    public static final RegistrySupplier<Block> AROLLA_PINE_SINK = registerWithItem("arolla_pine_sink", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS), false));
    public static final RegistrySupplier<Block> AROLLA_PINE_WASHBASIN = registerWithItem("arolla_pine_washbasin", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), true));
    public static final RegistrySupplier<Block> AROLLA_PINE_CABINET = registerWithItem("arolla_pine_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), () -> SoundEvents.WOODEN_TRAPDOOR_OPEN, () -> SoundEvents.WOODEN_TRAPDOOR_CLOSE, () -> false));
    public static final RegistrySupplier<Block> AROLLA_PINE_COOKING_AISLE = registerWithItem("arolla_pine_cooking_aisle", () -> new ConnectibleCabinetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), () -> SoundEvents.WOODEN_TRAPDOOR_OPEN, () -> SoundEvents.WOODEN_TRAPDOOR_CLOSE, () -> false));
    public static final RegistrySupplier<Block> AROLLA_PINE_WALL_CABINET = registerWithItem("arolla_pine_wall_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), () -> SoundEvents.WOODEN_TRAPDOOR_OPEN, () -> SoundEvents.WOODEN_TRAPDOOR_CLOSE, () -> true));
    public static final RegistrySupplier<Block> AROLLA_PINE_SMOKER = registerWithItem("arolla_pine_smoker", () -> new SmokeOvenBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOKER)));
    public static final RegistrySupplier<Block> AROLLA_PINE_WARDROBE = registerWithItem("arolla_pine_wardrobe", () -> new WardrobeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final RegistrySupplier<Block> AROLLA_PINE_BATHTUB = registerWithItem("arolla_pine_bathtub", () -> new BathtubBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).noOcclusion().strength(1.5f, 3.0f)));
    public static final RegistrySupplier<Block> AROLLA_PINE_PRIVY = registerWithItem("arolla_pine_privy", () -> new PrivyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistrySupplier<Block> AROLLA_PINE_TABLE = registerWithItem("arolla_pine_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> AROLLA_PINE_CHAIR = registerWithItem("arolla_pine_chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> FIREPLACE_CORNICE = registerWithItem("fireplace_cornice", () -> new FireplaceCorniceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS)));

    public static void init() {
        ITEMS.register();
        BLOCKS.register();
    }

    public static BlockBehaviour.Properties properties(float strength) {
        return properties(strength, strength);
    }

    public static BlockBehaviour.Properties properties(float breakSpeed, float explosionResist) {
        return BlockBehaviour.Properties.of().strength(breakSpeed, explosionResist);
    }

    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    static Item.Properties getSettings() {
        return getSettings(s -> {
        });
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block) {
        return GeneralUtil.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, AlpineWhispers.identifier(name), block);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return GeneralUtil.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, AlpineWhispers.identifier(path), block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return GeneralUtil.registerItem(ITEMS, ITEM_REGISTRAR, AlpineWhispers.identifier(path), itemSupplier);
    }
}
