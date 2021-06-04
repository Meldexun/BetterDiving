package meldexun.better_diving.client.event;

import static org.lwjgl.opengl.GL20C.glUniformMatrix4fv;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.util.List;

import org.lwjgl.opengl.GL21C;
import org.lwjgl.opengl.GL33C;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.capability.oxygen.item.CapabilityOxygenItemProvider;
import meldexun.better_diving.client.ResourceSupplier;
import meldexun.better_diving.client.ShaderProgram;
import meldexun.better_diving.client.gui.GuiOxygen;
import meldexun.better_diving.client.gui.GuiSeamoth;
import meldexun.better_diving.client.gui.HotbarSeamoth;
import meldexun.better_diving.config.BetterDivingConfig;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.network.packet.client.CPacketOpenSeamothInventory;
import meldexun.better_diving.oxygenprovider.DivingGearManager;
import meldexun.better_diving.oxygenprovider.DivingMaskProviderItem;
import meldexun.better_diving.oxygenprovider.MiningspeedProviderItem;
import meldexun.better_diving.oxygenprovider.SwimspeedProviderItem;
import meldexun.better_diving.util.OxygenPlayerHelper;
import meldexun.better_diving.util.reflection.ReflectionField;
import meldexun.better_diving.util.reflection.ReflectionMethod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BetterDivingRenderUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher.ChunkRender;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.event.InputEvent.MouseScrollEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterDiving.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {

	private static final DecimalFormat FORMAT = new DecimalFormat("#.##");

	@SubscribeEvent
	public static void onRenderGameOverlayEventPre(RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getInstance();
		if (event.getType() == ElementType.AIR && BetterDivingConfig.CLIENT_CONFIG.oxygenGuiEnabled.get()) {
			event.setCanceled(true);

			if (mc.player.areEyesInFluid(FluidTags.WATER) || OxygenPlayerHelper.getOxygenRespectEquipment(mc.player) < OxygenPlayerHelper.getOxygenCapacityRespectEquipment(mc.player)) {
				GuiOxygen.render(event.getMatrixStack());
			}
		}
		if (event.getType() == ElementType.ALL && BetterDivingConfig.CLIENT_CONFIG.seamothGuiEnabled.get() && mc.player.getRidingEntity() instanceof EntitySeamoth) {
			GuiSeamoth.render(event.getMatrixStack());
		}
		if (event.getType() == ElementType.HOTBAR && mc.player.getRidingEntity() instanceof EntitySeamoth) {
			event.setCanceled(true);
			HotbarSeamoth.render(event.getMatrixStack());
		}
	}

	@SubscribeEvent
	public static void onFOVModifierEvent(EntityViewRenderEvent.FOVModifier event) {
		if (!event.getInfo().getFluidState().isEmpty()) {
			event.setFOV(event.getFOV() * 7.0D / 6.0D);
		}
	}

	@SubscribeEvent
	public static void onItemTooltipEvent(ItemTooltipEvent event) {
		List<ITextComponent> tooltip = event.getToolTip();
		ItemStack stack = event.getItemStack();

		DivingMaskProviderItem divingMaskProvider = DivingGearManager.getDivingMaskProviderItem(stack);
		if (divingMaskProvider != null) {
			tooltip.add(1, new StringTextComponent(TextFormatting.GRAY + String.format("Max Diving Depth %dm", divingMaskProvider.maxDivingDepth)));
		}

		stack.getCapability(CapabilityOxygenItemProvider.OXYGEN).ifPresent(c -> {
			tooltip.add(1, new StringTextComponent(TextFormatting.GRAY + Integer.toString((int) Math.round(c.getOxygen() / 20.0D / 3.0D) * 3) + "s of Oxygen"));
		});

		MiningspeedProviderItem miningSpeedProvider = DivingGearManager.getMiningspeedProviderItem(stack);
		if (miningSpeedProvider != null) {
			tooltip.add(1, new StringTextComponent(TextFormatting.GRAY + (miningSpeedProvider.miningspeed >= 0.0D ? "+" : "") + FORMAT.format(miningSpeedProvider.miningspeed * 100.0D) + "% Mining Speed"));
		}

		SwimspeedProviderItem swimspeedProvider = DivingGearManager.getSwimspeedProviderItem(stack);
		if (swimspeedProvider != null) {
			tooltip.add(1, new StringTextComponent(TextFormatting.GRAY + (swimspeedProvider.swimspeed >= 0.0D ? "+" : "") + FORMAT.format(swimspeedProvider.swimspeed * 100.0D) + "% Swim Speed"));
		}
	}

	private static final ReflectionMethod<?> KEY_BINDING_UNPRESS_KEY = new ReflectionMethod<>(KeyBinding.class, "?", "unpressKey");

	@SubscribeEvent
	public static void onKeyInputEvent(KeyInputEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player != null && mc.player.getRidingEntity() instanceof EntitySeamoth && event.getAction() == 1) {
			EntitySeamoth seamoth = (EntitySeamoth) mc.player.getRidingEntity();
			if (event.getKey() == mc.gameSettings.keyBindInventory.getKey().getKeyCode() && event.getAction() == 1 && mc.currentScreen == null) {
				BetterDiving.NETWORK.sendToServer(new CPacketOpenSeamothInventory());
			}
			for (int i = 0; i < mc.gameSettings.keyBindsHotbar.length; i++) {
				if (event.getKey() == mc.gameSettings.keyBindsHotbar[i].getKey().getKeyCode()) {
					KEY_BINDING_UNPRESS_KEY.invoke(mc.gameSettings.keyBindsHotbar[i]);
					if (i < 4) {
						ItemStack stack = seamoth.getModule(i);
						if (!stack.isEmpty()) {
							if (seamoth.currentModule != i) {
								seamoth.currentModule = i;
								mc.player.sendStatusMessage(stack.getDisplayName(), true);
							} else {
								seamoth.currentModule = -1;
							}
						}
						break;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void on(MouseScrollEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player != null && mc.player.getRidingEntity() instanceof EntitySeamoth) {
			System.out.println(event.getScrollDelta());
		}
	}

	private static final ReflectionMethod<Boolean> CLIPPING_HELPER_IS_BOX_IN_FRUSTUM = new ReflectionMethod<>(ClippingHelper.class, "?", "isBoxInFrustum", double.class, double.class, double.class, double.class, double.class, double.class);
	private static boolean init;
	private static ShaderProgram shader;
	private static ShaderProgram shader2;
	private static int uniform_projectionMatrix;
	private static int uniform_viewMatrix;
	private static int uniform_modelMatrix;
	private static int uniform_offset;
	private static int uniform_size;
	private static int uniform_color;
	private static int uniform_power;
	private static int uniform_mint;
	private static int uniform_dist;
	private static int uniform_diff;
	private static final FloatBuffer buffer = ByteBuffer.allocateDirect(64).order(ByteOrder.nativeOrder()).asFloatBuffer();
	
	@SubscribeEvent
	public static void onRender(RenderWorldLastEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null) {
			return;
		}
		if (!(mc.player.getRidingEntity() instanceof EntitySeamoth)) {
			return;
		}
		
		if (!init) {
			init = true;
			ShaderProgram.Builder shaderBuilder = new ShaderProgram.Builder();
			shaderBuilder.add(GL33C.GL_VERTEX_SHADER, new ResourceSupplier(new ResourceLocation(BetterDiving.MOD_ID, "shaders/vertex_shader.glsl")));
			shaderBuilder.add(GL33C.GL_GEOMETRY_SHADER, new ResourceSupplier(new ResourceLocation(BetterDiving.MOD_ID, "shaders/geometry_shader.glsl")));
			shaderBuilder.add(GL33C.GL_FRAGMENT_SHADER, new ResourceSupplier(new ResourceLocation(BetterDiving.MOD_ID, "shaders/fragment_shader.glsl")));
			shader = shaderBuilder.build();
			uniform_projectionMatrix = GL33C.glGetUniformLocation(shader.shaderProgram, "projectionMatrix");
			uniform_viewMatrix = GL33C.glGetUniformLocation(shader.shaderProgram, "viewMatrix");
			uniform_modelMatrix = GL33C.glGetUniformLocation(shader.shaderProgram, "modelMatrix");
			uniform_offset = GL33C.glGetUniformLocation(shader.shaderProgram, "offset");
			uniform_size = GL33C.glGetUniformLocation(shader.shaderProgram, "size");
			uniform_color = GL33C.glGetUniformLocation(shader.shaderProgram, "color");
			uniform_power = GL33C.glGetUniformLocation(shader.shaderProgram, "power");
			uniform_mint = GL33C.glGetUniformLocation(shader.shaderProgram, "mint");
			uniform_dist = GL33C.glGetUniformLocation(shader.shaderProgram, "dist");
			uniform_diff = GL33C.glGetUniformLocation(shader.shaderProgram, "diff");
		}

		ActiveRenderInfo activeRenderInfo = new ReflectionField<ActiveRenderInfo>(EntityRendererManager.class, "?", "info").get(mc.getRenderManager());
		Vector3d vec = activeRenderInfo.getProjectedView();
		double x = vec.getX();
		double y = vec.getY();
		double z = vec.getZ();
		
		float f = Math.max(mc.world.getGameTime() % 400 - 100, 0);
		f /= 300.0F;
		GL33C.glUseProgram(true ? shader.shaderProgram : shader2.shaderProgram);
		GL33C.glUniform1f(uniform_offset, 1.0F / 256.0F);
		GL33C.glUniform1f(uniform_size, 1.0F / 20.0F);
		GL33C.glUniform1f(uniform_mint, 0.1F);
		//System.out.println(String.format("%.3f %.3f %.3f", f, MathHelper.clamp(f * 2F, 0.0F, 1.0F) * 300, MathHelper.clamp(f * 2 - 1, 0.0F, 1.0F) * 300));
		GL33C.glUniform1f(uniform_dist, MathHelper.clamp(f * 2F, 0.0F, 1.0F) * 200);
		GL33C.glUniform1f(uniform_diff, MathHelper.clamp(f * 2 - 1, 0.0F, 1.0F) * 200);
		event.getProjectionMatrix().write(buffer);
		glUniformMatrix4fv(uniform_projectionMatrix, false, buffer);
		event.getMatrixStack().getLast().getMatrix().write(buffer);
		glUniformMatrix4fv(uniform_viewMatrix, false, buffer);
		mc.textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.color4f(1.0F, 0.0F, 0.0F, 0.5F);
		GlStateManager.disableFog();
		GlStateManager.disableLighting();
		GlStateManager.disableAlphaTest();
		GlStateManager.depthFunc(GL21C.GL_LEQUAL);
		//GlStateManager.disableDepthTest();
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL21C.GL_SRC_ALPHA, GL21C.GL_ONE_MINUS_SRC_ALPHA);
		GL33C.glEnable(GL33C.GL_POLYGON_SMOOTH);
		//GlStateManager.disableTexture();

		MatrixStack matrixStack = event.getMatrixStack();
		RenderType[] blockLayers = { RenderType.getSolid()/*, RenderType.getCutoutMipped(), RenderType.getCutout()*/ };
		for (int i = 0; i < blockLayers.length; i++) {
			RenderType blockLayer = blockLayers[i];
			GL33C.glUniform4f(uniform_color, 1.0F, 0.0F, 0.0F, blockLayer == RenderType.getSolid() ? 1.0F : 0.5F);
			GL33C.glUniform1f(uniform_power, blockLayer == RenderType.getSolid() ? 0.75F : 1.0F);
			for (ChunkRender chunkRender : BetterDivingRenderUtil.getRenderChunks()) {
				boolean flag = false;
				//for (int i = 0; i < blockLayers.length; i++) {
					//RenderType blockLayer = blockLayers[i];
					if (chunkRender.getCompiledChunk().isLayerEmpty(blockLayer)) {
						continue;
					}
					if (!flag) {
						flag = true;
						BlockPos pos = chunkRender.getPosition();
						
						Matrix4f.makeTranslate((float) (pos.getX() - x), (float) (pos.getY() - y), (float) (pos.getZ() - z)).write(buffer);
						glUniformMatrix4fv(uniform_modelMatrix, false, buffer);
					}
					VertexBuffer vertexBuffer = chunkRender.getVertexBuffer(blockLayer);
					vertexBuffer.bindBuffer();
					//DefaultVertexFormats.BLOCK.setupBufferState(0L);
					GL21C.glEnableVertexAttribArray(0);
					GL21C.glEnableVertexAttribArray(1);
					GL21C.glEnableVertexAttribArray(2);
					GL21C.glEnableVertexAttribArray(3);
					GL21C.glEnableVertexAttribArray(4);
					GL21C.glVertexAttribPointer(0, 3, GL21C.GL_FLOAT, false, 32, 0);
					GL21C.glVertexAttribPointer(1, 1, GL21C.GL_INT, false, 32, 12);
					GL21C.glVertexAttribPointer(2, 2, GL21C.GL_FLOAT, false, 32, 16);
					GL21C.glVertexAttribPointer(3, 2, GL21C.GL_SHORT, false, 32, 24);
					GL21C.glVertexAttribPointer(4, 3, GL21C.GL_BYTE, true, 32, 28);
					vertexBuffer.draw(matrixStack.getLast().getMatrix(), GL33C.GL_LINES_ADJACENCY);
				//}
			}
		}
		VertexBuffer.unbindBuffer();
		RenderSystem.clearCurrentColor();
		//DefaultVertexFormats.BLOCK.clearBufferState();
		GL21C.glDisableVertexAttribArray(0);
		GL21C.glDisableVertexAttribArray(1);
		GL21C.glDisableVertexAttribArray(2);
		GL21C.glDisableVertexAttribArray(3);
		GL21C.glDisableVertexAttribArray(4);
		GL33C.glUseProgram(0);
		GL33C.glDisable(GL33C.GL_POLYGON_SMOOTH);
		
		/*
		if (!init) {
			vbo = GL21C.glGenBuffers();
			buffer = ByteBuffer.allocateDirect(0xFFFFFFF).order(ByteOrder.nativeOrder()).asFloatBuffer();
			init = true;
		}
		WorldRenderer w = null;
		w.updateCameraAndRender(matrixStackIn, partialTicks, finishTimeNano, drawBlockOutline, activeRenderInfoIn, gameRendererIn, lightmapIn, projectionIn);

		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null) {
			return;
		}
		if (!(mc.player.getRidingEntity() instanceof EntitySeamoth)) {
			return;
		}
		EntitySeamoth seamoth = (EntitySeamoth) mc.player.getRidingEntity();
		int x = MathHelper.floor(seamoth.getPosXWidth(0.5D));
		int y = MathHelper.floor(seamoth.getPosYHeight(0.5D));
		int z = MathHelper.floor(seamoth.getPosZWidth(0.5D));
		Entity entity = mc.renderViewEntity;
		float partialTicks = mc.getRenderPartialTicks();
		float dx = (float) (entity.prevPosX + (entity.getPosX() - entity.prevPosX) * partialTicks);
		float dy = (float) (entity.prevPosY + (entity.getPosY() - entity.prevPosY) * partialTicks + entity.getEyeHeight());
		float dz = (float) (entity.prevPosZ + (entity.getPosZ() - entity.prevPosZ) * partialTicks);

		try {
			boolean update = false;
			if (update) {
			buffer.clear();
			count = 0;

			BlockPos.Mutable pos = new BlockPos.Mutable();
			BlockPos.Mutable pos1 = new BlockPos.Mutable();
			//*
			ClippingHelper frustum = new ClippingHelper(event.getMatrixStack().getLast().getMatrix(), event.getProjectionMatrix());
			frustum.setCameraPosition(dx, dy, dz);
			int r = 8;
			for (int cx = -r; cx <= r; cx++) {
				int chunkX = (x >> 4) + cx;
				for (int cz = -r; cz <= r; cz++) {
					int chunkZ = (z >> 4) + cz;
					Chunk chunk = mc.world.getChunk(chunkX, chunkZ);
					ChunkSection[] sections = chunk.getSections();
					for (int cy = -r; cy <= r; cy++) {
						int chunkY = (y >> 4) + cy;
						if (chunkY < 0 || chunkY >= 16) {
							continue;
						}
						if (!CLIPPING_HELPER_IS_BOX_IN_FRUSTUM.invoke(frustum, chunkX << 4, chunkY << 4, chunkZ << 4, (chunkX << 4) + 16, (chunkY << 4) + 16, (chunkZ << 4) + 16)) {
							continue;
						}
						ChunkSection section = sections[chunkY];
						if (section == null) {
							continue;
						}
						for (int ix = 0; ix < 16; ix++) {
							for (int iy = 0; iy < 16; iy++) {
								for (int iz = 0; iz < 16; iz++) {
									pos.setPos((chunkX << 4) + ix, (chunkY << 4) + iy, (chunkZ << 4) + iz);
									BlockState state = section.getBlockState(ix, iy, iz);
									boolean flag = state.getCollisionShape(mc.world, pos).isEmpty();
									flag = state.getBlock() == Blocks.AIR || state.getBlock() == Blocks.WATER;

									pos1.setPos(pos.getX() + 1, pos.getY(), pos.getZ());
									BlockState state1 = ix < 15 ? section.getBlockState(ix + 1, iy, iz) : mc.world.getBlockState(pos1);
									boolean flag1 = state1.getCollisionShape(mc.world, pos1).isEmpty();
									flag1 = state1.getBlock() == Blocks.AIR || state1.getBlock() == Blocks.WATER;
									if (!flag && !state1.isSolidSide(mc.world, pos1, Direction.WEST)) {
										t(Direction.EAST, pos, dx, dy, dz);
										count += 4;
									} else if (!flag1 && !state.isSolidSide(mc.world, pos, Direction.EAST)) {
										t(Direction.WEST, pos1, dx, dy, dz);
										count += 4;
									}

									pos1.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
									state1 = iy < 15 ? section.getBlockState(ix, iy + 1, iz) : mc.world.getBlockState(pos1);
									flag1 = state1.getCollisionShape(mc.world, pos1).isEmpty();
									flag1 = state1.getBlock() == Blocks.AIR || state1.getBlock() == Blocks.WATER;
									if (!flag && !state1.isSolidSide(mc.world, pos1, Direction.DOWN)) {
										t(Direction.UP, pos, dx, dy, dz);
										count += 4;
									} else if (!flag1 && !state.isSolidSide(mc.world, pos, Direction.UP)) {
										t(Direction.DOWN, pos1, dx, dy, dz);
										count += 4;
									}

									pos1.setPos(pos.getX(), pos.getY(), pos.getZ() + 1);
									state1 = iz < 15 ? section.getBlockState(ix, iy, iz + 1) : mc.world.getBlockState(pos1);
									flag1 = state1.getCollisionShape(mc.world, pos1).isEmpty();
									flag1 = state1.getBlock() == Blocks.AIR || state1.getBlock() == Blocks.WATER;
									if (!flag && !state1.isSolidSide(mc.world, pos1, Direction.NORTH)) {
										t(Direction.SOUTH, pos, dx, dy, dz);
										count += 4;
									} else if (!flag1 && !state.isSolidSide(mc.world, pos, Direction.SOUTH)) {
										t(Direction.NORTH, pos1, dx, dy, dz);
										count += 4;
									}
								}
							}
						}	
					}
				}
			}
			/* /
			int r = 20;
			for (int ix = -r; ix <= r; ix++) {
				for (int iy = -r; iy <= r; iy++) {
					for (int iz = -r; iz <= r; iz++) {
						pos.setPos(x + ix, y + iy, z + iz);
						BlockState state = mc.world.getBlockState(pos);
						VoxelShape shape = state.getCollisionShape(mc.world, pos);
						if (shape.isEmpty()) {
							continue;
						}
						for (Direction dir : Direction.values()) {
							pos1.setAndMove(pos, dir);
							BlockState state1 = mc.world.getBlockState(pos1);
							if (state1.isSolidSide(mc.world, pos1, dir.getOpposite())) {
								continue;
							}
							count += 4;
							t(dir, pos, dx, dy, dz);
						}
					}
				}
			}
			//* /
			}

			if (update) {
				buffer.limit(buffer.position());
				buffer.rewind();
			}
			
			GL21C.glBindBuffer(GL21C.GL_ARRAY_BUFFER, vbo);
			if (update)
				GL21C.glBufferData(GL21C.GL_ARRAY_BUFFER, buffer, GL21C.GL_STREAM_DRAW);

			GlStateManager.color4f(1.0F, 0.0F, 0.0F, 0.5F);
			GlStateManager.disableFog();
			GlStateManager.disableLighting();
			GlStateManager.disableAlphaTest();
			GlStateManager.depthFunc(GL21C.GL_LEQUAL);
			//GlStateManager.disableDepthTest();
			GlStateManager.depthMask(false);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GL21C.GL_SRC_ALPHA, GL21C.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.disableTexture();
			GlStateManager.pushMatrix();
			GlStateManager.rotatef(mc.player.rotationPitch, 1, 0, 0);
			GlStateManager.rotatef(180 + mc.player.rotationYaw, 0, 1, 0);

			GL21C.glEnableVertexAttribArray(0);
			GL21C.glVertexAttribPointer(0, 3, GL21C.GL_FLOAT, false, 0, 0);
			GL21C.glDrawArrays(GL21C.GL_QUADS, 0, count);
			GL21C.glDisableVertexAttribArray(0);

			GlStateManager.popMatrix();
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

			GL21C.glBindBuffer(GL21C.GL_ARRAY_BUFFER, 0);
		} finally {
		}
		*/
	}

	/*
	private static void t(Direction dir, BlockPos pos, float dx, float dy, float dz) {
		float f = 0.02F;
		switch (dir) {
		case DOWN:
			buffer.put(pos.getX() - dx - f);
			buffer.put(pos.getY() - dy - f);
			buffer.put(pos.getZ() - dz - f);
			buffer.put(pos.getX() - dx + f + 1);
			buffer.put(pos.getY() - dy - f);
			buffer.put(pos.getZ() - dz - f);
			buffer.put(pos.getX() - dx + f + 1);
			buffer.put(pos.getY() - dy - f);
			buffer.put(pos.getZ() - dz + f + 1);
			buffer.put(pos.getX() - dx - f);
			buffer.put(pos.getY() - dy - f);
			buffer.put(pos.getZ() - dz + f + 1);
			break;
		case UP:
			buffer.put(pos.getX() - dx - f);
			buffer.put(pos.getY() - dy + f + 1);
			buffer.put(pos.getZ() - dz + f + 1);
			buffer.put(pos.getX() - dx + f + 1);
			buffer.put(pos.getY() - dy + f + 1);
			buffer.put(pos.getZ() - dz + f + 1);
			buffer.put(pos.getX() - dx + f + 1);
			buffer.put(pos.getY() - dy + f + 1);
			buffer.put(pos.getZ() - dz - f);
			buffer.put(pos.getX() - dx - f);
			buffer.put(pos.getY() - dy + f + 1);
			buffer.put(pos.getZ() - dz - f);
			break;
		case NORTH:
			buffer.put(pos.getX() - dx + f + 1);
			buffer.put(pos.getY() - dy - f);
			buffer.put(pos.getZ() - dz - f);
			buffer.put(pos.getX() - dx - f);
			buffer.put(pos.getY() - dy - f);
			buffer.put(pos.getZ() - dz - f);
			buffer.put(pos.getX() - dx - f);
			buffer.put(pos.getY() - dy + f + 1);
			buffer.put(pos.getZ() - dz - f);
			buffer.put(pos.getX() - dx + f + 1);
			buffer.put(pos.getY() - dy + f + 1);
			buffer.put(pos.getZ() - dz - f);
			break;
		case SOUTH:
			buffer.put(pos.getX() - dx - f);
			buffer.put(pos.getY() - dy - f);
			buffer.put(pos.getZ() - dz + f + 1);
			buffer.put(pos.getX() - dx + f + 1);
			buffer.put(pos.getY() - dy - f);
			buffer.put(pos.getZ() - dz + f + 1);
			buffer.put(pos.getX() - dx + f + 1);
			buffer.put(pos.getY() - dy + f + 1);
			buffer.put(pos.getZ() - dz + f + 1);
			buffer.put(pos.getX() - dx - f);
			buffer.put(pos.getY() - dy + f + 1);
			buffer.put(pos.getZ() - dz + f + 1);
			break;
		case WEST:
			buffer.put(pos.getX() - dx - f);
			buffer.put(pos.getY() - dy - f);
			buffer.put(pos.getZ() - dz - f);
			buffer.put(pos.getX() - dx - f);
			buffer.put(pos.getY() - dy - f);
			buffer.put(pos.getZ() - dz + f + 1);
			buffer.put(pos.getX() - dx - f);
			buffer.put(pos.getY() - dy + f + 1);
			buffer.put(pos.getZ() - dz + f + 1);
			buffer.put(pos.getX() - dx - f);
			buffer.put(pos.getY() - dy + f + 1);
			buffer.put(pos.getZ() - dz - f);
			break;
		case EAST:
			buffer.put(pos.getX() - dx + f + 1);
			buffer.put(pos.getY() - dy - f);
			buffer.put(pos.getZ() - dz + f + 1);
			buffer.put(pos.getX() - dx + f + 1);
			buffer.put(pos.getY() - dy - f);
			buffer.put(pos.getZ() - dz - f);
			buffer.put(pos.getX() - dx + f + 1);
			buffer.put(pos.getY() - dy + f + 1);
			buffer.put(pos.getZ() - dz - f);
			buffer.put(pos.getX() - dx + f + 1);
			buffer.put(pos.getY() - dy + f + 1);
			buffer.put(pos.getZ() - dz + f + 1);
			break;
		}
	}
	*/

	@SubscribeEvent
	public static void onFogColorEvent(EntityViewRenderEvent.FogColors event) {
		Minecraft mc = Minecraft.getInstance();
		float partialTicks = mc.getRenderPartialTicks();
		ActiveRenderInfo activeRenderInfo = event.getInfo();
		FluidState fluidState = activeRenderInfo.getFluidState();
		if (!fluidState.isTagged(FluidTags.WATER)) {
			return;
		}
		float minFogBrightness = 0.05F;
		float f = mc.world.getSunBrightness(partialTicks);
		f = MathHelper.clamp((f - 0.2F) / 0.8F, 0.0F, 1.0F);
		f = MathHelper.clamp(f * (1.0F - minFogBrightness) + minFogBrightness, 0.0F, 1.0F);
		event.setRed(event.getRed() * f);
		event.setGreen(event.getGreen() * f);
		event.setBlue(event.getBlue() * f);
	}

	private static float fogBiomeFactor;
	private static float fogBiomeFactorTarget;
	private static long fogBiomeLastUpdate;

	@SubscribeEvent
	public static void onFogDensityEvent(EntityViewRenderEvent.FogDensity event) {
		Minecraft mc = Minecraft.getInstance();
		float partialTicks = mc.getRenderPartialTicks();
		ActiveRenderInfo activeRenderInfo = event.getInfo();
		FluidState fluidstate = activeRenderInfo.getFluidState();
		if (!fluidstate.isTagged(FluidTags.WATER)) {
			fogBiomeLastUpdate = -1L;
			return;
		}
		Entity entity = activeRenderInfo.getRenderViewEntity();
		float fogDensityBase = 0.025F;
		float fogDensityNightBonus = 0.01F;
		float f = fogDensityBase + 0.01F;
		if (entity instanceof ClientPlayerEntity) {
			ClientPlayerEntity clientplayerentity = (ClientPlayerEntity) entity;
			f -= clientplayerentity.getWaterBrightness() * 0.01F;
			
			Biome biome = clientplayerentity.world.getBiome(clientplayerentity.getPosition());
			long time = Util.milliTime();
			float f1 = biome.getCategory() == Biome.Category.SWAMP ? 0.005F : 0.0F;
			if (fogBiomeLastUpdate < 0L) {
				fogBiomeFactor = f1;
				fogBiomeFactorTarget = f1;
				fogBiomeLastUpdate = time;
			}

			float f2 = MathHelper.clamp((float) (time - fogBiomeLastUpdate) / 3000.0F, 0.0F, 1.0F);
			float f3 = MathHelper.lerp(f2, fogBiomeFactor, fogBiomeFactorTarget);
			
			if (f1 != fogBiomeFactorTarget) {
				fogBiomeFactor = f3;
				fogBiomeFactorTarget = f1;
				fogBiomeLastUpdate = time;
			}
			f += f3;
		}
		float f1 = mc.world.getSunBrightness(partialTicks);
		f1 = MathHelper.clamp((f1 - 0.2F) / 0.8F, 0.0F, 1.0F);
		f += (1.0F - f1) * fogDensityNightBonus;
		
		event.setCanceled(true);
		event.setDensity(f);
	}

	@SubscribeEvent
	public static void t(WorldEvent.Load e) {
		if (e.getWorld().isRemote()) {
			((ClientWorld) e.getWorld()).getDimensionRenderInfo().setSkyRenderHandler(new ISkyRenderHandler() {
				@SuppressWarnings("deprecation")
				@Override
				public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc) {
					boolean vanilla = false || !mc.player.areEyesInFluid(FluidTags.WATER);
					if (mc.world.getDimensionRenderInfo().getFogType() == DimensionRenderInfo.FogType.END) {
						// renderSkyEnd(matrixStack);
					} else if (mc.world.getDimensionRenderInfo().getFogType() == DimensionRenderInfo.FogType.NORMAL) {
						RenderSystem.disableTexture();
						Vector3d vector3d = world.getSkyColor(mc.gameRenderer.getActiveRenderInfo().getBlockPos(), partialTicks);
						float f = (float) vector3d.x;
						float f1 = (float) vector3d.y;
						float f2 = (float) vector3d.z;
						FogRenderer.applyFog();
						BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
						RenderSystem.depthMask(false);
						RenderSystem.enableFog();
						RenderSystem.color3f(f, f1, f2);
						VertexBuffer skyVBO = new ReflectionField<VertexBuffer>(WorldRenderer.class, "?", "skyVBO").get(mc.worldRenderer);
						skyVBO.bindBuffer();
						DefaultVertexFormats.POSITION.setupBufferState(0L);
						skyVBO.draw(matrixStack.getLast().getMatrix(), 7);
						VertexBuffer.unbindBuffer();
						DefaultVertexFormats.POSITION.clearBufferState();
						RenderSystem.disableFog();
						RenderSystem.disableAlphaTest();
						RenderSystem.enableBlend();
						RenderSystem.defaultBlendFunc();
						float[] afloat = world.getDimensionRenderInfo().func_230492_a_(world.func_242415_f(partialTicks), partialTicks);
						if (vanilla && afloat != null) {
							RenderSystem.disableTexture();
							RenderSystem.shadeModel(7425);
							matrixStack.push();
							matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0F));
							float f3 = MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F;
							matrixStack.rotate(Vector3f.ZP.rotationDegrees(f3));
							matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0F));
							float f4 = afloat[0];
							float f5 = afloat[1];
							float f6 = afloat[2];
							Matrix4f matrix4f = matrixStack.getLast().getMatrix();
							bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
							bufferbuilder.pos(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();
							int i = 16;

							for (int j = 0; j <= 16; ++j) {
								float f7 = (float) j * ((float) Math.PI * 2F) / 16.0F;
								float f8 = MathHelper.sin(f7);
								float f9 = MathHelper.cos(f7);
								bufferbuilder.pos(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
							}

							bufferbuilder.finishDrawing();
							WorldVertexBufferUploader.draw(bufferbuilder);
							matrixStack.pop();
							RenderSystem.shadeModel(7424);
						}

						RenderSystem.enableTexture();
						RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
						matrixStack.push();
						float f11 = 1.0F - world.getRainStrength(partialTicks);
						RenderSystem.color4f(1.0F, 1.0F, 1.0F, f11);
						matrixStack.rotate(Vector3f.YP.rotationDegrees(-90.0F));
						matrixStack.rotate(Vector3f.XP.rotationDegrees(world.func_242415_f(partialTicks) * 360.0F));
						Matrix4f matrix4f1 = matrixStack.getLast().getMatrix();
						float f12 = 30.0F;
						mc.textureManager.bindTexture(new ResourceLocation("textures/environment/sun.png"));
						bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
						bufferbuilder.pos(matrix4f1, -f12, 100.0F, -f12).tex(0.0F, 0.0F).endVertex();
						bufferbuilder.pos(matrix4f1, f12, 100.0F, -f12).tex(1.0F, 0.0F).endVertex();
						bufferbuilder.pos(matrix4f1, f12, 100.0F, f12).tex(1.0F, 1.0F).endVertex();
						bufferbuilder.pos(matrix4f1, -f12, 100.0F, f12).tex(0.0F, 1.0F).endVertex();
						bufferbuilder.finishDrawing();
						if (vanilla) {
							WorldVertexBufferUploader.draw(bufferbuilder);
						} else {
							bufferbuilder.getNextBuffer().getSecond().clear();
						}
						f12 = 20.0F;
						mc.textureManager.bindTexture(new ResourceLocation("textures/environment/moon_phases.png"));
						int k = world.getMoonPhase();
						int l = k % 4;
						int i1 = k / 4 % 2;
						float f13 = (float) (l + 0) / 4.0F;
						float f14 = (float) (i1 + 0) / 2.0F;
						float f15 = (float) (l + 1) / 4.0F;
						float f16 = (float) (i1 + 1) / 2.0F;
						bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
						bufferbuilder.pos(matrix4f1, -f12, -100.0F, f12).tex(f15, f16).endVertex();
						bufferbuilder.pos(matrix4f1, f12, -100.0F, f12).tex(f13, f16).endVertex();
						bufferbuilder.pos(matrix4f1, f12, -100.0F, -f12).tex(f13, f14).endVertex();
						bufferbuilder.pos(matrix4f1, -f12, -100.0F, -f12).tex(f15, f14).endVertex();
						bufferbuilder.finishDrawing();
						if (vanilla) {
							WorldVertexBufferUploader.draw(bufferbuilder);
						} else {
							bufferbuilder.getNextBuffer().getSecond().clear();
						}
						RenderSystem.disableTexture();
						float f10 = world.getStarBrightness(partialTicks) * f11;
						if (vanilla && f10 > 0.0F) {
							RenderSystem.color4f(f10, f10, f10, f10);
							VertexBuffer starVBO = new ReflectionField<VertexBuffer>(WorldRenderer.class, "?", "starVBO").get(mc.worldRenderer);
							starVBO.bindBuffer();
							DefaultVertexFormats.POSITION.setupBufferState(0L);
							starVBO.draw(matrixStack.getLast().getMatrix(), 7);
							VertexBuffer.unbindBuffer();
							DefaultVertexFormats.POSITION.clearBufferState();
						}

						RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
						RenderSystem.disableBlend();
						RenderSystem.enableAlphaTest();
						RenderSystem.enableFog();
						matrixStack.pop();
						RenderSystem.disableTexture();
						RenderSystem.color3f(0.0F, 0.0F, 0.0F);
						double d0 = mc.player.getEyePosition(partialTicks).y - world.getWorldInfo().getVoidFogHeight();
						if (vanilla && d0 < 0.0D) {
							matrixStack.push();
							matrixStack.translate(0.0D, 12.0D, 0.0D);
							VertexBuffer sky2VBO = new ReflectionField<VertexBuffer>(WorldRenderer.class, "?", "sky2VBO").get(mc.worldRenderer);
							sky2VBO.bindBuffer();
							DefaultVertexFormats.POSITION.setupBufferState(0L);
							sky2VBO.draw(matrixStack.getLast().getMatrix(), 7);
							VertexBuffer.unbindBuffer();
							DefaultVertexFormats.POSITION.clearBufferState();
							matrixStack.pop();
						}

						if (world.getDimensionRenderInfo().func_239216_b_()) {
							RenderSystem.color3f(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
						} else {
							RenderSystem.color3f(f, f1, f2);
						}

						RenderSystem.enableTexture();
						RenderSystem.depthMask(true);
						RenderSystem.disableFog();
					}
				}
			});
		}
	}
}
