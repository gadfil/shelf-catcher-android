package ru.shelfcatcher.app.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.view.PhotoFragment;
import ru.shelfcatcher.app.view.ShelveFragment;

/**
 * Created by gadfil on 11.09.2014.
 */
public class PhotoActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, PhotoFragment.newInstance())
                .commit();
//        getWindow().addFlags(WindowManager.LayoutParams.NO);
    }
}