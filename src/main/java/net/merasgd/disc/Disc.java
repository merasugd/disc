package net.merasgd.disc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.merasgd.disc.items.DiscGroup;
import net.merasgd.disc.items.ItemsRegistry;

public class Disc implements ModInitializer {
    public static final String MOD_ID = "disc";
    public static final Logger LOGGER = LoggerFactory.getLogger(Disc.MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing...");

        ItemsRegistry.registerAllItems();
        DiscGroup.registerGroup();
    }
    
}
