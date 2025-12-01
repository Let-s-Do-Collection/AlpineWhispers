package net.satisfy.alpinewhispers.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.satisfy.alpinewhispers.AlpineWhispers;

public class SoundEventRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(AlpineWhispers.MOD_ID, Registries.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> WINTER_MAGIC_SOUND_EVENT = SOUND_EVENTS.register("winter_magic_sound_event", () -> SoundEvent.createFixedRangeEvent(AlpineWhispers.identifier("winter_magic"), 16f));
    public static final RegistrySupplier<SoundEvent> REINDEER_AMBIENT = SOUND_EVENTS.register("reindeer_ambient", () -> SoundEvent.createFixedRangeEvent(AlpineWhispers.identifier("reindeer_ambient"), 16f));
    public static final RegistrySupplier<SoundEvent> REINDEER_HURT = SOUND_EVENTS.register("reindeer_hurt", () -> SoundEvent.createFixedRangeEvent(AlpineWhispers.identifier("reindeer_hurt"), 16f));
    public static final RegistrySupplier<SoundEvent> REINDEER_DEATH = SOUND_EVENTS.register("reindeer_death", () -> SoundEvent.createFixedRangeEvent(AlpineWhispers.identifier("reindeer_death"), 16f));
    public static final RegistrySupplier<SoundEvent> FALLING_SNOW = SOUND_EVENTS.register("falling_snow", () -> SoundEvent.createFixedRangeEvent(AlpineWhispers.identifier("falling_snow"), 16f));
    public static final RegistrySupplier<SoundEvent> GROVE_AMBIENT = SOUND_EVENTS.register("grove_ambient", () -> SoundEvent.createFixedRangeEvent(AlpineWhispers.identifier("grove_ambient"), 16f));

    public static final ResourceKey<JukeboxSong> WINTER_MAGIC = song("winter_magic");

    private static ResourceKey<JukeboxSong> song(String id) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, AlpineWhispers.identifier(id));
    }

    public static void init() {
        SOUND_EVENTS.register();
    }
}