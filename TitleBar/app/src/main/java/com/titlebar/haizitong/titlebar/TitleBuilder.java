package com.titlebar.haizitong.titlebar;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * name qiuxianfu
 * 2016/3/17.
 */
public class TitleBuilder implements View.OnClickListener{

    /**
     * title栏根布局
     */
    protected View mTitleView;// titleView
    protected  RelativeLayout mTitleBgColor;
    protected RelativeLayout mTitleLeftRelativeLayout;//title左边布局
    protected RelativeLayout mTitleMiddleRelativeLayout;//title中间布局
    protected RelativeLayout mTitleRightRelativeLayout;//title右边布局
    protected ImageView mTitleLeftImageView;//title左边icon显示
    protected Button mTitleLeftButton;//title左边文本显示
    protected TextView mTitleMiddleTextView;//title中间文本显示
    protected TextView mSubTitleTextView;//副标题文本显示
    protected TextView mTitleRightTextView;//title右边文本显示
    protected ImageView mTitleRightImageView;//title右边图片显示
    protected ImageView mTitleRightTips;//title右边小红点提示
    public View mRootView;
    public RelativeLayout relativeLayout;
    private ScrollView mScrollView;
    private Activity activity;
    private TitleBuilderListener mListener;



    public enum TitleButton {
        LEFT, MIDDLE, RIGHT
    }
    /**
     * 第一种  初始化方式
     * 定义一个容器加载title xml和activity布局文件
     * @param activity
     */
    public TitleBuilder(Activity activity) {
        this.activity = activity;
    }

    /**
     *设置一个布局容器，装载title_layout和传进来的layoutId 返回一个View视图
     * @param layoutId
     * @return
     */
    protected  View initBar(int layoutId){
        relativeLayout = new RelativeLayout(activity);
        mScrollView = new ScrollView(activity);
        RelativeLayout.LayoutParams rootLl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(rootLl);
        mTitleView = activity.getLayoutInflater().inflate(R.layout.title_layout_activity,null);
        mTitleBgColor = (RelativeLayout) mTitleView.findViewById(R.id.title_bar_id);
        mTitleLeftRelativeLayout = (RelativeLayout) mTitleView.findViewById(R.id.left_relative_layout);
        mTitleMiddleRelativeLayout = (RelativeLayout) mTitleView.findViewById(R.id.mid_relative_layout);
        mTitleRightRelativeLayout = (RelativeLayout) mTitleView.findViewById(R.id.right_relative_layout);
        mTitleLeftImageView = (ImageView) mTitleView.findViewById(R.id.left_icon_title);
        mTitleLeftButton = (Button) mTitleView.findViewById(R.id.left_btn);
        mTitleMiddleTextView = (TextView) mTitleView.findViewById(R.id.middle_text);
        mSubTitleTextView = (TextView) mTitleView.findViewById(R.id.middle_sub_text);
        mTitleRightTextView = (TextView) mTitleView.findViewById(R.id.title_right_text);
        mTitleRightImageView = (ImageView) mTitleView.findViewById(R.id.title_right_image_view);
        mTitleRightTips = (ImageView) mTitleView.findViewById(R.id.right_new_info);
        mRootView = LayoutInflater.from(activity).inflate(layoutId,null);//activity布局文件
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        rl.addRule(RelativeLayout.BELOW, R.id.title_bar_id);
        relativeLayout.addView(mTitleView);
        relativeLayout.setClipToPadding(false);
        relativeLayout.setFitsSystemWindows(true);
        relativeLayout.addView(mRootView);
        mRootView.setLayoutParams(rootLl);
//        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) mRootView.getLayoutParams();//面板没有全屏
        Log.e("qxf","mRootView.getWidth():"+mRootView.getWidth()+"mRootView.getHeight():"+mRootView.getHeight()+"mRootView.getMeasuredWidth():"+mRootView.getMeasuredWidth());
        mTitleLeftButton.setOnClickListener(this);
        mTitleLeftRelativeLayout.setOnClickListener(this);
        mTitleRightRelativeLayout.setOnClickListener(this);
        mTitleMiddleRelativeLayout.setOnClickListener(this);

        return  relativeLayout;
    }

    /**
     * 第二种初始化方式
     * 这里是比如你用代码创建布局的时候使用
     * @param context
     */
    public TitleBuilder(View context) {

        mTitleView = context.findViewById(R.id.title_bar_id);

//        mTitleLeftRelativeLayout = (RelativeLayout) mTitleView.findViewById(R.id.left_relative_layout);
//        mTitleMiddleRelativeLayout = (RelativeLayout) mTitleView.findViewById(R.id.mid_relative_layout);
//        mTitleRightRelativeLayout = (RelativeLayout) mTitleView.findViewById(R.id.right_relative_layout);
        mTitleLeftImageView = (ImageView) mTitleView.findViewById(R.id.left_icon_title);
        mTitleLeftButton = (Button) mTitleView.findViewById(R.id.left_btn);
        mTitleMiddleTextView = (TextView) mTitleView.findViewById(R.id.middle_text);
        mTitleRightTextView = (TextView) mTitleView.findViewById(R.id.title_right_text);
        mTitleRightImageView = (ImageView) mTitleView.findViewById(R.id.title_right_image_view);
        mTitleRightTips = (ImageView) mTitleView.findViewById(R.id.right_new_info);


    }

