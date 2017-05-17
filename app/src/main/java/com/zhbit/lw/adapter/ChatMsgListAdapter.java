package com.zhbit.lw.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhbit.lw.activity.FriendInforActivity;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.bean.ChatInfo;
import com.zhbit.lw.model.dao.ChatTable;
import com.zhbit.lw.model.dao.UserTable;

import static com.zhbit.lw.model.dao.ChatTable.CHAT_MSG_CONTENT;
import static com.zhbit.lw.model.dao.ChatTable.CHAT_MSG_TYPE;
import static com.zhbit.lw.model.dao.ChatTable.CHAT_MSG_TYPE_RECEIVER;
import static com.zhbit.lw.model.dao.ChatTable.CHAT_MSG_TYPE_SEND;
import static com.zhbit.lw.model.dao.ChatTable.USER_ID;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_ID;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_NAME;

/**
 * Created by wjh on 17-5-13.
 */

public class ChatMsgListAdapter extends BaseAdapter implements View.OnClickListener, View.OnLongClickListener{

    private Context context;        // 上下文，用于绘制图象
    private ChatInfo chatInfo;      // 当前聊天对象

    // 适配器的构造方法
    public ChatMsgListAdapter(Context context, ChatInfo chatInfo) {
        this.context = context;
        this.chatInfo = chatInfo;
    }

    public int getCount() {
        return chatInfo.getChatMsgData().size();
    }

    @Override
    public Object getItem(int position) {
        return chatInfo.getChatMsgData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 根据不同的对象获取不同的图象
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 判断消息是发送还是接受
        String flag = chatInfo.getChatMsgData().get(position).get(CHAT_MSG_TYPE).toString();
        if (flag.equals(CHAT_MSG_TYPE_SEND)) {
            // 发送的则实例化右侧气泡布局
            convertView = View.inflate(context, R.layout.listview_row_chat_msg_right, null);

            // 获取消息并设置气泡内容
            TextView tvContent = (TextView) convertView.findViewById(R.id.rightMsg_content);
            tvContent.setText(chatInfo.getChatMsgData().get(position).get(CHAT_MSG_CONTENT).toString());
            tvContent.setFocusable(true);
            tvContent.setOnLongClickListener(this);

            // 判断消息时间根据间隔设置时间
            TextView tvLastMsgTime = (TextView) convertView.findViewById(R.id.rightMsg_lastTime);
            tvLastMsgTime.setVisibility(View.VISIBLE);

            // 获取头像的View设置监听事件
            ImageView ivUserHead = (ImageView) convertView.findViewById(R.id.rightMsg_userHead);
            ivUserHead.setOnClickListener(this);

        }else if(flag.equals(CHAT_MSG_TYPE_RECEIVER)) {
            // 接受的则实例化左侧气泡布局
            convertView = View.inflate(context, R.layout.listview_row_chat_msg_left, null);

            // 获取消息并设置气泡内容
            TextView tvContent = (TextView) convertView.findViewById(R.id.leftMsg_content);
            tvContent.setText(chatInfo.getChatMsgData().get(position).get(ChatTable.CHAT_MSG_CONTENT).toString());
            tvContent.setFocusable(true);
            tvContent.setOnLongClickListener(this);

            // 判断消息时间根据间隔设置时间
            TextView tvLastMsgTime = (TextView) convertView.findViewById(R.id.leftMsg_lastTime);
            tvLastMsgTime.setVisibility(View.VISIBLE);

            // 获取头像的View设置监听事件
            ImageView ivUserHead = (ImageView) convertView.findViewById(R.id.leftMsg_userHead);
            ivUserHead.setOnClickListener(this);
        }
        return convertView;
    }

    // 头像点击事件
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.leftMsg_userHead:
                intent = new Intent(context, FriendInforActivity.class);
                intent.putExtra(USER_ID, chatInfo.getUserId());
                intent.putExtra(FRIEND_ID, chatInfo.getFriendId());
                context.startActivity(intent);
                break;
            case R.id.rightMsg_userHead:
                intent = new Intent(context, FriendInforActivity.class);
                intent.putExtra(USER_ID, chatInfo.getUserId());
                intent.putExtra(FRIEND_ID, chatInfo.getFriendId());
                context.startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.leftMsg_content:
                Toast.makeText(context, "You Long Click this.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rightMsg_content:
                Toast.makeText(context, "You Long Click this.", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
