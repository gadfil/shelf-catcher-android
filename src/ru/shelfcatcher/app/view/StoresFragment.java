package ru.shelfcatcher.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.controller.CategoryActivity;
import ru.shelfcatcher.app.controller.MyActivity;
import ru.shelfcatcher.app.model.data.Store;
import ru.shelfcatcher.app.model.operation.Util;
import ru.shelfcatcher.app.model.operation.netowrk.Api;

import java.net.UnknownHostException;

/**
 * Created by gadfil on 10.09.2014.
 */
public class StoresFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private ArrayAdapter<Store> mAdapter;
    private ActionBar mActionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_content, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listView);
        mListView.setOnItemClickListener(this);
        new ApiStoresTask(getActivity()).execute();
        return rootView;

    }


    public static Fragment newInstance() {
        StoresFragment fragment = new StoresFragment();

        return fragment;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("mylog", parent.getAdapter().getItem(position).toString());
        long storeId = ((Store) parent.getAdapter().getItem(position)).getId();
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        intent.putExtra(CategoryActivity.STORE_ID, storeId);
        Util.setStore(getActivity(), ((TextView) view).getText().toString());
//        String arr []= new String[]{((TextView)view).getText().toString()};
//        intent.putExtra(CategoryActivity.ARRAY, arr);
        startActivity(intent);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setHasOptionsMenu(true);
        mActionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.title_stores);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getActivity(), MyActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    class ApiStoresTask extends AsyncTask {
        private Context mContext;
        private Store[] mStores;
        private boolean mError = false;
        private int mToast;

        private void errorByStatusResponse(int code) {
            if (code == 400) {
                mError = true;
                mToast = R.string.user_not_found;
            }
        }

        ApiStoresTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Object doInBackground(Object[] params) {

            try {
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint(Api.BASE_URL)
                        .build();
                Api api = restAdapter.create(Api.class);
                mStores = api.getStores(Util.getToken(mContext)).getStores();
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


            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (mError) {
                Toast.makeText(getActivity(), mToast, Toast.LENGTH_LONG).show();
            } else if (mStores != null) {
                mAdapter = new ArrayAdapter<Store>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, mStores);
                mListView.setAdapter(mAdapter);
            }
        }
    }
}
