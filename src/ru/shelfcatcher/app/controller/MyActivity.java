package ru.shelfcatcher.app.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager;
import retrofit.RestAdapter;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.model.data.Stores;
import ru.shelfcatcher.app.model.data.User;
import ru.shelfcatcher.app.model.operation.Util;
import ru.shelfcatcher.app.model.operation.netowrk.Api;
import ru.shelfcatcher.app.view.LoginFragment;

import java.util.Arrays;

public class MyActivity extends ActionBarActivity    implements RequestManager.RequestListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        String token = Util.getToken(this);
        if (token == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commit();
        }else {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.container, UserFragment.newInstance())
//                    .commit();
        }

        new MyAPi().execute();
    }

    @Override
    public void onRequestFinished(Request request, Bundle resultData) {

    }

    @Override
    public void onRequestConnectionError(Request request, int statusCode) {

    }

    @Override
    public void onRequestDataError(Request request) {

    }

    @Override
    public void onRequestCustomError(Request request, Bundle resultData) {

    }
    class  MyAPi extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] params) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Api.BASE_URL)
                    .build();
            Api api = restAdapter.create(Api.class);
            User user = api.login("manager", "manager");
            Log.d("mylog", user.toString());

            Stores []stores = api.getStores();
            Log.d("mylog", Arrays.toString(stores));

            return null;
        }
    }
}
