package meldexun.better_diving.item;

import java.util.List;

import javax.annotation.Nullable;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.ArmorModels;
import meldexun.better_diving.util.config.DivingGearConfig;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractItemDivingGear extends ItemArmor {

	protected final DivingGearConfig config;

	public AbstractItemDivingGear(ArmorMaterial material, EntityEquipmentSlot slot, DivingGearConfig config) {
		super(material, 0, slot);
		this.config = config;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		if (armorSlot == EntityEquipmentSlot.LEGS) {
			return ArmorModels.divingGearModelLegs;
		}
		if (entityLiving instanceof AbstractClientPlayer && ((AbstractClientPlayer) entityLiving).getSkinType().equals("slim")) {
			return ArmorModels.divingGearModelSlim;
		}
		return ArmorModels.divingGearModel;
	}

	@Override
	@Nullable
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (Loader.isModLoaded("mobends") && slot != EntityEquipmentSlot.LEGS) {
			return BetterDiving.MOD_ID + ":textures/models/armor/" + this.getArmorMaterial().getName().substring(14) + "_layer_1_old.png";
		}
		return null;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (this.isReinforced()) {
			tooltip.add(I18n.format("item.reinforced_gear.tooltip"));
		}
	}

	public DivingGearConfig getConfig() {
		return this.config;
	}

	public int getOxygenCapacity() {
		return 0;
	}

	public double getSwimSpeed() {
		return 0.0D;
	}

	public float getBreakSpeed() {
		return 0.0F;
	}

	public boolean isImproved() {
		return this.config.improvedGear;
	}

	public boolean isReinforced() {
		return this.config.reinforcedGear;
	}

}
