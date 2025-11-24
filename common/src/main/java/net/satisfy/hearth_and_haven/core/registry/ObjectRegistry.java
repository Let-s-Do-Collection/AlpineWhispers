package net.satisfy.hearth_and_haven.core.registry;

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
import net.satisfy.hearth_and_haven.HearthAndHaven;
import net.satisfy.hearth_and_haven.core.block.*;
import net.satisfy.hearth_and_haven.core.block.BedBlock;
import net.satisfy.hearth_and_haven.core.util.GeneralUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ObjectRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(HearthAndHaven.MOD_ID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(HearthAndHaven.MOD_ID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();


    public static final RegistrySupplier<Block> alpine_gneiss_BRICKS = registerWithItem("alpine_gneiss_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)));
    public static final RegistrySupplier<Block> alpine_gneiss_BRICK_STAIRS = registerWithItem("alpine_gneiss_brick_stairs", () -> new StairBlock(alpine_gneiss_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));
    public static final RegistrySupplier<Block> alpine_gneiss_BRICK_SLAB = registerWithItem("alpine_gneiss_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final RegistrySupplier<Block> alpine_gneiss_BRICK_WALL = registerWithItem("alpine_gneiss_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> MOSSY_alpine_gneiss_BRICKS = registerWithItem("mossy_alpine_gneiss_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)));
    public static final RegistrySupplier<Block> MOSSY_alpine_gneiss_BRICK_STAIRS = registerWithItem("mossy_alpine_gneiss_brick_stairs", () -> new StairBlock(MOSSY_alpine_gneiss_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));
    public static final RegistrySupplier<Block> MOSSY_alpine_gneiss_BRICK_SLAB = registerWithItem("mossy_alpine_gneiss_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final RegistrySupplier<Block> MOSSY_alpine_gneiss_BRICK_WALL = registerWithItem("mossy_alpine_gneiss_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> LAYERED_alpine_gneiss_BRICKS = registerWithItem("layered_alpine_gneiss_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)));
    public static final RegistrySupplier<Block> LAYERED_alpine_gneiss_BRICK_STAIRS = registerWithItem("layered_alpine_gneiss_brick_stairs", () -> new StairBlock(LAYERED_alpine_gneiss_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));
    public static final RegistrySupplier<Block> LAYERED_alpine_gneiss_BRICK_SLAB = registerWithItem("layered_alpine_gneiss_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final RegistrySupplier<Block> LAYERED_alpine_gneiss_BRICK_WALL = registerWithItem("layered_alpine_gneiss_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> MOSSY_LAYERED_alpine_gneiss_BRICKS = registerWithItem("mossy_layered_alpine_gneiss_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)));
    public static final RegistrySupplier<Block> MOSSY_LAYERED_alpine_gneiss_BRICK_STAIRS = registerWithItem("mossy_layered_alpine_gneiss_brick_stairs", () -> new StairBlock(MOSSY_LAYERED_alpine_gneiss_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));
    public static final RegistrySupplier<Block> MOSSY_LAYERED_alpine_gneiss_BRICK_SLAB = registerWithItem("mossy_layered_alpine_gneiss_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final RegistrySupplier<Block> MOSSY_LAYERED_alpine_gneiss_BRICK_WALL = registerWithItem("mossy_layered_alpine_gneiss_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> RUSTIC_TIMBER_FLOOR = registerWithItem("rustic_timber_floor", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS).sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
    public static final RegistrySupplier<Block> RUSTIC_BED = registerWithItem("rustic_bed", () -> new BedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAY_BED).sound(SoundType.WOOD).strength(0.2F).noOcclusion()));
    public static final RegistrySupplier<Block> RUSTIC_SOFA = registerWithItem("rustic_sofa", () -> new SofaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final RegistrySupplier<Block> RUSTIC_DRESSER = registerWithItem("rustic_dresser", () -> new DresserBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), () -> SoundEvents.WOODEN_TRAPDOOR_OPEN, () -> SoundEvents.WOODEN_TRAPDOOR_CLOSE));
    public static final RegistrySupplier<Block> RUSTIC_SINK = registerWithItem("rustic_sink", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS), false));
    public static final RegistrySupplier<Block> RUSTIC_WASHBASIN = registerWithItem("rustic_washbasin", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), true));
    public static final RegistrySupplier<Block> RUSTIC_CABINET = registerWithItem("rustic_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), () -> SoundEvents.WOODEN_TRAPDOOR_OPEN, () -> SoundEvents.WOODEN_TRAPDOOR_CLOSE, () -> false));
    public static final RegistrySupplier<Block> RUSTIC_COOKING_AISLE = registerWithItem("rustic_cooking_aisle", () -> new ConnectibleCabinetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), () -> SoundEvents.WOODEN_TRAPDOOR_OPEN, () -> SoundEvents.WOODEN_TRAPDOOR_CLOSE, () -> false));
    public static final RegistrySupplier<Block> RUSTIC_WALL_CABINET = registerWithItem("rustic_wall_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), () -> SoundEvents.WOODEN_TRAPDOOR_OPEN, () -> SoundEvents.WOODEN_TRAPDOOR_CLOSE, () -> true));
    public static final RegistrySupplier<Block> RUSTIC_SMOKER = registerWithItem("rustic_smoker", () -> new SmokeOvenBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOKER)));
    public static final RegistrySupplier<Block> RUSTIC_WARDROBE = registerWithItem("rustic_wardrobe", () -> new WardrobeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final RegistrySupplier<Block> RUSTIC_BATHTUB = registerWithItem("rustic_bathtub", () -> new BathtubBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).noOcclusion().strength(1.5f, 3.0f)));
    public static final RegistrySupplier<Block> RUSTIC_PRIVY = registerWithItem("rustic_privy", () -> new PrivyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));
    public static final RegistrySupplier<Block> RUSTIC_TABLE = registerWithItem("rustic_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> RUSTIC_CHAIR = registerWithItem("rustic_chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).pushReaction(PushReaction.IGNORE)));
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
        return GeneralUtil.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, HearthAndHaven.identifier(name), block);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return GeneralUtil.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, HearthAndHaven.identifier(path), block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return GeneralUtil.registerItem(ITEMS, ITEM_REGISTRAR, HearthAndHaven.identifier(path), itemSupplier);
    }
}
