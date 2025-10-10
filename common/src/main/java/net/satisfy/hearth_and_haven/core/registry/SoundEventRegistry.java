package net.satisfy.hearth_and_haven.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.satisfy.hearth_and_haven.HearthAndHaven;

public class SoundEventRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(HearthAndHaven.MOD_ID, Registries.SOUND_EVENT);


    public static void init() {
        SOUND_EVENTS.register();
    }
}