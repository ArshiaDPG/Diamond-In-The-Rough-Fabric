package net.digitalpear.ditr.common.datagen.tags;


import net.digitalpear.ditr.DiamondInTheRough;
import net.digitalpear.ditr.init.DDBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class DDBlockTagProvider extends FabricTagProvider<Block> {
    /**
     * Constructs a new {@link FabricTagProvider} with the default computed path.
     *
     * <p>Common implementations of this class are provided.
     *
     * @param output           the {@link FabricDataOutput} instance

     * @param registriesFuture the backing registry for the tag type
     */
    public DDBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, Registries.BLOCK.getKey(), registriesFuture);
    }

    public static final TagKey<Block> OBSIDIAN_DIAMOND_ORE_REPLACEABLES = of("obsidian_diamond_ore_replaceables");

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(DiamondInTheRough.MOD_ID, id));
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.DIAMOND_ORES).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);
        getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);
        getOrCreateTagBuilder(OBSIDIAN_DIAMOND_ORE_REPLACEABLES).add(Blocks.OBSIDIAN);
    }
}
