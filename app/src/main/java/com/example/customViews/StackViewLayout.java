package com.example.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class StackViewLayout extends LinearLayout {

    public StackViewLayout(Context context) {
        super(context);
        init();
    }

    public StackViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StackViewLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // Convert 80dp to pixels
        float scale = getContext().getResources().getDisplayMetrics().density;
        int paddingTop = (int) (80 * scale + 0.5f);
        setOrientation(VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getScreenHeight(getContext()) - paddingTop));
    }

    private int getScreenHeight(Context context) {

        if (getContext() != null) {
            return context.getResources().getDisplayMetrics().heightPixels;
        }
        return 0;

    }

}
