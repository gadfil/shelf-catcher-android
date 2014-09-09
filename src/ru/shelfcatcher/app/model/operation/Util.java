package ru.shelfcatcher.app.model.operation;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gadfil on 09.09.2014.
 */
public class Util {
    private static final String TOKEN = "token";

    public static String getToken(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences( TOKEN, Context.MODE_PRIVATE);
        return sharedPref.getString(TOKEN, null);
    }


}
