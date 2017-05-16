package com.zhbit.lw.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.Model;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUserAccount;
    private EditText edtPassword;
    private Button btnSignIn;
    private Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUserAccount = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        //初始化监听事件
        InitListener();
    }
    private void InitListener(){
        //登陆业务逻辑处理
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        //注册业务逻辑处理
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivityForResult(intent,666);
            }
        });
    }
    //返回数据
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 666:
                if (resultCode == 66) {
                    Toast.makeText(this, "注册账号成功，请您登录", Toast.LENGTH_LONG).show();
                    edtUserAccount.setText(data.getStringExtra("account"));
                    edtPassword.setText(data.getStringExtra("password"));
                }
        }
    }
    //登陆按钮业务处理
    private void Login(){
        final String account = edtUserAccount.getText().toString();
        final String password = edtPassword.getText().toString();
        //判断用户名密码是否为空
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)){
            Toast.makeText(this,"请填入正确用户名密码",Toast.LENGTH_LONG).show();
            return;
        }
        //登录
        Model.getInstance().getGlobalTheadPool().execute(new Runnable() {
            @Override
            public void run() {
                //去环信服务器登录
                EMClient.getInstance().login(account, password, new EMCallBack() {
                    //登录成功后的处理
                    @Override
                    public void onSuccess() {
                        //跳转主页面
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    //登录失败后的处理
                    @Override
                    public void onError(int i, String s) {
                        //提示登录失败
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                    }
                    //登录过程中的处理
                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });
    }
}
