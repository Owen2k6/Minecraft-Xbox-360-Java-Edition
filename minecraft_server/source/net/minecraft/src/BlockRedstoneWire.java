package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BlockRedstoneWire extends Block {
	private boolean wiresProvidePower = true;
	private Set field_21032_b = new HashSet();

	public BlockRedstoneWire(int var1, int var2) {
		super(var1, var2, Material.circuits);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F / 16.0F, 1.0F);
	}

	public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
		return this.blockIndexInTexture;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return null;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean func_28025_b() {
		return false;
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return var1.isBlockOpaqueCube(var2, var3 - 1, var4);
	}

	private void updateAndPropagateCurrentStrength(World var1, int var2, int var3, int var4) {
		this.func_21031_a(var1, var2, var3, var4, var2, var3, var4);
		ArrayList var5 = new ArrayList(this.field_21032_b);
		this.field_21032_b.clear();

		for(int var6 = 0; var6 < var5.size(); ++var6) {
			ChunkPosition var7 = (ChunkPosition)var5.get(var6);
			var1.notifyBlocksOfNeighborChange(var7.x, var7.y, var7.z, this.blockID);
		}

	}

	private void func_21031_a(World var1, int var2, int var3, int var4, int var5, int var6, int var7) {
		int var8 = var1.getBlockMetadata(var2, var3, var4);
		int var9 = 0;
		this.wiresProvidePower = false;
		boolean var10 = var1.isBlockIndirectlyGettingPowered(var2, var3, var4);
		this.wiresProvidePower = true;
		int var11;
		int var12;
		int var13;
		if(var10) {
			var9 = 15;
		} else {
			for(var11 = 0; var11 < 4; ++var11) {
				var12 = var2;
				var13 = var4;
				if(var11 == 0) {
					var12 = var2 - 1;
				}

				if(var11 == 1) {
					++var12;
				}

				if(var11 == 2) {
					var13 = var4 - 1;
				}

				if(var11 == 3) {
					++var13;
				}

				if(var12 != var5 || var3 != var6 || var13 != var7) {
					var9 = this.getMaxCurrentStrength(var1, var12, var3, var13, var9);
				}

				if(var1.isBlockOpaqueCube(var12, var3, var13) && !var1.isBlockOpaqueCube(var2, var3 + 1, var4)) {
					if(var12 != var5 || var3 + 1 != var6 || var13 != var7) {
						var9 = this.getMaxCurrentStrength(var1, var12, var3 + 1, var13, var9);
					}
				} else if(!var1.isBlockOpaqueCube(var12, var3, var13) && (var12 != var5 || var3 - 1 != var6 || var13 != var7)) {
					var9 = this.getMaxCurrentStrength(var1, var12, var3 - 1, var13, var9);
				}
			}

			if(var9 > 0) {
				--var9;
			} else {
				var9 = 0;
			}
		}

		if(var8 != var9) {
			var1.editingBlocks = true;
			var1.setBlockMetadataWithNotify(var2, var3, var4, var9);
			var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
			var1.editingBlocks = false;

			for(var11 = 0; var11 < 4; ++var11) {
				var12 = var2;
				var13 = var4;
				int var14 = var3 - 1;
				if(var11 == 0) {
					var12 = var2 - 1;
				}

				if(var11 == 1) {
					++var12;
				}

				if(var11 == 2) {
					var13 = var4 - 1;
				}

				if(var11 == 3) {
					++var13;
				}

				if(var1.isBlockOpaqueCube(var12, var3, var13)) {
					var14 += 2;
				}

				boolean var15 = false;
				int var16 = this.getMaxCurrentStrength(var1, var12, var3, var13, -1);
				var9 = var1.getBlockMetadata(var2, var3, var4);
				if(var9 > 0) {
					--var9;
				}

				if(var16 >= 0 && var16 != var9) {
					this.func_21031_a(var1, var12, var3, var13, var2, var3, var4);
				}

				var16 = this.getMaxCurrentStrength(var1, var12, var14, var13, -1);
				var9 = var1.getBlockMetadata(var2, var3, var4);
				if(var9 > 0) {
					--var9;
				}

				if(var16 >= 0 && var16 != var9) {
					this.func_21031_a(var1, var12, var14, var13, var2, var3, var4);
				}
			}

			if(var8 == 0 || var9 == 0) {
				this.field_21032_b.add(new ChunkPosition(var2, var3, var4));
				this.field_21032_b.add(new ChunkPosition(var2 - 1, var3, var4));
				this.field_21032_b.add(new ChunkPosition(var2 + 1, var3, var4));
				this.field_21032_b.add(new ChunkPosition(var2, var3 - 1, var4));
				this.field_21032_b.add(new ChunkPosition(var2, var3 + 1, var4));
				this.field_21032_b.add(new ChunkPosition(var2, var3, var4 - 1));
				this.field_21032_b.add(new ChunkPosition(var2, var3, var4 + 1));
			}
		}

	}

	private void notifyWireNeighborsOfNeighborChange(World var1, int var2, int var3, int var4) {
		if(var1.getBlockId(var2, var3, var4) == this.blockID) {
			var1.notifyBlocksOfNeighborChange(var2, var3, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
		}
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		super.onBlockAdded(var1, var2, var3, var4);
		if(!var1.singleplayerWorld) {
			this.updateAndPropagateCurrentStrength(var1, var2, var3, var4);
			var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
			this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3, var4);
			this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3, var4);
			this.notifyWireNeighborsOfNeighborChange(var1, var2, var3, var4 - 1);
			this.notifyWireNeighborsOfNeighborChange(var1, var2, var3, var4 + 1);
			if(var1.isBlockOpaqueCube(var2 - 1, var3, var4)) {
				this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3 + 1, var4);
			} else {
				this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3 - 1, var4);
			}

			if(var1.isBlockOpaqueCube(var2 + 1, var3, var4)) {
				this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3 + 1, var4);
			} else {
				this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3 - 1, var4);
			}

			if(var1.isBlockOpaqueCube(var2, var3, var4 - 1)) {
				this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 + 1, var4 - 1);
			} else {
				this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 - 1, var4 - 1);
			}

			if(var1.isBlockOpaqueCube(var2, var3, var4 + 1)) {
				this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 + 1, var4 + 1);
			} else {
				this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 - 1, var4 + 1);
			}

		}
	}

	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
		super.onBlockRemoval(var1, var2, var3, var4);
		if(!var1.singleplayerWorld) {
			var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
			var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
			this.updateAndPropagateCurrentStrength(var1, var2, var3, var4);
			this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3, var4);
			this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3, var4);
			this.notifyWireNeighborsOfNeighborChange(var1, var2, var3, var4 - 1);
			this.notifyWireNeighborsOfNeighborChange(var1, var2, var3, var4 + 1);
			if(var1.isBlockOpaqueCube(var2 - 1, var3, var4)) {
				this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3 + 1, var4);
			} else {
				this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3 - 1, var4);
			}

			if(var1.isBlockOpaqueCube(var2 + 1, var3, var4)) {
				this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3 + 1, var4);
			} else {
				this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3 - 1, var4);
			}

			if(var1.isBlockOpaqueCube(var2, var3, var4 - 1)) {
				this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 + 1, var4 - 1);
			} else {
				this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 - 1, var4 - 1);
			}

			if(var1.isBlockOpaqueCube(var2, var3, var4 + 1)) {
				this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 + 1, var4 + 1);
			} else {
				this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 - 1, var4 + 1);
			}

		}
	}

	private int getMaxCurrentStrength(World var1, int var2, int var3, int var4, int var5) {
		if(var1.getBlockId(var2, var3, var4) != this.blockID) {
			return var5;
		} else {
			int var6 = var1.getBlockMetadata(var2, var3, var4);
			return var6 > var5 ? var6 : var5;
		}
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		if(!var1.singleplayerWorld) {
			int var6 = var1.getBlockMetadata(var2, var3, var4);
			boolean var7 = this.canPlaceBlockAt(var1, var2, var3, var4);
			if(!var7) {
				this.dropBlockAsItem(var1, var2, var3, var4, var6);
				var1.setBlockWithNotify(var2, var3, var4, 0);
			} else {
				this.updateAndPropagateCurrentStrength(var1, var2, var3, var4);
			}

			super.onNeighborBlockChange(var1, var2, var3, var4, var5);
		}
	}

	public int idDropped(int var1, Random var2) {
		return Item.redstone.shiftedIndex;
	}

	public boolean isIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5) {
		return !this.wiresProvidePower ? false : this.isPoweringTo(var1, var2, var3, var4, var5);
	}

	public boolean isPoweringTo(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		if(!this.wiresProvidePower) {
			return false;
		} else if(var1.getBlockMetadata(var2, var3, var4) == 0) {
			return false;
		} else if(var5 == 1) {
			return true;
		} else {
			boolean var6 = isPowerProviderOrWire(var1, var2 - 1, var3, var4) || !var1.isBlockOpaqueCube(var2 - 1, var3, var4) && isPowerProviderOrWire(var1, var2 - 1, var3 - 1, var4);
			boolean var7 = isPowerProviderOrWire(var1, var2 + 1, var3, var4) || !var1.isBlockOpaqueCube(var2 + 1, var3, var4) && isPowerProviderOrWire(var1, var2 + 1, var3 - 1, var4);
			boolean var8 = isPowerProviderOrWire(var1, var2, var3, var4 - 1) || !var1.isBlockOpaqueCube(var2, var3, var4 - 1) && isPowerProviderOrWire(var1, var2, var3 - 1, var4 - 1);
			boolean var9 = isPowerProviderOrWire(var1, var2, var3, var4 + 1) || !var1.isBlockOpaqueCube(var2, var3, var4 + 1) && isPowerProviderOrWire(var1, var2, var3 - 1, var4 + 1);
			if(!var1.isBlockOpaqueCube(var2, var3 + 1, var4)) {
				if(var1.isBlockOpaqueCube(var2 - 1, var3, var4) && isPowerProviderOrWire(var1, var2 - 1, var3 + 1, var4)) {
					var6 = true;
				}

				if(var1.isBlockOpaqueCube(var2 + 1, var3, var4) && isPowerProviderOrWire(var1, var2 + 1, var3 + 1, var4)) {
					var7 = true;
				}

				if(var1.isBlockOpaqueCube(var2, var3, var4 - 1) && isPowerProviderOrWire(var1, var2, var3 + 1, var4 - 1)) {
					var8 = true;
				}

				if(var1.isBlockOpaqueCube(var2, var3, var4 + 1) && isPowerProviderOrWire(var1, var2, var3 + 1, var4 + 1)) {
					var9 = true;
				}
			}

			return !var8 && !var7 && !var6 && !var9 && var5 >= 2 && var5 <= 5 ? true : (var5 == 2 && var8 && !var6 && !var7 ? true : (var5 == 3 && var9 && !var6 && !var7 ? true : (var5 == 4 && var6 && !var8 && !var9 ? true : var5 == 5 && var7 && !var8 && !var9)));
		}
	}

	public boolean canProvidePower() {
		return this.wiresProvidePower;
	}

	public static boolean isPowerProviderOrWire(IBlockAccess var0, int var1, int var2, int var3) {
		int var4 = var0.getBlockId(var1, var2, var3);
		return var4 == Block.redstoneWire.blockID ? true : (var4 == 0 ? false : Block.blocksList[var4].canProvidePower());
	}
}
