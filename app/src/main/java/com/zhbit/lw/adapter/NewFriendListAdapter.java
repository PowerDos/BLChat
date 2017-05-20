package com.zhbit.lw.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zhbit.lw.Logs.Logs;
import com.zhbit.lw.ServerRequest.SRequest;
import com.zhbit.lw.activity.FriendInforActivity;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.model.bean.FriendInfo;
import com.zhbit.lw.model.dao.InvitationTable;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wjh on 17-5-13.
 */

public class NewFriendListAdapter extends BaseAdapter{

    private Context context;
    private List<Map<String, Object>> newFriendListData;

    public NewFriendListAdapter(Context context, List<Map<String, Object>> newFriendListData) {
        this.context = context;
        this.newFriendListData = newFriendListData;
    }

    @Override
    public int getCount() {
        return newFriendListData.size();
    }

    @Override
    public Object getItem(int position) {
        return newFriendListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.listview_row_newfriend, null);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.lvRow_newfriend_userName);
        TextView tvRequestMsg = (TextView) convertView.findViewById(R.id.lvRow_newfriend_requestMsg);
        tvUserName.setText(newFriendListData.get(position).get(InvitationTable.ACCOUNT).toString());
        tvRequestMsg.setText(newFriendListData.get(position).get(InvitationTable.REASON).toString());
        final Button btnAddNewFriend = (Button) convertView.findViewById(R.id.btn_add_new_friend);
        final TextView txtShowAdded = (TextView) convertView.findViewById(R.id.txtShowAdded);
        final String Account = (String) tvUserName.getText();
        btnAddNewFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //按钮消失
                btnAddNewFriend.setVisibility(View.GONE);
                //文字出现
                txtShowAdded.setVisibility(View.VISIBLE);
                Model.getInstance().getGlobalTheadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().contactManager().acceptInvitation(Account);
                            Logs.d("Lss","成功");
                            //把邀请信息改为受理
                            Model.getInstance().getDbManager().getInvitationTableDao().setAdded(Account);
                            //去服务器获取用户信息
                            Model.getInstance().getGlobalTheadPool().execute(new Runnable() {
                                @Override
                                public void run() {
                                    Map<String, String> data = new HashMap<String, String>();
                                    data.put("username", Account);
                                    data.put("type", "1"); //请求1，为获取个人信息
                                    String reDate = SRequest.PostRequest(data);
                                    Logs.d("POST", reDate);
                                    try {
                                        JSONTokener jsonParser = new JSONTokener(reDate);
                                        JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
                                        if (jsonObject.isNull("error")) {
                                            //获取数据
                                            FriendInfo friendInfo = new FriendInfo();
                                            friendInfo.setFriendId(jsonObject.getInt("id"));
                                            friendInfo.setFriendAccount(jsonObject.getString("username"));
                                            friendInfo.setFriendName(jsonObject.getString("nickname"));
                                            friendInfo.setFriendSex(jsonObject.getString("sex"));
                                            friendInfo.setFriendLocation(jsonObject.getString("location"));
                                            Model.getInstance().getDbManager().getFriendTableDao().AddFriend(friendInfo);
                                        }
                                    } catch (JSONException e) {
                                            e.printStackTrace();
                                    }
                                }
                            });
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        return convertView;
    }
}
