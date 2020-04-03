package meldexun.better_diving.network;

import meldexun.better_diving.client.gui.GuiSeamothContainer;
import meldexun.better_diving.container.ContainerSeamoth;
import meldexun.better_diving.entity.EntitySeamoth;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int GUI_SEAMOTH_ENTITY = 0;
	public static final int GUI_SEAMOTH_ITEM = 1;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case GUI_SEAMOTH_ENTITY:
			return new ContainerSeamoth(player.inventory, (EntitySeamoth) world.getEntityByID(x));
		case GUI_SEAMOTH_ITEM:
			return new ContainerSeamoth(player.inventory, player.getHeldItem(EnumHand.values()[x]));
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case GUI_SEAMOTH_ENTITY:
			return new GuiSeamothContainer((Container) this.getServerGuiElement(ID, player, world, x, y, z));
		case GUI_SEAMOTH_ITEM:
			return new GuiSeamothContainer((Container) this.getServerGuiElement(ID, player, world, x, y, z));
		default:
			return null;
		}
	}

}
