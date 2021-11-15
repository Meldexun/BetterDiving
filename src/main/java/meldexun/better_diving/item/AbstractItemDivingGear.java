package meldexun.better_diving.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.ArmorModels;
import meldexun.better_diving.init.BetterDivingItemGroups;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AbstractItemDivingGear extends ArmorItem {

	public AbstractItemDivingGear(IArmorMaterial materialIn, EquipmentSlotType slot) {
		super(materialIn, slot, new Properties().tab(BetterDivingItemGroups.BETTER_DIVING));
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onConfigReload);
	}

	@SuppressWarnings("unchecked")
	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
		if (armorSlot == EquipmentSlotType.LEGS) {
			return (A) ArmorModels.modelLegs;
		}
		if (entityLiving instanceof ClientPlayerEntity && ((ClientPlayerEntity) entityLiving).getModelName().equals("slim")) {
			return (A) ArmorModels.modelSlim;
		}
		return (A) ArmorModels.model;
	}

	private void onConfigReload(ModConfig.ModConfigEvent event) {
		if (!event.getConfig().getModId().equals(BetterDiving.MOD_ID)) {
			return;
		}
		this.maxDamage = this.material.getDurabilityForSlot(this.slot);
		this.defense = this.material.getDefenseForSlot(this.slot);
		this.toughness = this.material.getToughness();
		this.knockbackResistance = this.material.getKnockbackResistance();

		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[this.slot.getIndex()];
		builder.put(Attributes.ARMOR,
				new AttributeModifier(uuid, "Armor modifier", this.defense, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ARMOR_TOUGHNESS,
				new AttributeModifier(uuid, "Armor toughness", this.toughness, AttributeModifier.Operation.ADDITION));
		if (this.knockbackResistance > 0.0F) {
			builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance",
					this.knockbackResistance, AttributeModifier.Operation.ADDITION));
		}
		this.defaultModifiers = builder.build();
	}

}
