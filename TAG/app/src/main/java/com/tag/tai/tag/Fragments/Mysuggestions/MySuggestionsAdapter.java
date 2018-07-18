package com.tag.tai.tag.Fragments.Mysuggestions;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tag.tai.tag.R;

import java.util.ArrayList;

/**
 * Created by Jam on 18-03-2018.
 */

public class MySuggestionsAdapter extends RecyclerView.Adapter<MySuggestionsAdapter.MyVH>{

    ArrayList<Eachsuggestion> allsuggestions;
    MySuggestionsClick mySuggestionsClick;

    public void setData(ArrayList<Eachsuggestion> data,MySuggestionsClick mySuggestionsClick) {
        allsuggestions = data;
        this.mySuggestionsClick = mySuggestionsClick;
        notifyDataSetChanged();

    }

    public MySuggestionsAdapter() {
    }

    public MySuggestionsAdapter(ArrayList<Eachsuggestion> allsuggestions) {
        this.allsuggestions = allsuggestions;
    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_mysuggestion_card,parent,false);
        return new MyVH(v);
    }

    @Override
    public void onBindViewHolder(MyVH holder, final int position) {

        final Eachsuggestion e = allsuggestions.get(position);
        holder.tv_businessname.setText(e.getBusinessname());
        holder.tv_location.setText(e.getBusinesslocation());
        holder.tv_contact.setText(e.getBusinesscontact());
        holder.tv_comments.setText("Comments : " + e.getComments());
        holder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySuggestionsClick.onEditClicked(position,e.getSuggestionid());
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

        TextView tv_comments,tv_contact,tv_location,tv_businessname;
        CardView card;
        ImageView iv_edit;

        public MyVH(View itemView) {
            super(itemView);

            card = (CardView) itemView;

            tv_businessname = itemView.findViewById(R.id.tv_businessname);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_contact = itemView.findViewById(R.id.tv_contact);
            tv_comments = itemView.findViewById(R.id.tv_comments);
            iv_edit = itemView.findViewById(R.id.iv_edit);

        }
    }
}
