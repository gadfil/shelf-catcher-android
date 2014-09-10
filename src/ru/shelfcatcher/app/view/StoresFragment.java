package ru.shelfcatcher.app.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import retrofit.RestAdapter;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.model.data.Store;
import ru.shelfcatcher.app.model.operation.netowrk.Api;

/**
 * Created by gadfil on 10.09.2014.
 */
public class StoresFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private ArrayAdapter<Store> mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_content, container, false);
        mListView = (ListView)rootView.findViewById(R.id.listView);
        new ApiStoresTask().execute();
        return rootView;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    class ApiStoresTask extends AsyncTask{
        Store[] stores;
        @Override
        protected Object doInBackground(Object[] params) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Api.BASE_URL)
                    .build();
            Api api = restAdapter.create(Api.class);
            stores = api.getStores();

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(stores!=null){
                mAdapter = new ArrayAdapter<Store>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, stores);
                mListView.setAdapter(mAdapter);
            }
        }
    }
}
