package com.titlebar.haizitong.titlebar;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by qiuxianfu
 * Date: 2016-01-13
 * Time: 20:21
 * FIXME
 */
public class TitleBarTestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //super.setToolBarVisible(View.GONE);
        this.setStatusBar(R.color.actionbar_bg,true);
        setContentView(R.layout.activity_test);
    }

    @Override
    public void initToolBar(TitleBuilder mTitleBuilder) {
        mTitleBuilder.setLeftText("");
        mTitleBuilder.setMiddleTitleText("测试activity标题测试activity标题测试activity标题测试activity标题测试activity标题测试activity标题测试activity标题");
        mTitleBuilder.setRightText("下一步");
        mTitleBuilder.setRightImageRes(R.drawable.tab_icon_talk);
        mTitleBuilder.setTipsVisible(View.VISIBLE);
    }

    /**
     * 重载父类的方法直接操作
     * @param clicked
     */
    @Override
    public void onButtonClicked(TitleBuilder.TitleButton clicked) {
        switch (clicked){
            case LEFT:
                Toast.makeText(this, "this is back", Toast.LENGTH_SHORT).show();
                this.setFullScreen(false);
                break;
            case MIDDLE:
                Toast.makeText(this, "this is middle", Toast.LENGTH_SHORT).show();
                this.setFullScreen(false);
                break;
            case RIGHT:
                Toast.makeText(this, "this is back", Toast.LENGTH_SHORT).show();
                this.setFullScreen(true);
                break;
        }
    }
}

