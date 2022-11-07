package com.example.kachow_now;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CookList extends RecyclerView.Adapter<CookList.ViewHolder> {
    ArrayList<Cook> cooks;

    private Cook curCook;
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    private Button uploadBtn;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public CookList(ArrayList<Cook> dataSet) {
        cooks = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_cook_list, viewGroup, false);

        database = FirebaseDatabase.getInstance().getReference("UID");
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        ImageButton a = (ImageButton) viewHolder.profilePic;
        Cook cook = cooks.get(position);
        curCook = cook;
        String n = cook.getFirstName() + " " + cook.getLastName();
        ((TextView) viewHolder.name).setText(n);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        StorageReference mImageRef = storageReference.child("images/" + cook.getUID() + "/profilePhoto");

        final long TWO_MEGABYTE = 2048 * 2048;
        try {
            Task<byte[]> im = mImageRef.getBytes(TWO_MEGABYTE);
            im.addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    if (im.isSuccessful()) {
                        byte[] b = im.getResult();
                        Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
                        a.setImageBitmap(bm);
                    } else {
                        System.out.println("Not Successful");
                    }
                }
            });

        } catch (IndexOutOfBoundsException e) {
        }

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add connection between adapter and buttons
                //curCook.getUID();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cooks.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton profilePic;
        public TextView name;
        public TextView uid;//Stupid idea but could be interesting

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            profilePic = view.findViewById(R.id.cookImage);
            name = view.findViewById(R.id.nameOfChef);
        }
    }

/*
    public CookList(Activity context, List<Cook> listOfCooks) {
        super(context, R.layout.layout_cook_list, listOfCooks);
        this.context = context;
        this.cooks = listOfCooks;

        database = FirebaseDatabase.getInstance().getReference("UID");
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_cook_list, null, true);

        ImageButton a = (ImageButton) listViewItem.findViewById(R.id.cookImage);

        Cook cook = cooks.get(position);
        StorageReference mImageRef = storageReference.child("images/" + cook.UID + "/profilePhoto");

        final long TWO_MEGABYTE = 2048 * 2048;
        try {
            Task<byte[]> im = mImageRef.getBytes(TWO_MEGABYTE);
            if (im.isSuccessful()) {
                byte[] b = im.getResult();
                Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
                a.setImageBitmap(bm);
            }

        } catch (IndexOutOfBoundsException e) {
        }

        return listViewItem;
    }*/
}