package com.zhbit.lw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhbit.lw.activity.NewFriendActivity;
import com.zhbit.lw.activity.UserInforActivity;
import com.zhbit.lw.blchat.R;


/**
 * ContactFragment: 好友列表界面
 * Created by wjh on 17-5-6.
 */

public class ContactFragment extends Fragment implements View.OnClickListener{

    private View view;      // 当前fragment的视图
    private View headerView;    // expandableListView的表头
    private ExpandableListView contactExpandableListView;     // 通讯录扩展列表

    private ImageView imgNewFriend;     // exoandableListView表头当中的新朋友
    private ImageView imgGroupChat;     // exoandableListView表头当中的群聊
    private ImageView imgRelation;      // exoandableListView表头当中的关系

    private String[] parentList;    // 分组名称
    private String[][] childList;   // 通讯录列表名称

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_contact, container, false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

         initView();        // 初始化视图
         initData();        // 初始化数据
         initEvent();       // 初始化点击事件

    }

    // 初始化视图
    private void initView() {
        // 通讯录分组列表
        contactExpandableListView = (ExpandableListView) view.findViewById(R.id.contactExpandableListView);
        // 设置分组的指示图标
//        contactExpandableListView.setGroupIndicator(null);

        // 设置通讯录列表的表头
        headerView = View.inflate(getActivity(), R.layout.expandlistview_header, null);
        contactExpandableListView.addHeaderView(headerView, null, true);

        // 通讯录列表表头当中的三个图片
        imgNewFriend = (ImageView) headerView.findViewById(R.id.expandLvHeader_new_frined);
        imgGroupChat = (ImageView) headerView.findViewById(R.id.expandLvHeader_group_chat);
        imgRelation = (ImageView) headerView.findViewById(R.id.expandLvHeader_relation);

    }

    // 初始化数据
    private void initData() {
        // 设置父列表和子列表的数据
        parentList = new String[]{"friend", "family", "class"};
        childList = new String[][]{{"果冻名", "王博", "小陪"}, {"爸爸", "妈妈", "爷爷", "妈妈", "爷爷", "妈妈", "爷爷", "妈妈", "爷爷", "妈妈", "爷爷", "妈妈", "爷爷", "妈妈", "爷爷", "妈妈", "爷爷", "妈妈", "爷爷", "妈妈", "爷爷", "妈妈", "爷爷"}, {"小龙", "小红", "小花"}};

        // 设置通讯录扩展列表的适配器
        ContactExpandableListAdapter adapter = new ContactExpandableListAdapter();
        contactExpandableListView.setAdapter(adapter);

    }

    // 初始化点击事件
    private void initEvent() {

        // 设置通讯录列表表头三个图片的点击事件
        imgNewFriend.setOnClickListener(this);
        imgGroupChat.setOnClickListener(this);
        imgRelation.setOnClickListener(this);

        // 通讯录扩展列表的点击事件
        contactExpandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // 如果为表头则返回
                if (position == 0) {
                    return false;
                }
                // 如果contact_name的Tag为-1, 代表其为通讯录列表
                int parentPosition = (int) view.getTag(R.id.expandLvRow_group_name);
                int childPosition = (int) view.getTag(R.id.expandLvRow_contact_name);
                if (childPosition == -1) {
                    Toast.makeText(getActivity(), "I am group. " + parentList[parentPosition], Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "I am child. " + childList[parentPosition][childPosition], Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        // 设置通讯录列表的点击事件
        contactExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), UserInforActivity.class);
                intent.putExtra("userName", childList[groupPosition][childPosition]);
                startActivity(intent);
                return true;
            }
        });

    }

    // Fragment摧毁视图的生命周期
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 当view被销毁时去掉headView
        contactExpandableListView.removeHeaderView(headerView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.expandLvHeader_new_frined:
                startActivity(new Intent(getActivity(), NewFriendActivity.class));
                break;
            case R.id.expandLvHeader_group_chat:
                Toast.makeText(getActivity(), "Group Chat", Toast.LENGTH_SHORT).show();
                break;
            case R.id.expandLvHeader_relation:
                Toast.makeText(getActivity(), "Relation", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    // 通讯录扩展列表适配器
    class ContactExpandableListAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return parentList.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childList[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return parentList[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childList[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.expandlistview_row_group, null);
            }
            // 设置分组的名称和人数
            TextView groupName = (TextView) convertView.findViewById(R.id.expandLvRow_group_name);
            TextView groupCount = (TextView) convertView.findViewById(R.id.expandLvRow_group_count);
            groupName.setText(parentList[groupPosition]);
            groupCount.setText(childList[groupPosition].length + "人");

            convertView.setTag(R.id.expandLvRow_group_name, groupPosition);
            convertView.setTag(R.id.expandLvRow_contact_name, -1);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.expandlistview_row_contact, null);
            }
            TextView contactName = (TextView) convertView.findViewById(R.id.expandLvRow_contact_name);
            contactName.setText(childList[groupPosition][childPosition]);

            convertView.setTag(R.id.expandLvRow_group_name, groupPosition);
            convertView.setTag(R.id.expandLvRow_contact_name, childPosition);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}
