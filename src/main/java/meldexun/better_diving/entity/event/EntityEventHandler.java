package meldexun.better_diving.entity.event;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.init.ModSounds;
import meldexun.better_diving.util.BetterDivingConfigClient;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(modid = BetterDiving.MOD_ID)
public class EntityEventHandler {

	@SubscribeEvent
	public static void onLivingAttackEvent(LivingAttackEvent event) {
		if (event.getEntity().getRidingEntity() instanceof EntitySeamoth && event.getSource().getTrueSource() instanceof EntityGuardian) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onEntityMountEvent(EntityMountEvent event) {
		if (!event.getWorldObj().isRemote && event.isDismounting() && event.getEntityMounting() instanceof EntityPlayer && event.getEntityBeingMounted() instanceof EntitySeamoth) {
			Entity e = event.getEntityBeingMounted();
			if (e.isInsideOfMaterial(Material.WATER)) {
				event.getWorldObj().playSound(null, e.posX, e.posY, e.posZ, ModSounds.SEAMOTH_EXIT, SoundCategory.NEUTRAL, 0.6F, 1.0F);
			}
		}
	}

	@SubscribeEvent
	public static void onWorldTickEvent(TickEvent.WorldTickEvent event) {
		if (BetterDivingConfigClient.itemEntityMovement) {
			if (event.phase == Phase.END) {
				World world = event.world;
				handleItemEntityMovement(world);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onClientTickEvent(TickEvent.ClientTickEvent event) {
		if (BetterDivingConfigClient.itemEntityMovement) {
			if (event.phase == Phase.END) {
				World world = Minecraft.getMinecraft().world;
				handleItemEntityMovement(world);
			}
		}
	}

	public static void handleItemEntityMovement(World world) {
		if (world != null) {
			for (Entity entity : world.loadedEntityList) {
				if (entity instanceof EntityItem) {
					if (entity.isInWater() && !entity.onGround && !entity.hasNoGravity()) {
						entity.motionY += 0.03999999910593033D * (1.0D - BetterDivingConfigClient.itemEntityMovementFactor);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
		if (!event.getWorld().isRemote && event.getEntity() instanceof EntityItem) {
			Entity entity = event.getEntity();
			if (event.getWorld().getBlockState(new BlockPos(entity)).getMaterial() == Material.WATER) {
				double d = 1.0D - 0.5D * (1.0D - BetterDivingConfigClient.itemEntityMovementFactor);
				entity.motionX *= d;
				entity.motionY *= d;
				entity.motionZ *= d;
			}
		}
	}

}