    /**
     * 设置左侧菜单显示图片
     * @param resId
     * @return
     */
    public TitleBuilder setLeftDrawable(int resId){
        mTitleLeftButton.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
        return  this;
    }

    /**
     * 左边文字按钮
     *
     * @param text
     * @return
     */
    public TitleBuilder setLeftText(String text) {
        // mTitleLeftButton.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        mTitleLeftButton.setText(text);
        return this;
    }

    /**
     * 设置文本的颜色
     * @param colorId 颜色值的ID
     * @return
     */
    public TitleBuilder setLeftTextColor(int colorId){
        mTitleLeftButton.setTextColor(colorId);
        return this;
    }
    /**
     * title 的背景色
     */

    public TitleBuilder seTitleBgRes(int resId) {
        mTitleView.setBackgroundResource(resId);

        return this;
    }
    /**
     * title的文本
     *
     * @param text
     * @return
     */
    public TitleBuilder setMiddleTitleText(String text) {

        mTitleMiddleTextView.setVisibility(TextUtils.isEmpty(text) ? View.GONE
                : View.VISIBLE);
        mTitleMiddleTextView.setText(text);

        return this;
    }

    /**
     * 设置副标题
     * @param subTitleText
     * @return
     */
   public TitleBuilder setSubTitleText(String subTitleText){
       mSubTitleTextView.setVisibility(TextUtils.isEmpty(subTitleText) ? View.GONE
               : View.VISIBLE);
       mSubTitleTextView.setText(subTitleText);
       return this;
   }

    /**
     * 设置左边的事件
     */
    public TitleBuilder setLeftTextOrImageListener(View.OnClickListener listener) {

        if (mTitleLeftImageView.getVisibility() == View.VISIBLE) {

            mTitleLeftImageView.setOnClickListener(listener);

        } else if (mTitleLeftButton.getVisibility() == View.VISIBLE) {

            mTitleLeftButton.setOnClickListener(listener);

        }

        return this;
    }

    /**
     * right
     */
    /**
     * 图片按钮
     *
     * @param resId
     * @return
     */
    public TitleBuilder setRightImageRes(int resId) {

        mTitleRightImageView.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        mTitleRightImageView.setBackgroundResource(resId);

        return this;
    }

    /**
     * 左边文字按钮
     *
     * @param text
     * @return
     */
    public TitleBuilder setRightText(String text) {

        mTitleRightTextView.setVisibility(TextUtils.isEmpty(text) ? View.GONE:View.VISIBLE);
        mTitleRightTextView.setText(text);

        return this;
    }

    /**
     *设置tips图片
     * @param resId
     * @return
     */
    public TitleBuilder setTipsDrawable(int resId){
        mTitleRightTips.setVisibility(resId ==0 ? View.GONE : View.VISIBLE);
        mTitleRightTips.setBackgroundResource(resId);
        return this;
    }
    public TitleBuilder setTipsVisible(int visible){
           if (mTitleRightTips.getVisibility() == visible){
               return this;
           }else{
               mTitleRightTips.setVisibility(visible);
           }

        return this;
    }
    /**
     * 设置左边的事件
     */
    public TitleBuilder setRightTextOrImageListener(View.OnClickListener listener) {

        if (mTitleRightImageView.getVisibility() == View.VISIBLE) {

            mTitleRightImageView.setOnClickListener(listener);

        } else if (mTitleRightTextView.getVisibility() == View.VISIBLE) {

            mTitleRightTextView.setOnClickListener(listener);

        }

        return this;
    }

    /**
     * 设置按钮是否可以使用
     * @param enable
     * @return
     */
    public TitleBuilder setLeftButtonEnabled(boolean enable){
        mTitleLeftButton.setEnabled(enable);
        return this;
    }

    /**
     * 设置title中间不可以操作
     * @param enable
     * @return
     */
    public TitleBuilder setMiddleTextViewEnabled(boolean enable){
        mTitleMiddleRelativeLayout.setEnabled(enable);
        return this;
    }
    /**
     * 设置title中间不可以操作
     * @param enable
     * @return
     */
    public TitleBuilder setRightViewEnabled(boolean enable){
        mTitleRightRelativeLayout.setEnabled(enable);
        return this;
    }

    /**
     * 设置监听事件
     * @param mTitleBuilderListener
     */
    public void setTitleBuilderListener(TitleBuilderListener mTitleBuilderListener){
        this.mListener = mTitleBuilderListener;
    }
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }
        if (view == mTitleLeftImageView|| view == mTitleLeftButton ||  view == mTitleLeftRelativeLayout){
            mListener.onButtonClicked(TitleButton.LEFT);
        }else if (view == mTitleMiddleTextView || view == mTitleMiddleRelativeLayout){
            mListener.onButtonClicked(TitleButton.MIDDLE);
        }
        else if(view == mTitleRightRelativeLayout || view == mTitleRightImageView || view == mTitleRightTextView){
            mListener.onButtonClicked(TitleButton.RIGHT);
        }
    }
    public interface TitleBuilderListener {
        void onButtonClicked(TitleButton clicked);
    }
}
