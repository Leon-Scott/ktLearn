package imageSlide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.myapplication.R;

public class ImageSwitcherView extends View {

    private Bitmap[] images;
    private int currentIndex = 0;
    private float touchX;
    private float offsetX;
    private boolean isSliding = false;
    private Paint paint;

    public ImageSwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        // 加载图片资源
        images = new Bitmap[]{
                BitmapFactory.decodeResource(getResources(), R.drawable.img1),
                BitmapFactory.decodeResource(getResources(), R.drawable.img2),
                BitmapFactory.decodeResource(getResources(), R.drawable.img3)
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制当前图片
        Bitmap currentImage = images[currentIndex];
        float alpha = 1f;
        if (isSliding) {
            // 根据滑动偏移量计算透明度
            alpha = 1 - Math.abs(offsetX) / getWidth();
        }
        paint.setAlpha((int) (alpha * 255));
        canvas.drawBitmap(currentImage, 0, 0, paint);

        // 如果正在滑动，绘制下一张图片
        if (isSliding && currentIndex < images.length - 1) {
            Bitmap nextImage = images[currentIndex + 1];
            paint.setAlpha((int) ((1 - alpha) * 255));
            canvas.drawBitmap(nextImage, offsetX, 0, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                isSliding = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = event.getX();
                offsetX = currentX - touchX;
                isSliding = true;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (isSliding) {
                    if (offsetX > getWidth() / 2) {
                        currentIndex++;
                    } else if (offsetX < -getWidth() / 2) {
                        currentIndex--;
                    }
                    offsetX = 0;
                    isSliding = false;
                    invalidate();
                }
                break;
        }
        return true;
    }
}
