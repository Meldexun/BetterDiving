package meldexun.better_diving.network.packet;

import io.netty.buffer.ByteBuf;
import meldexun.better_diving.item.crafting.Recipe;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class CPacketCraftRecipe implements IMessage {

	private Recipe recipe;

	public CPacketCraftRecipe() {

	}

	public CPacketCraftRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.recipe = ByteBufUtils.readRegistryEntry(buf, Recipe.REGISTRY);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeRegistryEntry(buf, this.recipe);
	}

	public Recipe getRecipe() {
		return this.recipe;
	}

}
