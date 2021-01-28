package meldexun.better_diving.network.packet;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public interface IPacket {

	void encode(PacketBuffer buffer);

	void decode(PacketBuffer buffer);

	boolean handle(Supplier<NetworkEvent.Context> ctxSupplier);

}
