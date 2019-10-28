package com.example.activitytest.Persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MydatabaseHelper extends SQLiteOpenHelper {

    private   static final  String sqlString =
            "CREATE TABLE Book(\n" +
            " id INTEGER PRIMARY KEY autoincrement,\n" +
            " author text,\n" +
            " price real,\n" +
            " pages INTEGER,\n" +
            " name text)";


    private  static  final  String categorySqlString= "CREATE TABLE categroy(\n" +
            " id INTEGER PRIMARY KEY autoincrement,\n" +
            " categroy_name text,\n" +
            " categroy_code INTEGER)";

    private  Context context;
    public MydatabaseHelper(Context context, String name,
            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlString);
        sqLiteDatabase.execSQL(categorySqlString);
       // Toast.makeText(context,"创建数据库",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if  exists Book");
            sqLiteDatabase.execSQL("drop table if exists categroy");
            onCreate(sqLiteDatabase);
    }
}
