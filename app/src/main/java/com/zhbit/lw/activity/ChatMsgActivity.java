package com.zhbit.lw.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhbit.lw.adapter.ChatMsgListAdapter;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.model.bean.ChatInfo;
import com.zhbit.lw.model.dao.ChatTable;
import com.zhbit.lw.model.dao.FriendTable;
import com.zhbit.lw.model.dao.UserTable;
import com.zhbit.lw.ui.CustomToolbar;
import com.zhbit.lw.util.DensityUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatMsgActivity extends ListActivity {

    private CustomToolbar chatMsgToolbar;       // 顶部Toolbar

    private EditText etSendContent;     // 用户想要发送内容
    private Button btnSendMsg;      // 发送消息按钮
    private int btnSendMsgFlag;     // 判断发送消息按钮状态的Flag

    private ListView chatMsgListView;       // 聊天界面聊天记录列表试图
    private ChatMsgListAdapter chatMsgListAdapter;  // 聊天记录列表适配器
    private ChatInfo chatInfo;      // 聊天对象

    private int userId, friendId;       // 用户和好友的Id

    public static int BTN_SEND_STATU = 1;   // 发送按钮处于发送状态
    public static int BTN_ADD_STATU = 0;    // 发送按钮处于更多功能状态

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

        // 文本编辑框－发送内容
        etSendContent = (EditText) findViewById(R.id.chatMsg_etSendContent);
        btnSendMsg = (Button) findViewById(R.id.chatMsg_btnSendMsg);

    }

    // 初始化数据
    private void initData() {

        // 暂时写死用户Id
        userId = getIntent().getIntExtra(UserTable.USER_ID, -1);
        friendId = getIntent().getIntExtra(FriendTable.FRIEND_ID, -1);
        if (userId == -1 || friendId == -1) {
            Toast.makeText(this, "获取聊天记录失败, 请检查你的网络.", Toast.LENGTH_SHORT).show();
            finish();
        }

        // 默认发送消息按钮为更多功能的状态
        btnSendMsgFlag = BTN_ADD_STATU;

        // 实例化当前聊天对象
        chatInfo = Model.getInstance().getDbManager().getChatTableDao().getChatMsgInfo(userId, friendId);

        // 设置当前界面Toolbar的标题为聊天对象的姓名
        chatMsgToolbar.setTitle(chatInfo.getFriendName());

        // 设置聊天信息列表的适配器
        chatMsgListAdapter = new ChatMsgListAdapter(this, chatInfo);
        chatMsgListView.setAdapter(chatMsgListAdapter);

    }

    // 初始化点击事件
    private void initEvent() {
        // 设置顶部Toolbar的overflow点击事件
        chatMsgToolbar.setOnOverflowClickListener(new CustomToolbar.OnOverflowClickListener() {
            @Override
            public void onOverflowClick() {
                Intent intent = new Intent(ChatMsgActivity.this, FriendInforActivity.class);
                intent.putExtra(UserTable.USER_ID, userId);
                intent.putExtra(FriendTable.FRIEND_ID, friendId);
                startActivity(intent);
            }
        });

        // 设置输入框的监听事件, 当用户输入内容后对按钮作变化
        etSendContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 判断内容改变后的值
                String content = String.valueOf(s);
                // 如果没有内容设置图标为更多
                // 5dp 10dp
                int pxFive = DensityUtil.dip2px(ChatMsgActivity.this, 5);
                int pxTen = DensityUtil.dip2px(ChatMsgActivity.this, 10);
                if (content.equals("")) {
                    btnSendMsg.getLayoutParams().width = DensityUtil.dip2px(ChatMsgActivity.this, 40);
                    btnSendMsg.getLayoutParams().height = DensityUtil.dip2px(ChatMsgActivity.this, 40);
                    ((LinearLayout.LayoutParams) btnSendMsg.getLayoutParams()).setMargins(pxFive, pxFive, pxFive, pxFive);
                    btnSendMsg.setBackgroundResource(R.drawable.send_msg_add);
                    btnSendMsg.setText("");
                    btnSendMsgFlag = BTN_ADD_STATU;
                }else{
                    // 根据dp转px, 动态的修改按钮的布局
                    btnSendMsg.getLayoutParams().width = DensityUtil.dip2px(ChatMsgActivity.this, 45);
                    btnSendMsg.getLayoutParams().height = DensityUtil.dip2px(ChatMsgActivity.this, 30);
                    ((LinearLayout.LayoutParams) btnSendMsg.getLayoutParams()).setMargins(pxFive, pxTen, pxFive, pxTen);
                    btnSendMsg.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    btnSendMsg.setText("发送");
                    btnSendMsgFlag = BTN_SEND_STATU;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 设置发送按钮的监听事件
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断发送按钮的的状态
                if (btnSendMsgFlag == BTN_ADD_STATU) {
                }else{

                    List<Map<String, Object>> chatListData = chatInfo.getChatMsgData();

                    // 获取上一跳消息的时间
                    String lastMsgTime = chatListData.get(chatListData.size()-1).get(ChatTable.CHAT_MSG_TIME).toString();

                    try {
                        // 设置的内容
                        String msgContent = etSendContent.getText().toString();
                        String currentTime;
                        int showTimeFlag;

                        // 获取当前时间
                        Date currentDate = new Date();     // 创建一个时间对象，获取到当前的时间
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间显示格式
                        currentTime = sdf.format(currentDate);

                        Date lastMsgDate = sdf.parse(lastMsgTime);
                        // 判断消息的时间是否超过两分钟
                        if ((currentDate.getTime() - lastMsgDate.getTime())/(60*1000) > 3) {
                            showTimeFlag = ChatTable.HIDE_TIME;
                        }else{
                            showTimeFlag = ChatTable.SHOW_TIME;
                        }

                        // 更新当前聊天界面的列表数据
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(ChatTable.CHAT_MSG_CONTENT, msgContent);
                        map.put(ChatTable.CHAT_MSG_TIME, currentTime);
                        map.put(ChatTable.CHAT_MSG_TYPE, ChatTable.CHAT_MSG_TYPE_SEND);
                        map.put(ChatTable.SHOW_TIME_FLAG, showTimeFlag);

                        chatListData.add(map);
                        chatInfo.setChatMsgData(chatListData);
                        chatMsgListAdapter.notifyDataSetChanged();

                        // 将聊天记录插入数据库当中
                        Model.getInstance().getDbManager().getChatTableDao().insertNewChatMsg(userId, friendId, msgContent, currentTime, ChatTable.CHAT_MSG_TYPE_SEND, showTimeFlag);

                        // 置空输入框
                        etSendContent.setText("");

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

}
