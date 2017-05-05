package com.zhbit.lw.blchat;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by wjh on 17-5-6.
 */

public class ChangeColorIconWithText extends View{

    private int mColor = 0xFF45C01A;
    private Bitmap mIconBitmap;
    private String mText = "微信";
    private int mTextSize = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());

    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mpaint;

    private int mAplha;

    private Rect mIconRect;
    private Rect mTextBound;

    private Paint mTextPaint;

    public ChangeColorIconWithText(Context context) {
        super(context);
    }

    public ChangeColorIconWithText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChangeColorIconWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorIconWithText);

        int n = typedArray.getIndexCount();
        for(int i = 0;i < n;i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.ChangeColorIconWithText_icon:
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) typedArray.getDrawable(attr);
                    mIconBitmap = bitmapDrawable.getBitmap();
                    break;
                case R.styleable.ChangeColorIconWithText_color:
                    mColor = typedArray.getColor(attr, 0xFF45C01A);
                    break;
                case R.styleable.ChangeColorIconWithText_text:
                    mText = typedArray.getString(attr);
                    break;
                case R.styleable.ChangeColorIconWithText_text_size:
                    mTextSize = (int) typedArray.getDimension(attr,
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                                    getResources().getDisplayMetrics()));
                    break;
            }
        }

        typedArray.recycle();

        mTextBound = new Rect();
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0Xff555555);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int iconWidth = Math.min(getMeasuredWidth()-getPaddingLeft()-getPaddingRight(),
//                getMeasuredHeight()-getPaddingTop()-getPaddingBottom()-mTextBound.height());
//
//        int left = getMeasuredWidth() / 2 - iconWidth / 2;
//        int top = getMeasuredHeight()/2 - (mTextBound.height() + iconWidth)/2;
//        mIconRect = new Rect(left, top, left+iconWidth, top+iconWidth);
//            mIconRect = new Rect(100, 100, 100, 100);
//    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
//        super.onDraw(canvas);
//    }
}
