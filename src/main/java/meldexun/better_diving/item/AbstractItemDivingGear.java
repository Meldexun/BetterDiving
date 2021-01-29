package meldexun.better_diving.item;

import meldexun.better_diving.client.ArmorModels;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AbstractItemDivingGear extends ArmorItem {

	public AbstractItemDivingGear(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	@SuppressWarnings("unchecked")
	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
		if (armorSlot == EquipmentSlotType.LEGS) {
			return (A) ArmorModels.modelLegs;
		}
		if (entityLiving instanceof ClientPlayerEntity && ((ClientPlayerEntity) entityLiving).getSkinType().equals("slim")) {
			return (A) ArmorModels.modelSlim;
		}
		return (A) ArmorModels.model;
	}

}
