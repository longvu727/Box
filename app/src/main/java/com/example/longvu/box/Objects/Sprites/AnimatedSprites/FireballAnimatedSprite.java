package com.example.longvu.box.Objects.Sprites.AnimatedSprites;

import android.content.res.Resources;

import com.example.longvu.box.DrawablePanel;
import com.example.longvu.box.Objects.Position;
import com.example.longvu.box.Objects.Sprites.Sprite;
import com.example.longvu.box.Objects.Sprites.MotherSprite;

import java.util.Map;

public class FireballAnimatedSprite extends AnimatedSprite {
    public FireballAnimatedSprite(Resources res, String packageName, Map spriteElement) {
        super(res, packageName, spriteElement);
    }

    public void update(long gameTime, DrawablePanel panel, MotherSprite motherSprite) {
        super.update(gameTime, panel, motherSprite);

        int x = this.position.x + this.spriteWidth;
        int y = this.position.y + this.spriteWidth;

        if( x > panel.getWidth() || y > panel.getHeight() ) {
            this.destruct(motherSprite);
        }
    }

    private void destruct( MotherSprite motherSprite) {
        this.X_X = true;
        //motherSprite.sanitize();

        Sprite fireballSprite = motherSprite.createSprite("explosion" );

        int x = this.position.x + this.spriteWidth - fireballSprite.spriteWidth;
        int y = this.position.y + this.spriteHeight - fireballSprite.spriteHeight;
        Position position = new Position( x, y );

        fireballSprite.position = position;
        fireballSprite.ttl = this.numFrames;
    }
}
