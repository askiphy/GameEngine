package ru.gameengine.Network.Packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import ru.gameengine.Dialogue.Bench;
import ru.gameengine.Dialogue.DialogScreen;
import ru.gameengine.Network.Network;

import java.util.function.Supplier;

public class SDialogPacket  {
    private final String heroSay;
    private final byte[] benches;
    public SDialogPacket(String heroSay, byte[] bench) {
        this.heroSay = heroSay;
        this.benches = bench;
    }
    public SDialogPacket(PacketBuffer buf) {
        this.heroSay = buf.readUtf();
        this.benches = buf.readByteArray();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeUtf(heroSay);
        buf.writeByteArray(benches);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.setScreen(new DialogScreen(this.heroSay,(Bench[]) Network.toObj(benches)));
        });
        return true;
    }
}
