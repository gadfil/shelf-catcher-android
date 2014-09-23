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
import ru.shelfcatcher.app.controller.CategoryActivity;
import ru.shelfcatcher.app.controller.PhotoReportActivity;
import ru.shelfcatcher.app.controller.StoresActivity;
import ru.shelfcatcher.app.model.data.Shelve;
import ru.shelfcatcher.app.model.operation.Util;
import ru.shelfcatcher.app.model.operation.netowrk.Api;

import java.net.UnknownHostException;

/**
 * Created by gadfil on 10.09.2014.
 */
public class ShelveFragment extends Fragment  implements AdapterView.OnItemClickListener{

    public static final String ARG_STORE_ID = "arg_store_id";
    public static final String ARG_CATEGORY_ID = "arg_category_id";
    private ListView mListView;
    private ActionBar mActionBar;
    private ArrayAdapter<Shelve> mAdapter;
    private long mCategoryId;
    private long mStoreId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_content, container, false);
        mListView = (ListView)rootView.findViewById(R.id.listView);

        if(getArguments()!=null){
            mCategoryId = getArguments().getLong(ARG_CATEGORY_ID);
            mStoreId = getArguments().getLong(ARG_STORE_ID);
            Bundle arg = new Bundle();
            arg.putLong(ARG_STORE_ID,mStoreId);
            new ApiShelvesTask(getActivity()).execute(arg);
        }
        mListView.setOnItemClickListener(this);
        return rootView;

    }



        public static Fragment newInstance(long storeId, long categoryId){
            ShelveFragment fragment = new ShelveFragment();
            Bundle arg = new Bundle();
            arg.putLong(ARG_CATEGORY_ID,categoryId);
            arg.putLong(ARG_STORE_ID,storeId);
            fragment.setArguments(arg);
            return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setHasOptionsMenu(true);
        mActionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.title_shelves);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent =new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra(CategoryActivity.STORE_ID, mStoreId);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long shelfId = ((Shelve)parent.getAdapter().getItem(position)).getId();
        PhotoReportActivity.newInstance(getActivity(), mStoreId, mCategoryId, shelfId);
    }

    class ApiShelvesTask extends AsyncTask <Bundle, Void, Integer> {
        private Shelve[] shelves;
        private Context mContext;
        private boolean mError = false;
        private int mToast;

        private void errorByStatusResponse(int code) {
            if (code == 400) {
                mError = true;
                mToast = R.string.user_not_found;
            }
        }
        ApiShelvesTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Integer doInBackground(Bundle...params) {

            if(params.length!=0) {
                try {
                    long storeId = params[0].getLong(ARG_STORE_ID);
                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Api.BASE_URL)
                            .build();
                    Api api = restAdapter.create(Api.class);
                    shelves = api.getShelves(String.valueOf(storeId), Util.getToken(mContext)).getShelves();
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

            }



            return null;
        }

        @Override
        protected  void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (mError) {
                Toast.makeText(getActivity(), mToast, Toast.LENGTH_LONG).show();
            } else if(shelves !=null){
                mAdapter = new ArrayAdapter<Shelve>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, shelves);
                mListView.setAdapter(mAdapter);
            }
        }


    }
}
