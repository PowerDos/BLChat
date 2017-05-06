package com.zhbit.lw.blchat;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 自定义底部Tab的View, 以实现颜色渐变
 * Created by wjh on 17-5-6.
 */

public class ChangeColorIconWithText extends View{

    // 自定义View的属性
    private int mColor = 0xFF45C01A;
    private Bitmap mIconBitmap;     //用于获取图像信息, 用于Tab的图标
    private String mText = "微信";    //图标下面的文字
    private int mTextSize = (int) TypedValue.applyDimension(        //字体大小
            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());

    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;

    private float mAlpha;

    private Rect mIconRect;
    private Rect mTextBound;
    private Paint mTextPaint;

    private static final String INSTANCE_STATUS = "instance_status";
    private static final String STATUS_ALPHA = "status_alpha";

    public ChangeColorIconWithText(Context context) {
        this(context, null);
    }

    public ChangeColorIconWithText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 获取laoout中自定义属性的值
     *
     * @param context   上下文
     * @param attrs     属性集
     * @param defStyleAttr  默认风格属性
     */
    public ChangeColorIconWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取风格属性放入类型数组
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorIconWithText);

        // 获取属性的总和
        int n = a.getIndexCount();
        // 遍历所有属性值
        for(int i = 0;i < n;i++) {
            int attr = a.getIndex(i);
            // 判断是什么属性, 对应不同的属性对各自的值初始化
            switch (attr) {
                case R.styleable.ChangeColorIconWithText_icon:
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) a.getDrawable(attr);
                    mIconBitmap = bitmapDrawable.getBitmap();
                    break;
                case R.styleable.ChangeColorIconWithText_color:
                    mColor = a.getColor(attr, 0xFF45C01A);
                    break;
                case R.styleable.ChangeColorIconWithText_text:
                    mText = a.getString(attr);
                    break;
                case R.styleable.ChangeColorIconWithText_text_size:
                    mTextSize = (int) a.getDimension(attr,
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                                    getResources().getDisplayMetrics()));
                    break;
            }
        }

        a.recycle();    //回收类型数组

        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0Xff555555);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iconWidth = Math.min(getMeasuredWidth()-getPaddingLeft()-getPaddingRight(),
                getMeasuredHeight()-getPaddingTop()-getPaddingBottom()-mTextBound.height());

        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        int top = getMeasuredHeight()/2 - (mTextBound.height() + iconWidth)/2;
        mIconRect = new Rect(left, top, left + iconWidth, top+iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);

        int alpha = (int) Math.ceil(255 * mAlpha);

        // 内存去准备mBitmapp, setAlpha, 纯色, xfermode, 图标
        setupTargetBitmap(alpha);
        // 绘制原文本
        drawSourceText(canvas, alpha);
        // 绘制变色的文本
        drawTargetText(canvas, alpha);

        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    /**
     * 在内存中绘制可变色的Icon
     *
     * @param alpha
     */
    private void setupTargetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);

        mCanvas.drawRect(mIconRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);

    }

    /**
     * 绘制原文本
     *
     * @param canvas
     * @param alpha
     */
    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255 - alpha);

        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    /**
     * 绘制变色的文本
     *
     * @param canvas
     * @param alpha
     */
    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);

        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);

    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putFloat(STATUS_ALPHA, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATUS_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    /**
     * 重绘
     */
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        }else {
            postInvalidate();
        }
    }

}
