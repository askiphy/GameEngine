package ru.bananus.gameengine.Network.Packets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import ru.bananus.gameengine.GameEngine;
import ru.bananus.gameengine.GamesAPI.Game;
import ru.bananus.gameengine.GamesAPI.Instances.SceneInstance;
import ru.bananus.gameengine.GamesAPI.Root;
import ru.bananus.gameengine.GamesAPI.data.ActionPacketData;

import java.util.function.Supplier;

public class ActionPacket {
    public boolean isKeyPressed;
    public String messageChatted;

    public ActionPacket(ActionPacketData data) {
        isKeyPressed = data.playKeyPressed;
        messageChatted = data.messageSent;
    }

    public ActionPacket(boolean isKeyPressed, String messageChatted) {
        this.isKeyPressed = isKeyPressed;
        this.messageChatted = messageChatted;
    }

    public ActionPacket(PacketBuffer buffer) {
        isKeyPressed = buffer.readBoolean();
        messageChatted = buffer.readUtf();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeBoolean(isKeyPressed);
        buffer.writeUtf(messageChatted);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if(context.get().getSender() == null) return;
            PlayerEntity sender = context.get().getSender();
            Game story = Root.getActiveStory();
            if(story == null) return;
            SceneInstance playerScene = story.getActiveSceneForPlayer(sender.getUUID());
            if(playerScene == null) return;
            ActionPacketData packetData = new ActionPacketData();
            packetData.messageSent = messageChatted;
            packetData.playKeyPressed = isKeyPressed;
            packetData.scene = playerScene;
            playerScene.applyActPacket(packetData);
        });
        context.get().setPacketHandled(true);
    }
}
