package com.example.longvu.box.Objects.Sprites;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;

import com.example.longvu.box.Configs.SpriteConfig;
import com.example.longvu.box.DrawablePanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class MotherSprite {
    private List<Sprite> sprites;

    private SpriteConfig spriteConfig;

    protected Resources res;
    protected String packageName;

    public MotherSprite(Resources res, String packageName ) {
        this.sprites = Collections.synchronizedList(new ArrayList<Sprite>());
        this.res = res;
        this.packageName = packageName;
    }

    public List<Sprite> getSprites() { return new CopyOnWriteArrayList<>(this.sprites); }

    public Sprite getSprite(String spriteId ) {
        for( Sprite sprite : this.getSprites()) {
            if( sprite.id.equals(spriteId) ) {
                return sprite;
            }
        }
        return null;
    }

    public Sprite createSprite(String spriteId) {
        Sprite sprite = null;

        Log.d("createSprite","SpriteSize: " + this.sprites.size() + " SpriteId: " + spriteId );

        for( Object spriteElement : this.spriteConfig.getSprites() ) {
            String id = SpriteConfig.spriteElementChain( (Map)spriteElement, new String[]{"id", "text"} );

            if( id.equals(spriteId) ) {
                sprite = Sprite.createSpriteFromFactory(this.res, this.packageName, (Map)spriteElement);

                if( sprite != null ){
                    this.sprites.add( sprite );
                }

                return sprite;
            }
        }

        return sprite;
    }

    public void initialize() {
        this.spriteConfig = new SpriteConfig( this.res );
        ArrayList displayableSpriteElements = this.spriteConfig.filterSprites("display", "1" );

        for( Object spriteElement : displayableSpriteElements ) {
            String id = SpriteConfig.spriteElementChain( (Map)spriteElement, new String[]{"id", "text"} );
            this.createSprite( id );
        }
    }

    public void sanitize() {
        Iterator<Sprite> i = this.sprites.iterator();

        while( i.hasNext() ) {
            if( i.next().X_X ) {
                i.remove();
            }
        }
    }

    public void draw(Canvas canvas) {
        Iterator<Sprite> i = this.getSprites().iterator();

        while( i.hasNext() ) {
            i.next().draw(canvas);
        }
    }

    public void update(long gameTime, DrawablePanel panel){
        this.sanitize();

        Iterator<Sprite> i = this.getSprites().iterator();

        while( i.hasNext() ) {
            Sprite sprite = i.next();
            sprite.update(gameTime, panel, this);
        }
    }

}
