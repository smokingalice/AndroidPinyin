package com.example.myapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.SDActivity;
import com.example.myapplication.bean.UserInfo;
import com.example.myapplication.coreActivity;
import com.example.myapplication.database.UserDBHelper;
import com.example.myapplication.fache;

import java.util.List;
import java.util.Objects;

public class TabSecondFragment extends Fragment {
    private static final String TAG = "TabSecondFragment";
    protected View mView; // 声明一个视图对象
    protected Context mContext; // 声明一个上下文对象
    String PHONE="";
    private UserDBHelper mHelper; // 声明一个用户数据库帮助器的对象

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        PHONE = ((coreActivity) activity).getTitles();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity(); // 获取活动页面的上下文
        // 根据布局文件fragment_tab_second.xml生成视图对象
        mView = inflater.inflate(R.layout.fragment_tab_second, container, false);

        return mView;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        Button myOther = (Button) getActivity().findViewById(R.id.btn_rect);


        myOther.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SDActivity.class);
                mHelper = UserDBHelper.getInstance(getActivity(), 1);
                mHelper.openReadLink(); // 打开数据库帮助器的读连接
                mHelper.openWriteLink(); // 打开数据库帮助器的写连接
                List<UserInfo> userList = mHelper.query("1=1");
                int i;
                for ( i = 0; i < userList.size(); i++) {
                    UserInfo info = userList.get(i);
                    if(Objects.equals(info.phone,PHONE))
                    {
                        info.study++;
                    }
                    mHelper.update(info);

                }
                startActivity(intent);
            }
        });

        Button myOther2 = (Button) getActivity().findViewById(R.id.btn_oval);


        myOther2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), fache.class);

                mHelper = UserDBHelper.getInstance(getActivity(), 1);
                mHelper.openReadLink(); // 打开数据库帮助器的读连接
                mHelper.openWriteLink(); // 打开数据库帮助器的写连接
                List<UserInfo> userList = mHelper.query("1=1");
                int i;
                for ( i = 0; i < userList.size(); i++) {
                    UserInfo info = userList.get(i);
                    if(Objects.equals(info.phone,PHONE))
                    {
                        info.game++;
                    }
                    mHelper.update(info);

                }
                startActivity(intent);
            }
        });
    }

}
