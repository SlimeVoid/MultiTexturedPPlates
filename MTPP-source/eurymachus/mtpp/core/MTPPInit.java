package eurymachus.mtpp.core;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.Configuration;
import slimevoid.lib.ICommonProxy;
import slimevoid.lib.ICore;
import slimevoid.lib.core.BlockRemover;
import slimevoid.lib.core.Core;
import slimevoid.lib.core.RecipeRemover;
import slimevoid.lib.core.SlimevoidCore;
import eurymachus.mtpp.tileentities.TileEntityMTPPlate;

public class MTPPInit {
	public static ICore MTPP;
	private static boolean initialized = false;

	public static void initialize(ICommonProxy proxy) {
		if (initialized)
			return;
		initialized = true;
		MTPP = new Core(proxy);
		MTPP.setModName("MultiTexturedPPlates");
		MTPP.setModChannel("MTPP");
		MTPPCore.configFile = new File(MTPPInit.MTPP
				.getProxy()
					.getMinecraftDir(), "config/MultiTexturedPPlates.cfg");
		MTPPCore.configuration = new Configuration(MTPPCore.configFile);
		load();
	}

	public static void load() {
		SlimevoidCore.console(MTPP.getModName(), "Removing Recipies...");
		RecipeRemover.registerItemRecipeToRemove(Block.pressurePlatePlanks);
		RecipeRemover.removeCrafting();
		SlimevoidCore.console(MTPP.getModName(), "Removing Blocks...");
		BlockRemover.removeVanillaBlock(Block.pressurePlatePlanks);
		SlimevoidCore.console(MTPP.getModName(), "Registering items...");
		MTPPCore.addItems();
		SlimevoidCore.console(MTPP.getModName(), "Registering blocks...");
		MTPPCore.registerBlocks();
		MTPP.getProxy().registerRenderInformation();
		SlimevoidCore.console(MTPP.getModName(), "Naming items...");
		MTPPCore.addItemNames();
		SlimevoidCore.console(MTPP.getModName(), "Registering recipes...");
		MTPPCore.addRecipes();
	}

	/*
	 * public static int getDamageValue(IBlockAccess world, int x, int y, int z)
	 * { int meta = world.getBlockMetadata(x, y, z); int damage = meta >> 2;
	 * return damage; }
	 */

	public static int getDamageValue(IBlockAccess world, int x, int y, int z) {
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		if (tileentity != null) {
			if (tileentity instanceof TileEntityMTPPlate) {
				TileEntityMTPPlate tileentitymtpplate = (TileEntityMTPPlate) tileentity;
				return tileentitymtpplate.getTextureValue();
			}
		}
		return 1000;
	}
}
