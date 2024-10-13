package com.example.canvastest;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    CanvasView gameCanvas;
    GameThread gameThread;
    Game game;
    GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_main);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        gameManager = new GameManager() {
            @Override
            public void redraw() {
                gameCanvas.invalidate();
            }

            @Override
            public void draw(Canvas canvas) {
                game.draw(canvas);
            }

            @Override
            public Vector getSceneSize() {
                return new Vector(gameCanvas.getWidth(), gameCanvas.getHeight());
            }

            @Override
            public void postEvent(GameEvent event) {
                game.postEvent(event);
            }
        };

        gameCanvas = new CanvasView(this, gameManager);
        setContentView(gameCanvas);

        game = new Game(gameManager);
        gameThread = new GameThread(game);

        Game.initStatic(this);

        gameCanvas.post(() -> {
            game.init();
            gameThread.start();
        });
    }
}