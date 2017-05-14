package com.zhbit.lw.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhbit.lw.adapter.NewFriendListAdapter;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.ui.CustomToolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zhbit.lw.model.dao.UserTable.USER_NAME;

public class NewFriendActivity extends ListActivity {

    private ListView newFriendListView;     // 新好友列表视图
    private List<Map<String, Object>> newFriendListData;    // 新好友列表适配器

    private CustomToolbar customToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);

        initView();     // 初始化试图
        initData();     // 初始化数据
        initEvent();    // 初始化点击事件

    }

    // 初始化视图
    private void initView() {
        // 顶部Toolbar
        customToolbar = (CustomToolbar) findViewById(R.id.newFriend_toolbar);
        // 新好友列表
        newFriendListView = getListView();
    }

    // 初始化数据
    private void initData() {
        // 设置顶部Toolbar的Overflow文字
        customToolbar.setOverflowTitle("添加好友");

        newFriendListData = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", "一本正经、");
        map.put("requestMsg", "你好，asdklasdj我是一本正经、asdsaaskdjasld");
        newFriendListData.add(map);

        for(int i = 1;i < 10;i++) {
            map = new HashMap<String, Object>();
            map.put("userName", "一本正经、" + i);
            map.put("requestMsg", "你好，我是一本正经、" + i);
            newFriendListData.add(map);
        }

        // 设置新好友列表适配器
        newFriendListView.setAdapter(new NewFriendListAdapter(this, newFriendListData));
    }

    // 初始化点击事件
    private void initEvent() {
        customToolbar.setOnOverflowClickListener(new CustomToolbar.OnOverflowClickListener() {
            @Override
            public void onOverflowClick() {
                startActivity(new Intent(NewFriendActivity.this, AddFriendActivity.class));
            }
        });

        // 添加新好友列表项的点击事件
        newFriendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewFriendActivity.this, FriendInforActivity.class);
                intent.putExtra(USER_NAME, newFriendListData.get(position).get("userName").toString());
                startActivity(intent);
            }
        });

    }

}
