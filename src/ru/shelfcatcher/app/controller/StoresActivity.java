package ru.shelfcatcher.app.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.view.StoresFragment;

/**
 * Created by gadfil on 10.09.2014.
 */
public class StoresActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,new StoresFragment())
//                    .replace(R.id.container, LoginFragment.newInstance())
                .commit();
    }
}