package meldexun.better_diving.item;

import java.util.List;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.init.ModItems;
import meldexun.better_diving.init.ModSounds;
import meldexun.better_diving.network.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ItemSeamoth extends ItemTooltip {

	public ItemSeamoth() {
		this.setMaxStackSize(1);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			items.add(new ItemStack(this));
			ItemStack stack = new ItemStack(this);
			((ItemStackHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).setStackInSlot(0, new ItemStack(ModItems.POWER_CELL));
			items.add(stack);
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!worldIn.isRemote) {
			if (playerIn.isSneaking()) {
				playerIn.openGui(BetterDiving.instance, GuiHandler.GUI_SEAMOTH_ITEM, worldIn, handIn.ordinal(), 0, 0);
			} else {
				EntitySeamoth seamoth = new EntitySeamoth(worldIn);
				ItemStack battery = playerIn.getHeldItem(handIn).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0).copy();
				((ItemStackHandler) seamoth.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).setStackInSlot(0, battery);
				Vec3d start = new Vec3d(playerIn.posX, playerIn.posY + playerIn.eyeHeight, playerIn.posZ);
				Vec3d look = playerIn.getLookVec();
				Vec3d end = new Vec3d(start.x + 5.0D * look.x, start.y + 5.0D * look.y, start.z + 5.0D * look.z);
				RayTraceResult result = worldIn.rayTraceBlocks(start, end, false, true, false);
				Vec3d vec = (result != null ? result.hitVec : end).subtract(look);

				seamoth.setPosition(vec.x, vec.y, vec.z);
				worldIn.spawnEntity(seamoth);

				if (worldIn.getBlockState(new BlockPos(vec)).getMaterial() == Material.WATER) {
					worldIn.playSound(null, vec.x, vec.y, vec.z, ModSounds.SEAMOTH_EXIT, SoundCategory.NEUTRAL, 0.6F, 1.0F);
				}

				if (!playerIn.capabilities.isCreativeMode) {
					playerIn.getHeldItem(handIn).shrink(1);
				}
			}
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		ItemStack powerCell = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
		if (powerCell.getItem() instanceof ItemPowerCell) {
			IEnergyStorage ienergy = powerCell.getCapability(CapabilityEnergy.ENERGY, null);
			int percent = (int) (100.0D * ienergy.getEnergyStored() / ienergy.getMaxEnergyStored());
			if (flagIn.isAdvanced()) {
				tooltip.add(I18n.format("tooltip.energy_advanced", percent, ienergy.getEnergyStored(), ienergy.getMaxEnergyStored()));
			} else {
				tooltip.add(I18n.format("tooltip.energy", percent));
			}
		} else {
			tooltip.add(I18n.format("tooltip.no_power_cell"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		super.onCreated(stack, worldIn, playerIn);
		IItemHandler iitemhandler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		((ItemStackHandler) iitemhandler).setStackInSlot(0, new ItemStack(ModItems.POWER_CELL));
	}

}
