package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.adapter.ClassPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class studyActivity extends AppCompatActivity {
    private final List<String> mPageList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_class);
        // 从布局文件中获取名叫tab_title的标签布局
        Toolbar tl_head = findViewById(R.id.tl_head);

       setSupportActionBar(tl_head); // 使用tl_head替换系统自带的ActionBar
        tl_head.setTitle("学习模式"); // 设置工具栏的标题文字
        mPageList.add("单韵母");
        mPageList.add("声母");
        mPageList.add("复韵母");
        mPageList.add("鼻韵母");
        mPageList.add("整体认读");
        TabLayout tab_title = findViewById(R.id.tab_title);
        // 从布局文件中获取名叫vp2_content的二代翻页视图
        ViewPager2 vp2_content = findViewById(R.id.vp2_content);
        // 构建一个分类信息的翻页适配器。注意Fragment嵌套时要传getChildFragmentManager
        ClassPagerAdapter adapter = new ClassPagerAdapter(this, mPageList);
        vp2_content.setAdapter(adapter); // 设置二代翻页视图的适配器
        // 把标签布局跟翻页视图通过指定策略连为一体，二者在页面切换时一起联动
        new TabLayoutMediator(tab_title, vp2_content, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(mPageList.get(position)); // 设置每页的标签文字
            }
        }).attach();

    }
}
