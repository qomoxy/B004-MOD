package xyz.sncf.b004.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int ICON_MARGIN = 5;

    @SuppressWarnings("unused")
    private static final ResourceLocation ICON = new ResourceLocation("b004", "textures/gui/quest_icon.png");
    private Button questButton;

    @SubscribeEvent
    public void onInventoryInit(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof InventoryScreen inventoryScreen) {
            int positionX = inventoryScreen.getGuiLeft();
            int positionY = inventoryScreen.getGuiTop() - HEIGHT - 5;

            questButton = Button.builder(
                            Component.empty(),
                            button -> Minecraft.getInstance().setScreen(new InfoScreen(inventoryScreen))
                    )
                    .bounds(positionX, positionY, WIDTH, HEIGHT)
                    .build();

            event.addListener(questButton);
        }
    }

    @SubscribeEvent
    public void onScreenRender(ScreenEvent.Render.Post event) {
        if (questButton != null && event.getScreen() instanceof InventoryScreen) {
            GuiGraphics gui = event.getGuiGraphics();
            gui.blit(
                    ICON,
                    questButton.getX() + ICON_MARGIN / 2, questButton.getY()+ ICON_MARGIN / 2,
                    0, 0,
                    WIDTH - ICON_MARGIN, HEIGHT - ICON_MARGIN,
                    WIDTH - ICON_MARGIN, HEIGHT - ICON_MARGIN
            );
        }
    }
}
