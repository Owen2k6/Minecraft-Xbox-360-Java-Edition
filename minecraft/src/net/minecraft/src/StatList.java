package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StatList {
	protected static Map field_25169_C = new HashMap();
	public static List field_25188_a = new ArrayList();
	public static List field_25187_b = new ArrayList();
	public static List field_25186_c = new ArrayList();
	public static List field_25185_d = new ArrayList();
	public static StatBase startGameStat = (new StatBasic(1000, StatCollector.translateToLocal("stat.startGame"))).func_27082_h().registerStat();
	public static StatBase createWorldStat = (new StatBasic(1001, StatCollector.translateToLocal("stat.createWorld"))).func_27082_h().registerStat();
	public static StatBase loadWorldStat = (new StatBasic(1002, StatCollector.translateToLocal("stat.loadWorld"))).func_27082_h().registerStat();
	public static StatBase joinMultiplayerStat = (new StatBasic(1003, StatCollector.translateToLocal("stat.joinMultiplayer"))).func_27082_h().registerStat();
	public static StatBase leaveGameStat = (new StatBasic(1004, StatCollector.translateToLocal("stat.leaveGame"))).func_27082_h().registerStat();
	public static StatBase minutesPlayedStat = (new StatBasic(1100, StatCollector.translateToLocal("stat.playOneMinute"), StatBase.field_27086_j)).func_27082_h().registerStat();
	public static StatBase distanceWalkedStat = (new StatBasic(2000, StatCollector.translateToLocal("stat.walkOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceSwumStat = (new StatBasic(2001, StatCollector.translateToLocal("stat.swimOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceFallenStat = (new StatBasic(2002, StatCollector.translateToLocal("stat.fallOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceClimbedStat = (new StatBasic(2003, StatCollector.translateToLocal("stat.climbOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceFlownStat = (new StatBasic(2004, StatCollector.translateToLocal("stat.flyOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceDoveStat = (new StatBasic(2005, StatCollector.translateToLocal("stat.diveOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceByMinecartStat = (new StatBasic(2006, StatCollector.translateToLocal("stat.minecartOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceByBoatStat = (new StatBasic(2007, StatCollector.translateToLocal("stat.boatOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceByPigStat = (new StatBasic(2008, StatCollector.translateToLocal("stat.pigOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase jumpStat = (new StatBasic(2010, StatCollector.translateToLocal("stat.jump"))).func_27082_h().registerStat();
	public static StatBase dropStat = (new StatBasic(2011, StatCollector.translateToLocal("stat.drop"))).func_27082_h().registerStat();
	public static StatBase damageDealtStat = (new StatBasic(2020, StatCollector.translateToLocal("stat.damageDealt"))).registerStat();
	public static StatBase damageTakenStat = (new StatBasic(2021, StatCollector.translateToLocal("stat.damageTaken"))).registerStat();
	public static StatBase deathsStat = (new StatBasic(2022, StatCollector.translateToLocal("stat.deaths"))).registerStat();
	public static StatBase mobKillsStat = (new StatBasic(2023, StatCollector.translateToLocal("stat.mobKills"))).registerStat();
	public static StatBase playerKillsStat = (new StatBasic(2024, StatCollector.translateToLocal("stat.playerKills"))).registerStat();
	public static StatBase fishCaughtStat = (new StatBasic(2025, StatCollector.translateToLocal("stat.fishCaught"))).registerStat();
	public static StatBase[] mineBlockStatArray = func_25153_a("stat.mineBlock", 16777216);
	public static StatBase[] field_25158_z;
	public static StatBase[] field_25172_A;
	public static StatBase[] field_25170_B;
	private static boolean field_25166_D;
	private static boolean field_25164_E;

	public static void func_27360_a() {
	}

	public static void func_25154_a() {
		field_25172_A = func_25155_a(field_25172_A, "stat.useItem", 16908288, 0, Block.blocksList.length);
		field_25170_B = func_25149_b(field_25170_B, "stat.breakItem", 16973824, 0, Block.blocksList.length);
		field_25166_D = true;
		func_25157_c();
	}

	public static void func_25151_b() {
		field_25172_A = func_25155_a(field_25172_A, "stat.useItem", 16908288, Block.blocksList.length, 32000);
		field_25170_B = func_25149_b(field_25170_B, "stat.breakItem", 16973824, Block.blocksList.length, 32000);
		field_25164_E = true;
		func_25157_c();
	}

	public static void func_25157_c() {
		if(field_25166_D && field_25164_E) {
			HashSet var0 = new HashSet();
			Iterator var1 = CraftingManager.getInstance().getRecipeList().iterator();

			while(var1.hasNext()) {
				IRecipe var2 = (IRecipe)var1.next();
				var0.add(Integer.valueOf(var2.func_25117_b().itemID));
			}

			var1 = FurnaceRecipes.smelting().getSmeltingList().values().iterator();

			while(var1.hasNext()) {
				ItemStack var4 = (ItemStack)var1.next();
				var0.add(Integer.valueOf(var4.itemID));
			}

			field_25158_z = new StatBase[32000];
			var1 = var0.iterator();

			while(var1.hasNext()) {
				Integer var5 = (Integer)var1.next();
				if(Item.itemsList[var5.intValue()] != null) {
					String var3 = StatCollector.translateToLocalFormatted("stat.craftItem", new Object[]{Item.itemsList[var5.intValue()].getStatName()});
					field_25158_z[var5.intValue()] = (new StatCrafting(16842752 + var5.intValue(), var3, var5.intValue())).registerStat();
				}
			}

			replaceAllSimilarBlocks(field_25158_z);
		}
	}

	private static StatBase[] func_25153_a(String var0, int var1) {
		StatBase[] var2 = new StatBase[256];

		for(int var3 = 0; var3 < 256; ++var3) {
			if(Block.blocksList[var3] != null && Block.blocksList[var3].getEnableStats()) {
				String var4 = StatCollector.translateToLocalFormatted(var0, new Object[]{Block.blocksList[var3].translateBlockName()});
				var2[var3] = (new StatCrafting(var1 + var3, var4, var3)).registerStat();
				field_25185_d.add((StatCrafting)var2[var3]);
			}
		}

		replaceAllSimilarBlocks(var2);
		return var2;
	}

	private static StatBase[] func_25155_a(StatBase[] var0, String var1, int var2, int var3, int var4) {
		if(var0 == null) {
			var0 = new StatBase[32000];
		}

		for(int var5 = var3; var5 < var4; ++var5) {
			if(Item.itemsList[var5] != null) {
				String var6 = StatCollector.translateToLocalFormatted(var1, new Object[]{Item.itemsList[var5].getStatName()});
				var0[var5] = (new StatCrafting(var2 + var5, var6, var5)).registerStat();
				if(var5 >= Block.blocksList.length) {
					field_25186_c.add((StatCrafting)var0[var5]);
				}
			}
		}

		replaceAllSimilarBlocks(var0);
		return var0;
	}

	private static StatBase[] func_25149_b(StatBase[] var0, String var1, int var2, int var3, int var4) {
		if(var0 == null) {
			var0 = new StatBase[32000];
		}

		for(int var5 = var3; var5 < var4; ++var5) {
			if(Item.itemsList[var5] != null && Item.itemsList[var5].isDamagable()) {
				String var6 = StatCollector.translateToLocalFormatted(var1, new Object[]{Item.itemsList[var5].getStatName()});
				var0[var5] = (new StatCrafting(var2 + var5, var6, var5)).registerStat();
			}
		}

		replaceAllSimilarBlocks(var0);
		return var0;
	}

	private static void replaceAllSimilarBlocks(StatBase[] var0) {
		replaceSimilarBlocks(var0, Block.waterStill.blockID, Block.waterMoving.blockID);
		replaceSimilarBlocks(var0, Block.lavaStill.blockID, Block.lavaStill.blockID);
		replaceSimilarBlocks(var0, Block.pumpkinLantern.blockID, Block.pumpkin.blockID);
		replaceSimilarBlocks(var0, Block.stoneOvenActive.blockID, Block.stoneOvenIdle.blockID);
		replaceSimilarBlocks(var0, Block.oreRedstoneGlowing.blockID, Block.oreRedstone.blockID);
		replaceSimilarBlocks(var0, Block.redstoneRepeaterActive.blockID, Block.redstoneRepeaterIdle.blockID);
		replaceSimilarBlocks(var0, Block.torchRedstoneActive.blockID, Block.torchRedstoneIdle.blockID);
		replaceSimilarBlocks(var0, Block.mushroomRed.blockID, Block.mushroomBrown.blockID);
		replaceSimilarBlocks(var0, Block.stairDouble.blockID, Block.stairSingle.blockID);
		replaceSimilarBlocks(var0, Block.grass.blockID, Block.dirt.blockID);
		replaceSimilarBlocks(var0, Block.tilledField.blockID, Block.dirt.blockID);
	}

	private static void replaceSimilarBlocks(StatBase[] var0, int var1, int var2) {
		if(var0[var1] != null && var0[var2] == null) {
			var0[var2] = var0[var1];
		} else {
			field_25188_a.remove(var0[var1]);
			field_25185_d.remove(var0[var1]);
			field_25187_b.remove(var0[var1]);
			var0[var1] = var0[var2];
		}
	}

	public static StatBase func_27361_a(int var0) {
		return (StatBase)field_25169_C.get(Integer.valueOf(var0));
	}

	static {
		AchievementList.func_27374_a();
		field_25166_D = false;
		field_25164_E = false;
	}
}
