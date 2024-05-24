package net.merasgd.client;

import net.fabricmc.api.ClientModInitializer;
import net.merasgd.client.screen.MusicRecorderScreen;
import net.merasgd.client.screen.ScreenHandlers;
import net.merasgd.disc.entity.EntityRegistry;
import net.merasgd.disc.entity.MusicRecorderRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HandledScreens.register(ScreenHandlers.MUSIC_RECORDER_SCREEN_HANDLER, MusicRecorderScreen::new);

        BlockEntityRendererFactories.register(EntityRegistry.MUSIC_RECORDER_ENTITY_TYPE, MusicRecorderRenderer::new);
    }

}
