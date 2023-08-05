package net.digitalpear.ditr;

import net.digitalpear.ditr.common.datagen.tags.DDBlockTagProvider;
import net.digitalpear.ditr.init.DDBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class DiamondInTheRough implements ModInitializer {
    public static final String MOD_ID = "ditr";

    public static final GameRules.Key<GameRules.IntRule> DIAMOND_CONVERSION_PERCENTAGE = GameRuleRegistry.register("diamondConversionPercentage",
            GameRules.Category.MOBS, GameRuleFactory.createIntRule(40));

    @Override
    public void onInitialize() {
        DDBlocks.init();

        if (FabricLoader.getInstance().isDevelopmentEnvironment()){
            registerFireballConversion(EntityType.FIREBALL);
        }

        registerFireballConversion(EntityType.DRAGON_FIREBALL);
    }

    public static void registerFireballConversion(EntityType<?> fireball){
        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            if (entity.getType() == fireball && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && !(world.getGameRules().getInt(DIAMOND_CONVERSION_PERCENTAGE) < 1)) {
                BlockPos blockPos = entity.getBlockPos();
                int width = world.random.nextBetween(2, 4);

                float radius = (float)(width * 3) * 0.333F + 0.5F;

                BlockPos.iterate(blockPos.add(-width, -width, -width), blockPos.add(width, width, width)).forEach(pos -> {
                    if (world.getBlockState(pos).isIn(DDBlockTagProvider.OBSIDIAN_DIAMOND_ORE_REPLACEABLES) && (pos.getSquaredDistance(blockPos) <= (double)(radius * radius)) && yesDiamond(world)){
                        world.setBlockState(pos, DDBlocks.OBSIDIAN_DIAMOND_ORE.getDefaultState(), Block.NOTIFY_ALL);
                    }
                });
            }
        });
    }

    public static boolean yesDiamond(World world){
        return (world.random.nextBetween(1, 100) <= world.getGameRules().getInt(DIAMOND_CONVERSION_PERCENTAGE));
    }
}
