package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AchievementMap {
	public static AchievementMap field_25134_a = new AchievementMap();
	private Map field_25133_b = new HashMap();

	private AchievementMap() {
		try {
			BufferedReader var1 = new BufferedReader(new InputStreamReader(AchievementMap.class.getResourceAsStream("/achievement/map.txt")));

			while(true) {
				String var2 = var1.readLine();
				if(var2 == null) {
					var1.close();
					break;
				}

				String[] var3 = var2.split(",");
				int var4 = Integer.parseInt(var3[0]);
				this.field_25133_b.put(Integer.valueOf(var4), var3[1]);
			}
		} catch (Exception var5) {
			var5.printStackTrace();
		}

	}

	public static String func_25132_a(int var0) {
		return (String)field_25134_a.field_25133_b.get(Integer.valueOf(var0));
	}
}
