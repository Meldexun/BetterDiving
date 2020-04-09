package meldexun.better_diving.item;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSeaglide extends ItemTool {

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (worldIn.isRemote && entityIn instanceof EntityPlayer && ItemSeaglide.isUsingSeaglide((EntityPlayer) entityIn)) {

		}
	}

	public static boolean canUseSeaglide(EntityPlayer player) {
		ItemStack stack = player.getHeldItemMainhand();
		return stack.getItem() instanceof ItemSeaglide && ItemTool.hasEnergy(stack) && player.isInsideOfMaterial(Material.WATER);
	}

	@SideOnly(Side.CLIENT)
	private static boolean isUsingSeaglide(EntityPlayer player) {
		GameSettings settings = Minecraft.getMinecraft().gameSettings;
		return settings.keyBindForward.isKeyDown() && !settings.keyBindBack.isKeyDown() && ItemSeaglide.canUseSeaglide(player);
	}

}
