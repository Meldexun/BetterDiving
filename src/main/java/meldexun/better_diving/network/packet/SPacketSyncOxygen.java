package meldexun.better_diving.network.packet;

import io.netty.buffer.ByteBuf;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SPacketSyncOxygen implements IMessage {

	private int oxygenPlayer;

	public SPacketSyncOxygen() {

	}

	public SPacketSyncOxygen(EntityPlayer player) {
		this.oxygenPlayer = player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null).getOxygen();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.oxygenPlayer = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.oxygenPlayer);
	}

	public int getOxygenPlayer() {
		return this.oxygenPlayer;
	}

}
