package meldexun.better_diving.network.packet.server;

import java.util.function.Supplier;

import meldexun.better_diving.client.ClientBetterDiving;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.item.ItemEnergyStorage;
import meldexun.better_diving.network.packet.IPacket;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class SPacketSyncSeamothEnergy implements IPacket {

	private int entityId;
	private int energy;

	public SPacketSyncSeamothEnergy() {

	}

	public SPacketSyncSeamothEnergy(EntitySeamoth seamoth) {
		this.entityId = seamoth.getEntityId();
		LazyOptional<IItemHandler> optionalItemHandler = seamoth.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if (optionalItemHandler.isPresent()) {
			IItemHandler itemHandler = optionalItemHandler.orElseThrow(NullPointerException::new);
			this.energy = ItemEnergyStorage.getEnergy(itemHandler.getStackInSlot(0));
		}
	}

	@Override
	public void encode(PacketBuffer buffer) {
		buffer.writeInt(this.entityId);
		buffer.writeInt(this.energy);
	}

	@Override
	public void decode(PacketBuffer buffer) {
		this.entityId = buffer.readInt();
		this.energy = buffer.readInt();
	}

	@Override
	public boolean handle(Supplier<Context> ctxSupplier) {
		ctxSupplier.get().enqueueWork(() -> {
			World world = ClientBetterDiving.getWorld();
			Entity entity = world.getEntityByID(this.entityId);
			if (entity instanceof EntitySeamoth) {
				EntitySeamoth seamoth = (EntitySeamoth) entity;

				seamoth.setEnergy(this.energy);
			}
		});
		return true;
	}

}
