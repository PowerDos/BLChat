package com.zhbit.lw.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;

import com.zhbit.lw.adapter.MomentInfoAdapter;
import com.zhbit.lw.blchat.R;
import com.zhbit.lw.model.bean.MomentInfo;
import com.zhbit.lw.model.Model;
import com.zhbit.lw.ui.CustomToolbar;

import java.util.List;

public class MomentActivity extends Activity {

    CustomToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment);

        toolbar = (CustomToolbar) findViewById(R.id.moment_toolbar);
        toolbar.setTitle("朋友圈");

        ListView lv_moment = (ListView) findViewById(R.id.lv_moment_info);
        List<MomentInfo> momentEntities = Model.getInstance().getDbManager().getMomentTableDao().getAllMomentInfo();
        MomentInfoAdapter momentInfoAdapter = new MomentInfoAdapter(MomentActivity.this, momentEntities);
        lv_moment.addHeaderView(this.getLayoutInflater().inflate(R.layout.layout_moment_heading,null),
                null,false);
        //设置Adapter显示
        lv_moment.setAdapter(momentInfoAdapter);
    }
}
