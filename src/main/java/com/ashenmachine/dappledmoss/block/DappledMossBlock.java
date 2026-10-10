package com.ashenmachine.dappledmoss.block;

import com.ashenmachine.dappledmoss.DappledMoss;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.BonemealableFeaturePlacerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;

// Bonemeal runs the feature named here (defined in data/dappled_moss/worldgen/feature).
public class DappledMossBlock extends BonemealableFeaturePlacerBlock {
	private static final ResourceKey<Feature> BONEMEAL_FEATURE = ResourceKey.create(
			Registries.FEATURE, Identifier.fromNamespaceAndPath(DappledMoss.MOD_ID, "dappled_moss_patch_bonemeal"));

	public DappledMossBlock(BlockBehaviour.Properties settings) {
		super(BONEMEAL_FEATURE, settings);
	}
}