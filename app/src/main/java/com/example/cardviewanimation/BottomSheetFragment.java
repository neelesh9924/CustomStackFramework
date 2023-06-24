package com.example.cardviewanimation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cardviewanimation.databinding.FragmentBottomSheetBinding;
import com.example.pojo.FilledDetails;
import com.example.pojo.StackItem;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class BottomSheetFragment extends BottomSheetDialogFragment implements StackFramework.OnChangeListener {

    FragmentBottomSheetBinding binding;
    BottomSheetListener listener;
    StackFramework stackFramework;

    String forId = "";

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

        //get arguments from the parent fragment
        Bundle args = getArguments();
        String forId = args.getString("forId");

        //example layouts added to stack items list
        ArrayList<StackItem> stackItemsList = new ArrayList<>();
        stackItemsList.add(new StackItem(R.layout.inflating_layout1, R.color.color1, "Basic Details"));
        stackItemsList.add(new StackItem(R.layout.inflating_layout2, R.color.color2, "Address Information"));
        stackItemsList.add(new StackItem(R.layout.inflating_layout3, R.color.color3, "Query and Issues"));
        stackItemsList.add(new StackItem(R.layout.inflating_layout4, R.color.color4, "Submit Details"));

        stackFramework = new StackFramework(requireContext(), stackItemsList, this);
        stackFramework.createAndAddViews();

    }

    @Override
    public void onViewAdded(ArrayList<CustomCardView> customCardViewsCreated) { //add the views to your desired layout

        customCardViewsCreated.forEach(customCardView -> binding.linearLayout.addView(customCardView));

        handleInflatedView(customCardViewsCreated); // add actions and listeners to the views

    }

    public void handleInflatedView(ArrayList<CustomCardView> customCardViewsCreated) { //handle the inflated views here, like setting the data to the views, setting the listeners etc

        //init
        CustomCardView infV1 = customCardViewsCreated.get(0);
        TextInputEditText firstNameEditText = infV1.findViewById(R.id.firstNameTextInputEditText);
        TextInputEditText lastNameEditText = infV1.findViewById(R.id.lastNameTextInputEditText);

        CustomCardView infV2 = customCardViewsCreated.get(1);
        TextInputEditText phoneEditText = infV2.findViewById(R.id.phoneTextInputEditText);
        TextInputEditText emailEditText = infV2.findViewById(R.id.emailTextInputEditText);

        CustomCardView infV3 = customCardViewsCreated.get(2);
        TextInputEditText queryEditText = infV3.findViewById(R.id.queryTextInputEditText);
        TextInputEditText remarksEditText = infV3.findViewById(R.id.remarksTextInputEditText);

        CustomCardView infV4 = customCardViewsCreated.get(3);
        MaterialButton submitButton = infV4.findViewById(R.id.submit_button);

        FilledDetails filledDetails = new FilledDetails();

        //adding listeners
        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filledDetails.setFirstName(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (filledDetails.getFirstName() == null || filledDetails.getFirstName().isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter first name first", Toast.LENGTH_SHORT).show();
                    firstNameEditText.requestFocus();
                } else {
                    filledDetails.setLastName(charSequence.toString().trim());
                    infV2.setIsActive(true); //todo: change color

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        phoneEditText.addTextChangedListener(new TextWatcher() { //similarly add listeners to other views
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filledDetails.setPhone(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (filledDetails.getPhone() == null || filledDetails.getPhone().isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter phone number first", Toast.LENGTH_SHORT).show();
                    phoneEditText.requestFocus();
                } else if (filledDetails.getPhone().length() != 10) {
                    Toast.makeText(requireContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                    phoneEditText.requestFocus();
                } else {
                    filledDetails.setEmail(charSequence.toString().trim());
                    infV3.setIsActive(true); //todo: change color
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        queryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                filledDetails.setQuery(charSequence.toString().trim());

                if (filledDetails.getQuery() != null && filledDetails.getQuery().length() > 0) {
                    infV4.setIsActive(true); //todo: change color
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        remarksEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filledDetails.setRemarks(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        submitButton.setOnClickListener(view -> {

            submitDetails(filledDetails);


        });


    }

    private void submitDetails(FilledDetails filledDetails) {

        listener.getSubmittedDetails(filledDetails);

        dismiss();

    }

    public interface BottomSheetListener { //interface to send back the data to the parent fragment
        void getSubmittedDetails(FilledDetails submittedDetails);
    }


    //setting the behaviour of the bottom sheet
    private void setBottomSheetBehaviour(View view) {

        int topPaddingInDp = 80;
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