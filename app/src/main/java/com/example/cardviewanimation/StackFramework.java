package com.example.cardviewanimation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pojo.StackItem;

import java.util.ArrayList;

public class StackFramework {
    private final Context context;
    private final ArrayList<StackItem> stackItems;
    private final ArrayList<CustomCardView> views;
    private final OnChangeListener listener;
    private CustomCardView customCardView;

    public StackFramework(Context context, ArrayList<StackItem> stackItems, OnChangeListener listener) {

        this.context = context;

        this.stackItems = stackItems;

        this.listener = listener;

        views = new ArrayList<>();

    }

    public void createAndAddViews() { //creating the views and adding them to the list

        stackItems.forEach(stackItem -> {

            customCardView = new CustomCardView(context);
            customCardView.initCardView(stackItem);

            views.add(customCardView);

        });

        listener.onViewAdded(views); //passing the list back to host so they can add in their desired ViewGroup

        ArrayList<View> inflatedViews = new ArrayList<>();
        views.forEach(view -> {

            inflatedViews.add(view.getChildAt(0));

        });

        initiateFirstView();

    }

    private void initiateFirstView() { //expanded first view

        expandView(views.get(0));

        views.get(0).setIsActive(true);

        addListeners();

    }

    private void addListeners() { //adding listeners to the views
        //todo: manage this and disable proceeding to next view if the data is not valid

        views.forEach(view -> {

            view.setOnClickListener(v -> {
                if (view.getIsActive()) {
                    expandView(view);
                } else {
                    Toast.makeText(context, "Please fill the above details", Toast.LENGTH_SHORT).show();
                }
            });

        });

    }


    private void expandView(CustomCardView selectedView) { //expanding the view

        int nextIndex = views.indexOf(selectedView) + 1;

        if (nextIndex != views.size()) {
            views.get(nextIndex).setVisibility(View.VISIBLE);
        }

        animateExpandCollapseUsingWeight(selectedView, CardViewState.EXPAND);

        views.forEach(view -> {
            if (view != selectedView) {
                animateExpandCollapseUsingWeight(view, CardViewState.COLLAPSE); //collapsing the rest of the views
                if (views.indexOf(view) > nextIndex) {
                    view.setVisibility(View.GONE); //hiding the views that are out of scope of current
                }
            }
        });

    }

    private void animateExpandCollapseUsingWeight(CustomCardView view, CardViewState state) { //animation method

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

    public interface OnChangeListener { //interface to interact with the host fragment
        void onViewAdded(ArrayList<CustomCardView> views);


    }

}
