package meldexun.better_diving.network.packet;

import io.netty.buffer.ByteBuf;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.diving.ICapabilityDivingAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SPacketSyncDivingCapability implements IMessage {

	private int maxAir;
	private double swimBaseSpeed;
	private double swimBonusSpeed;
	private float breakSpeed;

	public SPacketSyncDivingCapability() {

	}

	public SPacketSyncDivingCapability(EntityPlayer player) {
		ICapabilityDivingAttributes idiving = player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);

		this.maxAir = idiving.getOxygenCapacity();
		this.swimBaseSpeed = idiving.getSwimSpeedBase();
		this.swimBonusSpeed = idiving.getSwimSpeedBonus();
		this.breakSpeed = idiving.getBreakSpeed();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.maxAir);
		buf.writeDouble(this.swimBaseSpeed);
		buf.writeDouble(this.swimBonusSpeed);
		buf.writeFloat(this.breakSpeed);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.maxAir = buf.readInt();
		this.swimBaseSpeed = buf.readDouble();
		this.swimBonusSpeed = buf.readDouble();
		this.breakSpeed = buf.readFloat();
	}

	public int getMaxAir() {
		return this.maxAir;
	}

	public double getSwimBaseSpeed() {
		return this.swimBaseSpeed;
	}

	public double getSwimBonusSpeed() {
		return this.swimBonusSpeed;
	}

	public float getBreakSpeed() {
		return this.breakSpeed;
	}

}
