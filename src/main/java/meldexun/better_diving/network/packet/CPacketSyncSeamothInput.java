package meldexun.better_diving.network.packet;

import io.netty.buffer.ByteBuf;
import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class CPacketSyncSeamothInput implements IMessage {

	private int entityId;
	private boolean inputForward;
	private boolean inputRight;
	private boolean inputBack;
	private boolean inputLeft;
	private boolean inputUp;
	private boolean inputDown;
	private float yaw;
	private float pitch;

	public CPacketSyncSeamothInput() {

	}

	public CPacketSyncSeamothInput(EntitySeamoth seamoth) {
		this(seamoth.getEntityId(), seamoth.inputForward, seamoth.inputRight, seamoth.inputBack, seamoth.inputLeft, seamoth.inputUp, seamoth.inputDown, seamoth.rotationYaw, seamoth.rotationPitch);
	}

	public CPacketSyncSeamothInput(int entityId, boolean inputForward, boolean inputRight, boolean inputBack, boolean inputLeft, boolean inputUp, boolean inputDown, float yaw, float pitch) {
		this.entityId = entityId;
		this.inputForward = inputForward;
		this.inputRight = inputRight;
		this.inputBack = inputBack;
		this.inputLeft = inputLeft;
		this.inputUp = inputUp;
		this.inputDown = inputDown;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityId = buf.readInt();
		this.inputForward = buf.readBoolean();
		this.inputRight = buf.readBoolean();
		this.inputBack = buf.readBoolean();
		this.inputLeft = buf.readBoolean();
		this.inputUp = buf.readBoolean();
		this.inputDown = buf.readBoolean();
		this.yaw = buf.readFloat();
		this.pitch = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityId);
		buf.writeBoolean(this.inputForward);
		buf.writeBoolean(this.inputRight);
		buf.writeBoolean(this.inputBack);
		buf.writeBoolean(this.inputLeft);
		buf.writeBoolean(this.inputUp);
		buf.writeBoolean(this.inputDown);
		buf.writeFloat(this.yaw);
		buf.writeFloat(this.pitch);
	}

	public int getEntityId() {
		return this.entityId;
	}

	public boolean getInputForward() {
		return this.inputForward;
	}

	public boolean getInputRight() {
		return this.inputRight;
	}

	public boolean getInputBack() {
		return this.inputBack;
	}

	public boolean getInputLeft() {
		return this.inputLeft;
	}

	public boolean getInputUp() {
		return this.inputUp;
	}

	public boolean getInputDown() {
		return this.inputDown;
	}

	public float getYaw() {
		return this.yaw;
	}

	public float getPitch() {
		return this.pitch;
	}

}
