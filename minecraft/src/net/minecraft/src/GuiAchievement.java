package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiAchievement extends Gui {
	private Minecraft theGame;
	private int achievementWindowWidth;
	private int achievementWindowHeight;
	private String field_25085_d;
	private String field_25084_e;
	private Achievement theAchievement;
	private long field_25083_f;
	private RenderItem itemRender;
	private boolean field_27103_i;

	public GuiAchievement(Minecraft var1) {
		this.theGame = var1;
		this.itemRender = new RenderItem();
	}

	public void queueTakenAchievement(Achievement var1) {
		this.field_25085_d = StatCollector.translateToLocal("achievement.get");
		this.field_25084_e = var1.statName;
		this.field_25083_f = System.currentTimeMillis();
		this.theAchievement = var1;
		this.field_27103_i = false;
	}

	public void queueAchievementInformation(Achievement var1) {
		this.field_25085_d = var1.statName;
		this.field_25084_e = var1.getDescription();
		this.field_25083_f = System.currentTimeMillis() - 2500L;
		this.theAchievement = var1;
		this.field_27103_i = true;
	}

	private void updateAchievementWindowScale() {
		GL11.glViewport(0, 0, this.theGame.displayWidth, this.theGame.displayHeight);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		this.achievementWindowWidth = this.theGame.displayWidth;
		this.achievementWindowHeight = this.theGame.displayHeight;
		ScaledResolution var1 = new ScaledResolution(this.theGame.gameSettings, this.theGame.displayWidth, this.theGame.displayHeight);
		this.achievementWindowWidth = var1.getScaledWidth();
		this.achievementWindowHeight = var1.getScaledHeight();
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, (double)this.achievementWindowWidth, (double)this.achievementWindowHeight, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
	}

	public void updateAchievementWindow() {
		if(Minecraft.hasPaidCheckTime > 0L) {
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			RenderHelper.disableStandardItemLighting();
			this.updateAchievementWindowScale();
			String var1 = "Minecraft Beta 1.6.6   Unlicensed Copy :(";
			String var2 = "(Or logged in from another location)";
			String var3 = "Purchase at minecraft.net";
			this.theGame.fontRenderer.drawStringWithShadow(var1, 2, 2, 16777215);
			this.theGame.fontRenderer.drawStringWithShadow(var2, 2, 11, 16777215);
			this.theGame.fontRenderer.drawStringWithShadow(var3, 2, 20, 16777215);
			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}

		if(this.theAchievement != null && this.field_25083_f != 0L) {
			double var8 = (double)(System.currentTimeMillis() - this.field_25083_f) / 3000.0D;
			if(this.field_27103_i || this.field_27103_i || var8 >= 0.0D && var8 <= 1.0D) {
				this.updateAchievementWindowScale();
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glDepthMask(false);
				double var9 = var8 * 2.0D;
				if(var9 > 1.0D) {
					var9 = 2.0D - var9;
				}

				var9 *= 4.0D;
				var9 = 1.0D - var9;
				if(var9 < 0.0D) {
					var9 = 0.0D;
				}

				var9 *= var9;
				var9 *= var9;
				int var5 = this.achievementWindowWidth - 160;
				int var6 = 0 - (int)(var9 * 36.0D);
				int var7 = this.theGame.renderEngine.getTexture("/achievement/bg.png");
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, var7);
				GL11.glDisable(GL11.GL_LIGHTING);
				this.drawTexturedModalRect(var5, var6, 96, 202, 160, 32);
				if(this.field_27103_i) {
					this.theGame.fontRenderer.func_27278_a(this.field_25084_e, var5 + 30, var6 + 7, 120, -1);
				} else {
					this.theGame.fontRenderer.drawString(this.field_25085_d, var5 + 30, var6 + 7, -256);
					this.theGame.fontRenderer.drawString(this.field_25084_e, var5 + 30, var6 + 18, -1);
				}

				GL11.glPushMatrix();
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				RenderHelper.enableStandardItemLighting();
				GL11.glPopMatrix();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glEnable(GL11.GL_COLOR_MATERIAL);
				GL11.glEnable(GL11.GL_LIGHTING);
				this.itemRender.renderItemIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, this.theAchievement.theItemStack, var5 + 8, var6 + 8);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDepthMask(true);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			} else {
				this.field_25083_f = 0L;
			}
		}
	}
}
