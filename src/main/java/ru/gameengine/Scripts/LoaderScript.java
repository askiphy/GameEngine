package ru.gameengine.Scripts;

import net.minecraftforge.common.MinecraftForge;

public class LoaderScript {
    public void load(Object Script) {
        MinecraftForge.EVENT_BUS.register(Script);
    }
}