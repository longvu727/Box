package com.example.longvu.box.Configs;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.example.longvu.box.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by longvu on 11/9/17.
 *
 */

public class SpriteConfig extends Config {

    public SpriteConfig( Resources resource ) {
        super( resource );
        this.CONFIG.put( "spritesXML", this.parseSpritesXML() );
    }

    private Map<String, String> parseSpritesXML () {
        XmlResourceParser parser = res.getXml( R.xml.sprites );
        try {
            return this.parseXML( parser );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList getSprites() {
        try {
            return (ArrayList) ( (Map) this.CONFIG.get("spritesXML").get("sprites") ).get("sprite");
        }
        catch ( NullPointerException e ){
            return new ArrayList();
        }
    }

    public ArrayList filterSprites( String criteria, String value ) {

        if( criteria.equals("display") ) {
            return this.filterByDisplay( value );
        }

        return this.getSprites();
    }

    private ArrayList filterByDisplay( String value ) {
        ArrayList filteredSpriteElement = new ArrayList();

        for (Object spriteElement : this.getSprites()) {
            String display = SpriteConfig.spriteElementChain((Map) spriteElement, new String[]{"display", "text"});

            if (display.equals(value)) {
                filteredSpriteElement.add(spriteElement);
            }
        }

        return filteredSpriteElement;
    }

    public static String spriteElementChain ( Map element, String[] keys ) {
        Map e = element;

        for( String key : keys ) {
            if( e.get(key) instanceof Map ) {
                e = (Map) e.get( key );
            }
            else if( e.get(key) instanceof String ) {
                return e.get(key).toString();
            }
            else {
                return "";
            }
        }

        return "";
    }
}
