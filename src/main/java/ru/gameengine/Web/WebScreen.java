package ru.gameengine.Web;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.java.games.input.Component;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.gameengine.GameEngine;

import java.io.IOException;

import static org.jsoup.nodes.Document.OutputSettings.Syntax.html;

public class WebScreen extends Screen {
    String url;

    public WebScreen(String url) {
        super(new StringTextComponent("Screen"));
        this.url = url;
    }

    @Override
    protected void init() {
        this.addButton(new Button(this.width / 2 - 100, this.height - 30, 200, 20, new StringTextComponent("Back"), (buttonWidget) -> this.minecraft.setScreen(null)));
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        Elements elements = Jsoup.parse(url).getElementsByTag("body").first().getAllElements();
        int y = 10;
        for (Element element : elements) {
            String tag = element.tagName();
            switch (tag) {
                case "h1":
                    drawCenteredString(matrices, this.font, element.text(), this.width / 2, 85, 16777215);
                    y += 20;
                    break;
                case "p":
                    drawCenteredString(matrices, this.font, element.text(), this.width / 2, 85, 16777215);
                    y += 10;
                    break;
                    /*
                case "img":
                    String src = element.attr("src");
                    assert this.minecraft != null;
                    this.minecraft.getTextureManager().bind(new ResourceLocation(GameEngine.MODID, src));
                    renderBackground(matrices);
                    y += 200;
                    break;

                     */
            }
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

}
