package com.reasure.neoforge_tutorial.client.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.reasure.neoforge_tutorial.NeoforgeTutorial;
import com.reasure.neoforge_tutorial.block.entity.menu.custom.PedestalMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PedestalScreen extends AbstractContainerScreen<PedestalMenu> {
    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(NeoforgeTutorial.MODID, "textures/gui/pedestal/pedestal_gui.png");

    public PedestalScreen(PedestalMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        // 인벤토리가 그려질 위치 (인벤토리의 왼쪽 위의 위치)
        // width, height: 화면 크기
        // imageWidth, imageHeight: 인벤토리 크기
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        // imageWidth imageHeight: 이미지 파일 중, 인벤토리 영역의 가로 세로
        // textureWidth, textureHeight: 이미지 파일의 가로 세로 (기본값: 256 x 256)
        // uOffset, vOffset: 이미지 파일에서 인벤토리 영역의 시작(왼쪽 위) 위치
        // guiGraphics.blit(GUI_TEXTURE, x, y, uOffset, vOffset, imageWidth, imageHeight, textureWidth, textureHeight);
        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // super.render 순서
        // 1. renderBackground
        //     1 - 1. renderTransparentBackground (월드 화면 위에 투명한 까만색 렌더링)
        //     1 - 2. renderBg (인벤토리 이미지 렌더링)
        // 2. ContainerScreenEvent.Render.Background
        // 3. 슬롯 렌더링 (ex. 마우스가 가리키는 슬롯 아이라이트)
        // 4. renderLabels
        // 5. ContainerScreenEvent.Render.Foreground
        // 6. renderFloatingItem (ItemStack itemstack = this.draggingItem.isEmpty() ? this.menu.getCarried() : this.draggingItem;)
        // 7. renderFloatingItem (this.snapbackItem)
        // 참고: draggingItem이랑 snapbackItem은 터치스크린이 활성화 됐을때만. dragging은 옮기는 중인 아이템, snapback은 옮기다 취소되어 돌아가는 아이템
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
