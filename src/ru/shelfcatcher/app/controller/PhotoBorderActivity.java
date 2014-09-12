package ru.shelfcatcher.app.controller;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.view.MyViewBorder;

/**
 * Created by gadfil on 12.09.2014.
 */
public class PhotoBorderActivity extends Activity {

    private Camera camera;
    private SurfaceView preview;
    private MyViewBorder myViewBorder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photo);
         myViewBorder = (MyViewBorder)findViewById(R.id.photoFrame);
        myViewBorder.setOnTouchListener(myViewBorder);
        preview = (SurfaceView)findViewById(R.id.surfaceView);
        SurfaceHolder surfaceHolder = preview.getHolder();
//        surfaceHolder.addCallback(this);
//        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


    }
}