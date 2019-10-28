package com.example.activitytest.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.activitytest.R;
import com.example.activitytest.network.NetworkCallbackImpl;

/**
 * 动态监视网络变化的小例子
 *
 */


public class DynamicRegistBroadcastActivity extends AppCompatActivity {


    private IntentFilter intentFilter; //IntentFilter对象负责过滤掉组件无法响应和处理的Intent，只将自己关心的Intent接收进来进行处理。 IntentFilter实行“白名单”管理，即只列出组件乐意接受的Intent，
                                     // 但IntentFilter只会过滤隐式Intent，显式的Intent会直接传送到目标组件

    private  NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_regist_broadcast);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    class  NetworkChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(
                    Context.CONNECTIVITY_SERVICE); //通过系统服务类获取一个实例
            //ConnectivityManager 用于网络管理的

           /*//29开始已废弃，推荐使用
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo !=null &&networkInfo.isAvailable()){
                Toast.makeText(context,"网络变化:有网络",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context,"网络变化：无网络",Toast.LENGTH_SHORT).show();
            }*/


           //--------开始 21以上-------------//

            NetworkCallbackImpl networkCallback = new NetworkCallbackImpl();

            NetworkRequest.Builder builder = new NetworkRequest.Builder();

            NetworkRequest request = builder.build();

           /* ConnectivityManager connMgr = (ConnectivityManager) NetworkListener.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connMgr != null) {
                connMgr.registerNetworkCallback(request, networkCallback);
            }*/
            if(connectivityManager !=null){
                connectivityManager.registerNetworkCallback(request,networkCallback);
            }
            //--------结束 21以上-------------//

        }

        /**
         * 测试ConnectivityManager
         * ConnectivityManager主要管理和网络连接相关的操作
         * 相关的TelephonyManager则管理和手机、运营商等的相关信息；WifiManager则管理和wifi相关的信息。
         * 想访问网络状态，首先得添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
         * NetworkInfo类包含了对wifi和mobile两种网络模式连接的详细描述,通过其getState()方法获取的State对象则代表着
         * 连接成功与否等状态。
         *
         */







    }


    public void mySendBroadCast(View view){

        //Android 8.0变更之后台执行限制（自定义广播接收不到的问题）
        //解决方案：
        //方案一 ：使用动态注册广播接收器代替静态注册广播接收器。
        //可以通过更改广播方式设置ComponentName为显式广播（指定包名）：
        //
        //Intent it = new Intent("目标广播接收器对应的action");
        //it.setComponent(new ComponentName("目标广播接收器所在应用的包名","目标广播接收器类全路径"));
        //sendBroadcast(it);
        Intent it = new Intent("com.example.intent.MY_BROADCAST_RECEIVER");
        it.setComponent(new ComponentName(this,"com.example.activitytest.broadcast.MyBroadcastReceiver"));
        sendBroadcast(it);
    }




}
