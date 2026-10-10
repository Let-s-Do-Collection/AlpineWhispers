package net.satisfy.alpinewhispers.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.satisfy.alpinewhispers.AlpineWhispers;

public class ParticleTypeRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(AlpineWhispers.MOD_ID, Registries.PARTICLE_TYPE);

    public static final RegistrySupplier<SimpleParticleType> SNOW_GLOBE_FLAKE = PARTICLE_TYPES.register("snow_globe_flake", () -> new SimpleParticleType(false) {});

    public static void init() {
        PARTICLE_TYPES.register();
    }
}
