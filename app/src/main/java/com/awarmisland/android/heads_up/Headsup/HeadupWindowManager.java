package com.awarmisland.android.heads_up.Headsup;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.WINDOW_SERVICE;

public class HeadupWindowManager {

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private Context mContext;
    private boolean isViewAdded;
    private HeadupView headupView;
    private TimerTask timerTask;
    private Timer timer;

    public HeadupWindowManager(Context context,HeadupView headupView){
        this.mContext = context;
        this.isViewAdded=false;
        setHeadupView(headupView);
        windowManager = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PRIORITY_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        //WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        );
        layoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
//        if (Build.VERSION.SDK_INT >= 26) {
        layoutParams.type =WindowManager.LayoutParams.TYPE_TOAST;
//        }
    }

    public void addViewToWindowManager() {
        if(isViewAdded){
           removeViewToWindowManager();
        }
        if (!isViewAdded) {
            if(Build.MANUFACTURER.toLowerCase().equals("meizu")) {
                setMeizuParams(layoutParams);
            }
            windowManager.addView(headupView, layoutParams);
            headupView.requestFocus();
            isViewAdded = true;
            //执行动画
            headupView.animaIn();
        }
    }
    public void removeViewToWindowManager(){
        try {
            windowManager.removeViewImmediate(headupView);
        }catch (Exception E){
        }
        isViewAdded=false;
        resetTimeSchedule();
    }

    private HeadupView.HeadupViewListener headupViewListener = new HeadupView.HeadupViewListener() {
        @Override
        public void touchDown() {
            resetTimeSchedule();
        }
        @Override
        public void touchUp() {
            closeHeadup();
        }
        @Override
        public void animaInEnd() {
            closeHeadup();
        }
        @Override
        public void animaOutEnd() {
            removeViewToWindowManager();
        }

        @Override
        public void onDimiss() {
            removeViewToWindowManager();
        }
    };

    private void closeHeadup(){
        try{
            if(timer==null){
                timer = new Timer();
            }
            if(timerTask == null){
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        headupView.animOut();
                    }
                };
            }
            if(timer!=null&&timerTask!=null) {
                timer.schedule(timerTask, 5000);
            }
        }catch (Exception e){
           resetTimeSchedule();
        }
    }
    private void resetTimeSchedule(){
        if(timerTask!=null){
            timerTask.cancel();
        }
        if(timer!=null){
            timer.cancel();
        }
        timerTask =null;
        timer=null;
    }
    private static void setMeizuParams(WindowManager.LayoutParams params) {
        try {
            Class MeizuParamsClass = Class.forName("android.view.MeizuLayoutParams");
            Field flagField = MeizuParamsClass.getDeclaredField("flags");
            flagField.setAccessible(true);
            Object MeizuParams = MeizuParamsClass.newInstance();
            flagField.setInt(MeizuParams, 0x40);

            Field mzParamsField = params.getClass().getField("meizuParams");
            mzParamsField.set(params, MeizuParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHeadupView(HeadupView headupView) {
        this.headupView = headupView;
        this.headupView.setHeadupViewListener(headupViewListener);
    }
}
