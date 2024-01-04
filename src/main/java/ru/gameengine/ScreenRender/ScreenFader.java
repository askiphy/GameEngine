package ru.gameengine.ScreenRender;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ScreenFader { 
    private static final Map<UUID, FadeInfo> fadeMap = new HashMap<>(); 
// Метод для начала затемнения 
    public static void startFade(UUID playerUUID, long duration, ITextComponent text) { 
        fadeMap.put(playerUUID, new FadeInfo(Util.milliTime(), duration, text)); } 
            
        @SubscribeEvent 
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.START) {
                fadeMap.entrySet().removeIf(entry -> {
                    FadeInfo info = entry.getValue();
                    // Удалить элемент, если затемнение закончилось 
                    return Util.milliTime() > info.startTime + info.duration;}
                    );
                }
            } 
            @SubscribeEvent 
            public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
                if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
                    return; 
                }
                Minecraft mc = Minecraft.getInstance();
                // Пройтись по всем активным затемнениям и отрисовать их 
                for (FadeInfo info : fadeMap.values()) { 
                    long currentTime = Util.milliTime();
                    if (currentTime >= info.startTime) { 
                        float opacity = (float) (currentTime - info.startTime) / info.duration;
                        if (opacity > 1.0f) opacity = 1.0f; drawFade(opacity, info.text); 
                        
                    } 
                    
                } 
                
            } 
            // Вспомогательный метод для рендеринга затемнения и текста
            private static void drawFade(float opacity, ITextComponent text) { 
                Minecraft mc = Minecraft.getInstance(); 
                int width = mc.getMainWindow().getScaledWidth(); 
                int height = mc.getMainWindow().getScaledHeight();
                // Рисуем полупрозрачный черный фон
                RenderSystem.disableDepthTest();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.color4f(0.0f, 0.0f, 0.0f, opacity);
                mc.ingameGUI.fill(0, 0, width, height, 0x00000000);
                RenderSystem.disableBlend();
                RenderSystem.enableDepthTest();
                // Рисуем текст по центру экрана
                int textWidth = mc.fontRenderer.getStringWidth(text.getFormattedText());
                mc.fontRenderer.drawStringWithShadow(text.getFormattedText(), (width - textWidth) / 2.0f, height / 2.0f, 0xFFFFFF);
                } 
                
}