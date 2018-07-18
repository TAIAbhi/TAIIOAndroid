package com.tag.tai.tag.Fragments.FindSugesstions;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tag.tai.tag.R;

import java.util.ArrayList;

import static com.tag.tai.tag.Services.RetroClient.TAG;

/**
 * Created by Jam on 18-03-2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyVH> {

    ArrayList<EachSuggestionCategory> allCategories;
    Activity activity;
    FindSuggestionsCalls findSuggestionsCalls;

    public CategoryAdapter(ArrayList<EachSuggestionCategory> allCategories, Activity activity, FindSuggestionsCalls findSuggestionsCalls) {
        this.allCategories = allCategories;
        this.activity = activity;
        this.findSuggestionsCalls = findSuggestionsCalls;
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_suggestion_category,parent,false);
        return new MyVH(v);
    }

    @Override
    public void onBindViewHolder(MyVH holder, int position) {

        EachSuggestionCategory c = allCategories.get(position);

        if(c.getMicrocatname().isEmpty()){
            holder.tv_category.setText(c.getSubcategoryname());
        }else{
            holder.tv_category.setText(c.getSubcategoryname() + " - " + c.getMicrocatname());
        }

        holder.adapter.setData(c.getSuggestions());

        if (position >= allCategories.size() - 1){
            findSuggestionsCalls.onEndOfRecycler();
            Log.d(TAG, "onBindViewHolder: " + position);
        }
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

            adapter = new MySuggestionsAdapter(activity, findSuggestionsCalls);
            recyc_mysuggestions.setLayoutManager(new LinearLayoutManager(activity));
            recyc_mysuggestions.setAdapter(adapter);
        }
    }
}
