package ru.gameengine.GamesAPI.JS.Event;

public class TickEvent implements EventJS {
    public int ticks;
    public TickEvent(int ticks) {this.ticks = ticks;}

    @Override
    public String getName() {return "tick";}
}
