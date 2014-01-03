package com.negusoft.holoaccent.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Utility class to get native android resources.
 */
public class NativeResources {

    private static final String NATIVE_PACKAGE = "android";

    private static final String TYPE_ID = "id";
    private static final String TYPE_STRING = "string";
    private static final String TYPE_DRAWABLE = "drawable";

    /** Get a native identifier by name as in 'R.id.name'. */
    public static int getIdentifier(String name) {
        Resources res = Resources.getSystem();
        return res.getIdentifier(name, TYPE_ID, NATIVE_PACKAGE);
    }

    /** Get a native string id by name as in 'R.string.name'. */
    public static int getStringIdentifier(String name) {
        Resources res = Resources.getSystem();
        return res.getIdentifier(name, TYPE_STRING, NATIVE_PACKAGE);
    }

    /** Get a native drawable by name as in 'R.drawable.name'. */
    public static String getString(String name) {
        Resources res = Resources.getSystem();
        int id = res.getIdentifier(name, TYPE_STRING, NATIVE_PACKAGE);
        return id == 0 ? null : res.getString(id);
    }

    /** Get a native drawable id by name as in 'R.drawable.name'. */
    public static int getDrawableIdentifier(String name) {
        Resources res = Resources.getSystem();
        return res.getIdentifier(name, TYPE_DRAWABLE, NATIVE_PACKAGE);
    }

    /** Get a native drawable by name as in 'R.drawable.name'. */
    public static Drawable getDrawable(String name) {
        Resources res = Resources.getSystem();
        int id = res.getIdentifier(name, TYPE_DRAWABLE, NATIVE_PACKAGE);
        return id == 0 ? null : res.getDrawable(id);
    }

}
