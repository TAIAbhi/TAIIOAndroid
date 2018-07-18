package com.tag.tai.tag.Fragments.FindSugesstions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Responses.GetSuggestions.SuggestionData;

import java.util.ArrayList;

/**
 * Created by Jam on 18-03-2018.
 */

public class MySuggestionsAdapter extends RecyclerView.Adapter<MySuggestionsAdapter.MyVH>{

    ArrayList<SuggestionData> allsuggestions;
    Activity activity;
    FindSuggestionsCalls findSuggestionsCalls;
    SessionManager session;

    public MySuggestionsAdapter(Activity activity,FindSuggestionsCalls findSuggestionsCalls) {
        this.activity = activity;
        this.findSuggestionsCalls = findSuggestionsCalls;
        session = new SessionManager(this.activity);
    }

    public void setData(ArrayList<SuggestionData> data) {
        allsuggestions = data;
        notifyDataSetChanged();

    }

    public MySuggestionsAdapter(ArrayList<SuggestionData> allsuggestions) {
        this.allsuggestions = allsuggestions;
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_suggestion_card,parent,false);
        return new MyVH(v);
    }

    @Override
    public void onBindViewHolder(MyVH holder, int position) {

        final SuggestionData s = allsuggestions.get(position);
        holder.tv_businessname.setText(s.getBusinessName());
        holder.tv_location.setText(s.getLocation());
        holder.tv_contact.setText(s.getBusinessContact());
        holder.tv_comments.setText("Comments : " + s.getComments());
        holder.tv_refree.setText("By " + s.getContactName());

        holder.tv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = s.getBusinessContact();
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",tel,null));
                activity.startActivity(i);
            }
        });

        if(s.getBusinessContact() == null){
            holder.ll_call_holder.setVisibility(View.GONE);
        }else{
            holder.ll_call_holder.setVisibility(View.VISIBLE);
        }

        if(session.getUserID().equals(s.getContactId())){
            holder.tv_refree.setVisibility(View.GONE);
            holder.iv_suggestion_edit.setVisibility(View.VISIBLE);
        }else{
            holder.tv_refree.setVisibility(View.VISIBLE);
            holder.iv_suggestion_edit.setVisibility(View.GONE);
        }

        holder.iv_suggestion_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findSuggestionsCalls.onEditSuggestionClicked(s.getSuggestionId(),1);
            }
        });

        if(position > 0){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 20, 0, 0);
            holder.card.setLayoutParams(params);
        }

    }

    @Override
    public int getItemCount() {
        return allsuggestions.size();
    }

    public class MyVH extends RecyclerView.ViewHolder {

        TextView tv_refree,tv_comments,tv_contact,tv_location,tv_businessname;
        CardView card;
        ImageView iv_suggestion_edit;
        LinearLayout ll_call_holder;

        public MyVH(View itemView) {
            super(itemView);

            card = (CardView) itemView;

            tv_businessname = itemView.findViewById(R.id.tv_businessname);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_contact = itemView.findViewById(R.id.tv_contact);
            tv_comments = itemView.findViewById(R.id.tv_comments);
            tv_refree = itemView.findViewById(R.id.tv_refree);
            iv_suggestion_edit = itemView.findViewById(R.id.iv_suggestion_edit);

            ll_call_holder = itemView.findViewById(R.id.ll_call_holder);
        }
    }
}
