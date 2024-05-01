package com.example.myapplication.bean;

//用户信息
public class UserInfo {
    public long rowid; // 行号
    public int xuhao; // 序号
    public String name; // 姓名
    public int age; // 年龄
    public long study; //study次数
    public float game; // game次数
    public boolean married;
    public String update_time; // 更新时间
    public String phone; // 手机号
    public String password; // 密码
    public String remnber; // 密码
    public String remnber2;
    public String remnber3;
    public String remnber4;

    public UserInfo() {
        rowid = 0L;
        xuhao = 0;
        name = "";
        age = 0;
        study= 0;
        game = 0.0f;
        married = false;
        update_time = "";
        phone = "";
        password = "";
        remnber="";
        remnber2="";
        remnber3="";
        remnber4="";

    }
}
