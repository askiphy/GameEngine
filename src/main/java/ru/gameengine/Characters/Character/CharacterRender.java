package ru.gameengine.Characters.Character;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.io.File;

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
        File f = new File("resources/gameengine/assets/" + path);
        if (!f.exists()) {
            return new ResourceLocation(modId, "textures/entity/npc.png");
        }
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
            ItemStack heldItem = entity.getMainHandItem();
            if (!heldItem.isEmpty()) {
                stack.pushPose();
                stack.translate(entity.getX(), entity.getY(), entity.getZ());
                stack.translate(0.2, 0.5, 0);
                stack.scale(0.5f, 0.5f, 0.5f);
                Minecraft.getInstance().getItemRenderer().render(heldItem, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, true, stack, Minecraft.getInstance().renderBuffers().bufferSource(), 1, 1, Minecraft.getInstance().getItemRenderer().getModel(heldItem, Minecraft.getInstance().level, entity));
                stack.popPose();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
