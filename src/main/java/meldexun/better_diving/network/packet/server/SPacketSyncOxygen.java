package meldexun.better_diving.network.packet.server;

import java.util.function.Supplier;

import meldexun.better_diving.capability.oxygen.entity.CapabilityOxygenProvider;
import meldexun.better_diving.client.ClientBetterDiving;
import meldexun.better_diving.network.packet.IPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class SPacketSyncOxygen implements IPacket {

	private int oxygen;

	public SPacketSyncOxygen() {

	}

	public SPacketSyncOxygen(int oxygen) {
		this.oxygen = oxygen;
	}

	@Override
	public void encode(PacketBuffer buffer) {
		buffer.writeInt(this.oxygen);
	}

	@Override
	public void decode(PacketBuffer buffer) {
		this.oxygen = buffer.readInt();
	}

	@Override
	public boolean handle(Supplier<Context> ctxSupplier) {
		PlayerEntity player = ClientBetterDiving.getPlayer();
		player.getCapability(CapabilityOxygenProvider.OXYGEN).ifPresent(cap -> cap.setOxygen(this.oxygen));
		return true;
	}

}
