package ru.gameengine.Characters.Camera;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CameraRender extends EntityRenderer<CameraEntity> {

    public CameraRender(EntityRendererManager p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(CameraEntity p_114482_) {
        return null;
    }
}
