package com.zhbit.lw.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhbit.lw.activity.ChatMsgActivity;
import com.zhbit.lw.adapter.ChatListAdapter;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.model.bean.ChatInfo;
import com.zhbit.lw.model.bean.FriendInfo;
import com.zhbit.lw.model.dao.FriendTable;

import java.util.List;
import java.util.Map;

import static com.zhbit.lw.model.dao.ChatTable.FRIEND_ID;
import static com.zhbit.lw.model.dao.ChatTable.USER_ID;

/**
 * ChatFragment: 聊天列表界面
 * Created by wjh on 17-5-6.
 */

public class ChatFragment extends Fragment{

    private View view;      // 当前Fragment的视图
    private ListView chatListView;      // 聊天列表

    private List<Map<String, Object>> chatListData;     // 聊天列表数据
    private ChatListAdapter chatListAdapter;        // 聊天列表适配器

    private ChatInfo chatInfo;      // 聊天对象

    private int userId;     // 先写死　后期改成从互联网中获取

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_chat, container, false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();         // 初始化试图
        initData();         // 初始化数据
        initEvent();        // 初始化事件监听器

    }

    @Override
    public void onResume() {
        super.onResume();
        // resume时更新列表的数据
        chatListData = Model.getInstance().getDbManager().getChatTableDao().getChatList(userId);
        chatInfo.setRecentChatData(chatListData);
        chatListAdapter.notifyDataSetChanged();
    }

    // 初始化试图
    private void initView() {
        chatListView = (ListView) view.findViewById(R.id.chatListView);
    }

    // 初始化试图
    private void initData() {

        // 获取用户ID
        userId = Model.getInstance().getDbManager().getUserTableDao().getUserId();

        // 实例化聊天对象
        chatInfo = new ChatInfo();

        // 设置聊天列表数据
        chatListData = Model.getInstance().getDbManager().getChatTableDao().getChatList(userId);
        chatInfo.setRecentChatData(chatListData);

        // 设置列表监听器
        chatListAdapter = new ChatListAdapter(getActivity(), chatInfo);
        chatListView.setAdapter(chatListAdapter);
    }

    // 初始化试图
    private void initEvent() {
        // 设置chatListView的Item点击事件
        chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChatMsgActivity.class);
                FriendInfo friendInfo = Model.getInstance().getDbManager().getFriendTableDao().getFriendInfoById(Integer.parseInt(chatListData.get(position).get(FRIEND_ID).toString()));
                intent.putExtra(FriendTable.FRIEND_NAME,friendInfo.getFriendName());
                intent.putExtra(FriendTable.FRIEND_ACCOUNT,friendInfo.getFriendAccount());
                intent.putExtra(FriendTable.FRIEND_SEX,friendInfo.getFriendSex());
                intent.putExtra(FriendTable.FRIEND_HEAD,friendInfo.getFriendHead());
                intent.putExtra(FriendTable.FRIEND_ID,friendInfo.getFriendId());
                intent.putExtra(USER_ID, userId);
                startActivity(intent);
            }
        });

        // 设置chatListView的Item长按点击事件
        chatListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, int position, long id) {

                // 菜单标题
                String[] itemTitle = new String[]{"标记已读", "置顶聊天", "删除该聊天"};
                // 转化会final以便于匿名内部类调用
                final View listItemView = view;
                final int listItemIndex = position;

                // 长按后弹出菜单选择
                new AlertDialog.Builder(getActivity()).setItems(itemTitle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    // 标记已读
                                    case 0:
                                        break;
                                    // 置顶聊天
                                    case 1:
                                        // 获取用户选中的数据
                                        Map<String, Object> map = chatListData.get(listItemIndex);

                                        // 将数据删除并重新加到第一个当中
                                        chatListData.remove(listItemIndex);
                                        chatListData.add(0, map);
                                        // 更新chatListView的数据
                                        chatListAdapter.notifyDataSetChanged();
                                        break;
                                    // 删除该聊天
                                    case 2:
                                        // 删除数据
                                        chatListData.remove(listItemIndex);
                                        // 更新chatListView的数据
                                        chatListAdapter.notifyDataSetChanged();
                                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }
                ).show();
                return true;
            }
        });
    }
}
