package meldexun.better_diving.item;

import meldexun.better_diving.structure.BasicCompartment;
import meldexun.better_diving.structure.SeabaseModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemHabitatBuilder extends ItemTool {

	public SeabaseModule module = new BasicCompartment();

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		Rotation rot = Rotation.NONE;
		if (playerIn.getHorizontalFacing() == EnumFacing.WEST) {
			rot = Rotation.CLOCKWISE_90;
		} else if (playerIn.getHorizontalFacing() == EnumFacing.NORTH) {
			rot = Rotation.CLOCKWISE_180;
		} else if (playerIn.getHorizontalFacing() == EnumFacing.EAST) {
			rot = Rotation.COUNTERCLOCKWISE_90;
		}

		Vec3d start = new Vec3d(playerIn.posX, playerIn.posY + (double) playerIn.eyeHeight, playerIn.posZ);
		Vec3d look = playerIn.getLookVec();
		Vec3d end = new Vec3d(start.x + 5.0D * look.x, start.y + 5.0D * look.y, start.z + 5.0D * look.z);
		RayTraceResult result = worldIn.rayTraceBlocks(start, end, false, false, false);
		BlockPos pos = new BlockPos(result != null ? result.hitVec.subtract(look) : end);

		this.module.generate(worldIn, pos, rot);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

}
