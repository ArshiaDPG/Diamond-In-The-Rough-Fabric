package net.digitalpear.ditr;

import net.digitalpear.ditr.common.datagen.tags.DDBlockTagProvider;
import net.digitalpear.ditr.init.DDBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DiamondInTheRough implements ModInitializer {
    public static final String MOD_ID = "ditr";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


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

                Random random = world.getRandom();
                BlockPos blockPos = entity.getBlockPos();

                int width = world.random.nextBetween(2, 4);
                float radius = (float)(width * 3) * 0.333F + 0.5F;

                List<Block> ores = new ArrayList<>();
                Registries.BLOCK.iterateEntries(DDBlockTagProvider.DRAGON_MADE_ORES).forEach((entry) -> ores.add(entry.value()));

                if (!ores.isEmpty()){
                    BlockPos.iterate(blockPos.add(-width, -width, -width), blockPos.add(width, width, width)).forEach(pos -> {
                        if (world.getBlockState(pos).isIn(DDBlockTagProvider.OBSIDIAN_ORE_REPLACEABLES) && (pos.getSquaredDistance(blockPos) <= (double)(radius * radius)) && yesDiamond(world)){
                            world.setBlockState(pos, ores.get(random.nextInt(ores.size())).getDefaultState(), Block.NOTIFY_ALL);
                        }
                    });
                }
                else{
                    LOGGER.info("List of obsidian ores is empty!");
                }
            }
        });
    }


    public static boolean yesDiamond(World world){
        return world.getGameRules().getInt(DIAMOND_CONVERSION_PERCENTAGE) >= world.random.nextBetween(1, 100);
    }
}
