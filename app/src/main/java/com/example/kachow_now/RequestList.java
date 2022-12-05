package com.example.kachow_now;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class RequestList extends ArrayAdapter<Request> {
    private final Activity context;
    private final List<Request> requests;
    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public RequestList(Activity context, List<Request> requests) {
        super(context, R.layout.layout_request_list, requests);
        this.context = context;
        this.requests = requests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_request_list, null, true);


        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("MEALS");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Request request = requests.get(position);


        TextView listOfOrders = listViewItem.findViewById(R.id.textViewOrders);

        String orders;
        StringBuilder line = new StringBuilder();
        for (String meal : request.getOrders()) {
            line.append("   - " + meal + "\n");
        }
        orders = line.toString();

        listOfOrders.setText("Orders: " + "\n" + orders);


        return listViewItem;
    }
}
