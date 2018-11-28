package vn.com.phamvantruongit.customtakephoto.camerasquare;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import vn.com.phamvantruongit.customtakephoto.R;


public class ImageActivity extends Activity implements View.OnClickListener {
    private CustomImageView imageView;
    private ImageView button, img;
    private Thread thread;
    private ProgressDialog progressDialog;
    private ImageButton button_close, button_back;
    private String img_name = "";

    private boolean status = false;
    private String img_url, url;
    private boolean is_delete = false;
    private Button btnSave, btnAddText;
    EditText edMessage;
    TextView tvText;
    private String message = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image);
        button = findViewById(R.id.button);
        button_close = findViewById(R.id.button_close);
        button_back = findViewById(R.id.camera_back);
        img = findViewById(R.id.img);
        tvText = findViewById(R.id.tvText);
        btnAddText = findViewById(R.id.btnAddText);
        btnSave = findViewById(R.id.btnSave);
        edMessage = findViewById(R.id.editText);

        button_close.setOnClickListener(this);
        button_back.setOnClickListener(this);
        img.setImageBitmap(CameraFragment.bitmap);

        Intent intent = getIntent();
        img_url = intent.getExtras().getString("image_url");
        setImageView(img_url);


        btnAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMessage.getVisibility() == View.GONE) {
                    edMessage.setVisibility(View.VISIBLE);

                }

                if (message != null && message != "") {
                    setImageView(img_url);
                }

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMessage.getVisibility() == View.VISIBLE && edMessage.getText().toString().length() > 0) {
                    String path = img_url;
                    File mFile = new File(path);
                    Bitmap bitmap;
                    if (mFile.exists()) {
                        bitmap = BitmapFactory.decodeFile(path);
                        DrawTextToBitmap(bitmap, "はじめまして。私はアンナです。はじめまして。私はアンナです はじめまして。私はアンナです はじめまして。私はアンナです。はじめまして。私はアンナです はじめまして。私はアンナです");
                        //DrawTextToBitmap(bitmap, edMessage.getText().toString());
                    }

                } else {
                    String path = img_url;
                    File mFile = new File(path);
                    Bitmap bitmap;
                    if (mFile.exists()) {
                        bitmap = BitmapFactory.decodeFile(path);
                        DrawTextToBitmap(bitmap, "はじめまして。私はアンナです。はじめまして。私はアンナです");


                    }
                }
            }
        });


    }

    public void setImageView(String img_url) {
        String path = img_url;
        File mFile = new File(path);
        Bitmap bitmap;
        if (mFile.exists()) {
            bitmap = BitmapFactory.decodeFile(path);
            img.setImageBitmap(bitmap);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!is_delete == true) {
                showCameradialog();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Toast.makeText(ImageActivity.this, "Save image OK", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.putExtra("img_url",img_url);
                setResult(CameraSquareActivity.RESULT_FINISH,intent);
                ImageActivity.this.finish();

            }
        }
    };

    /**
     * 保存方法
     */
    //https://stackoverflow.com/questions/6756975/draw-multi-line-text-to-canvas
    public Bitmap DrawTextToBitmap(
            Bitmap bitmap,
            String message) {
        Resources resources = this.getResources();
        float scale = resources.getDisplayMetrics().density;

        android.graphics.Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }

        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.YELLOW);


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_layout_show, null);


        tvText = view.findViewById(R.id.tvMessage);
        tvText.setText(message);
        tvText.setDrawingCacheEnabled(true);
        tvText.setTextSize(10);
        tvText.setTextColor(Color.RED);
        this.message = message;


        int color = ContextCompat.getColor(this, R.color.color_show);
        tvText.setBackgroundColor(color);
        tvText.setMaxLines(2);
        tvText.measure(View.MeasureSpec.makeMeasureSpec(canvas.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(canvas.getHeight(), View.MeasureSpec.EXACTLY));
        tvText.layout(0, 0, tvText.getMeasuredWidth(), tvText.getMeasuredHeight());
        canvas.drawBitmap(tvText.getDrawingCache(), 0, 470, paint);
        tvText.setDrawingCacheEnabled(false);
        edMessage.setVisibility(View.GONE);

        saveBitmap(bitmap);

        img.setImageBitmap(bitmap);

        return bitmap;
    }


    public void saveBitmap(Bitmap bm) {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        File file = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                this.getString(R.string.app_name)
        );
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!img_name.equals("")) {
            File f = new File(file.getAbsolutePath() + "/", img_name + ".png");
            if (f.exists()) {
                f.delete();
            }
        }
        img_name = System.currentTimeMillis() + "";
        File f = new File(file.getAbsolutePath() + "/", img_name + ".png");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 0, out);
            out.flush();
            out.close();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            final Uri uri = Uri.fromFile(f);
            intent.setData(uri);
            this.sendBroadcast(intent);
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_close:
                Intent intent = new Intent(ImageActivity.this, CameraSquareActivity.class);
                setResult(CameraSquareActivity.RESULT_FINISH, intent);
                finish();
                break;
            case R.id.camera_back:
                showCameradialog();
                break;
        }
    }

    private void showCameradialog() {
        finish();
    }
}
