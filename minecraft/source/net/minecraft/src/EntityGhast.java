package net.minecraft.src;

public class EntityGhast extends EntityFlying implements IMob {
	public int courseChangeCooldown = 0;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	private Entity targetedEntity = null;
	private int aggroCooldown = 0;
	public int prevAttackCounter = 0;
	public int attackCounter = 0;

	public EntityGhast(World var1) {
		super(var1);
		this.texture = "/mob/ghast.png";
		this.setSize(4.0F, 4.0F);
		this.isImmuneToFire = true;
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
	}

	public void onUpdate() {
		super.onUpdate();
		byte var1 = this.dataWatcher.getWatchableObjectByte(16);
		this.texture = var1 == 1 ? "/mob/ghast_fire.png" : "/mob/ghast.png";
	}

	protected void updatePlayerActionState() {
		if(!this.worldObj.multiplayerWorld && this.worldObj.difficultySetting == 0) {
			this.setEntityDead();
		}

		this.func_27021_X();
		this.prevAttackCounter = this.attackCounter;
		double var1 = this.waypointX - this.posX;
		double var3 = this.waypointY - this.posY;
		double var5 = this.waypointZ - this.posZ;
		double var7 = (double)MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5);
		if(var7 < 1.0D || var7 > 60.0D) {
			this.waypointX = this.posX + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.waypointY = this.posY + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.waypointZ = this.posZ + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
		}

		if(this.courseChangeCooldown-- <= 0) {
			this.courseChangeCooldown += this.rand.nextInt(5) + 2;
			if(this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, var7)) {
				this.motionX += var1 / var7 * 0.1D;
				this.motionY += var3 / var7 * 0.1D;
				this.motionZ += var5 / var7 * 0.1D;
			} else {
				this.waypointX = this.posX;
				this.waypointY = this.posY;
				this.waypointZ = this.posZ;
			}
		}

		if(this.targetedEntity != null && this.targetedEntity.isDead) {
			this.targetedEntity = null;
		}

		if(this.targetedEntity == null || this.aggroCooldown-- <= 0) {
			this.targetedEntity = this.worldObj.getClosestPlayerToEntity(this, 100.0D);
			if(this.targetedEntity != null) {
				this.aggroCooldown = 20;
			}
		}

		double var9 = 64.0D;
		if(this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < var9 * var9) {
			double var11 = this.targetedEntity.posX - this.posX;
			double var13 = this.targetedEntity.boundingBox.minY + (double)(this.targetedEntity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
			double var15 = this.targetedEntity.posZ - this.posZ;
			this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(var11, var15)) * 180.0F / (float)Math.PI;
			if(this.canEntityBeSeen(this.targetedEntity)) {
				if(this.attackCounter == 10) {
					this.worldObj.playSoundAtEntity(this, "mob.ghast.charge", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				}

				++this.attackCounter;
				if(this.attackCounter == 20) {
					this.worldObj.playSoundAtEntity(this, "mob.ghast.fireball", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
					EntityFireball var17 = new EntityFireball(this.worldObj, this, var11, var13, var15);
					double var18 = 4.0D;
					Vec3D var20 = this.getLook(1.0F);
					var17.posX = this.posX + var20.xCoord * var18;
					var17.posY = this.posY + (double)(this.height / 2.0F) + 0.5D;
					var17.posZ = this.posZ + var20.zCoord * var18;
					this.worldObj.entityJoinedWorld(var17);
					this.attackCounter = -40;
				}
			} else if(this.attackCounter > 0) {
				--this.attackCounter;
			}
		} else {
			this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI;
			if(this.attackCounter > 0) {
				--this.attackCounter;
			}
		}

		if(!this.worldObj.multiplayerWorld) {
			byte var21 = this.dataWatcher.getWatchableObjectByte(16);
			byte var12 = (byte)(this.attackCounter > 10 ? 1 : 0);
			if(var21 != var12) {
				this.dataWatcher.updateObject(16, Byte.valueOf(var12));
			}
		}

	}

	private boolean isCourseTraversable(double var1, double var3, double var5, double var7) {
		double var9 = (this.waypointX - this.posX) / var7;
		double var11 = (this.waypointY - this.posY) / var7;
		double var13 = (this.waypointZ - this.posZ) / var7;
		AxisAlignedBB var15 = this.boundingBox.copy();

		for(int var16 = 1; (double)var16 < var7; ++var16) {
			var15.offset(var9, var11, var13);
			if(this.worldObj.getCollidingBoundingBoxes(this, var15).size() > 0) {
				return false;
			}
		}

		return true;
	}

	protected String getLivingSound() {
		return "mob.ghast.moan";
	}

	protected String getHurtSound() {
		return "mob.ghast.scream";
	}

	protected String getDeathSound() {
		return "mob.ghast.death";
	}

	protected int getDropItemId() {
		return Item.gunpowder.shiftedIndex;
	}

	protected float getSoundVolume() {
		return 10.0F;
	}

	public boolean getCanSpawnHere() {
		return this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.worldObj.difficultySetting > 0;
	}

	public int getMaxSpawnedInChunk() {
		return 1;
	}
}
