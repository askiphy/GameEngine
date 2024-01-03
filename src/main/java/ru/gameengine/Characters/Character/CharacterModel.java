package ru.gameengine.Characters.Character;

import net.minecraft.util.ResourceLocation;
import ru.gameengine.GameEngine;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CharacterModel extends AnimatedGeoModel<CharacterEntity> {
    @Override
    public ResourceLocation getModelLocation(CharacterEntity object) {
        String path = object.getModelPath();
        ResourceLocation modelPath = parsePath(path == "" ? "geo/alex.geo" : path);
        return modelPath;
    }

    public ResourceLocation parsePath(String path) {
        String id = path;
        String modId = "gameengine";
        String name = "animations/npc.animation_1.json";
        if(id.indexOf(":") != -1) {
            modId = id.substring(0,id.indexOf(":"));
        }
        name = id.substring(id.indexOf(":")+1);
        return new ResourceLocation(modId,name+(name.endsWith(".json") ? "" : ".json"));
    }


    @Override
    public ResourceLocation getTextureLocation(CharacterEntity object) {
        String path = object.getTexturePath();
        return parsePathTexture(path == "" ? "gameengine:textures/entity/npc" : path.toLowerCase());
    }

    public ResourceLocation parsePathTexture(String path) {
        String id = path;
        String modId = "gameengine";
        String name = "textures/entity/npc";
        if(id.indexOf(":") != -1) {
            modId = id.substring(0,id.indexOf(":"));
        }
        name = id.substring(id.indexOf(":")+1);
        return new ResourceLocation(modId,name+(name.endsWith(".png") ? "" : ".png"));
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CharacterEntity animatable) {
        String path = animatable.getAnimationPath();
        ResourceLocation animPath = parsePath(path == "" ? "animations/character.animation" : path);
        return animPath;
    }

    @Override
    public void setCustomAnimations(CharacterEntity animatable, int instanceId, AnimationEvent animationEvent) {
        try {
            super.setCustomAnimations(animatable, instanceId, animationEvent);
            IBone head = this.getAnimationProcessor().getBone("Head");

            EntityModelData extraData = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
            if (head != null) {
                head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
                head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
