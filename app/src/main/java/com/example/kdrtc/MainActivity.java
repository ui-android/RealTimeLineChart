package com.example.kdrtc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.kdrtc.activity.RealtimeLineChartActivity;
import com.example.kdrtc.fragment.FragmentChart;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btn_call_rt_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*//去掉顶部标题
        getSupportActionBar().hide();
        //去掉最上面时间、电量等
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        setContentView(R.layout.activity_main);

        getSupportFragmentManager()    //
                .beginTransaction()
                .add(R.id.fragment_rt_linechart,new FragmentChart())   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();

        btn_call_rt_activity = findViewById(R.id.btn_call_rt_activity);
        btn_call_rt_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RealtimeLineChartActivity.class);
                startActivity(intent);
            }
        });

    }
}
