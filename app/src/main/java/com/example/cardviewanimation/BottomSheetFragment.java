package com.example.cardviewanimation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardviewanimation.databinding.FragmentBottomSheetBinding;
import com.example.customViews.CustomCardView;
import com.example.frameworks.StackFramework;
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

    String eventId = "";
    FilledDetails filledDetails;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        if (args != null) {
            eventId = args.getString("forId");
        }

        filledDetails = new FilledDetails();
        filledDetails.setEventId(eventId);

        //todo: example layouts added to stack items list, change them to your desired layouts
        //format of stack item: (your layout on expanded), (layout before step isn't reached yet), (layout after a step is been reached), (color of the step)

        ArrayList<StackItem> stackItemsList = new ArrayList<>();
        stackItemsList.add(new StackItem(R.layout.main_layout1, R.layout.pre_layout1, R.layout.post_layout1, R.color.color1));
        stackItemsList.add(new StackItem(R.layout.main_layout2, R.layout.pre_layout2, R.layout.post_layout2, R.color.color2));
        stackItemsList.add(new StackItem(R.layout.main_layout3, R.layout.pre_layout3, R.layout.post_layout3, R.color.color3));
        stackItemsList.add(new StackItem(R.layout.main_layout4, R.layout.pre_layout4, R.layout.post_layout4, R.color.color4));

        stackFramework = new StackFramework(requireContext(), stackItemsList, this); //setting up the framework

        stackFramework.createAndAddViews(); //call when you want to create the views

    }

    @Override
    public void onViewAdded(ArrayList<CustomCardView> customCardViewsCreated) { //response from the framework when a view are created

        customCardViewsCreated.forEach(customCardView -> binding.linearLayout.addView(customCardView)); //todo: add the views to your desired layout

        handleInflatedView(customCardViewsCreated);

    }

    @SuppressLint("SetTextI18n")
    public void handleInflatedView(ArrayList<CustomCardView> customCardViewsCreated) { //todo: handle your inflated views here, adding the data to the views, setting the listeners etc.

        //init
        CustomCardView view1 = customCardViewsCreated.get(0);
        TextInputEditText firstNameEditText = view1.findViewById(R.id.firstNameTextInputEditText);
        TextInputEditText lastNameEditText = view1.findViewById(R.id.lastNameTextInputEditText);
        TextView firstNameTextView = view1.findViewById(R.id.firstNameTextView);
        TextView lastNameTextView = view1.findViewById(R.id.lastNameTextView);

        CustomCardView view2 = customCardViewsCreated.get(1);
        TextInputEditText phoneEditText = view2.findViewById(R.id.phoneTextInputEditText);
        TextInputEditText emailEditText = view2.findViewById(R.id.emailTextInputEditText);
        TextView phoneTextView = view2.findViewById(R.id.phoneTextView);
        TextView emailTextView = view2.findViewById(R.id.emailTextView);

        CustomCardView view3 = customCardViewsCreated.get(2);
        TextInputEditText queryEditText = view3.findViewById(R.id.queryTextInputEditText);
        TextInputEditText remarksEditText = view3.findViewById(R.id.remarksTextInputEditText);
        TextView queryTextView = view3.findViewById(R.id.queryTextView);
        TextView remarksTextView = view3.findViewById(R.id.remarksTextView);

        CustomCardView view4 = customCardViewsCreated.get(3);
        MaterialButton submitButton = view4.findViewById(R.id.submit_button);
        TextView submitTextView = view4.findViewById(R.id.submitTextView);

        //adding listeners
        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filledDetails.setFirstName(charSequence.toString().trim());
                firstNameTextView.setText(charSequence.toString().trim());
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
                } else if (!charSequence.toString().trim().isEmpty()) {
                    filledDetails.setLastName(charSequence.toString().trim());
                    lastNameTextView.setText(charSequence.toString().trim());
                    stackFramework.setCompleted(view1, true);
                } else {
                    filledDetails.setLastName(null);
                    lastNameTextView.setText(null);
                    stackFramework.setCompleted(view1, false);
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
                phoneTextView.setText(charSequence.toString().trim());
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
                } else if (!charSequence.toString().trim().isEmpty()) {
                    filledDetails.setEmail(charSequence.toString().trim());
                    emailTextView.setText(charSequence.toString().trim());
                    stackFramework.setCompleted(view2, true);
                } else {
                    filledDetails.setEmail(null);
                    emailTextView.setText(null);
                    stackFramework.setCompleted(view2, false);
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
                queryTextView.setText(charSequence.toString().trim());

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
                if (filledDetails.getQuery() == null || filledDetails.getQuery().isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter query first", Toast.LENGTH_SHORT).show();
                    queryEditText.requestFocus();
                } else if (!charSequence.toString().trim().isEmpty()) {
                    filledDetails.setRemarks(charSequence.toString().trim());
                    remarksTextView.setText(charSequence.toString().trim());
                    stackFramework.setCompleted(view3, true);
                } else {
                    filledDetails.setRemarks(null);
                    remarksTextView.setText(null);
                    stackFramework.setCompleted(view3, false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        submitTextView.setText("Submit the form for the event id: " + eventId);

        submitButton.setOnClickListener(view -> { //send the data back to the parent fragment and dismiss the bottom sheet

            listener.getSubmittedDetails(filledDetails);
            dismiss();


        });


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