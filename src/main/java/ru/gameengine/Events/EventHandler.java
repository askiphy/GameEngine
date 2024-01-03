package ru.gameengine.Events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import ru.gameengine.Commands.MainCommand;
import ru.gameengine.Utils.LogUtils;
import ru.gameengine.GameEngine;
import ru.gameengine.GamesAPI.Root;
import ru.gameengine.GamesAPI.data.ActionPacketData;
import ru.gameengine.Network.Network;
import ru.gameengine.Network.Packets.ActionPacket;

@Mod.EventBusSubscriber(modid = GameEngine.MODID)
public class EventHandler {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if(event.phase != TickEvent.Phase.START) return;
        boolean actBtnDown = ClientBusEvents.keyStory.consumeClick();
        if(actBtnDown) {
            ActionPacketData pack = new ActionPacketData();
            pack.playKeyPressed = actBtnDown;
            sendActionPacket(pack);
        }
    }

    @SubscribeEvent
    public static void onCommandsReg(RegisterCommandsEvent event) {
        MainCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void serverTick(TickEvent.ServerTickEvent event) { Root.tick(); }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void clientChat(ClientChatEvent event) {
        ActionPacketData pack = new ActionPacketData();
        pack.messageSent = event.getMessage().toLowerCase();
        event.setResult(Event.Result.ALLOW);
        sendActionPacket(pack);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void playerJoined(PlayerEvent.PlayerLoggedInEvent event) {}

    @SubscribeEvent()
    public static void serverStarted(FMLServerStartedEvent event) {
        LogUtilitis.sendInformationMessage();
        Root.reloadStories();
    }

    public static void sendActionPacket(ActionPacketData pack) {
        Minecraft mc = Minecraft.getInstance();

        Network.INSTANCE.send(PacketDistributor.SERVER.noArg(),
                new ActionPacket(pack));
    }

}
