package com.example.canvastest;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import engine.Game;
import engine.assets.BulkLoader;

public class MainActivity extends AppCompatActivity {
    Game game;
    View gameDisplay;
    BulkLoader bulkLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        bulkLoader = new BulkLoader();

        BaloonEntity.initStatic(bulkLoader);

        bulkLoader.load(this);

        game = new Game();
        gameDisplay = game.initDisplay(this);

        setContentView(gameDisplay);

        gameDisplay.post(() -> {
            game.start();
            game.replaceScene(new BaloonScene());
        });
    }
}