package meldexun.better_diving.init;

import java.util.ArrayList;
import java.util.List;

import meldexun.better_diving.BetterDiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(BetterDiving.MOD_ID)
public class ModSounds {

	public static final SoundEvent SEAMOTH_ENGINE_LOOP = null;
	public static final SoundEvent SEAMOTH_ENTER = null;
	public static final SoundEvent SEAMOTH_EXIT = null;
	public static final SoundEvent SEAMOTH_IMPACT = null;
	public static final SoundEvent UNDERWATER_AMBIENCE = null;

	@EventBusSubscriber(modid = BetterDiving.MOD_ID)
	public static class SoundRegistrationHandler {

		public static final List<SoundEvent> ITEMS = new ArrayList<SoundEvent>();

		@SubscribeEvent
		public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
			final SoundEvent[] sounds = { createSoundEvent("seamoth_engine_loop"), createSoundEvent("seamoth_enter"), createSoundEvent("seamoth_exit"), createSoundEvent("seamoth_impact"), createSoundEvent("underwater_ambience") };

			IForgeRegistry<SoundEvent> registry = event.getRegistry();

			for (SoundEvent sound : sounds) {
				registry.register(sound);
			}
		}

		private static SoundEvent createSoundEvent(String name) {
			ResourceLocation location = new ResourceLocation(BetterDiving.MOD_ID, name);
			return new SoundEvent(location).setRegistryName(location);
		}
	}

}
