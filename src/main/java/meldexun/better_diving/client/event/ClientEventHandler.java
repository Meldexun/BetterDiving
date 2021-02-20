package meldexun.better_diving.client.event;

import java.text.DecimalFormat;
import java.util.List;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.oxygen.item.CapabilityOxygenItemProvider;
import meldexun.better_diving.client.gui.GuiOxygen;
import meldexun.better_diving.client.gui.GuiSeamoth;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.oxygenprovider.DivingGearManager;
import meldexun.better_diving.oxygenprovider.DivingMaskProviderItem;
import meldexun.better_diving.oxygenprovider.MiningspeedProviderItem;
import meldexun.better_diving.oxygenprovider.SwimspeedProviderItem;
import meldexun.better_diving.util.OxygenPlayerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {

	private static final DecimalFormat FORMAT = new DecimalFormat("#.##");

	@SubscribeEvent
	public static void onRenderGameOverlayEventPre(RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getInstance();
		if (event.getType() == ElementType.AIR && BetterDivingConfig.CLIENT_CONFIG.oxygenGuiEnabled.get()) {
			event.setCanceled(true);

			if (mc.player.areEyesInFluid(FluidTags.WATER) || OxygenPlayerHelper.getOxygenRespectEquipment(mc.player) < OxygenPlayerHelper.getOxygenCapacityRespectEquipment(mc.player)) {
				GuiOxygen.render(event.getMatrixStack());
			}
		}
		if (event.getType() == ElementType.ALL && BetterDivingConfig.CLIENT_CONFIG.seamothGuiEnabled.get() && mc.player.getRidingEntity() instanceof EntitySeamoth) {
			GuiSeamoth.render(event.getMatrixStack());
		}
	}

	@SubscribeEvent
	public static void onFOVModifierEvent(EntityViewRenderEvent.FOVModifier event) {
		if (!event.getInfo().getFluidState().isEmpty()) {
			event.setFOV(event.getFOV() * 7.0D / 6.0D);
		}
	}

	@SubscribeEvent
	public static void onItemTooltipEvent(ItemTooltipEvent event) {
		List<ITextComponent> tooltip = event.getToolTip();
		ItemStack stack = event.getItemStack();

		DivingMaskProviderItem divingMaskProvider = DivingGearManager.getDivingMaskProviderItem(stack);
		if (divingMaskProvider != null) {
			tooltip.add(1, new StringTextComponent(TextFormatting.GRAY + String.format("Max Diving Depth %dm", divingMaskProvider.maxDivingDepth)));
		}

		stack.getCapability(CapabilityOxygenItemProvider.OXYGEN).ifPresent(c -> {
			tooltip.add(1, new StringTextComponent(TextFormatting.GRAY + Integer.toString((int) Math.round(c.getOxygen() / 20.0D / 3.0D) * 3) + "s of Oxygen"));
		});

		MiningspeedProviderItem miningSpeedProvider = DivingGearManager.getMiningspeedProviderItem(stack);
		if (miningSpeedProvider != null) {
			tooltip.add(1, new StringTextComponent(TextFormatting.GRAY + (miningSpeedProvider.miningspeed >= 0.0D ? "+" : "") + FORMAT.format(miningSpeedProvider.miningspeed * 100.0D) + "% Mining Speed"));
		}

		SwimspeedProviderItem swimspeedProvider = DivingGearManager.getSwimspeedProviderItem(stack);
		if (swimspeedProvider != null) {
			tooltip.add(1, new StringTextComponent(TextFormatting.GRAY + (swimspeedProvider.swimspeed >= 0.0D ? "+" : "") + FORMAT.format(swimspeedProvider.swimspeed * 100.0D) + "% Swim Speed"));
		}
	}
		}
	}

}
