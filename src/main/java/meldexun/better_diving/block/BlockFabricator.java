package meldexun.better_diving.block;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.network.GuiHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFabricator extends BlockEnergyUser {

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.openGui(BetterDiving.instance, GuiHandler.GUI_FABRICATOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

}
