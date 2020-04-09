package meldexun.better_diving.item;

import meldexun.better_diving.block.AbstractBlockCreepvine;
import meldexun.better_diving.init.ModBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCreepvineSeedCluster extends ItemTooltip {

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		BlockPos position = pos.offset(facing);
		if (!(worldIn.getBlockState(position.down()).getBlock() instanceof AbstractBlockCreepvine) && ModBlocks.CREEPVINE_TOP.canPlaceBlockAt(worldIn, position)) {
			ModBlocks.CREEPVINE_TOP.setCreepvine(worldIn, position, 3, AbstractBlockCreepvine.MAX_HEIGHT - 4 + worldIn.rand.nextInt(5), true);
			if (!player.isCreative()) {
				player.getHeldItem(hand).shrink(1);
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}

}
