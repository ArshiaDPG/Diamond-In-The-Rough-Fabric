package net.digitalpear.ditr.init;


import net.digitalpear.ditr.DiamondInTheRough;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class DDBlocks {
    public static BlockItem createBlockItem(String blockID, Block block){
        return Registry.register(Registries.ITEM, Identifier.of(DiamondInTheRough.MOD_ID, blockID), new BlockItem(block, new Item.Settings()));
    }

    public static Block createBlockWithItem(String blockID, Block block){
        createBlockItem(blockID, block);
        return Registry.register(Registries.BLOCK, Identifier.of(DiamondInTheRough.MOD_ID, blockID), block);
    }
    public static Block createBlockWithoutItem(String blockID, Block block){
        return Registry.register(Registries.BLOCK, Identifier.of(DiamondInTheRough.MOD_ID, blockID), block);
    }

    public static final Block OBSIDIAN_DIAMOND_ORE = createBlockWithItem("obsidian_diamond_ore", new ExperienceDroppingBlock(UniformIntProvider.create(3, 7),
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN).strength(52.0F, 1200.0F).instrument(NoteBlockInstrument.BASEDRUM).pistonBehavior(PistonBehavior.NORMAL)));


    public static void init(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.addAfter(Items.DEEPSLATE_DIAMOND_ORE, OBSIDIAN_DIAMOND_ORE));
    }
}
