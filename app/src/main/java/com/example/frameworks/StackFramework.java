package com.example.frameworks;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.customViews.CustomCardView;
import com.example.customViews.StackViewLayout;
import com.example.pojo.StackItem;

import java.util.ArrayList;

public class StackFramework {
    private final Context context;
    private final ArrayList<CustomCardView> views;

    public StackFramework(Context context, ArrayList<StackItem> StackItems, StackViewLayout StackViewLayout) {

        this.context = context;

        views = new ArrayList<>();

        createAndAddViews(StackItems, StackViewLayout);
    }

    private void createAndAddViews(ArrayList<StackItem> stackItems, StackViewLayout stackViewLayout) { //creating the views and adding them to the list

        stackItems.forEach(stackItem -> views.add(new CustomCardView(context, stackItem)));

        addStackViewsToStackViewLayout(stackViewLayout);
    }

    private void addStackViewsToStackViewLayout(StackViewLayout stackViewLayout) {

        views.forEach(stackViewLayout::addView);

        initiateFirstView();
    }

    private void initiateFirstView() { //expanded first view

        views.get(0).setEnabledToExpand(true);
        expandView(views.get(0));

        addListeners();

    }

    private void addListeners() { //adding listeners to the views

        views.forEach(view -> {
            view.setOnClickListener(v -> {
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

            finalWeight = 20f;
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

    public void setCompleted(View view, CompletionState action) { //setting the view as completed and enabling the next view

        boolean isComplete = false;

        int currentIndex = views.indexOf((CustomCardView) view);
        int nextIndex = currentIndex + 1;

        switch (action) {
            case COMPLETE:
                isComplete = true;
                break;
            case COMPLETE_EXPAND_NEXT:
                isComplete = true;
                expandView(views.get(nextIndex));

                break;
            case INCOMPLETE:
                break;
        }

        if (nextIndex < views.size()) {
            views.get(currentIndex).setCompleted(isComplete);
            views.get(nextIndex).setEnabledToExpand(isComplete);
        }

    }

    public enum CompletionState {
        INCOMPLETE,
        COMPLETE,
        COMPLETE_EXPAND_NEXT
    }

    public void getViews(ViewsAddedListener onViewsAdded) { //returning the views to the host fragment
        onViewsAdded.viewsAdded(views);
    }

    public interface ViewsAddedListener { //interface for returning the views to the host fragment
        void viewsAdded(ArrayList<CustomCardView> views);

    }

    private enum CardViewState {
        EXPAND,
        COLLAPSE
    }

}
