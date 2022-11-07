package com.example.kachow_now;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClientHomepage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference dB;
    private ImageButton chef1;
    private ArrayList<Cook> chefs;
    private ListView listViewChefs;
    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clienthomepage);
        mAuth = FirebaseAuth.getInstance();
        dB = FirebaseDatabase.getInstance().getReference("UID");

        rv = (RecyclerView) findViewById(R.id.chefRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        rv.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        chefs = new ArrayList<>();


    }

    @Override
    protected void onStart() {
        super.onStart();
        dB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chefs.clear();
                for (DataSnapshot s : snapshot.getChildren()) {
                    User t = s.getValue(User.class);
                    if (t.getRole().equalsIgnoreCase("cook")) {
                        chefs.add(s.getValue(Cook.class));
                    }
                }
                rv.setAdapter(new CookList(chefs));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
