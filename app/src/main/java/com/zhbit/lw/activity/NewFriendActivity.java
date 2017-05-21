package com.zhbit.lw.activity;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.zhbit.lw.Logs.Logs;
import com.zhbit.lw.adapter.NewFriendListAdapter;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.ui.CustomToolbar;

import java.util.List;
import java.util.Map;

import static com.zhbit.lw.model.dao.UserTable.USER_ID;

public class NewFriendActivity extends ListActivity {

    private ListView newFriendListView;     // 新好友列表视图
    private List<Map<String, Object>> newFriendListData;    // 新好友列表适配器

    private CustomToolbar customToolbar;
    private Button btnAddNewFriend;

    private int userId;

    private final String NEW_FRIEND_INVITATION = "com.zhbit.lw.NEW_FRIEND_INVITATION";  //广播信号
    private LocalBroadcastManager localBroadcastManager; //广播管理者对象
    private BroadcastReceiver NEW_FRIEND_INVITATION_RECEIVER = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            Logs.d("NEW_FRIEND_INVITATION", " 新的好友申请");
            String account = intent.getStringExtra("account");
            if (intent.getIntExtra("type",0) == 1){
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(AddFriendActivity.this,account+"，请求添加您为好友",Toast.LENGTH_LONG).show();
//                    }
//                });
                Toast.makeText(NewFriendActivity.this,account+"，请求添加您为好友",Toast.LENGTH_LONG).show();
            }else if (intent.getIntExtra("type",0) == 0){
                Toast.makeText(NewFriendActivity.this,account+"，已添加您为好友",Toast.LENGTH_LONG).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);

        initView();     // 初始化试图
        initData();     // 初始化数据
        initEvent();    // 初始化点击事件

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        //注册广播
        localBroadcastManager.registerReceiver(NEW_FRIEND_INVITATION_RECEIVER, new IntentFilter(NEW_FRIEND_INVITATION));
    }

    // 初始化视图
    private void initView() {
        // 顶部Toolbar
        customToolbar = (CustomToolbar) findViewById(R.id.newFriend_toolbar);
        // 新好友列表
        newFriendListView = getListView();
        btnAddNewFriend = (Button) findViewById(R.id.btn_add_new_friend);
    }

    // 初始化数据
    private void initData() {
        // 设置顶部Toolbar的文字
        customToolbar.setTitle("新的朋友");
        // 设置顶部Toolbar的Overflow文字
        customToolbar.setOverflowTitle("添加好友");

        // 从ContactFragment中获取userId
        userId = getIntent().getIntExtra(USER_ID, -1);

//        newFriendListData = Model.getInstance().getDbManager().getFriendTableDao().getNewFriendListById();
        newFriendListData = Model.getInstance().getDbManager().getInvitationTableDao().getInvitationInfo();
        // 设置新好友列表适配器
        newFriendListView.setAdapter(new NewFriendListAdapter(this, newFriendListData));

    }

    // 初始化点击事件
    private void initEvent() {
        // 设置顶部Toolbar中Overflow的点击事件
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
//                Intent intent = new Intent(NewFriendActivity.this, FriendInforActivity.class);
//                intent.putExtra(USER_ID, userId);
//                intent.putExtra(FRIEND_ID, Integer.parseInt(newFriendListData.get(position).get(FRIEND_ID).toString()));
//                startActivity(intent);
//                Logs.d("ListView", " "+parent.toString()+view+"position:"+position+"  "+id);
//                Toast.makeText(NewFriendActivity.this, " "+parent.toString()+view+"position:"+position+"  "+id,Toast.LENGTH_LONG).show();
            }
        });
    }
}
