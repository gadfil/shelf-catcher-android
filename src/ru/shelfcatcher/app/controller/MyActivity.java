package ru.shelfcatcher.app.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.model.data.Store;
import ru.shelfcatcher.app.model.data.User;
import ru.shelfcatcher.app.model.operation.Util;
import ru.shelfcatcher.app.model.operation.netowrk.Api;
import ru.shelfcatcher.app.view.LoginFragment;
import ru.shelfcatcher.app.view.StoresFragment;
import ru.shelfcatcher.app.view.UserFragment;

import java.util.Arrays;

public class MyActivity extends ActionBarActivity implements RequestManager.RequestListener, Login {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        String token = Util.getToken(this);
        if (token == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commit();
        } else {


            new LoginTask(this).execute();
        }

//        new MyAPi().execute();
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

    @Override
    public void login(String login, String password) {
        Log.d("login", "#login");
        Bundle arg = new Bundle();
        arg.putString(LoginTask.ARG_LOGIN, login);
        arg.putString(LoginTask.ARG_PASSWORD, password);
        new LoginTask(this).execute(arg);

    }

    @Override
    public void logout() {

    }

    class LoginTask extends AsyncTask<Bundle, Void, Integer> {


        public static final String ARG_LOGIN = "arg_login";
        public static final String ARG_PASSWORD = "arg_password";
        private Context mContext;
        private User mUser;

        LoginTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Integer doInBackground(Bundle... params) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Api.BASE_URL)
                    .build();
            Api api = restAdapter.create(Api.class);

            if (params.length != 0) {
                try {
                    String login = params[0].getString(ARG_LOGIN);
                    String password = params[0].getString(ARG_PASSWORD);
                    mUser = api.login(login, password).getUser();
                } catch (RetrofitError error) {
                    if (error.getResponse() != null) {
                        int code = error.getResponse().getStatus();
                        Log.e("log", "Http error, status : " + code);
                    } else {
                        Log.d("log", "Unknown error");
                        error.printStackTrace();
                        Log.e("log", error.getMessage());
                    }
                }

            } else {

                try {
                    mUser = api.userMe(Util.getToken(mContext)).getUser();
                } catch (RetrofitError error) {
                        if (error.getResponse() != null) {
                            int code = error.getResponse().getStatus();
                            Log.e("log", "Http error, status : " + code);
                        } else {
                            Log.d("log", "Unknown error");
                            error.printStackTrace();
                            Log.e("log", error.getMessage());
                        }
                    }

            }


            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (mUser != null) {
                Util.setToken(getApplicationContext(), mUser.getToken());
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, UserFragment.newInstance(mUser.getFull_name(), mUser.getCompany_name()))
                        .commit();
            }
        }

    }

}
