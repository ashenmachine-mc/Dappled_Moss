package com.ashenmachine.dappledmoss.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class DappledLeafLitterBlock extends LeafLitterBlock {
	public DappledLeafLitterBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		if (context.getItemInHand().getItem() instanceof BlockItem blockItem
				&& blockItem.getBlock() instanceof LeafLitterBlock
				&& blockItem.getBlock() != this) {
			return false;
		}

		return super.canBeReplaced(state, context);
	}
}
