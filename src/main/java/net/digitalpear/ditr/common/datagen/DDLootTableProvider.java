package net.digitalpear.ditr.common.datagen;


import net.digitalpear.ditr.init.DDBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class DDLootTableProvider extends FabricBlockLootTableProvider {

    public DDLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(DDBlocks.OBSIDIAN_DIAMOND_ORE, oreDrops(DDBlocks.OBSIDIAN_DIAMOND_ORE, Items.DIAMOND));
    }
}
