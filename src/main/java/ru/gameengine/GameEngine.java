package ru.gameengine;

import io.netty.util.internal.logging.InternalLogger;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.gameengine.Characters.AttributeRegistry;
import ru.gameengine.Characters.CharacterInit;
import ru.gameengine.Characters.RenderRegistry;
import ru.gameengine.Events.ClientBusEvents;
import ru.gameengine.Events.EventHandler;
import ru.gameengine.Items.ItemRegistry;
import ru.gameengine.Network.Network;
import ru.gameengine.Scripts.Script;
import java.util.stream.Collectors;

@Mod("gameengine")
public class GameEngine {
    public static final String MODID = "gameengine";
    public static final Logger LOGGER = LogManager.getLogger();
    
    public GameEngine() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
        Object EventHandler = new EventHandler();
        MinecraftForge.EVENT_BUS.register(EventHandler);
        Object ClientBusEvents = new ClientBusEvents();
        MinecraftForge.EVENT_BUS.register(ClientBusEvents);
        CharacterInit.register(eventBus);
        ItemRegistry.register(eventBus);
        Object RenderRegistry = new RenderRegistry();
        MinecraftForge.EVENT_BUS.register(RenderRegistry);
        Object AttributeRegistry = new AttributeRegistry();
        MinecraftForge.EVENT_BUS.register(AttributeRegistry);
        Object Sc = new Script();
        MinecraftForge.EVENT_BUS.register(Sc);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("[GameEngine] I pre-init");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("[GameEngine] Client Init");
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("gameengine", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("[GameEngine] Server Init");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
