package net.merasgd.disc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.merasgd.disc.block.BlockRegistry;
import net.merasgd.disc.entity.EntityRegistry;
import net.merasgd.disc.items.DiscGroup;
import net.merasgd.disc.items.ItemsRegistry;
import net.merasgd.disc.recipe.RecipeRegistry;
import net.merasgd.client.screen.ScreenHandlers;
import net.merasgd.disc.sound.SoundRegistry;

public class Disc implements ModInitializer {
    public static final String MOD_ID = "disc";
    public static final Logger LOGGER = LoggerFactory.getLogger(Disc.MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing...");

        DiscGroup.registerGroup();

        ItemsRegistry.registerAllItems();
        BlockRegistry.registerBlocks();
        EntityRegistry.registerEntity();
        ScreenHandlers.registerScreens();
        RecipeRegistry.registerRecipes();
        
        SoundRegistry.registerSound();
    }
    
}
