package com.negusoft.holoaccent.example.model;

/** Class to statically store and access color override configuration values. */
public class ColorOverrideConfig {

    private static int sColor = 0;

    public static void setColor(int color) {
        sColor = color;
    }

    public static int getColor() {
        return sColor;
    }

}
