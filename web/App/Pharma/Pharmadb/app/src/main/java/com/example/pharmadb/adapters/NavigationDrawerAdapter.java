package com.example.pharmadb.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharmadb.AboutUsActivity;
import com.example.pharmadb.AcceptedOrdersActivity;
import com.example.pharmadb.HomePage;
import com.example.pharmadb.InfoActivity;
import com.example.pharmadb.LoginPage;
import com.example.pharmadb.NewOrderListActivity;
import com.example.pharmadb.R;
import com.example.pharmadb.UpdateLocationActivity;

public class NavigationDrawerAdapter  extends BaseAdapter {
    private Context mContext = null;
    private String[] menuItemList = null;
    private LayoutInflater inflater = null;

    public NavigationDrawerAdapter(Context context, String[] list) {
        mContext = context;
        menuItemList = list;
    }
    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.navigation_drawer_item_view_layout, null);

        TextView textView = rootView.findViewById(R.id.nav_menu_item_title);
        textView.setText(menuItemList[position]);
        rootView.setId(position);

        rootView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = null;

                switch (menuItemList[v.getId()]) {

                    case "Home":
                        i = new Intent(mContext, HomePage.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                        break;

                    case "New Orders":
                        i = new Intent(mContext, NewOrderListActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                        break;

                    case "Accepted Orders":
                        i = new Intent(mContext, AcceptedOrdersActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                        break;

                    case "Info":
                        i = new Intent(mContext, InfoActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                        break;

                    case "Location":
                        i = new Intent(mContext, UpdateLocationActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                        break;

                    case "About App":
                        i = new Intent(mContext, AboutUsActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                        break;


                    case "Logout":

                        i = new Intent(mContext, LoginPage.class);
                        mContext.startActivity(i);
                        ((Activity) mContext).finish();
                        break;

                    default:
                        Toast.makeText(mContext, "Something Wrong", Toast.LENGTH_SHORT).show();
                        break;

                }

            }

        });
        return rootView;
    }
}
