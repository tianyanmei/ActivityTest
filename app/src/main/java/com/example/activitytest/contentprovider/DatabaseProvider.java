package com.example.activitytest.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.activitytest.Persistence.MydatabaseHelper;
import com.example.activitytest.pojo.News;

import java.io.Serializable;

public class DatabaseProvider extends ContentProvider {
    public  static  final int BOOK_DIR=0;
    public  static  final  int BOOK_ITEM=1;
    public  static  final  int CATEGORY_DIR=3;
    public  static  final  int CATEGORY_ITEM=4;

    public  static  final  String AUTHORITY="com.example.activitytest.provider";

    private  static UriMatcher uriMatcher;
    private  MydatabaseHelper mydatabaseHelper;

    static {
      uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
      uriMatcher.addURI(AUTHORITY,"BOOK",BOOK_DIR);
      uriMatcher.addURI(AUTHORITY,"BOOK/#",BOOK_ITEM);
      uriMatcher.addURI(AUTHORITY,"categroy",CATEGORY_DIR);
      uriMatcher.addURI(AUTHORITY,"categroy/#",CATEGORY_ITEM);


    }


    public DatabaseProvider() {
    }

    @Override
    public boolean onCreate() {
        mydatabaseHelper = new MydatabaseHelper(getContext(),"bookstore.db",
                null,3);
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        //throw new UnsupportedOperationException("Not yet implemented");
        int retDel=0;
        SQLiteDatabase sqLiteDatabase = mydatabaseHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                sqLiteDatabase.delete("Book",selection,selectionArgs);
                break;
            case  BOOK_ITEM:
                String bookId= uri.getPathSegments().get(1);
                sqLiteDatabase.delete("Book","id=?",new String[]{bookId});
                break;
            case  CATEGORY_DIR:
                sqLiteDatabase.delete("Category",selection,selectionArgs);
                break;
            case  CATEGORY_ITEM:
                String cateGoryId = uri.getPathSegments().get(1);
                sqLiteDatabase.delete("Category","id=?",new String[]{cateGoryId});
                break;
            default:
                break;
        }
        return  retDel;




    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        //throw new UnsupportedOperationException("Not yet implemented");
        String retUri= null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                retUri="vnd.android.cursor.dir/vnd.com.example.activitytest.provider.book";
                break;
            case  BOOK_ITEM:
                retUri="vnd.android.cursor.item/vnd.com.example.activitytest.provider.book";
                break;
            case  CATEGORY_DIR:
                retUri="vnd.android.cursor.dir/vnd.com.example.activitytest.provider.category";
                break;
            case  CATEGORY_ITEM:
                retUri ="vnd.android.cursor.item/vnd.com.example.activitytest.provider.category";
                break;
            default:
                break;
        }
        return  retUri;


    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
       // throw new UnsupportedOperationException("Not yet implemented");
        SQLiteDatabase sqLiteDatabase = mydatabaseHelper.getWritableDatabase();
        Uri uriReturn=null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR :
            case BOOK_ITEM :
                long bookId = sqLiteDatabase.insert("book",null,values);
                uriReturn= Uri.parse("content://com.example.activitytest.provider/book/"+bookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM :
                long categoryId = sqLiteDatabase.insert("category",null,values);
                uriReturn = Uri.parse("content://com.example.activitytest.provider/catagory/"+categoryId);
                break;
            default:
        }
        return  uriReturn;
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        //throw new UnsupportedOperationException("Not yet implemented");
        SQLiteDatabase sqLiteDatabase = mydatabaseHelper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR :
                cursor=sqLiteDatabase.query("book",projection,selection,
                        selectionArgs,null,null,sortOrder);
                break;

            case BOOK_ITEM :
                String bookId = uri.getPathSegments().get(1);
                cursor =sqLiteDatabase.query("book",projection,"id=?",new
                        String[]{bookId},null,null,sortOrder);
                break;

            case CATEGORY_DIR:
                cursor = sqLiteDatabase.query("category",projection,selection,selectionArgs,
                        null,null,sortOrder);
                break;
            case CATEGORY_ITEM :
                String cateGoryId = uri.getPathSegments().get(1);
                cursor = sqLiteDatabase.query("categroy",projection,"id=?",
                        new String[]{cateGoryId},null,null,sortOrder);

                break;
            default:
        }
        return  cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        //throw new UnsupportedOperationException("Not yet implemented");
        SQLiteDatabase sqLiteDatabase = mydatabaseHelper.getWritableDatabase();
        int retUrl=0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR :
                retUrl = sqLiteDatabase.update("Book",values,selection,selectionArgs);
                break;
            case BOOK_ITEM :
                String bookId = uri.getPathSegments().get(1);
                retUrl =sqLiteDatabase.update("Book",values,"id=?",new String[]{bookId});
                break;

            case CATEGORY_DIR:
                retUrl = sqLiteDatabase.update("Category",values,selection,selectionArgs);
                break;
            case CATEGORY_ITEM :
                String cateGoryId = uri.getPathSegments().get(1);
                retUrl = sqLiteDatabase.update("Category",values,"id=?",new String[]{cateGoryId});
                break;
            default:
        }
        return  retUrl;

    }
}
