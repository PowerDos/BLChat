package com.zhbit.lw.blchat;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.zhbit.lw.adapter.MomentInfoAdapter;
import com.zhbit.lw.entity.MomentInfo;
import com.zhbit.lw.model.Model;

import java.util.List;

public class Moment extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment);
        ListView lv_moment = (ListView) findViewById(R.id.lv_moment_info);
        List<MomentInfo> momentInfos = Model.getInstance().getDbManager(this).getMomentTableDao().getAllMomentInfo();
        MomentInfoAdapter momentInfoAdapter = new MomentInfoAdapter(Moment.this,momentInfos);
        lv_moment.addHeaderView(this.getLayoutInflater().inflate(R.layout.layout_moment_heading,null),
                null,false);
        //设置Adapter显示
        lv_moment.setAdapter(momentInfoAdapter);
    }
}
