package com.awarmisland.android.heads_up.Headsup;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.awarmisland.android.heads_up.R;

public class DefaultHeapupView extends HeadupView {
    private Context mContext;

    public DefaultHeapupView (Context context){
        super(context,R.layout.view_headup_default);
        this.mContext = context;
    }
    @Override
    public void setTitle(String title) {
       TextView notification_icon =  findViewById(R.id.notification_title);
       notification_icon.setText(title);
    }

    @Override
    public void setContent(String content) {
        TextView notification_icon =  findViewById(R.id.notification_content);
        notification_icon.setText(content);
    }

    @Override
    public void setLargerIcon(String path) {

    }

    @Override
    public void setLargerIcon(int res) {
        ImageView notification_icon =  findViewById(R.id.notification_icon);
        notification_icon.setImageResource(res);
    }
}
