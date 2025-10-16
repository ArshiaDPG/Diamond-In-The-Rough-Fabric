package net.digitalpear.ditr.init;

import net.digitalpear.ditr.DiamondInTheRough;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class DDConfiguredFeatures {
    public static RegistryKey<ConfiguredFeature<?, ?>> of(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, DiamondInTheRough.id(id));
    }
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_BEDROCK_DIAMOND = of("ore_bedrock_diamond");
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        RuleTest ruleTest = new BlockMatchRuleTest(Blocks.BEDROCK);
        List<OreFeatureConfig.Target> list3 = List.of(OreFeatureConfig.createTarget(ruleTest, DDBlocks.BEDROCK_DIAMOND_ORE.getDefaultState()));

        ConfiguredFeatures.register(featureRegisterable, ORE_BEDROCK_DIAMOND, Feature.ORE, new OreFeatureConfig(list3, 12, 0.7F));
    }
}
