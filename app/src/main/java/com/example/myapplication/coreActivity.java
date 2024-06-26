package com.example.myapplication;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.adapter.TabPagerAdapter;
import com.example.myapplication.fragment.TabFirstFragment;

public class coreActivity extends AppCompatActivity {
    private NEWViewPager vp_content; // 声明一个翻页视图对象
    private RadioGroup rg_tabbar; // 声明一个单选组对象
    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);
        mPhone = getIntent().getStringExtra("phone");
        Toolbar tl_head = findViewById(R.id.tl_head);

        setSupportActionBar(tl_head); // 使用tl_head替换系统自带的ActionBar
        vp_content = findViewById(R.id.vp_content); // 从布局文件获取翻页视图
        // 构建一个翻页适配器
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager());
        vp_content.setAdapter(adapter); // 设置翻页视图的适配器
        // 给翻页视图添加页面变更监听器
        vp_content.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 选中指定位置的单选按钮
                rg_tabbar.check(rg_tabbar.getChildAt(position).getId());
            }
        });
        rg_tabbar = findViewById(R.id.rg_tabbar); // 从布局文件获取单选组
        // 设置单选组的选中监听器
        rg_tabbar.setOnCheckedChangeListener((group, checkedId) -> {
            for (int pos=0; pos<rg_tabbar.getChildCount(); pos++) {
                // 获得指定位置的单选按钮
                RadioButton tab = (RadioButton) rg_tabbar.getChildAt(pos);
                if (tab.getId() == checkedId) { // 正是当前选中的按钮
                    vp_content.setCurrentItem(pos); // 设置翻页视图显示第几页

                }
            }
        });
    }

    public String getTitles(){
        return mPhone;
    }


    /*重新加载布局(刷新)*/
    public void reLoadFragView(){
        /*现将该fragment从fragmentList移除*/
        setContentView(R.layout.activity_core);
        mPhone = getIntent().getStringExtra("phone");
        Toolbar tl_head = findViewById(R.id.tl_head);

        setSupportActionBar(tl_head); // 使用tl_head替换系统自带的ActionBar
        vp_content = findViewById(R.id.vp_content); // 从布局文件获取翻页视图
        // 构建一个翻页适配器
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager());
        vp_content.setAdapter(adapter); // 设置翻页视图的适配器
        // 给翻页视图添加页面变更监听器
        vp_content.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 选中指定位置的单选按钮
                rg_tabbar.check(rg_tabbar.getChildAt(position).getId());
            }
        });
        rg_tabbar = findViewById(R.id.rg_tabbar); // 从布局文件获取单选组
        // 设置单选组的选中监听器
        rg_tabbar.setOnCheckedChangeListener((group, checkedId) -> {
            for (int pos=0; pos<rg_tabbar.getChildCount(); pos++) {
                // 获得指定位置的单选按钮
                RadioButton tab = (RadioButton) rg_tabbar.getChildAt(pos);
                if (tab.getId() == checkedId) { // 正是当前选中的按钮
                    vp_content.setCurrentItem(pos); // 设置翻页视图显示第几页
                }
            }
        });

    }


}
