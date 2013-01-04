package eurymachus.mtpp.core;

import net.minecraft.block.EnumMobType;
import net.minecraft.item.ItemStack;

public enum MTPPItemPPlates {
	topplaceholder,
	bottomplaceholder,
	iron,
	gold,
	diamond,
	oakPlank,
	sprucePlank,
	birchPlank,
	junglePlank,
	polishedStone,
	cobbleStone;

	public ItemStack me;
	public int stackID;
	public String name;
	private int textureIndex;
	private int triggerType;
	private float hardness;

	public static ItemStack getStack(int itemDamage) {
		for (MTPPItemPPlates itemstack : MTPPItemPPlates.values()) {
			if (itemstack != null && itemstack.stackID == itemDamage) {
				return itemstack.me;
			}
		}
		return null;
	}

	public static int getTexture(int itemDamage) {
		for (MTPPItemPPlates itemstack : MTPPItemPPlates.values()) {
			if (itemstack != null && itemstack.stackID == itemDamage) {
				return itemstack.textureIndex;
			}
		}
		return -1;
	}

	public static int getTriggerType(int itemDamage) {
		for (MTPPItemPPlates itemstack : MTPPItemPPlates.values()) {
			if (itemstack != null && itemstack.stackID == itemDamage) {
				return itemstack.triggerType;
			}
		}
		return 1;
	}

	public static float getHardness(int itemDamage) {
		for (MTPPItemPPlates itemstack : MTPPItemPPlates.values()) {
			if (itemstack != null && itemstack.stackID == itemDamage) {
				return itemstack.hardness;
			}
		}
		return 0.5F;
	}

	public void setTextureIndex(int textureIndex) {
		this.textureIndex = textureIndex;
	}

	public void setTriggerType(EnumMobType triggerType) {

		if (triggerType == EnumMobType.everything) {
			this.triggerType = 0;
		}

		if (triggerType == EnumMobType.mobs) {
			this.triggerType = 1;
		}
	}

	public void setBlockHardness(float hardness) {
		this.hardness = hardness;
	}

	public static String[] getPPlateNames() {
		String[] names = new String[MTPPItemPPlates.values().length];
		int i = 0;
		for (MTPPItemPPlates itemstack : MTPPItemPPlates.values()) {
			if (itemstack != null && itemstack.name != null && !itemstack.name
					.isEmpty()) {
				names[i] = itemstack.name;
			} else {
				names[i] = "";
			}
			i++;
		}
		return names;
	}
}
