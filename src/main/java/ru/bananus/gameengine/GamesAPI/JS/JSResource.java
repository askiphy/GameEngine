package ru.bananus.gameengine.GamesAPI.JS;

public abstract class JSResource {
    abstract public Object getNative();
    abstract String getResourceId();
    abstract boolean isClient();
}
