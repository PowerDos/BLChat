package com.zhbit.lw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhbit.lw.blchat.R;
import com.zhbit.lw.entity.ChatEntity;
import com.zhbit.lw.ui.CustomToolbar;

import static com.zhbit.lw.entity.UserEntity.USER_NAME;


public class UserInforActivity extends AppCompatActivity {

    private TextView tvUserName;    // 用户名称视图
    private Button btnSendMsg;      // 发送消息按钮
    private String userName;        // 用户名称

    private CustomToolbar customToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);

        initView();       // 初始化试图
        initData();       // 初始化数据
        initEvent();      // 初始化事件监听器

    }

    // 初始化试图
    private void initView() {
        tvUserName = (TextView) findViewById(R.id.userinfor_userName);
        btnSendMsg = (Button) findViewById(R.id.userInfor_btnSendMsg);

        customToolbar = (CustomToolbar) findViewById(R.id.userInfor_toolbar);
    }

    // 初始化数据
    private void initData() {
        // 设置
        customToolbar.setTitle("详细资料");
        customToolbar.setOverflowImg(android.R.drawable.ic_notification_overlay);

        // 设置用户姓名
        userName = getIntent().getStringExtra(USER_NAME);
        tvUserName.setText(userName);
    }

    // 初始化点击事件
    private void initEvent() {
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInforActivity.this, ChatMsgActivity.class);
                intent.putExtra(ChatEntity.TARGET_NAME, userName);
                intent.putExtra(ChatEntity.USER_NAME, "wjh");
                finish();
                startActivity(intent);
            }
        });
    }

}
