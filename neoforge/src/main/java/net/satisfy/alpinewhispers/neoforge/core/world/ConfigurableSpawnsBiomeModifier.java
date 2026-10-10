package net.satisfy.alpinewhispers.neoforge.core.world;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.common.world.MobSpawnSettingsBuilder;
import net.satisfy.alpinewhispers.core.config.AlpineWhispersConfig;
import net.satisfy.alpinewhispers.core.registry.EntityTypeRegistry;

public record ConfigurableSpawnsBiomeModifier(HolderSet<Biome> biomes) implements BiomeModifier {
    public static final MapCodec<ConfigurableSpawnsBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(ConfigurableSpawnsBiomeModifier::biomes)
    ).apply(instance, ConfigurableSpawnsBiomeModifier::new));

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (!biomes.contains(biome)) return;
        MobSpawnSettingsBuilder spawns = builder.getMobSpawnSettings();
        if (phase == Phase.REMOVE && AlpineWhispersConfig.replaceVanillaFarmAnimals) {
            spawns.getSpawner(MobCategory.CREATURE).removeIf(data -> data.type == EntityType.SHEEP || data.type == EntityType.COW);
        } else if (phase == Phase.ADD) {
            if (AlpineWhispersConfig.reindeerSpawnWeight > 0) {
                spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityTypeRegistry.REINDEER_ENTITY.get(), AlpineWhispersConfig.reindeerSpawnWeight, AlpineWhispersConfig.reindeerMinGroup, AlpineWhispersConfig.reindeerMaxGroup));
            }
            if (AlpineWhispersConfig.alpineSheepSpawnWeight > 0) {
                spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityTypeRegistry.ALPINE_SHEEP_ENTITY.get(), AlpineWhispersConfig.alpineSheepSpawnWeight, AlpineWhispersConfig.alpineSheepMinGroup, AlpineWhispersConfig.alpineSheepMaxGroup));
            }
        }
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return AlpineWhispersBiomeModifiers.CONFIGURABLE_SPAWNS.get();
    }
}
