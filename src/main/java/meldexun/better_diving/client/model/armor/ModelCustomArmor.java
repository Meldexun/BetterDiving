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
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
	}

	@Override
	public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity instanceof ArmorStandEntity) {
			ArmorStandEntity armorStand = (ArmorStandEntity) entity;
			this.bipedHead.rotateAngleX = 0.017453292F * armorStand.getHeadRotation().getX();
			this.bipedHead.rotateAngleY = 0.017453292F * armorStand.getHeadRotation().getY();
			this.bipedHead.rotateAngleZ = 0.017453292F * armorStand.getHeadRotation().getZ();
			this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
			this.bipedBody.rotateAngleX = 0.017453292F * armorStand.getBodyRotation().getX();
			this.bipedBody.rotateAngleY = 0.017453292F * armorStand.getBodyRotation().getY();
			this.bipedBody.rotateAngleZ = 0.017453292F * armorStand.getBodyRotation().getZ();
			this.bipedLeftArm.rotateAngleX = 0.017453292F * armorStand.getLeftArmRotation().getX();
			this.bipedLeftArm.rotateAngleY = 0.017453292F * armorStand.getLeftArmRotation().getY();
			this.bipedLeftArm.rotateAngleZ = 0.017453292F * armorStand.getLeftArmRotation().getZ();
			this.bipedRightArm.rotateAngleX = 0.017453292F * armorStand.getRightArmRotation().getX();
			this.bipedRightArm.rotateAngleY = 0.017453292F * armorStand.getRightArmRotation().getY();
			this.bipedRightArm.rotateAngleZ = 0.017453292F * armorStand.getRightArmRotation().getZ();
			this.bipedLeftLeg.rotateAngleX = 0.017453292F * armorStand.getLeftLegRotation().getX();
			this.bipedLeftLeg.rotateAngleY = 0.017453292F * armorStand.getLeftLegRotation().getY();
			this.bipedLeftLeg.rotateAngleZ = 0.017453292F * armorStand.getLeftLegRotation().getZ();
			this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
			this.bipedRightLeg.rotateAngleX = 0.017453292F * armorStand.getRightLegRotation().getX();
			this.bipedRightLeg.rotateAngleY = 0.017453292F * armorStand.getRightLegRotation().getY();
			this.bipedRightLeg.rotateAngleZ = 0.017453292F * armorStand.getRightLegRotation().getZ();
			this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
			this.bipedHeadwear.copyModelAngles(this.bipedHead);
		} else {
			super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		}
	}

}
