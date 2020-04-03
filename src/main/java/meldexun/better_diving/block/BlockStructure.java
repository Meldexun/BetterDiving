package meldexun.better_diving.block;

import meldexun.better_diving.tileentity.TileEntityBuilding;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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
		return new TileEntityBuilding();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		this.breakStructure(worldIn, pos);

		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	public void breakStructure(World worldIn, BlockPos pos) {
		if (worldIn.getTileEntity(pos) instanceof TileEntityBuilding) {
			TileEntityBuilding tileEntityBuilding = (TileEntityBuilding) worldIn.getTileEntity(pos);

			if (tileEntityBuilding.isBase) {
				for (int x = -5; x <= 5; x++) {
					for (int y = -5; y <= 5; y++) {
						for (int z = -5; z <= 5; z++) {
							BlockPos pos1 = pos.add(x, y, z);
							TileEntity tileEntity = worldIn.getTileEntity(pos1);

							if (tileEntity instanceof TileEntityBuilding) {
								BlockPos basePos = ((TileEntityBuilding) tileEntity).basePos;
								if (basePos.getX() == pos.getX() && basePos.getY() == pos.getY() && basePos.getZ() == pos.getZ()) {
									worldIn.setBlockState(pos1, Blocks.AIR.getDefaultState(), 3);
								}
							}
						}
					}
				}
			} else if (tileEntityBuilding.hasBasePos()) {
				this.breakStructure(worldIn, tileEntityBuilding.basePos);
			}
		}
	}

}
