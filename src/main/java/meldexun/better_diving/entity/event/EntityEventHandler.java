package meldexun.better_diving.entity.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class EntityEventHandler {

	private EntityEventHandler() {

	}

	@SubscribeEvent
	public static void onLivingAttackEvent(LivingAttackEvent event) {
		if (event.getEntity().getRidingEntity() instanceof EntitySeamoth && event.getSource().getImmediateSource() != null) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onWorldTickEvent(TickEvent.WorldTickEvent event) {
		World world = event.world;
		if (BetterDivingConfig.getInstance().general.itemEntityMovement && event.phase == Phase.END) {
			EntityEventHandler.handleItemEntityMovement(world);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onClientTickEvent(TickEvent.ClientTickEvent event) {
		World world = Minecraft.getMinecraft().world;
		if (world != null && BetterDivingConfig.getInstance().general.itemEntityMovement && event.phase == Phase.END) {
			EntityEventHandler.handleItemEntityMovement(world);
		}
	}

	public static void handleItemEntityMovement(World world) {
		if (world != null) {
			for (Entity entity : world.loadedEntityList) {
				if (entity instanceof EntityItem && entity.isInWater() && !entity.onGround && !entity.hasNoGravity()) {
					entity.motionY += 0.03999999910593033D * (1.0D - BetterDivingConfig.getInstance().general.itemEntityMovementFactor);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
		if (!event.getWorld().isRemote && event.getEntity() instanceof EntityItem) {
			Entity entity = event.getEntity();
			if (event.getWorld().getBlockState(new BlockPos(entity)).getMaterial() == Material.WATER) {
				double d = 1.0D - 0.5D * (1.0D - BetterDivingConfig.getInstance().general.itemEntityMovementFactor);
				entity.motionX *= d;
				entity.motionY *= d;
				entity.motionZ *= d;
			}
		}
	}

}
