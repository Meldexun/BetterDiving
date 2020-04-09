package meldexun.better_diving.capability.item.oxygen;

import meldexun.better_diving.init.ModItems;
import meldexun.better_diving.item.AbstractItemDivingGear;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;

public class CapabilityOxygen implements ICapabilityOxygen {

	private final ItemStack stack;

	public CapabilityOxygen() {
		this(new ItemStack(ModItems.STANDARD_O2_TANK));
	}

	public CapabilityOxygen(ItemStack stack) {
		this.stack = stack;
		if (!this.stack.hasTagCompound()) {
			this.stack.setTagCompound(new NBTTagCompound());
		}
		if (!this.stack.getTagCompound().hasKey(CapabilityOxygenProvider.LOCATION_OXYGEN.toString())) {
			this.setOxygen(0);
		}
	}

	@Override
	public void setOxygen(int oxygen) {
		if (this.stack.hasTagCompound()) {
			this.stack.getTagCompound().setInteger(CapabilityOxygenProvider.LOCATION_OXYGEN.toString(), oxygen);
		}
	}

	@Override
	public int getOxygen() {
		if (this.stack.hasTagCompound()) {
			return this.stack.getTagCompound().getInteger(CapabilityOxygenProvider.LOCATION_OXYGEN.toString());
		}
		return 0;
	}

	@Override
	public int receiveOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.getOxygenCapacity() - this.getOxygen());
		this.setOxygen(this.getOxygen() + amount);
		return amount;
	}

	@Override
	public int extractOxygen(int amount) {
		amount = MathHelper.clamp(amount, 0, this.getOxygen());
		this.setOxygen(this.getOxygen() - amount);
		return amount;
	}

	@Override
	public int getOxygenCapacity() {
		if (this.stack.getItem() instanceof AbstractItemDivingGear) {
			return ((AbstractItemDivingGear) this.stack.getItem()).getConfig().tankAirStorage;
		}
		return 0;
	}

}
