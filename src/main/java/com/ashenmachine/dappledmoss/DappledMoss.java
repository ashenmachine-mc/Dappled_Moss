package com.ashenmachine.dappledmoss;

import com.ashenmachine.dappledmoss.block.DappledMossBlocks;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DappledMoss implements ModInitializer {
	public static final String MOD_ID = "dappled_moss";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		DappledMossBlocks.initialize();
		LOGGER.info("Initializing Dappled Moss...");
	}
}