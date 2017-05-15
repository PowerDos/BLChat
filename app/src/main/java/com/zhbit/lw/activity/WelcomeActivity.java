package com.zhbit.lw.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.hyphenate.chat.EMClient;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.Model;

/**
 *
 */
public class WelcomeActivity extends Activity {
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            // 如果当前Activity已经退出，就不处理handle中的嘻嘻
            if (isFinishing()){
                return;
            }
            //判断进入主界面还是登陆界面
            isMainOrLogin();
        }

    };
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
        Model.getInstance().getDbManager().getMomentTableDao().initMomentTableDao();
        Model.getInstance().getDbManager().getUserTableDao().initUserTableDao();
        Model.getInstance().getDbManager().getFriendTableDao().initFriendTableDao();
        // 发送2.6秒延时信息
        handler.sendMessageDelayed(Message.obtain(), 2000);
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
    private void isMainOrLogin(){
        //加入线程池
        Model.getInstance().getGlobalTheadPool().execute(new Runnable() {
            @Override
            public void run() {
                //判断当前账号是否登陆过
                if (EMClient.getInstance().isLoggedInBefore()){
                    //登陆过
                    //获取到当前登陆用户的信息

                    //跳转到主页面
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    //没有登陆过
                    //跳转到登录页面
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });
    }

    protected void onDestroy(){
        super.onDestroy();
        // 销毁handler里面的消息
        handler.removeCallbacksAndMessages(null);
    }
}
