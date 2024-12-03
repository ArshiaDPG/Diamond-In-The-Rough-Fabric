package net.digitalpear.ditr;


import net.digitalpear.ditr.common.datagen.tags.DDBlockTagProvider;
import net.digitalpear.ditr.init.DDBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
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



    public static Identifier id(String name){
        return Identifier.of(MOD_ID, name);
    }


    public static final GameRules.Key<GameRules.IntRule> DIAMOND_CONVERSION_PERCENTAGE = GameRuleRegistry.register("diamondConversionPercentage",
            GameRules.Category.MOBS,
            GameRuleFactory.createIntRule(40));
    public static final GameRules.Key<GameRules.BooleanRule> HAND_CONVERSION = GameRuleRegistry.register("handDiamondConversion",
            GameRules.Category.MISC,
            GameRuleFactory.createBooleanRule(false));

    @Override
    public void onInitialize() {
        DDBlocks.init();

        if (FabricLoader.getInstance().isDevelopmentEnvironment()){
            registerFireballConversion(EntityType.FIREBALL);
        }
        registerFireballConversion(EntityType.DRAGON_FIREBALL);
        registerBottleConversion();
    }

    public static void registerBottleConversion(){
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            ItemStack stack = player.getStackInHand(hand);
            BlockPos pos = hitResult.getBlockPos();
            Random random = world.getRandom();
            if (stack.isOf(Items.DRAGON_BREATH) && world.getBlockState(pos).isIn(DDBlockTagProvider.OBSIDIAN_ORE_REPLACEABLES)){
                if (world instanceof ServerWorld && !world.getServer().getGameRules().getBoolean(HAND_CONVERSION)){
                    return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
                }
                List<Block> ores = new ArrayList<>();
                Registries.BLOCK.iterateEntries(DDBlockTagProvider.DRAGON_MADE_ORES).forEach((entry) -> ores.add(entry.value()));

                if (!ores.isEmpty()){
                    if (world instanceof ServerWorld){
                        world.setBlockState(pos, ores.get(random.nextInt(ores.size())).getDefaultState(), Block.NOTIFY_ALL);
                        if (!player.isCreative()){
                            stack.decrement(1);
                            player.giveItemStack(Items.GLASS_BOTTLE.getDefaultStack());
                        }
                        world.playSound(null, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }
                    return ActionResult.SUCCESS;
                }
                else{
                    LOGGER.info("List of obsidian ores is empty!");
                }
            }
            return ActionResult.PASS;
        });
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

    public static boolean yesDiamond(ServerWorld world){
        return world.getServer().getGameRules().getInt(DIAMOND_CONVERSION_PERCENTAGE) >= world.random.nextBetween(1, 100);
    }
}
