package com.mrh.nested_scroller.child;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 默认实现的 NestedBody 实现类， 继承LinearLayout
 * Created by haoxinlei on 2019-12-07.
 */
public class NestedBodyView extends LinearLayout implements NestedBody {

    public NestedBodyView(Context context) {
        super(context);
        init();
    }

    public NestedBodyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedBodyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
    }
}
