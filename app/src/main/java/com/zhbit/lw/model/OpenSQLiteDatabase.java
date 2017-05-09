package com.zhbit.lw.model;

import android.database.sqlite.SQLiteDatabase;

import com.zhbit.lw.blchat.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


/**
 * Created by fl5900 on 2017/5/9.
 */

public class OpenSQLiteDatabase {
    private final String DATABASES_PATH = android.os.Environment.
            getExternalStorageDirectory().getAbsolutePath()+"/blchat";
    private final String DATABASES_NAME = "blchat.db";

    public SQLiteDatabase openDatabase(){
        try {
            String databasesFileName = DATABASES_PATH + '/' +DATABASES_NAME;
            File dir = new File(DATABASES_PATH);
            //这里为了开发更改数据库方便，这里先每次都写入数据库
            if (dir.exists())
                dir.mkdir();
            if (!new File(databasesFileName).exists()){
                InputStream is = getResources().openRawResource(R.raw.blchat);
                FileOutputStream fos = new FileOutputStream(databasesFileName);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = is.read(buffer))>0){
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databasesFileName,null);
            return  database;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
}
