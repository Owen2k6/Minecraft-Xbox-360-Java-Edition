package net.minecraft.src;

public class WorldProviderSky extends WorldProvider {
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.5D, 0.0D);
		this.worldType = 1;
	}

	public IChunkProvider getChunkProvider() {
		return new ChunkProviderSky(this.worldObj, this.worldObj.getRandomSeed());
	}

	public float calculateCelestialAngle(long var1, float var3) {
		return 0.0F;
	}

	public float[] calcSunriseSunsetColors(float var1, float var2) {
		return null;
	}

	public Vec3D func_4096_a(float var1, float var2) {
		int var3 = 8421536;
		float var4 = MathHelper.cos(var1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;
		if(var4 < 0.0F) {
			var4 = 0.0F;
		}

		if(var4 > 1.0F) {
			var4 = 1.0F;
		}

		float var5 = (float)(var3 >> 16 & 255) / 255.0F;
		float var6 = (float)(var3 >> 8 & 255) / 255.0F;
		float var7 = (float)(var3 & 255) / 255.0F;
		var5 *= var4 * 0.94F + 0.06F;
		var6 *= var4 * 0.94F + 0.06F;
		var7 *= var4 * 0.91F + 0.09F;
		return Vec3D.createVector((double)var5, (double)var6, (double)var7);
	}

	public boolean func_28112_c() {
		return false;
	}

	public float getCloudHeight() {
		return 8.0F;
	}

	public boolean canCoordinateBeSpawn(int var1, int var2) {
		int var3 = this.worldObj.getFirstUncoveredBlock(var1, var2);
		return var3 == 0 ? false : Block.blocksList[var3].blockMaterial.getIsSolid();
	}
}
