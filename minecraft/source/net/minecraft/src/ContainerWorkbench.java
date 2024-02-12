package net.minecraft.src;

public class ContainerWorkbench extends Container {
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public IInventory craftResult = new InventoryCraftResult();
	private World field_20133_c;
	private int field_20132_h;
	private int field_20131_i;
	private int field_20130_j;

	public ContainerWorkbench(InventoryPlayer var1, World var2, int var3, int var4, int var5) {
		this.field_20133_c = var2;
		this.field_20132_h = var3;
		this.field_20131_i = var4;
		this.field_20130_j = var5;
		this.addSlot(new SlotCrafting(var1.player, this.craftMatrix, this.craftResult, 0, 124, 35));

		int var6;
		int var7;
		for(var6 = 0; var6 < 3; ++var6) {
			for(var7 = 0; var7 < 3; ++var7) {
				this.addSlot(new Slot(this.craftMatrix, var7 + var6 * 3, 30 + var7 * 18, 17 + var6 * 18));
			}
		}

		for(var6 = 0; var6 < 3; ++var6) {
			for(var7 = 0; var7 < 9; ++var7) {
				this.addSlot(new Slot(var1, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
			}
		}

		for(var6 = 0; var6 < 9; ++var6) {
			this.addSlot(new Slot(var1, var6, 8 + var6 * 18, 142));
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	public void onCraftMatrixChanged(IInventory var1) {
		this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix));
	}

	public void onCraftGuiClosed(EntityPlayer var1) {
		super.onCraftGuiClosed(var1);

		for(int var2 = 0; var2 < 9; ++var2) {
			ItemStack var3 = this.craftMatrix.getStackInSlot(var2);
			if(var3 != null) {
				var1.dropPlayerItem(var3);
			}
		}

	}

	public boolean isUsableByPlayer(EntityPlayer var1) {
		return this.field_20133_c.getBlockId(this.field_20132_h, this.field_20131_i, this.field_20130_j) != Block.workbench.blockID ? false : var1.getDistanceSq((double)this.field_20132_h + 0.5D, (double)this.field_20131_i + 0.5D, (double)this.field_20130_j + 0.5D) <= 64.0D;
	}

	public ItemStack getStackInSlot(int var1) {
		ItemStack var2 = null;
		Slot var3 = (Slot)this.slots.get(var1);
		if(var3 != null && var3.getHasStack()) {
			ItemStack var4 = var3.getStack();
			var2 = var4.copy();
			if(var1 == 0) {
				this.func_28125_a(var4, 10, 46, true);
			} else if(var1 >= 10 && var1 < 37) {
				this.func_28125_a(var4, 37, 46, false);
			} else if(var1 >= 37 && var1 < 46) {
				this.func_28125_a(var4, 10, 37, false);
			} else {
				this.func_28125_a(var4, 10, 46, false);
			}

			if(var4.stackSize == 0) {
				var3.putStack((ItemStack)null);
			} else {
				var3.onSlotChanged();
			}

			if(var4.stackSize == var2.stackSize) {
				return null;
			}

			var3.onPickupFromSlot(var4);
		}

		return var2;
	}
}
