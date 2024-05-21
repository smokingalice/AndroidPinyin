package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.bean.UserInfo;
import com.example.myapplication.database.UserDBHelper;
import com.example.myapplication.util.DateUtil;
import com.example.myapplication.util.ToastUtil;

public class SQLiteWriteActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private UserDBHelper mHelper; // 声明一个用户数据库帮助器的对象
    private EditText et_name; // 声明一个编辑框对象
    private EditText et_age; // 声明一个编辑框对象
    private EditText et_height; // 声明一个编辑框对象
    private EditText et_weight; // 声明一个编辑框对象
    private boolean isMarried = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_write);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        Toolbar tl_head = findViewById(R.id.tl_head);
        tl_head.setTitle("注册"); // 设置工具栏的标题文字

        findViewById(R.id.btn_save).setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isMarried = isChecked;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 获得数据库帮助器的实例
        mHelper = UserDBHelper.getInstance(this, 1);
        mHelper.openWriteLink(); // 打开数据库帮助器的写连接
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink(); // 关闭数据库连接
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            String name = et_name.getText().toString();
            String age = et_age.getText().toString();
            String phone = et_height.getText().toString();
            String password = et_weight.getText().toString();
            if (TextUtils.isEmpty(name)) {
                ToastUtil.show(this, "请先填写姓名");
                return;
            } else if (TextUtils.isEmpty(age)) {
                ToastUtil.show(this, "请先填写年龄");
                return;
            } else if (TextUtils.isEmpty(phone)) {
                ToastUtil.show(this, "请先填写手机号");
                return;
            } else if (TextUtils.isEmpty(password)) {
                ToastUtil.show(this, "请先填写密码");
                return;
            }
            // 以下声明一个用户信息对象，并填写它的各字段值
            UserInfo info = new UserInfo();
            info.name = name;
            info.age = Integer.parseInt(age);
            info.phone = phone;
            info.password =password ;
            info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
            mHelper = UserDBHelper.getInstance(this, 1);
            mHelper.openWriteLink(); // 打开数据库帮助器的写连接
            mHelper.insert(info); // 执行数据库帮助器的插入操作
            ToastUtil.show(this, "注册成功");
          // 携带意图返回上一个页面
            finish(); // 结束当前的活动页面
        }
    }

}
