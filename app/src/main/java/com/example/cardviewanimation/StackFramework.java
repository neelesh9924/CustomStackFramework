package com.example.cardviewanimation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class StackFramework {
    private final Context context;
    private final ArrayList<Integer> layoutsList;
    private final ArrayList<View> views;
    private final OnChangeListener listener;

    public StackFramework(Context context, ArrayList<Integer> layoutsList, OnChangeListener listener) {

        this.context = context;

        this.layoutsList = layoutsList;

        this.listener = listener;

        views = new ArrayList<>();

    }

    //add views and handling animations
    public void createAndAddViews() {

        layoutsList.forEach(id -> {

            View view = LayoutInflater.from(context).inflate(id, null, false); //creating View by inflating the layouts with their ids from list

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //setting various layout params to create the stack effect

            int marginInPixelsNegative = (int) context.getResources().getDimension(R.dimen.bottom_margin_negative);

            params.setMargins(0, 0, 0, marginInPixelsNegative);

            view.setLayoutParams(params);

            if (view instanceof ViewGroup) {
                if (((ViewGroup) view).getChildCount() > 0) {

                    FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    int marginInPixelsPositive = (int) context.getResources().getDimension(R.dimen.bottom_margin_positive);

                    params1.setMargins(0, 0, 0, marginInPixelsPositive);

                    ((ViewGroup) view).getChildAt(0).setLayoutParams(params1);

                }
            }

            views.add(view); //adding the view to the list

        });

        listener.onViewAdded(views); //passing the list back to host so they can add in their desired ViewGroup

        initiateFirstView();

    }

    private void initiateFirstView() {

        expandView(views.get(0));

        addListeners();

    }

    private void addListeners() {

        views.forEach(view -> {

            view.setOnClickListener(v -> {
                expandView(view);
            });

        });

    }


    private void expandView(View selectedView) {

        int nextIndex = views.indexOf(selectedView) + 1;

        if (nextIndex != views.size()) {
            views.get(nextIndex).setVisibility(View.VISIBLE);
        }

        animateExpandCollapseUsingWeight(selectedView, CardViewState.EXPAND);

        views.forEach(view -> {
            if (view != selectedView) {
                animateExpandCollapseUsingWeight(view, CardViewState.COLLAPSE);
                if (views.indexOf(view) > nextIndex) {
                    view.setVisibility(View.GONE);
                }
            }
        });

    }


    //animations methods
    private void animateExpandCollapseUsingWeight(final View view, CardViewState state) {

        final float initialWeight = ((LinearLayout.LayoutParams) view.getLayoutParams()).weight;

        float finalWeight = 0f;

        if (state == CardViewState.COLLAPSE) {

            view.findViewById(R.id.preLayout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.mainLayout).setVisibility(View.GONE);

            finalWeight = 1f;

        } else if (state == CardViewState.EXPAND) {

            view.findViewById(R.id.preLayout).setVisibility(View.GONE);
            view.findViewById(R.id.mainLayout).setVisibility(View.VISIBLE);

            finalWeight = 10f;

        }

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialWeight, finalWeight);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(valueAnimator1 -> {

            float animatedValue = (float) valueAnimator1.getAnimatedValue();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.weight = animatedValue;
            view.setLayoutParams(params);

        });

        valueAnimator.start();


    }

    private enum CardViewState {
        EXPAND,
        COLLAPSE
    }


    //Managing and sending data to the fragment


    public static class Info {
        String name = "";
        String number = "";
        String amount = "";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }

    public interface OnChangeListener {
        void onViewAdded(ArrayList<View> views);


    }

}
