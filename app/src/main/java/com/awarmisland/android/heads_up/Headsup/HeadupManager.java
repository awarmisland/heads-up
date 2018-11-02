package com.awarmisland.android.heads_up.Headsup;

import android.content.Context;

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
        windowManager.addViewToWindowManager();
    }

}
