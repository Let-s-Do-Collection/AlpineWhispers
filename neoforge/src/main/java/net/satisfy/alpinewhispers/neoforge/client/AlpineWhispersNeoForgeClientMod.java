package net.satisfy.alpinewhispers.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.satisfy.alpinewhispers.AlpineWhispers;

@Mod(value = AlpineWhispers.MOD_ID, dist = Dist.CLIENT)
public class AlpineWhispersNeoForgeClientMod {
    public AlpineWhispersNeoForgeClientMod(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
