package net.minecraft.src;

public class EntityFlying extends EntityLiving {
	public EntityFlying(World var1) {
		super(var1);
	}

	protected void fall(float var1) {
	}

	public void moveEntityWithHeading(float var1, float var2) {
		if(this.isInWater()) {
			this.moveFlying(var1, var2, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= (double)0.8F;
			this.motionY *= (double)0.8F;
			this.motionZ *= (double)0.8F;
		} else if(this.handleLavaMovement()) {
			this.moveFlying(var1, var2, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
		} else {
			float var3 = 0.91F;
			if(this.onGround) {
				var3 = 546.0F * 0.1F * 0.1F * 0.1F;
				int var4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
				if(var4 > 0) {
					var3 = Block.blocksList[var4].slipperiness * 0.91F;
				}
			}

			float var8 = 0.16277136F / (var3 * var3 * var3);
			this.moveFlying(var1, var2, this.onGround ? 0.1F * var8 : 0.02F);
			var3 = 0.91F;
			if(this.onGround) {
				var3 = 546.0F * 0.1F * 0.1F * 0.1F;
				int var5 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
				if(var5 > 0) {
					var3 = Block.blocksList[var5].slipperiness * 0.91F;
				}
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= (double)var3;
			this.motionY *= (double)var3;
			this.motionZ *= (double)var3;
		}

		this.field_705_Q = this.field_704_R;
		double var10 = this.posX - this.prevPosX;
		double var9 = this.posZ - this.prevPosZ;
		float var7 = MathHelper.sqrt_double(var10 * var10 + var9 * var9) * 4.0F;
		if(var7 > 1.0F) {
			var7 = 1.0F;
		}

		this.field_704_R += (var7 - this.field_704_R) * 0.4F;
		this.field_703_S += this.field_704_R;
	}

	public boolean isOnLadder() {
		return false;
	}
}
