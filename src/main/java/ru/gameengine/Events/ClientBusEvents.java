package ru.gameengine.Events;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import ru.gameengine.GameEngine;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = GameEngine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientBusEvents {
    public static KeyBinding keyStory;

    @SubscribeEvent
    public static void register(final FMLClientSetupEvent event) {
        keyStory = new KeyBinding("key.gameengine.play_act", InputMappings.Type.KEYSYM, 72, "keylist.gameengine");
        ClientRegistry.registerKeyBinding(keyStory);
    }
}
