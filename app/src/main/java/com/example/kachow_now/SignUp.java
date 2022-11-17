package com.example.kachow_now;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class    SignUp extends AppCompatActivity {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 22;
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private String address;

    private EditText AddressField;


    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    private Button uploadBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database = FirebaseDatabase.getInstance().getReference("UID");
        mAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        uploadBtn = findViewById(R.id.UploadItemPicture);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        AddressField = findViewById(R.id.Allergens);

        String apiKey = getString(R.string.api_key);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        AddressField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the fields to specify which types of place data to
                // return after the user has made a selection.
                List<Place.Field> field = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, field)
                        .build(SignUp.this);
                //start activity result
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

            }

        });


        Button register = findViewById(R.id.AddNewItemButton);
        Spinner spin = findViewById(R.id.SignupRole);
        EditText AccountOrCardNumber = findViewById(R.id.ServingSize);
        EditText CCVorInstitution = findViewById(R.id.Calories);
        TextView BranchNumberorExpiry = findViewById(R.id.BranchNumberorExpiry);
        EditText MonthOrBranchNumber = findViewById(R.id.MonthOrBranchNumber);
        EditText Year = findViewById(R.id.Year);
        EditText postalcode1 = findViewById(R.id.ItemDescription);
        EditText postalcode2 = findViewById(R.id.pc2);
        EditText firstPhone = findViewById(R.id.IngredientsList);
        EditText secondPhone = findViewById(R.id.secondPhone);
        EditText thirdPhone = findViewById(R.id.thirdPhone);

        postalcode1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (postalcode1.getText().toString().length() == 3)     //size as per your requirement
                {
                    postalcode2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void afterTextChanged(Editable s) {
            }

        });

        firstPhone.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count) {
                if(firstPhone.getText().toString().length()==3)     //size as per your requirement
                {
                    secondPhone.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        secondPhone.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count) {
                if(secondPhone.getText().toString().length()==3)     //size as per your requirement
                {
                    thirdPhone.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //When success initialize place
                Place place = Autocomplete.getPlaceFromIntent(data);
                //set address on edittext
                AddressField.setText(place.getAddress());

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                //Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {

                // The user canceled the operation.
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), filePath);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Catch the exception
            }
        }
    }

    private void createAccount(View view) {

        try {
            String Type = String.valueOf(((Spinner) findViewById(R.id.SignupRole)).getSelectedItem()).trim().toLowerCase();
            String FirstName = ((EditText) findViewById(R.id.ItemName)).getText().toString().trim();
            String Surname = ((EditText) findViewById(R.id.MealType)).getText().toString().trim();
            String firstPhone = ((EditText) findViewById(R.id.IngredientsList)).getText().toString().trim();
            String secondPhone = ((EditText) findViewById(R.id.secondPhone)).getText().toString().trim();
            String thirdPhone = ((EditText) findViewById(R.id.thirdPhone)).getText().toString().trim();
            String Phone = firstPhone + secondPhone + thirdPhone;
            String Email = ((EditText) findViewById(R.id.CusineType)).getText().toString().trim();
            String Password = ((EditText) findViewById(R.id.Price)).getText().toString().trim();
            String AccountOrCardNumber = ((EditText) findViewById(R.id.ServingSize)).getText().toString().trim();
            String CCVorInstitution = ((EditText) findViewById(R.id.Calories)).getText().toString().trim();
            String BranchOrMonth = ((EditText) findViewById(R.id.MonthOrBranchNumber)).getText().toString().trim();
            String address = ((EditText) findViewById(R.id.Allergens)).getText().toString().trim();
            String postalcode1 = ((EditText) findViewById(R.id.ItemDescription)).getText().toString().trim();
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
                    CCVorInstitution.isEmpty() || address.isEmpty() || postalcode.isEmpty() || (Type.equals("client") && Year.isEmpty())) {
                throw new IllegalArgumentException();
            }
            if (Phone.length() != 10 || postalcode.length() != 6) {
                throw new NumberFormatException();
            }
            if (Type.equals("client") && ((AccountOrCardNumber.length() != 16 || CCVorInstitution.length() != 3 ||
                    Integer.parseInt(BranchOrMonth) > 12 || Integer.parseInt(BranchOrMonth) < 1 || Integer.parseInt(Year) < 22 || Integer.parseInt(Year) > 99))) {
                throw new NumberFormatException();
            }
            if (Type.equals("cook") && (AccountOrCardNumber.length() > 12 || AccountOrCardNumber.length() < 7 || CCVorInstitution.length() != 3 ||
                    BranchOrMonth.length() != 5)) {
                throw new NumberFormatException();
            }

            if (Type.equals("cook") && filePath == null) {
                throw new NullPointerException();
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
                            u.setUID(mAuth.getCurrentUser().getUid());
                            database.child(mAuth.getCurrentUser().getUid()).setValue(u);
                            //database.child(String.valueOf(mAuth.getCurrentUser().getUid())).child("role").setValue("Client");
                        }else{
                            Cook u = new Cook(FirstName, Surname, Password, Email, address, postalcode,
                                    Long.parseLong(Phone), Integer.parseInt(BranchOrMonth), Integer.parseInt(CCVorInstitution),
                                    Double.parseDouble(AccountOrCardNumber));
                            u.setUID(mAuth.getCurrentUser().getUid());
                            database.child(mAuth.getCurrentUser().getUid()).setValue(u);
                            //database.child(String.valueOf(mAuth.getCurrentUser().getUid())).child("role").setValue("Cook");
                        }
                        uploadImage();
                        Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(SignUp.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                        Toast.makeText(SignUp.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (NumberFormatException e) {
            Toast.makeText(SignUp.this, e + " Please Enter Valid Input", Toast.LENGTH_LONG).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(SignUp.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
        } catch (NullPointerException e) {
            Toast.makeText(SignUp.this, "Please add an image", Toast.LENGTH_LONG).show();
        }

    }

    private void SelectImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {
        if (filePath != null) {
            // Code for showing progressDialog while uploading

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/" + (mAuth.getCurrentUser()).getUid() + "/profilePhoto");

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Dismiss dialog

                    //Toast.makeText(SignUp.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    // Error, Image not uploaded

                    Toast.makeText(SignUp.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}