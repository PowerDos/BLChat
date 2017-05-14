package com.zhbit.lw.blchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhbit.lw.activity.MainActivity;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.model.dao.MomentTable;
import com.zhbit.lw.model.dao.UserTable;

/**
 *
 */
public class welcomePage extends Activity {
    private Handler handler = new Handler();
    private ImageView imgLoading;
    private RelativeLayout wel_page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        imgLoading = (ImageView) findViewById(R.id.img_loading);
        wel_page = (RelativeLayout) findViewById(R.id.activity_welcome_page);
        //显示动画
        showAnimation();
        Model.getInstance().getDbManager(this).getMomentTableDao().initMomentTableDao();
        Model.getInstance().getDbManager(this).getUserTableDao().initUserTableDao();
        Model.getInstance().getDbManager(this).getFriendTableDao().initFriendTableDao();
        //使用handler的postDelayed实现延时跳转
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(welcomePage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2600);//2.6秒后跳转至应用主界面MainActivity
    }

    private void showAnimation(){
        //页面渐变显示
        //创建透明动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        //创建
        alphaAnimation.setDuration(1000);
        //开始动画
        wel_page.startAnimation(alphaAnimation);


        //图片旋转
        //创建旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 760,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        //防止重复时卡顿
        rotateAnimation.setInterpolator(new LinearInterpolator());
        //设置重复次数
        rotateAnimation.setRepeatCount(2);
        //设置时间
        rotateAnimation.setDuration(2600);
        imgLoading.startAnimation(rotateAnimation);
    }
}
