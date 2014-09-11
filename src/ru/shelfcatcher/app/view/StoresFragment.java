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
import retrofit.RestAdapter;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.controller.CategoryActivity;
import ru.shelfcatcher.app.model.data.Store;
import ru.shelfcatcher.app.model.operation.Util;
import ru.shelfcatcher.app.model.operation.netowrk.Api;

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
        mListView = (ListView)rootView.findViewById(R.id.listView);
        mListView.setOnItemClickListener(this);
        new ApiStoresTask(getActivity()).execute();
        return rootView;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("mylog", parent.getAdapter().getItem(position).toString());
        long storeId = ((Store)parent.getAdapter().getItem(position)).getId();
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        intent.putExtra(CategoryActivity.STORE_ID, storeId);
        startActivity(intent);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
            setHasOptionsMenu(true);
            mActionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
            mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    class ApiStoresTask extends AsyncTask{
        private Context mContext;
        private Store[] mStores;

        ApiStoresTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Object doInBackground(Object[] params) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Api.BASE_URL)
                    .build();
            Api api = restAdapter.create(Api.class);
            mStores = api.getStores(Util.getToken(mContext)).getStores();

            return null;
        }



        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(mStores !=null){
                mAdapter = new ArrayAdapter<Store>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, mStores);
                mListView.setAdapter(mAdapter);
            }
        }
    }
}
