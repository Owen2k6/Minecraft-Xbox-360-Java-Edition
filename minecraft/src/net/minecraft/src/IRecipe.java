package net.minecraft.src;

public interface IRecipe {
	boolean matches(InventoryCrafting var1);

	ItemStack getCraftingResult(InventoryCrafting var1);

	int getRecipeSize();

	ItemStack func_25117_b();
}
