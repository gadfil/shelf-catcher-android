package ru.shelfcatcher.app.view;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.controller.PhotoReport;

/**
 * Created by gadfil on 16.09.2014.
 */
public class PhotoUiFragment extends Fragment implements View.OnClickListener, SensorEventListener {
    private PhotoReport mPhotoReport;
    private ImageButton mButtonPhoto;
    private Button mButtonCancel;
    private SensorManager mSensorManager;
    private float[] rotationMatrix;
    private float[] accelData;
    private float[] magnetData;
    private float[] OrientationData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo_controller, container, false);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        initView(rootView);
        rotationMatrix = new float[16];
        accelData = new float[3];
        magnetData = new float[3];
        OrientationData = new float[3];
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
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    private void loadNewSensorData(SensorEvent event) {
        final int type = event.sensor.getType(); //Определяем тип датчика
        if (type == Sensor.TYPE_ACCELEROMETER) { //Если акселерометр
            accelData = event.values.clone();
        }

        if (type == Sensor.TYPE_MAGNETIC_FIELD) { //Если геомагнитный датчик
            magnetData = event.values.clone();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        loadNewSensorData(event);
        SensorManager.getRotationMatrix(rotationMatrix, null, accelData, magnetData);
        SensorManager.getOrientation(rotationMatrix, OrientationData);

        float xy = Math.round(Math.toDegrees(OrientationData[0]));
        float xz = Math.round(Math.toDegrees(OrientationData[1]));
        float zy = Math.round(Math.toDegrees(OrientationData[2]));


//        if ((xy >= 110 && xy <= 150) && (xz >= -80 && xz <= -60)) {

        if ((zy < -50) && (xz < -65)) {
            mButtonPhoto.setClickable(true);
            mButtonPhoto.setBackgroundResource(R.drawable.photo_on_btn_default_holo_light);
        } else {
            mButtonPhoto.setClickable(false);
            mButtonPhoto.setBackgroundResource(R.drawable.photo_of_btn_default_holo_light);
        }

//        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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
            case R.id.fragment_photo_controller_photo:
                mPhotoReport.photo();
                break;
            case R.id.fragment_photo_controller_cancel:
                mPhotoReport.cancel();
                break;
        }

    }
}
