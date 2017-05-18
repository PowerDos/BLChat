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
import com.zhbit.lw.ServerRequest.SRequest;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.bean.UserInfo;
import com.zhbit.lw.model.Model;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;


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

        //注册用户
        final Map<String,String> data = new HashMap<String, String>();
        data.put("username", account);
        data.put("password", password);
        data.put("location", location);
        data.put("sex", sex);
        data.put("nickname", name);
        data.put("type","2"); //请求2，为注册请求
        Model.getInstance().getGlobalTheadPool().execute(new Runnable() {
            @Override
            public void run() {
                //注册请求
                String reDate = SRequest.PostRequest(data);
                //接收返回的数据，看看是否注册成功
                try {
                    JSONTokener jsonParser = new JSONTokener(reDate);
                    JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
                    if (Integer.parseInt(jsonObject.getString("error")) == 0){
                        getIntent().putExtra("account", account);
                        getIntent().putExtra("password", password);
                        setResult(66, getIntent());
                        finish();
                    }else {
                        Toast.makeText(SignUpActivity.this,"注册失败", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
