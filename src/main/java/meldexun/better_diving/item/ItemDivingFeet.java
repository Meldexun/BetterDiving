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

public class ItemDivingFeet extends AbstractItemDivingGear {

	public ItemDivingFeet(ArmorMaterial material, DivingGearConfig config) {
		this(material, config.finsMovespeed, config.improvedGear, config.reinforcedGear);
	}

	public ItemDivingFeet(ArmorMaterial material, double swimSpeed, boolean isImprovedGear, boolean isReinforcedGear) {
		super(material, EntityEquipmentSlot.FEET, 0, swimSpeed, 0.0F, isImprovedGear, isReinforcedGear);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("item.fins.tooltip", (int) (this.swimSpeed * 100.0D)));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
