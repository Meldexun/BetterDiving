package meldexun.better_diving.util.config;

import meldexun.better_diving.util.ByteBufHelper;
import net.minecraftforge.common.config.Config;

public class DivingGearConfig {

	public ArmorConfig armorValues;
	@ByteBufHelper.Sync
	@Config.Comment("Swim speed bonus in percent")
	@Config.RangeDouble(min = 0.0D, max = 1.0D)
	public double finsMovespeed;
	@ByteBufHelper.Sync
	@Config.Comment("An improved diving mask removes the oxygen usage penality when diving deep")
	public boolean improvedGear;
	@ByteBufHelper.Sync
	@Config.Comment("Reinforced diving dear shows tool tip")
	public boolean reinforcedGear;
	@ByteBufHelper.Sync
	@Config.Comment("Break speed bonus in percent")
	@Config.RangeDouble(min = 0.0D, max = 1.0D)
	public double legsBreakspeed;
	@ByteBufHelper.Sync
	@Config.Comment("Oxygen capacity in ticks (20 ticks = 1 second)")
	@Config.RangeInt(min = 0, max = 10000)
	public int tankAirStorage;
	@ByteBufHelper.Sync
	@Config.Comment("Swim speed bonus in percent")
	@Config.RangeDouble(min = -1.0D, max = 0.0D)
	public double tankMovespeed;

	public DivingGearConfig(double finsMovespeed, boolean improvedGear, boolean reinforcedGear, double legsBreakspeed, int tankAirStorage, double tankMovespeed, ArmorConfig armorValues) {
		this.finsMovespeed = finsMovespeed;
		this.improvedGear = improvedGear;
		this.legsBreakspeed = legsBreakspeed;
		this.reinforcedGear = reinforcedGear;
		this.tankAirStorage = tankAirStorage;
		this.tankMovespeed = tankMovespeed;
		this.armorValues = armorValues;
	}

	public DivingGearConfig(double finsMovespeed, boolean improvedGear, boolean reinforcedGear, double legsBreakspeed, int tankAirStorage, double tankMovespeed, int durability, int enchantability, int[] protection, float toughness) {
		this.finsMovespeed = finsMovespeed;
		this.improvedGear = improvedGear;
		this.legsBreakspeed = legsBreakspeed;
		this.reinforcedGear = reinforcedGear;
		this.tankAirStorage = tankAirStorage;
		this.tankMovespeed = tankMovespeed;
		this.armorValues = new ArmorConfig(durability, enchantability, protection, toughness);
	}

}
