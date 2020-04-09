package meldexun.better_diving.client.gui;

import org.lwjgl.opengl.GL11;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHabitatBuilderTab extends GuiButton {

	protected static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/gui_tab_normal.png");
	protected static final ResourceLocation TEXTURE_SELECTED = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/gui_tab_selected.png");
	protected static final ResourceLocation TEXTURE_ICONS = new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/gui_tab_icons.png");

	private boolean selected = false;
	protected int textureId = 0;

	public GuiHabitatBuilderTab(int buttonId, int x, int y, int textureId) {
		super(buttonId, x, y, 36, 14, "");
		this.textureId = textureId;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			ScaledResolution scaled = new ScaledResolution(mc);
			int i = MathHelper.clamp(scaled.getScaleFactor(), 1, 4);
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			this.mouseDragged(mc, mouseX, mouseY);

			GL11.glPushMatrix();
			GL11.glDepthMask(false);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);

			if (this.selected) {
				mc.getTextureManager().bindTexture(new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/habitat_builder/habitat_builder_tab_selected_marker_" + i + ".png"));
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
				GuiHelper.drawTexture(this.x + 9.5D, this.y + 13.0D, 0.0D, 0.0D, 8.0D, 5.0D, 1.0D, 1.0D);
				mc.getTextureManager().bindTexture(new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/habitat_builder/habitat_builder_tab_selected_" + i + ".png"));
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			} else {
				mc.getTextureManager().bindTexture(new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/habitat_builder/habitat_builder_tab_normal_" + i + ".png"));
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			}
			GuiHelper.drawTexture(this.x - 1.625D, this.y, 0.0D, 0.0D, 36.0D, 14.0D, 1.0D, 1.0D);

			mc.getTextureManager().bindTexture(new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/tab_icon_" + this.textureId + ".png"));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GuiHelper.drawTexture(this.x + 8.0D, this.y, 0.0D, 0.0D, 16.0D, 13.0D, 1.0D, 1.0D);

			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glDepthMask(true);
			GL11.glPopMatrix();
		}
	}

	@Override
	public void playPressSound(SoundHandler soundHandlerIn) {
		// no sound needed
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return this.selected;
	}

}
