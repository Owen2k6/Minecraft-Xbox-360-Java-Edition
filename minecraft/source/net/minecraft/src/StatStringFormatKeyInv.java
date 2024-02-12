package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class StatStringFormatKeyInv implements IStatStringFormat {
	final Minecraft field_27344_a;

	public StatStringFormatKeyInv(Minecraft var1) {
		this.field_27344_a = var1;
	}

	public String formatString(String var1) {
		return String.format(var1, new Object[]{Keyboard.getKeyName(this.field_27344_a.gameSettings.keyBindInventory.keyCode)});
	}
}
