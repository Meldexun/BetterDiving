package meldexun.better_diving.structure.event;

import meldexun.better_diving.structure.manager.ModuleManager;
import meldexun.better_diving.structure.manager.StructureManager;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// @EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class WorldEventHandler {

	@SubscribeEvent
	public static void onWorldSaveEvent(WorldEvent.Save event) {
		World world = event.getWorld();
		ModuleManager.onWorldSave(world);
		StructureManager.onWorldSave(world);
	}

	@SubscribeEvent
	public static void onWorldLoadEvent(WorldEvent.Load event) {
		World world = event.getWorld();
		ModuleManager.onWorldLoad(world);
		StructureManager.onWorldLoad(world);
	}

	@SubscribeEvent
	public static void onWorldUnloadEvent(WorldEvent.Unload event) {
		World world = event.getWorld();
		ModuleManager.onWorldUnload(world);
		StructureManager.onWorldUnload(world);
	}

}
