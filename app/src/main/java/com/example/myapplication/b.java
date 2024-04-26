package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class b extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b);
        Toolbar tl_head = findViewById(R.id.tl_head);
        tl_head.setTitle("教学视频"); // 设置工具栏的标题文字
        VideoView videoView = findViewById((R.id.videoView));
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.sb;
        videoView.setVideoURI(Uri.parse(uri));
        videoView.setMediaController(new MediaController(this));
        videoView.start();


    }

    @Override
    public void onClick(View v) {

    }

}
