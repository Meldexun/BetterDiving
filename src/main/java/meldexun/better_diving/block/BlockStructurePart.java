package meldexun.better_diving.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStructurePart extends Block {

	public BlockStructurePart() {
		super(Material.IRON);
		this.blockResistance = Float.MAX_VALUE;
		this.blockHardness = -1.0F;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}

}
