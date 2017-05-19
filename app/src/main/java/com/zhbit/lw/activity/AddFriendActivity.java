package com.zhbit.lw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhbit.lw.Logs.Logs;
import com.zhbit.lw.ServerRequest.SRequest;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.model.bean.FriendInfo;
import com.zhbit.lw.model.bean.UserInfo;
import com.zhbit.lw.model.dao.FriendTable;
import com.zhbit.lw.model.dao.UserTable;
import com.zhbit.lw.ui.CustomToolbar;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import static com.zhbit.lw.model.dao.UserTable.USER_ID;

public class AddFriendActivity extends AppCompatActivity{

    private CustomToolbar customToolbar;

    private int userId;     // 用户的ID
    private EditText etSearchFriend;    // 搜索好友的输入框
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        initView();     // 初始化视图
        initData();     // 初始化数据
        initEvent();    // 初始化点击事件

    }

    // 初始化视图
    private void initView() {
        // 实例化顶部Toolbar
        customToolbar = (CustomToolbar) findViewById(R.id.addFriend_toolbar);
        customToolbar.setTitle("添加朋友");

        etSearchFriend = (EditText) findViewById(R.id.base_search);
    }

    // 初始化数据
    private void initData() {
        // 从ContactFragment联系人列表跳转时传的userId
        userId = getIntent().getIntExtra(USER_ID, -1);
    }

    // 初始化点击事件
    private void initEvent() {
        // 设置输入框的按键监听事件，　监听用户按下回车键
        etSearchFriend.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    final String account = etSearchFriend.getText().toString();
                    //校验
                    if (TextUtils.isEmpty(account)){
                        Toast.makeText(AddFriendActivity.this,"输入的用户名不能为空",Toast.LENGTH_LONG);
                        return false;
                    }
                    // 去服务器判断用户是否存在
                    Model.getInstance().getGlobalTheadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            /**
                             * 我把显示在这个添加好友界面的内容全部去掉了，改成了搜索到之后就跳转到好友信息的界面
                             * 如果没有好友信息就Toast提示用户没有找到
                             * 回车键的监听事件是已经成功了的，不过服务器判断的内容我只改了下面我注释的内容
                             * 其他不懂的都没有改
                             *　
                             * 本来设置的是UserInfo, 因为要改成跳转到FriendInfo所以修改了一下
                             * 不过没有认真看服务器的操作，你要改一改我写的
                             *
                             * 如果查到存在用户就跳转到好友信息界面，在好友信息，在添加好友成功之后才会把好友
                             * 的信息添加到本地数据库当中，所以好友信息界面通过好友ID是否存在本地数据库当中来
                             * 判别是不是好友也就是判断设置按钮为发消息还是添加好友
                             *
                             * 没有设置个性签名　我设计的好友表当中忘记了这个字段了
                             * 添加了设置好友ID
                             */
                            Map<String,String> data = new HashMap<String, String>();
                            data.put("username",account);
                            data.put("type","1"); //请求1，为获取个人信息
                            String reDate = SRequest.PostRequest(data);
                            Logs.d("POST", reDate);
                            try {
                                // 解析JSON文件
                                JSONTokener jsonParser = new JSONTokener(reDate);
                                JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
                                if (jsonObject.isNull("error")) {
                                    // 获取到信息后跳转到好友信息界面
                                    //获取数据
                                    FriendInfo friendInfo = new FriendInfo();
//                                    friendInfo.setFriendId(jsonObject.getString("friend_id"));
//                                    friendInfo.setFriendAccount(jsonObject.getString("friend_account"));
//                                    friendInfo.setFriendName(jsonObject.getString("friend_name"));
//                                    friendInfo.setFriendSex(jsonObject.getString("friend_sex"));
//                                    friendInfo.setFriendLocation(jsonObject.getString("friend_location"));
                                    // 跳转到好友信息界面
                                    Intent intent = new Intent(AddFriendActivity.this, FriendInforActivity.class);
                                    intent.putExtra(UserTable.USER_ID, userId);
                                    intent.putExtra(FriendTable.FRIEND_ID, friendInfo.getFriendId());
                                    intent.putExtra(FriendTable.FRIEND_NAME, friendInfo.getFriendName());
                                    intent.putExtra(FriendTable.FRIEND_ACCOUNT, friendInfo.getFriendAccount());
                                    intent.putExtra(FriendTable.FRIEND_LOCATION, friendInfo.getFriendLocation());
                                    intent.putExtra(FriendTable.FRIEND_SEX, friendInfo.getFriendSex());
                                    startActivity(intent);

//                            String showInfo = userInfo.getUserAccount()+userInfo.getUserName();
//                            txtNotifyInfo.setText(showInfo);
                                }else {
                                    //显示用户不存在  这里子线程获取不了view，今晚回来再解决
//                                  String showInfo = jsonObject.getString("error")+jsonObject.getString("msg");
//                                    txtNotifyInfo.setText(showInfo);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else if(keyCode == KeyEvent.KEYCODE_BACK) {
                    // 返回键的监听事件
                    finish();
                }
                return true;
            }
        });
    }

}
