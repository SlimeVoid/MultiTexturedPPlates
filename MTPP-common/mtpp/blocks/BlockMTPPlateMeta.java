package mtpp.blocks;

import java.util.List;
import java.util.Random;

import eurysmods.api.IContainer;

import mtpp.core.MTPPInit;
import mtpp.core.MTPPItemPPlates;
import mtpp.tileentities.TileEntityMTPPlate;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockFence;
import net.minecraft.src.BlockPressurePlate;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.StepSound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockMTPPlateMeta extends BlockPressurePlate {

	public BlockMTPPlateMeta(int par1, float hardness, StepSound sound, boolean disableStats, boolean requiresSelfNotify) {
		super(par1, 0, null, Material.circuits);
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
	public int getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		int damage = getPlateFromMeta(meta);
		if (damage <= 1) damage = meta;
		return MTPPInit.MTPP.getProxy().getBlockTextureFromSideAndMetadata(side, damage);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		int damage = getPlateFromMeta(meta);
		if (damage <= 1) damage = meta;
		return MTPPInit.MTPP.getProxy().getBlockTextureFromSideAndMetadata(side, damage);
	}
	
	private static int getStateFromMeta(int meta) {
		int state = meta & 1;
		return state;
	}
	
	private static int getTriggerFromMeta(int meta) {
		int trigger = meta >> 1;
		return trigger & 1;
	}
	
	private static int getPlateFromMeta(int meta) {
		int texture = meta >> 2;
		return texture & 3;
	}
	
	private static int addStateToMeta(int meta, int state) {
		int newmeta = meta & 14;
		int newstate = state & 1;
		int stateAndMeta = newmeta | newstate;
		return stateAndMeta;
	}

	private void setStateIfMobInteractsWithMTPlate(World world, int x, int y, int z) {
		int state = getStateFromMeta(world.getBlockMetadata(x, y, z));
		System.out.println("state: " + Integer.toBinaryString(state));
		int trigger = getTriggerFromMeta(world.getBlockMetadata(x, y, z));
		System.out.println("trigger: " + Integer.toBinaryString(trigger));
		boolean flag = state == 1;
		boolean flag1 = false;
		float f = 0.125F;
		List list = null;
		if (trigger == 0) {
			list = world.getEntitiesWithinAABBExcludingEntity(
					null,
					AxisAlignedBB.getBoundingBox(
							x + f,
							y,
							z + f,
							(x + 1) - f,
							y + 0.25D,
							(z + 1) - f));
		}
		if (trigger == 1) {
			list = world.getEntitiesWithinAABB(
					net.minecraft.src.EntityLiving.class,
					AxisAlignedBB.getBoundingBox(
							x + f,
							y,
							z + f,
							(x + 1) - f,
							y + 0.25D,
							(z + 1) - f));
		}
		if (trigger == 2) {
			list = world.getEntitiesWithinAABB(
					net.minecraft.src.EntityPlayer.class,
					AxisAlignedBB.getBoundingBox(
							x + f,
							y,
							z + f,
							(x + 1) - f,
							y + 0.25D,
							(z + 1) - f));
		}
		if (list.size() > 0) {
			flag1 = true;
		}
		if (flag1 && !flag) {
			int newMeta = addStateToMeta(world.getBlockMetadata(x, y, z), 1); 
			System.out.println("newmeta: " + Integer.toBinaryString(newMeta));
			world.setBlockMetadataWithNotify(x, y, z, newMeta);
			world.notifyBlocksOfNeighborChange(x, y, z, blockID);
			world.notifyBlocksOfNeighborChange(x, y - 1, z, blockID);
			world.markBlocksDirty(x, y, z, x, y, z);
			world.playSoundEffect(
					x + 0.5D,
					y + 0.10000000000000001D,
					z + 0.5D,
					"random.click",
					0.3F,
					0.6F);
		}
		if (!flag1 && flag) {
			int newMeta = addStateToMeta(world.getBlockMetadata(x, y, z), 0); 
			System.out.println("newmeta: " + Integer.toBinaryString(newMeta));
			world.setBlockMetadataWithNotify(x, y, z, newMeta);
			world.notifyBlocksOfNeighborChange(x, y, z, blockID);
			world.notifyBlocksOfNeighborChange(x, y - 1, z, blockID);
			world.markBlocksDirty(x, y, z, x, y, z);
			world.playSoundEffect(
					x + 0.5D,
					y + 0.10000000000000001D,
					z + 0.5D,
					"random.click",
					0.3F,
					0.5F);
		}
		if (flag1) {
			world.scheduleBlockUpdate(x, y, z, blockID, tickRate());
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if (!world.isRemote) {
			int state = getStateFromMeta(world.getBlockMetadata(x, y, z));
			if (state != 0) {
				this.setStateIfMobInteractsWithMTPlate(
						world,
						x,
						y,
						z);
			}
		}
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the
	 * block). Args: world, x, y, z, entity
	 */
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (!world.isRemote) {
			int state = getStateFromMeta(world.getBlockMetadata(x, y, z));
			if (state != 1) {
				this.setStateIfMobInteractsWithMTPlate(
						world,
						x,
						y,
						z);
			}
		}
	}
	
    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
	@Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
		int meta = world.getBlockMetadata(x, y, z);
        boolean flag = getStateFromMeta(meta) == 1;
        float var6 = 0.0625F;

        if (flag)
        {
            this.setBlockBounds(var6, 0.0F, var6, 1.0F - var6, 0.03125F, 1.0F - var6);
        }
        else
        {
            this.setBlockBounds(var6, 0.0F, var6, 1.0F - var6, 0.0625F, 1.0F - var6);
        }
    }

    /**
     * Is this block powering the block on the specified side
     */
	@Override
    public boolean isPoweringTo(IBlockAccess world, int x, int y, int z, int side)
    {
		int meta = world.getBlockMetadata(x, y, z);
        return getStateFromMeta(meta) > 0;
    }

    /**
     * Is this block indirectly powering the block on the specified side
     */
	@Override
    public boolean isIndirectlyPoweringTo(IBlockAccess world, int x, int y, int z, int side)
    {
		int meta = world.getBlockMetadata(x, y, z);
        return getStateFromMeta(meta) == 0 ? false : side == 1;
    }
	
	@Override
    public void breakBlock(World world, int x, int y, int z, int side, int meta) {
		System.out.println(meta);
		int damage = getPlateFromMeta(meta);
		ItemStack itemstack = MTPPItemPPlates.getStack(damage);
		EntityItem entityitem = new EntityItem(world, x, y, z, new ItemStack(
				itemstack.itemID,
				1,
				itemstack.getItemDamage()));
		world.spawnEntityInWorld(entityitem);
		super.breakBlock(world, x, y, z, side, meta);
    }

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
}
