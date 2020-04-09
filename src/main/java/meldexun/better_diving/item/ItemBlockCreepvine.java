package meldexun.better_diving.item;

import meldexun.better_diving.block.AbstractBlockCreepvine;
import meldexun.better_diving.init.ModBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockCreepvine extends ItemBlockTooltip {

	public ItemBlockCreepvine() {
		super(ModBlocks.CREEPVINE);
		this.setUnlocalizedName(this.block.getUnlocalizedName());
		this.setRegistryName(this.block.getRegistryName());
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
		if (worldIn.getBlockState(pos.offset(side).down()).getBlock() instanceof AbstractBlockCreepvine) {
			return false;
		}
		return super.canPlaceBlockOnSide(worldIn, pos, side, player, stack);
	}

}
