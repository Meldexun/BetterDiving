package meldexun.better_diving.container;

import javax.annotation.Nullable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerFabricator extends Container {

	private static final EntityEquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EntityEquipmentSlot[] { EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET };

	public ContainerFabricator(InventoryPlayer playerInv, EntityPlayer player) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
		}

		for (int k = 0; k < 4; ++k) {
			final EntityEquipmentSlot entityequipmentslot = ContainerFabricator.VALID_EQUIPMENT_SLOTS[k];
			this.addSlotToContainer(new Slot(playerInv, 36 + (3 - k), 8, 8 + k * 18) {
				@Override
				public int getSlotStackLimit() {
					return 1;
				}

				@Override
				public boolean isItemValid(ItemStack stack) {
					return stack.getItem().isValidArmor(stack, entityequipmentslot, player);
				}

				@Override
				public boolean canTakeStack(EntityPlayer playerIn) {
					ItemStack itemstack = this.getStack();
					return !itemstack.isEmpty() && (playerIn.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.canTakeStack(playerIn);
				}

				@Override
				@Nullable
				@SideOnly(Side.CLIENT)
				public String getSlotTexture() {
					return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
				}
			});
		}

		this.addSlotToContainer(new Slot(playerInv, 40, 77, 62) {
			@Override
			@Nullable
			@SideOnly(Side.CLIENT)
			public String getSlotTexture() {
				return "minecraft:items/empty_armor_slot_shield";
			}
		});
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			ItemStack itemstack = itemstack1.copy();

			if (index > 35) {
				if (this.mergeItemStack(itemstack1, 0, 36, false)) {
					return itemstack;
				}
			} else {
				if (this.mergeItemStack(itemstack1, 36, 37, false)) {
					return itemstack;
				} else if (this.mergeItemStack(itemstack1, 37, 38, false)) {
					return itemstack;
				} else if (this.mergeItemStack(itemstack1, 38, 39, false)) {
					return itemstack;
				} else if (this.mergeItemStack(itemstack1, 39, 40, false)) {
					return itemstack;
				} else {
					if (index > 26) {
						if (this.mergeItemStack(itemstack1, 0, 27, false)) {
							return itemstack;
						}
					} else {
						if (this.mergeItemStack(itemstack1, 27, 36, false)) {
							return itemstack;
						}
					}
				}
			}
		}

		return ItemStack.EMPTY;
	}

}
