package com.example.activitytest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activitytest.Persistence.OpenFileOutputActivity;
import com.example.activitytest.baidu_gps.BaiduMainActivity;
import com.example.activitytest.broadcast.DynamicRegistBroadcastActivity;
import com.example.activitytest.broadcast.LocalBroadcastReceiver;
import com.example.activitytest.downloadtest.DownloadMainActivity;
import com.example.activitytest.gps.LocationAndroidGpsActivity;
import com.example.activitytest.material_design.MaterialDesignMainActivity;
import com.example.activitytest.multimedia.Multimedia;
import com.example.activitytest.online.NetWorkActivity;
import com.example.activitytest.online.WebViewActivity;
import com.example.activitytest.service.MyServiceMainActivity;
import com.example.activitytest.threads.HandleMainActivity;

public class FirstActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState !=null){
           Log.d("tag",savedInstanceState.getString("data_Bundle"));
        }



        setContentView(R.layout.first_layout);
        Button button =findViewById(R.id.buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText =findViewById(R.id.editText_1_1);
                TextView textView = findViewById(R.id.text1_1);
                textView.setText(editText.getText().toString());
                Toast.makeText(FirstActivity.this,
                        "you onclick button1",Toast.LENGTH_LONG).show();
            }
        });

        //显式打开activity
        Button button1 = findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                String data = "hello secondActivity";
                intent.putExtra("data",data);
                startActivity(intent);
            }
        });

        //隐式打开ACTIVITY,默认的category为default，自动添加
        //每个intent只能有一个action,可以有多个category
        Button button2 = findViewById(R.id.button_1_1);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.ACTION_START");
                intent.addCategory("android.intent.com.exanmple.MYDEFAULT");
                startActivity(intent);
            }
        });

        Button button3 = findViewById(R.id.button_1_2);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });

        //打开电话
        Button button4 = findViewById(R.id.button_1_3);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent   = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
            }
        });

        //接收回参

        Button button5 = findViewById(R.id.button_1_4);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                intent.putExtra("data","要有回参");
                startActivityForResult(intent,1);

            }
        });

        //生命周期

        Button nomalBotton  = findViewById(R.id.button_nomalId);
        nomalBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this,NomalActivity.class);
                startActivity(intent);
            }
        });

        Button dialogButton = findViewById(R.id.button_dialogId);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this,DialogActivity.class);
                startActivity(intent);
            }
        });


        //图片，命名是小写字母，数字，下划线
        final ImageView imageView = findViewById(R.id.mt4Id_01);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageResource(R.drawable.naruto01);
            }
        });

        //进度条 三种状态，versible(可见) inversible（不可见存在） gone（不存在）

        final ProgressBar progressBar = findViewById(R.id.progressBarId);

        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(View.GONE ==progressBar.getVisibility()){
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


        final ProgressBar progressBarId01 = findViewById(R.id.progressBarId01);

        progressBarId01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int progress = progressBarId01.getProgress();
               progress+=10;
               progressBarId01.setProgress(progress);
            }
        });


        //对话框

        Button button6 = findViewById(R.id.button_1_5);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FirstActivity.this);
                alertDialog.setTitle("this is alertDialog");//标题
                alertDialog.setMessage("this is a alertDialog content!");//内容
                alertDialog.setCancelable(false); //是否可取消,按返回键不能退出
                alertDialog.setIcon(R.drawable.mt401);
                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(FirstActivity.this,"你点击了OK按钮",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setNegativeButton("cancer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(FirstActivity.this,"你点击了cancer按钮",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();

            }
        });


        //progressDialog可以用alertDialog来构建，此方法过时

        Button  progressDialogButton = findViewById(R.id.button_1_6);

        progressDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog1 =new ProgressDialog(FirstActivity.this);
                progressDialog1.setTitle("进度条标题");
                progressDialog1.setMessage("进度条内容");
                progressDialog1.setCancelable(false);
                progressDialog1.show();
                //progressDialog1.dismiss();//去掉对话框
            }
        });

        Button listViewButton = findViewById(R.id.button_1_7);
        listViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this,ListViewActivity.class);
                startActivity(intent);
            }
        });

        Button listViewButtonFruit = findViewById(R.id.button_1_8);

        listViewButtonFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this,FruitListViewActivity.class);
                startActivity(intent);
            }
        });


        Button recyclerView01 = findViewById(R.id.button_1_9);

        recyclerView01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this,RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        Button horizontal_recycler = findViewById(R.id.button_1_10);

        horizontal_recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this,HorizontalRecyclerViewActivity.class);
                startActivity(intent);
            }
        });


        final Button staggeredGrid = findViewById(R.id.button_1_11);
        staggeredGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this,StaggeredGridLayoutActivity.class);
                startActivity(intent);
            }
        });


        Button chatWithButton = findViewById(R.id.button_1_12);
        chatWithButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this,ChatWithActivity.class);
                startActivity(intent);
            }
        });

        Button button_fragment = findViewById(R.id.button_1_13);

        button_fragment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent   = new Intent(FirstActivity.this,FragmentActivity.class);
               startActivity(intent);
           }
       });

        Button button_news = findViewById(R.id.button_1_14);
        button_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(FirstActivity.this,NewsMainActivity.class);
                 startActivity(intent);
            }
        });


        Button button_1_15 = findViewById(R.id.button_1_15);
        button_1_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, DynamicRegistBroadcastActivity.class);
                startActivity(intent);
            }
        });

        Button button_1_16 = findViewById(R.id.button_1_16);
        button_1_16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, LocalBroadcastReceiver.class);
                startActivity(intent);
            }
        });

        Button button_1_17 = findViewById(R.id.button_1_17);
        button_1_17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, OpenFileOutputActivity.class);
                startActivity(intent);
            }
        });

        //contentprovider 组件

        Button button_1_18 =findViewById(R.id.button_1_18);
        button_1_18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, ContentProviderActivity.class);
                startActivity(intent);
            }
        });

        Button button_1_19 =findViewById(R.id.button_1_19);
        button_1_19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, Multimedia.class);
                startActivity(intent);
            }
        });

        Button button_1_20 = findViewById(R.id.button_1_20);
        button_1_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        });

        Button button_1_21 = findViewById(R.id.button_1_21);
        button_1_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, NetWorkActivity.class);
                startActivity(intent);
            }
        });

        Button button_1_22 = findViewById(R.id.button_1_22);
        button_1_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, HandleMainActivity.class);
                startActivity(intent);
            }
        });

        Button button_1_23=findViewById(R.id.button_1_23);
        button_1_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, MyServiceMainActivity.class);
                startActivity(intent);
            }
        });

        Button button_1_24 =findViewById(R.id.button_1_24);
        button_1_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this, DownloadMainActivity.class));
            }
        });

        Button button_1_25 =findViewById(R.id.button_1_25);
        button_1_25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this, LocationAndroidGpsActivity.class));
            }
        });

        Button button_1_26 =findViewById(R.id.button_1_26);
        button_1_26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this, MaterialDesignMainActivity.class));
            }
        });

        Button button_1_27 =findViewById(R.id.button_1_27);
        button_1_27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this, BaiduMainActivity.class));
            }
        });





    }
   //加载菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    //点击菜单时间
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.add_itemId:
                Toast.makeText(this,"你点击了add_item",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.removeId:
                Toast.makeText(this,"你点击了RemoveId",Toast.LENGTH_LONG).show();
                break;
            default:
        }
        return  true;
    }

    //接收startActivityForResult(startActivityForResult)的回参


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 1:
                if(RESULT_OK==resultCode){
                   String return_data  = data.getStringExtra("data_return");
                    Log.d("debug",return_data);
                    TextView textView = findViewById(R.id.text1_1);
                    textView.setText(return_data);
                }
            default:

        }

    }

    /**
     *  用于活动在销毁前保存数据
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("data_Bundle","some thing should be save");
    }



    /*//button点击事件另外一种写法
    public  void localBroadCast(View view){
        Intent intent = new Intent(FirstActivity.this, LocalBroadcastReceiver.class);
        startActivity(intent);
    }*/

}
