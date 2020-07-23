package meldexun.better_diving.client.gui;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.item.ItemPowerCell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

@SideOnly(Side.CLIENT)
public class GuiSeamothContainer extends GuiContainer {

	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/seamoth_container.png");

	private EntitySeamoth entity;
	private ItemStack stack;

	public GuiSeamothContainer(Container inventorySlotsIn, EntitySeamoth entity) {
		super(inventorySlotsIn);
		this.entity = entity;
		this.stack = null;
	}

	public GuiSeamothContainer(Container inventorySlotsIn, ItemStack stack) {
		super(inventorySlotsIn);
		this.entity = new EntitySeamoth(Minecraft.getMinecraft().world);
		this.stack = stack;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(I18n.format("entity.seamoth.name"), 8, 6, 4210752);
		ItemStack powerCell = this.entity != null ? this.entity.getBattery() : this.stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
		if (powerCell.getItem() instanceof ItemPowerCell) {
			IEnergyStorage ienergy = powerCell.getCapability(CapabilityEnergy.ENERGY, null);
			int percent = (int) (100.0D * ienergy.getEnergyStored() / ienergy.getMaxEnergyStored());
			this.fontRenderer.drawString("Energy: " + percent + "%", 8, 16, 4210752);
		} else {
			this.fontRenderer.drawString("No power cell", 8, 16, 4210752);
		}
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GuiSeamothContainer.TEXTURE);
		this.drawTexturedModalRect((this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
		this.drawEntity(this.entity, (this.width - this.xSize) / 2 + 118, (this.height - this.ySize) / 2 + 60, 16, mouseX, mouseY);
	}

	private void drawEntity(Entity entity, int x, int y, int scale, float mouseX, float mouseY) {
		this.drawEntityOnScreen(x, y, scale, x - mouseX, y - scale * entity.getEyeHeight() - mouseY, entity);
	}

	private void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, Entity entity) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(posX, posY, 50.0F);
		GlStateManager.scale((-scale), scale, scale);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		float f1 = entity.rotationYaw;
		float f2 = entity.rotationPitch;
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(15.0F, 1.0F, 0.0F, 0.0F);
		entity.rotationYaw = 35.0F;
		entity.rotationPitch = 0.0F;
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		entity.rotationYaw = f1;
		entity.rotationPitch = f2;
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
