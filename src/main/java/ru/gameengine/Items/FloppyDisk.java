package ru.gameengine.Items;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import ru.gameengine.MainAPI.SelectionScreen;

public class FloppyDisk extends Item {
    public FloppyDisk(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        Minecraft.getInstance().setScreen(new SelectionScreen());
        return super.onItemUseFirst(stack, context);
    }
}
