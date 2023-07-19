package ru.bananus.gameengine.Scripts;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.bananus.gameengine.Characters.Utils.CharacterBuilder;
import ru.bananus.gameengine.Dialogue.Bench;
import ru.bananus.gameengine.Dialogue.Dialog;
import ru.bananus.gameengine.MainAPI.DownloadScreen;
import ru.bananus.gameengine.Utils.Cutscene;

import java.io.Serializable;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Script {
    /*
    static PlayerEntity player;
    static Dialog dialog = new Dialog("Привет. Хочешь я создам NPC?", new Bench[]{
            new Bench("Да",
                    new Dialog(1, (Serializable & Runnable) () -> {
                        CharacterBuilder npc = new CharacterBuilder("textures/entity/eil.png", new BlockPos(player.getX(), player.getY(), player.getZ()), player.level);
                    }))
    });
    @SubscribeEvent
    public void onBreak(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof PlayerEntity && !event.getEntity().level.isClientSide) {
            player = (PlayerEntity) event.getEntity();
            Cutscene cutscene = new Cutscene(player.level);
            cutscene.moveCamera(new BlockPos(event.getEntity().getX()+5, event.getEntity().getY()+10, event.getEntity().getZ()), 1f);
            cutscene.show(player);
        }
    }

     */
}
