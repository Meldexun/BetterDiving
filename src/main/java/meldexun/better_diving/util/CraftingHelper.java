package meldexun.better_diving.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class CraftingHelper {

	public static boolean areItemStacksEqualIgnoreCount(ItemStack stack, ItemStack stack1) {
		if (stack.isEmpty() && stack1.isEmpty()) {
			return true;
		}
		if (!ItemStack.areItemsEqual(stack, stack1)) {
			return false;
		}
		return ItemStack.areItemStackTagsEqual(stack, stack1);
	}

	public static boolean remove(NonNullList<ItemStack> list, ItemStack stack, boolean simulate) {
		int i = stack.getCount();
		for (ItemStack stack1 : list) {
			if (i == 0) {
				break;
			}
			if (CraftingHelper.areItemStacksEqualIgnoreCount(stack, stack1)) {
				int j = Math.min(stack1.getCount(), i);
				i -= j;
				if (!simulate) {
					stack1.shrink(j);
				}
			}
		}
		return i == 0;
	}

}
