package net.satisfy.alpinewhispers.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.satisfy.alpinewhispers.fabric.datagen.lang.AWEnglishLangGen;
import net.satisfy.alpinewhispers.fabric.datagen.model.AWModelGen;

public class AlpineWhispersDatagen implements DataGeneratorEntrypoint {
    static FabricDataGenerator.Pack mainPack;

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        mainPack = fabricDataGenerator.createPack();

        mainPack.addProvider(AWEnglishLangGen::new);
        mainPack.addProvider(AWModelGen::new);
    }
}
