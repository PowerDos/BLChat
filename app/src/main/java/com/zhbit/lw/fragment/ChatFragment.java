package com.zhbit.lw.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.zhbit.lw.blchat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ChatFragment: 聊天列表界面
 * Created by wjh on 17-5-6.
 */

public class ChatFragment extends Fragment{

    private View view;      // 当前Fragment的视图
    private ListView chatListView;      // 聊天列表
    private SimpleAdapter simpleAdapter;        // 聊天列表适配器

    private List<Map<String, Object>> chatListData;     // 聊天列表数据
    private String[] titleList;         // 用于适配器将视图和数据一一对应
    private int[] idList;               // 用于适配器将视图和数据一一对应

    // titleList和idList之间的桥梁
    public static final String HEAD = "userHead";
    public static final String USER_NAME = "userName";
    public static final String LAST_CHAT_TIME = "lastChatTime";
    public static final String LAST_CHAT_CONTENT = "lastChatContent";
    public static final String EXPAND_RELATION = "expandRelation";

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

    // 初始化试图
    private void initView() {
        chatListView = (ListView) view.findViewById(R.id.chatListView);

    // 用于适配器将视图和数据一一对应
        titleList = new String[]{HEAD, USER_NAME, LAST_CHAT_TIME, LAST_CHAT_CONTENT};
        idList = new int[]{R.id.lvRow_userHead, R.id.lvRow_userName, R.id.lvRow_lastChatTime, R.id.lvRow_lastChatContent};
    }

    // 初始化试图
    private void initData() {
        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.listview_row_chat, titleList, idList);
        chatListView.setAdapter(simpleAdapter);
    }

    // 初始化试图
    private void initEvent() {
        // 设置chatListView的Item点击事件
        chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "I am " + chatListData.get(position).get(USER_NAME), Toast.LENGTH_SHORT).show();
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
                                        simpleAdapter.notifyDataSetChanged();
                                        break;
                                    // 删除该聊天
                                    case 2:
                                        // 删除数据
                                        chatListData.remove(listItemIndex);
                                        // 更新chatListView的数据
                                        simpleAdapter.notifyDataSetChanged();
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

    // 获取chatListView需要的数据数据
    private List<Map<String, Object>> getData() {
        chatListData = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(HEAD, R.drawable.app_icon);
        map.put(USER_NAME, "一本正经、");
        map.put(LAST_CHAT_TIME, "4月21日");
        map.put(LAST_CHAT_CONTENT, "I miss you. ");
        chatListData.add(map);

        for (int i = 0;i < 100;i++) {
            map = new HashMap<String, Object>();
            map.put(HEAD, R.drawable.head);
            map.put(USER_NAME, "胡说八道、" + i);
            map.put(LAST_CHAT_TIME, "12:30");
            map.put(LAST_CHAT_CONTENT, "I miss you. And how about you.asd");
            chatListData.add(map);
        }

        return chatListData;
    }

}
