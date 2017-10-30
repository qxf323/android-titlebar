package com.titlebar.haizitong.titlebar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Field;


public abstract class  BaseActivity extends Activity  implements TitleBuilder.TitleBuilderListener {
    public TitleBuilder mTitleBuilder;

    private int TitleBarVisible;
    private boolean isChanageStatus = true;
    private boolean isFullScreen = false;
    private boolean isFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
    }

    @Override
    public void setContentView(int id) {
        this.TitleBarVisible = View.VISIBLE;
        mTitleBuilder = new TitleBuilder(this);
        this.setToolBarVisible(this.TitleBarVisible);
        super.setContentView(mTitleBuilder.initBar(id));
        this.initToolBar(mTitleBuilder);
        this.setFullScreen(isFullScreen);
        mTitleBuilder.setTitleBuilderListener(this);
        setStatusBar(this.getTitleColor(),isChanageStatus);
    }

    @Override
    public void onButtonClicked(TitleBuilder.TitleButton clicked) {
        switch (clicked) {
            case LEFT:
                finish();
                Toast.makeText(this, "this is back", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public abstract void initToolBar(TitleBuilder mTitleBuilder);

    /**
     * 设置title是否可见  默认是可见状态
     *
     * @param visible
     */
    public void setToolBarVisible(int visible) {
        if (this.mTitleBuilder.mTitleView != null) {
            if (this.TitleBarVisible == visible)
                return;
            this.mTitleBuilder.mTitleView.setVisibility(visible);
        }
        this.TitleBarVisible = visible;
    }

    /**
     * 设置状态的属性
     *
     * @param statusBar
     */
    public void setStatusBar(int statusColor,boolean statusBar) {
        this.isChanageStatus = statusBar;
        if (statusBar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window win = getWindow();
                WindowManager.LayoutParams winParams = win.getAttributes();
                final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                winParams.flags |= bits;
                win.setAttributes(winParams);
            }
            //透明状态栏
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(statusColor);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            tintManager.setNavigationBarTintEnabled(true);
            //透明导航栏
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public void setFullScreen(boolean isFullScreen) {
//        if (isFullScreen) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }else{
//            //getWindow().setFlags(WindowManager.LayoutParams.F,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
        this.isFullScreen = isFullScreen;
        if (isFullScreen) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            this.setToolBarVisible(View.GONE);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            this.setToolBarVisible(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        this.setContentScreen();
        super.onResume();
    }

    private float getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    private void setContentScreen() {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mTitleBuilder.mRootView.measure(widthSpec, heightSpec);
        mTitleBuilder.mTitleView.measure(widthSpec, heightSpec);
            mTitleBuilder.mRootView.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {

                        @Override
                        public boolean onPreDraw() {
                            float height;
                            if(isFullScreen){
                                height = mTitleBuilder.mRootView.getMeasuredHeight();
                            }else if (TitleBarVisible == View.GONE){
                                height = mTitleBuilder.mRootView.getMeasuredHeight() + getStatusBarHeight();
                            }else{
                                height = mTitleBuilder.mRootView.getMeasuredHeight() + mTitleBuilder.mTitleView.getMeasuredHeight() + getStatusBarHeight();
                            }
                            Log.e("qxf", "height:" + height + "getScreenHeight():" + getScreenHeight() + "content" + mTitleBuilder.mRootView.getMeasuredHeight());
                            if (!isFirst) {
                                if (height+1 > getScreenHeight()) {
                                    isFirst = true;
                                    Log.e("qxf", "height:" + height + "getScreenHeight():" + getScreenHeight());
                                    ViewGroup viewParent = (ViewGroup) mTitleBuilder.mRootView.getParent();
                                    Log.e("qxf", viewParent + "111111111");
                                    if (viewParent != null)
                                        viewParent.removeView(mTitleBuilder.mRootView);
                                    ScrollView scrollView = new ScrollView(BaseActivity.this);
                                    RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                    rl.addRule(RelativeLayout.BELOW, R.id.title_bar_id);
                                    scrollView.setLayoutParams(rl);
                                    scrollView.setBackgroundColor(getResources().getColor(R.color.withe));
//                              viewParent.addView(mTitleBuilder.mTitleView);
                                    scrollView.addView(mTitleBuilder.mRootView);
                                    viewParent.addView(scrollView);

                                } else {
                                    Log.e("qxf", "getScreenHeight():" + getScreenHeight() + "mTitleBuilder.mRootView.getHeight()" + height);
                                }
                            }
                            return true;
                        }
                        });
    }

    private float getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            Log.e("qxf", "get status bar height fail" + e1);
            e1.printStackTrace();
        }
       return  sbar;
    }

}
