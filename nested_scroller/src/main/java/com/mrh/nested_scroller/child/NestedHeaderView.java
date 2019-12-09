package com.mrh.nested_scroller.child;

import android.content.Context;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.mrh.nested_scroller.touch.TouchDelegate;

/**
 * Created by haoxinlei on 2019-12-07.
 */
public class NestedHeaderView extends LinearLayout implements NestedHeader {
    TouchDelegate mTouchDelegate;

    public NestedHeaderView(Context context) {
        super(context);
        init();
    }

    public NestedHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTouchDelegate = new TouchDelegate(getContext(), new NestedScrollingChildHelper(this),
                ViewConfiguration.get(getContext()).getScaledTouchSlop());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!mTouchDelegate.dispatchTouchEvent(event)) {
            super.dispatchTouchEvent(event);
        }
        return true;
    }
}
