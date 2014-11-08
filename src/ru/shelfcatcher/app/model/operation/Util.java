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
