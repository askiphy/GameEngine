package ru.bananus.gameengine.Network.Packets;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SCutscene {
    public final int entity;
    public SCutscene(int entity) {
        this.entity = entity;
    }
    public SCutscene(PacketBuffer buf) {
        this.entity = buf.readInt();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(entity);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            assert minecraft.level != null;
            Entity entityCamera = minecraft.level.getEntity(entity);
            minecraft.setCameraEntity(entityCamera);
        });
        return true;
    }
}
