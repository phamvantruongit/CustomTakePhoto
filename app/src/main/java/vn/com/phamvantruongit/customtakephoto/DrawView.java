package vn.com.phamvantruongit.customtakephoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.View;


public class DrawView extends View {
    public DrawView(Context context) {
        super(context);
        setFocusable(true);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();

        canvas.drawColor(Color.GREEN);

        Bitmap b = Bitmap.createBitmap(200, 200, Bitmap.Config.ALPHA_8);
        Canvas c = new Canvas(b);
        c.drawRect(0, 0, 200, 200, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        paint.setTextSize(40);
        paint.setTextScaleX(1.f);
        paint.setAlpha(0);
        paint.setAntiAlias(true);
        c.drawText("Your text", 30, 40, paint);
        paint.setColor(Color.RED);

        canvas.drawBitmap(b, 10,10, paint);
    }

}

