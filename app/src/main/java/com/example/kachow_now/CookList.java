package com.example.kachow_now;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class CookList extends ArrayAdapter<Cook> {
    private final Activity context;
    List<Cook> cooks;

    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    private Button uploadBtn;


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
    }
}