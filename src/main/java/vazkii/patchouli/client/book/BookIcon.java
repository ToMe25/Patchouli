package vazkii.patchouli.client.book;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.common.base.Patchouli;
import vazkii.patchouli.common.util.ItemStackUtil;

public class BookIcon {
	private static final BookIcon EMPTY = new BookIcon(ItemStack.EMPTY);

	private final IconType type;
	private final ItemStack stack;
	private final ResourceLocation res;

	public static BookIcon from(String str) {
		if(str.endsWith(".png")) {
			return new BookIcon(new ResourceLocation(str));
		} else {
			try {
				ItemStack stack = ItemStackUtil.loadStackFromString(str);
				return new BookIcon(stack);
			} catch (Exception e) {
				Patchouli.LOGGER.warn("Invalid icon item stack: {}", e.getMessage());
				return EMPTY;
			}
		}
	}

	public BookIcon(ItemStack stack) {
		type = IconType.STACK;
		this.stack = stack;
		res = null;
	}

	public BookIcon(ResourceLocation res) {
		type = IconType.RESOURCE;
		stack = null;
		this.res = res;
	}

	public void render(int x, int y) {
		Minecraft mc = Minecraft.getInstance();
		switch(type) {
		case STACK:
			RenderHelper.enableGUIStandardItemLighting();
			mc.getItemRenderer().renderItemIntoGUI(stack, x, y);
			break;

		case RESOURCE:
			GlStateManager.color4f(1F, 1F, 1F, 1F);
			mc.textureManager.bindTexture(res);
			AbstractGui.blit(x, y, 0, 0, 16, 16, 16, 16);
			break;
		}
	}

	private enum IconType {
		STACK, RESOURCE
	}

}
