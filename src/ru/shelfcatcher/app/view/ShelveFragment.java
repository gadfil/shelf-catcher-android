package ru.shelfcatcher.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import retrofit.RestAdapter;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.controller.PhotoActivity;
import ru.shelfcatcher.app.model.data.Category;
import ru.shelfcatcher.app.model.data.Shelve;
import ru.shelfcatcher.app.model.operation.Util;
import ru.shelfcatcher.app.model.operation.netowrk.Api;

/**
 * Created by gadfil on 10.09.2014.
 */
public class ShelveFragment extends Fragment  implements AdapterView.OnItemClickListener{

    public static final String ARG_STORE_ID = "arg_store_id";
    public static final String ARG_CATEGORY_ID = "arg_category_id";
    private ListView mListView;
    private ActionBar mActionBar;
    private ArrayAdapter<Shelve> mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_content, container, false);
        mListView = (ListView)rootView.findViewById(R.id.listView);

        if(getArguments()!=null){
            Bundle arg = new Bundle();
            arg.putLong(ARG_STORE_ID,1);
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
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getActivity(), PhotoActivity.class));
    }

    class ApiShelvesTask extends AsyncTask <Bundle, Void, Integer> {
        private Shelve[] shelves;
        private Context mContext;

        ApiShelvesTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Integer doInBackground(Bundle...params) {

            if(params.length!=0) {
                long storeId = params[0].getLong(ARG_STORE_ID);
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint(Api.BASE_URL)
                        .build();
                Api api = restAdapter.create(Api.class);
                shelves = api.getShelves(String.valueOf(storeId), Util.getToken(mContext)).getShelves();

            }
//            Shelve[] shelves = api.getShelves("1").getShelves();
//
//            Log.d("mylog", "#shelves.length = "  + shelves.length);



            return null;
        }

        @Override
        protected  void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(shelves !=null){
                mAdapter = new ArrayAdapter<Shelve>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, shelves);
                mListView.setAdapter(mAdapter);
            }
        }


    }
}
