package net.digitalpear.ditr.common.datagen;

import net.digitalpear.ditr.init.DDBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DDRecipeProvider extends FabricRecipeProvider {

    public DDRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                offerSmelting(List.of(DDBlocks.OBSIDIAN_DIAMOND_ORE), RecipeCategory.MISC, Items.DIAMOND, 1.0F, 200, "diamond");
                offerBlasting(List.of(DDBlocks.OBSIDIAN_DIAMOND_ORE), RecipeCategory.MISC, Items.DIAMOND, 1.0F, 100, "diamond");
            }
        };
    }

    @Override
    public String getName() {
        return "recipe";
    }
}
