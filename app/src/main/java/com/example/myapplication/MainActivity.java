package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_shape).setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_shape) {
            Intent intent = new Intent(this, coreActivity.class);
            startActivity(intent);
        }
    }
}
