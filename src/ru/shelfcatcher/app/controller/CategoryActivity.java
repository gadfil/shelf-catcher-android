package ru.shelfcatcher.app.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.view.CategoryFragment;
import ru.shelfcatcher.app.view.StoresFragment;

/**
 * Created by gadfil on 10.09.2014.
 */
public class CategoryActivity extends ActionBarActivity {
    public static final String ARRAY = "array";
    public static String STORE_ID = "store_id";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        long storeId = getIntent().getLongExtra(STORE_ID, 1);
//        String []arr = getIntent().getStringArrayExtra(ARRAY);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, CategoryFragment.newInstance(storeId))
                .commit();
    }


}