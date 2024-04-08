package com.example.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.BookingDetails;
import com.example.studenthub.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<BookingDetails> houseList;

    public HistoryAdapter(List<BookingDetails> houseList) {
        this.houseList = houseList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_items, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        BookingDetails bookingDetails = houseList.get(position);
        holder.ConfirmationNoDetail.setText(bookingDetails.getConfirmationNumber());
        holder.descriptionDetail.setText(bookingDetails.getAptInfo());
        holder.decisionStatusDetail.setText(bookingDetails.getStatus());

    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView ConfirmationNoDetail, descriptionDetail, decisionStatusDetail;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ConfirmationNoDetail = itemView.findViewById(R.id.ConfirmationNoDetail);
            descriptionDetail = itemView.findViewById(R.id.descriptionDetail);
            decisionStatusDetail = itemView.findViewById(R.id.decisionStatusDetail);

        }
    }
}
