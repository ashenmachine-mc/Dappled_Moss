package com.ashenmachine.dappledmoss.block;

import com.ashenmachine.dappledmoss.DappledMoss;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.core.registries.Registries;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class DappledMossBlocks {
	public static final Block DAPPLED_MOSS = register("dappled_moss", DappledMossBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK));
	public static final Block DAPPLED_MOSS_CARPET = register("dappled_moss_carpet", DappledMossCarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET));
	public static final Block LEAF_LITTER_RED = register("leaf_litter_red", DappledLeafLitterBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LEAF_LITTER));
	public static final Block LEAF_LITTER_YELLOW = register("leaf_litter_yellow", DappledLeafLitterBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LEAF_LITTER));
	public static final Block LEAF_LITTER_ORANGE = register("leaf_litter_orange", DappledLeafLitterBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LEAF_LITTER));

	public static final Block DAPPLED_SHORT_GRASS = register("dappled_short_grass", DappledShortGrassBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS));
	public static final Block DAPPLED_TALL_GRASS = register("dappled_tall_grass", DappledTallGrassBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS), DoubleHighBlockItem::new);

	private DappledMossBlocks() {
	}

	private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties) {
		return register(name, blockFactory, properties, BlockItem::new);
	}

	private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties, BiFunction<Block, Item.Properties, BlockItem> itemFactory) {
		Identifier id = Identifier.fromNamespaceAndPath(DappledMoss.MOD_ID, name);
		ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
		Block block = blockFactory.apply(properties.setId(blockKey));
		Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
		BlockItem item = itemFactory.apply(block, new Item.Properties().useBlockDescriptionPrefix().setId(itemKey));
		Registry.register(BuiltInRegistries.ITEM, itemKey, item);
		return block;
	}

	public static void initialize() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(tab -> {
			tab.accept(DAPPLED_MOSS.asItem());
			tab.accept(DAPPLED_MOSS_CARPET.asItem());
			tab.accept(LEAF_LITTER_RED.asItem());
			tab.accept(LEAF_LITTER_YELLOW.asItem());
			tab.accept(LEAF_LITTER_ORANGE.asItem());
			tab.accept(DAPPLED_SHORT_GRASS.asItem());
			tab.accept(DAPPLED_TALL_GRASS.asItem());
		});
	}
}