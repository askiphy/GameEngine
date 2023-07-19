package ru.bananus.gameengine.Characters.Character;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CharacterRender extends GeoEntityRenderer<CharacterEntity> {
    public CharacterRender(EntityRendererManager renderManager) {
        super(renderManager, new CharacterModel());
    }

    public ResourceLocation getEntityTexture(CharacterEntity entity) {
        String path = entity.getTexturePath();
        return parsePath(path == "" ? "gameengine:textures/entity/npc" : path.toLowerCase());
    }

    public ResourceLocation parsePath(String path) {
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
    public void render(CharacterEntity entity, float entityYaw, float partialTicks, MatrixStack stack,
                       IRenderTypeBuffer bufferIn, int packedLightIn) {
        try {
            stack.pushPose();
            super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
            stack.popPose();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
