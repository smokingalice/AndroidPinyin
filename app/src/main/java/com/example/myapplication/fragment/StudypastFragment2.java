package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.b;


public class StudypastFragment2 extends Fragment {
    private static final String TAG = "StudypastFragment2";
    protected View mView; // 声明一个视图对象
    protected Context mContext; // 声明一个上下文对象

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity(); // 获取活动页面的上下文
        // 根据布局文件fragment_tab_first.xml生成视图对象
        mView = inflater.inflate(R.layout.studypast2, container, false);
        return mView;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        ImageButton myOther = (ImageButton) getActivity().findViewById(R.id.bimageButton);


        myOther.setOnClickListener(new View.OnClickListener() {
            /**
             二、从一个Activity的Fragment跳转到另外一个Activity(等同于Activity之间的跳转（上下文是getActivity）)
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), b.class);
                startActivity(intent);
            }
        });
    }




}
