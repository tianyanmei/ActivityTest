package com.example.activitytest.online;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.activitytest.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetWorkActivity extends AppCompatActivity {

    private TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work);
        Button reqButton = findViewById(R.id.network_id);
        responseText =findViewById(R.id.network_Text_Id);
        reqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestHttpUrlConnection();
            }
        });

        Button network_okhttp_id = findViewById(R.id.network_okhttp_id);

        network_okhttp_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestOkHttpConnection();
            }
        });

        Button network_pull_id = findViewById(R.id.network_pull_id);

        network_pull_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestOkHttpConnection("http://10.6.18.119/get_data.xml");
            }
        });

    }


    public  void  sendRequestHttpUrlConnection(){

        new  Thread(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection httpsURLConnection = null;
                BufferedReader reader=null;
                try {
                    URL uri = new URL("https://www.baidu.com");
                    /*if (uri.getProtocol().toLowerCase().equals("https")) {

                        httpsURLConnection = (HttpsURLConnection) uri.openConnection();

                    }*/
                    httpsURLConnection =(HttpsURLConnection) uri.openConnection();

                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.setConnectTimeout(8000);
                    httpsURLConnection.setReadTimeout(5000);

                    Log.i("network", ""+httpsURLConnection.getResponseCode());

                    InputStream inputStream =httpsURLConnection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line="";
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) !=null){
                        response.append(line);
                    }
                    showResponse(response.toString());


                }catch (Exception e){
                    Log.d("network",e.getMessage());
                }finally {
                    if(reader !=null){
                        try{
                            reader.close();
                        }catch (IOException e){
                            Log.d("network",e.getMessage());
                        }

                    }

                    if(httpsURLConnection!=null){
                        httpsURLConnection.disconnect();
                    }

                }
            }
        }).start();
    }

    public  void  showResponse(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("network",str);
                responseText.setText(str);
            }
        });
    }

    public  void  sendRequestOkHttpConnection(){

        new  Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient okHttpClient = new OkHttpClient();

                    Request request = new Request.Builder().
                            url("https://www.baidu.com").build();
                    Response response = okHttpClient.newCall(request).execute();

                    String str =response.body().string();
                    showResponse(str);

                }catch (IOException e){
                    Log.d("network",e.getMessage());
                }



            }
        }).start();



    }


    public  void  sendRequestOkHttpConnection(final String url){
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new  Request.Builder().url(url).build();
                    Response response = okHttpClient.newCall(request).execute();
                    String str = response.body().string();


                }catch(Exception e){
                    Log.d("network","sendRequestOkHttpConnection--"+e.getMessage());
                }
            }
        }).start();
    }

    public void  showPullXML(final String xml){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    StringBuffer buffer = new  StringBuffer();
                    XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                    XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
                    xmlPullParser.setInput(new StringReader(xml));
                    int eventType = xmlPullParser.getEventType();

                    String id="";
                    String name="";
                    String version="";

                    while (eventType !=xmlPullParser.END_DOCUMENT){
                        String nodeName = xmlPullParser.getName();
                        switch (eventType){
                            case XmlPullParser.START_TAG :
                                if("id".equals(nodeName)){
                                    id =xmlPullParser.nextText();
                                }else if("name".equals(nodeName)){
                                    name = xmlPullParser.nextText();
                                }else if("version".equals(nodeName)){
                                    version = xmlPullParser.nextText();
                                }
                                break ;
                            case XmlPullParser.END_TAG :  //解析完成
                              if("app".equals(nodeName)){
                                  buffer.append("id="+id+",name="+name+",version="+version+";");
                              }
                              break;

                            default:
                                break;
                        }
                        eventType = xmlPullParser.next();
                    }
                    responseText.setText(buffer.toString());
                }catch (Exception e){
                    Log.d("network","showPullXML--"+e.getMessage());
                }


            }
        });





    }






}
