package meldexun.better_diving.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SPacketSyncPlayerInput implements IMessage {

	private int entityId;
	private boolean isDiving;
	private float divingTick;
	private float divingTickHorizontal;
	private float divingTickVertical;

	public SPacketSyncPlayerInput() {

	}

	public SPacketSyncPlayerInput(int entityId, boolean isDiving, float divingTick, float divingTickHorizontal, float divingTickVertical) {
		this.entityId = entityId;
		this.isDiving = isDiving;
		this.divingTick = divingTick;
		this.divingTickHorizontal = divingTickHorizontal;
		this.divingTickVertical = divingTickVertical;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityId = buf.readInt();
		this.isDiving = buf.readBoolean();
		// this.divingTick = buf.readFloat();
		// this.divingTickHorizontal = buf.readFloat();
		// this.divingTickVertical = buf.readFloat();
		this.divingTick = (float) buf.readByte() / 100;
		this.divingTickHorizontal = (float) buf.readByte() / 100;
		this.divingTickVertical = (float) buf.readByte() / 100;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityId);
		buf.writeBoolean(this.isDiving);
		// buf.writeFloat(this.divingTick);
		// buf.writeFloat(this.divingTickHorizontal);
		// buf.writeFloat(this.divingTickVertical);
		buf.writeByte((byte) (this.divingTick * 100));
		buf.writeByte((byte) (this.divingTickHorizontal * 100));
		buf.writeByte((byte) (this.divingTickVertical * 100));
	}

	public int getEntityId() {
		return this.entityId;
	}

	public boolean isDiving() {
		return this.isDiving;
	}

	public float getDivingTick() {
		return this.divingTick;
	}

	public float getDivingTickHorizontal() {
		return this.divingTickHorizontal;
	}

	public float getDivingTickVertical() {
		return this.divingTickVertical;
	}

}
