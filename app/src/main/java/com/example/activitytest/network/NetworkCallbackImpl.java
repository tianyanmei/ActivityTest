package com.example.activitytest.network;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

import androidx.annotation.NonNull;

public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        Log.d("network", "onAvailable: 网络已连接");
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        Log.d("network", "onAvailable: 网络已断开");
    }


    @Override
    public void onCapabilitiesChanged(@NonNull Network network,
                                      @NonNull NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);

        if(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)){
            if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                Log.i("network","onCapabilitiesChanged: 网络类型为wifi");
            }else if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                Log.i("network","onCapabilitiesChanged: 蜂窝网络");
            }else {
                Log.i("network","onCapabilitiesChanged: 其他网络");
            }
        }

    }
}
