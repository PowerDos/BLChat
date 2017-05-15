package com.zhbit.lw.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.zhbit.lw.adapter.ChatMsgListAdapter;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.entity.ChatEntity;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.model.dao.UserTable;
import com.zhbit.lw.ui.CustomToolbar;

import java.util.List;
import java.util.Map;

import static com.zhbit.lw.model.dao.ChatTable.FRIEND_ID;
import static com.zhbit.lw.model.dao.ChatTable.USER_ID;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_NAME;

public class ChatMsgActivity extends ListActivity {

    private ListView chatMsgListView;       // 聊天界面聊天记录列表试图
    private List<Map<String, Object>> chatMsgDataList;      // 聊天界面聊天记录列表适配器

    private ChatEntity chatEntity;      // 聊天对象

    private CustomToolbar chatMsgToolbar;       // 顶部Toolbar

    private int userId, friendId;       // 用户和好友的Id

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

        // 暂时写死用户Id
        userId = getIntent().getIntExtra(USER_ID, -1);
        friendId = getIntent().getIntExtra(FRIEND_ID, -1);

        chatMsgDataList = Model.getInstance().getDbManager(this).getChatTableDao().getChatMsgList(userId, friendId);

        // 实例化当前聊天对象
        chatEntity = new ChatEntity();
        // 设置当前聊天对象的信息
        chatEntity.setUserId(userId);
        chatEntity.setFriendId(friendId);
        chatEntity.setChatMsgData(chatMsgDataList);

        // 设置当前界面Toolbar的标题为聊天对象的姓名
        chatMsgToolbar.setTitle(chatMsgDataList.get(0).get(FRIEND_NAME).toString());

        // 设置聊天信息列表的适配器
        chatMsgListView.setAdapter(new ChatMsgListAdapter(this, chatEntity));

    }

    // 初始化点击事件
    private void initEvent() {
        // 设置顶部Toolbar的overflow点击事件
        chatMsgToolbar.setOnOverflowClickListener(new CustomToolbar.OnOverflowClickListener() {
            @Override
            public void onOverflowClick() {
                Intent intent = new Intent(ChatMsgActivity.this, FriendInforActivity.class);
                intent.putExtra(UserTable.USER_ID, userId);
                intent.putExtra(FRIEND_ID, friendId);
                startActivity(intent);
            }
        });
    }

}
