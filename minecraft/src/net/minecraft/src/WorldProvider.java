package net.minecraft.src;

public abstract class WorldProvider {
	public World worldObj;
	public WorldChunkManager worldChunkMgr;
	public boolean isNether = false;
	public boolean isHellWorld = false;
	public boolean field_6478_e = false;
	public float[] lightBrightnessTable = new float[16];
	public int worldType = 0;
	private float[] colorsSunriseSunset = new float[4];

	public final void registerWorld(World var1) {
		this.worldObj = var1;
		this.registerWorldChunkManager();
		this.generateLightBrightnessTable();
	}

	protected void generateLightBrightnessTable() {
		float var1 = 0.05F;

		for(int var2 = 0; var2 <= 15; ++var2) {
			float var3 = 1.0F - (float)var2 / 15.0F;
			this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
		}

	}

	protected void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManager(this.worldObj);
	}

	public IChunkProvider getChunkProvider() {
		return new ChunkProviderGenerate(this.worldObj, this.worldObj.getRandomSeed());
	}

	public boolean canCoordinateBeSpawn(int var1, int var2) {
		int var3 = this.worldObj.getFirstUncoveredBlock(var1, var2);
		return var3 == Block.sand.blockID;
	}

	public float calculateCelestialAngle(long var1, float var3) {
		int var4 = (int)(var1 % 24000L);
		float var5 = ((float)var4 + var3) / 24000.0F - 0.25F;
		if(var5 < 0.0F) {
			++var5;
		}

		if(var5 > 1.0F) {
			--var5;
		}

		float var6 = var5;
		var5 = 1.0F - (float)((Math.cos((double)var5 * Math.PI) + 1.0D) / 2.0D);
		var5 = var6 + (var5 - var6) / 3.0F;
		return var5;
	}

	public float[] calcSunriseSunsetColors(float var1, float var2) {
		float var3 = 0.4F;
		float var4 = MathHelper.cos(var1 * (float)Math.PI * 2.0F) - 0.0F;
		float var5 = -0.0F;
		if(var4 >= var5 - var3 && var4 <= var5 + var3) {
			float var6 = (var4 - var5) / var3 * 0.5F + 0.5F;
			float var7 = 1.0F - (1.0F - MathHelper.sin(var6 * (float)Math.PI)) * 0.99F;
			var7 *= var7;
			this.colorsSunriseSunset[0] = var6 * 0.3F + 0.7F;
			this.colorsSunriseSunset[1] = var6 * var6 * 0.7F + 0.2F;
			this.colorsSunriseSunset[2] = var6 * var6 * 0.0F + 0.2F;
			this.colorsSunriseSunset[3] = var7;
			return this.colorsSunriseSunset;
		} else {
			return null;
		}
	}

	public Vec3D func_4096_a(float var1, float var2) {
		float var3 = MathHelper.cos(var1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;
		if(var3 < 0.0F) {
			var3 = 0.0F;
		}

		if(var3 > 1.0F) {
			var3 = 1.0F;
		}

		float var4 = 192.0F / 255.0F;
		float var5 = 216.0F / 255.0F;
		float var6 = 1.0F;
		var4 *= var3 * 0.94F + 0.06F;
		var5 *= var3 * 0.94F + 0.06F;
		var6 *= var3 * 0.91F + 0.09F;
		return Vec3D.createVector((double)var4, (double)var5, (double)var6);
	}

	public boolean canRespawnHere() {
		return true;
	}

	public static WorldProvider getProviderForDimension(int var0) {
		return (WorldProvider)(var0 == -1 ? new WorldProviderHell() : (var0 == 0 ? new WorldProviderSurface() : (var0 == 1 ? new WorldProviderSky() : null)));
	}

	public float getCloudHeight() {
		return 108.0F;
	}

	public boolean func_28112_c() {
		return true;
	}
}
