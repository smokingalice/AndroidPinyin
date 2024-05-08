package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.board.GameService;
import com.example.myapplication.board.impl.GameServiceImpl;
import com.example.myapplication.utils.GameConf;
import com.example.myapplication.utils.LinkInfo;
import com.example.myapplication.utils.SizeUtils;
import com.example.myapplication.view.GameView;
import com.example.myapplication.view.Piece;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 游戏Activity <br/>
 * <br/>
 * 关于本代码介绍可以参考一下博客: <a href="http://blog.csdn.net/ouyang_peng">欧阳鹏的CSDN博客</a> <br/>
 */
public class LinkActivity extends Activity implements BaseHandlerCallBack {
    private static final String TAG = "LinkActivity";
    /**
     * 游戏配置对象
     */
    private GameConf config;
    /**
     * 游戏业务逻辑接口
     */
    private GameService gameService;
    /**
     * 游戏界面
     */

    private GameView gameView;
    /**
     * 开始按钮
     */
    private boolean music=true;
    private Button startButton;
    private Button inmage;
    private Button musicbutton;
    private Button musicbutton2;
    /**
     * 记录剩余时间的TextView
     */
    private TextView timeTextView;
    /**
     * 失败后弹出的对话框
     */
    private AlertDialog.Builder lostDialog;
    /**
     * 游戏胜利后的对话框
     */
    private AlertDialog.Builder successDialog;
    /**
     * 定时器
     */
    private Timer timer = new Timer();
    /**
     * 底部layout
     */
    private LinearLayout controlPanel;
    /**
     * 重开按钮
     */
    private Button restartButton;
    private Button restartButton2;
    /**
     * 记录游戏的剩余时间
     */
    private int gameTime;
    /**
     * 记录是否处于游戏状态
     */
    private boolean isPlaying;
    /**
     * 振动处理类
     */
    private Vibrator vibrator;
    /**
     * 记录已经选中的方块
     */
    private Piece selectedPiece = null;
    /**
     * Handler类，异步处理
     */
    private NoLeakHandler handler;
    /**
     * Handler发送消息的ID
     */
    private final int MESSAGE_ID = 0x123;
    private MediaPlayer mediaPlayer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.gameplay);
        handler = new NoLeakHandler(this);
        // 初始化界面
        init();
    }

    /**
     * 初始化游戏的方法
     */
    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        // 适配不同的屏幕，dp转为px
        int beginImageX = SizeUtils.dp2Px(this, GameConf.BEGIN_IMAGE_X);
        int beginImageY = SizeUtils.dp2Px(this, GameConf.BEGIN_IMAGE_Y);
        mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        config = new GameConf(GameConf.PIECE_X_SUM, GameConf.PIECE_X_SUM, beginImageX, beginImageY, GameConf.DEFAULT_TIME, this);
        // 得到游戏区域对象
        gameView = (GameView) findViewById(R.id.gameView);
        // 获取显示剩余时间的文本框
        timeTextView = (TextView) findViewById(R.id.timeText);
        // 获取开始按钮
        startButton = (Button) this.findViewById(R.id.startButton);
            inmage = (Button) this.findViewById(R.id.button2);
        // 获取底部行
        controlPanel = (LinearLayout) this.findViewById(R.id.controlPanel);
        // 获取重开按钮
        restartButton = (Button) this.findViewById(R.id.restartButton);
        restartButton2 = (Button) this.findViewById(R.id.restartButton2);
         musicbutton=(Button) this.findViewById(R.id.button3);
        musicbutton2=(Button) this.findViewById(R.id.button4);
        // 获取振动器
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        // 初始化游戏业务逻辑接口
        gameService = new GameServiceImpl(this.config);
        // 设置游戏逻辑的实现类
        gameView.setGameService(gameService);
        // 为开始按钮的单击事件绑定事件监听器
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                startGame(GameConf.DEFAULT_TIME,0);
                startButton.setVisibility(View.INVISIBLE);
                inmage.setVisibility(View.INVISIBLE);
                controlPanel.setVisibility(View.VISIBLE);
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(GameConf.DEFAULT_TIME,0);
            }
        });
        restartButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(GameConf.DEFAULT_TIME,1);
            }
        });
        // 为游戏区域的触碰事件绑定监听器
        musicbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(music)
                {
                    // R.raw.mmp是资源文件，MP3格式的
                    mediaPlayer.pause();
                    music=false;
                    musicbutton.setVisibility(View.INVISIBLE);
                    musicbutton2.setVisibility(View.VISIBLE);
                }else
                {
                    // R.raw.mmp是资源文件，MP3格式的
                    mediaPlayer.start();
                    music=true;
                    musicbutton.setVisibility(View.VISIBLE);
                    musicbutton2.setVisibility(View.INVISIBLE);
                }

            }

        });
        musicbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(music)
                {
                    // R.raw.mmp是资源文件，MP3格式的
                    mediaPlayer.pause();
                    music=false;
                    musicbutton.setVisibility(View.INVISIBLE);
                    musicbutton2.setVisibility(View.VISIBLE);
                }else
                {
                    // R.raw.mmp是资源文件，MP3格式的
                    mediaPlayer.start();
                    music=true;
                    musicbutton.setVisibility(View.VISIBLE);
                    musicbutton2.setVisibility(View.INVISIBLE);
                }

            }

        });
        this.gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                if (!isPlaying) {
                    return false;
                }
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    gameViewTouchDown(e);
                }
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    gameViewTouchUp(e);
                }
                return true;
            }
        });
        // 初始化游戏失败的对话框
        lostDialog = createDialog(getString(R.string.lost), getString(R.string.lost_restart), R.drawable.lost)
                .setPositiveButton(R.string.dialog_sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame(GameConf.DEFAULT_TIME,0);
                        controlPanel.setVisibility(View.VISIBLE);
                    }
                });
        // 初始化游戏胜利的对话框
        successDialog = createDialog(getString(R.string.success), getString(R.string.success_restart),
                R.drawable.success).setPositiveButton(R.string.dialog_sure,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame(GameConf.DEFAULT_TIME,0);
                    }
                });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        WindowManager wm = (WindowManager) LinkActivity.this.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Log.d(TAG, " width = " + width + "，height =" + height);

        int gameViewWidth = gameView.getWidth();
        int gameViewHeight = gameView.getHeight();
        Log.e("LinkActivity：", " gameViewWidth = " + gameViewWidth + "，gameViewHeight =" + gameViewHeight);


        // 每个 方块的 宽度 等于 公共画盘的宽度 / x方向的个数
        int tempWidth = (gameViewWidth - GameConf.BEGIN_IMAGE_X) / GameConf.PIECE_X_SUM;
        // 每个 方块的 高度 等于 公共画盘的高度 / y方向的个数
        int tempHeight = (gameViewHeight - GameConf.BEGIN_IMAGE_Y) / GameConf.PIECE_Y_SUM;
        int sideLengthOfSquare = tempWidth > tempHeight ? tempHeight : tempWidth;
        GameConf.PIECE_WIDTH = sideLengthOfSquare;
        // 每个 方块的 高度 等于 公共画盘的高度 / y方向的个数
        GameConf.PIECE_HEIGHT = sideLengthOfSquare;

        Log.d(TAG, " tempWidth =" + tempWidth + "， tempHeight =" + tempHeight);
        Log.d(TAG, " GameConf.PIECE_WIDTH =" + GameConf.PIECE_WIDTH + "， GameConf.PIECE_HEIGHT =" + GameConf.PIECE_HEIGHT);
    }

    @Override
    protected void onPause() {
        // 暂停游戏
        stopTimer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // 如果处于游戏状态中
        if (isPlaying) {
            // 以剩余时间重新开始游戏
            startGame(gameTime,1);
        }
        super.onResume();
    }

    /**
     * 触碰游戏区域的处理方法
     *
     * @param event
     */
    private void gameViewTouchDown(MotionEvent event) {
        // 获取GameServiceImpl中的Piece[][]数组
        Piece[][] pieces = gameService.getPieces();
        // 获取用户点击的x座标
        float touchX = event.getX();
        // 获取用户点击的y座标
        float touchY = event.getY();
        // 根据用户触碰的座标得到对应的Piece对象
        Piece currentPiece = gameService.findPiece(touchX, touchY);
        // 如果没有选中任何Piece对象(即鼠标点击的地方没有图片), 不再往下执行
        if (currentPiece == null) {
            return;
        }
        // 将gameView中的选中方块设为当前方块
        this.gameView.setSelectedPiece(currentPiece);
        // 表示之前没有选中任何一个Piece
        if (this.selectedPiece == null) {
            // 将当前方块设为已选中的方块, 重新将GamePanel绘制, 并不再往下执行
            this.selectedPiece = currentPiece;
            this.gameView.postInvalidate();
            return;
        }
        // 表示之前已经选择了一个
        if (this.selectedPiece != null) {
            // 在这里就要对currentPiece和prePiece进行判断并进行连接
            LinkInfo linkInfo = this.gameService.link(this.selectedPiece, currentPiece);
            // 两个Piece不可连, linkInfo为null
            if (linkInfo == null) {
                // 如果连接不成功, 将当前方块设为选中方块
                this.selectedPiece = currentPiece;
                this.gameView.postInvalidate();
            } else {
                // 处理成功连接
                handleSuccessLink(linkInfo, this.selectedPiece, currentPiece, pieces);
            }
        }
    }

    /**
     * 触碰游戏区域的处理方法
     *
     * @param e
     */
    private void gameViewTouchUp(MotionEvent e) {
        this.gameView.postInvalidate();
    }

    /**
     * 以gameTime作为剩余时间开始或恢复游戏
     *
     * @param gameTime 剩余时间
     */
    private void startGame(int gameTime,int n) {
        // 如果之前的timer还未取消，取消timer
        if (this.timer != null) {
            stopTimer();
        }
        // 重新设置游戏时间
        this.gameTime = gameTime;
        // 如果游戏剩余时间与总游戏时间相等，即为重新开始新游戏
        if (gameTime == GameConf.DEFAULT_TIME) {
            // 开始新的游戏游戏
            gameView.startGame(n);
        }
        isPlaying = true;
        this.timer = new Timer();
        // 启动计时器 ， 每隔1秒发送一次消息
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(MESSAGE_ID);
            }
        }, 0, 1000);
        // 将选中方块设为null。
        this.selectedPiece = null;
    }

    /**
     * 成功连接后处理
     *
     * @param linkInfo     连接信息
     * @param prePiece     前一个选中方块
     * @param currentPiece 当前选择方块
     * @param pieces       系统中还剩的全部方块
     */
    private void handleSuccessLink(LinkInfo linkInfo, Piece prePiece,
                                   Piece currentPiece, Piece[][] pieces) {
        // 它们可以相连, 让GamePanel处理LinkInfo
        this.gameView.setLinkInfo(linkInfo);
        // 将gameView中的选中方块设为null
        this.gameView.setSelectedPiece(null);
        this.gameView.postInvalidate();
        // 将两个Piece对象从数组中删除
        pieces[prePiece.getIndexX()][prePiece.getIndexY()] = null;
        pieces[currentPiece.getIndexX()][currentPiece.getIndexY()] = null;
        // 将选中的方块设置null。
        this.selectedPiece = null;
        // 手机振动(100毫秒)
        this.vibrator.vibrate(100);
        // 判断是否还有剩下的方块, 如果没有, 游戏胜利
        if (!this.gameService.hasPieces()) {
            // 游戏胜利
            this.successDialog.show();
            // 停止定时器
            stopTimer();
            // 更改游戏状态
            isPlaying = false;
        }
    }

    /**
     * 创建对话框的工具方法
     *
     * @param title         标题
     * @param message       内容
     * @param imageResource 图片
     * @return
     */
    private AlertDialog.Builder createDialog(String title, String message,
                                             int imageResource) {
        return new AlertDialog.Builder(this).setTitle(title)
                .setMessage(message).setIcon(imageResource).setCancelable(false);
    }

    /**
     * 停止计时
     */
    private void stopTimer() {
        // 停止定时器
        if(this.timer != null){
            this.timer.cancel();
            this.timer = null;
        }
    }

    @Override
    public void callBack(Message msg) {
        switch (msg.what) {
            case MESSAGE_ID:
                timeTextView.setText(String.format(getString(R.string.remaining_time), gameTime));
                gameTime--; // 游戏剩余时间减少
                // 时间小于0, 游戏失败
                if (gameTime < 0) {
                    // 停止计时
                    stopTimer();
                    // 更改游戏的状态
                    isPlaying = false;
                    // 失败后弹出对话框
                    lostDialog.show();
                    controlPanel.setVisibility(View.INVISIBLE);
                    return;
                }
                break;
            default:
                break;
        }
    }

    private static class NoLeakHandler<T extends BaseHandlerCallBack> extends Handler {
        private WeakReference<T> wr;

        private NoLeakHandler(T t) {
            wr = new WeakReference<>(t);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            T t = wr.get();
            if (t != null) {
                t.callBack(msg);
            }
        }
    }


    protected void onStop(){
        mediaPlayer.stop();
        super.onStop();


    }
}