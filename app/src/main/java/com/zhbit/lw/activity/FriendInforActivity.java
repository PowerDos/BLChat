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
import com.zhbit.lw.model.Model;
import com.zhbit.lw.model.bean.FriendInfo;
import com.zhbit.lw.model.dao.ChatTable;
import com.zhbit.lw.model.dao.FriendTable;
import com.zhbit.lw.model.dao.UserTable;
import com.zhbit.lw.ui.CustomToolbar;


public class FriendInforActivity extends AppCompatActivity {

    private TextView tvFriendName, tvFriendAccount;    // 用户名称视图
    private ImageView ivFriendHead, ivFriendSex;    // 用户性别视图
    private Button btnSendMsg;      // 发送消息按钮

    private CustomToolbar customToolbar;    // 顶部Toolbar

    private int userId, friendId;

    private FriendInfo friendInfo;

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
        // 初始化界面的基本视图
        tvFriendName = (TextView) findViewById(R.id.friendInfor_userName);
        tvFriendAccount = (TextView) findViewById(R.id.friendInfor_userAccount);
        ivFriendHead = (ImageView) findViewById(R.id.friendInfor_userHead);
        ivFriendSex = (ImageView) findViewById(R.id.friendInfor_userSex);

        // 发送消息按钮
        btnSendMsg = (Button) findViewById(R.id.friendInfor_btnSendMsg);

        // 顶部Toolbar
        customToolbar = (CustomToolbar) findViewById(R.id.friendInfor_toolbar);
    }

    // 初始化数据
    private void initData() {
        // 设置Toolbar
        customToolbar.setTitle("详细资料");
        customToolbar.setOverflowImg(R.drawable.overflow);

        // 获取用户Id和当前好友Id
        userId = getIntent().getIntExtra(UserTable.USER_ID, -1);
        friendId = getIntent().getIntExtra(FriendTable.FRIEND_ID, -1);

        // 从数据库中获取用户信息
        friendInfo = Model.getInstance().getDbManager().getFriendTableDao().getFriendInforByFriendId(friendId);

        // 判断是否成功获取好友信息
        if (friendInfo != null) {
            // 设置好友的界面数据
            tvFriendName.setText(friendInfo.getFriendName());
            tvFriendAccount.setText(friendInfo.getFriendAccount());
            // 判断性别设置性别图标
            if (friendInfo.getFriendSex().equals("男")) {
                ivFriendSex.setImageResource(R.drawable.user_sex_male);
            } else {
                ivFriendSex.setImageResource(R.drawable.user_sex_female);
            }
        }else{
            // 从数据库获取失败，提醒用户网络连接失败
            Toast.makeText(this, "网络连接失败, 请检查你的网络.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // 初始化点击事件
    private void initEvent() {
        // 发送信息按钮的点击事件
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendInforActivity.this, ChatMsgActivity.class);
                intent.putExtra(ChatTable.USER_ID, userId);
                intent.putExtra(ChatTable.FRIEND_ID, friendId);
                finish();
                startActivity(intent);
            }
        });
    }

}
