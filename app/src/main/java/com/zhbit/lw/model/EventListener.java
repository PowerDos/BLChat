package com.zhbit.lw.model;

import android.content.Context;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.zhbit.lw.Logs.Logs;
import com.zhbit.lw.ServerRequest.SRequest;
import com.zhbit.lw.model.bean.FriendInfo;
import com.zhbit.lw.model.bean.InvitationInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fl5900 on 2017/5/19.
 * 全局事件监听类
 */

public class EventListener {
    private Context mContext;
    private final EMContactListener emContactListener = new EMContactListener() {
        @Override
        public void onContactAdded(final String s) {
            //联系人添加后执行的方法
            //更新数据库
//            Model.getInstance().getGlobalTheadPool().execute(new Runnable() {
//                     @Override
//                     public void run() {
//                         Map<String, String> data = new HashMap<String, String>();
//                         data.put("username", s);
//                         data.put("type", "1"); //请求1，为获取个人信息
//                         String reDate = SRequest.PostRequest(data);
//                         Logs.d("POST_onContactAdded", reDate);
//                         try {
//                             // 解析JSON文件
//                             JSONTokener jsonParser = new JSONTokener(reDate);
//                             JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
//                             if (jsonObject.isNull("error")) {
//                                 //获取数据
//                                 FriendInfo friendInfo = new FriendInfo();
//                                 friendInfo.setFriendAccount(jsonObject.getString("username"));
//                                 friendInfo.setFriendName(jsonObject.getString("nickname"));
//                                 friendInfo.setFriendSex(jsonObject.getString("sex"));
//                                 friendInfo.setFriendLocation(jsonObject.getString("location"));
//                                 Model.getInstance().getDbManager().getFriendTableDao().AddFriend(friendInfo);
//                             }
//                         } catch (JSONException e) {
//                             e.printStackTrace();
//                         }
//                     }
//                 }
//            );
        }

        @Override
        public void onContactDeleted(String s) {
            //联系人删除后执行的方法
        }

        @Override
        public void onContactInvited(String account,String reason) {
            //接收到联系人的新邀请
            InvitationInfo invitationInfo = new InvitationInfo();
            invitationInfo.setAccount(account);
            invitationInfo.setReason(reason);
            Model.getInstance().getDbManager().getInvitationTableDao().addInvitation(invitationInfo);
        }

        @Override
        public void onFriendRequestAccepted(final String username) {
            //别人同意了你的好友邀请
            //去服务器获取好友
            Model.getInstance().getGlobalTheadPool().execute(new Runnable() {
                @Override
                public void run() {
                    Map<String, String> data = new HashMap<String, String>();
                    data.put("username", username);
                    data.put("type", "1"); //请求1，为获取个人信息
                    String reDate = SRequest.PostRequest(data);
                    Logs.d("POST_onFriendRequestAccepted", reDate);
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
                            //添加好友在数据库
                            Model.getInstance().getDbManager().getFriendTableDao().AddFriend(friendInfo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void onFriendRequestDeclined(String s) {
            //别人拒绝了你的好友邀请
        }
    };

    public EventListener(Context context){
        mContext = context;
        EMClient.getInstance().contactManager().setContactListener(emContactListener);
    }
}
