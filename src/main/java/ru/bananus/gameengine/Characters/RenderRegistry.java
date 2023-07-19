package ru.bananus.gameengine.Characters;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import ru.bananus.gameengine.Characters.Camera.CameraRender;
import ru.bananus.gameengine.Characters.Character.CharacterRender;

import static ru.bananus.gameengine.GameEngine.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RenderRegistry {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(CharacterInit.CHARACTER.get(), CharacterRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CharacterInit.CAMERA.get(), CameraRender::new);
    }
}
