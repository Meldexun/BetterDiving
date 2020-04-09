package meldexun.better_diving.network.packet;

import io.netty.buffer.ByteBuf;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.item.oxygen.CapabilityOxygenProvider;
import meldexun.better_diving.capability.item.oxygen.ICapabilityOxygen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SPacketSyncOxygen implements IMessage {

	private int oxygenPlayer;
	private int oxygenTank;

	public SPacketSyncOxygen() {

	}

	public SPacketSyncOxygen(EntityPlayer player) {
		this.oxygenPlayer = player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null).getOxygen();
		ICapabilityOxygen ioxygen = player.inventory.armorInventory.get(2).getCapability(CapabilityOxygenProvider.OXYGEN, null);
		if (ioxygen != null) {
			this.oxygenTank = ioxygen.getOxygen();
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.oxygenPlayer = buf.readInt();
		this.oxygenTank = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.oxygenPlayer);
		buf.writeInt(this.oxygenTank);
	}

	public int getOxygenPlayer() {
		return this.oxygenPlayer;
	}

	public int getOxygenTank() {
		return this.oxygenTank;
	}
}
