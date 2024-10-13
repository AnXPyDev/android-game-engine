package com.example.canvastest;

import java.util.ArrayList;

public class EntityManager {
    public ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Entity> toSpawn = new ArrayList<>();
    private ArrayList<Entity> toKill = new ArrayList<>();

    void spawn(Entity entity) {
        toSpawn.add(entity);
    }

    void kill(Entity entity) {
        toKill.add(entity);
    }

    void tick(Game game) {
        for (Entity entity : toKill) {
            entities.remove(entity);
        }
        toKill.clear();
        for (Entity entity : toSpawn) {
            entity.spawn(game);
            entities.add(entity);
        }
        toSpawn.clear();
    }
}
