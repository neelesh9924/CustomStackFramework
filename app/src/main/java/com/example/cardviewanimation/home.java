package com.example.cardviewanimation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cardviewanimation.databinding.FragmentHomeBinding;

public class home extends Fragment {

    FragmentHomeBinding binding;

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

        BottomSheetFramework bottomSheetFramework = new BottomSheetFramework(home.this);

        binding.addButton.setOnClickListener(v -> {


        });

        bottomSheetFramework.getInfo().observe(getViewLifecycleOwner(), infoReturned -> {

//            Toast.makeText(getContext(), infoReturned.getName(), Toast.LENGTH_SHORT).show();

        });

    }
}