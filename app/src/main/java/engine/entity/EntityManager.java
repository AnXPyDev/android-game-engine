package engine.entity;

import java.util.ArrayList;

import engine.Game;
import engine.event.GameEvent;
import engine.graphics.DrawQueue;

public class EntityManager {
    public ArrayList<Entity> entities = new ArrayList<>();
    public ArrayList<Entity> toSpawn = new ArrayList<>();
    public ArrayList<Entity> toKill = new ArrayList<>();

    public void tick(Game game, double dt) {
        for (Entity entity : entities) {
            entity.tick(game, dt);
        }

        for (Entity entity : toKill) {
            entities.remove(entity);
        }

        toKill.clear();

        for (Entity entity : toSpawn) {
            entity.init(game);
            entities.add(entity);
        }

        toSpawn.clear();
    }

    public void draw(Game game, DrawQueue queue) {
        for (Entity entity : entities) {
            entity.draw(game, queue);
        }
    }


    public void handleEvent(Game game, GameEvent event) {
        for (int i = entities.size() - 1; i >= 0; i--) {
            if (entities.get(i).handle(game, event)) {
                break;
            }
        }
    }

    public void spawn(Entity entity) {
        toSpawn.add(entity);
    }

    public void kill(Entity entity) {
        toKill.add(entity);
    }

}
