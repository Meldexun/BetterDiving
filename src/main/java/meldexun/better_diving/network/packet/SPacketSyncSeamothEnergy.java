package meldexun.better_diving.network.packet;

import io.netty.buffer.ByteBuf;
import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SPacketSyncSeamothEnergy implements IMessage {

	private int energy;

	public SPacketSyncSeamothEnergy() {

	}

	public SPacketSyncSeamothEnergy(EntitySeamoth seamoth) {
		this.energy = seamoth.getEnergy();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.energy = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.energy);
	}

	public int getEnergy() {
		return this.energy;
	}

}
