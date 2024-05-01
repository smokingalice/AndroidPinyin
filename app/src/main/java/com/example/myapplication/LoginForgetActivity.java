package com.example.myapplication;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.bean.UserInfo;
import com.example.myapplication.database.UserDBHelper;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@SuppressLint("DefaultLocale")
public class LoginForgetActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_password_first; // 声明一个编辑框对象
    private EditText et_password_second; // 声明一个编辑框对象
    private EditText et_verifycode; // 声明一个编辑框对象
    private String mVerifyCode; // 验证码
    private String mPhone; // 手机号码
    private UserDBHelper mHelper; // 声明一个用户数据库的帮助器对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);
        // 从布局文件中获取名叫et_password_first的编辑框
        et_password_first = findViewById(R.id.et_password_first);
        // 从布局文件中获取名叫et_password_second的编辑框
        et_password_second = findViewById(R.id.et_password_second);
        // 从布局文件中获取名叫et_verifycode的编辑框
        Toolbar tl_head = findViewById(R.id.tl_head);
        tl_head.setTitle("找回密码"); // 设置工具栏的标题文字
        mHelper = UserDBHelper.getInstance(this, 1);
        mHelper.openReadLink(); // 打开数据库帮助器的读连接
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        // 从上一个页面获取要修改密码的手机号码
        mPhone = getIntent().getStringExtra("phone");
    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.btn_confirm) {
            if (mPhone == null || mPhone.length() < 11) {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }// 点击了“确定”按钮
            String password_first = et_password_first.getText().toString();
            String password_second = et_password_second.getText().toString();
            if (password_first.length() < 6 || password_second.length() < 6) {
                Toast.makeText(this, "请输入正确的新密码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password_first.equals(password_second)) {
                Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }

                Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                // 以下把修改好的新密码返回给上一个页面
            List<UserInfo> userList = mHelper.query("1=1");
            int i;
            for ( i = 0; i < userList.size(); i++) {
                UserInfo info = userList.get(i);
                if(Objects.equals(info.phone, mPhone))
                {
                   info.password=password_first;
                }
                mHelper.update(info);

            }

                Intent intent = new Intent(); // 创建一个新意图
                intent.putExtra("new_password", password_first); // 存入新密码
                setResult(Activity.RESULT_OK, intent); // 携带意图返回上一个页面


                finish(); // 结束当前的活动页面

        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink(); // 关闭数据库连接
    }
}

