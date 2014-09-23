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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.controller.MyActivity;
import ru.shelfcatcher.app.controller.ShelveActivity;
import ru.shelfcatcher.app.controller.StoresActivity;
import ru.shelfcatcher.app.model.data.Category;
import ru.shelfcatcher.app.model.operation.Util;
import ru.shelfcatcher.app.model.operation.netowrk.Api;

import java.net.UnknownHostException;

/**
 * Created by gadfil on 10.09.2014.
 */
public class CategoryFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String ARG_STORE_ID = "arg_store_id";
    private long mStoreId;
    private ArrayAdapter<Category> mAdapter;
    private ListView mListView;
    private ActionBar mActionBar;

    @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_content, container, false);
        if(getArguments()!=null){
            mStoreId = getArguments().getLong(ARG_STORE_ID);
        }
        mListView = (ListView)rootView.findViewById(R.id.listView);
        mListView.setOnItemClickListener(this);
        new ApiCategoryTask(getActivity()).execute();
        return rootView;

    }

    public static Fragment newInstance(long storeId){
        Bundle arg = new Bundle();
        arg.putLong(ARG_STORE_ID, storeId);
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long categoryId = ((Category)parent.getAdapter().getItem(position)).getId();

        Intent intent = new Intent(getActivity(), ShelveActivity.class);
        intent.putExtra(ShelveActivity.STORE_ID, mStoreId);
        intent.putExtra(ShelveActivity.CATEGORY_ID, categoryId);

        startActivity(intent);



    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setHasOptionsMenu(true);
        mActionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.title_category);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent =new Intent(getActivity(), StoresActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    class ApiCategoryTask extends AsyncTask {
        private Category[] mCategories;
        private Context mContext;
        private boolean mError = false;
        private int mToast;

        private void errorByStatusResponse(int code) {
            if (code == 400) {
                mError = true;
                mToast = R.string.user_not_found;
            }
        }

        ApiCategoryTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Object doInBackground(Object[] params) {

            try {
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint(Api.BASE_URL)
                        .build();
                Api api = restAdapter.create(Api.class);
                mCategories = api.getCategories(Util.getToken(mContext)).getCategories();
            }  catch (RetrofitError error) {
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
            } else if (mCategories !=null){
                mAdapter = new ArrayAdapter<Category>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, mCategories);
                mListView.setAdapter(mAdapter);
            }
        }
    }
}
