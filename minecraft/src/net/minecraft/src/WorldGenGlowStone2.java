package net.minecraft.src;

import java.util.Random;

public class WorldGenGlowStone2 extends WorldGenerator {
	public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
		if(!var1.isAirBlock(var3, var4, var5)) {
			return false;
		} else if(var1.getBlockId(var3, var4 + 1, var5) != Block.netherrack.blockID) {
			return false;
		} else {
			var1.setBlockWithNotify(var3, var4, var5, Block.glowStone.blockID);

			for(int var6 = 0; var6 < 1500; ++var6) {
				int var7 = var3 + var2.nextInt(8) - var2.nextInt(8);
				int var8 = var4 - var2.nextInt(12);
				int var9 = var5 + var2.nextInt(8) - var2.nextInt(8);
				if(var1.getBlockId(var7, var8, var9) == 0) {
					int var10 = 0;

					for(int var11 = 0; var11 < 6; ++var11) {
						int var12 = 0;
						if(var11 == 0) {
							var12 = var1.getBlockId(var7 - 1, var8, var9);
						}

						if(var11 == 1) {
							var12 = var1.getBlockId(var7 + 1, var8, var9);
						}

						if(var11 == 2) {
							var12 = var1.getBlockId(var7, var8 - 1, var9);
						}

						if(var11 == 3) {
							var12 = var1.getBlockId(var7, var8 + 1, var9);
						}

						if(var11 == 4) {
							var12 = var1.getBlockId(var7, var8, var9 - 1);
						}

						if(var11 == 5) {
							var12 = var1.getBlockId(var7, var8, var9 + 1);
						}

						if(var12 == Block.glowStone.blockID) {
							++var10;
						}
					}

					if(var10 == 1) {
						var1.setBlockWithNotify(var7, var8, var9, Block.glowStone.blockID);
					}
				}
			}

			return true;
		}
	}
}
