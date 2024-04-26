package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
   public  static Boolean a=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar tl_head = findViewById(R.id.tl_head2);
        tl_head.setTitle("登录"); // 设置工具栏的标题文字
        findViewById(R.id.btn_shape).setOnClickListener(this);
    }
    public void onClick(View v) {
        if (v.getId() == R.id.btn_shape) {
            Intent intent = new Intent(this, coreActivity.class);
            startActivity(intent);
        }
    }
}
