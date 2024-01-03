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
        CharacterBuilder npc = new CharacterBuilder("textures/entity/eil.png", new BlockPos(player.getX()-2, player.getY(), player.getZ()), player.level);
        npc.renderItem(new ItemStack(Items.DIAMOND));
    }
}
