package com.example.longvu.box.Objects.Sprites;

import android.graphics.Canvas;
import android.graphics.Color;

public class Background {
    public Background() {}

    public void initialize() {}
    public void update(long gameTime) {}
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.argb(255, 50, 50, 50));
    }
}
