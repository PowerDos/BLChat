package com.zhbit.lw.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhbit.lw.blchat.R;
import com.zhbit.lw.entity.ChatEntity;

import static com.zhbit.lw.model.dao.ChatTable.CHAT_MSG_CONTENT;
import static com.zhbit.lw.model.dao.ChatTable.CHAT_MSG_TIME;
import static com.zhbit.lw.model.dao.FriendTable.FRIEND_NAME;

/**
 *
 * Created by wjh on 17-5-13.
 */

public class ChatListAdapter extends BaseAdapter{

    private Context context;
    private ChatEntity chatEntity;

    public ChatListAdapter(Context context, ChatEntity chatEntity) {
        this.context = context;
        this.chatEntity = chatEntity;
    }

    @Override
    public int getCount() {
        return chatEntity.getRecentChatData().size();
    }

    @Override
    public Object getItem(int position) {
        return chatEntity.getRecentChatData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView =View.inflate(context, R.layout.listview_row_chat, null);

        // 获取所有视图
        ImageView ivTargetHead = (ImageView) convertView.findViewById(R.id.lvRow_friendHead);
        TextView tvTargetName = (TextView) convertView.findViewById(R.id.lvRow_friendName);
        TextView tvLastChatRecord = (TextView) convertView.findViewById(R.id.lvRow_lastChatContent);
        TextView tvLastChatTime = (TextView) convertView.findViewById(R.id.lvRow_lastChatTime);
        ImageView ivExpandRelation = (ImageView) convertView.findViewById(R.id.lvRow_expand);

        // 设置视图数据
        ivTargetHead.setImageResource(R.drawable.head);
        tvTargetName.setText(chatEntity.getRecentChatData().get(position).get(FRIEND_NAME).toString());
        tvLastChatRecord.setText(chatEntity.getRecentChatData().get(position).get(CHAT_MSG_CONTENT).toString());
        tvLastChatTime.setText(chatEntity.getRecentChatData().get(position).get(CHAT_MSG_TIME).toString());
//        ivExpandRelation.setImageResource(R.drawable.ic_menu_emoticons);

        return convertView;
    }
}
