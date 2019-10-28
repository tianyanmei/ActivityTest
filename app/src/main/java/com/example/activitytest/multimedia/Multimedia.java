package com.example.activitytest.multimedia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.activitytest.R;

import java.io.File;
import java.io.IOException;


public class Multimedia extends AppCompatActivity implements View.OnClickListener {

    public  static  final  String CHANNEL_ID ="channel1";


    public  static  final  int TAKE_PHONE=1;
    public  static  final  int CHOOSE_PICTURE=2;

    private ImageView imageView;

    private  Uri imageUri;

    //音乐部分
    private MediaPlayer mediaPlayer = new MediaPlayer();

    //视频部分

    private VideoView videoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);
        Button multimedia_notificationId = findViewById(R.id.multimedia_notificationId);
        multimedia_notificationId.setOnClickListener(this);

        Button  multimedia_takePhoneId = findViewById(R.id.multimedia_takePhoneId);
        multimedia_takePhoneId.setOnClickListener(this);

        Button multimedia_takePhoneWatchId = findViewById(R.id.multimedia_takePhoneWatchId);
        multimedia_takePhoneWatchId.setOnClickListener(this);

        // 音乐部分
        Button initMediaPlayer = findViewById(R.id.initMediaPlayer);
        initMediaPlayer.setOnClickListener(this);


        Button startMediaPlayer = findViewById(R.id.startMediaPlayer); //开始
        startMediaPlayer.setOnClickListener(this);

        Button pauseMediaPlayer =findViewById(R.id.pauseMediaPlayer);
        pauseMediaPlayer.setOnClickListener(this);

        Button resetMediaPlayer = findViewById(R.id.resetMediaPlayer);
        resetMediaPlayer.setOnClickListener(this);

        //视频部分
        videoView = findViewById(R.id.videoViewId);
        initVedioPath();

        Button startVideoPlayer = findViewById(R.id.startVideoPlayer);
        startVideoPlayer.setOnClickListener(this);

        Button pauseVideoPlayer =findViewById(R.id.pauseVideoPlayer);
        pauseVideoPlayer.setOnClickListener(this);

        Button stopVideoPlayer = findViewById(R.id.restVideoPlayer);
        stopVideoPlayer.setOnClickListener(this);
















    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.multimedia_notificationId:
                Log.i("multimedia","通知开始");

                Intent intent = new Intent(this,Notificatioin_layout.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this,
                        0,intent,0);

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
               // Notification notification = new NotificationCompat().Builder(this,"default"); 过时

               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                            "默认通知", NotificationManager.IMPORTANCE_HIGH);
                    manager.createNotificationChannel(notificationChannel);
                }*/

               //高版本需要渠道
               if(Build.VERSION.SDK_INT >Build.VERSION_CODES.O){
                   NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                           "默认通知",NotificationManager.IMPORTANCE_HIGH);
                   notificationManager.createNotificationChannel(notificationChannel);
               }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
                builder.setContentTitle("通知头").setContentText("通知内容1"). //setContentText 信息过长显示不全
                         setWhen(System.currentTimeMillis()).setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.naruto01))
                        .setAutoCancel(true).setContentIntent(pendingIntent).setSound(Uri.fromFile(  //声音
                                new File("/system/media/audio/ringtones/blues.ogg")))
                        .setVibrate(new long[]{0,1000,1000,1000}).setLights(Color.GREEN,1000,1000)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("通知内容通知内容通知内容通知内容通知内容通知内容通知内容通知内容通知内容通知" +
                                "通知内容通知内容通知内容通知内容通知内容通知内容通知内容内容通知内容通知内容通知内容"))
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.mt401)))
                        //.setPriority(NotificationCompat.PRIORITY_MAX) 通知的重要程度
                        ;
                        //.setDefaults(NotificationCompat.DEFAULT_ALL);//默认
                notificationManager.notify(1,builder.build());
                Log.i("multimedia","通知结束");
                break;

            case R.id.multimedia_takePhoneId :
                //创建一个用于存储照片的file
                File file = new File(getExternalCacheDir(),"output_image.jpg");
                try{
                    if(file.exists()){
                        file.delete();
                    }

                    file.createNewFile(); //创建新文件
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(Build.VERSION.SDK_INT >  Build.VERSION_CODES.M){
                    imageUri= FileProvider.getUriForFile(Multimedia.this,
                            "com.example.activitytest.fileprovider",file);
                }else {
                    imageUri=Uri.fromFile(file);
                }
                Intent takePhoneintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePhoneintent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(takePhoneintent,TAKE_PHONE);
                break;
            case R.id.multimedia_takePhoneWatchId:
                //判断是否有权限
                if(ContextCompat.checkSelfPermission(Multimedia.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Multimedia.this,new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
                }else {
                    openPicture();
                }
                break;
            case R.id.initMediaPlayer: //播放开始
                if(ContextCompat.checkSelfPermission(Multimedia.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                !=PackageManager.PERMISSION_GRANTED){
                  ActivityCompat.requestPermissions(Multimedia.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                  ,1002);
                }else {
                    initMediaPalyer();
                }
                break;
            case R.id.startMediaPlayer :
                if(!mediaPlayer.isPlaying()){
                    Log.i("multimedia","mediaPlayer Start");
                    mediaPlayer.start();
                }
                break;
            case R.id.pauseMediaPlayer:
                if(mediaPlayer.isPlaying()){
                    Log.i("multimedia","mediaPlayer pause");
                    mediaPlayer.pause();
                }
                break;
            case R.id.resetMediaPlayer:
                Log.i("multimedia","mediaPlayer pause");
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    initMediaPalyer();
                }
                break;
            case R.id.startVideoPlayer:
                Log.i("multimedia","startVideoPlayer");
                if(!videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.pauseVideoPlayer:
                Log.i("multimedia","pauseVideoPlayer");
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                break;
            case R.id.restVideoPlayer:
                Log.i("multimedia","restVideoPlayer");
                if(videoView.isPlaying()){
                    videoView.resume();
                }
                break;

            default:
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHONE:
                if(RESULT_OK == resultCode){
                    //BitmapFactory.decodeResource(getResources(),R.drawable.mt401);
                    try{
                        imageView = findViewById(R.id.multimedia_takePhoneImageId);
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(imageUri));
                        imageView.setImageBitmap(bitmap);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PICTURE:
                if(RESULT_OK ==resultCode){
                    if(Build.VERSION.SDK_INT >Build.VERSION_CODES.JELLY_BEAN_MR2){
                        handleImageOnkitkat(data);
                    }else {
                        handleImageBeforeOnkitkat(data);
                    }
                }
                break;

            default:

        }
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case  1001:
                if(grantResults.length>0 && PackageManager.PERMISSION_GRANTED ==grantResults[0]){
                    openPicture();
                }else {
                    Toast.makeText(Multimedia.this,"权限申请未通过",
                            Toast.LENGTH_LONG).show();
                }
            break;
            case 1002:
                if(grantResults.length>0 && PackageManager.PERMISSION_GRANTED ==grantResults[0]){
                    initMediaPalyer();
                }else {
                    Toast.makeText(Multimedia.this,"权限申请未通过",
                            Toast.LENGTH_LONG).show();
                }


        }
    }

    public  void openPicture(){ //查看照片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,CHOOSE_PICTURE);
        }else {
            Toast.makeText(Multimedia.this,"未找到图片查看器",Toast.LENGTH_LONG).show();
        }
    }

    @TargetApi(19)
    public  void  handleImageOnkitkat(Intent data){
        String imagePath="";
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(Multimedia.this,uri)){
            //如果是document类型的uri，则通过document 的id处理
            String documentId= DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID +"="+id;
                imagePath =getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(documentId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的uri，直接用普通的方式处理
            imagePath=getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath=uri.getPath();
        }

        dispalyImage(imagePath);
    }

    public void  handleImageBeforeOnkitkat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        dispalyImage(imagePath);
    }

    public  String getImagePath(Uri uri,String selection){
        String imagePath="";
        Cursor cursor = getContentResolver().query(uri,null,selection,
                null,null);
        if(cursor !=null) {
            if (cursor.moveToFirst()) {
                imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return  imagePath;

    }


    public  void  dispalyImage(String imagePath){
        Log.i("multimedia","路径："+imagePath);
        if(imagePath !=null && !"".equals(imagePath.trim())){
            Bitmap bitmap =BitmapFactory.decodeFile(imagePath);
            ImageView imageView = findViewById(R.id.multimedia_takePhoneImageId);
            imageView.setImageBitmap(bitmap);
        }else {
            Toast.makeText(Multimedia.this,"获取图片路径失败",Toast.LENGTH_LONG).show();
        }

    }

    //音乐播放
    public  void initMediaPalyer(){
        try{
            if(Environment.getExternalStorageState()==Environment.MEDIA_MOUNTED){
                  Log.i("multimedia","SD卡存在");
            }else {
                Log.i("multimedia","SD卡不存在");
            }
            File sdkFile = Environment.getExternalStorageDirectory();

            File file = new File(sdkFile,"music.mp3");
            Log.i("multimedia","=---"+file);

            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare(); //准备播放
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //视频初始化
    public  void  initVedioPath(){
        try {
            File file =new File(Environment.getExternalStorageDirectory(),"movie.mp4");
            videoView.setVideoPath(file.getPath());

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer !=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        if(videoView !=null){
            videoView.suspend();
        }

    }
}
