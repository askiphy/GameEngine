package ru.bananus.gameengine.Characters;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.bananus.gameengine.Characters.Camera.CameraEntity;
import ru.bananus.gameengine.Characters.Character.CharacterEntity;
import ru.bananus.gameengine.GameEngine;

public class CharacterInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, GameEngine.MODID);

    public static final RegistryObject<EntityType<CharacterEntity>> CHARACTER =
            ENTITY_TYPES.register("character",
                    () -> EntityType.Builder.of(CharacterEntity::new, EntityClassification.CREATURE)
                            .build(new ResourceLocation(GameEngine.MODID, "character").toString()));

    public static final RegistryObject<EntityType<CameraEntity>> CAMERA =
            ENTITY_TYPES.register("camera",
                    () -> EntityType.Builder.of(CameraEntity::new, EntityClassification.CREATURE)
                            .build(new ResourceLocation(GameEngine.MODID, "camera").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
