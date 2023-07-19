package ru.bananus.gameengine.GamesAPI.data;

import ru.bananus.gameengine.GamesAPI.ActionEvent;

import java.util.function.Consumer;

public class Action {
    private Consumer<ActionPacketData> actionFunc;
    private ActionEvent event;

    public Action(Consumer<ActionPacketData> act, ActionEvent ev) {
        actionFunc = act;
        event = ev;
    }

    public Consumer<ActionPacketData> getActionFunc() {
        return actionFunc;
    }

    public ActionEvent getEvent() {
        return event;
    }
}
