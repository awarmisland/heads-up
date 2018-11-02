package com.awarmisland.android.heads_up;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.awarmisland.android.heads_up.Headsup.HeadupManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onShow(null);
    }

    public void onShow(@NonNull View v){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HeadupManager.getInstance(MainActivity.this)
                        .setTitle("标题")
                        .setContent("测试内容")
                        .setLargerIcon(R.mipmap.ic_launcher)
                        .showHeadup();
            }
        },1000);
        try{

        }catch (Exception e){
            Log.d("122",e.getMessage());
//            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//            intent.setData(Uri.parse("package:" + getPackageName()));
//            startActivityForResult(intent, 100);
        }
    }
}
