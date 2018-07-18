package com.tag.tai.tag.Fragments.FindSugesstions;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.provider.FontRequest;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.Fragments.AddSuggestions.AddSuggestionFragment;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Responses.GetSuggestions.SuggestionData;
import com.tag.tai.tag.Services.RetroClient;

import java.util.ArrayList;

import static com.tag.tai.tag.Fragments.AddSuggestions.AddSuggestionFragment.ISAN_EDIT;
import static com.tag.tai.tag.Fragments.AddSuggestions.AddSuggestionFragment.ISA_COPY;

/**
 * Created by Jam on 17-04-2018.
 */

public class SuggestionsListAdapter extends RecyclerView.Adapter<SuggestionsListAdapter.MYVH>{

    ArrayList<SuggestionData> allsuggestions;
    Activity activity;
    FindSuggestionsCalls findSuggestionsCalls;
    SessionManager session;

    public SuggestionsListAdapter(ArrayList<SuggestionData> allsuggestions, Activity activity, FindSuggestionsCalls findSuggestionsCalls) {
        this.allsuggestions = allsuggestions;
        this.activity = activity;
        this.findSuggestionsCalls = findSuggestionsCalls;
        this.session = new SessionManager(activity);
    }

    @Override
    public MYVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_suggestion_card,parent,false);
        return new MYVH(v);
    }

    @Override
    public void onBindViewHolder(final MYVH holder, final int position) {

        final SuggestionData s = allsuggestions.get(position);

        if(s.getCategoryId().equals("1")){
            Glide.with(activity).load(R.drawable.hangout_b).into(holder.iv_category_icon);
        }else if(s.getCategoryId().equals("2")){
            Glide.with(activity).load(R.drawable.service_b).into(holder.iv_category_icon);
        }else{
            Glide.with(activity).load(R.drawable.shopping_b).into(holder.iv_category_icon);
        }



        holder.tv_category_group.setText(s.getSubCategory());

        if(!s.getMicrocategory().isEmpty())
        holder.tv_category_group.append(" - "  + s.getMicrocategory());

        StyleSpan spanitalics = new StyleSpan(Typeface.ITALIC);
        RelativeSizeSpan sizespan = new RelativeSizeSpan(0.9f);
        StyleSpan spannormal = new StyleSpan(Typeface.NORMAL);
        StyleSpan spanbold = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan colorblue = new ForegroundColorSpan(activity.getResources().getColor(R.color.colorblue));
        ForegroundColorSpan colorlightblue = new ForegroundColorSpan(activity.getResources().getColor(R.color.colorlightblue));

        ImageSpan imageSpan = new ImageSpan(activity,R.drawable.verified,ImageSpan.ALIGN_BASELINE);

        String location;
        int startindex;

        if(s.getIsAChain().equals("true")){
            location = s.getLocation() + " (Multiple locations)";
            startindex = location.indexOf("(Multiple locations)");
        }else{
            location = s.getLocation() + " (Single location)";
            startindex = location.indexOf("(Single location)");
        }

        Spannable locationsTextToSpan = new SpannableString(location);
        locationsTextToSpan.setSpan(spanitalics,startindex,location.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        locationsTextToSpan.setSpan(sizespan,startindex,location.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        holder.tv_location.setText(locationsTextToSpan);


        String businessname,businessname_raw;
        int business_startindex;

        if(s.getCitiLevelBusiness().equals("true")){
            businessname_raw = s.getBusinessName() + " (Serves "+ s.getCityName() +")  ";
            business_startindex = businessname_raw.indexOf("(Serves");
        }else{
            businessname_raw = s.getBusinessName() + " (Serves Locally)  ";
            business_startindex = businessname_raw.indexOf("(Serves");
        }

        businessname = businessname_raw.substring(0, 1).toUpperCase() + businessname_raw.substring(1);


        Spannable businessname_span = new SpannableString(businessname);
        businessname_span.setSpan(spanitalics,business_startindex,businessname.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        businessname_span.setSpan(sizespan,business_startindex,businessname.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        businessname_span.setSpan(colorblue,business_startindex,businessname.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        //if verified
        if(s.getVendorIsVerified().equalsIgnoreCase("true"))
        businessname_span.setSpan(imageSpan,businessname.length() - 1,businessname.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.tv_businessname.setText(businessname_span);

        holder.tv_contact.setText(s.getBusinessContact());

        String comments = s.getComments();
        comments = comments.replace("~ ","\n");

        if(comments.length() > 60){ //if the comment is huge

            String showmorecomment  = "Comments : " + comments.substring(0,60).trim() + "... Show more";
            String showlesscomment  = "Comments : " + comments + " Show less";

            Spannable showmore_commmentsspan = new SpannableString(showmorecomment);
            Spannable showless_commmentsspan = new SpannableString(showlesscomment);
            showmore_commmentsspan.setSpan(spanitalics,showmorecomment.indexOf(" Show more"),showmorecomment.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            showmore_commmentsspan.setSpan(spanbold,showmorecomment.indexOf(" Show more"),showmorecomment.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            //showmore_commmentsspan.setSpan(colorlightblue,showmorecomment.indexOf(" Show more"),showmorecomment.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            showless_commmentsspan.setSpan(spanitalics,showlesscomment.indexOf(" Show less"),showlesscomment.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            showless_commmentsspan.setSpan(spanbold,showlesscomment.indexOf(" Show less"),showlesscomment.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            //showless_commmentsspan.setSpan(colorlightblue,showlesscomment.indexOf(" Show less"),showlesscomment.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            holder.tv_comments_full.setText(showless_commmentsspan);
            holder.tv_comments.setText(showmore_commmentsspan);


            holder.tv_comments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tv_comments.setVisibility(View.GONE);
                    holder.tv_comments_full.setVisibility(View.VISIBLE);
                }
            });

            holder.tv_comments_full.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tv_comments.setVisibility(View.VISIBLE);
                    holder.tv_comments_full.setVisibility(View.GONE);
                }
            });

            //holder.tv_refree.setText("By " + s.getLastAddedBy() + " (Ref : " + s.getSourceName().trim().split(" ")[0] + ")");
            holder.tv_refree.setText("By " + s.getLastAddedBy());


        }else{
            holder.tv_comments.setText("Comments : " + comments);
            holder.tv_comments_full.setVisibility(View.GONE);
        }



        if(Integer.parseInt(s.getTagCount()) == 1){
            holder.tv_tagscount.setText(s.getTagCount() + " tag");
            Glide.with(activity).load(R.drawable.tag).into(holder.iv_tag);
        }else{
            holder.tv_tagscount.setText(s.getTagCount() + " tags");
            Glide.with(activity).load(R.drawable.tags).into(holder.iv_tag);
        }

        holder.tv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String tel = s.getBusinessContact();

            if(tel.length() > 0){
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",tel,null));
                activity.startActivity(i);
            }

            }
        });

        if(s.getUsedTagSuggetion().equalsIgnoreCase("true")){
            holder.v_tag_marker.setVisibility(View.VISIBLE);
        }else{
            holder.v_tag_marker.setVisibility(View.GONE);
        }

        if(s.getBusinessContact().equals("") || s.getBusinessContact().equals(",")){
            holder.ll_call_holder.setVisibility(View.GONE);
            Glide.with(activity).load(R.drawable.calloff).into(holder.iv_call);
        }else{
            holder.ll_call_holder.setVisibility(View.GONE);
            Glide.with(activity).load(R.drawable.callbottom).into(holder.iv_call);
        }

        if(s.getShowMaps().equalsIgnoreCase("true")){
            holder.iv_suggestion_map.setVisibility(View.VISIBLE);
        }else{
            holder.iv_suggestion_map.setVisibility(View.GONE);
        }

        holder.iv_suggestion_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + s.getBusinessName() + s.getLocation());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                activity.startActivity(mapIntent);
            }
        });

        if(session.getUserID().equals(s.getContactId())){
            holder.tv_refree.setVisibility(View.GONE);
            Glide.with(activity).load(R.drawable.edit).into(holder.iv_suggestion_edit);
            holder.iv_delete_suggestion.setVisibility(View.VISIBLE);
        }else{
            holder.tv_refree.setVisibility(View.VISIBLE);
            Glide.with(activity).load(R.drawable.like).into(holder.iv_suggestion_edit);
            holder.iv_delete_suggestion.setVisibility(View.GONE);
        }

        holder.iv_suggestion_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(session.getUserID().equals(s.getContactId())){
                    findSuggestionsCalls.onEditSuggestionClicked(s.getSuggestionId(),ISAN_EDIT);
                }else{
                    findSuggestionsCalls.onEditSuggestionClicked(s.getSuggestionId(),ISA_COPY);
                }
            }
        });

        holder.iv_delete_suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findSuggestionsCalls.onDeleteSuggestionClicked(s.getSuggestionId(),position);
            }
        });

        holder.ll_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(s.getBusinessContact() != null && s.getBusinessContact().length() > 0){
                String tel = s.getBusinessContact();
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",tel.split(",")[0].trim(),null));
                activity.startActivity(i);
            }

            }
        });


        holder.tv_date.setText(s.getLastAddedWhen());


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        if(position > 0){
            params.setMargins(5, 0, 5, 30);
            holder.card.setLayoutParams(params);
        }else{
            ///if position 0

            params.setMargins(5, 15, 5, 30);
            holder.card.setLayoutParams(params);
        }

        if(position >= allsuggestions.size() - 1){
            findSuggestionsCalls.onEndOfRecycler();
        }
    }

    @Override
    public int getItemCount() {
        return allsuggestions.size();
    }

    public class MYVH extends RecyclerView.ViewHolder {

        TextView tv_refree,tv_comments,tv_comments_full,tv_contact,tv_location,tv_businessname,tv_category_group,tv_date;
        CardView card;
        ImageView iv_suggestion_edit,iv_category_icon,iv_tag,iv_suggestion_map,iv_delete_suggestion,iv_call;
        LinearLayout ll_call_holder,ll_call;

        TextView tv_tagscount;
        View v_tag_marker;


        public MYVH(View itemView) {
            super(itemView);

            card = (CardView) itemView;

            tv_category_group = itemView.findViewById(R.id.tv_category_group);
            tv_businessname = itemView.findViewById(R.id.tv_businessname);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_contact = itemView.findViewById(R.id.tv_contact);
            tv_comments = itemView.findViewById(R.id.tv_comments);
            tv_comments_full = itemView.findViewById(R.id.tv_comments_full);
            tv_refree = itemView.findViewById(R.id.tv_refree);
            tv_date = itemView.findViewById(R.id.tv_date);

            iv_suggestion_edit = itemView.findViewById(R.id.iv_suggestion_edit);
            iv_category_icon = itemView.findViewById(R.id.iv_category_icon);
            iv_delete_suggestion = itemView.findViewById(R.id.iv_delete_suggestion);
            iv_call = itemView.findViewById(R.id.iv_call);

            tv_tagscount = itemView.findViewById(R.id.tv_tagscount);
            v_tag_marker = itemView.findViewById(R.id.v_tag_marker);

            iv_tag = itemView.findViewById(R.id.iv_tag);

            ll_call_holder = itemView.findViewById(R.id.ll_call_holder);
            ll_call = itemView.findViewById(R.id.ll_call);

            iv_suggestion_map = itemView.findViewById(R.id.iv_suggestion_map);
        }
    }
}
