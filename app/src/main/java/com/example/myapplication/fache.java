package com.example.myapplication;
import static android.text.TextUtils.substring;
import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static android.widget.ImageView.ScaleType.FIT_CENTER;

import static com.baidu.aip.asrwakeup3.core.inputstream.InFileStream.context;

import android.app.ActionBar;
import android.media.AudioManager;
import net.sourceforge.pinyin4j.PinyinHelper;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.example.myapplication.adapter.ASRresponse;
import com.google.gson.Gson;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import kotlin.text.UStringsKt;

public class fache extends AppCompatActivity  implements EventListener{

    protected TextView txtResult;//识别结果
    protected Button startBtn;//开始识别  一直不说话会自动停止，需要再次打开
    protected Button stopBtn;//停止识
    protected Button resetBtn;//重置
    private EventManager asr;//语音识别核心库
    private static final String[] VIDEO_PERMISSIONS = {Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int VIDEO_PERMISSIONS_CODE = 1;
    private int randomnumber;
    private int key=0;
    private int[] randomnumbertext= new int[]{0,0,0,0,0,0};
    private int usable=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fache);
        ImageView view_a=findViewById(R.id.yunmu_a);
        ImageView view_b=findViewById(R.id.yunmu_e);
        ImageView view_c=findViewById(R.id.yunmu_i);
        ImageView view_d=findViewById(R.id.yunmu_o);
        ImageView view_e=findViewById(R.id.yunmu_u);
        ImageView view_f=findViewById(R.id.yunmu_v);
        view_a.setImageAlpha(40);
        view_b.setImageAlpha(40);
        view_c.setImageAlpha(40);
        view_d.setImageAlpha(40);
        view_e.setImageAlpha(40);
        view_f.setImageAlpha(40);
        Random random=new Random();
        randomnumber=random.nextInt(6);
        key=key+1;
        randomnumbertext[randomnumber]=1;
        requestPermission();
        ImageView view_right1=findViewById(R.id.right1);
        ImageView view_right2=findViewById(R.id.right2);
        ImageView view_right3=findViewById(R.id.right3);
        ImageView view_right4=findViewById(R.id.right4);
        ImageView view_right5=findViewById(R.id.right5);
        ImageView view_right6=findViewById(R.id.right6);
        switch (randomnumber){
            case 0:
                view_right1.setVisibility(View.VISIBLE);
                view_a.setImageAlpha(255);
                break;
            case 1:
                view_right2.setVisibility(View.VISIBLE);
                view_b.setImageAlpha(255);
                break;
            case 2:
                view_right3.setVisibility(View.VISIBLE);
                view_c.setImageAlpha(255);
                break;
            case 3:
                view_right4.setVisibility(View.VISIBLE);
                view_d.setImageAlpha(255);
                break;
            case 4:
                view_right5.setVisibility(View.VISIBLE);
                view_e.setImageAlpha(255);
                break;
            case 5:
                view_right6.setVisibility(View.VISIBLE);
                view_f.setImageAlpha(255);
                break;

        }
        initView();

