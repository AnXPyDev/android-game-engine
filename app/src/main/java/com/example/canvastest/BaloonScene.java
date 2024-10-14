package com.example.canvastest;

import engine.Game;
import engine.Scene;
import engine.entity.Entity;

public class BaloonScene implements Scene {
    @Override
    public void init(Game game) {
        game.entityManager.spawn(new BackgroundEntity());
        for (int i = 0; i < 20; i++) {
            game.entityManager.spawn(new BaloonEntity());
        }
    }

    @Override
    public void destroy() {

    }

    public static void replaceBaloon(Game game, Entity baloon) {
        game.entityManager.kill(baloon);
        game.entityManager.spawn(new BaloonEntity());
    }
}
