package mtpp.items;

import mtpp.core.MTPPBlocks;
import mtpp.core.MTPPItemPPlates;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMTPPlateMeta extends ItemBlock {
	private String[] pPlateNames = MTPPItemPPlates.getPPlateNames();

	private final Block blockRef;

	public ItemMTPPlateMeta(int i) {
		super(i);
		this.blockRef = MTPPBlocks.mtPPlate.me;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setNoRepair();
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return (new StringBuilder())
				.append(super.getItemName())
					.append(".")
					.append(pPlateNames[itemstack.getItemDamage()])
					.toString();
	}

	public int filterData(int i) {
		return i;
	}

	/**
	 * sets the array of strings to be used for name lookups from item damage to
	 * metadata
	 */
	public ItemMTPPlateMeta setBlockNames(String[] par1ArrayOfStr) {
		this.pPlateNames = par1ArrayOfStr;
		return this;
	}

	private static int getFullMetadata(int pplate, int triggerType) {
		int newpplate = pplate << 2;
		int newtriggerType = triggerType << 1;
		return newpplate | newtriggerType;
	}

	@Override
	public int getIconFromDamage(int damage) {
		int icon = MTPPItemPPlates.getTexture(damage);
		return icon;
	}

	/**
	 * Returns the metadata of the block which this Item (ItemBlock) can place
	 */
	@Override
	public int getMetadata(int damage) {
		int meta = getFullMetadata(
				damage,
				MTPPItemPPlates.getTriggerType(damage));
		return meta;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int l, float a, float b, float c) {
		Block mtPPlate = MTPPBlocks.mtPPlate.me;
		if (l == 0) {
			--y;
		}

		if (l == 1) {
			++y;
		}

		if (l == 2) {
			--z;
		}

		if (l == 3) {
			++z;
		}

		if (l == 4) {
			--x;
		}

		if (l == 5) {
			++x;
		}
		if (itemstack.stackSize == 0) {
			return false;
		} else if (!entityplayer.canPlayerEdit(x, y, z, l, itemstack)) {
			return false;
		} else if (y == 255 && mtPPlate.blockMaterial.isSolid()) {
			return false;
		} else if (world.canPlaceEntityOnSide(
				mtPPlate.blockID,
				x,
				y,
				z,
				false,
				l,
				null)) {
			if (world.setBlockAndMetadataWithNotify(
					x,
					y,
					z,
					mtPPlate.blockID,
					this.getMetadata(itemstack.getItemDamage()))) {
				if (world.getBlockId(x, y, z) == mtPPlate.blockID) {
					mtPPlate.onBlockPlacedBy(world, x, y, z, entityplayer);
					mtPPlate.onPostBlockPlaced(world, x, y, z, l);
				}
				world.playSoundEffect(
						(x + 0.5F),
						(y + 0.5F),
						(z + 0.5F),
						mtPPlate.stepSound.getStepSound(),
						(mtPPlate.stepSound.getVolume() + 1.0F) / 2.0F,
						mtPPlate.stepSound.getPitch() * 0.8F);
				--itemstack.stackSize;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}