package com.example.cardviewanimation;

import android.animation.ValueAnimator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class BottomSheetFramework {
    private final home parentFragment;
    private int clickedPosition;
    private final ArrayList<View> views;
    private final Info currentInfo;
    private final MutableLiveData<Info> infoData;

    //int expandedHeight = 1200, collapsedHeight = 350;

    public BottomSheetFramework(home home) {

        this.parentFragment = home;
        views = new ArrayList<>();
        clickedPosition = 0;

        currentInfo = new Info();

        infoData = new MutableLiveData<>();

        createBottomSheet();

    }

    private void createBottomSheet() {

        addViews();

    }

    private void addViews() {

        View cardView = LayoutInflater.from(parentFragment.getContext()).inflate(R.layout.cardview, parentFragment.binding.linearLayout, false);
        parentFragment.binding.linearLayout.addView(cardView);
        views.add(cardView);

        View cardView2 = LayoutInflater.from(parentFragment.getContext()).inflate(R.layout.cardview2, parentFragment.binding.linearLayout, false);
        parentFragment.binding.linearLayout.addView(cardView2);
        views.add(cardView2);

        View cardView3 = LayoutInflater.from(parentFragment.getContext()).inflate(R.layout.cardview3, parentFragment.binding.linearLayout, false);
        parentFragment.binding.linearLayout.addView(cardView3);
        views.add(cardView3);

        addBottomMargins();

        //todo: handle dynamic height

        handleViewCollapsing();

        addListeners();

    }

    private void addListeners() {

        views.forEach(view -> {
            view.setOnClickListener(v -> {
                clickedPosition = views.indexOf(view);
                handleViewCollapsing();
            });
        });

        TextInputEditText name = views.get(0).findViewById(R.id.nameTextInputEditText);

        TextInputEditText number = views.get(0).findViewById(R.id.numberTextInputEditText);

        TextView nameTextView = views.get(0).findViewById(R.id.nameTextView);

        TextView numberTextView = views.get(0).findViewById(R.id.numberTextView);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                currentInfo.setName(charSequence.toString().trim());
                nameTextView.setText(charSequence.toString().trim());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() == 10) {
                    currentInfo.setNumber(charSequence.toString().trim());
                    clickedPosition = 1;
                    handleViewCollapsing();
                    numberTextView.setText(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        TextInputEditText amount = views.get(1).findViewById(R.id.amountTextInputEditText);

        TextView amountTextView = views.get(1).findViewById(R.id.amountTextView);

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentInfo.setAmount(charSequence.toString().trim());
                amountTextView.setText(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        amount.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    clickedPosition = 2;
                    handleViewCollapsing();
                    return true;
                }
                return false;
            }
        });

        views.get(2).findViewById(R.id.nextButton).setOnClickListener(v -> {

            infoData.postValue(currentInfo);

        });


    }

    //handling animations

    private void addBottomMargins() {

        views.forEach(view -> {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            int marginInPixelsNegative = (int) parentFragment.getContext().getResources().getDimension(R.dimen.bottom_margin_negative);

            params.setMargins(0, 0, 0, marginInPixelsNegative);

            view.setLayoutParams(params);

            if (view instanceof ViewGroup) {
                if (((ViewGroup) view).getChildCount() > 0) {

                    FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    int marginInPixelsPositive = (int) parentFragment.getContext().getResources().getDimension(R.dimen.bottom_margin_positive);

                    params1.setMargins(0, 0, 0, marginInPixelsPositive);

                    ((ViewGroup) view).getChildAt(0).setLayoutParams(params1);
                }
            }

        });

        CardView cv1 = (CardView) views.get(0);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, -40);

    }

    private void handleViewCollapsing() {

        if (clickedPosition == views.size()) {
            return;
        }
        expandCardView((MaterialCardView) views.get(clickedPosition));

    }


    private void expandCardView(MaterialCardView cardView) {

        animateExpandUsingWeight(cardView);


        if (clickedPosition != 2) {
            views.get(clickedPosition).findViewById(R.id.majorLayout).setVisibility(View.VISIBLE);
            views.get(clickedPosition).findViewById(R.id.miniLayout).setVisibility(View.GONE);
        }

        collapseAllCardViews();

        if (views.size() > clickedPosition + 1) {
            animateVisibility((MaterialCardView) views.get(clickedPosition + 1), View.VISIBLE);

        }
    }

    private void collapseAllCardViews() {

        if (clickedPosition == 0) {

            views.forEach(view -> {
                if (views.indexOf(view) == 1) {
                    animateCollapseUsingWeight((MaterialCardView) view);
                    view.findViewById(R.id.majorLayout).setVisibility(View.GONE);
                    view.findViewById(R.id.miniLayout).setVisibility(View.VISIBLE);
                } else if (views.indexOf(view) > 1)
                    animateVisibility((MaterialCardView) view, View.GONE);
            });

        } else {

            views.forEach(view -> {
                if (views.indexOf(view) <= clickedPosition + 1) {
                    if (views.indexOf(view) != clickedPosition) {
                        animateCollapseUsingWeight((MaterialCardView) view);

                        if (views.indexOf(view) == views.size() - 1) {
                            return;
                        }
                        view.findViewById(R.id.majorLayout).setVisibility(View.GONE);
                        view.findViewById(R.id.miniLayout).setVisibility(View.VISIBLE);

                    }

                }
            });

        }
    }


    //animations methods

    private void animateVisibility(final MaterialCardView cardView, int visibility) {

        int startHeight = cardView.getHeight();

        ValueAnimator valueAnimator;

        if (visibility == View.GONE) {
            valueAnimator = ValueAnimator.ofInt(startHeight, 0);
        } else {
            valueAnimator = ValueAnimator.ofInt(startHeight, ViewGroup.LayoutParams.WRAP_CONTENT);

        }

        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(animation -> {

            int value = (int) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
            layoutParams.height = value;
            cardView.setLayoutParams(layoutParams);

        });
        valueAnimator.start();
    }

    private void animateCardViewHeight(final MaterialCardView cardView, final int targetHeight) {
        final int startHeight = cardView.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight, targetHeight);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(animation -> {

            int value = (int) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
            layoutParams.height = value;
            cardView.setLayoutParams(layoutParams);

        });
        valueAnimator.start();
    }

    private void animateAndExpandToWrapContent(final MaterialCardView cardView) {

        final int startHeight = cardView.getHeight();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(startHeight, ViewGroup.LayoutParams.WRAP_CONTENT);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(animation -> {

            int value = (int) animation.getAnimatedValue();

            ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
            layoutParams.height = value;
            cardView.setLayoutParams(layoutParams);

        });
        valueAnimator.start();

    }

    private void animateExpandUsingWeight(final MaterialCardView cardView) {


        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 10f);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardView.getLayoutParams();
                params.weight = animatedValue;
                cardView.setLayoutParams(params);
            }
        });
        valueAnimator.start();


    }

    private void animateCollapseUsingWeight(final MaterialCardView cardView) {

        final float initialWeight = ((LinearLayout.LayoutParams) cardView.getLayoutParams()).weight;

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialWeight, 1f);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardView.getLayoutParams();
                params.weight = animatedValue;
                cardView.setLayoutParams(params);
            }
        });
        valueAnimator.start();


    }


    //Managing and sending data to the fragment
    public MutableLiveData<Info> getInfo() {
        return infoData;
    }


    public static class Info {
        String name;
        String number;

        String amount;

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


}
