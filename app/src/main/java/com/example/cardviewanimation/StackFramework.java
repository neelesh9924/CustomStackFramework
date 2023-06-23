package com.example.cardviewanimation;

import android.animation.ValueAnimator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class StackFramework {
    private final BottomSheetFragment parentFragment;
    private int clickedPosition;
    private final ArrayList<View> views;
    private final Info currentInfo;

    private final StepValidation valid;
    private final MutableLiveData<Info> infoData;

    public StackFramework(BottomSheetFragment home) {

        this.parentFragment = home;

        views = new ArrayList<>();

        clickedPosition = 0;

        currentInfo = new Info();

        valid = new StepValidation();

        infoData = new MutableLiveData<>();

        createBottomSheet();

    }

    private void createBottomSheet() {

        addViews();

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

        //todo: make below dynamic

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

                if (currentInfo.getName().isEmpty()) {
                    Toast.makeText(parentFragment.getContext(), "Enter your name", Toast.LENGTH_SHORT).show();
                }

                if (charSequence.toString().trim().length() == 10) {
                    currentInfo.setNumber(charSequence.toString().trim());
                    numberTextView.setText(charSequence.toString().trim());
                    clickedPosition = 1;
                    valid.setNameNumber(true);
                    handleViewCollapsing();
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

                    if (currentInfo.getAmount().isEmpty()) {
                        Toast.makeText(parentFragment.getContext(), "Enter amount", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    clickedPosition = 2;
                    valid.setAmount(true);
                    handleViewCollapsing();
                    valid.setPay(true);
                    return true;
                }
                return false;
            }
        });

        views.get(2).findViewById(R.id.nextButton).setOnClickListener(v -> {

            infoData.postValue(currentInfo);

        });


    }

    //add views and handling animations

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
    }

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

    }

    private void handleViewCollapsing() {

        if (clickedPosition == views.size()) {
            return;
        }
        expandCardView((MaterialCardView) views.get(clickedPosition));

    }


    private void expandCardView(MaterialCardView cardView) {

        animateExpandCollapseUsingWeight(cardView, CardViewState.EXPAND);

        if (clickedPosition != views.size() - 1) {
            views.get(clickedPosition).findViewById(R.id.majorLayout).setVisibility(View.VISIBLE);
            views.get(clickedPosition).findViewById(R.id.miniLayout).setVisibility(View.GONE);
        }

        collapseAllCardViews();

//        if (views.size() > clickedPosition + 1) {
//            animateExpandCollapseUsingWeight((MaterialCardView) views.get(clickedPosition + 1), null);
//        }
    }

    private void collapseAllCardViews() {

        if (clickedPosition == 0) {

            views.forEach(view -> {

                if (views.indexOf(view) == 1) {

                    animateExpandCollapseUsingWeight((MaterialCardView) view, CardViewState.COLLAPSE);
                    view.findViewById(R.id.majorLayout).setVisibility(View.GONE);
                    view.findViewById(R.id.miniLayout).setVisibility(View.VISIBLE);

                } else if (views.indexOf(view) == views.size() - 1) {

                    animateExpandCollapseUsingWeight((MaterialCardView) view, null);

                }

            });

        } else {

            views.get(views.size() - 1).setVisibility(View.VISIBLE);

            views.forEach(view -> {
                if (views.indexOf(view) <= clickedPosition + 1) {
                    if (views.indexOf(view) != clickedPosition) {
                        animateExpandCollapseUsingWeight((MaterialCardView) view, CardViewState.COLLAPSE);

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
    private void animateExpandCollapseUsingWeight(final MaterialCardView cardView, CardViewState state) {

        final float initialWeight = ((LinearLayout.LayoutParams) cardView.getLayoutParams()).weight;

        float finalWeight = 0f;

        if (state == CardViewState.COLLAPSE) {
            finalWeight = 1f;
        } else if (state == CardViewState.EXPAND) {
            finalWeight = 10f;
        } else {
            cardView.setVisibility(View.GONE);
        }

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialWeight, finalWeight);
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

    private static class StepValidation {

        boolean nameNumber = false;

        boolean amount = false;

        boolean pay = false;

        public boolean isNameNumber() {
            return nameNumber;
        }

        public void setNameNumber(boolean nameNumber) {
            this.nameNumber = nameNumber;
        }

        public boolean isAmount() {
            return amount;
        }

        public void setAmount(boolean amount) {
            this.amount = amount;
        }

        public boolean isPay() {
            return pay;
        }

        public void setPay(boolean pay) {
            this.pay = pay;
        }
    }

    private enum CardViewState {
        EXPAND,
        COLLAPSE
    }


}
