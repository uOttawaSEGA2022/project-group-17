package com.example.kachow_now;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CookProfileClientSide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_profile_client_side);

        Button reportButton = (Button) findViewById(R.id.ReportCookProfileClient);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO DO //
                //setContentView(R.layout.activity_submit_report);
            }
        });


    /*
        private void showMealEntry(final String productId, String productName) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.CookProfileClientSide, null);
            dialogBuilder.setView(dialogView);

            final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
            final EditText editTextPrice  = (EditText) dialogView.findViewById(R.id.editTextPrice);
            final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
            final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

            dialogBuilder.setTitle(productName);
            final AlertDialog b = dialogBuilder.create();
            b.show();

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = editTextName.getText().toString().trim();
                    double price = Double.parseDouble(String.valueOf(editTextPrice.getText().toString()));
                    if (!TextUtils.isEmpty(name)) {
                        updateProduct(productId, name, price);
                        b.dismiss();
                    }
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteProduct(productId);
                    b.dismiss();
                }
            });
        }
    */
    }


}