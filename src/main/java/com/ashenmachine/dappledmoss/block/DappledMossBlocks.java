package com.ashenmachine.dappledmoss.block;

import com.ashenmachine.dappledmoss.DappledMoss;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.core.registries.Registries;

import java.util.function.Function;

public final class DappledMossBlocks {
	public static final Block DAPPLED_MOSS = register("dappled_moss", DappledMossBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK));
	public static final Block DAPPLED_MOSS_CARPET = register("dappled_moss_carpet", DappledMossCarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET));

	private DappledMossBlocks() {
	}

	private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties) {
		Identifier id = Identifier.fromNamespaceAndPath(DappledMoss.MOD_ID, name);
		ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
		Block block = blockFactory.apply(properties.setId(blockKey));
		Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
		BlockItem item = new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix().setId(itemKey));
		Registry.register(BuiltInRegistries.ITEM, itemKey, item);
		return block;
	}

	public static void initialize() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(tab -> {
			tab.accept(DAPPLED_MOSS.asItem());
			tab.accept(DAPPLED_MOSS_CARPET.asItem());
		});
	}
}