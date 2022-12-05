package com.example.kachow_now;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ComplaintList extends ArrayAdapter<Complaint> {
    private final Activity context;
    List<Complaint> complaints ;

    public ComplaintList(Activity context, List<Complaint> products) {
        super(context, R.layout.layout_complaint_list, products);
        this.context = context;
        this.complaints = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_complaint_list, null, true);

        TextView textViewMeal = listViewItem.findViewById(R.id.textViewMealName);
        TextView textViewReview = listViewItem.findViewById(R.id.textViewReview);
        TextView textViewCook = listViewItem.findViewById(R.id.textViewCook);
        TextView textViewDate = listViewItem.findViewById(R.id.textViewDate);

        Complaint complaint = complaints.get(position);
        textViewMeal.setText(complaint.getMealReviewed());
        textViewReview.setText(complaint.getTextReview());
        textViewCook.setText(complaint.getComplaintee().getFirstName());
        textViewDate.setText(complaint.getMonth() + "/" + complaint.getDay() + "/" + complaint.getYear());

        return listViewItem;
    }
}