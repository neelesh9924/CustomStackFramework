package com.example.cardviewanimation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.cardviewanimation.databinding.FragmentBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetFragment extends BottomSheetDialogFragment implements StackFramework.OnChangeListener {

    FragmentBottomSheetBinding binding;
    BottomSheetListener listener;

    StackFramework stackFramework;

    public BottomSheetFragment() {

    }

    public static BottomSheetFragment newInstance(String forId) {

        BottomSheetFragment fragment = new BottomSheetFragment();
        Bundle args = new Bundle();
        args.putString("forId", forId);
        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(BottomSheetListener listener) {
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

        //example layout ids
        ArrayList<StackItem> stackItemsList = new ArrayList<>();
        stackItemsList.add(new StackItem(R.layout.inflating_layout1, R.color.color1, "1"));
        stackItemsList.add(new StackItem(R.layout.inflating_layout2, R.color.color2, "2"));
        stackItemsList.add(new StackItem(R.layout.inflating_layout3, R.color.color3, "3"));
        stackItemsList.add(new StackItem(R.layout.inflating_layout4, R.color.color4, "4"));


        stackFramework = new StackFramework(requireContext(), stackItemsList, this);

        stackFramework.createAndAddViews();

        //get arguments from the parent fragment

        Bundle args = getArguments();
        String forId = args.getString("forId");

        listener.getSubmittedDetails(forId);


        //send back a pojo of the filled data to the parent fragment


    }

    @Override
    public void onViewAdded(ArrayList<CustomCardView> views) {

        //add the views to the stack framework
        for (View view : views) {
            binding.linearLayout.addView(view);
        }


    }

    public interface BottomSheetListener { //interface to send back the data to the parent fragment
        void getSubmittedDetails(String text);
    }


    //bottom sheet behaviour
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

    @Override
    public int getTheme() {
        return R.style.CustomBottomSheetDialogTheme;
    }
}