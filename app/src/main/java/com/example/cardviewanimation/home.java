package com.example.cardviewanimation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cardviewanimation.databinding.FragmentHomeBinding;

public class home extends Fragment implements BottomSheetFragment.BottomSheetListener {

    FragmentHomeBinding binding;

    BottomSheetFragment bottomSheetFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomSheetFragment = new BottomSheetFragment();

        binding.addButton.setOnClickListener(v -> {

            bottomSheetFragment.show(getParentFragmentManager(), "bottomSheetTag");

        });


    }

    @Override
    public void getSubmittedDetails(String text) {


    }
}