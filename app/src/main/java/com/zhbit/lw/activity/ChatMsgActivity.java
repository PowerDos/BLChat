package com.zhbit.lw.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.zhbit.lw.adapter.ChatMsgListAdapter;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.bean.ChatInfo;
import com.zhbit.lw.ui.CustomToolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zhbit.lw.model.bean.ChatInfo.CONTENT;
import static com.zhbit.lw.model.bean.ChatInfo.TARGET_NAME;
import static com.zhbit.lw.model.bean.ChatInfo.TIME;
import static com.zhbit.lw.model.bean.ChatInfo.TYPE;
import static com.zhbit.lw.model.bean.ChatInfo.USER_NAME;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_NAME;

public class ChatMsgActivity extends ListActivity {

    private ListView chatMsgListView;       // 聊天界面聊天记录列表试图
    private List<Map<String, Object>> chatMsgDataList;      // 聊天界面聊天记录列表适配器

    private ChatInfo chatInfo;      // 聊天对象

    private CustomToolbar chatMsgToolbar;       // 顶部Toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_msg);

        initView();      // 初始化视图
        initData();      // 初始化数据
        initEvent();     // 初始化点击事件

    }

    // 初始化视图
    private void initView() {
        // 设置聊天信息列表
        chatMsgListView = getListView();
        chatMsgListView.setDivider(null);

        // 设置顶部Toolbar
        chatMsgToolbar = (CustomToolbar) findViewById(R.id.chatMsgToolbar);
        // 设置顶部Overflow的图标
        chatMsgToolbar.setOverflowImg(R.drawable.me);
    }

    // 初始化数据
    private void initData() {

        // 测试数据
        chatMsgDataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(TYPE, "send");
        map.put(TIME, "12:40");
        map.put(CONTENT, "You have a message. And how do you think of this color. And please reply me soon.");
        chatMsgDataList.add(map);

        map.put(TYPE, "send");
        map.put(TIME, "12:30");
        map.put(CONTENT, "You have a message. And how do you think of this color. And please reply me soon.");
        chatMsgDataList.add(map);

        for (int i = 0;i < 20;i++) {
            map = new HashMap<String, Object>();
            if (i%2 == 0) {
                map.put(TYPE, "send");
                map.put(TIME, "12:30");
                map.put(CONTENT, "So I recall you now.");
                chatMsgDataList.add(map);
            }else {
                map.put(TYPE, "receive");
                map.put(TIME, "12:30");
                map.put(CONTENT, "So I recall you now.");
                chatMsgDataList.add(map);
            }
        }

        // 实例化当前聊天对象
        chatInfo = new ChatInfo();
        // 设置当前聊天对象的信息
        chatInfo.setUserName(getIntent().getStringExtra(USER_NAME));
        chatInfo.setTargetName(getIntent().getStringExtra(TARGET_NAME));
        chatInfo.setChatContent(chatMsgDataList);

        // 实例化当前聊天对象
        // 以后采用这种方式　传ID获取聊天对象　数据库处理交给ChatEntity
//        chatInfo = new ChatInfo(getIntent().getIntExtra(USER_ID, -1), getIntent().getIntExtra(TARGET_ID, -1));

        // 设置当前界面Toolbar的标题为聊天对象的姓名
        chatMsgToolbar.setTitle(chatInfo.getTargetName());

        // 设置聊天信息列表的适配器
        chatMsgListView.setAdapter(new ChatMsgListAdapter(this, chatInfo));

    }

    // 初始化点击事件
    private void initEvent() {
        // 设置顶部Toolbar的overflow点击事件
        chatMsgToolbar.setOnOverflowClickListener(new CustomToolbar.OnOverflowClickListener() {
            @Override
            public void onOverflowClick() {
                Intent intent = new Intent(ChatMsgActivity.this, FriendInforActivity.class);
                intent.putExtra(FRIEND_NAME, chatInfo.getTargetName());
                startActivity(intent);
            }
        });
    }

}
