package com.tag.tai.tag.Fragments.Notification;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tag.tai.tag.Activities.MainActivity;
import com.tag.tai.tag.Common.RecyclerItemTouchHelper;
import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.Fragments.AddSuggestions.AddSuggestionFragment;
import com.tag.tai.tag.Fragments.FindSugesstions.FindSuggestionsFragment;
import com.tag.tai.tag.Fragments.MyDetails.MyDetailsFragment;
import com.tag.tai.tag.Fragments.Rankings.RankingFragment;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Interfaces.MyDetails;
import com.tag.tai.tag.Services.Interfaces.Notification;
import com.tag.tai.tag.Services.Interfaces.Notifications;
import com.tag.tai.tag.Services.Requests.NotificationDismiss.NotificationDismissRequest;
import com.tag.tai.tag.Services.Responses.GetNotificationResponse.NotificationData;
import com.tag.tai.tag.Services.Responses.GetNotificationResponse.NotificationResponse;
import com.tag.tai.tag.Services.Responses.RequestSuggestions.RequestSuggestionsData;
import com.tag.tai.tag.Services.Responses.UpdateNotification.UpdateNotificationData;
import com.tag.tai.tag.Services.RetroClient;

import java.util.ArrayList;

import io.fabric.sdk.android.services.common.SafeToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.VISIBLE;

public class NotificationFragment extends Fragment implements NotificationListeners,RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    SessionManager session;

    RecyclerView recyc_notification;
    ArrayList<com.tag.tai.tag.Services.Responses.GetNotificationResponse.Notification> allnotifications;
    NotificationsAdapter notificationsAdapter;

    TextView tv_notification_warning;

    public NotificationFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification,container,false);

        session = new SessionManager(getActivity());

        recyc_notification = v.findViewById(R.id.recyc_notification);
        allnotifications = new ArrayList<>();
        notificationsAdapter = new NotificationsAdapter(allnotifications,getActivity(),this);

        recyc_notification.setItemAnimator(new DefaultItemAnimator());
        recyc_notification.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyc_notification.setAdapter(notificationsAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyc_notification);

        tv_notification_warning = v.findViewById(R.id.tv_notification_warning);

        getNotifications();

        return v;
    }

    private void getNotifications() {

        Notifications n = RetroClient.getClient().create(Notifications.class);
        Call<NotificationResponse> call = n.getNotifications(session.getToken(),"1", "b480e7a958e6f13a");
        //Call<NotificationResponse> call = n.getNotifications(session.getToken(),session.getUserID(), Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
//                Toast.makeText(getActivity(), "Received Notifications", Toast.LENGTH_SHORT).show();

                if(response.code() == 200){

                    if(response.body().getData().size() > 0){
                        allnotifications.addAll(response.body().getData());
                        notificationsAdapter.notifyDataSetChanged();
                    }else{
                        tv_notification_warning.setVisibility(VISIBLE);
                    }


                }else{
                    Toast.makeText(getActivity(), "No notifications found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "No notifications found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void notificationDeleted(int position, com.tag.tai.tag.Services.Responses.GetNotificationResponse.Notification notification) {
        
    }

    @Override
    public void notificationClicked(int position, com.tag.tai.tag.Services.Responses.GetNotificationResponse.Notification notification) {

        if(notification.getNotificationType().equalsIgnoreCase("Default")){
            //Toast.makeText(getActivity(), "Default", Toast.LENGTH_SHORT).show();
        }else if(notification.getNotificationType().equalsIgnoreCase("Ranking")){

            RankingFragment r = new RankingFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,r).commit();

        }else if(notification.getNotificationType().equalsIgnoreCase("MyDetails")){

            MyDetailsFragment d = new MyDetailsFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,d).commit();

        }else if(notification.getNotificationType().equalsIgnoreCase("Reqdsuggprovd")){
//            Toast.makeText(getActivity(), "requested", Toast.LENGTH_SHORT).show();

            FragmentManager fm = getActivity().getSupportFragmentManager();

            for(int i = 0; i < fm.getBackStackEntryCount(); i++){
                fm.popBackStack();
            }

            FindSuggestionsFragment f = new FindSuggestionsFragment();

            Bundle b = new Bundle();
            NotificationData d = notification.getData();
            String location;

            if(d.getLocationName() == null){
                location = "";
            }else{
                location = d.getLocationName().split("-").length > 2 ? d.getLocationName().split("-")[1] : d.getLocationName().split("-")[0];
            }


            b.putBoolean("isFromNotification",true);
            b.putString("LocationId", location);
            b.putString("CatId",d.getCatId());
            b.putString("SubCatId",d.getSubCatId());
            b.putString("MCId",d.getMCId());

            f.setArguments(b);

            fm.beginTransaction().replace(R.id.container,f).commit();

        }else if(notification.getNotificationType().equalsIgnoreCase("ReqAddSug")){
            Toast.makeText(getActivity(), "suggadded", Toast.LENGTH_SHORT).show();

            FragmentManager fm = getActivity().getSupportFragmentManager();

            for(int i = 0; i < fm.getBackStackEntryCount(); i++){
                fm.popBackStack();
            }

            AddSuggestionFragment f = new AddSuggestionFragment();

            Bundle b = new Bundle();
            NotificationData d = notification.getData();
            RequestSuggestionsData rd = new RequestSuggestionsData("",d.getCategoryName(),d.getCatId(),
                                            d.getSubCategoryName(),d.getSubCatId(),"",d.getMCId() ==  "0" ? "" : d.getMCId(),
                                            d.getLocationName(),"","","","",
                                            "","","","","");

            b.putString("isARequest","true");
            b.putParcelable("requestedSuggestion",rd);

            f.setArguments(b);

            fm.beginTransaction().replace(R.id.container,f).commit();
        }

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

//        allnotifications.remove(position);
//        notificationsAdapter.notifyItemRemoved(0);


        //if(notificationsAdapter.allnotifications.size() > 0)
        markNotificationDismissed(notificationsAdapter.getAdapterItem(position), position);



    }

    private void markNotificationDismissed(com.tag.tai.tag.Services.Responses.GetNotificationResponse.Notification n, final int position) {

        Log.d(RetroClient.TAG, "markNotificationDismissed: " + n.getNotificationID());

        NotificationDismissRequest request = new NotificationDismissRequest(session.getUserID(),n.getNotificationID(),"true","true");

        Notifications notification = RetroClient.getClient().create(Notifications.class);
        Call<UpdateNotificationData> call = notification.updateNotification(session.getToken(),request);
        call.enqueue(new Callback<UpdateNotificationData>() {
            @Override
            public void onResponse(Call<UpdateNotificationData> call, Response<UpdateNotificationData> response) {

                Log.d(RetroClient.TAG, "onResponse: " + response.code());
                MainActivity m = (MainActivity) getActivity();

                if(m!= null){
                    m.checkNotificationCount();
                }

                notificationsAdapter.removeItem(position);

            }

            @Override
            public void onFailure(Call<UpdateNotificationData> call, Throwable t) {
                if(getActivity() != null)
                Toast.makeText(getActivity(), "Failed to dismiss the notification.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
