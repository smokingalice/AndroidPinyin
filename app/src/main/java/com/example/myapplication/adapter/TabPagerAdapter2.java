package com.example.myapplication.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.fragment.StudyFirstFragment;
import com.example.myapplication.fragment.StudySecondFragment;

public class TabPagerAdapter2 extends FragmentStatePagerAdapter {

    // 碎片页适配器的构造方法，传入碎片管理器
    public TabPagerAdapter2(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    // 获取指定位置的碎片Fragment
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new StudyFirstFragment();  // 返回第一个碎片
        } else if (position == 1) {
            return new StudySecondFragment();  // 返回第二个碎片
        }
        else {
            return null;
        }
    }

    // 获取碎片Fragment的个数
    @Override
    public int getCount() {
        return 2;
    }
}
