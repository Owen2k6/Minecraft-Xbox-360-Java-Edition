package net.minecraft.src;

import java.util.Random;

public class EntitySheep extends EntityAnimal {
	public static final float[][] field_21071_a = new float[][]{{1.0F, 1.0F, 1.0F}, {0.95F, 0.7F, 0.2F}, {0.9F, 0.5F, 0.85F}, {0.6F, 0.7F, 0.95F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.7F, 0.8F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.6F, 0.7F}, {0.7F, 0.4F, 0.9F}, {0.2F, 0.4F, 0.8F}, {0.5F, 0.4F, 0.3F}, {0.4F, 0.5F, 0.2F}, {0.8F, 0.3F, 0.3F}, {0.1F, 0.1F, 0.1F}};

	public EntitySheep(World var1) {
		super(var1);
		this.texture = "/mob/sheep.png";
		this.setSize(0.9F, 1.3F);
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte)0));
	}

	public boolean attackEntityFrom(Entity var1, int var2) {
		if(!this.worldObj.singleplayerWorld && !this.func_21069_f_() && var1 instanceof EntityLiving) {
			this.setSheared(true);
			int var3 = 1 + this.rand.nextInt(3);

			for(int var4 = 0; var4 < var3; ++var4) {
				EntityItem var5 = this.entityDropItem(new ItemStack(Block.cloth.blockID, 1, this.getFleeceColor()), 1.0F);
				var5.motionY += (double)(this.rand.nextFloat() * 0.05F);
				var5.motionX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
				var5.motionZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
			}
		}

		return super.attackEntityFrom(var1, var2);
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
		var1.setBoolean("Sheared", this.func_21069_f_());
		var1.setByte("Color", (byte)this.getFleeceColor());
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
		this.setSheared(var1.getBoolean("Sheared"));
		this.setFleeceColor(var1.getByte("Color"));
	}

	protected String getLivingSound() {
		return "mob.sheep";
	}

	protected String getHurtSound() {
		return "mob.sheep";
	}

	protected String getDeathSound() {
		return "mob.sheep";
	}

	public int getFleeceColor() {
		return this.dataWatcher.getWatchableObjectByte(16) & 15;
	}

	public void setFleeceColor(int var1) {
		byte var2 = this.dataWatcher.getWatchableObjectByte(16);
		this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 240 | var1 & 15)));
	}

	public boolean func_21069_f_() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 16) != 0;
	}

	public void setSheared(boolean var1) {
		byte var2 = this.dataWatcher.getWatchableObjectByte(16);
		if(var1) {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 16)));
		} else {
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -17)));
		}

	}

	public static int func_21066_a(Random var0) {
		int var1 = var0.nextInt(100);
		return var1 < 5 ? 15 : (var1 < 10 ? 7 : (var1 < 15 ? 8 : (var1 < 18 ? 12 : (var0.nextInt(500) == 0 ? 6 : 0))));
	}
}
