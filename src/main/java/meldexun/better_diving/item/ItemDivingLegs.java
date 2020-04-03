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

public class ItemDivingLegs extends AbstractItemDivingGear {

	public ItemDivingLegs(ArmorMaterial material, DivingGearConfig config) {
		this(material, (float) config.legsBreakspeed, config.improvedGear, config.reinforcedGear);
	}

	public ItemDivingLegs(ArmorMaterial material, float breakSpeed, boolean isImprovedGear, boolean isReinforcedGear) {
		super(material, EntityEquipmentSlot.LEGS, 0, 0.0D, breakSpeed, isImprovedGear, isReinforcedGear);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("item.wetsuit_leggings.tooltip", (int) ((double) this.breakSpeed * 100.0D)));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
