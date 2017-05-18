package com.zhbit.lw.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhbit.lw.Logs.Logs;
import com.zhbit.lw.ServerRequest.SRequest;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.model.bean.UserInfo;
import com.zhbit.lw.ui.CustomToolbar;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

import static com.zhbit.lw.model.dao.UserTable.USER_ID;

public class AddFriendActivity extends AppCompatActivity implements View.OnClickListener{

    private CustomToolbar customToolbar;

    private int userId;
    private TextView txtNotifyInfo; // 显示查找好友返回的结果，可用一个listview显示好友信息
    private Button btnsearchFriend; //查找好友按钮
    private Button btnaddFriend;  //添加好友按钮  可用附加在listviwe好友那个地方
    private TextView txtSearchFriendID; //搜索输入框
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        // 初始化
        txtNotifyInfo = (TextView) findViewById(R.id.txt_notify_info);
        txtSearchFriendID = (TextView) findViewById(R.id.base_search);
        btnaddFriend = (Button) findViewById(R.id.btn_add_this_friend);
        btnsearchFriend = (Button) findViewById(R.id.btn_search_friend);
        btnaddFriend.setOnClickListener(this);
        btnsearchFriend.setOnClickListener(this);
        initView();     // 初始化视图
        initData();     // 初始化数据
        initEvent();    // 初始化点击事件

    }

    // 初始化视图
    private void initView() {
        customToolbar = (CustomToolbar) findViewById(R.id.addFriend_toolbar);
        customToolbar.setTitle("添加朋友");
    }

    // 初始化数据
    private void initData() {
        userId = getIntent().getIntExtra(USER_ID, -1);
    }

    // 初始化点击事件
    private void initEvent() {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search_friend){
            //查找好友
            //获取输入用户的名称
            final String account = txtSearchFriendID.getText().toString();
            //校验
            if (TextUtils.isEmpty(account)){
                Toast.makeText(this,"输入的用户名不能为空",Toast.LENGTH_LONG);
                return;
            }
            // 去服务器判断用户是否存在
            Model.getInstance().getGlobalTheadPool().execute(new Runnable() {
                @Override
                public void run() {
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
                            //获取数据
                            UserInfo userInfo = new UserInfo();
                            userInfo.setUserAccount(jsonObject.getString("username"));
                            userInfo.setUserName(jsonObject.getString("nickname"));
                            userInfo.setUserSign(jsonObject.getString("sign"));
                            userInfo.setUserSex(jsonObject.getString("sex"));
                            userInfo.setUserLocation(jsonObject.getString("location"));
                            //上面都是用户的信息，把这些信息存在listview中
//                            String showInfo = userInfo.getUserAccount()+userInfo.getUserName();
//                            txtNotifyInfo.setText(showInfo);
                        }else {
                            //显示用户不存在  这里子线程获取不了view，今晚回来再解决
//                            String showInfo = jsonObject.getString("error")+jsonObject.getString("msg");
//                            txtNotifyInfo.setText(showInfo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else if (v.getId() == R.id.btn_add_this_friend){
            //添加好友
        }
    }
}
