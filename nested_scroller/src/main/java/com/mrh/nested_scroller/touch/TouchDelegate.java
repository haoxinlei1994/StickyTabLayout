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
    private float mLastTouchY;
    private int mMinTouchSlop;
    private VelocityTracker mVelocityTracker;
    private int mNestedYOffset;
    private int[] mScrollOffset = new int[2];
    private float mMaxFlingVelocity;
    private float mMinFlingVelocity;

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
                mDownY = event.getRawY();
                mLastTouchY = event.getY();
                mNestedScrollingChildHelper.startNestedScroll(View.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = this.mLastTouchY - event.getY();
                mNestedScrollingChildHelper.dispatchNestedPreScroll(0, (int) dy, new int[2], mScrollOffset);
                vtev.offsetLocation(0.0F, (float) this.mScrollOffset[1]);
                this.mNestedYOffset += this.mScrollOffset[1];
                break;
            case MotionEvent.ACTION_UP:
                //认为是一次滑动，不在向下传递
                if (Math.abs(event.getRawY() - mDownY) > mMinTouchSlop) {
                    mVelocityTracker.addMovement(vtev);
                    mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                    float yVelocity = mVelocityTracker.getYVelocity();
                    if (Math.abs(yVelocity) > mMinFlingVelocity) {
                        mNestedScrollingChildHelper.dispatchNestedPreFling(0, -yVelocity);
                    }
                    vtev.recycle();
                    if (mVelocityTracker != null) {
                        mVelocityTracker.recycle();
                        mVelocityTracker = null;
                    }
                    return true;
                }
        }
        mVelocityTracker.addMovement(vtev);
        vtev.recycle();
        if (vtev.getAction() == MotionEvent.ACTION_UP) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
        return false;
    }
}
