package meldexun.better_diving.item;

import java.util.List;

import meldexun.better_diving.capability.oxygen.CapabilityOxygenProvider;
import meldexun.better_diving.capability.oxygen.ICapabilityOxygen;
import meldexun.better_diving.util.config.DivingGearConfig;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDivingChest extends AbstractItemDivingGear {

	public ItemDivingChest(ArmorMaterial material, DivingGearConfig config) {
		this(material, config.tankAirStorage, config.tankMovespeed, config.improvedGear, config.reinforcedGear);
	}

	public ItemDivingChest(ArmorMaterial material, int oxygenCapacity, double swimSpeed, boolean isImprovedGear, boolean isReinforcedGear) {
		super(material, EntityEquipmentSlot.CHEST, oxygenCapacity, swimSpeed, 0.0F, isImprovedGear, isReinforcedGear);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return CapabilityOxygenProvider.createProvider(this.oxygenCapacity, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		ICapabilityOxygen ioxygen = stack.getCapability(CapabilityOxygenProvider.OXYGEN, null);
		if (ioxygen.getOxygen() > 0) {
			int oxygen = (int) Math.round(ioxygen.getOxygen() / 20.0D / 3.0D) * 3;
			tooltip.add(oxygen + "s of oxygen");
		} else {
			tooltip.add("empty");
		}
		tooltip.add(I18n.format("item.tank.tooltip", (int) ((double) this.oxygenCapacity / 20.0D)));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
