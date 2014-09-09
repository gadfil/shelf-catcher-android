package ru.shelfcatcher.app.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by gadfil on 09.09.2014.
 */
public class UserFragment extends Fragment {
    public static String ARG_FULL_NAME = "full_name";
    public static String ARG_COMPANY_NAME = "company_name";


    public static Fragment newInstance(String fullName, String companyName){
        UserFragment fragment = new UserFragment();
        Bundle arg = new Bundle();
        arg.putString(ARG_FULL_NAME, fullName);
        arg.putString(ARG_COMPANY_NAME, companyName);
        fragment.setArguments(arg);
        return fragment;
    }
}
