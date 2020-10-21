package meldexun.better_diving.item;

import meldexun.better_diving.block.AbstractBlockCreepvine;
import meldexun.better_diving.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCreepvineSeedCluster extends ItemTooltip {

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if (!block.isReplaceable(worldIn, pos)) {
			pos = pos.offset(facing);
		}

		ItemStack itemstack = player.getHeldItem(hand);

		if (!player.canPlayerEdit(pos, facing, itemstack)) {
			return EnumActionResult.FAIL;
		}
		if (worldIn.getBlockState(pos.down()).getBlock() instanceof AbstractBlockCreepvine) {
			return EnumActionResult.FAIL;
		}
		if (!worldIn.mayPlace(ModBlocks.CREEPVINE_TOP, pos, false, facing, player)) {
			return EnumActionResult.FAIL;
		}

		ModBlocks.CREEPVINE_TOP.setCreepvine(worldIn, pos, 3, AbstractBlockCreepvine.MAX_HEIGHT - 4 + worldIn.rand.nextInt(5), true);
		IBlockState iblockstate1 = worldIn.getBlockState(pos);
		SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
		worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

		if (!player.isCreative()) {
			player.getHeldItem(hand).shrink(1);
		}

		return EnumActionResult.SUCCESS;
	}

}
