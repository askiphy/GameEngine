package ru.gameengine.Dialogue;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import ru.gameengine.Characters.Character.CharacterEntity;
import ru.gameengine.Characters.CharacterInit;
import ru.gameengine.GameEngine;
import ru.gameengine.Network.Network;
import ru.gameengine.Network.Packets.SEndDialogPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DialogScreen extends Screen {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(GameEngine.MODID, "textures/gui/dialog_gui.png");
    String heroSay;
    Bench[] benches;
    List<Button> buttons = new ArrayList<>();
    int y = 50;

    //byte[] instance;

    public DialogScreen(String heroSay, Bench[] benches) {
        super(new StringTextComponent("dialog"));
        this.heroSay = heroSay;
        this.benches = benches;
        //this.instance = instance;
    }

    @Override
    protected void init() {
        for (Bench bench: benches) {
            Button button = this.addButton(new Button(this.width / 2 - 75, this.height / 4 + y, 150, 20, new StringTextComponent("> " + bench.playerSay), (p_213021_1_) -> {
                String[] strings = bench.dialog.herosay.split("\\.");
                if (Objects.equals(strings[0], "end")){
                    this.minecraft.setScreen(null);
                }
                else {
                    this.minecraft.setScreen(new DialogScreen(bench.dialog.herosay, bench.dialog.benches));
                }
            }));
            button.setFGColor(0x4a04b3);
            button.setAlpha(0.0f);
            buttons.add(button);
            y+=35;
        }
        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        this.minecraft.getTextureManager().bind(BACKGROUND);
        this.blit(matrixStack, this.width / 2 -100, this.height / 2 - 100, 256, 256, 227, 168);
        drawCenteredString(matrixStack, this.font, heroSay, this.width / 2, 85, 16777215);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
