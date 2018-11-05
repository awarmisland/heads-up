package com.awarmisland.android.heads_up.Headsup;

import android.content.Context;
import android.os.Build;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class CompatibleHandle {
    private Context mContext;

    public CompatibleHandle(Context context){
        this.mContext = context;
    }

    public void preAddWindow(WindowManager.LayoutParams layoutParams){
        if(Build.MANUFACTURER.toLowerCase().equals("meizu")) {
            setMeizuParams(layoutParams);
        }

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
}




