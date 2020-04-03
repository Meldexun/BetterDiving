package meldexun.better_diving;

import meldexun.better_diving.init.ModBlocks;
import meldexun.better_diving.init.ModCapabilities;
import meldexun.better_diving.init.ModItems;
import meldexun.better_diving.init.ModPackets;
import meldexun.better_diving.init.ModWorldGenerators;
import meldexun.better_diving.integration.IndustrialCraft;
import meldexun.better_diving.integration.TerraFirmaCraft;
import meldexun.better_diving.integration.Vampirism;
import meldexun.better_diving.network.GuiHandler;
import meldexun.better_diving.proxy.IProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = BetterDiving.MOD_ID, name = BetterDiving.NAME, version = BetterDiving.VERSION, acceptedMinecraftVersions = BetterDiving.ACCEPTED_VERSIONS, dependencies = BetterDiving.DEPENDENCIES)
public class BetterDiving {

	public static final String MOD_ID = "better_diving";
	public static final String NAME = "Better Diving";
	public static final String VERSION = "1.4.14";
	public static final String ACCEPTED_VERSIONS = "[1.12.2]";
	public static final String DEPENDENCIES = "after:biomesoplenty";

	public static final String CLIENT_PROXY_CLASS = "meldexun.better_diving.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "meldexun.better_diving.proxy.ServerProxy";

	@SidedProxy(clientSide = BetterDiving.CLIENT_PROXY_CLASS, serverSide = BetterDiving.SERVER_PROXY_CLASS)
	public static IProxy proxy;

	public static final SimpleNetworkWrapper CONNECTION = NetworkRegistry.INSTANCE.newSimpleChannel(BetterDiving.MOD_ID);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();

		ModCapabilities.registerCapabilities();
		ModPackets.registerMessages();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		proxy.init();

		NetworkRegistry.INSTANCE.registerGuiHandler(BetterDiving.MOD_ID, new GuiHandler());

		ModItems.registerOreDictionaryEntries();
		ModItems.registerFurnaceRecipes();
		ModBlocks.addBlockDrops();
		ModWorldGenerators.registerWorldGenerators();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();

		TerraFirmaCraft.loaded = Loader.isModLoaded("tfc");
		IndustrialCraft.loaded = Loader.isModLoaded("ic2");
		Vampirism.loaded = Loader.isModLoaded("vampirism");
	}

	public static CreativeTabs TAB_BETTER_DIVING = new CreativeTabs("tab_better_diving") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.DIVING_MASK);
		}
	};

}
