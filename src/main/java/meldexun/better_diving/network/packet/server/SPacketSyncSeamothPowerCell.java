package meldexun.better_diving.network.packet.server;

import java.util.function.Supplier;

import meldexun.better_diving.client.ClientBetterDiving;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.network.packet.IPacket;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class SPacketSyncSeamothPowerCell implements IPacket {

	private int entityId;
	private ItemStack powerCell = ItemStack.EMPTY;

	public SPacketSyncSeamothPowerCell() {

	}

	public SPacketSyncSeamothPowerCell(EntitySeamoth seamoth) {
		this.entityId = seamoth.getEntityId();
		LazyOptional<IItemHandler> optionalItemHandler = seamoth.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if (optionalItemHandler.isPresent()) {
			IItemHandler itemHandler = optionalItemHandler.orElseThrow(NullPointerException::new);
			this.powerCell = itemHandler.getStackInSlot(0);
		}
	}

	@Override
	public void encode(PacketBuffer buffer) {
		buffer.writeInt(this.entityId);
		buffer.writeItemStack(this.powerCell, false);
	}

	@Override
	public void decode(PacketBuffer buffer) {
		this.entityId = buffer.readInt();
		this.powerCell = buffer.readItemStack();
	}

	@Override
	public boolean handle(Supplier<Context> ctxSupplier) {
		ctxSupplier.get().enqueueWork(() -> {
			World world = ClientBetterDiving.getWorld();
			Entity entity = world.getEntityByID(this.entityId);
			if (entity instanceof EntitySeamoth) {
				EntitySeamoth seamoth = (EntitySeamoth) entity;

				seamoth.setPowerCell(this.powerCell);
			}
		});
		return true;
	}

}
