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
import com.zhbit.lw.entity.ChatEntity;
import com.zhbit.lw.model.dao.UserTable;

import static com.zhbit.lw.entity.ChatEntity.CONTENT;
import static com.zhbit.lw.entity.ChatEntity.TYPE;
import static com.zhbit.lw.entity.ChatEntity.USER_NAME;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_NAME;

/**
 * Created by wjh on 17-5-13.
 */

public class ChatMsgListAdapter extends BaseAdapter implements View.OnClickListener, View.OnLongClickListener{

    private Context context;        // 上下文，用于绘制图象
    private ChatEntity chatEntity;      // 当前聊天对象

    // 适配器的构造方法
    public ChatMsgListAdapter(Context context, ChatEntity chatEntity) {
        this.context = context;
        this.chatEntity = chatEntity;
    }

    public int getCount() {
        return chatEntity.getChatContent().size();
    }

    @Override
    public Object getItem(int position) {
        return chatEntity.getChatContent().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 根据不同的对象获取不同的图象
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 判断消息是发送还是接受
        String flag = chatEntity.getChatContent().get(position).get(TYPE).toString();
        if (flag.equals("send")) {
            // 发送的则实例化右侧气泡布局
            convertView = View.inflate(context, R.layout.listview_row_chat_msg_right, null);

            // 获取消息并设置气泡内容
            TextView tvContent = (TextView) convertView.findViewById(R.id.rightMsg_content);
            tvContent.setText(chatEntity.getChatContent().get(position).get(CONTENT).toString());
            tvContent.setFocusable(true);
            tvContent.setOnLongClickListener(this);

            // 判断消息时间根据间隔设置时间
            TextView tvLastMsgTime = (TextView) convertView.findViewById(R.id.rightMsg_lastTime);
            tvLastMsgTime.setVisibility(View.VISIBLE);

            // 获取头像的View设置监听事件
            ImageView ivUserHead = (ImageView) convertView.findViewById(R.id.rightMsg_userHead);
            ivUserHead.setOnClickListener(this);

        }else if(flag.equals("receive")) {
            // 接受的则实例化左侧气泡布局
            convertView = View.inflate(context, R.layout.listview_row_chat_msg_left, null);

            // 获取消息并设置气泡内容
            TextView tvContent = (TextView) convertView.findViewById(R.id.leftMsg_content);
            tvContent.setText(chatEntity.getChatContent().get(position).get(CONTENT).toString());
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
                intent.putExtra(FRIEND_NAME, chatEntity.getTargetName());
                context.startActivity(intent);
                break;
            case R.id.rightMsg_userHead:
                intent = new Intent(context, FriendInforActivity.class);
                intent.putExtra(UserTable.USER_NAME, "Wjh");
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
