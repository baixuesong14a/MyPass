package com.cedar.mypass.Layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.cedar.mypass.R;

import androidx.annotation.NonNull;

public class SlideLayout extends FrameLayout {
    private View contentView;
    private View menuView;

    private int viewHeight; //高是相同的
    private int contentWidth;
    private int menuWidth;

    //滑动器
    private Scroller scroller;

    public SlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    /**
     * 布局文件加载完成时被调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = findViewById(R.id.item_data);
        menuView = findViewById(R.id.item_del);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewHeight = getMeasuredHeight();

        contentWidth = contentView.getMeasuredWidth();
        menuWidth = menuView.getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        menuView.layout(contentWidth, 0, contentWidth+menuWidth,viewHeight);
    }

    private float startX;
    private float startY;

    private float downX;
    private float downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float endY = event.getY();

                //计算偏移量
                float distanceX = endX - startX;

                int toScrollX = (int) (getScrollX()-distanceX);
                //屏蔽非法值
                if (toScrollX < 0 ) { toScrollX = 0; }
                if (toScrollX > menuWidth) { toScrollX = menuWidth ; }
                //System.out.println("toScroll-->"+toScrollX+"-->"+getScrollX());
                scrollTo(toScrollX,getScrollY());

                startX = event.getX();

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    /**
     * 打开menu菜单
     */
    public void openMenu() {
        int dx = menuWidth-getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(),dx, getScrollY());
        invalidate();
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        //0表示menu移动到的目标距离,目标位置-起始位置
        int dx = 0-getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(),dx, getScrollY());
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }
}
