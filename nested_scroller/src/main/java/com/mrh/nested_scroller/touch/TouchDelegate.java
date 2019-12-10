package com.mrh.nested_scroller.touch;

import android.content.Context;
import android.support.v4.view.NestedScrollingChildHelper;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by haoxinlei on 2019-12-07.
 */
public class TouchDelegate {

    private NestedScrollingChildHelper mNestedScrollingChildHelper;
    private float mDownY;
    private float mDownX;
    private float mLastTouchY;
    private int mMinTouchSlop;
    private VelocityTracker mVelocityTracker;
    private int mNestedYOffset;
    private int[] mScrollOffset = new int[2];
    private float mMaxFlingVelocity;
    private float mMinFlingVelocity;
    /**
     * 第一次Move事件
     */
    private static final int FIRST_MOVE_ACTION = 0x1;
    /**
     * 垂直滚动
     */
    private static final int VERTICAL_MOVE_MASK = 0x2;
    /**
     * 是否是垂直方向的滑动。触发第一次Move事件的时候，判断是否是垂直方向
     */
    private int mMoveStatus;

    public TouchDelegate(Context context, NestedScrollingChildHelper nestedScrollingChildHelper, int minTouchSlop) {
        mNestedScrollingChildHelper = nestedScrollingChildHelper;
        mNestedScrollingChildHelper.setNestedScrollingEnabled(true);
        mMinTouchSlop = minTouchSlop;
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mMaxFlingVelocity = configuration.getScaledMaximumFlingVelocity();
        mMinFlingVelocity = configuration.getScaledMinimumFlingVelocity();
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        MotionEvent vtev = MotionEvent.obtain(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mNestedYOffset = 0;
        }
        vtev.offsetLocation(0, mNestedYOffset);
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getRawX();
                mDownY = event.getRawY();
                mLastTouchY = event.getY();
                mNestedScrollingChildHelper.startNestedScroll(View.SCROLL_AXIS_VERTICAL);
                mMoveStatus = 0;
                mVelocityTracker.addMovement(vtev);
                break;
            case MotionEvent.ACTION_MOVE:
                //只有在第一个Move事件的时候去判断滑动方向，后续的事件都根据这个条件筛选
                //当前为第一个Move事件
                if ((mMoveStatus & FIRST_MOVE_ACTION) == 0) {
                    mMoveStatus |= FIRST_MOVE_ACTION;
                    if (Math.abs(event.getRawY() - mDownY) > Math.abs(event.getRawX() - mDownX)) {
                        mMoveStatus |= VERTICAL_MOVE_MASK;
                    }
                }
                //当前是垂直方向上的滑动,将事件全部dispatch出去，子View将获取不到Move事件
                if ((mMoveStatus & VERTICAL_MOVE_MASK) == VERTICAL_MOVE_MASK) {
                    float dy = this.mLastTouchY - event.getY();
                    mNestedScrollingChildHelper.dispatchNestedPreScroll(0, (int) dy, new int[2], mScrollOffset);
                    vtev.offsetLocation(0.0F, (float) this.mScrollOffset[1]);
                    this.mNestedYOffset += this.mScrollOffset[1];
                    mVelocityTracker.addMovement(vtev);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //如果是垂直方向的滑动，并且滑动距离大于最小滑动距离，不在向下传递
                if ((mMoveStatus & VERTICAL_MOVE_MASK) == VERTICAL_MOVE_MASK
                        && Math.abs(event.getRawY() - mDownY) > mMinTouchSlop) {
                    mVelocityTracker.addMovement(vtev);
                    mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                    float yVelocity = mVelocityTracker.getYVelocity();
                    if (Math.abs(yVelocity) > mMinFlingVelocity) {
                        mNestedScrollingChildHelper.dispatchNestedPreFling(0, -yVelocity);
                    }
                    recycler(vtev);
                    return true;
                }
                break;
        }
        recycler(vtev);
        return false;
    }

    private void recycler(MotionEvent event) {
        if ((event.getAction() == MotionEvent.ACTION_UP
                || event.getAction() == MotionEvent.ACTION_CANCEL)
                && mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
        if (event != null) {
            event.recycle();
        }
    }
}
