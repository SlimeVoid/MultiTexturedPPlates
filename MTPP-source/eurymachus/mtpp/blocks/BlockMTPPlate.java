package eurymachus.mtpp.blocks;

import java.util.List;
import java.util.Random;

import slimevoid.lib.IContainer;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eurymachus.mtpp.core.MTPPInit;
import eurymachus.mtpp.core.MTPPItemPPlates;
import eurymachus.mtpp.tileentities.TileEntityMTPPlate;

public class BlockMTPPlate extends BlockPressurePlate implements IContainer {
	Class mtPPlateEntityClass;

	public BlockMTPPlate(int blockId, Class pPlateClass, float hardness, StepSound sound, boolean disableStats, boolean requiresSelfNotify, String blockName) {
		super(blockId, 0, null, Material.circuits);
		this.setBlockName(blockName);
		this.isBlockContainer = true;
		mtPPlateEntityClass = pPlateClass;
		setHardness(hardness);
		setStepSound(sound);
		if (disableStats) {
			disableStats();
		}
		if (requiresSelfNotify) {
			setRequiresSelfNotify();
		}
	}

	@Override
	public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		int texture = MTPPItemPPlates.getTexture(MTPPInit.getDamageValue(
				par1IBlockAccess,
				par2,
				par3,
				par4));
		if (texture >= 0) {
			return texture;
		}
		return 22;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		return MTPPInit.MTPP.getProxy().getBlockTextureFromSideAndMetadata(
				side,
				meta);
	}

	private void setStateIfMobInteractsWithMTPlate(World world, int i, int j, int k) {
		TileEntity tileentity = world.getBlockTileEntity(i, j, k);
		if ((tileentity != null) && (tileentity instanceof TileEntityMTPPlate)) {
			TileEntityMTPPlate tileentitymtpplate = (TileEntityMTPPlate) tileentity;
			boolean flag = world.getBlockMetadata(i, j, k) == 1;
			boolean flag1 = false;
			float f = 0.125F;
			List list = null;
			if (tileentitymtpplate.getTriggerType() == 0) {
				list = world.getEntitiesWithinAABBExcludingEntity(
						null,
						AxisAlignedBB.getBoundingBox(
								i + f,
								j,
								k + f,
								(i + 1) - f,
								j + 0.25D,
								(k + 1) - f));
			}
			if (tileentitymtpplate.getTriggerType() == 1) {
				list = world.getEntitiesWithinAABB(
						net.minecraft.entity.EntityLiving.class,
						AxisAlignedBB.getBoundingBox(
								i + f,
								j,
								k + f,
								(i + 1) - f,
								j + 0.25D,
								(k + 1) - f));
			}
			if (tileentitymtpplate.getTriggerType() == 2) {
				list = world.getEntitiesWithinAABB(
						net.minecraft.entity.player.EntityPlayer.class,
						AxisAlignedBB.getBoundingBox(
								i + f,
								j,
								k + f,
								(i + 1) - f,
								j + 0.25D,
								(k + 1) - f));
			}
			if (list.size() > 0) {
				flag1 = true;
			}
			if (flag1 && !flag) {
				world.setBlockMetadataWithNotify(i, j, k, 1);
				world.notifyBlocksOfNeighborChange(i, j, k, blockID);
				world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
				world.markBlockRangeForRenderUpdate(i, j, k, i, j, k);
				world.playSoundEffect(
						i + 0.5D,
						j + 0.10000000000000001D,
						k + 0.5D,
						"random.click",
						0.3F,
						0.6F);
			}
			if (!flag1 && flag) {
				world.setBlockMetadataWithNotify(i, j, k, 0);
				world.notifyBlocksOfNeighborChange(i, j, k, blockID);
				world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
				world.markBlockRangeForRenderUpdate(i, j, k, i, j, k);
				world.playSoundEffect(
						i + 0.5D,
						j + 0.10000000000000001D,
						k + 0.5D,
						"random.click",
						0.3F,
						0.5F);
			}
			if (flag1) {
				world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
			}
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (!par1World.isRemote) {
			if (par1World.getBlockMetadata(par2, par3, par4) != 0) {
				this.setStateIfMobInteractsWithMTPlate(
						par1World,
						par2,
						par3,
						par4);
			}
		}
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the
	 * block). Args: world, x, y, z, entity
	 */
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		if (!par1World.isRemote) {
			if (par1World.getBlockMetadata(par2, par3, par4) != 1) {
				this.setStateIfMobInteractsWithMTPlate(
						par1World,
						par2,
						par3,
						par4);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	private boolean isCreative(EntityPlayer entityplayer, World world) {
		if (entityplayer instanceof EntityClientPlayerMP) {
			if (ModLoader.getMinecraftInstance().playerController
					.isInCreativeMode() || world.getWorldInfo().getGameType() == EnumGameType.CREATIVE) {
				return true;
			}
		}
		return false;
	}

	private boolean isCreative(EntityPlayer entityplayer) {
		if (entityplayer.worldObj.isRemote) {
			return isCreative(entityplayer, entityplayer.worldObj);
		}
		if (entityplayer != null && entityplayer instanceof EntityPlayerMP) {
			if (((EntityPlayerMP) entityplayer).theItemInWorldManager
					.getGameType() == EnumGameType.CREATIVE) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer entityplayer, int x, int y, int z) {
		if (!isCreative(entityplayer)) {
			if (!world.isRemote) {
				ItemStack itemstack = MTPPItemPPlates.getStack(MTPPInit
						.getDamageValue(world, x, y, z));
				EntityItem entityitem = new EntityItem(
						world,
							x,
							y,
							z,
							new ItemStack(
									itemstack.itemID,
										1,
										itemstack.getItemDamage()));
				world.spawnEntityInWorld(entityitem);
			}
		}
		return world.setBlockWithNotify(x, y, z, 0);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int a, int b) {
		super.breakBlock(world, i, j, k, a, b);
		world.removeBlockTileEntity(i, j, k);
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		try {
			return (TileEntity) mtPPlateEntityClass.newInstance();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return createNewTileEntity(world);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.setBlockTileEntity(
				x,
				y,
				z,
				this.createTileEntity(world, world.getBlockMetadata(x, y, z)));
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return MTPPItemPPlates.getHardness(MTPPInit.getDamageValue(
				world,
				x,
				y,
				z));
	}

	@SideOnly(Side.CLIENT)
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(int blockId, CreativeTabs creativeTabs, List blockList) {
		for (MTPPItemPPlates plate : MTPPItemPPlates.values()) {
			if (plate.stackID > 1) {
				blockList.add(new ItemStack(blockId, 1, plate.stackID));
			}
		}
	}
}
