package meldexun.better_diving.world.gen.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

public class SeafloorOutcropFeature extends Feature<OceanOreFeatureConfig> {

	public SeafloorOutcropFeature(Codec<OceanOreFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, OceanOreFeatureConfig config) {
		if (config.getConfig().chance.get() > 0 && rand.nextInt(config.getConfig().chance.get()) != 0) {
			return false;
		}
		int i = 0;
		int j = config.sample(rand);
		for (int k = 0; k < j; k++) {
			for (int l = 0; l < 4; l++) {
				int x = rand.nextInt(8) - rand.nextInt(8);
				int z = rand.nextInt(8) - rand.nextInt(8);
				int yCenter = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + x, pos.getZ() + z);
				if (yCenter > config.getConfig().maxHeight.get()) {
					continue;
				}
				int yNorth = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + x, pos.getZ() + z - 1);
				int ySouth = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + x, pos.getZ() + z + 1);
				int yEast = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + x + 1, pos.getZ() + z);
				int yWest = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + x - 1, pos.getZ() + z);
				int yMax = Math.max(Math.max(yNorth, ySouth), Math.max(yEast, yWest));
				if (yMax < config.getConfig().minHeight.get()) {
					continue;
				}
				int yMin = Math.max(yCenter, config.getConfig().minHeight.get());
				yMax = Math.min(yMax, config.getConfig().maxHeight.get());
				int y = yMax > yMin ? yMin + rand.nextInt(yMax - yMin) : yMin;

				BlockPos p = new BlockPos(pos.getX() + x, y, pos.getZ() + z);
				List<Direction> list = new ArrayList<>();
				if (y == yCenter) {
					list.add(Direction.UP);
				}
				if (y < yNorth) {
					list.add(Direction.SOUTH);
				}
				if (y < ySouth) {
					list.add(Direction.NORTH);
				}
				if (y < yEast) {
					list.add(Direction.WEST);
				}
				if (y < yWest) {
					list.add(Direction.EAST);
				}
				Direction dir = list.get(rand.nextInt(list.size()));
				BlockState state = config.getBlock().defaultBlockState().setValue(BlockStateProperties.FACING, dir);
				if (reader.getBlockState(p).is(Blocks.WATER) && state.canSurvive((IWorldReader) reader, p)) {
					reader.setBlock(p, state, 2);
					i++;
					break;
				}
			}
		}
		return i > 0;
	}

}
