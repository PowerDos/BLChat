package com.zhbit.lw.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.bean.ChatInfo;

import static com.zhbit.lw.model.bean.ChatInfo.LAST_CHAT_CONTENT;
import static com.zhbit.lw.model.bean.ChatInfo.LAST_CHAT_TIME;
import static com.zhbit.lw.model.bean.ChatInfo.TARGET_NAME;

/**
 *
 * Created by wjh on 17-5-13.
 */

public class ChatListAdapter extends BaseAdapter{

    private Context context;
    private ChatInfo chatInfo;

    public ChatListAdapter(Context context, ChatInfo chatInfo) {
        this.context = context;
        this.chatInfo = chatInfo;
    }

    @Override
    public int getCount() {
        return chatInfo.getRecentChatData().size();
    }

    @Override
    public Object getItem(int position) {
        return chatInfo.getRecentChatData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView =View.inflate(context, R.layout.listview_row_chat, null);

        // 获取所有视图
        ImageView ivTargetHead = (ImageView) convertView.findViewById(R.id.lvRow_targetHead);
        TextView tvTargetName = (TextView) convertView.findViewById(R.id.lvRow_targetName);
        TextView tvLastChatRecord = (TextView) convertView.findViewById(R.id.lvRow_lastChatContent);
        TextView tvLastChatTime = (TextView) convertView.findViewById(R.id.lvRow_lastChatTime);
        ImageView ivExpandRelation = (ImageView) convertView.findViewById(R.id.lvRow_expand);

        // 设置视图数据
        ivTargetHead.setImageResource(R.drawable.head);
        tvTargetName.setText(chatInfo.getRecentChatData().get(position).get(TARGET_NAME).toString());
        tvLastChatRecord.setText(chatInfo.getRecentChatData().get(position).get(LAST_CHAT_CONTENT).toString());
        tvLastChatTime.setText(chatInfo.getRecentChatData().get(position).get(LAST_CHAT_TIME).toString());
        ivExpandRelation.setImageResource(R.drawable.ic_menu_emoticons);

        return convertView;
    }
}
