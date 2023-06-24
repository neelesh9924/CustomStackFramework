package com.example.cardviewanimation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cardviewanimation.databinding.FragmentHomeBinding;
import com.example.pojo.FilledDetails;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class home extends Fragment implements BottomSheetFragment.BottomSheetListener {

    FragmentHomeBinding binding;

    BottomSheetFragment bottomSheetFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.button1.setOnClickListener(v -> openBottomSheet("1"));

        binding.button2.setOnClickListener(v -> openBottomSheet("2"));

        binding.button3.setOnClickListener(v -> openBottomSheet("3"));

        binding.button4.setOnClickListener(v -> openBottomSheet("4"));

    }

    public void openBottomSheet(String eventId) { //Open BottomSheetFragment
        if (bottomSheetFragment != null && bottomSheetFragment.isVisible()) {
            return;
        }
        bottomSheetFragment = BottomSheetFragment.newInstance(eventId);
        bottomSheetFragment.setListener(home.this);
        bottomSheetFragment.show(getParentFragmentManager(), "bottomSheetTag");
    }

    @Override
    public void getSubmittedDetails(FilledDetails s) { //Submitted Details received from BottomSheetFragment

        StringBuilder formattedDetailsText = new StringBuilder();
        formattedDetailsText.append("Submitted Details-\n");
        formattedDetailsText.append("\nEvent ID: ").append(s.getEventId());
        switch (s.getEventId()) {
            case "1":
                formattedDetailsText.append("\nEvent Name: ").append("Event A");
                break;
            case "2":
                formattedDetailsText.append("\nEvent Name: ").append("Event B");
                break;
            case "3":
                formattedDetailsText.append("\nEvent Name: ").append("Event C");
                break;
            case "4":
                formattedDetailsText.append("\nEvent Name: ").append("Event D");
                break;
        }
        formattedDetailsText.append("\nName: ").append(s.getFirstName()).append(" ").append(s.getLastName());
        formattedDetailsText.append("\nPhone: ").append(s.getPhone());
        formattedDetailsText.append("\nEmail: ").append(s.getEmail());
        formattedDetailsText.append("\nQuery: ").append(s.getQuery());
        if (s.getRemarks() != null && s.getRemarks().length() > 0) {
            formattedDetailsText.append("\nRemarks: ").append(s.getRemarks());
        }

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Submitted Details")
                .setMessage(formattedDetailsText)
                .setPositiveButton("Okay", (dialogInterface, i) -> dialogInterface.dismiss()).show();

    }
}