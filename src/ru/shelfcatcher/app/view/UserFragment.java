package ru.shelfcatcher.app.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ru.shelfcatcher.app.R;

/**
 * Created by gadfil on 09.09.2014.
 */
public class UserFragment extends Fragment  implements View.OnClickListener{
    public static String ARG_FULL_NAME = "full_name";
    public static String ARG_COMPANY_NAME = "company_name";

    private TextView mFullName;
    private TextView mCompanyName;
    private Button mButtonStart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        initView(rootView);

        if(getArguments()!=null && getArguments().containsKey(ARG_FULL_NAME) && getArguments().containsKey(ARG_COMPANY_NAME)){
            mCompanyName.setText(getArguments().getString(ARG_COMPANY_NAME));
            mFullName.setText(getArguments().getString(ARG_FULL_NAME));

        }
            return rootView;

    }

    private void initView(View rootView) {
        mFullName = (TextView)rootView.findViewById(R.id.user_fragment_tv_full_name);
        mCompanyName = (TextView)rootView.findViewById(R.id.user_fragment_tv_company_name);
        mButtonStart = (Button)rootView.findViewById(R.id.user_fragment_btStart);
        mButtonStart.setOnClickListener(this);
    }

    public static Fragment newInstance(String fullName, String companyName){
        UserFragment fragment = new UserFragment();
        Bundle arg = new Bundle();
        arg.putString(ARG_FULL_NAME, fullName);
        arg.putString(ARG_COMPANY_NAME, companyName);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onClick(View v) {

    }
}
