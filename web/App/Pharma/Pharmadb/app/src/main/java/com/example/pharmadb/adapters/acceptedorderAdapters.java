package com.example.pharmadb.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmadb.AcceptedOrdersActivity;
import com.example.pharmadb.AcceptedOrdersDetails;
import com.example.pharmadb.NewOrderDetailsActivity;
import com.example.pharmadb.R;
import com.example.pharmadb.data_models.acceptedorderDataType;

import java.util.ArrayList;

public class acceptedorderAdapters extends RecyclerView.Adapter<acceptedorderAdapters.ViewHolder>{

    Context context;
    ArrayList<acceptedorderDataType> ex;

    public acceptedorderAdapters(Context context, ArrayList<acceptedorderDataType> ex) {
        this.context = context;
        this.ex = ex;
    }

    @NonNull
    @Override
    public acceptedorderAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(this.context).inflate(R.layout.layout_accepted_order_item, parent, false);
        return new acceptedorderAdapters.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull acceptedorderAdapters.ViewHolder holder, int position) {
        final acceptedorderDataType dt = ex.get(position);

        holder.tvID.setText(dt.getID());
        holder.tvOrderDate.setText(dt.getOrderDate());
        holder.tvStatus.setText(dt.getStatus());
        holder.tvUserID.setText(dt.getUserID());
        holder.tvBillAmount.setText(dt.getBillAmount());

        holder.btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AcceptedOrdersDetails.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("ID", dt.getID());
                context.startActivity(i);

                //   Toast.makeText(context.getApplicationContext(), "Click on button", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return ex.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvID, tvOrderDate,tvUserID,tvStatus,tvBillAmount;
        Button btnInfo;

        public ViewHolder(View view) {
            super(view);

            tvID = (TextView) view.findViewById(R.id.tvID);
            tvUserID = (TextView) view.findViewById(R.id.tvUserID);
            tvOrderDate = (TextView) view.findViewById(R.id.tvOrderDate);
            tvStatus = (TextView) view.findViewById(R.id.tvStatus);
            tvBillAmount = (TextView) view.findViewById(R.id.tvBillAmount);

            btnInfo=(Button)view.findViewById(R.id.btnInfo);
        }
    }

}
