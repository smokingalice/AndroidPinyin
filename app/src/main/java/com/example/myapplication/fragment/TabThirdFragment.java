package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.SDActivity;
import com.example.myapplication.Web1Activity;
import com.example.myapplication.Web2Activity;
import com.example.myapplication.bean.UserInfo;
import com.example.myapplication.database.UserDBHelper;
import com.example.myapplication.fache;

import java.util.List;
import java.util.Objects;

public class TabThirdFragment extends Fragment {
    private static final String TAG = "TabThirdFragment";
    protected View mView; // 声明一个视图对象
    protected Context mContext; // 声明一个上下文对象

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity(); // 获取活动页面的上下文
        // 根据布局文件fragment_tab_third.xml生成视图对象
        mView = inflater.inflate(R.layout.fragment_tab_third, container, false);


        return mView;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Typeface typeface = Typeface.createFromAsset(requireActivity().getAssets(),
                "ee.ttf");
        Button myOther = (Button) getActivity().findViewById(R.id.vedio);
        myOther.setTypeface(typeface);
        myOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Web1Activity.class);
                startActivity(intent);
            }
        });
        Button myOther2= (Button) getActivity().findViewById(R.id.file);
        myOther2.setTypeface(typeface);
        myOther2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Web2Activity.class);
                startActivity(intent);
            }
        });

    }
}
