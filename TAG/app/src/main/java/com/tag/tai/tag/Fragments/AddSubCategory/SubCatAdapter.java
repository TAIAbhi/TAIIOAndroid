package com.tag.tai.tag.Fragments.AddSubCategory;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tag.tai.tag.Fragments.AddLocation.LocationPojo;
import com.tag.tai.tag.R;

import java.util.ArrayList;

/**
 * Created by Jam on 20-03-2018.
 */

public class SubCatAdapter extends RecyclerView.Adapter<SubCatAdapter.MyVH> {

    ArrayList<SubCatPojo> subcats;
    SubCategorySelected subCategorySelected;

    public SubCatAdapter(ArrayList<SubCatPojo> subcats, SubCategorySelected subCategorySelected) {
        this.subcats = subcats;
        this.subCategorySelected = subCategorySelected;
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_location_suggestion,parent,false);

        return new MyVH(v);
    }

    @Override
    public void onBindViewHolder(MyVH holder, int position) {
        final SubCatPojo s = subcats.get(position);
        String subcat = "";

        if(s.getCategoryType().equals("0")){
            subcat = s.getCategoryName();
        }else{
            subcat = s.getCategoryGroup();
        }

        holder.tv_subcats.setText(subcat);
        holder.eachItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subCategorySelected.onSubCatSelected(s.getMicroId(),s.getCategoryGroup(),s.getCategoryName(),s.getCategoryType());
            }
        });


//
//        if(s.getCategoryType().equals("0")){
//            subcat = l.getSuburb();
//        }else{
//            location = l.getLocationname();
//        }
//
//        holder.tv_location.setText(location);
//        holder.eachItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                locationSelected.onLocationSelected(l.getLocationId(),l.getLocationtype(),location);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return subcats.size();
    }

    public class MyVH extends RecyclerView.ViewHolder {

        RelativeLayout eachItem;
        TextView tv_subcats;

        public MyVH(View itemView) {
            super(itemView);

            eachItem = (RelativeLayout) itemView;
            tv_subcats = itemView.findViewById(R.id.tv_location);
        }
    }
}
