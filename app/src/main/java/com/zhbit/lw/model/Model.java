package com.zhbit.lw.model;

import android.content.Context;

import com.zhbit.lw.model.db.DBManager;

/**
 * Created by fl5900 on 2017/5/11.
 */

public class Model {
    private Context context;
    private static Model model = new Model();
    private DBManager dbManager;

    // 获取单例对象
    public static Model getInstance(){
        return model;
    }



    //获取DB管理类
    public DBManager getDbManager(Context cot){
        context = cot;
        //待续
        // dbManager = new DBManager(context);
        return dbManager;
    }
}
