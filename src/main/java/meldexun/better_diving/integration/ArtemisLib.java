package meldexun.better_diving.integration;

import org.lwjgl.opengl.GL11;

import com.artemis.artemislib.compatibilities.sizeCap.ISizeCap;
import com.artemis.artemislib.compatibilities.sizeCap.SizeCapPro;
import com.artemis.artemislib.util.attributes.ArtemisLibAttributes;

import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.diving.ICapabilityDivingAttributes;
import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

public class ArtemisLib {

	public static boolean loaded = false;

	private ArtemisLib() {

	}

	public static void setLoaded() {
		ArtemisLib.loaded = Loader.isModLoaded("artemislib");
	}

	@Optional.Method(modid = "artemislib")
	public static void rescale(EntityPlayer player) {
		if (player.hasCapability(SizeCapPro.sizeCapability, null)) {
			ISizeCap isizecap = player.getCapability(SizeCapPro.sizeCapability, null);
			if (isizecap.getTrans()) {
				ICapabilityDivingAttributes idiving = player.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);
				if (player.getRidingEntity() instanceof EntitySeamoth) {
					GL11.glScaled(1.0D, 1.0D / 0.85D, 1.0D);
				} else if (idiving.isDiving()) {
					GL11.glScaled(1.0D, 3.0D, 1.0D);
				}
			}
		}
	}

	@Optional.Method(modid = "artemislib")
	public static double getHeightScale(EntityPlayer player) {
		return player.getEntityAttribute(ArtemisLibAttributes.ENTITY_HEIGHT).getAttributeValue();
	}

	@Optional.Method(modid = "artemislib")
	public static double getWidthScale(EntityPlayer player) {
		return player.getEntityAttribute(ArtemisLibAttributes.ENTITY_WIDTH).getAttributeValue();
	}

}
