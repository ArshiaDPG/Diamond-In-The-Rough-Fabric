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
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.function.Function;

public class DDBlocks {
    private static RegistryKey<Block> blockKey(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, DiamondInTheRough.id(id));
    }

    public static Block createBlockWithItem(String blockID, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings){
        Block block = Blocks.register(blockKey(blockID), factory, settings);
        Items.register(block);
        return block;
    }

    public static final Block OBSIDIAN_DIAMOND_ORE = createBlockWithItem("obsidian_diamond_ore",
            settings -> new ExperienceDroppingBlock(UniformIntProvider.create(3, 7),settings),
            AbstractBlock.Settings.copy(Blocks.OBSIDIAN).strength(52.0F, 1200.0F).instrument(NoteBlockInstrument.BASEDRUM).pistonBehavior(PistonBehavior.NORMAL));

    public static void init(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.addAfter(Items.DEEPSLATE_DIAMOND_ORE, OBSIDIAN_DIAMOND_ORE));
    }
}
