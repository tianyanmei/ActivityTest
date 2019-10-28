package com.example.activitytest.untils.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class HttpUrlConnect {


    //在类中调用此方法
    public static void getHttpUrlConntect(final String addressUrl,
                 final HttpCallbackListener httpCallbackListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection=null;
                BufferedReader reader = null;
                try{
                    URL url = new URL(addressUrl);
                    httpURLConnection =(HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(80000);
                    httpURLConnection.setReadTimeout(80000);
                    InputStream in = httpURLConnection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line=null;
                    while ((line=reader.readLine())!=null){
                        stringBuffer.append(line);
                    }

                    if (httpCallbackListener !=null){
                        httpCallbackListener.finish(stringBuffer.toString());
                    }

                }catch (Exception e){
                    if (httpCallbackListener !=null){
                        httpCallbackListener.error(e);
                    }
                }finally {
                    try{
                        if(reader !=null){
                            reader.close();
                        }
                        if(httpURLConnection !=null){
                            httpURLConnection.disconnect();
                        }
                    }catch (IOException e){
                        if (httpCallbackListener !=null){
                            httpCallbackListener.error(e);
                        }
                    }
                }
            }
        }).start();
    };

    public  static  void getOkHttpUrl(String addressUrl, Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(addressUrl).build();
        okHttpClient.newCall(request).enqueue(callback);
    }



}
