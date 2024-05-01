package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.bean.UserInfo;
import com.example.myapplication.database.UserDBHelper;
import com.example.myapplication.util.ToastUtil;
import com.example.myapplication.util.ViewUtil;

import java.util.List;
import java.util.Objects;

@SuppressLint("DefaultLocale")
public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnFocusChangeListener {
    public static boolean a;
    private RadioGroup rg_login; // 声明一个单选组对象

    private EditText et_phone; // 声明一个编辑框对象
    private TextView tv_password; // 声明一个文本视图对象
    private EditText et_password; // 声明一个编辑框对象
    private Button btn_forget; // 声明一个按钮控件对象
    private CheckBox ck_remember; // 声明一个复选框对象

    private int mRequestCode = 0; // 跳转页面时的请求代码
    private boolean isRemember = false; // 是否记住密码
    private String mPassword = "111155"; // 默认密码
    private String mVerifyCode; // 验证码
    private UserDBHelper mHelper; // 声明一个用户数据库的帮助器对象

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_phone = findViewById(R.id.et_phone);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember = findViewById(R.id.ck_remember);
        // 给rg_login设置单选监听器
        ck_remember = findViewById(R.id.ck_remember);
        // 给rg_login设置单选监听器
        Toolbar tl_head = findViewById(R.id.tl_head);
        tl_head.setTitle("南邮教你学拼音"); // 设置工具栏的标题文字
        // 给ck_remember设置勾选监听器
        ck_remember.setOnCheckedChangeListener((buttonView, isChecked) -> isRemember = isChecked);

        // 给ck_remember设置勾选监听器

        // 给et_phone添加文本变更监听器
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
        // 给et_password添加文本变更监听器
        et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));
        btn_forget.setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.regiester).setOnClickListener(this);
        findViewById(R.id.btn_forget).setOnClickListener(this);
        // 给密码编辑框注册一个焦点变化监听器，一旦焦点发生变化，就触发监听器的onFocusChange方法
        et_password.setOnFocusChangeListener(this);
    }

    // 定义登录方式的单选监听器


    // 定义一个编辑框监听器，在输入文本达到指定长度时自动隐藏输入法
    private class HideTextWatcher implements TextWatcher {
        private EditText mView; // 声明一个编辑框对象
        private int mMaxLength; // 声明一个最大长度变量

        public HideTextWatcher(EditText v, int maxLength) {
            super();
            mView = v;
            mMaxLength = maxLength;
        }

        // 在编辑框的输入文本变化前触发
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        // 在编辑框的输入文本变化时触发
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        // 在编辑框的输入文本变化后触发
        public void afterTextChanged(Editable s) {
            String str = s.toString(); // 获得已输入的文本字符串
            // 输入文本达到11位（如手机号码），或者达到6位（如登录密码）时关闭输入法
            if ((str.length() == 11 && mMaxLength == 11)
                    || (str.length() == 6 && mMaxLength == 6)) {
                ViewUtil.hideOneInputMethod(MainActivity.this, mView); // 隐藏输入法软键盘
            }
        }
    }

    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        if (v.getId() == R.id.btn_forget) { // 点击了“忘记密码”按钮
            if (phone.length() < 11) { // 手机号码不足11位
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this,   LoginForgetActivity.class);
            intent.putExtra("phone", phone);
            startActivityForResult(intent, mRequestCode);


        } else if (v.getId() == R.id.btn_login) { // 点击了“登录”按钮
            if (phone.length() < 11) { // 手机号码不足11位
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            } mHelper.openReadLink();
            if (mHelper == null) {
                ToastUtil.show(this, "数据库连接为空");
                return;
            }
            List<UserInfo> userList = mHelper.query("1=1");
            int i;
            for ( i = 0; i < userList.size(); i++) {
                UserInfo info = userList.get(i);
             if(Objects.equals(info.phone, phone))
             {

                 if (!et_password.getText().toString().equals(info.password)) {
                     Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                 } else { // 密码校验通过
                     loginSuccess(); // 提示用户登录成功
                 }
                  break;
             }

            }
            if(i==userList.size()){
                Toast.makeText(this, "该账号不存在", Toast.LENGTH_SHORT).show();
            }

            // 密码方式校验


        } else if (v.getId() == R.id.regiester) {
            Intent intent = new Intent(this,   SQLiteWriteActivity.class);
            startActivity(intent);

        }
    }

    // 从下一个页面携带参数返回当前页面时触发
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mRequestCode && data != null) {
            // 用户密码已改为新密码，故更新密码变量
            mPassword = data.getStringExtra("new_password");
        }
    }

    // 从修改密码页面返回登录页面，要清空密码的输入框
    @Override
    protected void onRestart() {
        super.onRestart();
        et_password.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHelper = UserDBHelper.getInstance(this, 1); // 获得用户数据库帮助器的实例

        mHelper.openWriteLink(); // 恢复页面，则打开数据库连接、

    }

    @Override
    protected void onPause() {
        super.onPause();
        mHelper.closeLink(); // 暂停页面，则关闭数据库连接

    }

    // 校验通过，登录成功
    private void loginSuccess() {
        String desc = String.format("您的手机号码是%s，恭喜你通过登录验证，点击“确定”按钮返回上个页面",
                et_phone.getText().toString());
        String phone = et_phone.getText().toString();
        // 以下弹出提醒对话框，提示用户登录成功
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Intent intent = new Intent(this,   coreActivity.class);
        intent.putExtra("phone", phone);
        startActivityForResult(intent, mRequestCode);


        // 如果勾选了“记住密码”，则把手机号码和密码保存为数据库的用户表记录
        if (isRemember) {
            mHelper = UserDBHelper.getInstance(this, 1);
            mHelper.openReadLink(); // 打开数据库帮助器的读连接

            mPassword = et_password.getText().toString();
            List<UserInfo> userList = mHelper.query("1=1");
            int i;
            for ( i = 0; i < userList.size(); i++) {
                UserInfo info = userList.get(i);
                if(Objects.equals(info.phone,phone))
                {
                    info.remnber=mPassword;
                }
                mHelper.update(info);

            }
        }
    }

    // 焦点变更事件的处理方法，hasFocus表示当前控件是否获得焦点。
    // 为什么光标进入密码框事件不选onClick？因为要点两下才会触发onClick动作（第一下是切换焦点动作）
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String phone = et_phone.getText().toString();
        // 判断是否是密码编辑框发生焦点变化
        if (v.getId() == R.id.et_password) {
            // 用户已输入手机号码，且密码框获得焦点
            if (phone.length() > 0 && hasFocus) {
                // 根据手机号码到数据库中查询用户记录
                mHelper = UserDBHelper.getInstance(this, 1);
                mHelper.openReadLink(); // 打开数据库帮助器的读连接
                UserInfo info = mHelper.queryByPhone(phone);
                if (info != null) {
                    // 找到用户记录，则自动在密码框中填写该用户的密码
                    et_password.setText(info.remnber);
                }
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink(); // 关闭数据库连接
    }

}