package ru.shelfcatcher.app.controller;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.*;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.*;
import android.widget.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import ru.shelfcatcher.app.R;
import ru.shelfcatcher.app.model.data.Message;
import ru.shelfcatcher.app.model.data.Report;
import ru.shelfcatcher.app.model.data.RequestReport;
import ru.shelfcatcher.app.model.operation.Util;
import ru.shelfcatcher.app.model.operation.netowrk.Api;
import ru.shelfcatcher.app.view.PhotoUiFragment;
import ru.shelfcatcher.app.view.ReportUiFragment;

import java.io.*;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gadfil on 16.09.2014.
 */
public class PhotoReportActivity extends ActionBarActivity implements PhotoReport, SurfaceHolder.Callback,
        Camera.PictureCallback, Camera.PreviewCallback {

    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_SHELF_ID = "shelf_id";
    private static final String KEY_STORE_ID = "store_id";
    private static final String KEY_ARRAY = "key_array";
//    private String []mArray;

    private ArrayList<Photo> photoList;
    private ArrayList<Long> photoIdList;
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private RelativeLayout mFrameLayout;
    private ProgressBar mProgressBar;
    private boolean isSend = true;
    private long mStoreId;
    private long mCategoryId;
    private long mShelfId;
    private File mDir;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_photo_report);

        Intent intent = getIntent();
        mStoreId = intent.getLongExtra(KEY_STORE_ID, 1);
        mCategoryId = intent.getLongExtra(KEY_CATEGORY_ID, 1);
        mShelfId = intent.getLongExtra(KEY_SHELF_ID, 1);
//        mArray = getIntent().getStringArrayExtra(KEY_ARRAY);
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        String dirs = "/" + Util.getStore(this) + "/" + Util.getCategory(this) + "/" + Util.getShelves(this);
//        for(String s:mArray){
//            dirs+="/"+s;
//        }
        mDir = new File(sdCardDirectory + "/ShelfCatcher" + dirs);
        mFrameLayout = (RelativeLayout) findViewById(R.id.photoFrame);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        photoUi();
        photoList = new ArrayList<Photo>();
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onResume() {
        super.onResume();
        mCamera = Camera.open(0);


    }

    @Override
    protected void onPause() {
        super.onPause();
//
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }


    }


    public static void newInstance(Context context, long storeId, long categoryId, long shelfId) {
        Intent intent = new Intent(context, PhotoReportActivity.class);
        intent.putExtra(KEY_CATEGORY_ID, categoryId);
        intent.putExtra(KEY_SHELF_ID, shelfId);
        intent.putExtra(KEY_STORE_ID, storeId);
//        intent.putExtra(KEY_ARRAY, array);
        context.startActivity(intent);
    }

    private void photoUi() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.controller_container, PhotoUiFragment.newInstance())
                .commit();
    }

    private void reportUi() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.controller_container, ReportUiFragment.newInstance())
                .commit();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.setPreviewCallback(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
        float aspect = (float) previewSize.width / previewSize.height;

        int previewSurfaceWidth = mSurfaceView.getWidth();
        int previewSurfaceHeight = mSurfaceView.getHeight();

        ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();


        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            mCamera.setDisplayOrientation(90);
            lp.height = previewSurfaceHeight;
            lp.width = (int) (previewSurfaceHeight / aspect);
        } else {
            mCamera.setDisplayOrientation(0);
            lp.width = previewSurfaceWidth;
            lp.height = (int) (previewSurfaceWidth / aspect);
        }

        mSurfaceView.setLayoutParams(lp);
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }


    @Override
    public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera) {


        try {


            photoList.add(new Photo(paramArrayOfByte));

        } catch (Exception e) {
        }


        reportUi();

    }


    @Override
    public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera) {
    }

    @Override
    public void photo() {
        if (isSend) {
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {

                    camera.takePicture(null, null, null, PhotoReportActivity.this);

                }
            });
