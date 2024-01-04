package ru.gameengine.ScreenRender;

import net.minecraft.util.text.ITextComponent;

public class FadeInfo {
    public final long startTime;
    public final long duration;
    public final ITextComponent text;

    public FadeInfo(long startTime, long duration, ITextComponent text) {
        this.startTime = startTime;
        this.duration = duration;
        this.text = text;
    }
}