package net.digitalpear.ditr;


import net.digitalpear.ditr.common.datagen.DDLanguageProvider;
import net.digitalpear.ditr.common.datagen.DDLootTableProvider;
import net.digitalpear.ditr.common.datagen.DDModelProvider;
import net.digitalpear.ditr.common.datagen.DDRecipeProvider;
import net.digitalpear.ditr.common.datagen.tags.DDBlockTagProvider;
import net.digitalpear.ditr.common.datagen.tags.DDItemTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DiamondInTheRoughDataGeneration implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(DDModelProvider::new);
        pack.addProvider(DDLootTableProvider::new);
        pack.addProvider(DDLanguageProvider::new);
        pack.addProvider(DDRecipeProvider::new);

        pack.addProvider(DDBlockTagProvider::new);
        pack.addProvider(DDItemTagProvider::new);
    }
}
