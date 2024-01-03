package ru.gameengine.Dialogue;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import ru.gameengine.GamesAPI.Instances.SceneInstance;
import ru.gameengine.GamesAPI.data.Action;
import ru.gameengine.Network.Network;
import ru.gameengine.Network.Packets.SDialogPacket;
import ru.gameengine.Utils.SerializableRunnable;

import java.io.Serializable;

public class Dialog implements Serializable {
    String herosay;
    Bench[] benches;
    byte[] instance;
    public byte[] runnable;


    public Dialog(int id, Serializable runnable) {
        this.herosay = "end." + id;
        SerializableRunnable runnable1 = new SerializableRunnable(runnable);
    }

    public Dialog(String heroSay, Bench[] benches){
        this.herosay = heroSay;
        this.benches = benches;
    }


    public void show(PlayerEntity entity) {
        if (entity.level.isClientSide) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.setScreen(new DialogScreen(herosay, benches));
        }
        else {
            for (ServerPlayerEntity player : entity.getServer().getPlayerList().getPlayers()) {
                if (entity instanceof ServerPlayerEntity) {
                    Network.sendToPlayer(new SDialogPacket(herosay, Network.toByte(benches)), player);
                }
            }
        }
    }

    public String getSpeaker() {
        return herosay;
    }

}
