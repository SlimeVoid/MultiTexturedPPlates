package mtpp.items;

import mtpp.core.MTPPBlocks;
import mtpp.core.MTPPItemPPlates;
import mtpp.tileentities.TileEntityMTPPlate;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class ItemMTPPlate extends ItemBlock {
	private String[] pPlateNames = MTPPItemPPlates.getPPlateNames();

	private final Block blockRef;

	public ItemMTPPlate(int i) {
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
	public ItemMTPPlate setBlockNames(String[] par1ArrayOfStr) {
		this.pPlateNames = par1ArrayOfStr;
		return this;
	}

	@Override
	public int getIconFromDamage(int damage) {
		return this.blockRef.getBlockTextureFromSideAndMetadata(1000, damage);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float a, float b, float c) {
		Block mtPPlate = MTPPBlocks.mtPPlate.me;
		if (l == 0) {
			--j;
		}

		if (l == 1) {
			++j;
		}

		if (l == 2) {
			--k;
		}

		if (l == 3) {
			++k;
		}

		if (l == 4) {
			--i;
		}

		if (l == 5) {
			++i;
		}
		if (itemstack.stackSize == 0) {
			return false;
		} else if (!entityplayer.func_82247_a(i, j, k, l, itemstack)) {
			return false;
		} else if (j == 255 && mtPPlate.blockMaterial.isSolid()) {
			return false;
		} else if (world.canPlaceEntityOnSide(
				mtPPlate.blockID,
				i,
				j,
				k,
				false,
				l,
				null)) {
			if (world.setBlockAndMetadataWithNotify(
					i,
					j,
					k,
					mtPPlate.blockID,
					0)) {
				if (world.getBlockId(i, j, k) == mtPPlate.blockID) {
					mtPPlate.onBlockPlacedBy(world, i, j, k, entityplayer);
					mtPPlate.func_85105_g(world, i, j, k, l);
					TileEntity tileentity = world.getBlockTileEntity(i, j, k);
					if (tileentity != null && tileentity instanceof TileEntityMTPPlate) {
						TileEntityMTPPlate tileentitymtpplate = (TileEntityMTPPlate) tileentity;
						tileentitymtpplate.setTextureValue(itemstack
								.getItemDamage());
						tileentitymtpplate.setTriggerType(MTPPItemPPlates
								.getTriggerType(itemstack.getItemDamage()));
						tileentitymtpplate.onInventoryChanged();
					}
				}
				world.playSoundEffect(
						(i + 0.5F),
						(j + 0.5F),
						(k + 0.5F),
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