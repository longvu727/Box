package com.example.longvu.box.Configs;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by longvu on 11/9/17.
 *
 */

public class Config {
    public HashMap<String, Map> CONFIG;
    public Resources res;

    public Config( Resources resource ) {
        this.res = resource;
        this.CONFIG = new HashMap<>();
    }

    protected Map parseXML( XmlResourceParser parser ) throws Exception {
        Map<String, Object> map = new HashMap<>();
        int eventType = parser.getEventType();

        while( eventType != XmlPullParser.END_DOCUMENT ) {

            if (eventType == XmlPullParser.START_TAG) {
                String tagName = parser.getName();
                parser.next();
                Map inner = this.parseXML(parser);

                Object element = map.get(tagName);
                if( element == null ) {
                    map.put( tagName, inner );
                }
                else if( element instanceof ArrayList ){
                    ( (ArrayList)element ).add( inner );
                }
                else {
                    ArrayList elements = new ArrayList();
                    elements.add( element );
                    elements.add( inner );
                    map.put( tagName, elements );
                }
            } else if (eventType == XmlPullParser.TEXT) {
                map.put("text", parser.getText() );
            }
            else if( eventType == XmlPullParser.END_TAG ) {
                return map;
            }
            eventType = parser.next();

        }
        return map;
    }

}
