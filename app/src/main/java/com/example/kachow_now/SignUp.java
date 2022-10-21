package com.example.kachow_now;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.AddressComponents;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;


public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference database;

    private EditText AddressField;

    private final ActivityResultLauncher<Intent> startAutocomplete = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (ActivityResultCallback<ActivityResult>) result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Place place = Autocomplete.getPlaceFromIntent(intent);

                        // Write a method to read the address components from the Place
                        // and populate the form with the address components
                        fillInAddress(place);
                    }
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    // The user canceled the operation.

                }
            });

    private void startAutocompleteIntent() {

        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS_COMPONENTS,
                Place.Field.LAT_LNG, Place.Field.VIEWPORT);

        // Build the autocomplete intent with field, country, and type filters applied
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("CA")
                .setTypeFilter(TypeFilter.ADDRESS)
                .build(this);
        startAutocomplete.launch(intent);
    }

    private void fillInAddress(Place place) {
        AddressComponents components = place.getAddressComponents();
        StringBuilder address1 = new StringBuilder();

        // Get each component of the address from the place details,
        // and then fill-in the corresponding field on the form.
        // Possible AddressComponent types are documented at https://goo.gle/32SJPM1
        if (components != null) {
            for (AddressComponent component : components.asList()) {
                String type = component.getTypes().get(0);
                switch (type) {
                    case "street_number": {
                        address1.insert(0, component.getName());
                        break;
                    }

                    case "route": {
                        address1.append(" ");
                        address1.append(component.getShortName());
                        break;
                    }

                }
            }
        }

        AddressField.setText(address1.toString());

        // After filling the form with address components from the Autocomplete
        // prediction, set cursor focus on the second address line to encourage
        // entry of sub-premise information such as apartment, unit, or floor number.

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database = FirebaseDatabase.getInstance().getReference("UID");
        mAuth = FirebaseAuth.getInstance();
        AddressField = (EditText) findViewById(R.id.SignupAddress);


        Button register = (Button)findViewById(R.id.RegisterButton);
        Spinner spin = (Spinner) findViewById(R.id.SignupRole);
        EditText AccountOrCardNumber = (EditText) findViewById(R.id.AccountOrCardNumber);
        EditText CCVorInstitution = (EditText) findViewById(R.id.CCVorInstitution);
        TextView BranchNumberorExpiry = (TextView) findViewById(R.id.BranchNumberorExpiry);
        EditText MonthOrBranchNumber = (EditText) findViewById(R.id.MonthOrBranchNumber);
        EditText Year = (EditText) findViewById(R.id.Year);


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String textFromSpinner =String.valueOf(spin.getSelectedItem()).trim().toLowerCase();
                if (textFromSpinner.equals("cook")){
                    AccountOrCardNumber.setVisibility(View.VISIBLE);
                    CCVorInstitution.setVisibility(View.VISIBLE);
                    BranchNumberorExpiry.setVisibility(View.GONE);
                    MonthOrBranchNumber.setVisibility(View.VISIBLE);
                    Year.setVisibility(View.GONE);
                    AccountOrCardNumber.setHint("Account Number");
                    AccountOrCardNumber.setFilters(new InputFilter[] {new InputFilter.LengthFilter(12)});
                    MonthOrBranchNumber.setHint("Branch Number");
                    MonthOrBranchNumber.setFilters(new InputFilter[] {new InputFilter.LengthFilter(5)});
                    CCVorInstitution.setHint("Institution Number");
                    CCVorInstitution.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});

                } else if (textFromSpinner.equals("client")){
                    AccountOrCardNumber.setVisibility(View.VISIBLE);
                    CCVorInstitution.setVisibility(View.VISIBLE);
                    BranchNumberorExpiry.setVisibility(View.VISIBLE);
                    MonthOrBranchNumber.setVisibility(View.VISIBLE);
                    Year.setVisibility(View.VISIBLE);
                    AccountOrCardNumber.setHint("Card Number");
                    AccountOrCardNumber.setFilters(new InputFilter[] {new InputFilter.LengthFilter(16)});
                    MonthOrBranchNumber.setHint("Month");
                    MonthOrBranchNumber.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                    CCVorInstitution.setHint("CCV");
                    CCVorInstitution.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});
                    Year.setHint("Year");
                    Year.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                }
                else{
                    AccountOrCardNumber.setVisibility(View.INVISIBLE);
                    CCVorInstitution.setVisibility(View.INVISIBLE);
                    BranchNumberorExpiry.setVisibility(View.INVISIBLE);
                    MonthOrBranchNumber.setVisibility(View.INVISIBLE);
                    Year.setVisibility(View.INVISIBLE);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(v);
            }
        });
    }

    private void createAccount(View view) {

        try {
            String Type = String.valueOf(((Spinner) findViewById(R.id.SignupRole)).getSelectedItem()).trim().toLowerCase();
            String FirstName = ((EditText) findViewById(R.id.SignupFirstName)).getText().toString().trim();
            String Surname = ((EditText) findViewById(R.id.SignupLastName)).getText().toString().trim();
            String firstPhone = ((EditText) findViewById(R.id.firstPhone)).getText().toString().trim();
            String secondPhone = ((EditText) findViewById(R.id.secondPhone)).getText().toString().trim();
            String thirdPhone = ((EditText) findViewById(R.id.thirdPhone)).getText().toString().trim();
            String Phone = firstPhone + secondPhone + thirdPhone;
            String Email = ((EditText) findViewById(R.id.SignupEmail)).getText().toString().trim();
            String Password = ((EditText) findViewById(R.id.SignupPassword)).getText().toString().trim();
            String AccountOrCardNumber = ((EditText) findViewById(R.id.AccountOrCardNumber)).getText().toString().trim();
            String CCVorInstitution = ((EditText) findViewById(R.id.CCVorInstitution)).getText().toString().trim();
            String BranchOrMonth = ((EditText) findViewById(R.id.MonthOrBranchNumber)).getText().toString().trim();
            String address = ((EditText) findViewById(R.id.SignupAddress)).getText().toString().trim();
            String postalcode1 = ((EditText) findViewById(R.id.pc1)).getText().toString().trim();
            String postalcode2 = ((EditText) findViewById(R.id.pc2)).getText().toString().trim();
            String postalcode = postalcode1 + postalcode2;
            String Year;



            if (Type.equals("client")) {
                Year = ((EditText) findViewById(R.id.Year)).getText().toString().trim();
            } else {
                Year = null;
            }

            //exception handling

            if (FirstName.isEmpty() || Surname.isEmpty() || Email.isEmpty() || Phone.isEmpty() ||
                    Password.isEmpty() || AccountOrCardNumber.isEmpty() || BranchOrMonth.isEmpty() ||
                    CCVorInstitution.isEmpty() || address.isEmpty() || postalcode.isEmpty() ||(Type.equals("client") && Year.isEmpty())) {
                throw new IllegalArgumentException();
            }
            if (Phone.length() != 10 || postalcode.length() != 6){
                throw new NumberFormatException();
            }
            if (Type.equals("client") && (AccountOrCardNumber.length() != 16 || CCVorInstitution.length() != 3 ||
                    Integer.parseInt(BranchOrMonth) >12 || Integer.parseInt(BranchOrMonth) < 1 || Integer.parseInt(Year) < 22 || Integer.parseInt(Year) >99)){
                throw new NumberFormatException();
            }
            if (Type.equals("cook") && (AccountOrCardNumber.length() > 12 || AccountOrCardNumber.length() <7 || CCVorInstitution.length() != 3 ||
                    BranchOrMonth.length() != 5)){
                throw new NumberFormatException();
            }

            mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        FirebaseUser usr = mAuth.getCurrentUser();
                        if (Type.equals("client")) {
                            Client u = new Client(Password, FirstName, Surname, Email,
                                    Long.parseLong(AccountOrCardNumber), Integer.parseInt(BranchOrMonth),
                                    Integer.parseInt(Year), Integer.parseInt(CCVorInstitution),
                                    address, postalcode ,Long.parseLong(Phone));
                            database.child(String.valueOf(mAuth.getCurrentUser().getUid())).setValue(u);
                            database.child(String.valueOf(mAuth.getCurrentUser().getUid())).child("role").setValue("Client");
                        }else{
                            Cook u = new Cook( FirstName, Surname,Password, Email,address, postalcode,
                                    Long.parseLong(Phone), Integer.parseInt(BranchOrMonth),Integer.parseInt(CCVorInstitution),
                                    Integer.parseInt(AccountOrCardNumber));
                            database.child(String.valueOf(mAuth.getCurrentUser().getUid())).setValue(u);
                            database.child(String.valueOf(mAuth.getCurrentUser().getUid())).child("role").setValue("Cook");
                        }
                        Toast.makeText(SignUp.this,"Registration Successful",Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(SignUp.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                        Toast.makeText(SignUp.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        catch (NumberFormatException e){
            Toast.makeText(SignUp.this, "Please Enter Valid Input", Toast.LENGTH_LONG).show();
        }
        catch (IllegalArgumentException e){
            Toast.makeText(SignUp.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
        }

    }
}