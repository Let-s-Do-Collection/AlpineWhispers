package net.satisfy.alpinewhispers.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;
import net.satisfy.alpinewhispers.AlpineWhispers;

public class JukeboxSongRegistry {

    public static final ResourceKey<JukeboxSong> WINTER_MAGIC = create();

    private static ResourceKey<JukeboxSong> create() {
        return ResourceKey.create(Registries.JUKEBOX_SONG, AlpineWhispers.identifier("winter_magic"));
    }

}
