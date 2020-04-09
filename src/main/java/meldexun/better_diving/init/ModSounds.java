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
	@ObjectHolder("ambient.underwater.enter")
	public static final SoundEvent UNDERWATER_ENTER = null;
	@ObjectHolder("ambient.underwater.exit")
	public static final SoundEvent UNDERWATER_EXIT = null;
	@ObjectHolder("ambient.underwater.loop")
	public static final SoundEvent UNDERWATER_AMBIENCE = null;
	@ObjectHolder("ambient.underwater.loop.additions")
	public static final SoundEvent UNDERWATER_AMBIENCE_ADDITIONS = null;
	@ObjectHolder("ambient.underwater.loop.additions.rare")
	public static final SoundEvent UNDERWATER_AMBIENCE_ADDITIONS_RARE = null;
	@ObjectHolder("ambient.underwater.loop.additions.ultra_rare")
	public static final SoundEvent UNDERWATER_AMBIENCE_ADDITIONS_ULTRA_RARE = null;

	private ModSounds() {

	}

	@EventBusSubscriber(modid = BetterDiving.MOD_ID)
	public static class SoundRegistrationHandler {

		public static final List<SoundEvent> ITEMS = new ArrayList<>();

		private SoundRegistrationHandler() {

		}

		@SubscribeEvent
		public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
			final SoundEvent[] sounds = {
					SoundRegistrationHandler.createSoundEvent("seamoth_engine_loop"),
					SoundRegistrationHandler.createSoundEvent("seamoth_enter"),
					SoundRegistrationHandler.createSoundEvent("seamoth_exit"),
					SoundRegistrationHandler.createSoundEvent("seamoth_impact"),
					SoundRegistrationHandler.createSoundEvent("ambient.underwater.enter"),
					SoundRegistrationHandler.createSoundEvent("ambient.underwater.exit"),
					SoundRegistrationHandler.createSoundEvent("ambient.underwater.loop"),
					SoundRegistrationHandler.createSoundEvent("ambient.underwater.loop.additions"),
					SoundRegistrationHandler.createSoundEvent("ambient.underwater.loop.additions.rare"),
					SoundRegistrationHandler.createSoundEvent("ambient.underwater.loop.additions.ultra_rare") };

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
