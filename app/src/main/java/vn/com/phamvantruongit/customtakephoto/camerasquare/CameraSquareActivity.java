package vn.com.phamvantruongit.customtakephoto.camerasquare;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import java.io.File;

import vn.com.phamvantruongit.customtakephoto.R;


public class CameraSquareActivity extends Activity {

    public static final String TAG = CameraSquareActivity.class.getSimpleName();
    public static final int RESULT_FINISH = 1;
    private CameraFragment mfragment;
    private int MY_PERMISSIONS_REQUEST_CAMERA=100;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squarecamera_camera);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int permission1 = ContextCompat.checkSelfPermission(CameraSquareActivity.this, android.Manifest.permission.CAMERA);
            int permission2 = ContextCompat.checkSelfPermission(CameraSquareActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
            int permission3 = ContextCompat.checkSelfPermission(CameraSquareActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission1 != PackageManager.PERMISSION_GRANTED ||
                    permission2 != PackageManager.PERMISSION_GRANTED ||
                    permission3 != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);
                return;
            } else {
                if (savedInstanceState == null) {
                    mfragment = (CameraFragment) CameraFragment.newInstance();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, mfragment, CameraFragment.TAG)
                            .commit();
                }
            }
        } else {
            if (savedInstanceState == null) {
                mfragment = (CameraFragment) CameraFragment.newInstance();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, mfragment, CameraFragment.TAG)
                        .commit();
            }
        }
//        if (savedInstanceState == null) {
//            mfragment = (CameraFragment) CameraFragment.newInstance();
//            getFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, mfragment, CameraFragment.TAG)
//                    .commit();
//        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_FINISH) {
            String img_url = data.getStringExtra("img_url");
            Log.d("img_url",img_url +"test");
            File file = new File(img_url);
            if (file.exists()) {
                file.delete();
            }
        } else {
            mfragment.restartCamera2();
        }
    }

    public void onCancel(View view) {
        getFragmentManager().popBackStack();
    }
}
