package com.mrh.nested_scroller;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.mrh.nested_scroller.child.NestedBody;
import com.mrh.nested_scroller.child.NestedHeader;

/**
 * Created by haoxinlei on 2019-12-07.
 */
public class NestedScrollLayout extends LinearLayout implements NestedScrollingParent {

    private int mHeaderHeight;
    private NestedScrollingParentHelper mNestedScrollingParentHelper;
    private Scroller mScroller;
    /**
     * fling的时候Y轴最小坐标
     */
    private int mMinY;
    /**
     * fling的时候Y轴最大坐标
     */
    private int mMaxY;

    public NestedScrollLayout(Context context) {
        super(context);
        init();
    }

    public NestedScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //找到child
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new IllegalStateException("NestedScrollContainer must only have NestedHeader and NestedBody!!!");
        }
        if (getChildAt(0) instanceof NestedHeader) {
            mHeaderHeight = getChildAt(0).getMeasuredHeight();
            mMinY = -mHeaderHeight * 10;
            mMaxY = mHeaderHeight * 10;
        } else {
            throw new IllegalStateException("NestedScrollContainer must only have NestedHeader and NestedBody!!!");
        }
        if (!(getChildAt(1) instanceof NestedBody)) {
            throw new IllegalStateException("NestedScrollContainer must only have NestedHeader and NestedBody!!!");
        }
        //重新设置长度
        resetContainerHeight();
    }

    private void resetContainerHeight() {
        getChildAt(1).measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getMeasuredHeight() + mHeaderHeight, MeasureSpec.EXACTLY));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 永远接收子view的nestscroll事件
     *
     * @param child
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        mNestedScrollingParentHelper.onStopNestedScroll(child);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean hiddenTop = dy > 0 && getScrollY() < mHeaderHeight;
        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop) {
            int temp = dy;
            //滑到顶部
            if (getScrollY() + dy < 0) {
                temp = -getScrollY();
            }
            //滑到底部
            if (getScrollY() + dy > mHeaderHeight) {
                temp = mHeaderHeight - getScrollY();
            }
            scrollBy(0, temp);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        //header view 还显示直接拦截fling事件
        if (getScrollY() < mHeaderHeight) {
            mScroller.abortAnimation();
            // Y 轴上的最大最小值是会影响fling时的快慢的
            mScroller.fling(0, getScrollY(), 0, (int) velocityY, 0, 0, mMinY, mMaxY);
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }


    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mHeaderHeight) {
            y = mHeaderHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int scrollY = getScrollY();
            if (scrollY <= 0
                    || scrollY >= mHeaderHeight) {
                mScroller.abortAnimation();
                return;
            }

            scrollTo(0, mScroller.getCurrY());
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 自定义 fling 的最大最小坐标
     * @param minY
     */
    public void setMinY(int minY) {
        mMinY = minY;
    }

    public void setMaxY(int maxY) {
        mMaxY = maxY;
    }
}
