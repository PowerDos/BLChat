package com.zhbit.lw.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zhbit.lw.blchat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends ListActivity {

    private ListView chatMsgListView;       // 聊天界面聊天记录列表试图
    private List<Map<String, Object>> chatMsgData;      // 聊天界面聊天记录列表适配器

    public static final String CONTENT = "content";
    public static final String TYPE = "type";
    public static final String TIME = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initView();      // 初始化视图
        initData();      // 初始化数据
        initEvent();     // 初始化点击事件

    }

    // 初始化视图
    private void initView() {
        chatMsgListView = getListView();
        chatMsgListView.setDivider(null);
    }

    // 初始化数据
    private void initData() {
        chatMsgData = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(TYPE, "send");
        map.put(TIME, "12:40");
        map.put(CONTENT, "You have a message. And how do you think of this color. And please reply me soon.");
        chatMsgData.add(map);

        map.put(TYPE, "send");
        map.put(TIME, "12:30");
        map.put(CONTENT, "You have a message. And how do you think of this color. And please reply me soon.");
        chatMsgData.add(map);

        for (int i = 0;i < 20;i++) {
            map = new HashMap<String, Object>();
            if (i%2 == 0) {
                map.put(TYPE, "send");
                map.put(TIME, "12:30");
                map.put(CONTENT, "So I recall you now.");
                chatMsgData.add(map);
            }else {
                map.put(TYPE, "receive");
                map.put(TIME, "12:30");
                map.put(CONTENT, "So I recall you now.");
                chatMsgData.add(map);
            }
        }
        chatMsgListView.setAdapter(new ChatMsgListAdapter());

    }

    // 初始化点击事件
    private void initEvent() {
    }

    class ChatMsgListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return chatMsgData.size();
        }

        @Override
        public Object getItem(int position) {
            return chatMsgData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 判断消息是发送还是接受
            String flag = chatMsgData.get(position).get(TYPE).toString();
            if (flag.equals("send")) {
                // 发送的则实例化右侧气泡布局
                convertView = getLayoutInflater().inflate(R.layout.listview_row_chat_msg_right, null);

                // 获取消息并设置气泡内容
                TextView tvContent = (TextView) convertView.findViewById(R.id.rightMsg_content);
                tvContent.setText(chatMsgData.get(position).get(CONTENT).toString());

                // 判断消息时间根据间隔设置时间
                TextView tvLastMsgTime = (TextView) convertView.findViewById(R.id.rightMsg_lastTime);
                tvLastMsgTime.setVisibility(View.VISIBLE);
            }else if(flag.equals("receive")) {
                // 接受的则实例化左侧气泡布局
                convertView = getLayoutInflater().inflate(R.layout.listview_row_chat_msg_left, null);

                // 获取消息并设置气泡内容
                TextView tvContent = (TextView) convertView.findViewById(R.id.leftMsg_content);
                tvContent.setText(chatMsgData.get(position).get(CONTENT).toString());

                // 判断消息时间根据间隔设置时间
                TextView tvLastMsgTime = (TextView) convertView.findViewById(R.id.leftMsg_lastTime);
                tvLastMsgTime.setVisibility(View.VISIBLE);
            }
            return convertView;
        }
    }

}
