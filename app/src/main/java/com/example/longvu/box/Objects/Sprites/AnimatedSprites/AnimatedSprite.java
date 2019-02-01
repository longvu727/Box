package com.example.longvu.box.Objects.Sprites.AnimatedSprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.longvu.box.Configs.SpriteConfig;
import com.example.longvu.box.DrawablePanel;
import com.example.longvu.box.Objects.Position;
import com.example.longvu.box.Objects.Sprites.Sprite;
import com.example.longvu.box.Objects.Sprites.MotherSprite;
import com.example.longvu.box.Objects.Velocity;

import java.util.Map;


public class AnimatedSprite extends Sprite {

    public static AnimatedSprite createSpriteFromFactory( Resources res, String packageName, Map spriteElement ) {
        String spriteId = SpriteConfig.spriteElementChain( (Map)spriteElement, new String[]{"id", "text"} );

        if( spriteId.equals("bob") ) {
            return new BobAnimatedSprite(res, packageName, spriteElement);
        }
        else if( spriteId.equals("fireball") ) {
            return new FireballAnimatedSprite(res, packageName, spriteElement);
        }
        else {
            return new AnimatedSprite(res, packageName, spriteElement);
        }
    }

    public AnimatedSprite(Resources res, String packageName, Map spriteElement) {
        super(res, packageName, spriteElement);

        Map bitmapElement = (Map)spriteElement.get("bitmap");

        int bitmapId = res.getIdentifier(
            ((Map)bitmapElement.get("name")).get("text").toString(),
            ((Map)bitmapElement.get("type")).get("text").toString(),
            packageName
        );

        Bitmap bitmap = BitmapFactory.decodeResource( res, bitmapId );
        Double fps = Double.parseDouble(  ( (Map)spriteElement.get("framepersec") ).get("text").toString()  );

        int height = Integer.parseInt(  ( (Map)spriteElement.get("height") ).get("text").toString()  );
        int width = Integer.parseInt(  ( (Map)spriteElement.get("width") ).get("text").toString()  );
        int frameCount = Integer.parseInt(  ( (Map)spriteElement.get("framecount") ).get("text").toString()  );

        Map position = (Map)spriteElement.get("position");
        int xPos = Integer.parseInt(  ( (Map)position.get("x") ).get("text").toString()  );
        int yPos = Integer.parseInt(  ( (Map)position.get("y") ).get("text").toString()  );

        Map bitmapPosition = (Map)bitmapElement.get("position");
        int xBitmapPos = Integer.parseInt(  ( (Map)bitmapPosition.get("x") ).get("text").toString()  );
        int yBitmapPos = Integer.parseInt(  ( (Map)bitmapPosition.get("y") ).get("text").toString()  );

        String id = ( (Map)spriteElement.get("id") ).get("text").toString();

        int speed = Integer.parseInt(
            SpriteConfig.spriteElementChain(
                spriteElement,
                new String[] {"velocity", "speed", "text"}
            )
        );

        int directionX = Integer.parseInt(
            SpriteConfig.spriteElementChain(
                spriteElement,
                new String[] {"velocity", "direction", "x", "text"}
            )
        );

        int directionY = Integer.parseInt(
            SpriteConfig.spriteElementChain(
                spriteElement,
                new String[] {"velocity", "direction", "y", "text"}
            )
        );

        this.animation = bitmap;
        this.spriteHeight = height;
        this.spriteWidth = width;

        this.sRectangle = new Rect(
            xBitmapPos,
            yBitmapPos,
            xBitmapPos + this.spriteWidth,
            yBitmapPos + this.spriteHeight
        );

        this.fps = 1000 / fps;
        this.numFrames = frameCount;
        this.position = new Position( xPos, yPos );
        this.velocity = new Velocity( speed, new Position(directionX, directionY) );
        this.id = id;
    }

    public void update(long gameTime, DrawablePanel panel, MotherSprite motherSprite) {
        super.update(gameTime, panel, motherSprite);

        if( gameTime > frameTimer + fps) {
            frameTimer = gameTime;
            currentFrame += 1;

            if( currentFrame >= numFrames ) {
                currentFrame = 0;
            }

            sRectangle.left = currentFrame * this.spriteWidth;
            sRectangle.right = sRectangle.left + this.spriteWidth;

            this.position.x += this.velocity.direction.x;
            this.position.y += this.velocity.direction.y;

            if( this.ttl > 0 ) {
                this.ttl--;
            }
            else if( this.ttl == 0 ) {
                this.X_X = true;
            }
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        Rect dest = new Rect(
            this.position.x,
            this.position.y,
            this.position.x + spriteWidth,
            this.position.y + spriteHeight
        );

        canvas.drawBitmap( this.animation, this.sRectangle, dest, null);
    }

    public boolean isCollided( float x, float y ) {
        super.isCollided(x, y);

        return
            x > this.position.x &&
            x < this.position.x + this.spriteWidth &&
            y > this.position.y &&
            y < this.position.y + this.spriteHeight;
    }

}
