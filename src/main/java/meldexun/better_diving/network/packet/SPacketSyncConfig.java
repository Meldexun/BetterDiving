package meldexun.better_diving.network.packet;

import io.netty.buffer.ByteBuf;
import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.ByteBufHelper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SPacketSyncConfig implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {
		// ByteBufHelper.copy(BetterDivingConfig.MASTER_CONFIG, BetterDivingConfig.SLAVE_CONFIG, false);
		ByteBufHelper.fromBytes(BetterDivingConfig.SLAVE_CONFIG, buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufHelper.toBytes(BetterDivingConfig.SLAVE_CONFIG, buf);
	}

}
