package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.fache;
import com.example.myapplication.fache2;
import com.example.myapplication.fache3;
import com.example.myapplication.fache4;
import com.example.myapplication.fache5;

import java.util.Objects;

public class StudySecondFragment extends Fragment {
    private static final String TAG = "TabThirdFragment";
    protected View mView; // 声明一个视图对象
    protected Context mContext; // 声明一个上下文对象

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity(); // 获取活动页面的上下文
        // 根据布局文件fragment_tab_third.xml生成视图对象
        mView = inflater.inflate(R.layout.fragment_study_second, container, false);

        return mView;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



       ImageButton myOther = (ImageButton) requireActivity().findViewById(R.id.studyspeach1);
        ImageButton myOther2 = (ImageButton) requireActivity().findViewById(R.id.studyspeach2);
        ImageButton myOther3 = (ImageButton) requireActivity().findViewById(R.id.studyspeach3);
        ImageButton myOther4= (ImageButton) requireActivity().findViewById(R.id.studyspeach4);
        ImageButton myOther5 = (ImageButton) requireActivity().findViewById(R.id.studyspeach5);


        myOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), fache.class);
                startActivity(intent);
            }
        });



        myOther2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), fache2.class);
                startActivity(intent);
            }
        });
        myOther3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), fache3.class);
                startActivity(intent);
            }
        });
        myOther4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), fache4.class);
                startActivity(intent);
            }
        });
        myOther5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), fache5.class);
                startActivity(intent);
            }
        });


    }

}
