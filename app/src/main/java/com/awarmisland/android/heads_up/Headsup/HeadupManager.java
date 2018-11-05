package com.awarmisland.android.heads_up.Headsup;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 横幅
 */
public class HeadupManager {
    private static HeadupManager headupManager;
    private Context mContext;
    private HeadupWindowManager windowManager;
    private HeadupView headupView;

    private HeadupManager(){}

    private HeadupManager(Context context){
        this.mContext = context;
        headupView = new DefaultHeapupView(context);
        windowManager = new HeadupWindowManager(context,headupView);
    }

    public static HeadupManager getInstance(Context context){
        if(headupManager ==null){
            headupManager = new HeadupManager(context);
        }
        return headupManager;
    }

    public HeadupManager setTitle(String title){
        if(headupView!=null)
            headupView.setTitle(title);
        return headupManager;
    }

    public HeadupManager setContent(String content){
        if(headupView!=null)
            headupView.setContent(content);
        return headupManager;
    }

    public HeadupManager setLargerIcon(int resId){
        if(headupView!=null)
            headupView.setLargerIcon(resId);
        return headupManager;
    }

    public HeadupManager setCustomerView(HeadupView view){
        this.headupView = view;
        windowManager.setHeadupView(view);
        return  headupManager;
    }

    public void showHeadup(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            if(!checkFloatPermission(mContext))
                windowManager.addViewToWindowManager();
        }
    }

    public static boolean checkFloatPermission(Context context) {
        Boolean result = true;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class clazz = Settings.class;
                Method canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
                result = (Boolean) canDrawOverlays.invoke(null, context);
            } catch (Exception e) {
            }
        }
        return result;
    }



}
