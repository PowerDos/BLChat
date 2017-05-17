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
import com.zhbit.lw.model.dao.FriendTable;
import com.zhbit.lw.model.dao.UserTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        String flag = chatInfo.getChatMsgData().get(position).get(ChatTable.CHAT_MSG_TYPE).toString();
        if (flag.equals(ChatTable.CHAT_MSG_TYPE_SEND)) {
            // 发送的则实例化右侧气泡布局
            convertView = View.inflate(context, R.layout.listview_row_chat_msg_right, null);

            // 获取消息并设置气泡内容
            TextView tvContent = (TextView) convertView.findViewById(R.id.rightMsg_content);
            tvContent.setText(chatInfo.getChatMsgData().get(position).get(ChatTable.CHAT_MSG_CONTENT).toString());
            tvContent.setFocusable(true);
            tvContent.setOnLongClickListener(this);

            // 判断显示时间的属性
            int showTimeFlag = Integer.parseInt(chatInfo.getChatMsgData().get(position).get(ChatTable.SHOW_TIME_FLAG).toString());
            if (showTimeFlag == ChatTable.SHOW_DATE) {
                // 获取显示时间的组件
                TextView tvLastMsgTime = (TextView) convertView.findViewById(R.id.rightMsg_lastTime);
                // 获取消息的时间
                String msgTime = chatInfo.getChatMsgData().get(position).get(ChatTable.CHAT_MSG_TIME).toString();
                // 截取日期的部分
                String date = msgTime.substring(0, msgTime.indexOf(" "));
                // 设置组件的属性
                tvLastMsgTime.setText(date);
                tvLastMsgTime.setVisibility(View.VISIBLE);
            }else if (showTimeFlag == ChatTable.SHOW_TIME){
                // 获取显示时间的组件
                TextView tvLastMsgTime = (TextView) convertView.findViewById(R.id.rightMsg_lastTime);
                // 获取消息的时间
                String msgTime = chatInfo.getChatMsgData().get(position).get(ChatTable.CHAT_MSG_TIME).toString();
                // 截取日期的部分
                String time = msgTime.substring(msgTime.indexOf(" ")+1, msgTime.lastIndexOf(":"));
                // 将截取出来的字符数组转化为字符串
                String timeResult = String.valueOf(time);
                // 设置组件的属性
                tvLastMsgTime.setText(timeResult);
                tvLastMsgTime.setVisibility(View.VISIBLE);
            }

            // 获取头像的View设置监听事件
            ImageView ivUserHead = (ImageView) convertView.findViewById(R.id.rightMsg_userHead);
            ivUserHead.setOnClickListener(this);

        }else if(flag.equals(ChatTable.CHAT_MSG_TYPE_RECEIVER)) {
            // 接受的则实例化左侧气泡布局
            convertView = View.inflate(context, R.layout.listview_row_chat_msg_left, null);

            // 获取消息并设置气泡内容
            TextView tvContent = (TextView) convertView.findViewById(R.id.leftMsg_content);
            tvContent.setText(chatInfo.getChatMsgData().get(position).get(ChatTable.CHAT_MSG_CONTENT).toString());
            tvContent.setFocusable(true);
            tvContent.setOnLongClickListener(this);

            // 判断是否显示时间
            int showTimeFlag = Integer.parseInt(chatInfo.getChatMsgData().get(position).get(ChatTable.SHOW_TIME_FLAG).toString());
            if (showTimeFlag == ChatTable.SHOW_DATE) {
                // 获取显示时间的组件
                TextView tvLastMsgTime = (TextView) convertView.findViewById(R.id.leftMsg_lastTime);
                // 获取消息的时间
                String msgTime = chatInfo.getChatMsgData().get(position).get(ChatTable.CHAT_MSG_TIME).toString();
                // 截取日期的部分
                String date = msgTime.substring(0, msgTime.indexOf(" "));
                // 将截取出来的字符数组转化为字符串
                String dateResult = String.valueOf(date);
                // 设置组件的属性
                tvLastMsgTime.setText(dateResult);
                tvLastMsgTime.setVisibility(View.VISIBLE);
            }else if (showTimeFlag == ChatTable.SHOW_TIME){
                // 获取显示时间的组件
                TextView tvLastMsgTime = (TextView) convertView.findViewById(R.id.leftMsg_lastTime);
                // 获取消息的时间
                String msgTime = chatInfo.getChatMsgData().get(position).get(ChatTable.CHAT_MSG_TIME).toString();
                // 截取日期的部分
                String time = msgTime.substring(msgTime.indexOf(" "+1), msgTime.lastIndexOf(":"));
                // 将截取出来的字符数组转化为字符串
                String timeResult = String.valueOf(time);
                // 设置组件的属性
                tvLastMsgTime.setText(timeResult);
                tvLastMsgTime.setVisibility(View.VISIBLE);
            }

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
                intent.putExtra(UserTable.USER_ID, chatInfo.getUserId());
                intent.putExtra(FriendTable.FRIEND_ID, chatInfo.getFriendId());
                context.startActivity(intent);
                break;
            case R.id.rightMsg_userHead:
                intent = new Intent(context, FriendInforActivity.class);
                intent.putExtra(UserTable.USER_ID, chatInfo.getUserId());
                intent.putExtra(FriendTable.FRIEND_ID, chatInfo.getFriendId());
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
