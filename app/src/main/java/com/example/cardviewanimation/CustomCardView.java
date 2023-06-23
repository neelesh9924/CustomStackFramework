package com.example.cardviewanimation;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.card.MaterialCardView;

public class CustomCardView extends MaterialCardView {

    StackItem stackItem;

    public CustomCardView(Context context, StackItem stackItem) {
        super(context);

        this.stackItem = stackItem;

        setRootParams(context);
    }

    private void setRootParams(Context context) {

        setCardElevation(dpToPx(context, 2));
        setRadius(dpToPx(context, 25));
        setMinimumHeight(dpToPx(context, 100));
        setPadding(dpToPx(context, 10), dpToPx(context, 10), dpToPx(context, 10), dpToPx(context, 10));

        inflateViewWithBGColour(stackItem.getLayoutId(), stackItem.getBgColorId(), stackItem.getTitle());

    }

    public void inflateViewWithBGColour(int layoutId, int color, String title) { //inflating the view with the layout id and setting the background colour

        inflate(getContext(), layoutId, this);
        setBackgroundTintList(ContextCompat.getColorStateList(getContext(), color));
        setInflatedViewBackgroundColour(color, title);

    }

    private void setInflatedViewBackgroundColour(int color, String title) { //matching the background colour of the inflated view with the parent

        if (getChildCount() > 0) {
            getChildAt(0).setBackgroundTintList(ContextCompat.getColorStateList(getContext(), color));
        }

        setLayoutMargin();

    }

    private void setLayoutMargin() { //setting various layout params to create the stack effect

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        int marginInPixelsNegative = dpToPx(getContext(), getContext().getResources().getDimension(R.dimen.bottom_margin_negative));

        params.setMargins(0, 0, 0, marginInPixelsNegative);

        setLayoutParams(params);

        addChildLayoutMargin();
    }

    private void addChildLayoutMargin() { //setting params to compensate for the negative margin of the parent for stack effect

        if (getChildCount() > 0) {

            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

            int marginInPixelsPositive = dpToPx(getContext(), getContext().getResources().getDimension(R.dimen.bottom_margin_positive));

            params.setMargins(0, 0, 0, marginInPixelsPositive);

            getChildAt(0).setLayoutParams(params);

        }

        addTitleChildLayout(stackItem.getTitle());

    }

    private void addTitleChildLayout(String title) {

        TextView textView = new TextView(getContext());
        textView.setText(title);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(
                dpToPx(getContext(), 10),
                dpToPx(getContext(), 10),
                dpToPx(getContext(), 10),
                dpToPx(getContext(), 10));

        addView(textView);

    }

    public void expanded() { //toggling the visibility of the inner view
        if (getChildCount() > 0) {
            getChildAt(0).setVisibility(View.VISIBLE);
            getChildAt(1).setVisibility(View.GONE);
        }
    }

    public void collapsed() { //toggling the visibility of the inner view
        if (getChildCount() > 0) {
            getChildAt(0).setVisibility(View.GONE);
            getChildAt(1).setVisibility(View.VISIBLE);
        }
    }


    public static int dpToPx(Context context, float dp) { //converting dp to pixels
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }


}
