package com.zhbit.lw.ui;

import android.app.Instrumentation;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.zhbit.lw.blchat.R;

/**
 * Created by wjh on 17-5-13.
 */

public class CustomToolbar extends Toolbar{

    private View overflowBtn;
    private OnOverflowClickListener onOverflowClickListener;

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar);

        // 设置标题
        this.setTitle(ta.getString(R.styleable.CustomToolbar_titleText));
        // 设置返回键的图标
        this.setNavigationIcon(android.R.drawable.ic_media_rew);
        // 设置overflow图标
        this.inflateMenu(R.menu.toolbar_custom_overflow);
        overflowBtn = findViewById(R.id.custom_toolbar_overflow);

        // 设置返回键的监听事件
        this.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建新线程发送触动返回键的信息
                new Thread() {
                    public void run() {
                        Instrumentation inst = new Instrumentation();
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                    }
                }.start();
            }
        });

        // 设置overflow的点击事件
        overflowBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOverflowClickListener == null) {
                    return;
                }
                onOverflowClickListener.onOverflowClick();
            }
        });

    }

    // 设置Overflow的图标
    public void setOverflowImg(int iconRes) {
        this.getMenu().getItem(0).setIcon(iconRes);
    }

    // 设置Overflow的标题
    public void setOverflowTitle(String title) {
        this.getMenu().getItem(0).setTitle(title);
    }

    // 回调接口
    public void setOnOverflowClickListener(OnOverflowClickListener onOverflowClickListener) {
        this.onOverflowClickListener = onOverflowClickListener ;
    }

    // Overflow按钮点击事件的回调接口
    public interface OnOverflowClickListener{
        public void onOverflowClick();
    }

}
