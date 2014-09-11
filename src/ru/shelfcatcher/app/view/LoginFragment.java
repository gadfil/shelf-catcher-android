package ru.shelfcatcher.app.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.controller.Login;

/**
 * Created by gadfil on 09.09.2014.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private Login mLogin;
    private ActionBar mActionBar;
    private EditText mEditTextLogin;
    private EditText mEditTextPass;
    private Button mButtonLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mEditTextLogin = (EditText) rootView.findViewById(R.id.editLogin);
        mEditTextPass = (EditText) rootView.findViewById(R.id.editPass);
        mButtonLogin = (Button) rootView.findViewById(R.id.buttonLogin);
        mButtonLogin.setOnClickListener(this);
    }

    public static Fragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setHasOptionsMenu(true);
        try {
            mLogin = (Login) activity;
            mActionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
            mActionBar.setTitle(R.string.app_name);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement Login.");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                String login = mEditTextLogin.getText().toString();
                String password = mEditTextPass.getText().toString();
                if (login.length() > 0 && password.length() > 0) {
                    mLogin.login(login, password);
                }else {
                    Toast.makeText(getActivity(),R.string.fill_the_fields, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}

