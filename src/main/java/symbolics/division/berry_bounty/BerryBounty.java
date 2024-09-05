package symbolics.division.berry_bounty;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import symbolics.division.berry_bounty.registry.BBRegistration;

public class BerryBounty implements ModInitializer {
	public static final String MOD_ID = "berry_bounty";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		BBRegistration.init();

		LOGGER.info("Bountiful berries arriving at a basket near you!");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}