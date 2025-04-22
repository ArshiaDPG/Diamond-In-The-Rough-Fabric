package net.digitalpear.ditr.common.datagen.tags;


import net.digitalpear.ditr.DiamondInTheRough;
import net.digitalpear.ditr.init.DDBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
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

    public static final TagKey<Block> OBSIDIAN_ORE_REPLACEABLES = of("obsidian_ore_replaceables");
    public static final TagKey<Block> DRAGON_MADE_ORES = of("dragon_made_ores");

    private static TagKey<Block> of(String id) {
        return of(DiamondInTheRough.MOD_ID, id);
    }
    private static TagKey<Block> of(String nameSpace, String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(nameSpace, id));
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.DIAMOND_ORES).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);
        getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);

        getOrCreateTagBuilder(OBSIDIAN_ORE_REPLACEABLES).add(Blocks.OBSIDIAN);
        getOrCreateTagBuilder(DRAGON_MADE_ORES).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);

        getOrCreateTagBuilder(ConventionalBlockTags.NORMAL_OBSIDIANS).add(DDBlocks.OBSIDIAN_DIAMOND_ORE);
    }
}
