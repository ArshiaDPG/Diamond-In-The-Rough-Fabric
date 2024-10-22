package net.digitalpear.ditr;


import net.digitalpear.ditr.common.datagen.*;
import net.digitalpear.ditr.common.datagen.tags.DDBlockTagProvider;
import net.digitalpear.ditr.common.datagen.tags.DDItemTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;

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
