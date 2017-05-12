package com.zhbit.lw.activity;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zhbit.lw.blchat.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class ChatActivity extends ListActivity {

    private ListView chatMsgListView;
    private List<Map<String, Object>> chatMsgData;

    public static final String CONTENT = "content";
    public static final String TYPE = "type";
    public static final String TIME = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initView();
        initData();
        initEvent();

    }

    private void initView() {
        chatMsgListView = getListView();
        chatMsgListView.setDivider(null);
    }

    private void initData() {
        chatMsgData = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(TYPE, "send");
        map.put(CONTENT, "You have a message. And how do you think of this color. And please reply me soon.");
        chatMsgData.add(map);

        map.put(TYPE, "send");
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

    private void initEvent() {
    }

    class ChatMsgListAdapter extends BaseAdapter{

        private TextView tvLastMsgTime;

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
            tvLastMsgTime = new TextView(ChatActivity.this);

            String flag = chatMsgData.get(position).get(TYPE).toString();
            if (flag.equals("send")) {
                convertView = getLayoutInflater().inflate(R.layout.listview_row_chat_msg_right, null);
                TextView tvContent = (TextView) convertView.findViewById(R.id.rightMsg_content);
                tvContent.setText(chatMsgData.get(position).get(CONTENT).toString());

            }else if(flag.equals("receive")) {
                convertView = getLayoutInflater().inflate(R.layout.listview_row_chat_msg_left, null);

                // 获取消息并设置气泡
                TextView tvContent = (TextView) convertView.findViewById(R.id.leftMsg_content);
                tvContent.setText(chatMsgData.get(position).get(CONTENT).toString());
            }
            return convertView;
        }
    }

}
