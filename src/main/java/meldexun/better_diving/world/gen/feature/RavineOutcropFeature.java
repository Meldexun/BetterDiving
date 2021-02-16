package meldexun.better_diving.world.gen.feature;

import java.util.ArrayList;
import java.util.Collections;
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
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;

public class RavineOutcropFeature extends Feature<FeatureSpreadConfig> {

	private final BlockUnderwaterOre block;

	public RavineOutcropFeature(Codec<FeatureSpreadConfig> codec, BlockUnderwaterOre block) {
		super(codec);
		this.block = block;
	}

	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, FeatureSpreadConfig config) {
		int i = 0;
		int j = config.func_242799_a().func_242259_a(rand);
		for (int k = 0; k < j; k++) {
			for (int l = 0; l < 16; l++) {
				int x = rand.nextInt(8) - rand.nextInt(8);
				int z = rand.nextInt(8) - rand.nextInt(8);
				int y = 1 + rand.nextInt(generator.getHeight(pos.getX() + x, pos.getZ() + z, Type.OCEAN_FLOOR) - 1);
				BlockPos p = new BlockPos(pos.getX() + x, y, pos.getZ() + z);
				if (!reader.getBlockState(p).isIn(Blocks.WATER)) {
					continue;
				}
				boolean flag = false;
				for (int x1 = -1; x1 <= 1; x1++) {
					for (int z1 = -1; z1 <= 1; z1++) {
						if (y > reader.getHeight(Heightmap.Type.OCEAN_FLOOR, p.getX() + x1, p.getZ() + z1)) {
							flag = true;
							break;
						}
					}
					if (flag) {
						break;
					}
				}
				if (!flag) {
					continue;
				}
				List<Direction> list = new ArrayList<>();
				list.add(Direction.UP);
				list.add(Direction.NORTH);
				list.add(Direction.SOUTH);
				list.add(Direction.EAST);
				list.add(Direction.WEST);
				Collections.shuffle(list);
				for (Direction d : list) {
					BlockState state = this.block.getDefaultState().with(BlockStateProperties.FACING, d);
					if (state.isValidPosition((IWorldReader) reader, p)) {
						reader.setBlockState(p, state, 2);
						i++;
						break;
					}
				}
			}
		}
		return i > 0;
	}

}
