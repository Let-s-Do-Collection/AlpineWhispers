package net.satisfy.alpinewhispers.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.satisfy.alpinewhispers.AlpineWhispers;

public class SoundEventRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(AlpineWhispers.MOD_ID, Registries.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> WINTER_MAGIC = SOUND_EVENTS.register("winter_magic", () -> SoundEvent.createFixedRangeEvent(AlpineWhispers.identifier("winter_magic"), 16f));

    public static void init() {
        SOUND_EVENTS.register();
    }
}