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

import com.example.pharmadb.AcceptOrderActivity;
import com.example.pharmadb.NewOrderDetailsActivity;
import com.example.pharmadb.R;
import com.example.pharmadb.data_models.neworderDataType;

import java.util.ArrayList;

public class neworderAdapters extends RecyclerView.Adapter<neworderAdapters.ViewHolder>{


    Context context;
    ArrayList<neworderDataType> ex;

    public neworderAdapters(Context context, ArrayList<neworderDataType> ex) {
        this.context = context;
        this.ex = ex;
    }

    @NonNull
    @Override
    public neworderAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(this.context).inflate(R.layout.layout_new_order_item, parent, false);
        return new neworderAdapters.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull neworderAdapters.ViewHolder holder, int position) {
        final neworderDataType dt = ex.get(position);

        holder.tvID.setText(dt.getID());
        holder.tvDistance.setText(dt.getDistance());
        holder.tvUserID.setText(dt.getUserID());

        holder.btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NewOrderDetailsActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("ID", dt.getID());
                context.startActivity(i);

                //   Toast.makeText(context.getApplicationContext(), "Click on button", Toast.LENGTH_LONG).show();
            }
        });

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AcceptOrderActivity.class);
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

        TextView tvUserID, tvDistance,tvID;
        Button btnInfo,btnAccept;

        public ViewHolder(View view) {
            super(view);

            tvUserID = (TextView) view.findViewById(R.id.tvUserID);
            tvDistance = (TextView) view.findViewById(R.id.tvDistance);
            tvID = (TextView) view.findViewById(R.id.tvID);

            btnInfo = (Button) view.findViewById(R.id.btnInfo);
            btnAccept = (Button) view.findViewById(R.id.btnAccept);
        }
    }

}
