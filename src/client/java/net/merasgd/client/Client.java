package net.merasgd.disc;

import net.fabricmc.api.ClientModInitializer;
import net.merasgd.disc.screen.MusicRecorderScreen;
import net.merasgd.disc.screen.ScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HandledScreens.register(ScreenHandlers.MUSIC_RECORDER_SCREEN_HANDLER, MusicRecorderScreen::new);
    }

}
