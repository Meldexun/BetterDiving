package meldexun.better_diving.block;

import java.util.Random;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.init.ModBlocks;
import meldexun.better_diving.tileentity.TileEntityCreepvine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractBlockCreepvine extends BlockUnderwaterBlock {

	public static final int MAX_HEIGHT = 14;

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCreepvine(AbstractBlockCreepvine.MAX_HEIGHT, false);
	}

	public boolean setCreepvine(World world, BlockPos pos, int flags, int maxHeight, boolean generateSeeds) {
		world.setBlockState(pos, this.getDefaultState(), flags);

		TileEntity tileEntity = world.getTileEntity(pos);

		if (tileEntity instanceof TileEntityCreepvine) {
			((TileEntityCreepvine) tileEntity).setMaxHeight(maxHeight);
			((TileEntityCreepvine) tileEntity).setCanGenerateSeeds(generateSeeds);
		} else {
			BetterDiving.logger.error("Failed to properly place creepvine block at {}", pos);
		}

		return true;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.CREEPVINE);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileEntityCreepvine tileEntity = (TileEntityCreepvine) worldIn.getTileEntity(pos);

		if (worldIn.getTileEntity(pos.down()) instanceof TileEntityCreepvine) {
			TileEntityCreepvine otherTileEntity = (TileEntityCreepvine) worldIn.getTileEntity(pos.down());

			tileEntity.setMaxHeight(otherTileEntity.getMaxHeight());
			tileEntity.setCanGenerateSeeds(otherTileEntity.canGenerateSeeds());
		} else {
			tileEntity.setMaxHeight(AbstractBlockCreepvine.MAX_HEIGHT - worldIn.rand.nextInt(5));
			tileEntity.setCanGenerateSeeds(false);
		}
	}

	@Override
	public boolean checkBottom(World world, BlockPos pos) {
		return super.checkBottom(world, pos) || world.getBlockState(pos.down()).getBlock() instanceof AbstractBlockCreepvine;
	}

}
