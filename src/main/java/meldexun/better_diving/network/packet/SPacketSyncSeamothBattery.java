package meldexun.better_diving.network.packet;

import io.netty.buffer.ByteBuf;
import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SPacketSyncSeamothBattery implements IMessage {

	private ItemStack battery;
	private int energy;

	public SPacketSyncSeamothBattery() {

	}

	public SPacketSyncSeamothBattery(EntitySeamoth seamoth) {
		this.battery = seamoth.getBattery();
		this.energy = seamoth.getEnergy();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.battery = ByteBufUtils.readItemStack(buf);
		this.energy = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeItemStack(buf, this.battery);
		buf.writeInt(this.energy);
	}

	public ItemStack getBattery() {
		return this.battery;
	}

	public int getEnergy() {
		return this.energy;
	}

}
