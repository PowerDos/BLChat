package com.zhbit.lw.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.bean.UserInfo;
import com.zhbit.lw.model.Model;


public class SignUpActivity extends Activity implements View.OnClickListener{
    private Button btnSignUpSubmit;
    private EditText edtAccount;
    private EditText edtName;
    private EditText edtSex;
    private EditText edtLocation;
    private EditText edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSignUpSubmit = (Button) findViewById(R.id.btn_sign_up_submit);
        edtAccount = (EditText) findViewById(R.id.edt_sgin_up_account);
        edtName = (EditText) findViewById(R.id.edt_sign_up_name);
        edtSex = (EditText) findViewById(R.id.edt_sign_up_sex);
        edtLocation = (EditText) findViewById(R.id.edt_sign_up_location);
        edtPassword = (EditText) findViewById(R.id.edt_sign_up_password);
        btnSignUpSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String account = edtAccount.getText().toString();
        String name = edtName.getText().toString();
        String location = edtLocation.getText().toString();
        String sex = edtSex.getText().toString();
        final String password = edtPassword.getText().toString();
        if (TextUtils.isEmpty(account)||TextUtils.isEmpty(name)||TextUtils.isEmpty(location)
                || TextUtils.isEmpty(sex)||TextUtils.isEmpty(password)){
            Toast.makeText(this,"请填入完整信息",Toast.LENGTH_LONG).show();
            return;
        }
        final UserInfo userInfo = new UserInfo();
        userInfo.setUserAccount(account);
        userInfo.setUserName(name);
        userInfo.setUserSex(sex);
        userInfo.setUserLocation(location);
        userInfo.setUserHead("R.drawable.head");
        userInfo.setUserSign("富强、民主、文明、和谐、自由、平等、公正、法制、爱国、敬业、诚信、友善");
        //注册信息
        Model.getInstance().getGlobalTheadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //注册用户
                    EMClient.getInstance().createAccount(account, password);
                    Model.getInstance().getDbManager().getUserTableDao().addUserAccount(userInfo);
                    getIntent().putExtra("account", account);
                    getIntent().putExtra("password", password);
                    setResult(66, getIntent());
                    finish();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this,"注册失败"+e.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
