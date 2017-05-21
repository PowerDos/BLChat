package com.zhbit.lw.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zhbit.lw.Logs.Logs;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.Model;

public class SystemSetting extends AppCompatActivity {
    private Button btnLoginOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting);
        btnLoginOut = (Button) findViewById(R.id.setting_login_out);
        btnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMClient.getInstance().logout(true, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Logs.d("LOGIN_OUT","退出成功");
                                //删除用户数据库记录
                                Model.getInstance().getDbManager().getUserTableDao().LoginOut();
                                Intent intent = new Intent(SystemSetting.this,LoginActivity.class);
                                finish();
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });
    }
}
