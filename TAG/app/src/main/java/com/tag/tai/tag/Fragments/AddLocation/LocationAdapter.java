package com.tag.tai.tag.Fragments.AddLocation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tag.tai.tag.R;

import java.util.ArrayList;

/**
 * Created by Jam on 20-03-2018.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyVH>{

    ArrayList<LocationPojo> locations;
    LocationSelected locationSelected;

    public LocationAdapter(ArrayList<LocationPojo> locations, LocationSelected locationSelected) {
        this.locations = locations;
        this.locationSelected = locationSelected;
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_location_suggestion,parent,false);

        return new MyVH(v);
    }

    @Override
    public void onBindViewHolder(MyVH holder, int position) {

        final LocationPojo l = locations.get(position);
        final String location;

        if(l.getLocationtype().equals("0")){
            location = l.getSuburb();
        }else{
            location = l.getLocationname();
        }

        holder.tv_location.setText(location);
        holder.eachItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationSelected.onLocationSelected(l.getLocationId(),l.getLocationtype(),location);
            }
        });

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class MyVH extends RecyclerView.ViewHolder {

        RelativeLayout eachItem;
        TextView tv_location;

        public MyVH(View itemView) {
            super(itemView);

            eachItem = (RelativeLayout) itemView;
            tv_location = itemView.findViewById(R.id.tv_location);
        }
    }
}
