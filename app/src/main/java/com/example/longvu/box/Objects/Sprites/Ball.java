package com.example.longvu.box.Objects.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Ball {
    private Bitmap animation;
    private int xPos;
    private int yPos;
    private Rect sRectangle;
    private int fps;
    private int numFrames;
    private int currentFrame;
    private long frameTimer;
    private int spriteHeight;
    private int spriteWidth;

    public Ball() {
        sRectangle = new Rect(0, 0, 0, 0);
        frameTimer = 0;
        currentFrame = 0;
        xPos = 80;
        yPos = 200;
    }

    public void Initialize(Bitmap bitmap, int height, int width, int fps, int frameCount) {
        this.animation = bitmap;
        this.spriteHeight = height;
        this.spriteWidth = width;
        this.sRectangle.top = 0;
        this.sRectangle.bottom = spriteHeight;
        this.sRectangle.left = 0;
        this.sRectangle.right = spriteWidth;
        this.fps = 1000 / fps;
        this.numFrames = frameCount;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setXPos(int value) {
        xPos = value;
    }

    public void setYPos(int value) {
        yPos = value;
    }

    public void Update(long gameTime) {
        if( gameTime > frameTimer + fps) {
            frameTimer = gameTime;
            currentFrame += 1;

            if( currentFrame >= numFrames ) {
                currentFrame = 0;
            }

            sRectangle.left = currentFrame * spriteWidth;
            sRectangle.right = sRectangle.left + spriteWidth;
        }
    }

    public void draw(Canvas canvas) {
        //canvas.drawColor(Color.argb(255, 26, 128, 182));

        Rect dest = new Rect(getXPos(), getYPos(), getXPos() + spriteWidth,
                getYPos() + spriteHeight);

        Paint resetPaint = new Paint();
        resetPaint.setColor( Color.argb(255,  26, 128, 182) );
        canvas.drawRect( dest, resetPaint );

        canvas.drawBitmap(animation, sRectangle, dest, null);
    }
}
