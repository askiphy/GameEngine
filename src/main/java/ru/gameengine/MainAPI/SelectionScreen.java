package ru.gameengine.MainAPI;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import ru.gameengine.Dialogue.Bench;
import ru.gameengine.Dialogue.DialogScreen;
import ru.gameengine.Utils.LogUtils;
import ru.gameengine.GameEngine;
import ru.gameengine.GamesAPI.Root;
import ru.gameengine.GamesAPI.Utils.Filters;
import ru.gameengine.Network.Network;
import ru.gameengine.Network.Packets.SEndDialogPacket;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectionScreen extends Screen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(GameEngine.MODID, "textures/gui/dialog_gui.png");
    List<Button> buttons = new ArrayList<>();
    int y = 50;
    //byte[] instance;

    public SelectionScreen() {
        super(new StringTextComponent("Selection Screen"));
    }

    @Override
    protected void init() {
        Path worldPath = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer()).getWorldPath(FolderName.ROOT);
        File worldFolder = worldPath.toFile();
        File storiesFolder = new File(worldFolder, "ge_games");
        File[] storyFolders = storiesFolder.listFiles(Filters.onlyDir);
        for (File story: storyFolders) {
            Button button = this.addButton(new Button(this.width / 2 - 75, this.height / 4 + y, 150, 20, new StringTextComponent("> " + story.getName()), (p_213021_1_) -> {
                Minecraft.getInstance().setScreen(null);
                Root.setActiveStory(story.getName());
                Root.reloadStories();
                Minecraft.getInstance().player.sendMessage(new StringTextComponent(TextFormatting.GREEN + "Loaded successfully!"), Minecraft.getInstance().player.getUUID());
            }));
            buttons.add(button);
            y+=35;
        }
        Button button = this.addButton(new Button(this.width / 2 - 100, this.height - 30, 200, 20, new StringTextComponent("Refresh stories"), (buttonWidget) -> {
            Root.reloadStories();
            Minecraft.getInstance().setScreen(null);
            Minecraft.getInstance().player.sendMessage(new StringTextComponent(TextFormatting.GREEN + LogUtils.udcStar + "Reloaded successfully!"), Minecraft.getInstance().player.getUUID());
        }));
        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        this.minecraft.getTextureManager().bind(BACKGROUND);
        this.blit(matrixStack, this.width / 2 -100, this.height / 2 - 100, 256, 256, 227, 168);
        drawCenteredString(matrixStack, this.font, new StringTextComponent("Выберите игру:"), this.width / 2, 85, 16777215);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
