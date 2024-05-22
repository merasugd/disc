package net.merasgd.client;

import net.fabricmc.api.ClientModInitializer;
import net.merasgd.client.screen.MusicRecorderScreen;
import net.merasgd.client.screen.ScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HandledScreens.register(ScreenHandlers.MUSIC_RECORDER_SCREEN_HANDLER, MusicRecorderScreen::new);
    }

}
