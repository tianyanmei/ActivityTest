package com.example.activitytest.Persistence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.activitytest.Persistence.litepal.Book;
import com.example.activitytest.R;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.tablemanager.Connector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class OpenFileOutputActivity extends AppCompatActivity {

    private EditText  inputId = null;

    private Button buttonId= null;

    private final static String FILE_NAME="activityTestData";

    private final static String SHAREPREFERENCES_NAME="activityTestsharepreferences";

    private MydatabaseHelper mydatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_file_output);

        buttonId = findViewById(R.id.openFileOutputButtonId);

        buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputId = findViewById(R.id.openFileOutputEditTextId);
                String data = inputId.getText().toString();
                Log.i("OpenFileTag","保存前:"+data);
                save(data);
            }
        });


        buttonId = findViewById(R.id.openFileOutputButtonId2);

        buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               inputId = findViewById(R.id.openFileOutputEditTextId2);
               String data = readFile();
               inputId.setText(data); //展示
            }
        });

        //share_preferences
        buttonId = findViewById(R.id.openFileOutputButtonId_getSharedPreferences);
        buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor =
                        getSharedPreferences(SHAREPREFERENCES_NAME,MODE_PRIVATE).edit();
                EditText key =null;
                EditText value= null;
                key =findViewById(R.id.openFileOutputEditTextId3k_1);
                value =findViewById(R.id.openFileOutputEditTextId3v_2);
                editor.putString(key.getText().toString(),value.getText().toString());
                key =findViewById(R.id.openFileOutputEditTextId3k_2);
                value =findViewById(R.id.openFileOutputEditTextId3v_2);
                editor.putString(key.getText().toString(),value.getText().toString());
                editor.putInt("age",28);
                editor.putBoolean("flag",true);
                editor.apply();

            }
        });



        buttonId = findViewById(R.id.openFileOutputButtonId_showSharedPreferences);
        buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               SharedPreferences pref =getSharedPreferences(SHAREPREFERENCES_NAME,MODE_PRIVATE);
               //SharedPreferences pref1= getPreferences(MODE_PRIVATE); //自动将当前活动名当做文件名

               String key =pref.getString("key1","");
               String value =pref.getString("key2","");
               int age = pref.getInt("age",0);
               boolean flage = pref.getBoolean("flag",false);
               inputId = findViewById(R.id.openFileOutputEditTextId4);
               String s=key+":"+value+":"+age+":"+flage;
               inputId.setText(s);
            }
        });


        mydatabaseHelper = new MydatabaseHelper(this,"bookstore.db",
                null,3);

        buttonId = findViewById(R.id.createSQLid);

        buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydatabaseHelper.getWritableDatabase();
            }
        });

        //向数据库中添加数据
        buttonId = findViewById(R.id.addSQLid);
        buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sqLiteDatabase = mydatabaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("author","Don Brown");
                contentValues.put("price",16.98);
                contentValues.put("pages",500);
                contentValues.put("name","android 第一版简介");
                long flageInt =sqLiteDatabase.insert("Book",null,contentValues);
                contentValues.clear();
                Log.i("SQLiteDate","===:"+String.valueOf(flageInt));
                contentValues.put("author","Don Brown");
                contentValues.put("price",20.98);
                contentValues.put("pages",500);
                contentValues.put("name","android 第二版简介");
                flageInt =sqLiteDatabase.insert("Book",null,contentValues);
                Log.i("SQLiteDate","-----:"+String.valueOf(flageInt));

                /*contentValues.clear();
                contentValues.put("author","Don Brown");
                contentValues.put("price",20.98);
                contentValues.put("pages",100);
                contentValues.put("name","android 第二版简介");
                flageInt =sqLiteDatabase.insert("Book","pages",contentValues);
                Log.i("SQLiteDate","-----:"+String.valueOf(flageInt));*/
            }
        });

        //更新数据
        buttonId = findViewById(R.id.updateSQLid);
        buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sqLiteDatabase = mydatabaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("pages",200);

                int updateInt= sqLiteDatabase.update("Book",contentValues,
                        "name=?",new  String[]{"android 第二版简介"});

                Log.i("SQLiteDate","-----:"+String.valueOf(updateInt));

            }
        });

        //删除数据
        buttonId = findViewById(R.id.deleteSQLid);
        buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sqLiteDatabase = mydatabaseHelper.getWritableDatabase();

                int deleteInt= sqLiteDatabase.delete("Book",
                        "name=?",new  String[]{"android 第二版简介"});
                Log.i("SQLiteDate","--delete---:"+String.valueOf(deleteInt));
            }
        });


        //查询
        buttonId = findViewById(R.id.querySQLid);
        buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sqLiteDatabase = mydatabaseHelper.getWritableDatabase();
                Cursor cursor = sqLiteDatabase.query("Book",null,
                        null,null,null,null,null,
                        null);

                if(cursor.moveToFirst()){
                    do{
                        int id =cursor.getInt(cursor.getColumnIndex("id"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        int page = cursor.getInt(cursor.getColumnIndex("pages"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));

                        String showStr ="id="+id+","+ "author="+author+","+
                                "price="+price+","+ "page="+page+","+ "name="+name +";";

                        Log.i("SQLiteDate","--query---:"+showStr);

                    }while (cursor.moveToNext());

                }

            }
        });


        //litepal工具类操作数据库
        buttonId = findViewById(R.id.createSQLlitepalid);
        buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connector.getDatabase(); //创建数据库
                Log.i("SQLiteDate","--创建数据库---:");
            }
        });

        //插入信息
        buttonId = findViewById(R.id.addlitepalSQLid);
        buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new  Book();
                book.setName("围城");
                book.setPress("天津出版社");
                book.setAuthor("老舍");
                book.setPage(300);
                book.setPrice(19.8);
                boolean flage=book.save();
                Log.i("SQLiteDate","book---"+book.toString()+flage); //可以用findbysql 来查询源生

               /* for (int i=0;i<10;i++){
                    Book book1 = new  Book(i, "Doner"+i,
                            "android 学习精要"+i, 17.8, 300+i,"北京出版社");
                    boolean flag= book1.save();
                    Log.i("SQLiteDate","book---"+flag); //可以用findbysql 来查询源生

                }*/
            }
        });


        //更新信息
        buttonId = findViewById(R.id.updatelitepalSQLid);
        buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new  Book();
                book.setPress("南京出版社");
                int litepalUpdate= book.updateAll("name=? AND id= ? ","android 学习精要","1");
                Log.i("SQLiteDate","book---"+litepalUpdate);
            }
        });

        //删除信息
        buttonId = findViewById(R.id.deletelitepalSQLid);
        buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new  Book();
                //book.setId(1);
                //book.delete(); //直接删除(第一种)

                //第二种
                int litepalDel = LitePal.deleteAll(Book.class,"name=? AND id= ? ","android 学习精要","1");
                Log.i("SQLiteDate","book---"+litepalDel);
            }
        });


        //查询信息
        buttonId = findViewById(R.id.querylitepalSQLid);
        buttonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Book> list = LitePal.findAll(Book.class); //查询所有d
                Log.i("SQLiteDate","-----:"+list.toString());

                //查询某一条记录

                list = LitePal.select("name","author").where("page > ?","100").
                        order("page").limit(10).offset(2).find(Book.class);

                for(int i=0;i<list.size();i++){
                    Book book = list.get(i);
                    Log.i("SQLiteDate","book---"+book.toString()); //可以用findbysql 来查询源生
                }

            }
        });
















    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void save(String data){
        Log.i("OpenFileTag","保存:"+data);
        FileOutputStream outputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(outputStream));
            bufferedWriter.write(data);



        }catch (IOException e){
            Log.i("openFileOutputDeal",e.getMessage());
        }finally {
            try{
                if(bufferedWriter !=null){
                    bufferedWriter.close();
                }

                if(outputStream !=null){
                    outputStream.close();
                }
            }catch (IOException e){
                Log.i("openFileOutputClose",e.getMessage());
            }
        }
    }

    public  String  readFile(){
        FileInputStream inputStream= null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String outString = null;

        try{
            inputStream = openFileInput(FILE_NAME);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line="";
            while ((line =bufferedReader.readLine()) !=null){
                stringBuilder.append(line);
            }
            outString = stringBuilder.toString();
            Log.i("OpenFileTag","输出:"+outString);
        }catch (IOException e){
            Log.i("openFileOutputDeal",e.getMessage());
        }finally {
            try{
                if(bufferedReader !=null){
                   bufferedReader.close();
                }

                if (inputStream !=null){
                    inputStream.close();
                }

            }catch (IOException e){
                Log.i("openFileOutputClose",e.getMessage());
            }
        }


        return  outString;
    }

}
