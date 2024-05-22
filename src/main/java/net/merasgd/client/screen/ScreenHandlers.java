package net.merasgd.client.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.merasgd.disc.Disc;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ScreenHandlers {

    public static final ScreenHandlerType<MusicRecorderScreenHandler> MUSIC_RECORDER_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, new Identifier(Disc.MOD_ID, "music_recorder"), new ExtendedScreenHandlerType<>(MusicRecorderScreenHandler::new));

    public static void registerScreens() {
        Disc.LOGGER.info("Registering Screens...");
    }
}
