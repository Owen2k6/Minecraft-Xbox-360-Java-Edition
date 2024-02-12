package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class TexturePackDefault extends TexturePackBase {
	private int texturePackName = -1;
	private BufferedImage texturePackThumbnail;

	public TexturePackDefault() {
		this.texturePackFileName = "Default";
		this.firstDescriptionLine = "The default look of Minecraft";

		try {
			this.texturePackThumbnail = ImageIO.read(TexturePackDefault.class.getResource("/pack.png"));
		} catch (IOException var2) {
			var2.printStackTrace();
		}

	}

	public void func_6484_b(Minecraft var1) {
		if(this.texturePackThumbnail != null) {
			var1.renderEngine.deleteTexture(this.texturePackName);
		}

	}

	public void bindThumbnailTexture(Minecraft var1) {
		if(this.texturePackThumbnail != null && this.texturePackName < 0) {
			this.texturePackName = var1.renderEngine.allocateAndSetupTexture(this.texturePackThumbnail);
		}

		if(this.texturePackThumbnail != null) {
			var1.renderEngine.bindTexture(this.texturePackName);
		} else {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, var1.renderEngine.getTexture("/gui/unknown_pack.png"));
		}

	}
}
