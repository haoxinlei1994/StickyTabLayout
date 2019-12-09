package com.mrh.nested_scroller.child;

import android.content.Context;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.mrh.nested_scroller.touch.TouchDelegate;

/**
 * 在那些没有 嵌套滚动功能的View（例如TextView）外层包裹一层 {@link NestedScrollDispatcherLayout}, 使View 具有嵌套滚动的能力
 * Created by haoxinlei on 2019-12-07.
 */
public class NestedScrollDispatcherLayout extends LinearLayout {

    private TouchDelegate mTouchDelegate;

    public NestedScrollDispatcherLayout(Context context) {
        super(context);
        init();
    }

    public NestedScrollDispatcherLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedScrollDispatcherLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
