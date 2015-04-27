package ru.shelfcatcher.app.model.operation;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gadfil on 09.09.2014.
 */
public class Util {
    private static final String TOKEN = "token";
    private static final String STORE = "store";
    private static final String CATEGORY = "category";
    private static final String SHELVES = "shelves";
    private static final String MAX_Y = "MAX_Y";
    private static final String MIN_Y = "MIN_Y";
    private static final String MAX_Z = "MAX_Z";
    private static final String MIN_Z = "MIN_Z";

    public static int getMAX_Y(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(MAX_Y, Context.MODE_PRIVATE);
        return sharedPref.getInt(MAX_Y, 0);
    }


    public static int getMIN_Y(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(MIN_Y, Context.MODE_PRIVATE);
        return sharedPref.getInt(MIN_Y, 0);
    }


    public static int getMAX_Z(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(MAX_Z, Context.MODE_PRIVATE);
        return sharedPref.getInt(MAX_Z, 0);
    }


    public static int getMIN_Z(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(MIN_Z, Context.MODE_PRIVATE);
        return sharedPref.getInt(MIN_Z, 0);
    }


    public static void setMaxY(Context context, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(MAX_Y, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(MAX_Y, value);
        editor.commit();
    }


    public static void setMinY(Context context, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(MIN_Y, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(MIN_Y, value);
        editor.commit();
    }


    public static void setMaxZ(Context context, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(MAX_Z, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(MAX_Z, value);
        editor.commit();
    }


    public static void setMinZ(Context context, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(MIN_Z, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(MIN_Z, value);
        editor.commit();
    }


    //
    public static String getToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
        return sharedPref.getString(TOKEN, null);
    }

    public static String getStore(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORE, Context.MODE_PRIVATE);
        return sharedPref.getString(STORE, null);
    }


    public static String getCategory(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(CATEGORY, Context.MODE_PRIVATE);
        return sharedPref.getString(CATEGORY, null);
    }


    public static String getShelves(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHELVES, Context.MODE_PRIVATE);
        return sharedPref.getString(SHELVES, null);
    }


    public static void setToken(Context context, String token) {
        SharedPreferences sharedPref = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }


    public static void setStore(Context context, String token) {
        SharedPreferences sharedPref = context.getSharedPreferences(STORE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(STORE, token);
        editor.commit();
    }


    public static void setCategory(Context context, String token) {
        SharedPreferences sharedPref = context.getSharedPreferences(CATEGORY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CATEGORY, token);
        editor.commit();
    }


    public static void setShelves(Context context, String token) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHELVES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SHELVES, token);
        editor.commit();
    }


}
