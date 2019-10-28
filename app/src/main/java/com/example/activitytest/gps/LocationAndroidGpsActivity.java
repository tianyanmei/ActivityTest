package com.example.activitytest.gps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.activitytest.BaseActivity;
import com.example.activitytest.R;

import java.util.Locale;

//纯android源生的实现gps定位信息
//android 定位一般有四种方法，这四种方式分别是： GPS定位，WIFI定位，基站定位，AGPS定位。
public class LocationAndroidGpsActivity extends BaseActivity implements View.OnClickListener {

    private TextView location_GPS_textView_id;

    private LocationManager locationManager;

    private Location location;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            location=location;
            if(location !=null){
                Log.i("GPS","时间:"+location.getTime());
                Log.i("GPS","纬度:"+location.getLatitude());
                Log.i("GPS","经度:"+location.getLongitude());
                Log.i("GPS","海拔:"+location.getAltitude());
            }else {
                Log.d("GPS","地址为空,未获取到具体的位置信息:");
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle bundle) {
            switch (status){
                // GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    Log.i("GPS", "当前GPS状态为可见状态");
                    break;
                // GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i("GPS", "当前GPS状态为服务区外状态");
                    break;
                // GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i("GPS", "当前GPS状态为暂停服务状态");
                    break;
                default:
                    break;
            }
        }

        //GPS开启时触发
        @Override
        public void onProviderEnabled(String provider) {
            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1){
                if(ContextCompat.checkSelfPermission(LocationAndroidGpsActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(LocationAndroidGpsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!=
                                PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(LocationAndroidGpsActivity.this,new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1001);
                }

            }
            location =locationManager.getLastKnownLocation(provider);

        }

        //GPS禁用时触发
        @Override
        public void onProviderDisabled(String s) {
            location =null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_android_gps);

        Button location_getGps_button =findViewById(R.id.location_getGps_button);
        location_getGps_button.setOnClickListener(this);

        location_GPS_textView_id =findViewById(R.id.location_GPS_textView_id);

        //获取locationManager
        //LocationManager.NETWORK_PROVIDER 移动网络中获取位置，精度较低但速度很快
       // LocationManager.GPS_PROVIDER  用GPS进行定位，精度很高但一般需要10-60秒时间才能开始第1次定位，如果是在室内则基本上无法定位。
        locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.location_getGps_button:
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){ //判断GPS是否开启
                    Log.i("GPS","开始去设置GPS界面");
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent,0);
                }

                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1){
                    if(ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=
                            PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(this,new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1001);
                    }

                }


                // 为获取地理位置信息时设置查询条件
                String bestProvider =locationManager.getBestProvider(getCriteria(),true);
                Log.i("GPS","bestProvider="+bestProvider);



                //通过getLastKnownLocation(String provider)传对应参数，
                // 此时得到的Location并非当前的GPS位置信息，而是上一次获取到的位置信息，
                // 而requestLocationUpdates才是真正去请求位置信息的更新。
                location =locationManager.getLastKnownLocation(bestProvider);

                // 监听状态
                //        mLocationManager.addGpsStatusListener(listener);

                // 绑定监听，有4个参数
                // 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
                // 参数2，位置信息更新周期，单位毫秒
                // 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
                // 参数4，监听
                // 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新

                // 1秒更新一次，或最小位移变化超过1米更新一次；
                // 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,locationListener);


                break;

            default:
                break;
        }

    }

    public Criteria getCriteria(){
        Criteria criteria = new Criteria();
        //设置定位精度
        //  Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);

        // 设置是否需要方位信息
        criteria.setBearingRequired(false);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        //setHorizontalAccuracy(int accuracy)　设置水平方向的精度（ACCURACY_HIGH,ACCURACY_LOW，ACCURACY_MEDIUM）

        //setSpeedAccuracy(int accuracy) 设置速度精度

        //setVerticalAccuracy(int accuracy) 设置垂直方向的精度（ACCURACY_HIGH, ACCURACY_LOW，ACCURACY_MEDIUM）

        //setPowerRequirement(int level) 设置耗电 NO_REQUIREMENT, POWER_LOW,POWER_HIGH, POWERMEDIUM



        return criteria;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 0:
                Log.i("GPS","返回去设置GPS界面:"+resultCode);
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1001:
                if(grantResults.length>0){
                    Log.i("GPS","返回去设置GPS界面:"+grantResults.toString());
                }
                break;
            default:
                break;

        }


    }
}
