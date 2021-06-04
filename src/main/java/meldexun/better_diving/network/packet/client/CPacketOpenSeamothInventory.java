package meldexun.better_diving.network.packet.client;

import java.util.function.Supplier;

import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.inventory.container.ContainerSeamothEntity;
import meldexun.better_diving.network.packet.IPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class CPacketOpenSeamothInventory implements IPacket {

	@Override
	public void encode(PacketBuffer buffer) {

	}

	@Override
	public void decode(PacketBuffer buffer) {

	}

	@Override
	public boolean handle(Supplier<Context> ctxSupplier) {
		ctxSupplier.get().enqueueWork(() -> {
			PlayerEntity player = ctxSupplier.get().getSender();
			Entity entity = player.getVehicle();

			if (entity instanceof EntitySeamoth) {
				player.openMenu(new SimpleNamedContainerProvider((id, playerInv, player1) -> {
					return new ContainerSeamothEntity(id, playerInv, (EntitySeamoth) entity);
				}, new TranslationTextComponent("Seamoth")));
			}
		});
		return true;
	}

}
