package meldexun.better_diving.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.diving.CapabilityDivingAttributesProvider;
import meldexun.better_diving.capability.diving.ICapabilityDivingAttributes;
import meldexun.better_diving.init.ModItems;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPlayerCustom extends RenderPlayer {

	public RenderPlayerCustom(RenderManager renderManager, boolean useSmallArms) {
		super(renderManager, useSmallArms);
		this.mainModel = new ModelPlayer(0.0F, useSmallArms) {
			@Override
			public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
				super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
				EntityPlayer player = (EntityPlayer) entityIn;
				if (player.getHeldItemMainhand().getItem() == ModItems.SEAGLIDE) {
					this.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-90.0D);
					this.bipedLeftArm.rotateAngleY = 0.0F;
					this.bipedLeftArm.rotateAngleZ = 0.0F;
					this.bipedRightArm.rotateAngleX = (float) Math.toRadians(-90.0D);
					this.bipedRightArm.rotateAngleY = 0.0F;
					this.bipedRightArm.rotateAngleZ = 0.0F;
				}
			}
		};
	}

	@Override
	public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
		ICapabilityDivingAttributes idiving = entity.getCapability(CapabilityDivingAttributesProvider.DIVING_ATTRIBUTES, null);
		double divingTick = MathHelper.clampedLerp(idiving.getPrevDivingTick(), idiving.getDivingTick(), partialTicks);
		double divingTickHorizontal = MathHelper.clampedLerp(idiving.getPrevDivingTickHorizontal(), idiving.getDivingTickHorizontal(), partialTicks);
		double divingTickVertical = MathHelper.clampedLerp(idiving.getPrevDivingTickVertical(), idiving.getDivingTickVertical(), partialTicks);

		entity.setSprinting(false);
		entity.setSneaking(false);

		GL11.glPushMatrix();
		double sinPitch = Math.sin(Math.toRadians(entity.rotationPitch));
		double cosPitch = Math.cos(Math.toRadians(entity.rotationPitch));
		double sinYaw = Math.sin(Math.toRadians(entity.rotationYaw));
		double cosYaw = Math.cos(Math.toRadians(entity.rotationYaw));
		float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
		if (divingTickVertical > 0.0D) {
			pitch += ((pitch - 90.0F) * 0.5F - pitch) * (float) divingTickVertical;
		} else if (divingTickVertical < 0.0D) {
			pitch += ((pitch + 90.0F) * 0.5F - pitch) * (float) -divingTickVertical;
		}
		pitch += idiving.isDiving() ? 90.0F : 60.0F;
		float yaw = (float) divingTickHorizontal * 45.0F;

		// Start RenderPlayer
		if (!entity.isUser() || this.renderManager.renderViewEntity == entity) {
			this.setModelVisibilities(entity);
			GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
			// Start RenderLivingBase
			GlStateManager.pushMatrix();
			GlStateManager.disableCull();
			this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
			boolean shouldSit = entity.isRiding() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit());
			this.mainModel.isRiding = shouldSit;
			this.mainModel.isChild = entity.isChild();

			try {
				float f14 = 75.0F + (float) divingTick * (15.0F - 75.0F);
				float f = this.interpolateRotation(MathHelper.clamp(entity.prevRenderYawOffset, entity.prevRotationYaw - f14, entity.prevRotationYaw + f14), MathHelper.clamp(entity.renderYawOffset, entity.rotationYaw - f14, entity.rotationYaw + f14), partialTicks);
				float f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
				float f2 = f1 - f;

				if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase) {
					EntityLivingBase entitylivingbase = (EntityLivingBase) entity.getRidingEntity();
					f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
					f2 = f1 - f;
					float f3 = MathHelper.wrapDegrees(f2);

					if (f3 < -85.0F) {
						f3 = -85.0F;
					}

					if (f3 >= 85.0F) {
						f3 = 85.0F;
					}

					f = f1 - f3;

					if (f3 * f3 > 2500.0F) {
						f += f3 * 0.2F;
					}

					f2 = f1 - f;
				}

				float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
				f7 = f7 + (float) divingTick * (-60.0F - f7);
				this.renderLivingAt(entity, x, y, z);
				GL11.glTranslated(0.0D, divingTick * 0.3D, 0.0D);
				GL11.glRotated(divingTick * pitch, cosYaw, 0.0D, sinYaw);
				GL11.glRotated(divingTick * yaw, sinYaw, 0.0D, -cosYaw);
				GL11.glTranslated(0.0D, divingTick * -0.9D, 0.0D);
				float f8 = this.handleRotationFloat(entity, partialTicks);
				this.applyRotations(entity, f8, f, partialTicks);
				float f4 = this.prepareScale(entity, partialTicks);
				float f5 = 0.0F;
				float f6 = 0.0F;

				if (!entity.isRiding()) {
					f5 = entity.limbSwingAmount;
					f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

					if (entity.isChild()) {
						f6 *= 3.0F;
					}

					if (f5 > 1.0F) {
						f5 = 1.0F;
					}
					f2 = f1 - f; // Forge: Fix MC-1207
				}

				GlStateManager.enableAlpha();
				this.mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
				this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, f4, entity);

				if (this.renderOutlines) {
					boolean flag1 = this.setScoreTeamColor(entity);
					GlStateManager.enableColorMaterial();
					GlStateManager.enableOutlineMode(this.getTeamColor(entity));

					if (!this.renderMarker) {
						this.renderModel(entity, f6, f5, f8, f2, f7, f4);
					}

					if (!entity.isSpectator()) {
						this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
					}

					GlStateManager.disableOutlineMode();
					GlStateManager.disableColorMaterial();

					if (flag1) {
						this.unsetScoreTeamColor();
					}
				} else {
					boolean flag = this.setDoRenderBrightness(entity, partialTicks);
					this.renderModel(entity, f6, f5, f8, f2, f7, f4);

					if (flag) {
						this.unsetBrightness();
					}

					GlStateManager.depthMask(true);

					if (!entity.isSpectator()) {
						this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
					}
				}

				GlStateManager.disableRescaleNormal();
			} catch (Exception exception) {
				BetterDiving.logger.error("Couldn't render entity", exception);
			}

			GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
			GlStateManager.enableTexture2D();
			GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
			GlStateManager.enableCull();
			GlStateManager.popMatrix();
			// Start Render
			if (!this.renderOutlines) {
				this.renderName(entity, x, y, z);
			}
			// End Render
			// End RenderLivingBase
			GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
		}
		// End RenderPlayer
		GL11.glPopMatrix();
	}

	private void setModelVisibilities(AbstractClientPlayer clientPlayer) {
		// Start RenderPlayer
		ModelPlayer modelplayer = this.getMainModel();

		if (clientPlayer.isSpectator()) {
			modelplayer.setVisible(false);
			modelplayer.bipedHead.showModel = true;
			modelplayer.bipedHeadwear.showModel = true;
		} else {
			ItemStack itemstack = clientPlayer.getHeldItemMainhand();
			ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
			modelplayer.setVisible(true);
			modelplayer.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
			modelplayer.bipedBodyWear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.JACKET);
			modelplayer.bipedLeftLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
			modelplayer.bipedRightLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
			modelplayer.bipedLeftArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
			modelplayer.bipedRightArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
			modelplayer.isSneak = false;
			ModelBiped.ArmPose armpose = ModelBiped.ArmPose.EMPTY;
			ModelBiped.ArmPose armpose1 = ModelBiped.ArmPose.EMPTY;

			if (!itemstack.isEmpty()) {
				armpose = ModelBiped.ArmPose.ITEM;

				if (clientPlayer.getItemInUseCount() > 0) {
					EnumAction enumaction = itemstack.getItemUseAction();

					if (enumaction == EnumAction.BLOCK) {
						armpose = ModelBiped.ArmPose.BLOCK;
					} else if (enumaction == EnumAction.BOW) {
						armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
					}
				}
			}

			if (!itemstack1.isEmpty()) {
				armpose1 = ModelBiped.ArmPose.ITEM;

				if (clientPlayer.getItemInUseCount() > 0) {
					EnumAction enumaction1 = itemstack1.getItemUseAction();

					if (enumaction1 == EnumAction.BLOCK) {
						armpose1 = ModelBiped.ArmPose.BLOCK;
					}
					// FORGE: fix MC-88356 allow offhand to use bow and arrow animation
					else if (enumaction1 == EnumAction.BOW) {
						armpose1 = ModelBiped.ArmPose.BOW_AND_ARROW;
					}
				}
			}

			if (clientPlayer.getPrimaryHand() == EnumHandSide.RIGHT) {
				modelplayer.rightArmPose = armpose;
				modelplayer.leftArmPose = armpose1;
			} else {
				modelplayer.rightArmPose = armpose1;
				modelplayer.leftArmPose = armpose;
			}
		}
		// End RenderPlayer
	}

}
