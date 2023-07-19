package ru.bananus.gameengine.Characters;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.bananus.gameengine.Characters.Camera.CameraEntity;
import ru.bananus.gameengine.Characters.Character.CharacterEntity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeRegistry {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(CharacterInit.CHARACTER.get(), CharacterEntity.setAttributes());
        event.put(CharacterInit.CAMERA.get(), CameraEntity.setAttributes().build());
    }
}
