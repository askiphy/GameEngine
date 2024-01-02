package ru.gameengine.GamesAPI;

import net.minecraft.entity.Entity;

import java.util.function.Supplier;

public class ActionEvent {
    public int type = 0;

    public static ActionEvent DEF() {
        return new ActionEvent();
    }

    public static ActionEvent MSG_SENT(String[] keyWords) {
        return new MessageSentActionEvent(keyWords);
    }

    public static ActionEvent DELAY(int ticks) {
        return new DelayActionEvent(ticks);
    }

    public static ActionEvent POSITIONED(Supplier<Entity> entity,Supplier<double[]> position,double radius) {
        return new PositionedActionEvent(entity,position,radius);
    }

    public static class MessageSentActionEvent extends ActionEvent {
        public final String[] keyWords;
        public int type = 1;
        public MessageSentActionEvent(String[] keyWords) { this.type = 1; super.type = 1; this.keyWords = keyWords; }
    }

    public static class DelayActionEvent extends ActionEvent {
        public final int ticks;
        public int type = 2;
        public DelayActionEvent(int ticks) { this.type = 2; super.type = 2; this.ticks = ticks; }
    }

    public static class PositionedActionEvent extends ActionEvent {
        public int type = 3;
        public Supplier<Entity> entity;
        public Supplier<double[]> position;
        public double radius;
        public PositionedActionEvent(Supplier<Entity> entity, Supplier<double[]> position, double radius) {
            this.type = 3; super.type = 3;
            this.entity = entity;
            this.position = position;
            this.radius = radius;
        }
    }
}
