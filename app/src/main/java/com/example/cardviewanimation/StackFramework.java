package com.example.cardviewanimation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class StackFramework {
    private final Context context;
    private final ArrayList<StackItem> layoutsList;
    private final ArrayList<CustomCardView> views;
    private final OnChangeListener listener;

    private CustomCardView customCardView;

    public StackFramework(Context context, ArrayList<StackItem> layoutsList, OnChangeListener listener) {

        this.context = context;

        this.layoutsList = layoutsList;

        this.listener = listener;

        views = new ArrayList<>();

    }

    //add views and handling animations
    public void createAndAddViews() {

        layoutsList.forEach(stackItem -> {

            customCardView = new CustomCardView(context, stackItem);

            views.add(customCardView); //adding the view to the list

        });

        listener.onViewAdded(views); //passing the list back to host so they can add in their desired ViewGroup

        initiateFirstView();

    }

    private void initiateFirstView() {

        expandView(views.get(0));

        addListeners();

    }

    private void addListeners() {
        //manage this and disable proceeding to next view if the data is not valid

        views.forEach(view -> {

            view.setOnClickListener(v -> {
                expandView(view);
            });

        });

    }


    private void expandView(CustomCardView selectedView) {

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
    private void animateExpandCollapseUsingWeight(CustomCardView view, CardViewState state) {

        final float initialWeight = ((LinearLayout.LayoutParams) view.getLayoutParams()).weight;

        float finalWeight = 0f;

        if (state == CardViewState.COLLAPSE) {

            finalWeight = 2f;

            view.collapsed();

        } else if (state == CardViewState.EXPAND) {

            finalWeight = 5f;

            view.expanded();


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


    //interface to interact with the host fragment
    public interface OnChangeListener {
        void onViewAdded(ArrayList<CustomCardView> views);


    }

}
