package engine.entity;

import engine.Game;
import engine.event.GameEvent;
import engine.graphics.DrawQueue;

public interface Entity {
    default void init(Game game) {}
    default void tick(Game game, double dt) {}
    default void draw(Game game, DrawQueue queue) {}

    default boolean handle(Game game, GameEvent event) {
        return false;
    }
}
