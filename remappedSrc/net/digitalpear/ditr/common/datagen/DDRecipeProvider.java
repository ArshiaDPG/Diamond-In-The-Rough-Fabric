package net.digitalpear.ditr.common.datagen;

import net.digitalpear.ditr.init.DDBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.List;
import java.util.function.Consumer;

public class DDRecipeProvider extends FabricRecipeProvider {
    public DDRecipeProvider(FabricDataOutput output) {
        super(output);
    }


    @Override
    public void generate(RecipeExporter exporter) {
        offerSmelting(exporter, List.of(DDBlocks.OBSIDIAN_DIAMOND_ORE), RecipeCategory.MISC, Items.DIAMOND, 1.0F, 200, "diamond");
        offerBlasting(exporter, List.of(DDBlocks.OBSIDIAN_DIAMOND_ORE), RecipeCategory.MISC, Items.DIAMOND, 1.0F, 100, "diamond");
    }
}
