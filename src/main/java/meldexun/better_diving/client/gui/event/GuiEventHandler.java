package meldexun.better_diving.client.gui.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.diving.ICapabilityDivingAttributes;
import meldexun.better_diving.client.gui.GuiOxygen;
import meldexun.better_diving.client.gui.GuiSeamoth;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Side.CLIENT)
public class GuiEventHandler {

	private GuiEventHandler() {

	}

	private static final GuiOxygen GUI_OXYGEN = new GuiOxygen();
	private static final GuiSeamoth GUI_SEAMOTH = new GuiSeamoth();

	@SubscribeEvent
	public static void onRenderGameOverlayEventPost(RenderGameOverlayEvent.Post event) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		ICapabilityDivingAttributes idiving = player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);
		BetterDivingConfig config = BetterDivingConfig.getInstance();

		if (event.getType() == ElementType.ALL) {
			if (config.modules.oxygenHandling && config.client.guiOxygenConfig.enabled && !player.isCreative() && !player.isSpectator()) {
				int mode = config.client.guiOxygen;
				if (mode == 0 || (mode == 1 && player.isInWater()) || (mode == 2 && player.isInsideOfMaterial(Material.WATER)) || (mode == 3 && (player.isInsideOfMaterial(Material.WATER) || idiving.getOxygenFromPlayerInPercent() < 1.0D))) {
					GuiEventHandler.GUI_OXYGEN.render();
				}
			}
			if (player.getRidingEntity() instanceof EntitySeamoth && config.client.guiSeamothConfig.enabled) {
				GuiEventHandler.GUI_SEAMOTH.render();
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onRenderGameOverlayEventPre(RenderGameOverlayEvent.Pre event) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		ICapabilityDivingAttributes idiving = player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);
		BetterDivingConfig config = BetterDivingConfig.getInstance();

		if (event.getType() == ElementType.AIR && config.modules.oxygenHandling) {
			if (config.client.guiOxygenConfig.enabled) {
				event.setCanceled(true);
			} else {
				int oxygen = idiving.getOxygenFromPlayer() > 0 ? (int) (idiving.getOxygenFromPlayerInPercent() * 300.0D) : 0;
				player.setAir(oxygen);
			}
		}
	}

}
