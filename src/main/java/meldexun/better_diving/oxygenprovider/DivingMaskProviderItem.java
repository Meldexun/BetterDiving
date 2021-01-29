package meldexun.better_diving.oxygenprovider;

import java.util.EnumSet;

import javax.annotation.Nullable;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public class DivingMaskProviderItem extends AbstractDivingGear {

	public final int maxDivingDepth;

	public DivingMaskProviderItem(ResourceLocation registryName, int maxDivingDepth) {
		super(registryName, EnumSet.of(EquipmentSlotType.HEAD));
		this.maxDivingDepth = maxDivingDepth;
	}

	@Nullable
	public static DivingMaskProviderItem parse(String config) {
		try {
			String[] configArray = config.split(",");
			for (int i = 0; i < configArray.length; i++) {
				configArray[i] = configArray[i].trim();
			}
			ResourceLocation registryName = new ResourceLocation(configArray[0]);
			int maxDivingDepth = Integer.parseInt(configArray[1]);
			return new DivingMaskProviderItem(registryName, maxDivingDepth);
		} catch (Exception e) {
			return null;
		}
	}

}
