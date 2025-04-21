package xyz.sncf.b004.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class InfoScreen extends Screen {

    private final Screen parent;

    public InfoScreen(Screen parent) {
        super(Component.literal("Infos supplémentaires"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.addRenderableWidget(Button.builder(Component.literal("Retour"), btn -> {
            Minecraft.getInstance().setScreen(parent);
        }).bounds(this.width / 2 - 40, this.height - 30, 80, 20).build());
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        this.renderBackground(gui);
        gui.drawCenteredString(this.font, "Informations de ton choix ici.", this.width / 2, 20, 0xFFFFFF);
        super.render(gui, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false; // N'arrête pas le jeu en arrière-plan
    }
}
