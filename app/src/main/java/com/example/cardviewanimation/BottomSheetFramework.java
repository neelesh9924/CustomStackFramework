package com.example.cardviewanimation;

import android.animation.ValueAnimator;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Visibility;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetFramework {
    home parentFragment;
    int clickedPosition;
    ArrayList<View> views;

    Info currentInfo;

    //int expandedHeight = 1200, collapsedHeight = 350;

    public BottomSheetFramework(home home) {

        this.parentFragment = home;
        views = new ArrayList<>();
        clickedPosition = 0;

        currentInfo = new Info();

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

//        if (clickedPosition == 0) {
//            expandCardView((MaterialCardView) views.get(clickedPosition));
//        }

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

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                currentInfo.setName(charSequence.toString().trim());

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
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        TextInputEditText amount = views.get(1).findViewById(R.id.amountTextInputEditText);

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentInfo.setAmount(charSequence.toString().trim());
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

        });


    }

    public void handleViewCollapsing() {

        if (clickedPosition == views.size()) {
            return;
        }
        expandCardView((MaterialCardView) views.get(clickedPosition));

    }


    public void expandCardView(MaterialCardView cardView) {

        animateAndExpandToWrapContent(cardView);


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
                    animateAndExpandToWrapContent((MaterialCardView) view);
                    view.findViewById(R.id.majorLayout).setVisibility(View.GONE);
                    view.findViewById(R.id.miniLayout).setVisibility(View.VISIBLE);
                } else if (views.indexOf(view) > 1)
                    animateVisibility((MaterialCardView) view, View.GONE);
            });

        } else {

            views.forEach(view -> {
                if (views.indexOf(view) <= clickedPosition + 1) {
                    if (views.indexOf(view) != clickedPosition) {
                        animateAndExpandToWrapContent((MaterialCardView) view);

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

    private static class Info {
        String name;
        String number;

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

        String amount;
    }


}
