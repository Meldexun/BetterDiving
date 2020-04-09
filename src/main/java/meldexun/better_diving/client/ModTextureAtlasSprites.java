package meldexun.better_diving.client;

import meldexun.better_diving.BetterDiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Side.CLIENT)
public class ModTextureAtlasSprites {

	private ModTextureAtlasSprites() {

	}

	@SubscribeEvent
	public static void registerTextureAtlasSprites(TextureStitchEvent.Pre event) {
		TextureMap map = event.getMap();

		map.registerSprite(new ResourceLocation(BetterDiving.MOD_ID, "items/empty_battery"));
		map.registerSprite(new ResourceLocation(BetterDiving.MOD_ID, "items/empty_power_cell"));
		map.registerSprite(new ResourceLocation(BetterDiving.MOD_ID, "items/empty_upgrade"));
	}

}
