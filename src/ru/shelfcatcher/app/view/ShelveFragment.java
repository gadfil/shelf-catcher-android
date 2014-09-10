package ru.shelfcatcher.app.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.model.data.Shelve;

/**
 * Created by gadfil on 10.09.2014.
 */
public class ShelveFragment extends Fragment {

    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_content, container, false);
        mListView = (ListView)rootView.findViewById(R.id.listView);
        ArrayAdapter<Shelve> arrayAdapter = new ArrayAdapter<Shelve>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, shelves);

//        new ApiCategoryTask().execute();
        return rootView;

    }

    static Shelve[] shelves;
    static {
        shelves = new Shelve[3];
        Shelve shelve = new Shelve();
        shelve.setName("Холодильная полка");
        shelve.setId(1);
        shelves[0] = shelve;

        shelve = new Shelve();
        shelve.setName("Теплая полка");
        shelve.setId(2);
        shelves[1] = shelve;

        shelve = new Shelve();
        shelve.setName("Хололдильники");
        shelve.setId(3);
        shelves[2] = shelve;

    }

    public static Fragment newInstance(long storeId, long categoryId){

        return new ShelveFragment();
    }
}
