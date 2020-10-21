package meldexun.better_diving.tileentity;

import java.util.Random;

import meldexun.better_diving.block.AbstractBlockCreepvine;
import meldexun.better_diving.init.ModBlocks;
import meldexun.better_diving.util.BetterDivingConfig;
import net.minecraft.util.ITickable;

public class TileEntityCreepvineTop extends TileEntityCreepvine implements ITickable {

	public final Random rand = new Random();

	public TileEntityCreepvineTop() {
		this(AbstractBlockCreepvine.MAX_HEIGHT, false);
	}

	public TileEntityCreepvineTop(int maxHeight, boolean generateSeeds) {
		super(maxHeight, generateSeeds);
	}

	@Override
	public void update() {
		if (!this.world.isRemote && this.rand.nextInt(1800) == 0) {
			int height = this.getCreepvineHeight();

			if (height < this.getMaxHeight()) {
				if (ModBlocks.CREEPVINE_TOP.canPlaceBlockAt(this.world, this.pos.up())) {
					ModBlocks.CREEPVINE.setCreepvine(this.world, this.pos, 3, this.getMaxHeight(), this.canGenerateSeeds());
					ModBlocks.CREEPVINE_TOP.setCreepvine(this.world, this.pos.up(), 3, this.getMaxHeight(), this.canGenerateSeeds());
				}
			} else if (BetterDivingConfig.getInstance().plants.shouldGenerateCreepvineSeedCluster && this.canGenerateSeeds() && this.rand.nextBoolean()) {
				ModBlocks.CREEPVINE_SEED.setCreepvine(this.world, this.pos.down((int) (height * 0.5D - 0.5D)), 3, this.getMaxHeight(), this.canGenerateSeeds());
			}
		}
	}

}
