package com.tag.tai.tag.Fragments.FindSugesstions;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Responses.RequestSuggestions.RequestSuggestionsData;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.MyVH> {

    ArrayList<RequestSuggestionsData> allRequests;
    FindSuggestionsCalls findSuggestionsCalls;
    Context context;

    public RequestsAdapter(ArrayList<RequestSuggestionsData> allRequests, FindSuggestionsCalls findSuggestionsCalls, Context context) {
        this.allRequests = allRequests;
        this.findSuggestionsCalls = findSuggestionsCalls;
        this.context = context;
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_request_suggestions_card,parent,false);
        return new MyVH(v);
    }

    @Override
    public void onBindViewHolder(final MyVH holder, int position) {

        final RequestSuggestionsData r = allRequests.get(position);
        holder.tv_comments.setText("Comments : " + r.getComments());
        holder.tv_refree.setText("By " + r.getContactName());
        holder.tv_date.setText(r.getAddedWhen());
        holder.tv_location.setText(r.getLocation());

        if(r.getCategoryId().equals("1")){
            Glide.with(context).load(R.drawable.hangout_b).into(holder.iv_category_icon);
        }else if(r.getCategoryId().equals("2")){
            Glide.with(context).load(R.drawable.service_b).into(holder.iv_category_icon);
        }else{
            Glide.with(context).load(R.drawable.shopping_b).into(holder.iv_category_icon);
        }

        holder.iv_suggestion_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findSuggestionsCalls.onAddRequestClicked(r.getUid());
            }
        });



        if(r.getMicrocategory() == null){
            holder.tv_category_group.setText(r.getCategory());
        }else{
            holder.tv_category_group.setText(r.getCategory() + " - " + r.getMicrocategory());
        }

        if(position > 0){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(0,20,0,0);
            holder.main_holder.setLayoutParams(layoutParams);
        }

    }

    @Override
    public int getItemCount() {
        return allRequests.size();
    }

    public class MyVH extends RecyclerView.ViewHolder {

        CardView main_holder;
        ImageView iv_category_icon,iv_suggestion_add;
        TextView tv_category_group,tv_location,tv_comments,tv_refree,tv_date;


        public MyVH(View itemView) {
            super(itemView);

            main_holder = (CardView) itemView;
            iv_category_icon = itemView.findViewById(R.id.iv_category_icon);
            iv_suggestion_add = itemView.findViewById(R.id.iv_suggestion_add);
            tv_category_group = itemView.findViewById(R.id.tv_category_group);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_comments = itemView.findViewById(R.id.tv_comments);
            tv_refree = itemView.findViewById(R.id.tv_refree);
            tv_date = itemView.findViewById(R.id.tv_date);


        }
    }
}
