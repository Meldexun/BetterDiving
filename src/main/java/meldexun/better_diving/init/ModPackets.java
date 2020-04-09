package meldexun.better_diving.init;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.network.handler.CPacketHandlerSyncConfig;
import meldexun.better_diving.network.handler.CPacketHandlerSyncOxygen;
import meldexun.better_diving.network.handler.CPacketHandlerSyncPlayerInput;
import meldexun.better_diving.network.handler.CPacketHandlerSyncSeamothBattery;
import meldexun.better_diving.network.handler.CPacketHandlerSyncSeamothEnergy;
import meldexun.better_diving.network.handler.CPacketHandlerSyncSeamothInput;
import meldexun.better_diving.network.handler.SPacketHandlerCraftRecipe;
import meldexun.better_diving.network.handler.SPacketHandlerSyncPlayerInput;
import meldexun.better_diving.network.handler.SPacketHandlerSyncSeamothInput;
import meldexun.better_diving.network.packet.CPacketCraftRecipe;
import meldexun.better_diving.network.packet.CPacketSyncPlayerInput;
import meldexun.better_diving.network.packet.CPacketSyncSeamothInput;
import meldexun.better_diving.network.packet.SPacketSyncConfig;
import meldexun.better_diving.network.packet.SPacketSyncOxygen;
import meldexun.better_diving.network.packet.SPacketSyncPlayerInput;
import meldexun.better_diving.network.packet.SPacketSyncSeamothBattery;
import meldexun.better_diving.network.packet.SPacketSyncSeamothEnergy;
import meldexun.better_diving.network.packet.SPacketSyncSeamothInput;
import net.minecraftforge.fml.relauncher.Side;

public class ModPackets {

	// Start the IDs at 1 so any unregistered messages (ID 0) throw a more obvious
	// exception when received
	private static int messageID = 1;

	private ModPackets() {

	}

	public static void registerMessages() {
		BetterDiving.network.registerMessage(CPacketHandlerSyncOxygen.class, SPacketSyncOxygen.class, ModPackets.messageID++, Side.CLIENT);
		BetterDiving.network.registerMessage(CPacketHandlerSyncConfig.class, SPacketSyncConfig.class, ModPackets.messageID++, Side.CLIENT);
		BetterDiving.network.registerMessage(CPacketHandlerSyncSeamothEnergy.class, SPacketSyncSeamothEnergy.class, ModPackets.messageID++, Side.CLIENT);
		BetterDiving.network.registerMessage(CPacketHandlerSyncSeamothBattery.class, SPacketSyncSeamothBattery.class, ModPackets.messageID++, Side.CLIENT);
		BetterDiving.network.registerMessage(CPacketHandlerSyncSeamothInput.class, SPacketSyncSeamothInput.class, ModPackets.messageID++, Side.CLIENT);
		BetterDiving.network.registerMessage(CPacketHandlerSyncPlayerInput.class, SPacketSyncPlayerInput.class, ModPackets.messageID++, Side.CLIENT);

		BetterDiving.network.registerMessage(SPacketHandlerSyncSeamothInput.class, CPacketSyncSeamothInput.class, ModPackets.messageID++, Side.SERVER);
		BetterDiving.network.registerMessage(SPacketHandlerSyncPlayerInput.class, CPacketSyncPlayerInput.class, ModPackets.messageID++, Side.SERVER);
		BetterDiving.network.registerMessage(SPacketHandlerCraftRecipe.class, CPacketCraftRecipe.class, ModPackets.messageID++, Side.SERVER);
	}

}
