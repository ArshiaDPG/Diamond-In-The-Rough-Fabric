package net.digitalpear.ditr.common.datagen;


import net.digitalpear.ditr.init.DDBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class DDLanguageProvider extends FabricLanguageProvider {


    public DDLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(DDBlocks.OBSIDIAN_DIAMOND_ORE, "Obsidian Diamond Ore");
        translationBuilder.add("gamerule.diamondConversionPercentage", "Obsidian to diamond conversion chance");
        translationBuilder.add("gamerule.handDiamondConversion", "Can obsidian be converted into diamond ore by hand using dragon's breath?");
    }
}
