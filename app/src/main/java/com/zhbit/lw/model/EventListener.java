package com.zhbit.lw.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.zhbit.lw.Logs.Logs;
import com.zhbit.lw.ServerRequest.SRequest;
import com.zhbit.lw.model.bean.FriendInfo;
import com.zhbit.lw.model.bean.InvitationInfo;
import com.zhbit.lw.model.bean.UserInfo;
import com.zhbit.lw.model.dao.ChatTable;
import com.zhbit.lw.model.dao.FriendTable;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fl5900 on 2017/5/19.
 * 全局事件监听类
 */

public class EventListener {
    private Context mContext;
    private int UserId;
    private final String MESSAGE_CHANGE = "com.zhbit.lw.MESSAGE_CHANGE"; //信号
    private final LocalBroadcastManager localBroadcastManager; //广播管理者对象
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
                            UserInfo userInfo = Model.getInstance().getDbManager().getUserTableDao().getUserInfor();
                            FriendInfo friendInfo = new FriendInfo();
                            friendInfo.setUserAccount(userInfo.getUserAccount());
                            friendInfo.setFriendId(jsonObject.getInt("id"));
                            friendInfo.setFriendAccount(jsonObject.getString("username"));
                            friendInfo.setFriendName(jsonObject.getString("nickname"));
                            friendInfo.setFriendSex(jsonObject.getString("sex"));
                            friendInfo.setFriendLocation(jsonObject.getString("location"));
                            //添加好友在数据库
                            Model.getInstance().getDbManager().getFriendTableDao().AddFriend(friendInfo);
                            Logs.d("ADD_FRIEND","添加好友"+friendInfo.getFriendAccount());
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

    EMMessageListener msgListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            for (EMMessage message : list){
                // 获取当前时间
                Logs.d("MESSAGE", " "+message.getFrom());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间显示格式
                // 将当前时间转化成字符串格式, 以便于存入数据库
                String currentTime = sdf.format(new Date());
                String msgContent = ((EMTextMessageBody) message.getBody()).getMessage();
                int showTimeFlag = ChatTable.HIDE_TIME;
                Logs.d("RECEIVER_NAME",message.getFrom());
                FriendInfo friendInfo = Model.getInstance().getDbManager().getFriendTableDao().getFriendInforByAccount(message.getFrom());
                Intent addContactIntent = new Intent(MESSAGE_CHANGE);
                //发送广播
                addContactIntent.putExtra(ChatTable.CHAT_MSG_CONTENT,""+msgContent);
                addContactIntent.putExtra(ChatTable.CHAT_MSG_TIME, currentTime);
                if (friendInfo != null ) {
                    addContactIntent.putExtra(FriendTable.FRIEND_ID,friendInfo.getFriendId());
                    localBroadcastManager.sendBroadcast(addContactIntent);
                    // 将聊天记录插入数据库当中
                    Model.getInstance().getDbManager().getChatTableDao().insertNewChatMsg(UserId, friendInfo.getFriendId(), msgContent, currentTime, ChatTable.CHAT_MSG_TYPE_RECEIVER, showTimeFlag);
                }

            }
            //消息存储在环信数据库中
            EMClient.getInstance().chatManager().importMessages(list);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {
            Logs.d("MESSAGE", "透传信息");
        }

        @Override
        public void onMessageRead(List<EMMessage> list) {
            Logs.d("MESSAGE", "已读回执");
        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {
            Logs.d("MESSAGE", "已送达回执");
        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {
            Logs.d("MESSAGE", "信息状态改变");
        }
    };


    public EventListener(Context context){
        mContext = context;
        UserId = Model.getInstance().getDbManager().getUserTableDao().getUserId();
        //注册好友申请监听
        EMClient.getInstance().contactManager().setContactListener(emContactListener);
        //注册消息监听
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        //创建广播管理者对象
        localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
    }
}
