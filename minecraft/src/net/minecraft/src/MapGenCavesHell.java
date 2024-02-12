package net.minecraft.src;

import java.util.Random;

public class MapGenCavesHell extends MapGenBase {
	protected void func_4129_a(int var1, int var2, byte[] var3, double var4, double var6, double var8) {
		this.func_4128_a(var1, var2, var3, var4, var6, var8, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
	}

	protected void func_4128_a(int var1, int var2, byte[] var3, double var4, double var6, double var8, float var10, float var11, float var12, int var13, int var14, double var15) {
		double var17 = (double)(var1 * 16 + 8);
		double var19 = (double)(var2 * 16 + 8);
		float var21 = 0.0F;
		float var22 = 0.0F;
		Random var23 = new Random(this.rand.nextLong());
		if(var14 <= 0) {
			int var24 = this.field_1306_a * 16 - 16;
			var14 = var24 - var23.nextInt(var24 / 4);
		}

		boolean var51 = false;
		if(var13 == -1) {
			var13 = var14 / 2;
			var51 = true;
		}

		int var25 = var23.nextInt(var14 / 2) + var14 / 4;

		for(boolean var26 = var23.nextInt(6) == 0; var13 < var14; ++var13) {
			double var27 = 1.5D + (double)(MathHelper.sin((float)var13 * (float)Math.PI / (float)var14) * var10 * 1.0F);
			double var29 = var27 * var15;
			float var31 = MathHelper.cos(var12);
			float var32 = MathHelper.sin(var12);
			var4 += (double)(MathHelper.cos(var11) * var31);
			var6 += (double)var32;
			var8 += (double)(MathHelper.sin(var11) * var31);
			if(var26) {
				var12 *= 0.92F;
			} else {
				var12 *= 0.7F;
			}

			var12 += var22 * 0.1F;
			var11 += var21 * 0.1F;
			var22 *= 0.9F;
			var21 *= 12.0F / 16.0F;
			var22 += (var23.nextFloat() - var23.nextFloat()) * var23.nextFloat() * 2.0F;
			var21 += (var23.nextFloat() - var23.nextFloat()) * var23.nextFloat() * 4.0F;
			if(!var51 && var13 == var25 && var10 > 1.0F) {
				this.func_4128_a(var1, var2, var3, var4, var6, var8, var23.nextFloat() * 0.5F + 0.5F, var11 - (float)Math.PI * 0.5F, var12 / 3.0F, var13, var14, 1.0D);
				this.func_4128_a(var1, var2, var3, var4, var6, var8, var23.nextFloat() * 0.5F + 0.5F, var11 + (float)Math.PI * 0.5F, var12 / 3.0F, var13, var14, 1.0D);
				return;
			}

			if(var51 || var23.nextInt(4) != 0) {
				double var33 = var4 - var17;
				double var35 = var8 - var19;
				double var37 = (double)(var14 - var13);
				double var39 = (double)(var10 + 2.0F + 16.0F);
				if(var33 * var33 + var35 * var35 - var37 * var37 > var39 * var39) {
					return;
				}

				if(var4 >= var17 - 16.0D - var27 * 2.0D && var8 >= var19 - 16.0D - var27 * 2.0D && var4 <= var17 + 16.0D + var27 * 2.0D && var8 <= var19 + 16.0D + var27 * 2.0D) {
					int var52 = MathHelper.floor_double(var4 - var27) - var1 * 16 - 1;
					int var34 = MathHelper.floor_double(var4 + var27) - var1 * 16 + 1;
					int var53 = MathHelper.floor_double(var6 - var29) - 1;
					int var36 = MathHelper.floor_double(var6 + var29) + 1;
					int var54 = MathHelper.floor_double(var8 - var27) - var2 * 16 - 1;
					int var38 = MathHelper.floor_double(var8 + var27) - var2 * 16 + 1;
					if(var52 < 0) {
						var52 = 0;
					}

					if(var34 > 16) {
						var34 = 16;
					}

					if(var53 < 1) {
						var53 = 1;
					}

					if(var36 > 120) {
						var36 = 120;
					}

					if(var54 < 0) {
						var54 = 0;
					}

					if(var38 > 16) {
						var38 = 16;
					}

					boolean var55 = false;

					int var40;
					int var43;
					for(var40 = var52; !var55 && var40 < var34; ++var40) {
						for(int var41 = var54; !var55 && var41 < var38; ++var41) {
							for(int var42 = var36 + 1; !var55 && var42 >= var53 - 1; --var42) {
								var43 = (var40 * 16 + var41) * 128 + var42;
								if(var42 >= 0 && var42 < 128) {
									if(var3[var43] == Block.lavaMoving.blockID || var3[var43] == Block.lavaStill.blockID) {
										var55 = true;
									}

									if(var42 != var53 - 1 && var40 != var52 && var40 != var34 - 1 && var41 != var54 && var41 != var38 - 1) {
										var42 = var53;
									}
								}
							}
						}
					}

					if(!var55) {
						for(var40 = var52; var40 < var34; ++var40) {
							double var56 = ((double)(var40 + var1 * 16) + 0.5D - var4) / var27;

							for(var43 = var54; var43 < var38; ++var43) {
								double var44 = ((double)(var43 + var2 * 16) + 0.5D - var8) / var27;
								int var46 = (var40 * 16 + var43) * 128 + var36;

								for(int var47 = var36 - 1; var47 >= var53; --var47) {
									double var48 = ((double)var47 + 0.5D - var6) / var29;
									if(var48 > -0.7D && var56 * var56 + var48 * var48 + var44 * var44 < 1.0D) {
										byte var50 = var3[var46];
										if(var50 == Block.netherrack.blockID || var50 == Block.dirt.blockID || var50 == Block.grass.blockID) {
											var3[var46] = 0;
										}
									}

									--var46;
								}
							}
						}

						if(var51) {
							break;
						}
					}
				}
			}
		}

	}

	protected void func_868_a(World var1, int var2, int var3, int var4, int var5, byte[] var6) {
		int var7 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(10) + 1) + 1);
		if(this.rand.nextInt(5) != 0) {
			var7 = 0;
		}

		for(int var8 = 0; var8 < var7; ++var8) {
			double var9 = (double)(var2 * 16 + this.rand.nextInt(16));
			double var11 = (double)this.rand.nextInt(128);
			double var13 = (double)(var3 * 16 + this.rand.nextInt(16));
			int var15 = 1;
			if(this.rand.nextInt(4) == 0) {
				this.func_4129_a(var4, var5, var6, var9, var11, var13);
				var15 += this.rand.nextInt(4);
			}

			for(int var16 = 0; var16 < var15; ++var16) {
				float var17 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
				float var18 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float var19 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
				this.func_4128_a(var4, var5, var6, var9, var11, var13, var19 * 2.0F, var17, var18, 0, 0, 0.5D);
			}
		}

	}
}
