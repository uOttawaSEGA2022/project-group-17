package com.example.kachow_now;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kachow_now.R.id;
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
import com.google.firebase.storage.UploadTask.TaskSnapshot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class SignUp extends AppCompatActivity {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 22;
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private String address;

    private EditText AddressField;


    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    private byte[] fileInBytes;
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
        EditText postalcode1 = findViewById(R.id.Description);
        EditText postalcode2 = findViewById(R.id.pc2);
        EditText firstPhone = findViewById(R.id.IngredientsList);
        EditText secondPhone = findViewById(R.id.secondPhone);
        EditText thirdPhone = findViewById(R.id.thirdPhone);

        //spin.setBackgroundColor(1);


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

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (firstPhone.getText().toString().length() == 3)     //size as per your requirement
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

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                if (secondPhone.getText().toString().length() == 3)     //size as per your requirement
                    thirdPhone.requestFocus();
            }

            @Override
            public void beforeTextChanged(final CharSequence s, final int start,
                                          final int count, final int after) {
            }

            @Override
            public void afterTextChanged(final Editable s) {
            }

        });


        spin.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view, final int pos, final long id) {
                final String textFromSpinner = String.valueOf(spin.getSelectedItem()).trim().toLowerCase();
                if (textFromSpinner.equals("cook")) {
                    AccountOrCardNumber.setVisibility(View.VISIBLE);
                    CCVorInstitution.setVisibility(View.VISIBLE);
                    BranchNumberorExpiry.setVisibility(View.GONE);
                    MonthOrBranchNumber.setVisibility(View.VISIBLE);
                    Year.setVisibility(View.GONE);
                    AccountOrCardNumber.setHint("Account Number");
                    AccountOrCardNumber.setFilters(new InputFilter[]{new LengthFilter(12)});
                    MonthOrBranchNumber.setHint("Branch Number");
                    MonthOrBranchNumber.setFilters(new InputFilter[]{new LengthFilter(5)});
                    CCVorInstitution.setHint("Institution Number");
                    CCVorInstitution.setFilters(new InputFilter[]{new LengthFilter(3)});

                } else if (textFromSpinner.equals("client")) {
                    AccountOrCardNumber.setVisibility(View.VISIBLE);
                    CCVorInstitution.setVisibility(View.VISIBLE);
                    BranchNumberorExpiry.setVisibility(View.VISIBLE);
                    MonthOrBranchNumber.setVisibility(View.VISIBLE);
                    Year.setVisibility(View.VISIBLE);
                    AccountOrCardNumber.setHint("Card Number");
                    AccountOrCardNumber.setFilters(new InputFilter[]{new LengthFilter(16)});
                    MonthOrBranchNumber.setHint("Month");
                    MonthOrBranchNumber.setFilters(new InputFilter[]{new LengthFilter(2)});
                    CCVorInstitution.setHint("CCV");
                    CCVorInstitution.setFilters(new InputFilter[]{new LengthFilter(3)});
                    Year.setHint("Year");
                    Year.setFilters(new InputFilter[]{new LengthFilter(2)});
                } else {
                    AccountOrCardNumber.setVisibility(View.INVISIBLE);
                    CCVorInstitution.setVisibility(View.INVISIBLE);
                    BranchNumberorExpiry.setVisibility(View.INVISIBLE);
                    MonthOrBranchNumber.setVisibility(View.INVISIBLE);
                    Year.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {

            }
        });

        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                SignUp.this.createAccount(v);
            }
        });

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == SignUp.AUTOCOMPLETE_REQUEST_CODE) if (resultCode == Activity.RESULT_OK) {
            //When success initialize place
            final Place place = Autocomplete.getPlaceFromIntent(data);
            //set address on edittext
            this.AddressField.setText(place.getAddress());

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            final Status status = Autocomplete.getStatusFromIntent(data);
            //Log.i(TAG, status.getStatusMessage());
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // The user canceled the operation.
        }

        if (requestCode == SignUp.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {

            // Get the Uri of data
            this.filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                final Bitmap bitmap = Media.getBitmap(
                        this.getContentResolver(), this.filePath);
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.JPEG, 0, baos);
                this.fileInBytes = baos.toByteArray();
                //imageView.setImageBitmap(bitmap);
            } catch (final IOException e) {
                // Catch the exception
            }
        }
    }

    private void createAccount(final View view) {

        try {
            final String Type = String.valueOf(((Spinner) this.findViewById(id.SignupRole)).getSelectedItem()).trim().toLowerCase();
            final String FirstName = ((EditText) this.findViewById(id.ItemName)).getText().toString().trim();
            final String Surname = ((EditText) this.findViewById(id.MealType)).getText().toString().trim();
            final String firstPhone = ((EditText) this.findViewById(id.IngredientsList)).getText().toString().trim();
            final String secondPhone = ((EditText) this.findViewById(id.secondPhone)).getText().toString().trim();
            final String thirdPhone = ((EditText) this.findViewById(id.thirdPhone)).getText().toString().trim();
            final String Phone = firstPhone + secondPhone + thirdPhone;
            final String Email = ((EditText) this.findViewById(id.CusineType)).getText().toString().trim();
            final String Password = ((EditText) this.findViewById(id.Price)).getText().toString().trim();
            final String AccountOrCardNumber = ((EditText) this.findViewById(id.ServingSize)).getText().toString().trim();
            final String CCVorInstitution = ((EditText) this.findViewById(id.Calories)).getText().toString().trim();
            final String BranchOrMonth = ((EditText) this.findViewById(id.MonthOrBranchNumber)).getText().toString().trim();
            final String address = ((EditText) this.findViewById(id.Allergens)).getText().toString().trim();
            final String postalcode1 = ((EditText) this.findViewById(id.Description)).getText().toString().trim();
            final String postalcode2 = ((EditText) this.findViewById(id.pc2)).getText().toString().trim();
            final String postalcode = postalcode1 + postalcode2;
            final String Year;


            if (Type.equals("client"))
                Year = ((EditText) this.findViewById(id.Year)).getText().toString().trim();
            else Year = null;

            //exception handling

            if (FirstName.isEmpty() || Surname.isEmpty() || Email.isEmpty() || Phone.isEmpty() ||
                    Password.isEmpty() || AccountOrCardNumber.isEmpty() || BranchOrMonth.isEmpty() ||
                    CCVorInstitution.isEmpty() || address.isEmpty() || postalcode.isEmpty() || Type.equals("client") && Year.isEmpty())
                throw new IllegalArgumentException();
            if (Phone.length() != 10 || postalcode.length() != 6) throw new NumberFormatException();
            if (Type.equals("client") && (AccountOrCardNumber.length() != 16 || CCVorInstitution.length() != 3 ||
                    Integer.parseInt(BranchOrMonth) > 12 || Integer.parseInt(BranchOrMonth) < 1 || Integer.parseInt(Year) < 22 || Integer.parseInt(Year) > 99))
                throw new NumberFormatException();
            if (Type.equals("cook") && (AccountOrCardNumber.length() > 12 || AccountOrCardNumber.length() < 7 || CCVorInstitution.length() != 3 ||
                    BranchOrMonth.length() != 5)) throw new NumberFormatException();

            if (Type.equals("cook") && this.filePath == null) throw new NullPointerException();

            this.mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull final Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        final FirebaseUser usr = SignUp.this.mAuth.getCurrentUser();
                        if (Type.equals("client")) {
                            final Client u = new Client(Password, FirstName, Surname, Email,
                                    Long.parseLong(AccountOrCardNumber), Integer.parseInt(BranchOrMonth),
                                    Integer.parseInt(Year), Integer.parseInt(CCVorInstitution),
                                    address, postalcode, Long.parseLong(Phone));
                            u.setUID(SignUp.this.mAuth.getCurrentUser().getUid());
                            SignUp.this.database.child(SignUp.this.mAuth.getCurrentUser().getUid()).setValue(u);
                            //database.child(String.valueOf(mAuth.getCurrentUser().getUid())).child("role").setValue("Client");
                        } else {
                            final Cook u = new Cook(FirstName, Surname, Password, Email, address, postalcode,
                                    Long.parseLong(Phone), Integer.parseInt(BranchOrMonth), Integer.parseInt(CCVorInstitution),
                                    Double.parseDouble(AccountOrCardNumber));
                            u.setUID(SignUp.this.mAuth.getCurrentUser().getUid());
                            SignUp.this.database.child(SignUp.this.mAuth.getCurrentUser().getUid()).setValue(u);
                            //database.child(String.valueOf(mAuth.getCurrentUser().getUid())).child("role").setValue("Cook");
                        }
                        SignUp.this.uploadImage();
                        Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_LONG).show();
                        SignUp.this.finish();
                    } else {
                        Toast.makeText(SignUp.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                        Toast.makeText(SignUp.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (final NumberFormatException e) {
            Toast.makeText(this, e + " Please Enter Valid Input", Toast.LENGTH_LONG).show();
        } catch (final IllegalArgumentException e) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show();
        } catch (final NullPointerException e) {
            Toast.makeText(this, "Please add an image", Toast.LENGTH_LONG).show();
        }

    }

    private void SelectImage() {
        // Defining Implicit Intent to mobile gallery
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), SignUp.PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {
        if (this.filePath != null) {
            // Code for showing progressDialog while uploading

            // Defining the child of storageReference
            final StorageReference ref = this.storageReference.child("images/" + this.mAuth.getCurrentUser().getUid() + "/profilePhoto");

            // adding listeners on upload
            // or failure of image
            ref.putBytes(this.fileInBytes).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
                @Override
                public void onSuccess(final TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Dismiss dialog

                    //Toast.makeText(SignUp.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull final Exception e) {

                    // Error, Image not uploaded

                    Toast.makeText(SignUp.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}