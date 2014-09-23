package ru.shelfcatcher.app.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;
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

import java.net.UnknownHostException;
import java.util.Arrays;

public class MyActivity extends ActionBarActivity implements Login {


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
    public void login(String login, String password) {
        Bundle arg = new Bundle();
        arg.putString(LoginTask.ARG_LOGIN, login);
        arg.putString(LoginTask.ARG_PASSWORD, password);
        new LoginTask(this).execute(arg);

    }


    @Override
    public void logout() {
        Util.setToken(this, null);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commit();

    }

    class LoginTask extends AsyncTask<Bundle, Void, Integer> {


        public static final String ARG_LOGIN = "arg_login";
        public static final String ARG_PASSWORD = "arg_password";
        private Context mContext;
        private User mUser;
        private boolean mError = false;
        private int mToast;

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
                String login = params[0].getString(ARG_LOGIN);
                String password = params[0].getString(ARG_PASSWORD);
                logIn(api, login, password);

            } else {

                logIn(api);

            }


            return null;
        }

        private void logIn(Api api) {
            try {
                mUser = api.userMe(Util.getToken(mContext)).getUser();
            } catch (RetrofitError error) {
                if (error.getCause() instanceof UnknownHostException) {

                    Log.e("log", "# @" + error.getMessage());
                    mError = true;
                    mToast = R.string.network_connection_error;
                    Toast.makeText(getApplicationContext(), mToast, Toast.LENGTH_LONG).show();
                }
                if (error.getResponse() != null) {
                    int code = error.getResponse().getStatus();
                    errorByStatusResponse(code);
                    Log.e("log", "Http error, status : " + code);
                } else {
                    Log.e("log", "Unknown error");
                    error.printStackTrace();
                    Log.e("log", error.getMessage());
                    Log.e("log", "" + error.isNetworkError());

                }
            } catch (Exception error) {
                if (error.getCause() instanceof UnknownHostException) {

                    Log.e("log", "# " + error.getMessage());
                }
                Log.e("log", "# " + error.getMessage());

            }
        }

        private void errorByStatusResponse(int code) {
            if (code == 400) {
                mError = true;
                mToast = R.string.user_not_found;
            }
        }

        private void logIn(Api api, String login, String password) {
            try {
                mUser = api.login(login, password).getUser();
            } catch (RetrofitError error) {
                if (error.getCause() instanceof UnknownHostException) {

                    Log.e("log", "# @" + error.getMessage());
                    mError = true;
                    mToast = R.string.network_connection_error;

                }
                if (error.getResponse() != null) {
                    int code = error.getResponse().getStatus();
                    errorByStatusResponse(code);
                    Log.e("log", "Http error, status : " + code);
                } else {
                    Log.e("log", "Unknown error");
                    error.printStackTrace();
                    Log.e("log", error.getMessage());
                }
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.d("log", "mUser " + mUser);
            if (mError) {
                Toast.makeText(getApplicationContext(), mToast, Toast.LENGTH_LONG).show();
            } else if (mUser != null) {
                Util.setToken(getApplicationContext(), mUser.getToken());
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, UserFragment.newInstance(mUser.getFull_name(), mUser.getCompany_name()))
                        .commit();
            } else {
                logout();
            }
        }

    }

}
