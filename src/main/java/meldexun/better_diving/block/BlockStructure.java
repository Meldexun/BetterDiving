package meldexun.better_diving.block;

import java.util.Random;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.structure.SeabaseStructure;
import meldexun.better_diving.structure.modules.SeabaseModule;
import meldexun.better_diving.tileentity.TileEntityStructure;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStructure extends Block {

	public BlockStructure() {
		super(Material.IRON);
		this.blockResistance = Float.MAX_VALUE;
		this.blockHardness = -1.0F;
		this.lightValue = 3;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityStructure();
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		if (world instanceof World && !((World) world).isRemote && world.getBlockState(neighbor).getBlock() instanceof BlockStructurePart) {
			TileEntity tileEntity = world.getTileEntity(pos);
			if (tileEntity instanceof TileEntityStructure) {
				TileEntityStructure tileEntityStructure = (TileEntityStructure) tileEntity;
				tileEntityStructure.getModule().addStructurePart(neighbor);
			}
		}
	}

	public void placeBlock(World world, BlockPos pos, int flags, SeabaseModule module, boolean isDoor) {
		if (!world.isRemote) {
			world.setBlockState(pos, this.getDefaultState(), flags);
			TileEntity tileEntity = world.getTileEntity(pos);
			if (tileEntity instanceof TileEntityStructure) {
				TileEntityStructure tileEntityStructure = (TileEntityStructure) tileEntity;
				tileEntityStructure.setModule(module);
				tileEntityStructure.setDoor(isDoor);
				if (!isDoor) {
					this.checkForNearbyModules(world, pos, module);
				}
			}
		}
	}

	private void checkForNearbyModules(World world, BlockPos pos, SeabaseModule module) {
		for (int i = 0; i < 6; i++) {
			TileEntity tileEntity = world.getTileEntity(pos.offset(EnumFacing.values()[i]));
			if (tileEntity instanceof TileEntityStructure) {
				TileEntityStructure tileEntityStructure = (TileEntityStructure) tileEntity;
				if (!tileEntityStructure.isDoor()) {
					SeabaseModule otherModule = tileEntityStructure.getModule();
					if (otherModule != module) {
						BetterDiving.logger.info(module.getStructure() + " &&& " + otherModule.getStructure());
					}
					if (otherModule != module && otherModule.getStructure() != module.getStructure()) {
						SeabaseStructure.merge(otherModule.getStructure(), module.getStructure());
					}
				}
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			TileEntityStructure tileEntity = (TileEntityStructure) worldIn.getTileEntity(pos);
			if (playerIn.isSneaking()) {
				tileEntity.getModule().destroy();
			} else {
				BetterDiving.logger.info(tileEntity.getModule());
			}
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

}
