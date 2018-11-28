package vn.com.phamvantruongit.customtakephoto.camerasquare;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import vn.com.phamvantruongit.customtakephoto.R;


public class CameraSquareActivity extends Activity {

    public static final String TAG = CameraSquareActivity.class.getSimpleName();
    public static final int RESULT_FINISH = 1;
    private CameraFragment mfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squarecamera_camera);

        if (savedInstanceState == null) {
            mfragment = (CameraFragment) CameraFragment.newInstance();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, mfragment, CameraFragment.TAG)
                    .commit();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_FINISH) {
            finish();
        } else {
            mfragment.restartCamera2();
        }
    }

    public void onCancel(View view) {
        getFragmentManager().popBackStack();
    }
}
