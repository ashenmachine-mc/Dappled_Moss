package com.ashenmachine.dappledmoss;

import com.ashenmachine.dappledmoss.block.DappledMossBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.ints.UniformGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DappledMoss implements ModInitializer {
	public static final String MOD_ID = "dappled_moss";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final ResourceKey<LootTable> ABANDONED_CAMP_COMMON_CHEST = lootTableKey("abandoned_camp_common_chest");
	private static final ResourceKey<LootTable> ABANDONED_CAMP_SECRET_CHEST = lootTableKey("abandoned_camp_secret_chest");

	@Override
	public void onInitialize() {
		DappledMossBlocks.initialize();
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (key.equals(ABANDONED_CAMP_COMMON_CHEST) || key.equals(ABANDONED_CAMP_SECRET_CHEST)) {
				LootPool.Builder pool = LootPool.lootPool()
						.setRolls(Holder.direct(new ConstantValue(1)))
						.add(LootItem.lootTableItem(DappledMossBlocks.DAPPLED_MOSS.asItem())
								.apply(SetItemCountFunction.setCount(Holder.direct(new UniformGenerator(
										Holder.direct(new ConstantValue(1)),
										Holder.direct(new ConstantValue(8)))))))
						.when(LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(
								registries.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.DAPPLED_FOREST))));
				tableBuilder.withPool(pool);
			}
		});
		LOGGER.info("Initializing Dappled Moss...");
	}

	private static ResourceKey<LootTable> lootTableKey(String path) {
		return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath("minecraft", "chests/" + path));
	}
}