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
		super(material, EntityEquipmentSlot.FEET, config);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("item.fins.tooltip", (int) (this.getSwimSpeed() * 100.0D)));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public double getSwimSpeed() {
		return this.config.finsMovespeed;
	}

}
