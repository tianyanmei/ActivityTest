package com.example.activitytest;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 *  用于控制所有的activity的管理
 */
public class ActivityController {

    public  static List<Activity> activities = new ArrayList<Activity>();

    public  static  void  addActivity(Activity activity){
        activities.add(activity);
    }

    public  static  void removeActivity(Activity activity){
        activities.remove(activity);
    }


    public static  void finishAll(){
        for (Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }





}
