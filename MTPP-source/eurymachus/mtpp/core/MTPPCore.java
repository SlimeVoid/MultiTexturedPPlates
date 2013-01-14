package eurymachus.mtpp.core;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.block.EnumMobType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
import slimevoid.lib.ICommonProxy;
import cpw.mods.fml.common.registry.GameRegistry;
import eurymachus.mtpp.blocks.BlockMTPPlate;
import eurymachus.mtpp.items.ItemMTPPlate;
import eurymachus.mtpp.tileentities.TileEntityMTPPlate;

public class MTPPCore {
	public static String version = "v1.2";
	public static File configFile;
	public static Configuration configuration;
	public static boolean initialized = false;

	public static void initialize(ICommonProxy proxy) {
		if (initialized)
			return;
		initialized = true;
		MTPPInit.initialize(proxy);
	}

	public static void addItems() {
		MTPPBlocks.mtPPlate.id = configurationProperties();
		MTPPBlocks.mtPPlate.me = (new BlockMTPPlate(
				MTPPBlocks.mtPPlate.id,
					TileEntityMTPPlate.class,
					0.5F,
					Block.soundStoneFootstep,
					true,
					true,
					"mtPPlate"));
		GameRegistry.registerTileEntity(TileEntityMTPPlate.class, "mtPPlate");
		for (MTPPItemPPlates pplate : MTPPItemPPlates.values()) {
			pplate.me = new ItemStack(MTPPBlocks.mtPPlate.me, 1, pplate.stackID);
		}
	}

	public static void registerBlocks() {
		for (MTPPBlocks block : MTPPBlocks.values()) {
			if (block != null && block.me != null) {
				GameRegistry.registerBlock(block.me, ItemMTPPlate.class, block.name);
			}
		}
		ModLoader.addName(Block.pressurePlateStone, "Stone Pressure Plate");
	}

	public static void addItemNames() {
		for (MTPPItemPPlates pplate : MTPPItemPPlates.values()) {
			if (pplate != null && pplate.me != null && pplate.name != null && !pplate.name
					.isEmpty()) {
				ModLoader.addName(pplate.me, pplate.name);
			}
		}
	}

	public static void addRecipes() {

		GameRegistry.addRecipe(MTPPItemPPlates.oakPlank.me, new Object[] {
				"XX",
				Character.valueOf('X'),
				new ItemStack(Block.planks, 1, 0) });

		GameRegistry.addRecipe(MTPPItemPPlates.sprucePlank.me, new Object[] {
				"XX",
				Character.valueOf('X'),
				new ItemStack(Block.planks, 1, 1) });

		GameRegistry.addRecipe(MTPPItemPPlates.birchPlank.me, new Object[] {
				"XX",
				Character.valueOf('X'),
				new ItemStack(Block.planks, 1, 2) });

		GameRegistry.addRecipe(MTPPItemPPlates.junglePlank.me, new Object[] {
				"XX",
				Character.valueOf('X'),
				new ItemStack(Block.planks, 1, 3) });

		GameRegistry.addRecipe(MTPPItemPPlates.polishedStone.me, new Object[] {
				"XX",
				Character.valueOf('X'),
				Block.stoneSingleSlab });

		GameRegistry.addRecipe(MTPPItemPPlates.cobbleStone.me, new Object[] {
				"XX",
				Character.valueOf('X'),
				Block.cobblestone });

		GameRegistry.addRecipe(MTPPItemPPlates.iron.me, new Object[] {
				"X",
				"Y",
				"X",
				Character.valueOf('X'),
				Item.ingotIron,
				Character.valueOf('Y'),
				Block.pressurePlateStone });

		GameRegistry.addRecipe(MTPPItemPPlates.gold.me, new Object[] {
				"X",
				"Y",
				"X",
				Character.valueOf('X'),
				Item.ingotGold,
				Character.valueOf('Y'),
				MTPPItemPPlates.iron.me });

		GameRegistry.addRecipe(MTPPItemPPlates.diamond.me, new Object[] {
				"X",
				"Y",
				"X",
				Character.valueOf('X'),
				Item.diamond,
				Character.valueOf('Y'),
				MTPPItemPPlates.gold.me });

		FurnaceRecipes.smelting().addSmelting(
				MTPPBlocks.mtPPlate.id,
				MTPPItemPPlates.iron.stackID,
				new ItemStack(Item.ingotIron, 2),
				1);
		FurnaceRecipes.smelting().addSmelting(
				MTPPBlocks.mtPPlate.id,
				MTPPItemPPlates.gold.stackID,
				new ItemStack(Item.ingotGold, 2),
				2);
		FurnaceRecipes.smelting().addSmelting(
				MTPPBlocks.mtPPlate.id,
				MTPPItemPPlates.diamond.stackID,
				new ItemStack(Item.diamond, 2),
				3);
	}

