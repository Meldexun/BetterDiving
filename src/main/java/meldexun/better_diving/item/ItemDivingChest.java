package meldexun.better_diving.item;

import java.util.List;

import meldexun.better_diving.capability.item.oxygen.CapabilityOxygenProvider;
import meldexun.better_diving.capability.item.oxygen.ICapabilityOxygen;
import meldexun.better_diving.util.config.DivingGearConfig;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDivingChest extends AbstractItemDivingGear {

	public ItemDivingChest(ArmorMaterial material, DivingGearConfig config) {
		super(material, EntityEquipmentSlot.CHEST, config);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		ICapabilityOxygen ioxygen = stack.getCapability(CapabilityOxygenProvider.OXYGEN, null);
		if (ioxygen.getOxygen() > 0) {
			int oxygen = (int) Math.round(ioxygen.getOxygen() / 20.0D / 3.0D) * 3;
			tooltip.add(I18n.format("tooltip.oxygen", oxygen));
		} else {
			tooltip.add(I18n.format("tooltip.oxygen_empty"));
		}
		tooltip.add(I18n.format("item.tank.tooltip", (int) (this.getOxygenCapacity() / 20.0D)));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public int getOxygenCapacity() {
		return this.config.tankAirStorage;
	}

	@Override
	public double getSwimSpeed() {
		return this.config.tankMovespeed;
	}

}
