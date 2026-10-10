package com.ashenmachine.dappledmoss.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class DappledShortGrassBlock extends TallGrassBlock {
	public DappledShortGrassBlock(BlockBehaviour.Properties settings) {
		super(settings);
	}

	// Vanilla hardcodes Blocks.TALL_GRASS here, so we override to grow our own tall grass instead.
	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
		BlockState tall = DappledMossBlocks.DAPPLED_TALL_GRASS.defaultBlockState();
		if (tall.canSurvive(level, pos) && level.isEmptyBlock(pos.above())) {
			DoublePlantBlock.placeAt(level, tall, pos, 2);
		}
	}
}
