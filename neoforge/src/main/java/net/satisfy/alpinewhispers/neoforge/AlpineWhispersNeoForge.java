package net.satisfy.alpinewhispers.neoforge;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.satisfy.alpinewhispers.AlpineWhispers;

@Mod(AlpineWhispers.MOD_ID)
public class AlpineWhispersNeoForge {
    
    public AlpineWhispersNeoForge(ModContainer modContainer) {
        AlpineWhispers.init();
    }
}
