package com.example.frameworks;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.customViews.CustomCardView;
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

        initiateFirstView();

    }

    private void initiateFirstView() { //expanded first view

        views.get(0).setEnabledToExpand(true);
        expandView(views.get(0));

        addListeners();

    }

    private void addListeners() { //adding listeners to the views

        views.forEach(view -> {

            int currentIndex = views.indexOf(view);
            int lastIndex = currentIndex - 1;

            view.setOnClickListener(v -> {

                /*if (currentIndex == 0) {
                    expandView(view); //for the first view
                    return;
                }

                if (lastIndex >= 0) { //for the rest of the views
                    if (views.get(lastIndex).getCompleted()) {
                        expandView(view);
                    } else {
                        Toast.makeText(context, "Please fill the above details", Toast.LENGTH_SHORT).show();
                    }
                }*/

                if (view.getEnabledToExpand()) {
                    expandView(view);
                } else {
                    Toast.makeText(context, "Please fill the above details", Toast.LENGTH_SHORT).show();
                }
            });


        });

    }


    private void expandView(CustomCardView selectedView) {

        int nextIndex = views.indexOf(selectedView) + 1;

        if (nextIndex != views.size()) {
            views.get(nextIndex).setVisibility(View.VISIBLE);
        }

        animateExpandCollapseUsingWeight(selectedView, CardViewState.EXPAND); //expanding the selected view

        views.forEach(view -> {
            if (view != selectedView) {
                animateExpandCollapseUsingWeight(view, CardViewState.COLLAPSE); //collapsing the rest of the views
                if (views.indexOf(view) > nextIndex) {
                    view.setVisibility(View.GONE); //hiding the views that are out of scope of current i.e. the views after next view
                }
            }
        });

    }

    private void animateExpandCollapseUsingWeight(CustomCardView view, CardViewState state) { //animation method for expanding and collapsing

        final float initialWeight = ((LinearLayout.LayoutParams) view.getLayoutParams()).weight;

        float finalWeight = 0f;

        if (state == CardViewState.COLLAPSE) { //todo: change weight values to your liking of expanded and collapsed views

            finalWeight = 1f;
            view.collapse();

        } else if (state == CardViewState.EXPAND) {

            finalWeight = 5f;
            view.expand();
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

    public void setCompleted(CustomCardView view, Boolean isComplete) { //setting the view as completed and enabling the next view

        int currentIndex = views.indexOf(view);
        int nextIndex = currentIndex + 1;

        if (nextIndex < views.size()) {
            views.get(currentIndex).setCompleted(isComplete);
            views.get(nextIndex).setEnabledToExpand(isComplete);
        }

    }

    private enum CardViewState {
        EXPAND,
        COLLAPSE
    }

    public interface OnChangeListener { //interface to interact with the host fragment
        void onViewAdded(ArrayList<CustomCardView> views);


    }

}
