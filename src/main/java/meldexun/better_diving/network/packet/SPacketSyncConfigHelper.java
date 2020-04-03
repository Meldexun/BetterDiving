package meldexun.better_diving.network.packet;

import io.netty.buffer.ByteBuf;
import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.BetterDivingConfigClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SPacketSyncConfigHelper implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {
		BetterDivingConfigClient.itemEntityMovement = buf.readBoolean();
		BetterDivingConfigClient.itemEntityMovementFactor = buf.readDouble();
		BetterDivingConfigClient.oxygenSyncPackets = buf.readBoolean();
		BetterDivingConfigClient.seamothEnergySyncPackets = buf.readBoolean();
		BetterDivingConfigClient.vanillaDivingMovement = buf.readBoolean();
		BetterDivingConfigClient.swimSpeedLimitLower = buf.readDouble();
		BetterDivingConfigClient.swimSpeedLimitUpper = buf.readDouble();

		BetterDivingConfigClient.blockBreaking = buf.readBoolean();
		BetterDivingConfigClient.divingMovement = buf.readBoolean();
		BetterDivingConfigClient.oxygenHandling = buf.readBoolean();

		BetterDivingConfigClient.airEfficiency = buf.readBoolean();
		BetterDivingConfigClient.airEfficiencyLimit = buf.readInt();
		BetterDivingConfigClient.seamothSpeed = buf.readDouble();
		BetterDivingConfigClient.seamothEnergyUsage = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(BetterDivingConfig.GENERAL.itemEntityMovement);
		buf.writeDouble(BetterDivingConfig.GENERAL.itemEntityMovementFactor);
		buf.writeBoolean(BetterDivingConfig.GENERAL.oxygenSyncPackets);
		buf.writeBoolean(BetterDivingConfig.GENERAL.seamothEnergySyncPackets);
		buf.writeBoolean(BetterDivingConfig.GENERAL.vanillaDivingMovement);
		buf.writeDouble(BetterDivingConfig.GENERAL.swimSpeedLimitLower);
		buf.writeDouble(BetterDivingConfig.GENERAL.swimSpeedLimitUpper);

		buf.writeBoolean(BetterDivingConfig.MODULES.blockBreaking);
		buf.writeBoolean(BetterDivingConfig.MODULES.divingMovement);
		buf.writeBoolean(BetterDivingConfig.MODULES.oxygenHandling);

		buf.writeBoolean(BetterDivingConfig.DIVING_VALUES.airEfficiency);
		buf.writeInt(BetterDivingConfig.DIVING_VALUES.airEfficiencyLimit);
		buf.writeDouble(BetterDivingConfig.DIVING_VALUES.seamothSpeed);
		buf.writeInt(BetterDivingConfig.DIVING_VALUES.seamothEnergyUsage);
	}

}
