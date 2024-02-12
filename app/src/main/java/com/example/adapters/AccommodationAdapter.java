package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Accommodation;
import com.example.studenthub.Personal_Info;
import com.example.studenthub.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class AccommodationAdapter extends RecyclerView.Adapter<AccommodationAdapter.ViewHolder> {
    private ArrayList<Accommodation> accommodations;
    private Context context;

    private DatabaseReference bookingsDatabase;

    public AccommodationAdapter(Context context, ArrayList<Accommodation> accommodations) {
        this.context = context;
        this.accommodations = accommodations;
        this.bookingsDatabase = FirebaseDatabase.getInstance().getReference().child("Bookings");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accommodation_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Accommodation accommodation = accommodations.get(position);

        holder.hotelName.setText(accommodation.getName());

        holder.hotelImage.setImageResource(accommodation.getImageResource());

        Button bookNowButton = holder.itemView.findViewById(R.id.bookNowButton); // Find the button within the item
        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Personal_Info.class);


                intent.putExtra("hotelObject", accommodation);

                context.startActivity(intent);
            }
        });
    }

    private void saveBookingToDatabase(Accommodation accommodation) {

        String bookingId = bookingsDatabase.push().getKey();
        bookingsDatabase.child(bookingId).setValue(accommodation);
    }



    @Override
    public int getItemCount() {
        return accommodations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView hotelImage;
        private TextView hotelName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelImage = itemView.findViewById(R.id.hotelImage);
            hotelName = itemView.findViewById(R.id.hotelName);
                }
    }
}

