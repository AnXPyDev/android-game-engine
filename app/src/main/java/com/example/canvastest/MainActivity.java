package com.example.canvastest;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import engine.core.Engine;
import gameframework.assets.BulkLoader;
import engine.feature.TouchInputFeature;

public class MainActivity extends AppCompatActivity {
    private BulkLoader bulkLoader = new BulkLoader();

    private Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        Engine.initStatic();

        bulkLoader.load(this);

        engine = new Engine(this);

        engine.enableFeature(TouchInputFeature.class);
        engine.setEnabledPerformanceOverlay(false);

        engine.boot(this, new BaloonsGame());
    }
}