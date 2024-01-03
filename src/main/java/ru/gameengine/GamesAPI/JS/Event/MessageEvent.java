package ru.gameengine.GamesAPI.JS.Event;

public class MessageEvent implements EventJS {
    public String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    @Override
    public String getName() {return "message";}
}
