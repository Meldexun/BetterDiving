package meldexun.better_diving.network.packet;

import io.netty.buffer.ByteBuf;
import meldexun.better_diving.item.ItemTool;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SPacketSyncSeaglideEnergy implements IMessage {

	private EnumHand hand;
	private int energy;

	public SPacketSyncSeaglideEnergy() {

	}

	public SPacketSyncSeaglideEnergy(EnumHand hand, ItemStack stack) {
		this.hand = hand;
		this.energy = ItemTool.getEnergy(stack);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.hand = buf.readBoolean() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
		this.energy = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(this.hand == EnumHand.MAIN_HAND);
		buf.writeInt(this.energy);
	}

	public int getEnergy() {
		return this.energy;
	}

	public EnumHand getHand() {
		return this.hand;
	}

}
