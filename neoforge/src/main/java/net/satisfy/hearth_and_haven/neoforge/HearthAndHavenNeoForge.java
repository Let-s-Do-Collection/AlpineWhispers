package net.satisfy.hearth_and_haven.neoforge;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.satisfy.hearth_and_haven.HearthAndHaven;

@Mod(HearthAndHaven.MOD_ID)
public class HearthAndHavenNeoForge {
    
    public HearthAndHavenNeoForge(ModContainer modContainer) {
          HearthAndHaven.init();
    }
}
