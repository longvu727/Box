package com.example.longvu.box.Objects.Sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.longvu.box.Configs.SpriteConfig;
import com.example.longvu.box.DrawablePanel;
import com.example.longvu.box.Objects.Position;
import com.example.longvu.box.Objects.Sprites.AnimatedSprites.AnimatedSprite;
import com.example.longvu.box.Objects.Velocity;

import java.util.Map;

public class Sprite {
    protected String id = "noId" + Math.random();
    protected Bitmap animation;
    protected Rect sRectangle;
    protected Double fps;
    protected int numFrames;
    protected int currentFrame;
    protected long frameTimer;

    public Velocity velocity;
    public boolean X_X = false;
    public Position position;
    public int spriteHeight;
    public int spriteWidth;
    public int ttl = -1 ;


    public Sprite(Resources res, String packageName, Map spriteElement) { }

    public static Sprite createSpriteFromFactory(Resources res, String packageName, Map spriteElement ) {
        String type = SpriteConfig.spriteElementChain( spriteElement, new String[]{"type", "text"} );
        Sprite sprite = null;

        if( type.equals("animated") ) {
            sprite = AnimatedSprite.createSpriteFromFactory(res, packageName, spriteElement);
        }

        return sprite;
    }

    public void moveLeft() { this.position.x -= this.velocity.speed; }
    public void moveRight() { this.position.x += this.velocity.speed; }

    public void moveUp() { this.position.y -= this.velocity.speed; }
    public void moveDown() { this.position.y += this.velocity.speed; }

    public void update(long gameTime, DrawablePanel panel, MotherSprite motherSprite){ }
    public void draw( Canvas canvas ) {}
    public boolean isCollided(float x, float y) { return false; }
}
