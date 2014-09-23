package ru.shelfcatcher.app.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.controller.PhotoReport;

/**
 * Created by gadfil on 16.09.2014.
 */
public class PhotoUiFragment extends Fragment implements View.OnClickListener {
    private PhotoReport mPhotoReport;
    private ImageButton mButtonPhoto;
    private Button mButtonCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo_controller, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mButtonPhoto = (ImageButton) rootView.findViewById(R.id.fragment_photo_controller_photo);
        mButtonCancel = (Button) rootView.findViewById(R.id.fragment_photo_controller_cancel);
        mButtonPhoto.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
    }

    public static Fragment newInstance() {
        return new PhotoUiFragment();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mPhotoReport = (PhotoReport) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement PhotoReport.");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_photo_controller_photo:
                mPhotoReport.photo();
                break;
            case R.id.fragment_photo_controller_cancel:
                mPhotoReport.cancel();
                break;
        }

    }
}
