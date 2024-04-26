package com.example.myapplication;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class bootActivity extends AppCompatActivity {

    ProgressBar splashProgress;
    int SPLASH_TIME = 1000;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //去掉导航栏
        StatusBar statusBar = new StatusBar(bootActivity.this);
        statusBar.setColor(R.color.transparent);
        setContentView(R.layout.activity_boot);

            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //进度条
        TextView tv_splash = findViewById(R.id.textb);
        //得到字体库类型
        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "ee.ttf");
        tv_splash.setTypeface(typeface);

        //启动和切换界面
        new Handler(getMainLooper()).postDelayed(new Runnable()
        {
            @Override
            public void run() {
                //切换到主界面
                Intent mySuperIntent = new Intent(bootActivity.this, MainActivity.class);
                startActivity(mySuperIntent);

                finish();

            }
        }, SPLASH_TIME);
    }

    private void playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 100)
                .setDuration(3000)
                .start();
    }

}