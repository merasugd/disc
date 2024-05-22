package net.merasgd.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import net.merasgd.disc.Disc;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MusicRecorderScreen extends HandledScreen<MusicRecorderScreenHandler> {

    private static final Identifier TEXTURE_CONTAINER = new Identifier(Disc.MOD_ID, "textures/gui/music_recorder.png");
    private static final Identifier TEXTURE_PROGRESS = new Identifier(Disc.MOD_ID, "textures/gui/record_progress.png");

    public MusicRecorderScreen(MusicRecorderScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleY = 1000;
        playerInventoryTitleY = 1000;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE_CONTAINER);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundWidth) / 2;

        context.drawTexture(TEXTURE_CONTAINER, x, y + 5, 0, 0, backgroundWidth, backgroundHeight);

        renderArrow(context, x, y);
    }

    private void renderArrow(DrawContext context, int x, int y) {
        if(handler.isCrafting()) {
            context.drawTexture(TEXTURE_PROGRESS, x + 80, y + 40, 0, 0, handler.getScale(), 16);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
