package vn.com.phamvantruongit.customtakephoto.camerasquare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

import vn.com.phamvantruongit.customtakephoto.R;

/**
 * Created by BraveSoft on 16/10/20.
 */
public class CustomImageView extends FrameLayout {

    private View view;
    private ImageView image_root,image_check;
    private Context mContext;
    private int width,heigth, img_width;

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view= LayoutInflater.from(context).inflate(R.layout.custom_imageview,this);
        this.mContext=context;
        initView(view);
    }

    private void initView(View view) {
        image_root= (ImageView) view.findViewById(R.id.img_root);
        image_check= (ImageView) view.findViewById(R.id.img_check);

    }

    public void setChecked(boolean isChecked) {
        if (isChecked) {
            image_check.setVisibility(VISIBLE);
        } else {
            image_check.setVisibility(GONE);
        }
    }

    public void addDefault(String img_url){
        String path= img_url;
        File mFile=new File(path);
        Bitmap bitmap=null;
        //若该文件存在
        if (mFile.exists()) {
            bitmap=BitmapFactory.decodeFile(path);
            image_root.setImageBitmap(bitmap);
        }
    }

//
//    public void addImage(ArrayList<ImageItem> items){
//
//        Bitmap bitmap1=null;
//        Bitmap firstBitmap=null;
//
//        if (items!=null||items.size()>0){
//            String path= items.get(0).getName();
//            File mFile=new File(path);
//            //若该文件存在
//            if (mFile.exists()) {
//                firstBitmap=BitmapFactory.decodeFile(path);
//                width = firstBitmap.getWidth();
//                heigth = firstBitmap.getHeight();
//            }
//            bitmap1 = Bitmap.createBitmap(width, heigth,
//                    firstBitmap.getConfig());
//            Bitmap bitmap_icon=null;
//            Bitmap bitmap;
//            Canvas canvas = new Canvas(bitmap1);
//            canvas.drawBitmap(firstBitmap, 0, 0, null);
//            for (int i = 1; i <items.size() ; i++) {
//                String p = getContext().getFilesDir().toString() + "/images/" + items.get(i).getName();//图片路径
//                File file = new File(p);
//                //若该文件存在
//
//                if (file.exists()) {
//
//                    bitmap_icon = BitmapFactory.decodeFile(p);
//                }
//                if (bitmap_icon != null) {
//                    img_width = bitmap_icon.getWidth();
//                }else {
//                    return;
//                }
//
//                float scale = 0.0f;
//                scale = (float) ((width * items.get(i).getScale()) / img_width);
//                bitmap = small(bitmap_icon, scale);
//                Log.d("PATH", bitmap.getWidth()+"---"+bitmap.getHeight());
//                canvas.drawBitmap(bitmap, (int) ((width * items.get(i).getWidth_position())), (int) ((width * items.get(i).getHeight_position())), null);
//                bitmap_icon.recycle();
//                bitmap_icon = null;
//                bitmap.recycle();
//            }
//            image_root.setImageBitmap(bitmap1);
//
//
//        }
//    }

    private  Bitmap small(Bitmap bitmap,float size) {
        Matrix matrix = new Matrix();
        matrix.postScale(size,size);
        int bm_width=bitmap.getWidth();
        int bm_height=bitmap.getHeight();
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0,bm_width,
                bm_height, matrix, true);
        return resizeBmp;
    }

}
