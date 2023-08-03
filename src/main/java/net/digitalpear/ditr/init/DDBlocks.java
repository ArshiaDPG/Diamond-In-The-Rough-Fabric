package net.digitalpear.ditr.init;


import net.digitalpear.ditr.DiamondInTheRough;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class DDBlocks {
    public static BlockItem createBlockItem(String blockID, Block block){
        return Registry.register(Registries.ITEM, new Identifier(DiamondInTheRough.MOD_ID, blockID), new BlockItem(block, new FabricItemSettings()));
    }

    public static Block createBlockWithItem(String blockID, Block block){
        createBlockItem(blockID, block);
        return Registry.register(Registries.BLOCK, new Identifier(DiamondInTheRough.MOD_ID, blockID), block);
    }
    public static Block createBlockWithoutItem(String blockID, Block block){
        return Registry.register(Registries.BLOCK, new Identifier(DiamondInTheRough.MOD_ID, blockID), block);
    }

    public static final Block OBSIDIAN_DIAMOND_ORE = createBlockWithItem("obsidian_diamond_ore", new ExperienceDroppingBlock(
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN).strength(52.0F, 1200.0F).instrument(Instrument.BASEDRUM).pistonBehavior(PistonBehavior.NORMAL),
            UniformIntProvider.create(3, 7)));


    public static void init(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.addAfter(Items.NETHER_GOLD_ORE, OBSIDIAN_DIAMOND_ORE));
    }
}
