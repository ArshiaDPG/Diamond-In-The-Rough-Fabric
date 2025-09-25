package net.digitalpear.ditr.common.datagen.tags;


import net.digitalpear.ditr.DiamondInTheRough;
import net.digitalpear.ditr.init.DDBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class DDBlockTagProvider extends FabricTagProvider.BlockTagProvider {


    public static final TagKey<Block> OBSIDIAN_ORE_REPLACEABLES = of("obsidian_ore_replaceables");
    public static final TagKey<Block> DRAGON_MADE_ORES = of("dragon_made_ores");

    public DDBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    private static TagKey<Block> of(String id) {
        return of(DiamondInTheRough.MOD_ID, id);
    }
    private static TagKey<Block> of(String nameSpace, String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(nameSpace, id));
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        valueLookupBuilder(BlockTags.DIAMOND_ORES).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);
        valueLookupBuilder(BlockTags.PICKAXE_MINEABLE).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);
        valueLookupBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);
        valueLookupBuilder(BlockTags.DRAGON_IMMUNE).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);

        valueLookupBuilder(OBSIDIAN_ORE_REPLACEABLES).forceAddTag(ConventionalBlockTags.NORMAL_OBSIDIANS);
        valueLookupBuilder(DRAGON_MADE_ORES).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);

        valueLookupBuilder(ConventionalBlockTags.NORMAL_OBSIDIANS).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);
    }

}
