package meldexun.better_diving.item;

import java.util.List;

import meldexun.better_diving.util.config.DivingGearConfig;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDivingHelm extends AbstractItemDivingGear {

	public ItemDivingHelm(ArmorMaterial material, DivingGearConfig config) {
		this(material, config.improvedGear, config.reinforcedGear);
	}

	public ItemDivingHelm(ArmorMaterial material, boolean isImprovedGear, boolean isReinforcedGear) {
		super(material, EntityEquipmentSlot.HEAD, 0, 0.0D, 0.0F, isImprovedGear, isReinforcedGear);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("item.mask.tooltip"));
		if (this.isImprovedGear) {
			tooltip.add(I18n.format("item.improved_mask.tooltip"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
