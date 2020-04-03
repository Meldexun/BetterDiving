package meldexun.better_diving.block;

import meldexun.better_diving.init.ModBlocks;
import meldexun.better_diving.init.ModItems;
import meldexun.better_diving.tileentity.TileEntityCreepvine;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCreepvineSeed extends BlockCreepvine {

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (playerIn.addItemStackToInventory(new ItemStack(ModItems.CREEPVINE_SEED_CLUSTER))) {
			TileEntityCreepvine tileEntity = (TileEntityCreepvine) worldIn.getTileEntity(pos);
			ModBlocks.CREEPVINE.setCreepvine(worldIn, pos, 3, tileEntity.maxHeight, tileEntity.generateSeeds);
			return true;
		}
		return false;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return 12;
	}

	// prevents deletion of creepvine when updating from v1.4.04

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockLiquid.LEVEL);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();
	}

}
