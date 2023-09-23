package net.digitalpear.ditr.common.datagen;


import net.digitalpear.ditr.init.DDBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class DDLanguageProvider extends FabricLanguageProvider {
    public DDLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(DDBlocks.OBSIDIAN_DIAMOND_ORE, "Obsidian Diamond Ore");
        translationBuilder.add("gamerule.diamondConversionPercentage", "Obsidian to diamond conversion chance");
    }
}
