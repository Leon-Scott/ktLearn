package com.android.myapplication.view;

/**
 * @author longbin
 * @date 2026/3/9
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.animation.ValueAnimator;

public class VideoPlayingIndicatorView extends View {
    // 画笔
    private Paint mPaint;
    // 三条竖线的宽度和间距
    private float mLineWidth;
    private float mLineSpacing;
    // 三条竖线的当前高度比例 (0~1)
    private float mLeftLineScale = 0.3f;   // 初始为1/3
    private float mMiddleLineScale = 0.3f;
    private float mRightLineScale = 0.3f;
    // 动画控制器
    private ValueAnimator mLeftAnimator;
    private ValueAnimator mMiddleAnimator;
    private ValueAnimator mRightAnimator;
    // 颜色配置（可自定义）
    private int mLineColor = Color.WHITE;

    public VideoPlayingIndicatorView(Context context) {
        super(context);
        init();
    }

    public VideoPlayingIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoPlayingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化画笔和动画
     */
    private void init() {
        // 初始化画笔
        mPaint = new Paint();
        mPaint.setColor(mLineColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        // 初始化动画
        initAnimations();
    }

    /**
     * 初始化三条竖线的动画
     */
    private void initAnimations() {
        // 中间竖线：最快，线性插值器（匀速）
        mMiddleAnimator = ValueAnimator.ofFloat(0.3f, 1.0f, 0.3f);
        mMiddleAnimator.setDuration(600);  // 最短的动画周期，速度最快
        mMiddleAnimator.setInterpolator(new LinearInterpolator());
        mMiddleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mMiddleAnimator.addUpdateListener(animation -> {
            mMiddleLineScale = (float) animation.getAnimatedValue();
            invalidate(); // 重绘View
        });

        // 左侧竖线：减速效果，周期更长
        mLeftAnimator = ValueAnimator.ofFloat(0.3f, 1.0f, 0.3f);
        mLeftAnimator.setDuration(800);
        // 加速减速插值器，模拟减速效果
        mLeftAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mLeftAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mLeftAnimator.addUpdateListener(animation -> {
            mLeftLineScale = (float) animation.getAnimatedValue();
            invalidate();
        });

        // 右侧竖线：减速效果，周期比中间长，与左侧略有差异
        mRightAnimator = ValueAnimator.ofFloat(0.3f, 1.0f, 0.3f);
        mRightAnimator.setDuration(900);
        mRightAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mRightAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mRightAnimator.addUpdateListener(animation -> {
            mRightLineScale = (float) animation.getAnimatedValue();
            invalidate();
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 计算竖线宽度和间距（适配View尺寸）
        mLineWidth = w / 8f; // 竖线宽度为View宽度的1/8
        mLineSpacing = w / 8f; // 竖线间距为View宽度的1/8
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        // View的总高度（作为竖线的最大高度）
        float viewHeight = getHeight();
        // 竖线的最低高度：viewHeight * 1/3
        float minHeight = viewHeight * 0.3f;

        // 计算每条竖线的实际高度（确保最低为1/3）
        float leftLineHeight = Math.max(minHeight, viewHeight * mLeftLineScale);
        float middleLineHeight = Math.max(minHeight, viewHeight * mMiddleLineScale);
        float rightLineHeight = Math.max(minHeight, viewHeight * mRightLineScale);

        // 计算每条竖线的绘制位置（居中绘制）
        float startX = (getWidth() - (3 * mLineWidth + 2 * mLineSpacing)) / 2;

        // 绘制左侧竖线
        float leftLineTop = (viewHeight - leftLineHeight) / 2;
        canvas.drawRect(
                startX,
                leftLineTop,
                startX + mLineWidth,
                leftLineTop + leftLineHeight,
                mPaint
        );

        // 绘制中间竖线
        float middleLineTop = (viewHeight - middleLineHeight) / 2;
        canvas.drawRect(
                startX + mLineWidth + mLineSpacing,
                middleLineTop,
                startX + 2 * mLineWidth + mLineSpacing,
                middleLineTop + middleLineHeight,
                mPaint
        );

        // 绘制右侧竖线
        float rightLineTop = (viewHeight - rightLineHeight) / 2;
        canvas.drawRect(
                startX + 2 * mLineWidth + 2 * mLineSpacing,
                rightLineTop,
                startX + 3 * mLineWidth + 2 * mLineSpacing,
                rightLineTop + rightLineHeight,
                mPaint
        );
    }

    /**
     * 开始动画（播放）
     */
    public void startAnimation() {
        if (!mLeftAnimator.isRunning()) {
            mLeftAnimator.start();
        }
        if (!mMiddleAnimator.isRunning()) {
            mMiddleAnimator.start();
        }
        if (!mRightAnimator.isRunning()) {
            mRightAnimator.start();
        }
    }

    /**
     * 停止动画（暂停）
     */
    public void stopAnimation() {
        mLeftAnimator.cancel();
        mMiddleAnimator.cancel();
        mRightAnimator.cancel();
        // 重置为初始状态（1/3高度）
        mLeftLineScale = 0.3f;
        mMiddleLineScale = 0.3f;
        mRightLineScale = 0.3f;
        invalidate();
    }

    /**
     * 设置竖线颜色（可选自定义）
     */
    public void setLineColor(int color) {
        mLineColor = color;
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // View附着到窗口时启动动画
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // View脱离窗口时停止动画，避免内存泄漏
        stopAnimation();
    }
}
