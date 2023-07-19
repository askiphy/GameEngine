package ru.bananus.gameengine.MainAPI;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import ru.bananus.gameengine.FSC;
import ru.bananus.gameengine.Web.HTMLUtils;

import java.io.IOException;
import java.util.zip.ZipError;
import java.util.zip.ZipInputStream;

public class DownloadScreen extends Screen {
    public DownloadScreen() {
        super(new StringTextComponent("Download"));
    }

    TextFieldWidget url;
    int y = 50;

    @Override
    protected void init() {
        super.init();
        url = new TextFieldWidget(font, this.width / 2 - 75, 50, 200, 15, new StringTextComponent("URL"));
        url.setMaxLength(1000);
        children.add(url);
        Button button = this.addButton(new Button(this.width / 2 - 100, this.height - 30, 200, 20, new StringTextComponent("Download World"), (buttonWidget) -> {
            PlayerEntity player = Minecraft.getInstance().player;
            try {
                HTMLUtils.downloadZip(url.getValue().toString());
            } catch (Exception e) {
                if (e instanceof IOException) {
                    player.sendMessage(new StringTextComponent(TextFormatting.GREEN + FSC.udcTick + "Загрузка завершена!"), player.getUUID());
                } else {
                    player.sendMessage(new StringTextComponent(TextFormatting.RED + "Ошибка загрузки!"), player.getUUID());
                }
            }
            Minecraft.getInstance().setScreen(null);
        }));
        children.add(button);
    }

    @Override
    public void tick() {
        url.tick();
        super.tick();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        url.render(matrixStack, this.width / 2 - 75, this.height / 4 + 50, 1f);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

}
