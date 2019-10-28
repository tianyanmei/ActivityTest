package com.example.activitytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.activitytest.fragment.Anther_right_fragment;
import com.example.activitytest.fragment.Right_fragment;

public class FragmentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Button left_Button = findViewById(R.id.button_fragment_leftId);

        left_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new Anther_right_fragment());
            }
        });

        replaceFragment(new Right_fragment());

    }

    public  void  replaceFragment(Fragment fragment){
        FragmentManager fragmentManager  = getSupportFragmentManager();

        //获取当前的fragment
       // Fragment currentFragment = fragmentManager.findFragmentById(R.id.right_fragment_mainId);

        //Log.d("Fragment",String.valueOf(currentFragment));

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.remove(currentFragment);
        fragmentTransaction.replace(R.id.right_fragment_mainId,fragment);

        fragmentTransaction.addToBackStack(null); //将一个事务添加到返回栈中
        fragmentTransaction.commit();



    }

}
