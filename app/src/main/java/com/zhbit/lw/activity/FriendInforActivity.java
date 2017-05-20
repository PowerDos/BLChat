package com.zhbit.lw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.model.bean.FriendInfo;
import com.zhbit.lw.model.bean.UserInfo;
import com.zhbit.lw.model.dao.ChatTable;
import com.zhbit.lw.model.dao.FriendTable;
import com.zhbit.lw.model.dao.UserTable;
import com.zhbit.lw.ui.CustomToolbar;


public class FriendInforActivity extends AppCompatActivity {

    private TextView tvFriendName, tvFriendAccount;    // 用户名称视图
    private ImageView ivFriendHead, ivFriendSex;    // 用户性别视图
    private Button btnSendMsg;      // 发送消息按钮
    private Button btnVideoChat;    // 聊天视频按钮

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
        btnVideoChat = (Button) findViewById(R.id.friendInfor_videoChat);

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
        tvFriendName.setText(getIntent().getStringExtra(FriendTable.FRIEND_NAME));
        tvFriendAccount.setText(getIntent().getStringExtra(FriendTable.FRIEND_ACCOUNT));
        // 判断性别设置性别图标
        if (getIntent().getStringExtra(FriendTable.FRIEND_SEX).equals("男")) {
            ivFriendSex.setImageResource(R.drawable.user_sex_male);
        } else {
            ivFriendSex.setImageResource(R.drawable.user_sex_female);
        }
        // 如果该用户不是好友
        if (getIntent().getIntExtra("isLocal",1) == 0) {
            // 设置两个按钮的布局属性
            btnSendMsg.setText("添加好友");
            btnVideoChat.setVisibility(View.GONE);
        }
    }

    // 初始化点击事件
    private void initEvent() {
        // 发送信息按钮的点击事件
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                如果不是好友的话
                if (getIntent().getIntExtra("isLocal",1) == 0){
                    //添加好友
                    Model.getInstance().getGlobalTheadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            //获取用户名
                            UserInfo userInfo = Model.getInstance().getDbManager().getUserTableDao().getUserInfor();
                            try {
                                //去环形服务器添加好友
                                EMClient.getInstance().contactManager().addContact(getIntent().getStringExtra(FriendTable.FRIEND_ACCOUNT), "添加好友");
                                //在UI中提示
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(FriendInforActivity.this, "发送添加好友申请成功",Toast.LENGTH_LONG).show();
                                    }
                                });
                            } catch (final HyphenateException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    //如果添加好友失败
                                    @Override
                                    public void run() {
                                        Toast.makeText(FriendInforActivity.this, "发送添加好友申请失败"+ e.toString(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
                }else {
                    //是好友就跳转到聊天页面
                    Intent intent = new Intent(FriendInforActivity.this, ChatMsgActivity.class);
                    intent.putExtra(ChatTable.USER_ID, userId);
                    intent.putExtra(ChatTable.FRIEND_ID, friendId);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

}
