package com.example.myapplication.fragment;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.SDActivity;
import com.example.myapplication.a;
import com.example.myapplication.bean.UserInfo;
import com.example.myapplication.coreActivity;
import com.example.myapplication.database.UserDBHelper;
import com.example.myapplication.fache;
import com.example.myapplication.util.ToastUtil;

import java.util.List;
import java.util.Objects;

public class TabFirstFragment extends Fragment {
    private static final String TAG = "TabFirstFragment";
    protected View mView; // 声明一个视图对象
    protected Context mContext; // 声明一个上下文对象
    protected Context mContext2;
    private String mPhone;
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
        Bundle bundle = getArguments();
        if (bundle != null) {
            PHONE = bundle.getString("phone");
        }
        // 根据布局文件fragment_tab_first.xml生成视图对象
        mView = inflater.inflate(R.layout.fragment_tab_first, container, false);

        return mView;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String name = null;
        int age = 0;
        long height = 0;
        int remnber2 =0;


        TextView tv_splash = ( TextView) requireActivity().findViewById(R.id.name);
        TextView tv_splash2 = ( TextView) requireActivity().findViewById(R.id.age);
        TextView tv_splash3 = ( TextView) requireActivity().findViewById(R.id.study);
        TextView tv_splash4 = ( TextView) requireActivity().findViewById(R.id.game);
        //得到字体库类型
        Typeface typeface = Typeface.createFromAsset(requireActivity().getAssets(),
                "ee.ttf");
        mHelper = UserDBHelper.getInstance(getActivity(), 1);
        mHelper.openReadLink(); // 打开数据库帮助器的读连接
        if (mHelper == null) {
            ToastUtil.show(getActivity(), "数据库连接为空");
            return;
        }
        // 执行数据库帮助器的查询操作
        List<UserInfo> userList = mHelper.query("1=1");
        for (int i = 0; i < userList.size(); i++) {
            UserInfo info = userList.get(i);
            if(Objects.equals(info.phone,PHONE))
            {
                name=info.name;
                age= info.age;
                height= info.study;
                remnber2=(int)info.game;
            }
        }
        if (userList.size() <= 0) {
            ToastUtil.show(getActivity(), "数据库查询为空");
        }
        tv_splash.setTypeface(typeface);
            tv_splash.setText("账户名称:   "+name);
        tv_splash2.setTypeface(typeface);
        tv_splash2.setText("年龄：   "+age+"岁");
        tv_splash3.setTypeface(typeface);
        tv_splash3.setText("学习次数：  "+height+"次");
        tv_splash4.setTypeface(typeface);
        tv_splash4.setText("游戏次数： "+remnber2+"次");
        Button myOther = (Button) getActivity().findViewById(R.id.refrash);
        myOther.setTypeface(typeface);
        myOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               coreActivity activity= (coreActivity) getActivity();
                activity.reLoadFragView();
            }
        });
    }

}