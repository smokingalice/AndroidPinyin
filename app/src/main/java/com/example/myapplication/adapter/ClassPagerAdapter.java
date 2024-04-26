package com.example.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.fragment.StudypastFragment;
import com.example.myapplication.fragment.StudypastFragment2;
import com.example.myapplication.fragment.StudypastFragment3;
import com.example.myapplication.fragment.StudypastFragment4;
import com.example.myapplication.fragment.StudypastFragment5;

import java.util.List;

public class ClassPagerAdapter extends FragmentStateAdapter {
    private List<String> mTitleList; // 声明一个标题文字列表

    // 碎片页适配器的构造方法，传入碎片管理器与标题列表
    public ClassPagerAdapter(FragmentActivity fa, List<String> titleList) {
        super(fa);
        mTitleList = titleList;
    }

    // 创建指定位置的碎片Fragmen
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new StudypastFragment();
        } else if (position == 1){
            return new StudypastFragment2();
        }else if (position == 2){
            return new StudypastFragment3();
        }else if(position == 3){
            return new StudypastFragment4();
        }else if(position == 4){
            return new StudypastFragment5();
        }  else {
            return null;
        }
    }

    // 获取碎片Fragment的个数
    @Override
    public int getItemCount() {
        return mTitleList.size();
    }

}
