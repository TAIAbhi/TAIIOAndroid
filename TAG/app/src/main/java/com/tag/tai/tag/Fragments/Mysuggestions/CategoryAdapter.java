package com.tag.tai.tag.Fragments.Mysuggestions;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tag.tai.tag.R;

import java.util.ArrayList;

/**
 * Created by Jam on 18-03-2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyVH> {

    ArrayList<EachSuggestionCategory> allCategories;
    MySuggestionsClick mySuggestionsClick;
    Activity activity;

    public CategoryAdapter(ArrayList<EachSuggestionCategory> allCategories, MySuggestionsClick mySuggestionsClick, Activity activity) {
        this.allCategories = allCategories;
        this.mySuggestionsClick = mySuggestionsClick;
        this.activity = activity;
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_suggestion_category,parent,false);
        return new MyVH(v);
    }

    @Override
    public void onBindViewHolder(MyVH holder, int position) {

        EachSuggestionCategory c = allCategories.get(position);
        holder.tv_category.setText(c.getCategoryname() + " " + c.getSubcategoryname());
        holder.adapter.setData(c.getSuggestions(),mySuggestionsClick);

    }

    @Override
    public int getItemCount() {
        return allCategories.size();
    }

    public class MyVH extends RecyclerView.ViewHolder {
        RecyclerView recyc_mysuggestions;
        TextView tv_category;
        MySuggestionsAdapter adapter;


        public MyVH(View itemView) {
            super(itemView);

            tv_category = itemView.findViewById(R.id.tv_category);
            recyc_mysuggestions = itemView.findViewById(R.id.recyc_mysuggestions);

            adapter = new MySuggestionsAdapter();
            recyc_mysuggestions.setLayoutManager(new LinearLayoutManager(activity));
            recyc_mysuggestions.setAdapter(adapter);
        }
    }
}
