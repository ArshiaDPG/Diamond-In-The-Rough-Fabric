package net.digitalpear.ditr.common.datagen.tags;


import net.digitalpear.ditr.init.DDBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class DDItemTagProvider extends FabricTagProvider.ItemTagProvider{


    public DDItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture, new DDBlockTagProvider(output, registriesFuture));
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        valueLookupBuilder(ItemTags.DIAMOND_ORES).add(DDBlocks.OBSIDIAN_DIAMOND_ORE.asItem());
        valueLookupBuilder(ConventionalItemTags.NORMAL_OBSIDIANS).add(DDBlocks.OBSIDIAN_DIAMOND_ORE.asItem());
    }

}
