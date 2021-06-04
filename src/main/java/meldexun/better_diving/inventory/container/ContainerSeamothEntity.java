package meldexun.better_diving.inventory.container;

import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.init.BetterDivingContainers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IntReferenceHolder;
import net.minecraftforge.items.CapabilityItemHandler;

public class ContainerSeamothEntity extends ContainerSeamoth {

	private final EntitySeamoth seamoth;
	private final IntReferenceHolder entity = IntReferenceHolder.standalone();

	/** Server */
	public ContainerSeamothEntity(int id, PlayerInventory playerInv, EntitySeamoth seamoth) {
		super(BetterDivingContainers.SEAMOTH_ENTITY.get(), id, playerInv, seamoth.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new));
		this.seamoth = seamoth;
		this.addDataSlot(this.entity);
		this.entity.set(seamoth.getId());
	}

	/** Client */
	public ContainerSeamothEntity(int id, PlayerInventory playerInv) {
		super(BetterDivingContainers.SEAMOTH_ENTITY.get(), id, playerInv);
		this.seamoth = null;
		this.addDataSlot(this.entity);
	}

	public int getEntityId() {
		return this.entity.get();
	}

	@Override
	protected void onSeamothSlotChanged(int slot) {
		if (this.seamoth != null) {
			this.seamoth.syncPowerCell();
		}
	}

}
