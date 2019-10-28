package com.example.activitytest.downloadtest;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.EventLog;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String,Integer,Integer> {

    public static final int TYPE_SUCCESS=1;
    public  static final int TYPE_FAILED=2;
    public  static final  int TYPE_PAUSE=3;
    public static final int TYPE_CANCLE=4;

    private DownloadListener downloadListener;

    private  boolean isCancle=false;
    private  boolean isPause=false;

    private  int lastProgress;

    public DownloadTask(DownloadListener downloadListener) {
        this.downloadListener=downloadListener;
    }


    public void  cancleDownload(){
        isCancle=true;
    }

    public void  pauseDownload(){
        isPause=true;
    }


    @Override
    protected Integer doInBackground(String... params) {
         //执行具体的处理流程
        InputStream inputStream=null;
        RandomAccessFile savefile=null;
        File file = null;
        try {
            long downloadedLength=0; //记录已经下载的进度
            String downUrl =params[0];
            String fileName =downUrl.substring(downUrl.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS).getPath();
            file =new File(directory+fileName);

            if (file.exists()){
                downloadedLength=file.length();
            }
            long contentLength=getContentLength(downUrl);

            if(0==contentLength){
                return TYPE_FAILED;
            }else if(contentLength ==downloadedLength){ //下载完成
                return TYPE_SUCCESS;
            }

            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder().addHeader("RANGE",
                    "bytes="+downloadedLength+"-").url(downUrl).build();

            Response response = okHttpClient.newCall(request).execute();
            if(response !=null){
                inputStream =response.body().byteStream();
                savefile =new RandomAccessFile(file,"rw");
                savefile.seek(downloadedLength); //跳过已下载的字节点
                byte[] bytes =new byte[1024];
                int total=0;
                int len;
                while ((len=inputStream.read())!=-1){
                    if(isCancle){
                        return TYPE_CANCLE;
                    }else if(isPause){
                        return  TYPE_PAUSE;
                    }else {
                        total+=len;
                        savefile.write(bytes,0,len);//文件写入
                        int progress =(int)((total+downloadedLength)*100/contentLength);
                        publishProgress(progress);
                    }

                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        }catch (Exception e){
            Log.d("downloadTest","doInBackground:"+e.getMessage());
        }finally {
            try {
                if(inputStream !=null){
                    inputStream.close();
                }
                if(savefile !=null){
                    savefile.close();
                }

                //如果取消删除文件
                if(isCancle && file !=null){
                    file.delete();
                }

            }catch (IOException e){
                Log.d("downloadTest","doInBackground+close:"+e.getMessage());
            }

        }

        return TYPE_FAILED;
    }


    @Override
    protected void onPreExecute() { //执行前
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer status) { //执行结束
        switch (status){
            case TYPE_CANCLE:
                downloadListener.onCancle();
                break;
            case TYPE_FAILED:
                downloadListener.onFailed();
                break;
            case TYPE_PAUSE:
                downloadListener.onPaused();
                break;
            case TYPE_SUCCESS:
                downloadListener.onSuccess();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) { //执行实时更新
        int progress = values[0];
        if(progress>lastProgress){
             downloadListener.onProgress(progress);
            lastProgress=progress;
        }

    }



    public long getContentLength(String downloadUrl){

        try {
            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder().url(downloadUrl).build();

            Response response =okHttpClient.newCall(request).execute();

            if(response !=null && response.isSuccessful()){
                long contentLength =response.body().contentLength();
                response.close();
                return contentLength;
            }
        }catch (IOException e){
            Log.d("downloadTest","getContentLength:"+e.getMessage());
        }


        return  0;
    }


}