//            mCamera.takePicture(null, null, null, this);
        }

    }

    @Override
    public void nextPhoto() {
        if (isSend) {
            mCamera.startPreview();
            photoUi();
        }

    }

    @Override
    public void cancel() {
        if (isSend) {
            Intent intent = new Intent(this, MyActivity.class);
            startActivity(intent);

        }

    }

    @Override
    public void report() {
        if (isSend) {
            new SendPhoto().execute();
        }

    }


    @Override
    public void newPhoto() {
        if (isSend) {
            mCamera.startPreview();
            photoList.remove(photoList.size() - 1);
            photoUi();
        }

    }


    class Photo {
        private String name;
        private byte[] photo;

        Photo(byte[] paramArrayOfByte) {
            name = "img-" + System.currentTimeMillis() + ".jpeg";
            photo = paramArrayOfByte;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public byte[] getPhoto() {
            return photo;
        }

        public void setPhoto(byte[] photo) {
            this.photo = photo;
        }
    }


    class SendPhoto extends AsyncTask {
        private boolean result = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            isSend = false;
        }

        private boolean mError = false;
        private int mToast;

        private void errorByStatusResponse(int code) {
            if (code == 400) {
                mError = true;
                mToast = R.string.user_not_found;
            }
        }

        @Override
        protected Object doInBackground(Object[] params) {


            try {

                if (photoList.size() > 0) {
                    photoIdList = new ArrayList<Long>();

                    for (int i = 0; i < photoList.size(); i++) {
                        Photo photo = photoList.get(i);

                        Bitmap dstBmp = getBitmap(photo);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        dstBmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                        ByteArrayInputStream bs = new ByteArrayInputStream(stream.toByteArray());


                        InputStream in = bs;
                        long id = loadPhoto(bs, photo.name);
                        if (id > 0) {
                            photoIdList.add(new Long(id));

                            File dir = new File(mDir, "/" + simpleDateFormat.format(new Date()));
                            dir.mkdirs();

                            Bitmap bitmap = getBitmap(photo);
                            File file = new File(dir, photo.getName());
                            if (file.exists()) file.delete();
                            try {
                                FileOutputStream out = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                                out.flush();
                                out.close();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }
            } catch (RetrofitError error) {
                if (error.getCause() instanceof UnknownHostException) {

                    Log.e("log", "# @" + error.getMessage());
                    mError = true;
                    mToast = R.string.network_connection_error;

                }
                if (error.getResponse() != null) {
                    int code = error.getResponse().getStatus();
                    errorByStatusResponse(code);
                    Log.e("log", "Http error, status : " + code);
                } else {
                    Log.e("log", "Unknown error");
                    error.printStackTrace();
                    Log.e("log", error.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("img", e.getMessage());
            }
            if (photoIdList.size() > 0) {
                long arr[] = new long[photoIdList.size()];
                for (int i = 0; i < photoIdList.size(); i++) {
                    arr[i] = (long) photoIdList.get(i);
                }

                try {
                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Api.BASE_URL)
                            .build();
                    Api api = restAdapter.create(Api.class);
                    Report report = new Report();
                    report.setCategory_id(mCategoryId);
                    report.setShelf_id(mShelfId);
                    report.setImage_ids(arr);
                    RequestReport requestReport = new RequestReport();
                    requestReport.setReport(report);
                    Message message = api.sendReports(requestReport, Util.getToken(getApplicationContext()));
                    if (message.getMessage().equals("ok")) {
                        result = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("img", e.getMessage());
                    Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                    startActivity(intent);

                }
            }
            return null;
        }

        private Bitmap getBitmap(Photo photo) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);

            Bitmap bitmapOrg = BitmapFactory.decodeByteArray(photo.getPhoto(), 0, photo.getPhoto().length);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmapOrg, bitmapOrg.getWidth(), bitmapOrg.getHeight(), true);

            Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);


            int border = rotatedBitmap.getWidth() / (mFrameLayout.getWidth() / mFrameLayout.getLeft());
            return Bitmap.createBitmap(
                    rotatedBitmap,
                    border,
                    border,
                    rotatedBitmap.getWidth() - border * 2,
                    rotatedBitmap.getHeight() - border * 2
            );
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (mError) {
                Toast.makeText(getApplicationContext(), mToast, Toast.LENGTH_LONG).show();
            } else if (result) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                intent.putExtra(CategoryActivity.STORE_ID, mStoreId);
                Toast.makeText(getApplicationContext(), R.string.report_ok, Toast.LENGTH_LONG).show();
                startActivity(intent);
            } else {
                mProgressBar.setVisibility(View.GONE);
                isSend = true;
                mCamera.startPreview();
                photoIdList = null;
                photoList = new ArrayList<Photo>();
                photoUi();
                Toast.makeText(getApplicationContext(), R.string.report_error, Toast.LENGTH_LONG).show();
            }
        }

        private long loadPhoto(InputStream ims, String name) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(Api.BASE_URL + Api.IMAGES);
            MultipartEntityBuilder multipartEntity = MultipartEntityBuilder
                    .create();


            try {

                multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipartEntity.addBinaryBody("image[data]",
                        ims, ContentType.MULTIPART_FORM_DATA,
                        "name");
                multipartEntity.addTextBody("token", Util.getToken(getApplicationContext()));
                post.setEntity(multipartEntity.build());
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                JSONObject object = new JSONObject(sb.toString());
                Log.d("img id ", " " + object.getJSONObject("image").getLong("id"));
                return object.getJSONObject("image").getLong("id");
            } catch (IOException e) {
                Log.e("img", e.getMessage());
            } catch (Exception e) {
                Log.e("img", e.getMessage());
            }
            return -1;
        }
    }
}