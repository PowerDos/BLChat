package com.zhbit.lw.blchat;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    // 定义组件
    private ViewPager viewPager;
    private List<Fragment> fragmentList;    //　用于存放fragment的列表
    private String[] mTitles;
    private FragmentPagerAdapter fragmentPagerAdapter;  //fragment页面的适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();     // 初始化组件视图
        initDatas();    // 初始化数据

        viewPager.setAdapter(fragmentPagerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 初始化组件试图
    private void initView() {
        // 实例化组件视图
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    // 初始化数据
    private void initDatas() {
        // 测试数据
        mTitles = new String[] {"ChatFragment", "ContactFragment", "DiscoveryFragment", "MyFragment"};
        fragmentList = new ArrayList<Fragment>();

        // 添加四个Fragment到列表当中
        for(String title:mTitles) {
            ChatFragment chatFragment = new ChatFragment();

            // 用于捆绑数据
            Bundle bundle = new Bundle();
            bundle.putString(ChatFragment.TITLE, title);

            chatFragment.setArguments(bundle);
            fragmentList.add(chatFragment);
        }

        // 初始化fragmentPagerAdapter
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
    }
}
