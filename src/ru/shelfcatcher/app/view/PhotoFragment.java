package ru.shelfcatcher.app.view;

import android.content.res.Configuration;
import android.graphics.*;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import ru.shelfcatcher.app.R;
import android.view.ViewGroup.LayoutParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by gadfil on 11.09.2014.
 */
public class PhotoFragment extends Fragment implements SurfaceHolder.Callback, Camera.PictureCallback,
        Camera.PreviewCallback, Camera.AutoFocusCallback, View.OnClickListener {

    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private SurfaceView preview;
    private Button btPhotoOk;
    private FrameLayout mFrameImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo, container, false);

        preview = (SurfaceView) rootView.findViewById(R.id.surfaceView);

        surfaceHolder = preview.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        mFrameImage = (FrameLayout)rootView.findViewById(R.id.photoFrame);
        mFrameImage.setDrawingCacheEnabled(true);
        mFrameImage.buildDrawingCache();

        btPhotoOk = (Button) rootView.findViewById(R.id.button);
        btPhotoOk.setOnClickListener(this);
        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        camera = Camera.open();


    }

    @Override
    public void onPause() {
        super.onPause();
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }

    }


    public static Fragment newInstance() {
        return new PhotoFragment();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.setPreviewCallback(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Camera.Size previewSize = camera.getParameters().getPreviewSize();
        float aspect = (float) previewSize.width / previewSize.height;

        int previewSurfaceWidth = preview.getWidth();
        int previewSurfaceHeight = preview.getHeight();

        LayoutParams lp = preview.getLayoutParams();


        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(90);
            lp.height = previewSurfaceHeight;
            lp.width = (int) (previewSurfaceHeight / aspect);
            ;
        } else {
            // ландшафтный
            camera.setDisplayOrientation(0);
            lp.width = previewSurfaceWidth;
            lp.height = (int) (previewSurfaceWidth / aspect);
        }

        preview.setLayoutParams(lp);
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.button:
//                Bitmap bitmap = mFrameImage.getDrawingCache();
//
//                String root = Environment.getExternalStorageDirectory().toString();
//                File myDir = new File(root + "/saved_images");
//                myDir.mkdirs();
//
//                long n = new Date().getTime();
//
//                String fname = "Image-"+ n +".jpg";
//                File file = new File (myDir, fname);
//                if (file.exists ()) file.delete ();
//                try {
//                    FileOutputStream out = new FileOutputStream(file);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
////                    out.write(bitmap.);
//                    out.flush();
//                    out.close();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
//        if (v == shotBtn)
//        {
//
//        camera.takePicture(null, null, null, this);
//        camera.autoFocus(this);
        camera.takePicture(null, null, null, this);
//        }
    }


    private Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, new Matrix(), null);
        return bmOverlay;
    }
    @Override
    public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera) {

        try {


            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/saved_images");
            myDir.mkdirs();

            long n = new Date().getTime();

            String fname = "Image-"+ n +".jpg";
            File file = new File (myDir, fname);
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
            Bitmap bitmap = mFrameImage.getDrawingCache();

            BitmapFactory.Options opt = new BitmapFactory.Options();


            float border = getActivity().getResources().getDimension(R.dimen.border);

            int bottom = mFrameImage.getBottom();
            int top = mFrameImage.getTop();
            int width= mFrameImage.getWidth();
            int height= mFrameImage.getHeight();
            Log.d("log", "bottom = " + bottom+
                    "\ntop = " + top+
                    "\nwidth= " +width +
                    "\nheight=" + height);

            Bitmap outb = Bitmap.createBitmap(bitmap1,top, top,width, height);
            if (file.exists ()) file.delete ();
            try {
                FileOutputStream out = new FileOutputStream(file);
               outb.compress(Bitmap.CompressFormat.JPEG, 90, out);
//                out.write(paramArrayOfByte);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
//            String root = Environment.getExternalStorageDirectory().toString();
//            File saveDir = new File(root);
//
//            if (!saveDir.exists()) {
//                saveDir.mkdirs();
////            }
//
//            FileOutputStream os = new FileOutputStream(String.format("/sdcard/CameraExample/%d.jpg", System.currentTimeMillis()));
//            os.write(paramArrayOfByte);
//            os.close();
        } catch (Exception e) {
        }

        paramCamera.startPreview();
    }

    @Override
    public void onAutoFocus(boolean paramBoolean, Camera paramCamera) {
        if (paramBoolean) {
            paramCamera.takePicture(null, null, null, this);
        }
    }

    @Override
    public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera) {
    }
}

