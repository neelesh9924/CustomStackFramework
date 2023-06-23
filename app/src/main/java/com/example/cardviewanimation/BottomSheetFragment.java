package com.example.cardviewanimation;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cardviewanimation.databinding.FragmentBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    FragmentBottomSheetBinding binding;

    BottomSheetListener listener;

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    public BottomSheetFragment(BottomSheetListener listener) {
        this.listener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setBottomSheetBehaviour(view);

        StackFramework stackFramework = new StackFramework(this);

        stackFramework.getInfo().observe(getViewLifecycleOwner(), infoReturned -> {


        });


        //send back a pojo of the filled data to the parent fragment


    }

    private void setBottomSheetBehaviour(View view) { //setting the top offset and various behaviours of the bottom sheet

        int topPaddingInDp = 180;
        ((View) view.getParent()).getLayoutParams().height = getResources().getDisplayMetrics().heightPixels - Math.round(getResources().getDisplayMetrics().density * topPaddingInDp);

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) view.getParent());
        behavior.setPeekHeight(BottomSheetBehavior.SAVE_PEEK_HEIGHT);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.setSkipCollapsed(true);
        behavior.setHideable(false);
        behavior.setDraggable(false);


    }

    public interface BottomSheetListener { //interface to send back the data to the parent fragment
        void getSubmittedDetails(String text);
    }

    @Override
    public int getTheme() {
        return R.style.CustomBottomSheetDialogTheme;
    }
}