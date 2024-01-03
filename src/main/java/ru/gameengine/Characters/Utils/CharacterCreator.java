package ru.gameengine.Characters.Utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import ru.gameengine.Characters.Character.CharacterEntity;
import ru.gameengine.Characters.CharacterInit;

public class CharacterCreator extends Screen {
    private final int xSize = 176;
    private final int ySize = 166;
    private int guiLeft, guiTop;
    private static CharacterEntity entity;
    public CharacterCreator(Entity entity) {
        super(new StringTextComponent("Character Creator"));
        CharacterCreator.entity = (CharacterEntity) entity;
    }

    TextFieldWidget texture;
    int y = 50;

    @Override
    protected void init() {
        super.init();
        texture = new TextFieldWidget(font, this.width / 2 - 75, 50, 200, 15, new StringTextComponent("Texture"));
        children.add(texture);
        Button button = this.addButton(new Button(this.width / 2 - 100, this.height - 30, 200, 20, new StringTextComponent("Sava Data"), (buttonWidget) -> {
            PlayerEntity player = Minecraft.getInstance().player;
            entity.setTexturePath(texture.getValue().toString());
            Minecraft.getInstance().setScreen(null);
        }));
        children.add(button);
    }

    @Override
    public void tick() {
        texture.tick();
        super.tick();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        texture.render(matrixStack, this.width / 2 - 75, this.height / 4 + 50, 1f);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
