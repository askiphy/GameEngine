package ru.gameengine.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class BlackScreenOverlay extends Gui {

    private Minecraft mc;
    private int screenWidth, screenHeight;
    private long startTime;
    private int duration;
    private String text;

    public BlackScreenOverlay(Minecraft mc, int duration, String text) {
        this.mc = mc;
        this.duration = duration;
        this.text = text;

        ScaledResolution scaledResolution = new ScaledResolution(mc);
        this.screenWidth = scaledResolution.getScaledWidth();
        this.screenHeight = scaledResolution.getScaledHeight();
    }

    public void displayBlackScreen() {
        startTime = System.currentTimeMillis();

        mc.getFramebuffer().unbindFramebuffer();
        mc.getFramebuffer().unbindFramebufferTexture();
        mc.getFramebuffer().framebufferRender(screenWidth, screenHeight);

        drawCenteredString(mc.fontRenderer, text, screenWidth / 2, screenHeight / 2, 0xFFFFFF);
    }

    public void update() {
        if (System.currentTimeMillis() - startTime >= duration) {
            mc.getFramebuffer().bindFramebuffer(true);
        }
    }

}


