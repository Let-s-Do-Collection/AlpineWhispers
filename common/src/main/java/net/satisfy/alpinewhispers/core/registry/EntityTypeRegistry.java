package net.satisfy.alpinewhispers.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.satisfy.alpinewhispers.AlpineWhispers;
import net.satisfy.alpinewhispers.core.block.entity.*;
import net.satisfy.alpinewhispers.core.entity.ChairEntity;

import java.util.function.Supplier;

import static net.satisfy.alpinewhispers.core.registry.ObjectRegistry.*;

public class EntityTypeRegistry {
    private static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(AlpineWhispers.MOD_ID, Registries.BLOCK_ENTITY_TYPE).getRegistrar();
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(AlpineWhispers.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<CabinetBlockEntity>> CABINET_BLOCK_ENTITY = registerBlockEntity("cabinet", () -> BlockEntityType.Builder.of(CabinetBlockEntity::new, AROLLA_PINE_CABINET.get(), AROLLA_PINE_WALL_CABINET.get(), AROLLA_PINE_DRESSER.get(), AROLLA_PINE_COOKING_AISLE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<SmokeOvenEntity>> SMOKER_BLOCK_ENTITY = registerBlockEntity("smoker", () -> BlockEntityType.Builder.of(SmokeOvenEntity::new, AROLLA_PINE_SMOKER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<WardrobeBlockEntity>> WARDROBE_BLOCK_ENTITY = registerBlockEntity("wardrobe", () -> BlockEntityType.Builder.of(WardrobeBlockEntity::new, AROLLA_PINE_WARDROBE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<BathtubBlockEntity>> BATHTUB_BLOCK_ENTITY = registerBlockEntity("bathtub", () -> BlockEntityType.Builder.of(BathtubBlockEntity::new, AROLLA_PINE_BATHTUB.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<PrivyBlockEntity>> PRIVY_BLOCK_ENTITY = registerBlockEntity("privy", () -> BlockEntityType.Builder.of(PrivyBlockEntity::new, AROLLA_PINE_PRIVY.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<FireplaceCorniceBlockEntity>> FIRE_PLACE_CORNICE_BLOCK_ENTITY = registerBlockEntity("fire_place_cornice", () -> BlockEntityType.Builder.of(FireplaceCorniceBlockEntity::new, FIREPLACE_CORNICE.get()).build(null));

    public static final RegistrySupplier<EntityType<ChairEntity>> CHAIR_ENTITY = registerEntityType(() -> EntityType.Builder.of(ChairEntity::new, MobCategory.MISC).sized(0.001F, 0.001F).build((AlpineWhispers.identifier("chair")).toString()));

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> registerBlockEntity(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(AlpineWhispers.identifier(path), type);
    }

    private static <T extends EntityType<?>> RegistrySupplier<T> registerEntityType(final Supplier<T> type) {
        return ENTITY_TYPES.register(AlpineWhispers.identifier("chair"), type);
    }

    public static void init() {
        ENTITY_TYPES.register();
    }
}
