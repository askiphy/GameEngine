package ru.bananus.gameengine.Utils;

import java.io.Serializable;

public class SerializableRunnable implements Runnable, Serializable {
    private final Serializable innerRunnable;

    public SerializableRunnable(Serializable innerRunnable) {
        this.innerRunnable = innerRunnable;
    }

    @Override
    public void run() {
        if (innerRunnable instanceof Runnable) {
            ((Runnable) innerRunnable).run();
        } else {
            throw new IllegalStateException("innerRunnable is not a Runnable");
        }
    }
}
