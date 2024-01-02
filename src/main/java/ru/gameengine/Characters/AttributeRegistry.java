package ru.gameengine.Characters;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.gameengine.Characters.Camera.CameraEntity;
import ru.gameengine.Characters.Character.CharacterEntity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeRegistry {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(CharacterInit.CHARACTER.get(), CharacterEntity.setAttributes());
        event.put(CharacterInit.CAMERA.get(), CameraEntity.setAttributes().build());
    }
}
