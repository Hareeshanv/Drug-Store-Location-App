package com.example.pharma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharma.R;
import com.example.pharma.data_models.orderDataType;

import java.util.ArrayList;

public class orderAdapters extends RecyclerView.Adapter<orderAdapters.ViewHolder>{

    Context context;
    ArrayList<orderDataType> ex;

    public orderAdapters(Context context, ArrayList<orderDataType> ex) {
        this.context = context;
        this.ex = ex;
    }

    @NonNull
    @Override
    public orderAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(this.context).inflate(R.layout.layout_order_item, parent, false);
        return new orderAdapters.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull orderAdapters.ViewHolder holder, int position) {
        final orderDataType dt = ex.get(position);

        holder.tvID.setText(dt.getID());
        holder.tvOrderDate.setText(dt.getOrderDate());
        holder.tvMedicalStoreID.setText(dt.getTvMedicalStoreID());
        holder.tvStatus.setText(dt.getStatus());
        holder.tvBillAmount.setText(dt.getBillAmount());
    }

    @Override
    public int getItemCount() {
        return ex.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvID, tvOrderDate,tvMedicalStoreID,tvStatus,tvBillAmount;

        public ViewHolder(View view) {
            super(view);

            tvID = (TextView) view.findViewById(R.id.tvID);
            tvOrderDate = (TextView) view.findViewById(R.id.tvOrderDate);
            tvMedicalStoreID = (TextView) view.findViewById(R.id.tvMedicalStoreID);
            tvStatus = (TextView) view.findViewById(R.id.tvStatus);
            tvBillAmount = (TextView) view.findViewById(R.id.tvBillAmount);
        }
    }

}
