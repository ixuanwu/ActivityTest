package com.merckor.activitytest;

import android.content.Context;
import android.graphics.PixelFormat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jhc on 2017/3/02.
 */
public class LogCatFloatView implements View.OnClickListener{

    private static final String TAG = "LogCatFloatView";
    private static Context mContext;
    public  static LogCatFloatView mLogCatFloatView = null;
    private static WindowManager mWindowManager;
    private static WindowManager.LayoutParams wmParams;
    private static View mView;
    private static boolean isShow = false;//悬浮框是否已经显示
    private static Button closeBtn,clearBtn;
    private static TextView logContentText;

    public static LogCatFloatView getInstance(){
        if(mLogCatFloatView==null){
            mLogCatFloatView = new LogCatFloatView();
        }
        return mLogCatFloatView;
    }

    /**
     * 显示悬浮框
     */
    public void showFloatView(Context context,int layoutId){
        mContext = context;
        if(isShow){
            return;
        }
        //初始化WindowManager配置
        initWindowManage(layoutId);
        //为当前view设置事件实现可以拖动
        initViewTouchMove();
        //初始化子视图
        initView();
        isShow = true;
    }

    /**
     * 隐藏悬浮窗
     */
    public void hideFloatView(){
        if(mWindowManager != null && isShow){
            mWindowManager.removeView(mView);
            isShow = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_clear:
                logContentText.setText("");
                break;
            case R.id.log_close:
                hideFloatView();
                break;
        }
    }

    /**
     * 初始化window manager相关
     * @param layoutId
     */
    private  void initWindowManage(int layoutId){
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.CENTER;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.x = mContext.getResources().getDisplayMetrics().widthPixels;
        wmParams.y = 0;

        wmParams.width  = 400;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mView = LayoutInflater.from(mContext).inflate(layoutId, null);
        mWindowManager.addView(mView, wmParams);
    }

    /**
     * 初始化相关子视图
     */
    private void initView(){
        closeBtn = (Button)mView.findViewById(R.id.log_close);
        clearBtn = (Button)mView.findViewById(R.id.log_clear);
        closeBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        logContentText = (TextView)mView.findViewById(R.id.log_content);
    }

    /**
     * 初始化拖拽事件
     */
    private void initViewTouchMove(){
        mView.setOnTouchListener(new View.OnTouchListener() {
            float downX = 0;
            float downY = 0;
            int oddOffsetX = 0;
            int oddOffsetY = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        downX =  event.getX();
                        downY =  event.getY();
                        oddOffsetX = wmParams.x;
                        oddOffsetY = wmParams.y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float moveX = event.getX();
                        float moveY =  event.getY();
                        //不除以3，拖动的view抖动的有点厉害
                        wmParams.x += (moveX - downX)/3;
                        wmParams.y += (moveY - downY)/3;
                        if(mView != null){
                            mWindowManager.updateViewLayout(mView,wmParams);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG, "LogCatFloatView onTouch 此处触摸后脱离屏幕暂未处理");
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 在面板上面显示log
     * @param log
     */
    public static void showLogCat(CharSequence log){
        if(isShow){
            String oldLog = logContentText.getText().toString();
            log = oldLog+log;
            logContentText.setText(log);
        }
    }
}
