package meldexun.better_diving.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class BetterDivingItemGroups {

	public static ItemGroup BETTER_DIVING = new ItemGroup("better_diving") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(BetterDivingItems.FINS.get());
		}
	};

}
