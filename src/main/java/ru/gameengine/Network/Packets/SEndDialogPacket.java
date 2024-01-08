package ru.gameengine.Network.Packets;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import ru.gameengine.Dialogue.Dialog;
import ru.gameengine.Network.Network;
import ru.gameengine.Utils.SerializableRunnable;

import java.util.function.Supplier;

public class SEndDialogPacket {
    private final String end;
    private final byte[] instance;
    public SEndDialogPacket(String end, byte[] instance) {
        this.end = end;
        this.instance = instance;
    }

    public SEndDialogPacket(PacketBuffer buf) {
        this.end = buf.readUtf();
        this.instance = buf.readByteArray();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeUtf(end);
        buf.writeByteArray(instance);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Dialog dialog = (Dialog) Network.toObj(this.instance);
            SerializableRunnable runnable = (SerializableRunnable) Network.toObj(dialog.runnable);
            runnable.run();
        });
        return true;
    }
}
