package net.minecraft.src;

import java.util.List;

public class EntityLightningBolt extends EntityWeatherEffect {
	private int field_27028_b;
	public long field_27029_a = 0L;
	private int field_27030_c;

	public EntityLightningBolt(World var1, double var2, double var4, double var6) {
		super(var1);
		this.setLocationAndAngles(var2, var4, var6, 0.0F, 0.0F);
		this.field_27028_b = 2;
		this.field_27029_a = this.rand.nextLong();
		this.field_27030_c = this.rand.nextInt(3) + 1;
		if(var1.difficultySetting >= 2 && var1.doChunksNearChunkExist(MathHelper.floor_double(var2), MathHelper.floor_double(var4), MathHelper.floor_double(var6), 10)) {
			int var8 = MathHelper.floor_double(var2);
			int var9 = MathHelper.floor_double(var4);
			int var10 = MathHelper.floor_double(var6);
			if(var1.getBlockId(var8, var9, var10) == 0 && Block.fire.canPlaceBlockAt(var1, var8, var9, var10)) {
				var1.setBlockWithNotify(var8, var9, var10, Block.fire.blockID);
			}

			for(var8 = 0; var8 < 4; ++var8) {
				var9 = MathHelper.floor_double(var2) + this.rand.nextInt(3) - 1;
				var10 = MathHelper.floor_double(var4) + this.rand.nextInt(3) - 1;
				int var11 = MathHelper.floor_double(var6) + this.rand.nextInt(3) - 1;
				if(var1.getBlockId(var9, var10, var11) == 0 && Block.fire.canPlaceBlockAt(var1, var9, var10, var11)) {
					var1.setBlockWithNotify(var9, var10, var11, Block.fire.blockID);
				}
			}
		}

	}

	public void onUpdate() {
		super.onUpdate();
		if(this.field_27028_b == 2) {
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
		}

		--this.field_27028_b;
		if(this.field_27028_b < 0) {
			if(this.field_27030_c == 0) {
				this.setEntityDead();
			} else if(this.field_27028_b < -this.rand.nextInt(10)) {
				--this.field_27030_c;
				this.field_27028_b = 1;
				this.field_27029_a = this.rand.nextLong();
				if(this.worldObj.doChunksNearChunkExist(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 10)) {
					int var1 = MathHelper.floor_double(this.posX);
					int var2 = MathHelper.floor_double(this.posY);
					int var3 = MathHelper.floor_double(this.posZ);
					if(this.worldObj.getBlockId(var1, var2, var3) == 0 && Block.fire.canPlaceBlockAt(this.worldObj, var1, var2, var3)) {
						this.worldObj.setBlockWithNotify(var1, var2, var3, Block.fire.blockID);
					}
				}
			}
		}

		if(this.field_27028_b >= 0) {
			double var6 = 3.0D;
			List var7 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBoxFromPool(this.posX - var6, this.posY - var6, this.posZ - var6, this.posX + var6, this.posY + 6.0D + var6, this.posZ + var6));

			for(int var4 = 0; var4 < var7.size(); ++var4) {
				Entity var5 = (Entity)var7.get(var4);
				var5.onStruckByLightning(this);
			}

			this.worldObj.field_27172_i = 2;
		}

	}

	protected void entityInit() {
	}

	protected void readEntityFromNBT(NBTTagCompound var1) {
	}

	protected void writeEntityToNBT(NBTTagCompound var1) {
	}

	public boolean isInRangeToRenderVec3D(Vec3D var1) {
		return this.field_27028_b >= 0;
	}
}
