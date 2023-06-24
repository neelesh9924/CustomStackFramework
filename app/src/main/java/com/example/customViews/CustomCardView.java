package com.example.customViews;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.cardviewanimation.R;
import com.example.pojo.StackItem;
import com.google.android.material.card.MaterialCardView;

public class CustomCardView extends MaterialCardView {

    private StackItem stackItem;
    private final Context context;
    private Boolean isCompleted = false; //to set current steps operation completed or not
    private View mainView, preView, postView;

    public CustomCardView(Context context) {
        super(context);
        this.context = context;

    }

    public void initCardView(StackItem stackItem) { //initialising the card view

        this.stackItem = stackItem;

        setCardElevation(dpToPx(context, 2));
        setRadius(dpToPx(context, 25));
        setMinimumHeight(dpToPx(context, 80));

        addLayouts();

    }

    private void addLayouts() {//inflating the view with the layout id and setting the background colour

        inflate(getContext(), stackItem.getMainLayoutId(), this);
        inflate(getContext(), stackItem.getPreLayoutId(), this);
        inflate(getContext(), stackItem.getPostLayoutId(), this);

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

            for (int i = 0; i < getChildCount(); i++) {

                LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                int marginInPixelsPositive = dpToPx(getContext(), getContext().getResources().getDimension(R.dimen.bottom_margin_positive));
                params.setMargins(0, 0, 0, marginInPixelsPositive);
                params.gravity = Gravity.TOP;

                getChildAt(i).setLayoutParams(params);
            }

        }

        setInflatedViewBackgroundColour(R.color.disabledStepColor);

    }

    private void setInflatedViewBackgroundColour(int bgColorId) { //matching the background colour of the inflated views with the parent

        setBackgroundTintList(ContextCompat.getColorStateList(getContext(), bgColorId));

        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).setBackgroundTintList(ContextCompat.getColorStateList(getContext(), bgColorId));
            }
        }

        createInstancesChild();

    }

    private void createInstancesChild() { //creating instances and initial visibility of the inflated views

        mainView = getChildAt(0);
        mainView.setVisibility(View.GONE);

        preView = getChildAt(1);
        preView.setVisibility(View.GONE);

        postView = getChildAt(2);
        postView.setVisibility(View.GONE);

    }

    public void expand() { //toggling the visibility of the inner view

        setInflatedViewBackgroundColour(stackItem.getBgColorId());

        mainView.setVisibility(View.VISIBLE);
        preView.setVisibility(View.GONE);
        postView.setVisibility(View.GONE);
    }

    public void collapse() { //toggling the visibility of the inner view

        mainView.setVisibility(View.GONE);

        if (isCompleted) {
            postView.setVisibility(View.VISIBLE);
            preView.setVisibility(View.GONE);
        } else {
            postView.setVisibility(View.GONE);
            preView.setVisibility(View.VISIBLE);
        }
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }


    private static int dpToPx(Context context, float dp) { //converting dp to pixels
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }


}
