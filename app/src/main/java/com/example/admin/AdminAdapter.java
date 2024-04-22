package com.example.admin;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.AdminModel;
import com.example.studenthub.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {
    private List<AdminModel> houseList;

    public AdminAdapter(List<AdminModel> houseList) {
        this.houseList = houseList;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_items, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        AdminModel house = houseList.get(position);

        holder.textNameDetail.setText(house.getNameDetail());
        holder.textHouseDetail.setText(house.getHouseDetail());
        holder.textRoomsDetail.setText(house.getRoomsDetail());
        holder.textDecisionStatusDetail.setText(house.getDecisionStatusDetail());


        holder.buttonDecision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirmationNumber = house.getConfirmationNumber();
                int adapterPosition = holder.getAdapterPosition();
                changeDecision(adapterPosition,"Confirmed");
                changeStatusInHistory(adapterPosition,"Confirmed");
                Intent intent = new Intent(v.getContext(), AdminActivity.class);
                intent.putExtra("CONFIRMATION_NUMBER", confirmationNumber);
                v.getContext().startActivity(intent);

            }
        });

        holder.pendingbuttonDecision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirmationNumber = house.getConfirmationNumber();
                int adapterPosition = holder.getAdapterPosition();
                changeDecision(adapterPosition,"Pending");
                changeStatusInHistory(adapterPosition,"Pending");
                Intent intent = new Intent(v.getContext(), AdminActivity.class);
                intent.putExtra("CONFIRMATION_NUMBER", confirmationNumber);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView  textNameDetail,textHouseDetail,textRoomsDetail,buttonDecision,pendingbuttonDecision,textDecisionStatusDetail;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);


            textNameDetail = itemView.findViewById(R.id.textNameDetail);
            textHouseDetail = itemView.findViewById(R.id.textHouseDetail);
            textRoomsDetail = itemView.findViewById(R.id.textRoomsDetail);
            pendingbuttonDecision=itemView.findViewById(R.id.pendingbuttonDecision);
            buttonDecision = itemView.findViewById(R.id.buttonDecision);
            textDecisionStatusDetail=itemView.findViewById(R.id.textDecisionStatusDetail);
        }
    }

    private void changeDecision(int position,String status) {
        AdminModel house = houseList.get(position);
        house.setDecisionStatusDetail(status);
        String confirmationNumber = house.getConfirmationNumber();
        DatabaseReference nodeRef = FirebaseDatabase.getInstance().getReference().child("Bookings").child(confirmationNumber);

        String decisionStatus = status;

        nodeRef.child("decisionStatus").setValue(decisionStatus);

    }



    private void changeStatusInHistory(int position,String status) {
        AdminModel house = houseList.get(position);
        house.setDecisionStatusDetail(status);
        String confirmationNumber = house.getConfirmationNumber();

        DatabaseReference nodeRef = FirebaseDatabase.getInstance().getReference()
                .child("History")
                .child(house.getUidNo())
                .child("bookings")
                .child(confirmationNumber);

        String decisionStatus = status;
        nodeRef.child("status").setValue(decisionStatus);

    }
}
