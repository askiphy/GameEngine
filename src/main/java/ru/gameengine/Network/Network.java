package ru.gameengine.Network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import ru.gameengine.GameEngine;
import ru.gameengine.Network.Packets.*;

import java.io.*;

public class Network {

    public static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(GameEngine.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        net.messageBuilder(SDialogPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SDialogPacket::new)
                .encoder(SDialogPacket::toBytes)
                .consumer(SDialogPacket::handle)
                .add();

        net.messageBuilder(SCutscene.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SCutscene::new)
                .encoder(SCutscene::toBytes)
                .consumer(SCutscene::handle)
                .add();


        net.messageBuilder(SEndDialogPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SEndDialogPacket::new)
                .encoder(SEndDialogPacket::toBytes)
                .consumer(SEndDialogPacket::handle)
                .add();

        net.messageBuilder(ClientPacket.class, id())
                .decoder(ClientPacket::new)
                .encoder(ClientPacket::encode)
                .consumer(ClientPacket::handle)
                .add();

        net.messageBuilder(ActionPacket.class, id())
                .decoder(ActionPacket::new)
                .encoder(ActionPacket::encode)
                .consumer(ActionPacket::handle)
                .add();

        INSTANCE = net;
    }

    public static byte[] toByte(Object object){
        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object toObj(byte[] bytes){
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = null;
            ois = new ObjectInputStream(bis);
            Object object = ois.readObject();
            return object;
        } catch (IOException | ClassNotFoundException e) {
            GameEngine.LOGGER.info(e);
            throw new RuntimeException(e);
        }
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, PlayerEntity player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), message);
    }
}
