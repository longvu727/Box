package com.example.longvu.box;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.longvu.box.Objects.Position;
import com.example.longvu.box.Objects.Sprites.Background;
import com.example.longvu.box.Objects.Sprites.MotherSprite;
import com.example.longvu.box.Objects.Sprites.Sprite;

public class MainActivity extends AppCompatActivity {
    MotherSprite motherSprite;

    Background background = new Background();
    BoxPanel boxPanel;

    long lastClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        motherSprite = new MotherSprite( getResources(), getPackageName() );

        boxPanel = new BoxPanel(this);
        setContentView( boxPanel );
    }

    public boolean onKeyDown(int keycode, KeyEvent event){
        Log.d("onKeyDown", "" + keycode );

        Sprite bobSprite = motherSprite.getSprite( "bob" );

        switch( keycode ) {
            case KeyEvent.KEYCODE_A:
                bobSprite.moveLeft();
                break;
            case KeyEvent.KEYCODE_D:
                bobSprite.moveRight();
                break;
            case KeyEvent.KEYCODE_W:
                bobSprite.moveUp();
                break;
            case KeyEvent.KEYCODE_S:
                bobSprite.moveDown();
                break;
            case KeyEvent.KEYCODE_SPACE:
                int x = bobSprite.position.x + bobSprite.spriteWidth;
                int y = bobSprite.position.y + bobSprite.spriteHeight / 2;

                Position fireballPosition = new Position( x, y );
                Sprite fireball = motherSprite.createSprite( "fireball" );

                fireball.velocity.direction.x = 25;
                fireball.position = fireballPosition;
                break;
        }
        return true;
    }

    class BoxPanel extends DrawablePanel {

        public BoxPanel(Context context) {
            super(context);
        }

        public void onDraw(Canvas canvas ) {
            super.onDraw(canvas);

            MainActivity.this.background.draw(canvas);
            MainActivity.this.motherSprite.draw(canvas);
        }

        public void onInitialize() {
            MainActivity.this.background.initialize();
            MainActivity.this.motherSprite.initialize();
        }

        public void onUpdate( long gameTime ) {
            MainActivity.this.background.update(gameTime);
            MainActivity.this.motherSprite.update(gameTime, this);
        }

        public boolean onTouchEvent(MotionEvent event) {
            if( System.currentTimeMillis() - MainActivity.this.lastClick > 500 ) {
                MainActivity.this.lastClick = System.currentTimeMillis();
                synchronized( getHolder() ) {
                    for( Sprite sprite : MainActivity.this.motherSprite.getSprites() ) {
                        if( sprite.isCollided(event.getX(), event.getY()) ) {
                            MainActivity.this.motherSprite.getSprites().remove(sprite);
                            break;
                        }
                    }
                }
            }
            return true;
        }
    }
}