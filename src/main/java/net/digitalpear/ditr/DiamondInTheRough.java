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
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
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

    public static void registerBottleDispenser(){
        DispenserBlock.registerBehavior(Items.DRAGON_BREATH, new ItemDispenserBehavior(){
            @Override
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                ServerWorld world = pointer.world();
                BlockPos blockPos2 = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
                Direction direction = pointer.state().get(DispenserBlock.FACING);
                if (world.getBlockState(blockPos2).isIn(DDBlockTagProvider.OBSIDIAN_ORE_REPLACEABLES)){
                    List<Block> ores = new ArrayList<>();
                    Registries.BLOCK.iterateEntries(DDBlockTagProvider.DRAGON_MADE_ORES).forEach((entry) -> ores.add(entry.value()));
                    if (!ores.isEmpty()){
                        world.playSound(null, blockPos2, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        world.setBlockState(blockPos2, Util.getRandom(ores, world.getRandom()).getDefaultState());
                        ItemStack itemStack = pointer.blockEntity().addToFirstFreeSlot(new ItemStack(Items.GLASS_BOTTLE));
                        if (!itemStack.isEmpty()){
                            ItemDispenserBehavior.spawnItem(world, new ItemStack(Items.GLASS_BOTTLE), 1, direction, blockPos2.toCenterPos());
                        }
                        stack.decrement(1);
                        return stack;
                    }
                    else{
                        LOGGER.info("List of obsidian ores is empty!");
                    }

                }
                return super.dispenseSilently(pointer, stack);
            }
        });
    }

    public static void registerBottleConversion(){
        registerBottleDispenser();
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            ItemStack stack = player.getStackInHand(hand);
            BlockPos pos = hitResult.getBlockPos();
            if (stack.isOf(Items.DRAGON_BREATH) && world.getBlockState(pos).isIn(DDBlockTagProvider.OBSIDIAN_ORE_REPLACEABLES)){
                if (world instanceof ServerWorld && !world.getServer().getGameRules().getBoolean(HAND_CONVERSION)){
                    return ActionResult.FAIL;
                }
                List<Block> ores = new ArrayList<>();
                Registries.BLOCK.iterateEntries(DDBlockTagProvider.DRAGON_MADE_ORES).forEach((entry) -> ores.add(entry.value()));

                if (!ores.isEmpty()){
                    if (world instanceof ServerWorld){
                        world.setBlockState(pos, Util.getRandom(ores, world.getRandom()).getDefaultState(), Block.NOTIFY_ALL);
                        stack.decrementUnlessCreative(1, player);
                        if (!player.isCreative()){
                            player.giveItemStack(Items.GLASS_BOTTLE.getDefaultStack());
                        }
                    }
                    player.swingHand(hand);
                    player.playSound(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, 1.0F, 1.0F);
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
