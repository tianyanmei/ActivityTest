package com.example.activitytest.material_design;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.activitytest.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MaterialDesignMainActivity extends AppCompatActivity {


    private CoordinatorLayout coordinatorLayout;

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design_main);
        initView();
    }

    public  void  initView(){
        coordinatorLayout =findViewById(R.id.snackbar_container);
        floatingActionButton = findViewById(R.id.snackbar_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("materialDesign","触发点击事件");
                Snackbar.make(coordinatorLayout,"无参数的snackbar",Snackbar.LENGTH_SHORT)
                        .setAction("action", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(coordinatorLayout,"被点击了！",Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        }).addCallback(new Snackbar.Callback(){
                        @Override
                        public void onShown(Snackbar sb) { //Snackbar展示的时候触发回调
                            super.onShown(sb);
                        }

                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) { //Snackbar消失的时触发回调
                            super.onDismissed(transientBottomBar, event);
                        }
                        })
                        .show();
            }
        });

        Button materialDesign_id =findViewById(R.id.materialDesign_id);
        materialDesign_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MaterialDesignMainActivity.this,
                        MaterialDesignBottomNavigation.class));
            }
        });



    }

}
