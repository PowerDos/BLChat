package com.zhbit.lw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.bean.ChatInfo;
import com.zhbit.lw.model.bean.FriendInfo;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.ui.CustomToolbar;

import static com.zhbit.lw.model.dao.FriendTable.FRIEND_NAME;


public class FriendInforActivity extends AppCompatActivity {

    private TextView tvFriendName, tvFriendAccount;    // 用户名称视图
    private ImageView ivFriendHead, ivFriendSex;    // 用户性别视图
    private Button btnSendMsg;      // 发送消息按钮

    private CustomToolbar customToolbar;    // 顶部Toolbar

    private String friendName;        // 用户名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_infor);

        initView();       // 初始化试图
        initData();       // 初始化数据
        initEvent();      // 初始化事件监听器

    }

    // 初始化试图
    private void initView() {
        tvFriendName = (TextView) findViewById(R.id.friendInfor_userName);
        tvFriendAccount = (TextView) findViewById(R.id.friendInfor_userAccount);
        ivFriendHead = (ImageView) findViewById(R.id.friendInfor_userHead);
        ivFriendSex = (ImageView) findViewById(R.id.friendInfor_userSex);

        btnSendMsg = (Button) findViewById(R.id.friendInfor_btnSendMsg);

        customToolbar = (CustomToolbar) findViewById(R.id.friendInfor_toolbar);
    }

    // 初始化数据
    private void initData() {
        // 设置Toolbar
        customToolbar.setTitle("详细资料");
        customToolbar.setOverflowImg(R.drawable.overflow);

        // 获取当前好友姓名
        friendName = getIntent().getStringExtra(FRIEND_NAME);

        // 从数据库中获取用户信息
        FriendInfo friendInfo = Model.getInstance().getDbManager().getFriendTableDao().getFriendInforByUserName(friendName);

        if (friendInfo != null) {
            // 设置好友的界面数据
            tvFriendName.setText(friendInfo.getFriendName());

            tvFriendAccount.setText(friendInfo.getFriendAccount());
            if (friendInfo.getFriendSex().equals("男")) {
                ivFriendSex.setImageResource(R.drawable.user_sex_male);
            }else {
                ivFriendSex.setImageResource(R.drawable.user_sex_female);
            }
        }else{
            Toast.makeText(this, "请检查你的网络.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // 初始化点击事件
    private void initEvent() {
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendInforActivity.this, ChatMsgActivity.class);
                intent.putExtra(ChatInfo.TARGET_NAME, friendName);
                intent.putExtra(ChatInfo.USER_NAME, "wjh");
                finish();
                startActivity(intent);
            }
        });
    }

}
