package ru.gameengine.Scripts;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.util.text.StringTextComponent;
import ru.gameengine.Characters.Utils.CharacterBuilder;
import ru.gameengine.Dialogue.Bench;
import ru.gameengine.Dialogue.Dialog;
import ru.gameengine.Utils.Cutscene;

import java.io.Serializable;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Script {
    static PlayerEntity player;

    @SubscribeEvent
    public void onBreak(BlockEvent.BreakEvent event) {
        player = event.getPlayer();
  /*      CharacterBuilder npc = new CharacterBuilder("textures/entity/npc.png", new BlockPos(player.getX()-2, player.getY(), player.getZ()), player.level);
        npc.setAnimationPath("animations/character.animation.json");
        npc.setAnim("story.npc.happy");*/
   /*   npc.renderItem(new ItemStack(Items.DIAMOND));
        npc.moveEntity(new BlockPos(10, 10, 10), 1);*/
        player.sendMessage(new StringTextComponent("Текст для чата"), player.getUUID());
        CharacterBuilder dex = new CharacterBuilder("textures/entity/mrdexstor.png", new BlockPos(player.getX(), player.getY()+5, player.getZ()), player.level);
        dex.setAnimationPath("animations/character.animation.json");
        dex.setModel("geo/model.geo.json");
        dex.setTexture("textures/entity/mrdexstor.png");
        dex.setAnim("story.npc.ziga");
        
    }
}
