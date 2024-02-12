package net.minecraft.src;

import java.util.Random;

public class BlockDoor extends Block {
	protected BlockDoor(int var1, Material var2) {
		super(var1, var2);
		this.blockIndexInTexture = 97;
		if(var2 == Material.iron) {
			++this.blockIndexInTexture;
		}

		float var3 = 0.5F;
		float var4 = 1.0F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4, 0.5F + var3);
	}

	public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
		if(var1 != 0 && var1 != 1) {
			int var3 = this.getState(var2);
			if((var3 == 0 || var3 == 2) ^ var1 <= 3) {
				return this.blockIndexInTexture;
			} else {
				int var4 = var3 / 2 + (var1 & 1 ^ var3);
				var4 += (var2 & 4) / 4;
				int var5 = this.blockIndexInTexture - (var2 & 8) * 2;
				if((var4 & 1) != 0) {
					var5 = -var5;
				}

				return var5;
			}
		} else {
			return this.blockIndexInTexture;
		}
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 7;
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		this.setBlockBoundsBasedOnState(var1, var2, var3, var4);
		return super.getSelectedBoundingBoxFromPool(var1, var2, var3, var4);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		this.setBlockBoundsBasedOnState(var1, var2, var3, var4);
		return super.getCollisionBoundingBoxFromPool(var1, var2, var3, var4);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4) {
		this.setDoorRotation(this.getState(var1.getBlockMetadata(var2, var3, var4)));
	}

	public void setDoorRotation(int var1) {
		float var2 = 3.0F / 16.0F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
		if(var1 == 0) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
		}

		if(var1 == 1) {
			this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}

		if(var1 == 2) {
			this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
		}

		if(var1 == 3) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
		}

	}

	public void onBlockClicked(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		this.blockActivated(var1, var2, var3, var4, var5);
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		if(this.blockMaterial == Material.iron) {
			return true;
		} else {
			int var6 = var1.getBlockMetadata(var2, var3, var4);
			if((var6 & 8) != 0) {
				if(var1.getBlockId(var2, var3 - 1, var4) == this.blockID) {
					this.blockActivated(var1, var2, var3 - 1, var4, var5);
				}

				return true;
			} else {
				if(var1.getBlockId(var2, var3 + 1, var4) == this.blockID) {
					var1.setBlockMetadataWithNotify(var2, var3 + 1, var4, (var6 ^ 4) + 8);
				}

				var1.setBlockMetadataWithNotify(var2, var3, var4, var6 ^ 4);
				var1.markBlocksDirty(var2, var3 - 1, var4, var2, var3, var4);
				var1.func_28107_a(var5, 1003, var2, var3, var4, 0);
				return true;
			}
		}
	}

	public void openDoor(World var1, int var2, int var3, int var4, boolean var5) {
		int var6 = var1.getBlockMetadata(var2, var3, var4);
		if((var6 & 8) != 0) {
			if(var1.getBlockId(var2, var3 - 1, var4) == this.blockID) {
				this.openDoor(var1, var2, var3 - 1, var4, var5);
			}

		} else {
			boolean var7 = (var1.getBlockMetadata(var2, var3, var4) & 4) > 0;
			if(var7 != var5) {
				if(var1.getBlockId(var2, var3 + 1, var4) == this.blockID) {
					var1.setBlockMetadataWithNotify(var2, var3 + 1, var4, (var6 ^ 4) + 8);
				}

				var1.setBlockMetadataWithNotify(var2, var3, var4, var6 ^ 4);
				var1.markBlocksDirty(var2, var3 - 1, var4, var2, var3, var4);
				var1.func_28107_a((EntityPlayer)null, 1003, var2, var3, var4, 0);
			}
		}
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		int var6 = var1.getBlockMetadata(var2, var3, var4);
		if((var6 & 8) != 0) {
			if(var1.getBlockId(var2, var3 - 1, var4) != this.blockID) {
				var1.setBlockWithNotify(var2, var3, var4, 0);
			}

			if(var5 > 0 && Block.blocksList[var5].canProvidePower()) {
				this.onNeighborBlockChange(var1, var2, var3 - 1, var4, var5);
			}
		} else {
			boolean var7 = false;
			if(var1.getBlockId(var2, var3 + 1, var4) != this.blockID) {
				var1.setBlockWithNotify(var2, var3, var4, 0);
				var7 = true;
			}

			if(!var1.func_28100_h(var2, var3 - 1, var4)) {
				var1.setBlockWithNotify(var2, var3, var4, 0);
				var7 = true;
				if(var1.getBlockId(var2, var3 + 1, var4) == this.blockID) {
					var1.setBlockWithNotify(var2, var3 + 1, var4, 0);
				}
			}

			if(var7) {
				if(!var1.multiplayerWorld) {
					this.dropBlockAsItem(var1, var2, var3, var4, var6);
				}
			} else if(var5 > 0 && Block.blocksList[var5].canProvidePower()) {
				boolean var8 = var1.isBlockIndirectlyGettingPowered(var2, var3, var4) || var1.isBlockIndirectlyGettingPowered(var2, var3 + 1, var4);
				this.openDoor(var1, var2, var3, var4, var8);
			}
		}

	}

	public int idDropped(int var1, Random var2) {
		return (var1 & 8) != 0 ? 0 : (this.blockMaterial == Material.iron ? Item.doorSteel.shiftedIndex : Item.doorWood.shiftedIndex);
	}

	public MovingObjectPosition collisionRayTrace(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6) {
		this.setBlockBoundsBasedOnState(var1, var2, var3, var4);
		return super.collisionRayTrace(var1, var2, var3, var4, var5, var6);
	}

	public int getState(int var1) {
		return (var1 & 4) == 0 ? var1 - 1 & 3 : var1 & 3;
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return var3 >= 127 ? false : var1.func_28100_h(var2, var3 - 1, var4) && super.canPlaceBlockAt(var1, var2, var3, var4) && super.canPlaceBlockAt(var1, var2, var3 + 1, var4);
	}

	public static boolean isOpen(int var0) {
		return (var0 & 4) != 0;
	}
}
