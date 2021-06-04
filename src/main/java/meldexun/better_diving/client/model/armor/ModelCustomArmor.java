package meldexun.better_diving.client.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelCustomArmor extends BipedModel<LivingEntity> {

	public ModelCustomArmor(int textureWidth, int textureHeight) {
		super(0.0F);
		this.texWidth = textureWidth;
		this.texHeight = textureHeight;
	}

	@Override
	public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity instanceof ArmorStandEntity) {
			ArmorStandEntity armorStand = (ArmorStandEntity) entity;
			this.head.xRot = 0.017453292F * armorStand.getHeadPose().getX();
			this.head.yRot = 0.017453292F * armorStand.getHeadPose().getY();
			this.head.zRot = 0.017453292F * armorStand.getHeadPose().getZ();
			this.head.setPos(0.0F, 1.0F, 0.0F);
			this.body.xRot = 0.017453292F * armorStand.getBodyPose().getX();
			this.body.yRot = 0.017453292F * armorStand.getBodyPose().getY();
			this.body.zRot = 0.017453292F * armorStand.getBodyPose().getZ();
			this.leftArm.xRot = 0.017453292F * armorStand.getLeftArmPose().getX();
			this.leftArm.yRot = 0.017453292F * armorStand.getLeftArmPose().getY();
			this.leftArm.zRot = 0.017453292F * armorStand.getLeftArmPose().getZ();
			this.rightArm.xRot = 0.017453292F * armorStand.getRightArmPose().getX();
			this.rightArm.yRot = 0.017453292F * armorStand.getRightArmPose().getY();
			this.rightArm.zRot = 0.017453292F * armorStand.getRightArmPose().getZ();
			this.leftLeg.xRot = 0.017453292F * armorStand.getLeftLegPose().getX();
			this.leftLeg.yRot = 0.017453292F * armorStand.getLeftLegPose().getY();
			this.leftLeg.zRot = 0.017453292F * armorStand.getLeftLegPose().getZ();
			this.leftLeg.setPos(1.9F, 11.0F, 0.0F);
			this.rightLeg.xRot = 0.017453292F * armorStand.getRightLegPose().getX();
			this.rightLeg.yRot = 0.017453292F * armorStand.getRightLegPose().getY();
			this.rightLeg.zRot = 0.017453292F * armorStand.getRightLegPose().getZ();
			this.rightLeg.setPos(-1.9F, 11.0F, 0.0F);
			this.hat.copyFrom(this.head);
		} else {
			super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}
	}

}
