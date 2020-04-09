package meldexun.better_diving.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.client.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHabitatBuilder extends GuiScreen {

	protected final List<GuiHabitatBuilderTab> tabList = new ArrayList<>();
	private int selectedTab = 0;

	@Override
	public void initGui() {
		super.initGui();
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaled = new ScaledResolution(mc);
		int x = (int) ((double) scaled.getScaledWidth() * 0.5D);
		int y = (int) ((double) scaled.getScaledHeight() * 0.5D);

		this.addTab(new GuiHabitatBuilderTab(0, x - 80, y - 80, 0));
		this.addTab(new GuiHabitatBuilderTab(1, x - 48, y - 80, 1));
		this.addTab(new GuiHabitatBuilderTab(2, x - 16, y - 80, 2));
		this.addTab(new GuiHabitatBuilderTab(3, x + 16, y - 80, 3));
		this.addTab(new GuiHabitatBuilderTab(4, x + 48, y - 80, 4));
		this.tabList.get(this.selectedTab).setSelected(true);
	}

	public void addTab(GuiHabitatBuilderTab tab) {
		this.addButton(tab);
		this.tabList.add(tab);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaled = new ScaledResolution(mc);
		int x = (int) ((double) scaled.getScaledWidth() * 0.5D);
		int y = (int) ((double) scaled.getScaledHeight() * 0.5D);

		GL11.glPushMatrix();
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);

		int i = MathHelper.clamp(scaled.getScaleFactor(), 1, 4);
		mc.getTextureManager().bindTexture(new ResourceLocation(BetterDiving.MOD_ID, "textures/gui/habitat_builder/habitat_builder_background_" + i + ".png"));
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GuiHelper.drawTexture(x - 126.0D, y - 96.0D, 0.0D, 0.0D, 252.0D, 192.0D, 1.0D, 1.0D);

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (this.tabList.contains(button)) {
			for (int i = 0; i < this.tabList.size(); i++) {
				GuiHabitatBuilderTab tab = this.tabList.get(i);
				if (button == tab) {
					tab.setSelected(true);
					this.selectedTab = i;
				} else {
					tab.setSelected(false);
				}
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void onResize(Minecraft mcIn, int w, int h) {
		this.tabList.clear();
		super.onResize(mcIn, w, h);
	}

}
