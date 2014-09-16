package ru.shelfcatcher.app.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.controller.PhotoReport;

/**
 * Created by gadfil on 16.09.2014.
 */
public class ReportUiFragment extends Fragment implements View.OnClickListener {
    private PhotoReport mPhotoReport;
    private Button mButtonUploadPhoto;
    private Button mButtonNewPhoto;
    private Button mButtonNextPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo_report, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mButtonUploadPhoto = (Button) rootView.findViewById(R.id.fragment_photo_report_upload_photo);
        mButtonNewPhoto = (Button) rootView.findViewById(R.id.fragment_photo_report_new_photo);
        mButtonNextPhoto = (Button) rootView.findViewById(R.id.fragment_photo_report_next_photo);
        mButtonUploadPhoto.setOnClickListener(this);
        mButtonNewPhoto.setOnClickListener(this);
        mButtonNextPhoto.setOnClickListener(this);
    }

    public static Fragment newInstance() {
        return new ReportUiFragment();
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
        switch (v.getId()) {
            case R.id.fragment_photo_report_upload_photo:
                mPhotoReport.report();
                break;
            case R.id.fragment_photo_report_new_photo:
                mPhotoReport.newPhoto();
                break;
            case R.id.fragment_photo_report_next_photo:
                mPhotoReport.nextPhoto();
                break;
        }

    }
}
