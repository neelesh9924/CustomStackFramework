package com.example.cardviewanimation;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cardviewanimation.databinding.FragmentHomeBinding;
import com.example.pojo.FilledDetails;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Random;

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


        binding.addButton.setOnClickListener(v -> {

            bottomSheetFragment = BottomSheetFragment.newInstance("RXS11304S");
            bottomSheetFragment.setListener(home.this);
            bottomSheetFragment.show(getParentFragmentManager(), "bottomSheetTag");

        });


    }

    @Override
    public void getSubmittedDetails(FilledDetails s) {

        StringBuilder formattedDetailsText = new StringBuilder("Name: " + s.getFirstName() + " " + s.getLastName() + "\nPhone: " + s.getPhone() + "\nEmail: " + s.getEmail() + "\nQuery: " + s.getQuery());
        if (s.getRemarks() != null && s.getRemarks().length() > 0) {
            formattedDetailsText.append("\nRemarks: ").append(s.getRemarks());
        }

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Submitted Details")
                .setMessage(formattedDetailsText)
                .setPositiveButton("Okay", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                }).show();

    }
}