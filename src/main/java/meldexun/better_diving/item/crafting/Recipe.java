package meldexun.better_diving.item.crafting;

import org.apache.commons.lang3.ArrayUtils;

import meldexun.better_diving.util.CraftingHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Recipe extends IForgeRegistryEntry.Impl<Recipe> {

	public static final IForgeRegistry<Recipe> REGISTRY = GameRegistry.findRegistry(Recipe.class);

	private ItemStack output;
	private ItemStack[] input;
	private boolean isRecipeValid;

	public Recipe(ItemStack output, ItemStack[] input) {
		this.output = output;
		this.input = input;
		this.fixInput();
		this.isRecipeValid = this.isOutputValid() && this.isInputValid();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder(this.getRegistryName() + "{" + this.output + ", [");
		if (this.input == null || this.input.length == 0) {
			s.append("null");
		} else {
			s.append(this.input[0]);
			for (int i = 1; i < this.input.length; i++) {
				s.append(", " + this.input[i]);
			}
		}
		s.append("]}");
		return s.toString();
	}

	private void fixInput() {
		if (this.input != null && this.input.length >= 1) {
			for (int i = 0; i < this.input.length; i++) {
				ItemStack stack = this.input[i];

				if (stack == null || stack.isEmpty()) {
					this.input = ArrayUtils.remove(this.input, i--);
				} else {
					for (int j = 0; j < i; j++) {
						ItemStack stack1 = this.input[j];

						if (CraftingHelper.areItemStacksEqualIgnoreCount(stack, stack1)) {
							stack1.setCount(stack1.getCount() + stack.getCount());
							this.input = ArrayUtils.remove(this.input, i--);
							break;
						}
					}
				}
			}
		}
	}

	public boolean canCraft(EntityPlayer player) {
		if (player.isCreative()) {
			return true;
		}
		NonNullList<ItemStack> inventory = player.inventory.mainInventory;
		for (ItemStack stack : this.input) {
			if (!CraftingHelper.remove(inventory, stack, true)) {
				return false;
			}
		}
		return true;
	}

	public boolean tryCraft(EntityPlayer player) {
		if (player.world.isRemote) {
			return false;
		}
		if (!player.isCreative()) {
			if (!this.canCraft(player)) {
				return false;
			}
			NonNullList<ItemStack> inventory = player.inventory.mainInventory;
			for (ItemStack stack : this.input) {
				CraftingHelper.remove(inventory, stack, false);
			}
		}
		ItemStack stack = this.output.copy();
		if (!player.addItemStackToInventory(stack)) {
			player.entityDropItem(stack, 0.0F);
		}
		if (player.openContainer != null) {
			player.openContainer.detectAndSendChanges();
		}
		return true;
	}

	private boolean isOutputValid() {
		return this.output != null && !this.output.isEmpty();
	}

	private boolean isInputValid() {
		return this.input != null && this.input.length >= 1;
	}

	public boolean isRecipeValid() {
		return this.isRecipeValid;
	}

	public ItemStack getOutput() {
		return this.output.copy();
	}

	public ItemStack[] getInput() {
		ItemStack[] array = new ItemStack[this.input.length];
		for (int i = 0; i < this.input.length; i++) {
			array[i] = this.input[i].copy();
		}
		return array;
	}

}
