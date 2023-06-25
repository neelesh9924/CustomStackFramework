# Download the APK
Click to download the apk for the project -
[app-release.apk](app-release.apk)

# StackFramework and CustomCardView
### Simplified Stack-Based View Management

The StackFramework is a utility class that facilitates the management and interaction of a stack of custom card views. It provides methods for creating and adding views, handling view actions, and notifying the host fragment of changes.

The CustomCardView class on the other hand is an extension of the MaterialCardView class that provides additional functionality for creating and managing a custom card view in a stack form.

## Installation
To use the framework in your Android project, follow these steps:
1. Add the StackFramework, StackItem and CustomCardView classes to your project.
2. Make sure you have the Material Card View library included in your project dependencies.

## Usage
### StackFramework
To start using the StackFramework, perform the following steps:
1. Create an instance of the StackFramework class:

   ```
   ArrayList<StackItem> stackItemsList = new ArrayList<>();
   stackItemsList.add(new StackItem(R.layout.main_layout1, R.layout.pre_layout1, R.layout.post_layout1, R.color.color1));
   stackItemsList.add(new StackItem(R.layout.main_layout2, R.layout.pre_layout2, R.layout.post_layout2, R.color.color2));
   stackItemsList.add(new StackItem(R.layout.main_layout3, R.layout.pre_layout3, R.layout.post_layout3, R.color.color3));
   stackItemsList.add(new StackItem(R.layout.main_layout4, R.layout.pre_layout4, R.layout.post_layout4, R.color.color4));

   stackFramework = new StackFramework(requireContext(), stackItemsList, this);
   ```
   In the above code, stackItemsList is an ArrayList of StackItem objects. Each StackItem represents a step in the stack and contains layout resources and colors associated with that step.

2. Call the createAndAddViews() method when you want to create the views:

   ```
   stackFramework.createAndAddViews();
   ```
   This method inflates the layouts, handles enabling/disabling and expand/collapse functionality, and creates a list of CustomCardView instances.

3. Implement the OnChangeListener interface in the host fragment:
   
   ```
   public class MyFragment extends Fragment implements StackFramework.OnChangeListener {
   
    @Override
    public void onViewAdded(ArrayList<CustomCardView> customCardViewsCreated) {
   
        customCardViewsCreated.forEach(customCardView -> binding.linearLayout.addView(customCardView));
        // Add the views to your desired layout

        // Initialize
        CustomCardView view1 = customCardViewsCreated.get(0);
        TextInputEditText firstNameEditText = view1.findViewById(R.id.firstNameTextInputEditText);

        // Add listeners
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
      }
   }
   ```

   The onViewAdded() method is called by the StackFramework when a view is created. In this method, you can access and customize the views as needed.

4. To handle the completion of the current step, call the setCompleted() method:

   ```
   stackFramework.setCompleted(view1, false);
   ```
   The above code marks the view1 as completed and updates the UI accordingly.

### CustomCardView
The CustomCardView class extends the MaterialCardView class and provides additional functionality for creating and managing a custom card view in a stack form. Here's how you can use it:

1. Create an instance of the CustomCardView class:
   
   ```
   CustomCardView customCardView = new CustomCardView(context);
   ```
2. Initialize the card view by calling the initCardView() method:

   ```
   customCardView.initCardView(stackItem);
   ```
   The stackItem parameter represents the data associated with the card view, including layout resources and colors.
3. Customize the card view as needed:
   You can customize the card view's elevation, radius, and minimum height by modifying the corresponding properties of the MaterialCardView class.

4. Expand or collapse the card view:
   To expand the card view and show the main content, call the expand() method:

   ```
   customCardView.expand();
   ```
   To collapse the card view and show either the pre-layout or post-layout, call the collapse() method:
   
   ```
   customCardView.collapse();
   ```
5. Set the completion status of the card view:
   To mark the card view as completed, call the setCompleted() method:

   ```
   customCardView.setCompleted(true);
   ```
   This method updates the UI to reflect the completion status.

6. Enable or disable the card view's expansion:
   To allow or disallow the card view to be expanded, call the setEnabledToExpand() method:

   ```
   customCardView.setEnabledToExpand(true);
   ```
   This method updates the UI to reflect the enabled or disabled state.

