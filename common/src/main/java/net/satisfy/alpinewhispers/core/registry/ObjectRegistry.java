package net.satisfy.alpinewhispers.core.registry;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.core.block.*;
import net.satisfy.alpinewhispers.core.block.CozyBedBlock;
import net.satisfy.alpinewhispers.core.item.FoodBlockItem;
import net.satisfy.alpinewhispers.core.item.TreeBaublesItem;
import net.satisfy.alpinewhispers.core.item.WinterHatItem;
import net.satisfy.alpinewhispers.core.util.GeneralUtil;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ObjectRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(AlpineWhispers.MOD_ID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(AlpineWhispers.MOD_ID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();

    public static final RegistrySupplier<Block> FROZEN_DIRT = registerWithItem("frozen_dirt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));
    public static final RegistrySupplier<Block> ICICLES = registerWithItem("icicles", () -> new IcicleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).noOcclusion()));

    public static final RegistrySupplier<Block> ALPINE_GNEISS = registerWithItem("alpine_gneiss", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)));
    public static final RegistrySupplier<Block> ALPINE_GNEISS_STAIRS = registerWithItem("alpine_gneiss_stairs", () -> new StairBlock(ALPINE_GNEISS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));
    public static final RegistrySupplier<Block> ALPINE_GNEISS_SLAB = registerWithItem("alpine_gneiss_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final RegistrySupplier<Block> ALPINE_GNEISS_WALL = registerWithItem("alpine_gneiss_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> ALPINE_GNEISS_BRICKS = registerWithItem("alpine_gneiss_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)));
    public static final RegistrySupplier<Block> ALPINE_GNEISS_BRICK_STAIRS = registerWithItem("alpine_gneiss_brick_stairs", () -> new StairBlock(ALPINE_GNEISS_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));
    public static final RegistrySupplier<Block> ALPINE_GNEISS_BRICK_SLAB = registerWithItem("alpine_gneiss_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final RegistrySupplier<Block> ALPINE_GNEISS_BRICK_WALL = registerWithItem("alpine_gneiss_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));


    public static final RegistrySupplier<Block> LAYERED_ALPINE_GNEISS_BRICKS = registerWithItem("layered_alpine_gneiss_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)));
    public static final RegistrySupplier<Block> LAYERED_ALPINE_GNEISS_BRICK_STAIRS = registerWithItem("layered_alpine_gneiss_brick_stairs", () -> new StairBlock(LAYERED_ALPINE_GNEISS_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));
    public static final RegistrySupplier<Block> LAYERED_ALPINE_GNEISS_BRICK_SLAB = registerWithItem("layered_alpine_gneiss_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final RegistrySupplier<Block> LAYERED_ALPINE_GNEISS_BRICK_WALL = registerWithItem("layered_alpine_gneiss_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> MOSSY_ALPINE_GNEISS_BRICKS = registerWithItem("mossy_alpine_gneiss_bricks", () -> new FrostyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)));
    public static final RegistrySupplier<Block> MOSSY_ALPINE_GNEISS_BRICK_STAIRS = registerWithItem("mossy_alpine_gneiss_brick_stairs", () -> new FrostyStairBlock(MOSSY_ALPINE_GNEISS_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));
    public static final RegistrySupplier<Block> MOSSY_ALPINE_GNEISS_BRICK_SLAB = registerWithItem("mossy_alpine_gneiss_brick_slab", () -> new FrostySlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final RegistrySupplier<Block> MOSSY_ALPINE_GNEISS_BRICK_WALL = registerWithItem("mossy_alpine_gneiss_brick_wall", () -> new FrostyWallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> MOSSY_LAYERED_ALPINE_GNEISS_BRICKS = registerWithItem("mossy_layered_alpine_gneiss_bricks", () -> new FrostyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE)));
    public static final RegistrySupplier<Block> MOSSY_LAYERED_ALPINE_GNEISS_BRICK_STAIRS = registerWithItem("mossy_layered_alpine_gneiss_brick_stairs", () -> new FrostyStairBlock(MOSSY_LAYERED_ALPINE_GNEISS_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_STAIRS)));
    public static final RegistrySupplier<Block> MOSSY_LAYERED_ALPINE_GNEISS_BRICK_SLAB = registerWithItem("mossy_layered_alpine_gneiss_brick_slab", () -> new FrostySlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_SLAB)));
    public static final RegistrySupplier<Block> MOSSY_LAYERED_ALPINE_GNEISS_BRICK_WALL = registerWithItem("mossy_layered_alpine_gneiss_brick_wall", () -> new FrostyWallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE_WALL)));

    public static final RegistrySupplier<Block> AROLLA_PINE_LOG = registerWithItem("arolla_pine_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> AROLLA_PINE_WOOD = registerWithItem("arolla_pine_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_AROLLA_PINE_WOOD = registerWithItem("stripped_arolla_pine_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_AROLLA_PINE_LOG = registerWithItem("stripped_arolla_pine_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> AROLLA_PINE_PLANKS = registerWithItem("arolla_pine_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistrySupplier<Block> AROLLA_PINE_STAIRS = registerWithItem("arolla_pine_stairs", () -> new StairBlock(AROLLA_PINE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> AROLLA_PINE_PRESSURE_PLATE = registerWithItem("arolla_pine_pressure_plate", () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD).mapColor(Blocks.SPRUCE_PLANKS.defaultMapColor())));
    public static final RegistrySupplier<Block> AROLLA_PINE_DOOR = registerWithItem("arolla_pine_door", () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.WOOD).noOcclusion().mapColor(Blocks.SPRUCE_PLANKS.defaultMapColor())));
    public static final RegistrySupplier<Block> AROLLA_PINE_FENCE_GATE = registerWithItem("arolla_pine_fence_gate", () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD).mapColor(Blocks.SPRUCE_PLANKS.defaultMapColor())));
    public static final RegistrySupplier<Block> AROLLA_PINE_BUTTON = registerWithItem("arolla_pine_button", () -> woodenButton(FeatureFlags.VANILLA));
    public static final RegistrySupplier<Block> AROLLA_PINE_SLAB = registerWithItem("arolla_pine_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> AROLLA_PINE_TRAPDOOR = registerWithItem("arolla_pine_trapdoor", () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));
    public static final RegistrySupplier<Block> AROLLA_PINE_FENCE = registerWithItem("arolla_pine_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> AROLLA_PINE_WINDOW_PANE = registerWithItem("arolla_pine_window_pane", () -> new WindowBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GLASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> AROLLA_PINE_WINDOW = registerWithItem("arolla_pine_window", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).noOcclusion()));
    public static final RegistrySupplier<Block> AROLLA_PINE_LEAVES = registerWithItem("arolla_pine_leaves", () -> new SnowyLeavesBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS)));
    public static final RegistrySupplier<Block> AROLLA_PINE_SAPLING = registerWithItem("arolla_pine_sapling", () -> new SaplingBlock(new TreeGrower("arolla_pine_mid", Optional.empty(), Optional.of(configuredFeatureKey("arolla_pine_mid")), Optional.empty()), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));

    public static final RegistrySupplier<Block> AROLLA_PINE_BED = registerWithItem("arolla_pine_bed", () -> new CozyBedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAY_BED).sound(SoundType.WOOD).strength(0.2F).noOcclusion()));
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

    public static final RegistrySupplier<Block> CANDLE_WREATH = registerWithItem("candle_wreath", () -> new CandleWreathBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK)));
    public static final RegistrySupplier<Block> WALL_WREATH = registerWithItem("wall_wreath", () -> new WallWreathBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK)));
    public static final RegistrySupplier<Block> FAIRY_LIGHTS = registerWithItem("fairy_lights", () -> new FairyLightsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE).emissiveRendering((state, world, pos) -> true).lightLevel(state -> 9).noOcclusion()));
    public static final RegistrySupplier<Block> GARLAND = registerWithItem("garland", () -> new GarlandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE).emissiveRendering((state, world, pos) -> true).lightLevel(state -> 4)));
    public static final RegistrySupplier<Block> STAR_TOPPER = registerWithItem("star_topper", () -> new StarTopperBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE)));
    public static final RegistrySupplier<Block> TREE_BAUBLES = registerWithoutItem("tree_baubles", () -> new TreeBaublesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE).emissiveRendering((state, world, pos) -> true).lightLevel(state -> 4).noOcclusion()));
    public static final RegistrySupplier<Item> TREE_BAUBLES_ITEM = registerItem("tree_baubles",  () -> new TreeBaublesItem(TREE_BAUBLES.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> WINTER_MAGIC_MUSIC_DISC = registerItem("winter_magic_music_disc", () -> new Item(new Item.Properties().stacksTo(1).jukeboxPlayable(SoundEventRegistry.WINTER_MAGIC)));
    public static final RegistrySupplier<Item> REINDEER_SPAWN_EGG = registerItem("reindeer_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityTypeRegistry.REINDEER_ENTITY, -1, -1, getSettings()));

    public static final RegistrySupplier<Block> SNOW_GENTIAN = registerWithItem("snow_gentian", () -> new FlowerBlock(MobEffects.HEAL, 1, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_SNOW_GENTIAN = registerWithoutItem("potted_snow_gentian", () -> new FlowerPotBlock(SNOW_GENTIAN.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));

    public static final RegistrySupplier<Block> CHRISTMAS_ROSE = registerWithItem("christmas_rose", () -> new FlowerBlock(MobEffects.HEAL, 1, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> POTTED_CHRISTMAS_ROSE = registerWithoutItem("potted_christmas_rose", () -> new FlowerPotBlock(CHRISTMAS_ROSE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> HOARFROST_GRASS = registerWithItem("hoarfrost_grass", () -> new SnowyGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)));
    public static final RegistrySupplier<Block> TALL_HOARFROST_GRASS = registerWithItem("tall_hoarfrost_grass", () -> new SnowyDoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS)));
    public static final RegistrySupplier<Block> SNOW_GLOBE = registerWithItem("snow_globe", () -> new SnowGlobeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));

    public static final RegistrySupplier<Block> HOMESPUN_WOOL = registerWithItem("homespun_wool", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_WOOL)));
    public static final RegistrySupplier<Block> HOMESPUN_CARPET = registerWithItem("homespun_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_CARPET)));
    public static final RegistrySupplier<Item> REINDEER = registerItem("reindeer", () -> new Item(getSettings().food(Foods.MUTTON)));
    public static final RegistrySupplier<Item> COOKED_REINDEER = registerItem("cooked_reindeer", () -> new Item(getSettings().food(Foods.COOKED_MUTTON)));

    public static final RegistrySupplier<Block> COOKED_REINDEER_DISH_BLOCK = registerWithoutItem("cooked_reindeer_dish", () -> new FoodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), 4, new FoodProperties.Builder().nutrition(8).saturationModifier(0.9F).build()));
    public static final RegistrySupplier<Item> COOKED_REINDEER_DISH = registerItem("cooked_reindeer_dish", () -> new FoodBlockItem(COOKED_REINDEER_DISH_BLOCK.get(), getSettings().food(Foods.GOLDEN_CARROT)));

    public static final RegistrySupplier<Item> WINTER_HAT = registerItem("winter_hat", () -> new WinterHatItem(ArmorMaterialRegistry.WINTER_HAT.value(), ArmorItem.Type.HELMET, getSettings().rarity(Rarity.RARE), AlpineWhispers.identifier("textures/models/armor/winter_hat_layer_1.png")));


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

    private static ButtonBlock woodenButton(FeatureFlag... featureFlags) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        if (featureFlags.length > 0) {
            properties = properties.requiredFeatures(featureFlags);
        }

        return new ButtonBlock(BlockSetType.OAK, 30, properties);
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, AlpineWhispers.identifier( name));
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
