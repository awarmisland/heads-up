package com.awarmisland.android.heads_up.Headsup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public abstract class HeadupView extends FrameLayout {
    private Context mContext;
    private HeadupViewListener headupViewListener;
    private View resView;
    public HeadupView(Context context) {
        super(context);
    }

    public HeadupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HeadupView(Context context,int resId){
        super(context);
        this.mContext = context;
        resView = LayoutInflater.from(context).inflate(resId,null);
        addView(resView);
        setOnTouchListener(new SwipeDismissTouchListener(resView,true,swipeDismissCallBack));
    }

    /**
     * 设置标题
     * @param title
     */
    public abstract void setTitle(String title);

    /**
     * 设置内容
     * @param content
     */
    public abstract void setContent(String content);

    /**
     * 设置通知logo
     * @param path
     */
    public abstract void setLargerIcon(String path);

    public abstract void setLargerIcon(int res);


    public void animaIn(){
        setTranslationX(0);
        setTranslationY(-300);
        setAlpha(0);
        animate()
                .setDuration(700)
                .alpha(1)
                .translationY(0)
                .translationX(0)
        .setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(headupViewListener!=null){
                    headupViewListener.animaInEnd();
                }
                super.onAnimationEnd(animation);
            }
        });
    }

    public void animOut(){
        animate()
                .setDuration(300)
                .alpha(0)
        .setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(headupViewListener!=null){
                    headupViewListener.animaOutEnd();
                }
                super.onAnimationEnd(animation);
            }
        });
    }

    private SwipeDismissTouchListener.DismissCallbacks swipeDismissCallBack = new SwipeDismissTouchListener.DismissCallbacks() {
        @Override
        public boolean canDismiss() {
            return true;
        }
        @Override
        public boolean canExpand() {
            return false;
        }
        @Override
        public void onDismiss(View view, Object token, int direction) {
            if(headupViewListener!=null){
                headupViewListener.onDimiss();
            }
        }
        @Override
        public void outside() {
        }

        @Override
        public void actionDown() {
            if(headupViewListener!=null){
                headupViewListener.touchDown();
            }
        }
        @Override
        public void actionUp() {
            if(headupViewListener!=null){
                headupViewListener.touchUp();
            }
        }

        @Override
        public void actionMove() {
            if(headupViewListener!=null){
                headupViewListener.touchDown();
            }
        }
    };
    public  interface HeadupViewListener{
        void touchDown();
        void touchUp();
        void animaInEnd();
        void animaOutEnd();
        void onDimiss();
    }

    public void setHeadupViewListener(HeadupViewListener headupViewListener) {
        this.headupViewListener = headupViewListener;
    }
}
