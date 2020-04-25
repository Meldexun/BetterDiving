package meldexun.better_diving;

import org.apache.logging.log4j.Logger;

import meldexun.better_diving.init.ModBlocks;
import meldexun.better_diving.init.ModCapabilities;
import meldexun.better_diving.init.ModItems;
import meldexun.better_diving.init.ModPackets;
import meldexun.better_diving.init.ModWorldGenerators;
import meldexun.better_diving.init.SeabaseModules;
import meldexun.better_diving.integration.ArtemisLib;
import meldexun.better_diving.integration.IndustrialCraft;
import meldexun.better_diving.integration.MatterOverdrive;
import meldexun.better_diving.integration.Metamorph;
import meldexun.better_diving.integration.TerraFirmaCraft;
import meldexun.better_diving.integration.Vampirism;
import meldexun.better_diving.network.GuiHandler;
import meldexun.better_diving.proxy.IProxy;
import meldexun.better_diving.util.BetterDivingConfig;
import meldexun.better_diving.util.ByteBufHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = BetterDiving.MOD_ID, name = BetterDiving.NAME, version = BetterDiving.VERSION, acceptedMinecraftVersions = BetterDiving.ACCEPTED_VERSIONS)
public class BetterDiving {

	public static final String MOD_ID = "better_diving";
	public static final String NAME = "Better Diving";
	public static final String VERSION = "1.5.1";
	public static final String ACCEPTED_VERSIONS = "[1.12.2]";

	public static final String CLIENT_PROXY_CLASS = "meldexun.better_diving.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "meldexun.better_diving.proxy.ServerProxy";

	public static final CreativeTabs TAB_BETTER_DIVING = new CreativeTabs("tab_better_diving") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.DIVING_MASK);
		}
	};

	@Mod.Instance(BetterDiving.MOD_ID)
	public static BetterDiving instance;

	@SidedProxy(clientSide = BetterDiving.CLIENT_PROXY_CLASS, serverSide = BetterDiving.SERVER_PROXY_CLASS)
	public static IProxy proxy;

	public static Logger logger;

	public static SimpleNetworkWrapper network;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		BetterDiving.logger = event.getModLog();
		BetterDiving.network = NetworkRegistry.INSTANCE.newSimpleChannel(BetterDiving.MOD_ID);

		ByteBufHelper.copy(BetterDivingConfig.MASTER_CONFIG, BetterDivingConfig.SLAVE_CONFIG, true);

		BetterDiving.proxy.preInit();

		ModCapabilities.registerCapabilities();
		ModPackets.registerMessages();
		SeabaseModules.registerModules();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		BetterDiving.proxy.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(BetterDiving.MOD_ID, new GuiHandler());

		ModItems.registerOreDictionaryEntries();
		ModItems.registerFurnaceRecipes();
		ModBlocks.addBlockDrops();
		ModWorldGenerators.registerWorldGenerators();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		BetterDiving.proxy.postInit();

		ArtemisLib.setLoaded();
		IndustrialCraft.setLoaded();
		MatterOverdrive.setLoaded();
		Metamorph.setLoaded();
		TerraFirmaCraft.setLoaded();
		Vampirism.setLoaded();
	}

}
