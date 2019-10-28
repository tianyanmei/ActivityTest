package com.example.activitytest.baidu_gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.activitytest.R;

import java.util.ArrayList;
import java.util.List;

public class BaiduMainActivity extends AppCompatActivity implements View.OnClickListener {


    private LocationClient mlocationClient;


    private TextView textView;

    private MapView mapView; //用于显示百度地图
    private BaiduMap baiduMap; //用于显示自己的位置坐标

    private boolean isFirstflag=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mlocationClient = new LocationClient(getApplicationContext());
        mlocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidu_main);
        textView =findViewById(R.id.baidu_showTextView);
        Button button = findViewById(R.id.baidu_get_id);
        button.setOnClickListener(this);
        mapView=findViewById(R.id.baidu_showMapId);
        baiduMap =mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
    }


    //获取当前的位置信息
    public void navigateTo(BDLocation bdLocation){
        if (isFirstflag){
            LatLng latlng = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());

            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latlng);

            baiduMap.animateMapStatus(mapStatusUpdate);

            mapStatusUpdate = MapStatusUpdateFactory.zoomTo(16f);

            baiduMap.animateMapStatus(mapStatusUpdate);
            isFirstflag=false;
        }
        MyLocationData.Builder locationDataBuilder = new MyLocationData.Builder();
        locationDataBuilder.latitude(bdLocation.getLatitude());
        locationDataBuilder.longitude(bdLocation.getLongitude());

        MyLocationData myLocationData = locationDataBuilder.build();

        baiduMap.setMyLocationData(myLocationData);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.baidu_get_id:
                Log.i("GPS","点击事件");
                List<String> permissions= new ArrayList<String>();
                if(ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED){
                    permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
                }
                if(ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
                     permissions.add(Manifest.permission.READ_PHONE_STATE);
                }

                if(ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

                if(!permissions.isEmpty()){
                    String[] permissionAll= permissions.toArray(new String[permissions.size()]);
                    ActivityCompat.requestPermissions(this,permissionAll,1001);
                }else {
                    Log.i("GPS","开启位置服务");
                    requestLocation();
                }
                break;
            default:


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode){
            case 1001:
                if(grantResults.length>0){
                    for (int result:grantResults ){
                        if(result !=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"需要同意所有权限才可以使用",Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();

                }else {
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:

        }
    }

    class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

            String showStr ="纬度信息："+latitude+"\n";
            showStr +="经度信息："+longitude+"\n";
            showStr +="坐标类型为准："+coorType+"\n";
            showStr +="定位错误返回码："+errorCode+"\n";
            if(errorCode ==BDLocation.TypeGpsLocation){
                showStr +="定位方式为：GPS \n";
            }else if (errorCode==BDLocation.TypeNetWorkLocation){
                showStr +="定位方式为：网络 \n";
            }
            showStr +="国家："+location.getCountry()+"\n";
            showStr +="省份："+location.getProvince()+"\n";
            showStr +="市区："+location.getCity()+"\n";
            showStr +="区："+location.getDistrict()+"\n";
            showStr +="街道："+location.getStreet()+"\n";

            Log.i("GPS","---"+showStr);
            textView.setText(showStr);

            if(BDLocation.TypeGpsLocation==location.getLocType()||
            BDLocation.TypeNetWorkLocation==location.getLocType()){
                navigateTo(location);
            }
        }
    }



    public void requestLocation(){
        initLocation();
        mlocationClient.start();

    }

    //移动中定位
    public void initLocation(){
        LocationClientOption locationClientOption = new LocationClientOption();
        //三种定位模式，高精度，网络，传感器
        //locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClientOption.setScanSpan(5000);
        locationClientOption.setIsNeedAddress(true);//是否需要地址
        mlocationClient.setLocOption(locationClientOption);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mlocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


}

