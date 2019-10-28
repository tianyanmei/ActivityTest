package com.example.activitytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderActivity extends AppCompatActivity {

    private  String phoneNumberStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        Button button = findViewById(R.id.contentProviderButtonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(ContentProviderActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                       //如果没有权限，就申请权限
                    ActivityCompat.requestPermissions(ContentProviderActivity.this,new String[]{
                            Manifest.permission.CALL_PHONE},1001); //获取电话权限
                }else {
                    EditText editText = findViewById(R.id.contentProviderTextId);
                    phoneNumberStr =editText.getText().toString();
                    call(phoneNumberStr);
                }

            }
        });

        //获取电话联系人
        button = findViewById(R.id.contentproviderPhoneButton);
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,listName);
        ListView listView = findViewById(R.id.contentproviderPhoneSet);
        listView.setAdapter(arrayAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(ContentProviderActivity.this,Manifest.permission.READ_CONTACTS)
                !=PackageManager.PERMISSION_GRANTED){
                  ActivityCompat.requestPermissions(ContentProviderActivity.this,
                          new  String[]{Manifest.permission.READ_CONTACTS},1002);
                }else {
                    readContacts();
                }



            }
        });






    }

    public void  call(String phoneNumber){
        try{
            Intent intent = new Intent(Intent.ACTION_CALL);//获取拨号权限
            phoneNumber ="tel:"+phoneNumber;
            Log.d("contentprovider","----"+phoneNumber);

            intent.setData(Uri.parse(phoneNumber));
            startActivity(intent);
        }catch (SecurityException e){
            Log.d("contentprovider",e.getMessage());
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1001:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                  call(phoneNumberStr);
                }else {
                    Toast.makeText(this,"权限申请未通过",Toast.LENGTH_LONG).show();
                }
                break;

            case 1002:
                if(grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }else {
                    Toast.makeText(this,"权限申请未通过",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }
    //获取联系人

    private   ArrayAdapter<String> arrayAdapter;
    private List<String> listName= new ArrayList<String>();


    public  void  readContacts(){
        Cursor cursor = null;
        cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
               null,null,null,null);
        if(cursor !=null){
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String showTitle = name+":"+phoneNumber;
                Log.i("contentprovider",showTitle);
                listName.add(showTitle);
            }
        }
        arrayAdapter.notifyDataSetChanged();
        if(cursor !=null){
            cursor.close();
        }

    }









}
