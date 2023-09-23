package net.digitalpear.ditr.common.datagen;


import net.digitalpear.ditr.init.DDBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.item.Items;

public class DDLootTableProvider extends FabricBlockLootTableProvider {
    public DDLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(DDBlocks.OBSIDIAN_DIAMOND_ORE, oreDrops(DDBlocks.OBSIDIAN_DIAMOND_ORE, Items.DIAMOND));
    }
}
