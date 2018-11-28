package vn.com.phamvantruongit.customtakephoto;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Random;

public class PictureActivity extends AppCompatActivity {
    private Context mContext;
    private Resources mResources;
    private RelativeLayout mRelativeLayout;
    private Button mButton;
    private ImageView mImageView;
    private Random mRandom = new Random();
    private String img_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show);

        // Get the application context
        mContext = getApplicationContext();

        // Get the Resources
        mResources = getResources();

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
        mButton = (Button) findViewById(R.id.btn);
        mImageView = (ImageView) findViewById(R.id.iv);
        drawTextToBitmap(this,R.drawable.th,"Phạm Văn Trường");

    }




    public Bitmap drawTextToBitmap(Context mContext,  int resourceId,  String mText) {
        try {
            Resources resources = mContext.getResources();
            float scale = resources.getDisplayMetrics().density;
            Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);
            android.graphics.Bitmap.Config bitmapConfig =   bitmap.getConfig();
            // set default bitmap config if none
            if(bitmapConfig == null) {
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
            }
            // resource bitmaps are imutable,
            // so we need to convert it to mutable one
            bitmap = bitmap.copy(bitmapConfig, true);
            Canvas canvas = new Canvas(bitmap);

            Paint.FontMetrics fm = new Paint.FontMetrics();
            // new antialised Paint
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            // text color - #3D3D3D
            paint.setColor(Color.YELLOW);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            // text size in pixels
            paint.setTextSize((int) (15 * scale));
            // text shadow
            paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);

            paint.getFontMetrics(fm);
            int margin = 5;
//            canvas.drawRect(100-margin , 100 + fm.top - margin,
//                    100 + paint.measureText(mText) + margin, 100 + fm.bottom
//                            + margin, paint);
            // draw text to the Canvas center
            Rect bounds = new Rect();
            paint.getTextBounds(mText, 0, mText.length(), bounds);

            canvas.drawText(mText, 120,300 , paint);
            mImageView.setImageBitmap(bitmap);

            saveBitmap(bitmap);

            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public void saveBitmap(Bitmap bm) {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }

        File file = new File(Environment.getExternalStorageDirectory() + "/EventImage/");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!img_name.equals("")){
            File f = new File(file.getAbsolutePath() + "/", img_name+".png");
            if (f.exists()) {
                f.delete();
            }
        }
        img_name=System.currentTimeMillis()+"";
        File f = new File(file.getAbsolutePath() + "/", img_name+".png");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 0, out);
            out.flush();
            out.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
