package meldexun.better_diving.init;

import meldexun.better_diving.BetterDiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BetterDivingSounds {

	private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BetterDiving.MOD_ID);

	public static final RegistryObject<SoundEvent> SEAMOTH_ENTER = SOUNDS.register("seamoth_enter", () -> new SoundEvent(new ResourceLocation(BetterDiving.MOD_ID, "seamoth_enter")));
	public static final RegistryObject<SoundEvent> SEAMOTH_START = SOUNDS.register("seamoth_start", () -> new SoundEvent(new ResourceLocation(BetterDiving.MOD_ID, "seamoth_start")));
	public static final RegistryObject<SoundEvent> SEAMOTH_ENGINE_LOOP = SOUNDS.register("seamoth_engine_loop", () -> new SoundEvent(new ResourceLocation(BetterDiving.MOD_ID, "seamoth_engine_loop")));

	public static void registerSounds() {
		SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

}
