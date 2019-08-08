package com.zxc.zhanglj.common.widget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zxc.zhanglj.utils.LogUtil;

/**
 * Author:ZLJ
 * Date: 2019-07-31 10:49
 */
public class DBHelp extends SQLiteOpenHelper {
    public static final String DB_NAME = "database.db";

    public static final int DB_VERSION = 2;

    public static final String TABLE_STUDENT = "students";
    //创建 students 表的 sql 语句
    private static final String STUDENTS_CREATE_TABLE_SQL = "create table " + TABLE_STUDENT + "("
            + "id integer primary key autoincrement,"
            + "name varchar(20) not null,"
            + "tel_no varchar(11) not null,"
            + "cls_id integer not null"
            + ");";

    private static final String sql2 ="ALTER TABLE "+TABLE_STUDENT+" ADD COLUMN address VARCHAR";

    public DBHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(STUDENTS_CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.i("ZLJ","oldVersion :"+oldVersion+",newVersion :"+newVersion);
        if (oldVersion < newVersion) {
            String sql3 = "ALTER TABLE Student ADD COLUMN address VARCHAR";
            db.execSQL(sql2);
        }
    }

}
