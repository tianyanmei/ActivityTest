package com.example.activitytest.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    public  static  final  int TABLE1_DIR=0;
    public  static  final  int TABLE1_ITEM=1;
    public  static  final  int TABLE2_DIR=2;
    public  static  final  int TABLE2_ITEM=3;

    public  static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.activitytest.provider","table1",TABLE1_DIR);
        uriMatcher.addURI("com.example.activitytest.provider","table1/#",TABLE1_ITEM);
        uriMatcher.addURI("com.example.activitytest.provider","table2",TABLE2_DIR);
        uriMatcher.addURI("com.example.activitytest.provider","table2/#",TABLE2_ITEM);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
       switch (uriMatcher.match(uri)){
           case TABLE1_DIR:
               //查询table1所有的数据
               break;
           case TABLE1_ITEM :
                //查询table1中的一条数据
               break;
           case  TABLE2_DIR:
               //查询table2中的一条数据
               break;
           case TABLE2_ITEM :
               //查询table2中的一条数据
               break;
           default:

       }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case TABLE1_DIR:
                //查询table1所有的数据
                break;
            case TABLE1_ITEM :
                //查询table1中的一条数据
                break;
            case  TABLE2_DIR:
                //查询table2中的一条数据
                break;
            case TABLE2_ITEM :
                //查询table2中的一条数据
                break;
            default:

        }







        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
