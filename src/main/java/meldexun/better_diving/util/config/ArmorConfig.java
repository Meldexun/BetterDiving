package meldexun.better_diving.util.config;

public class ArmorConfig {

	public int durability;
	public int enchantability;
	public int[] protection;
	public double toughness;

	public ArmorConfig(int durability, int enchantability, int[] protection, double toughness) {
		this.durability = durability;
		this.enchantability = enchantability;
		this.protection = protection;
		this.toughness = toughness;
	}

}