	public static int configurationProperties() {
		configuration.load();
		MTPPBlocks.mtPPlate.id = Integer.parseInt(configuration.get(
				Configuration.CATEGORY_BLOCK,
				"mtPPlate",
				72).value);
		MTPPBlocks.mtPPlate.name = "Multi-Textured Pressure Plate";
		MTPPItemPPlates.topplaceholder.name = "Top";
		MTPPItemPPlates.bottomplaceholder.name = "Bottom";
		MTPPItemPPlates.iron.name = "Iron-Clad Pressure Plate";
		MTPPItemPPlates.iron.stackID = 2;
		MTPPItemPPlates.iron.setTextureIndex(22);
		MTPPItemPPlates.iron.setTriggerType(EnumMobType.mobs);
		MTPPItemPPlates.iron.setBlockHardness(2.5F);
		MTPPItemPPlates.gold.name = "Gold-Plated Pressure Plate";
		MTPPItemPPlates.gold.stackID = 3;
		MTPPItemPPlates.gold.setTextureIndex(23);
		MTPPItemPPlates.gold.setTriggerType(EnumMobType.mobs);
		MTPPItemPPlates.gold.setBlockHardness(1.5F);
		MTPPItemPPlates.diamond.name = "Diamond-Lathered Pressure Plate";
		MTPPItemPPlates.diamond.stackID = 4;
		MTPPItemPPlates.diamond.setTextureIndex(24);
		MTPPItemPPlates.diamond.setTriggerType(EnumMobType.mobs);
		MTPPItemPPlates.diamond.setBlockHardness(2.5F);
		MTPPItemPPlates.oakPlank.name = "Oak Wood Pressure Plate";
		MTPPItemPPlates.oakPlank.stackID = 5;
		MTPPItemPPlates.oakPlank.setTextureIndex(4);
		MTPPItemPPlates.oakPlank.setTriggerType(EnumMobType.everything);
		MTPPItemPPlates.oakPlank.setBlockHardness(0.5F);
		MTPPItemPPlates.sprucePlank.name = "Spruce Wood Pressure Plate";
		MTPPItemPPlates.sprucePlank.stackID = 6;
		MTPPItemPPlates.sprucePlank.setTextureIndex(198);
		MTPPItemPPlates.sprucePlank.setTriggerType(EnumMobType.everything);
		MTPPItemPPlates.sprucePlank.setBlockHardness(0.5F);
		MTPPItemPPlates.birchPlank.name = "Birch Wood Pressure Plate";
		MTPPItemPPlates.birchPlank.stackID = 7;
		MTPPItemPPlates.birchPlank.setTextureIndex(214);
		MTPPItemPPlates.birchPlank.setTriggerType(EnumMobType.everything);
		MTPPItemPPlates.birchPlank.setBlockHardness(0.5F);
		MTPPItemPPlates.junglePlank.name = "Jungle Wood Pressure Plate";
		MTPPItemPPlates.junglePlank.stackID = 8;
		MTPPItemPPlates.junglePlank.setTextureIndex(199);
		MTPPItemPPlates.junglePlank.setTriggerType(EnumMobType.everything);
		MTPPItemPPlates.junglePlank.setBlockHardness(0.5F);
		MTPPItemPPlates.polishedStone.name = "Polished Stone Pressure Plate";
		MTPPItemPPlates.polishedStone.stackID = 9;
		MTPPItemPPlates.polishedStone.setTextureIndex(6);
		MTPPItemPPlates.polishedStone.setTriggerType(EnumMobType.mobs);
		MTPPItemPPlates.polishedStone.setBlockHardness(1.0F);
		MTPPItemPPlates.cobbleStone.name = "Cobblestone Pressure Plate";
		MTPPItemPPlates.cobbleStone.stackID = 10;
		MTPPItemPPlates.cobbleStone.setTextureIndex(16);
		MTPPItemPPlates.cobbleStone.setTriggerType(EnumMobType.mobs);
		MTPPItemPPlates.cobbleStone.setBlockHardness(1.0F);
		configuration.save();
		return MTPPBlocks.mtPPlate.id;
	}
}
