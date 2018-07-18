package com.tag.tai.tag.Fragments.Notification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Responses.GetNotificationResponse.Notification;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Notification> allnotifications;
    Context context;
    NotificationListeners notificationListener;

    public static final int DEFAULT_NOTIFICATION = 0;
    public static final int REQUEST_SUGGESTION_NOTIFICATION = 1;
    public static final int SUGGESTION_PROVIDED_NOTIFICATION = 2;

    public NotificationsAdapter(ArrayList<Notification> allnotifications, Context context, NotificationListeners notificationListener) {
        this.allnotifications = allnotifications;
        this.context = context;
        this.notificationListener = notificationListener;
    }

    @Override
    public int getItemViewType(int position) {
        Notification n = allnotifications.get(position);

        if(n.getNotificationType().equalsIgnoreCase("Default") || n.getNotificationType().equalsIgnoreCase("Ranking") || n.getNotificationType().equalsIgnoreCase("MyDetails")){
            return DEFAULT_NOTIFICATION;
        }else{
            return REQUEST_SUGGESTION_NOTIFICATION;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == DEFAULT_NOTIFICATION){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_notification_simple,parent,false);
            return new DefaultVH(v);
        }else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_requested_notification,parent,false);
            return new RequestsVH(v);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Notification n = allnotifications.get(position);
//        holder.tv_notification_title.setText(n.getNotificationTitle());
//        holder.tv_notification_description.setText(n.getDescription());


        if(holder.getItemViewType() == DEFAULT_NOTIFICATION){

            DefaultVH h = (DefaultVH)holder;

            h.tv_notification_title.setText(n.getNotificationID() + n.getNotificationType());
            //h.tv_notification_title.setText(n.getNotificationTitle());
            h.tv_notification_description.setText(n.getDescription());
            h.tv_notification_time_sent.setText("Time Sent : " + n.getTimeSent());

            //if(n.getNotificationPhoto() != null) {
            if(false) {
                Glide.with(context).load(n.getNotificationPhoto()).into(h.iv_notification_icon);
            }else if(n.getNotificationType().equalsIgnoreCase("MyDetails")){
                Glide.with(context).load(R.drawable.notif1).into(h.iv_notification_icon);
            }else if(n.getNotificationType().equalsIgnoreCase("Ranking")){
                Glide.with(context).load(R.drawable.notif7).into(h.iv_notification_icon);
            }else if(n.getNotificationType().equalsIgnoreCase("Default")) {
                Glide.with(context).load(R.drawable.notif4).into(h.iv_notification_icon);
            }else{
                Glide.with(context).load(R.drawable.iconround).into(h.iv_notification_icon);
            }

        }else{

            //requested notifications

            RequestsVH h = (RequestsVH)holder;

            h.tv_notification_category.setText(n.getData().getCategoryName() + " " + n.getData().getSubCategoryName());
            h.tv_notification_location.setText(n.getData().getLocationName());
            h.tv_target.setText(n.getData().getTarget());
            h.tv_notification_requester.setText(n.getData().getAddedBy());
            h.tv_notification_time_sent.setText("Time Sent : " + n.getTimeSent());
            h.tv_notification_date.setText(n.getData().getAddedWhen());


            h.tv_target.setText(n.getNotificationType());

            if(false){
            //if(n.getNotificationPhoto() != null){

                Glide.with(context).load(n.getNotificationPhoto()).into(h.iv_notification_icon);

            }else{

                if(n.getNotificationType().equalsIgnoreCase("Reqdsuggprovd")){
                    Glide.with(context).load(R.drawable.notif6).into(h.iv_notification_icon);
                }else if(n.getNotificationType().equalsIgnoreCase("ReqAddSug")) {
                    Glide.with(context).load(R.drawable.notif4).into(h.iv_notification_icon);
                }else if(n.getNotificationType().equalsIgnoreCase("ProvdReqdsugg")){
                    Glide.with(context).load(R.drawable.notif3).into(h.iv_notification_icon);
                }else{
                    Glide.with(context).load(R.drawable.iconround).into(h.iv_notification_icon);
                }
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationListener.notificationClicked(position,n);
            }
        });

    }



    @Override
    public int getItemCount() {
        return allnotifications.size();
    }

    public void removeItem(int position) {
        allnotifications.remove(position);
        notifyItemRemoved(position);
    }

    public Notification getAdapterItem(int position){
        return allnotifications.get(position);
    }


    public class DefaultVH extends RecyclerView.ViewHolder {
        TextView tv_notification_description,tv_notification_title,tv_notification_time_sent;
        ImageView iv_notification_icon;

        public RelativeLayout viewForeground;

        public DefaultVH(View itemView) {
            super(itemView);

            viewForeground = itemView.findViewById(R.id.viewForeground);

            tv_notification_description = itemView.findViewById(R.id.tv_notification_description);
            tv_notification_title = itemView.findViewById(R.id.tv_notification_title);
            tv_notification_time_sent = itemView.findViewById(R.id.tv_notification_time_sent);
            iv_notification_icon = itemView.findViewById(R.id.iv_notification_icon);
        }
    }

    public class RequestsVH extends RecyclerView.ViewHolder {
        TextView tv_notification_location,tv_notification_category,tv_target,tv_notification_requester,tv_notification_date,tv_notification_time_sent;
        ImageView iv_notification_icon;
        public RelativeLayout viewForeground;

        public RequestsVH(View itemView) {
            super(itemView);

            viewForeground = itemView.findViewById(R.id.viewForeground);

            tv_notification_location = itemView.findViewById(R.id.tv_notification_location);
            tv_notification_category = itemView.findViewById(R.id.tv_notification_category);
            tv_notification_requester = itemView.findViewById(R.id.tv_notification_requester);
            tv_notification_date = itemView.findViewById(R.id.tv_notification_date);
            tv_notification_time_sent = itemView.findViewById(R.id.tv_notification_time_sent);

            tv_target = itemView.findViewById(R.id.tv_targett);
            iv_notification_icon = itemView.findViewById(R.id.iv_notification_icon);

        }
    }
}