        //初始化EventManager对象
        asr = EventManagerFactory.create(this, "asr");
        //注册自己的输出事件类
        asr.registerListener((EventListener) this); //  EventListener 中 onEvent方法

    }
    /**
     * 初始化控件
     */
    private void initView() {
        txtResult = (TextView) findViewById(R.id.tb_view);
        startBtn = (Button) findViewById(R.id.btn_start);
        stopBtn = (Button) findViewById(R.id.btn_stop);
        resetBtn = (Button) findViewById(R.id.btn_reset);
        ImageView view_pause=findViewById(R.id.pause);
        MediaPlayer mediaplayer3= MediaPlayer.create(this,R.raw.qiehuan);
       startBtn.setOnClickListener(new View.OnClickListener() {//开始

            @Override
            public void onClick(View v) {
                asr.send(SpeechConstant.ASR_START, "{}", null, 0, 0);
                view_pause.setVisibility(View.VISIBLE);
                usable=0;
                view_pause.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view_pause.setVisibility(View.INVISIBLE);
                    }
                },4000);

            }

        });
      resetBtn.setOnClickListener(new View.OnClickListener() {//重置
                @Override
                public void onClick(View v) {

                    ImageView view_a=findViewById(R.id.yunmu_a);
                    ImageView view_b=findViewById(R.id.yunmu_e);
                    ImageView view_c=findViewById(R.id.yunmu_i);
                    ImageView view_d=findViewById(R.id.yunmu_o);
                    ImageView view_e=findViewById(R.id.yunmu_u);
                    ImageView view_f=findViewById(R.id.yunmu_v);
                    ImageView view_right1=findViewById(R.id.right1);
                    ImageView view_right2=findViewById(R.id.right2);
                    ImageView view_right3=findViewById(R.id.right3);
                    ImageView view_right4=findViewById(R.id.right4);
                    ImageView view_right5=findViewById(R.id.right5);
                    ImageView view_right6=findViewById(R.id.right6);
                    switch (randomnumber){
                        case 0:
                            view_right1.setVisibility(View.INVISIBLE);
                            view_a.setImageAlpha(40);
                            break;
                        case 1:
                            view_right2.setVisibility(View.INVISIBLE);
                            view_b.setImageAlpha(40);
                            break;
                        case 2:
                            view_right3.setVisibility(View.INVISIBLE);
                            view_c.setImageAlpha(40);
                            break;
                        case 3:
                            view_right4.setVisibility(View.INVISIBLE);
                            view_d.setImageAlpha(40);
                            break;
                        case 4:
                            view_right5.setVisibility(View.INVISIBLE);
                            view_e.setImageAlpha(40);
                            break;
                        case 5:
                            view_right6.setVisibility(View.INVISIBLE);
                            view_f.setImageAlpha(40);
                            break;
                    }
                    Random random=new Random();
                    if(key>=6){
                        for(int i=0;i<6;i++)
                        {
                            randomnumbertext[i]=0;
                        }
                        key=0;
                    }
                    mediaplayer3.start();
                    randomnumber=random.nextInt(6);
                    while(randomnumbertext[randomnumber]==1&&key<6){
                        randomnumber=random.nextInt(6);

                    }
                    randomnumbertext[randomnumber]=1;
                    key++;
                    switch (randomnumber){
                        case 0:
                            view_right1.setVisibility(View.VISIBLE);
                            view_a.setImageAlpha(255);
                            break;
                        case 1:
                            view_right2.setVisibility(View.VISIBLE);
                            view_b.setImageAlpha(255);
                            break;
                        case 2:
                            view_right3.setVisibility(View.VISIBLE);
                            view_c.setImageAlpha(255);
                            break;
                        case 3:
                            view_right4.setVisibility(View.VISIBLE);
                            view_d.setImageAlpha(255);
                            break;
                        case 4:
                            view_right5.setVisibility(View.VISIBLE);
                            view_e.setImageAlpha(255);
                            break;
                        case 5:
                            view_right6.setVisibility(View.VISIBLE);
                            view_f.setImageAlpha(255);
                            break;
                    }
                }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {//停止
            @Override
            public void onClick(View v) {
                asr.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0);
                view_pause.setVisibility(View.INVISIBLE);
            }
        });

    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void initPermission() {
        String[] permissions = {android.Manifest.permission.READ_MEDIA_AUDIO,
                android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
                Toast.makeText(this,"没有权限",Toast.LENGTH_SHORT).show();
            }
        }
        String[] tmpList = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }



    //申请权限
    private void requestPermission() {
        // 当API大于 23 时，才动态申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(fache.this,VIDEO_PERMISSIONS,VIDEO_PERMISSIONS_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case VIDEO_PERMISSIONS_CODE:
                //权限请求失败

                if (grantResults.length == VIDEO_PERMISSIONS.length&& MainActivity.a) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            //弹出对话框引导用户去设置
                            showDialog();

                            Toast.makeText(fache.this, "请求权限被拒绝", Toast.LENGTH_LONG).show();

                            break;
                        }
                    }
                   MainActivity.a=false;
                }else{
                        Toast.makeText(fache.this, "已授权", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    //弹出提示框
    private void showDialog(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("需要录音和读写权限，是否去设置？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        goToAppSetting();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        ImageView view_a=findViewById(R.id.right_a);
        ImageView view_b=findViewById(R.id.right_b);
        ImageView view_c=findViewById(R.id.right_c);
        ImageView view_d=findViewById(R.id.right_d);
        ImageView view_e=findViewById(R.id.right_e);
        ImageView view_f=findViewById(R.id.right_f);
        ImageView view_fales=findViewById(R.id.false1);
        ImageView view_pause=findViewById(R.id.pause);
        MediaPlayer mediaplayer1= MediaPlayer.create(this,R.raw.mright);
        MediaPlayer mediaplayer2= MediaPlayer.create(this,R.raw.mfaluse);


        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            // 识别相关的结果都在这里

            if (params == null || params.isEmpty()) {

                return;
            }
            if (params.contains("\"final_result\"")) {
                // 一句话的最终识别结果
                Log.i("ars.event",params);
                Gson gson = new Gson();
                ASRresponse asRresponse = gson.fromJson(params, ASRresponse.class);//数据解析转实体bean

                if(asRresponse == null) return;
                //从日志中，得出Best_result的值才是需要的，但是后面跟了一个中文输入法下的逗号，

                String  A= tach(asRresponse.getBest_result());

                String result = null;
                if(null !=A && !A.isEmpty()&& !A.matches(".*[a-zA-Z]+.*")&& !A.matches(".*[0-9]+.*")) {
                    char[] charArray = A.toCharArray();
                    StringBuilder sb = new StringBuilder();
                    for (char ch : charArray) {
                        // 逐个汉字转成拼音
                        String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(ch);
                        if(null != stringArray) {
                            sb.append(stringArray[0].replaceAll("\\d", ""));;
                        }
                    }
                    if(sb.length() > 0) {
                        result = sb.toString();
                    }
                }else if(A.matches(".*[a-zA-Z]+.*")){
                    result=A;
                }else{
                    result="x";
                }
                assert result != null;


                if (result.charAt(0) == 'a'&&!result.startsWith("an")&&randomnumber==0) { // 百分比为空，则正常显示
                    view_a.setVisibility(View.VISIBLE);
                    view_pause.setVisibility(View.INVISIBLE);
                    mediaplayer1.start();
                    view_a.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view_a.setVisibility(View.INVISIBLE);
                        }
                    }, 1500);
                }

               else if (result.charAt(0) == 'e'&&!result.startsWith("en")&&randomnumber==1) { // 百分比为空，则正常显示
                    view_b.setVisibility(View.VISIBLE);
                    view_pause.setVisibility(View.INVISIBLE);
                    mediaplayer1.start();
                    view_b.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view_b.setVisibility(View.INVISIBLE);
                        }
                    }, 1500);
                }
               else if (result.startsWith("yi")&&!result.startsWith("yin")&&randomnumber==2) { // 百分比为空，则正常显示
                    view_c.setVisibility(View.VISIBLE);
                    view_pause.setVisibility(View.INVISIBLE);
                    mediaplayer1.start();
                    view_c.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view_c.setVisibility(View.INVISIBLE);
                        }
                    }, 1500);
                }

               else if ((result.startsWith("wo")||result.startsWith("ee")||result.startsWith("O"))&&!result.startsWith("won")&&randomnumber==3) { // 百分比为空，则正常显示
                    view_d.setVisibility(View.VISIBLE);
                    view_pause.setVisibility(View.INVISIBLE);
                    mediaplayer1.start();
                    view_d.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view_d.setVisibility(View.INVISIBLE);
                        }
                    }, 1500);
                }
                else if (result.startsWith("wu")&&!result.startsWith("wun")&&randomnumber==4) { // 百分比为空，则正常显示
                    view_e.setVisibility(View.VISIBLE);
                    view_pause.setVisibility(View.INVISIBLE);
                    mediaplayer1.start();
                    view_e.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view_e.setVisibility(View.INVISIBLE);
                        }
                    }, 1500);
                }
               else if((result.startsWith("yu")||result.startsWith("ng"))&&!result.startsWith("yun")&&randomnumber==5) { // 百分比为空，则正常显示
                    view_f.setVisibility(View.VISIBLE);
                    view_pause.setVisibility(View.INVISIBLE);
                    mediaplayer1.start();
                    view_f.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view_f.setVisibility(View.INVISIBLE);
                        }
                    }, 1500);
                }
               else {
                    view_fales.setVisibility(View.VISIBLE);
                    view_pause.setVisibility(View.INVISIBLE);

                    mediaplayer2.start();
                    view_fales.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view_fales.setVisibility(View.INVISIBLE);
                        }
                    },1500);

                }
                txtResult.setText(result);
            }
        }

    }

String tach(String a){
        String A = "";
    if(a.contains("，")){
       A=a.replace('，',' ').trim();
    }else if(a.contains("。")){
       A=a.replace('。',' ').trim();

    }else if(a.contains("？")) {
        A = a.replace('？',' ').trim();

    }
    else if(a.contains("！")){
        A=a.replace('，',' ').trim();

    }



    return A;
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 基于SDK集成4.2 发送取消事件
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        // 基于SDK集成5.2 退出事件管理器
        // 必须与registerListener成对出现，否则可能造成内存泄露
        asr.unregisterListener((EventListener) this);
    }

}