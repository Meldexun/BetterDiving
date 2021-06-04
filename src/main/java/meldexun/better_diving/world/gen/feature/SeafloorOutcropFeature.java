package meldexun.better_diving.world.gen.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mojang.serialization.Codec;

import meldexun.better_diving.block.BlockUnderwaterOre;
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
import net.minecraft.world.gen.feature.FeatureSpreadConfig;

public class SeafloorOutcropFeature extends Feature<FeatureSpreadConfig> {

	private final BlockUnderwaterOre block;

	public SeafloorOutcropFeature(Codec<FeatureSpreadConfig> codec, BlockUnderwaterOre block) {
		super(codec);
		this.block = block;
	}

	@Override
	public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, FeatureSpreadConfig config) {
		int i = 0;
		int j = config.count().sample(rand);
		for (int k = 0; k < j; k++) {
			for (int l = 0; l < 4; l++) {
				int x = rand.nextInt(8) - rand.nextInt(8);
				int z = rand.nextInt(8) - rand.nextInt(8);
				int yCenter = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + x, pos.getZ() + z);
				int yNorth = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + x, pos.getZ() + z - 1);
				int ySouth = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + x, pos.getZ() + z + 1);
				int yEast = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + x + 1, pos.getZ() + z);
				int yWest = reader.getHeight(Heightmap.Type.OCEAN_FLOOR, pos.getX() + x - 1, pos.getZ() + z);
				int yMax = Math.max(Math.max(yNorth, ySouth), Math.max(yEast, yWest));
				int y = yMax > yCenter ? yCenter + rand.nextInt(yMax - yCenter) : yCenter;

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
				BlockState state = this.block.defaultBlockState().setValue(BlockStateProperties.FACING, dir);
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
