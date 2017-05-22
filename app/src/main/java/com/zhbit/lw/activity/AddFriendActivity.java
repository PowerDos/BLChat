package com.zhbit.lw.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zhbit.lw.Logs.Logs;
import com.zhbit.lw.ServerRequest.SRequest;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.model.bean.FriendInfo;
import com.zhbit.lw.model.dao.FriendTable;
import com.zhbit.lw.model.dao.UserTable;
import com.zhbit.lw.ui.CustomToolbar;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
        etSearchFriend.setCursorVisible(false);
    }

    // 初始化数据
    private void initData() {
        // 从ContactFragment联系人列表跳转时传的userId
        userId = getIntent().getIntExtra(USER_ID, -1);
    }

    // 初始化点击事件
    private void initEvent() {
        etSearchFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearchFriend.setCursorVisible(true);
            }
        });
        // 设置输入框的按键监听事件，　监听用户按下回车键
        etSearchFriend.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    etSearchFriend.setCursorVisible(false);
                    final String account = etSearchFriend.getText().toString();
                    //校验
                    if (TextUtils.isEmpty(account)){
                        Toast.makeText(AddFriendActivity.this,"输入的用户名不能为空",Toast.LENGTH_LONG);
                        return false;
                    }
                    //先判断本地有无该好友
                    FriendInfo friendInfo = Model.getInstance().getDbManager().getFriendTableDao().getFriendInforByAccount(account);
                    if (friendInfo != null){
                        // 跳转到好友信息界面
                        Intent intent = new Intent(AddFriendActivity.this, FriendInforActivity.class);
                        intent.putExtra(UserTable.USER_ID, userId);
                        intent.putExtra(FriendTable.FRIEND_ID, friendInfo.getFriendId());
                        intent.putExtra(FriendTable.FRIEND_NAME, friendInfo.getFriendName());
                        intent.putExtra(FriendTable.FRIEND_ACCOUNT, friendInfo.getFriendAccount());
                        intent.putExtra(FriendTable.FRIEND_LOCATION, friendInfo.getFriendLocation());
                        intent.putExtra(FriendTable.FRIEND_SEX, friendInfo.getFriendSex());
                        intent.putExtra("isLocal",1);
                        startActivity(intent);
                    }else {
                        //如果本地不存在该用户
                        // 去服务器判断用户是否存在
                        Model.getInstance().getGlobalTheadPool().execute(new Runnable() {
                            @Override
                            public void run() {
                                Map<String, String> data = new HashMap<String, String>();
                                data.put("username", account);
                                data.put("type", "1"); //请求1，为获取个人信息
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
                                        friendInfo.setFriendId(999);
                                        friendInfo.setFriendAccount(jsonObject.getString("username"));
                                        friendInfo.setFriendName(jsonObject.getString("nickname"));
                                        friendInfo.setFriendSex(jsonObject.getString("sex"));
                                        friendInfo.setFriendLocation(jsonObject.getString("location"));
                                        // 跳转到好友信息界面
                                        Intent intent = new Intent(AddFriendActivity.this, FriendInforActivity.class);
                                        intent.putExtra(UserTable.USER_ID, userId);
                                        intent.putExtra(FriendTable.FRIEND_ID, friendInfo.getFriendId());
                                        intent.putExtra(FriendTable.FRIEND_NAME, friendInfo.getFriendName());
                                        intent.putExtra(FriendTable.FRIEND_ACCOUNT, friendInfo.getFriendAccount());
                                        intent.putExtra(FriendTable.FRIEND_LOCATION, friendInfo.getFriendLocation());
                                        intent.putExtra(FriendTable.FRIEND_SEX, friendInfo.getFriendSex());
                                        intent.putExtra("isLocal",0);
                                        startActivity(intent);
                                    } else {
                                        //显示用户不存在
                                        final String showInfo = jsonObject.getString("msg");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(AddFriendActivity.this, showInfo, Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }else if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // 返回键的监听事件
                    finish();
                }else if (keyCode == KeyEvent.KEYCODE_DEL) {
                    String etContent = etSearchFriend.getText().toString();
                    if (etContent.equals("")) {
                        return true;
                    }else{
                        String result = etContent.substring(0, etContent.length()-1);
                        etSearchFriend.setText(result);
                        etSearchFriend.setSelection(result.length());
                    }
                }
                return true;
            }
        });
    }

}
