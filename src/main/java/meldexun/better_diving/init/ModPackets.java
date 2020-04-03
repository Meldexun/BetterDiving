package meldexun.better_diving.init;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.network.handler.CPacketHandlerSyncConfigHelper;
import meldexun.better_diving.network.handler.CPacketHandlerSyncDivingCapability;
import meldexun.better_diving.network.handler.CPacketHandlerSyncOxygen;
import meldexun.better_diving.network.handler.CPacketHandlerSyncSeamothBattery;
import meldexun.better_diving.network.handler.CPacketHandlerSyncSeamothEnergy;
import meldexun.better_diving.network.handler.SPacketHandlerSyncSeamoth;
import meldexun.better_diving.network.handler.SPacketHandlerUpdateConfigHelper;
import meldexun.better_diving.network.packet.CPacketSyncSeamoth;
import meldexun.better_diving.network.packet.CPacketUpdateConfigHelper;
import meldexun.better_diving.network.packet.SPacketSyncConfigHelper;
import meldexun.better_diving.network.packet.SPacketSyncDivingCapability;
import meldexun.better_diving.network.packet.SPacketSyncOxygen;
import meldexun.better_diving.network.packet.SPacketSyncSeamothBattery;
import meldexun.better_diving.network.packet.SPacketSyncSeamothEnergy;
import net.minecraftforge.fml.relauncher.Side;

public class ModPackets {

	// Start the IDs at 1 so any unregistered messages (ID 0) throw a more obvious
	// exception when received
	private static int messageID = 1;

	public static void registerMessages() {
		BetterDiving.CONNECTION.registerMessage(CPacketHandlerSyncDivingCapability.class, SPacketSyncDivingCapability.class, messageID++, Side.CLIENT);
		BetterDiving.CONNECTION.registerMessage(CPacketHandlerSyncOxygen.class, SPacketSyncOxygen.class, messageID++, Side.CLIENT);
		BetterDiving.CONNECTION.registerMessage(CPacketHandlerSyncConfigHelper.class, SPacketSyncConfigHelper.class, messageID++, Side.CLIENT);
		BetterDiving.CONNECTION.registerMessage(CPacketHandlerSyncSeamothEnergy.class, SPacketSyncSeamothEnergy.class, messageID++, Side.CLIENT);
		BetterDiving.CONNECTION.registerMessage(CPacketHandlerSyncSeamothBattery.class, SPacketSyncSeamothBattery.class, messageID++, Side.CLIENT);

		BetterDiving.CONNECTION.registerMessage(SPacketHandlerSyncSeamoth.class, CPacketSyncSeamoth.class, messageID++, Side.SERVER);
		BetterDiving.CONNECTION.registerMessage(SPacketHandlerUpdateConfigHelper.class, CPacketUpdateConfigHelper.class, messageID++, Side.SERVER);
	}

}
