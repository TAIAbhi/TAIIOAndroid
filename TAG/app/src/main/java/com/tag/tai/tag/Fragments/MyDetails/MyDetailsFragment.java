package com.tag.tai.tag.Fragments.MyDetails;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tag.tai.tag.Activities.LoginActivity;
import com.tag.tai.tag.Activities.MainActivity;
import com.tag.tai.tag.Common.Loader;
import com.tag.tai.tag.Common.ProcessError;
import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.Fragments.FindSugesstions.FindSuggestionsFragment;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Interfaces.Location;
import com.tag.tai.tag.Services.Interfaces.MyDetails;
import com.tag.tai.tag.Services.Requests.AddContact.AddContactRequest;
import com.tag.tai.tag.Services.Requests.UpdateDetails.UpdateDetailsRequest;
import com.tag.tai.tag.Services.Responses.AddContactResponse.AddContactResponse;
import com.tag.tai.tag.Services.Responses.GetLocations.LocationsData;
import com.tag.tai.tag.Services.Responses.GetLocations.LocationsResponse;
import com.tag.tai.tag.Services.Responses.GetMyDetails.MyDetailsResponse;
import com.tag.tai.tag.Services.Responses.UpdateDetails.UpdetailsResponse;
import com.tag.tai.tag.Services.RetroClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Jam on 16-03-2018.
 */

public class MyDetailsFragment extends Fragment {

    TextInputLayout input_layout_name,input_layout_number;

    EditText input_ref_by,input_name,input_number,input_prof_details;
    SessionManager session;

    Button btn_savedetails;
    Loader loader;

    TextView tv_category,tv_detailstitle,tv_add_contact_list;

    AutoCompleteTextView input_homelocation,input_worklocation,input_nativelocation;
    List<String> locationss;
    ArrayList<LocationsData> locationsdata;
    ArrayAdapter<String> homelocationsadapter;

    ImageView fab_add_contact;
    ImageView fab_view_requested_suggestions;
    public boolean isAddingNewContact = false;

    CheckBox cb_provide_suggestion,cb_details_updated;

    public MyDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mydetails,container,false);

        loader = new Loader(getActivity(),(MainActivity)getActivity());

        session = new SessionManager(getActivity());

        input_ref_by = v.findViewById(R.id.input_ref_by);
        input_name = v.findViewById(R.id.input_name);
        input_number = v.findViewById(R.id.input_number);
        input_homelocation = v.findViewById(R.id.input_homelocation);
        input_worklocation = v.findViewById(R.id.input_worklocation);
        input_nativelocation = v.findViewById(R.id.input_nativelocation);
        input_prof_details = v.findViewById(R.id.input_prof_details);

        input_layout_name = v.findViewById(R.id.input_layout_name);
        input_layout_number = v.findViewById(R.id.input_layout_number);

        tv_category = v.findViewById(R.id.tv_category);
        tv_detailstitle = v.findViewById(R.id.tv_detailstitle);
        tv_add_contact_list = v.findViewById(R.id.tv_add_contact_list);

        cb_provide_suggestion = v.findViewById(R.id.cb_provide_suggestion);
        cb_details_updated = v.findViewById(R.id.cb_details_updated);

        btn_savedetails = v.findViewById(R.id.btn_savedetails);
        btn_savedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isAddingNewContact){
                    addNewContact();
                }else{
                    saveMyDetails();
                }

            }
        });

        fab_add_contact = v.findViewById(R.id.fab_add_contact);
        fab_view_requested_suggestions = v.findViewById(R.id.fab_view_requested_suggestions);
        fab_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpAddContactsPage();
            }
        });
        fab_view_requested_suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRequestedSuggestions();
            }
        });

        if(session.getRole().equals("2")){
            fab_add_contact.setVisibility(VISIBLE);
        }

        tv_add_contact_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://tagaboutit.com/SourceContact/ViewMyContacts?srcMobile=" + session.getUserMobile()));
                startActivity(browserIntent);
            }
        });


        locationsdata = new ArrayList<>();
        locationss = new ArrayList<>();
        homelocationsadapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_text,new ArrayList<String>());
        input_homelocation.setAdapter(homelocationsadapter);
        input_worklocation.setAdapter(homelocationsadapter);
        input_nativelocation.setAdapter(homelocationsadapter);
        setLocations("");

        getMyDetails();

        Button btn_logout = v.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        return v;
    }

    private void viewRequestedSuggestions() {

        Bundle b = new Bundle();
        b.putBoolean("showRequestedSuggestions",true);

        FindSuggestionsFragment viewSuggestionsFrag = new FindSuggestionsFragment();
        viewSuggestionsFrag.setArguments(b);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,viewSuggestionsFrag).commit();
    }

    private void addNewContact() {
        loader.showLoader("Saving your new Conatct.");

        String contactName = input_ref_by.getText().toString();
        String contactNumber = input_number.getText().toString();
        String contactId = session.getUserID();
        String location1 = input_homelocation.getText().toString();
        String location2 = input_worklocation.getText().toString();
        String location3 = input_nativelocation.getText().toString();
        String contactLevelUnderstating = "3";
        String notification = "2";
        String isContactDetailsAdded = "true";
        String comments = input_prof_details.getText().toString();

        AddContactRequest request = new AddContactRequest(contactName,contactNumber,contactId,location1,location2,location3,contactLevelUnderstating,notification,isContactDetailsAdded,comments,"2");
        MyDetails m = RetroClient.getClient().create(MyDetails.class);
        Call<AddContactResponse> call = m.addContact(session.getToken(),request);
        call.enqueue(new Callback<AddContactResponse>() {
            @Override
            public void onResponse(Call<AddContactResponse> call, Response<AddContactResponse> response) {
                loader.hideLoader();

                if(response.code() == 200){

                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }else{

                    try {
                        ProcessError.processError(getActivity(),response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getActivity(), "" + RetroClient.CONNETION_ERROR, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddContactResponse> call, Throwable t) {

                loader.hideLoader();
                if(getActivity()==null)return;
                Toast.makeText(getActivity(), "" + RetroClient.CONNETION_ERROR, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setUpAddContactsPage() {

        isAddingNewContact = true;
        tv_detailstitle.setText("ADD CONTACT");

        //input_ref_by.setEnabled(true);
        input_name.setEnabled(true);
        input_number.setEnabled(true);

        tv_add_contact_list.setVisibility(VISIBLE);

        input_layout_name.setHint("Contact Name");
        input_layout_number.setHint("Contact Number");

        input_number.setCompoundDrawablesWithIntrinsicBounds(null,null,getActivity().getResources().getDrawable(R.drawable.contact_add),null);
        input_number.setOnTouchListener(getOnTouchListener());


        //input_ref_by.setText("");
        input_name.setText("");
        input_number.setText("");
        input_homelocation.setText("");
        input_worklocation.setText("");
        input_nativelocation.setText("");
        input_prof_details.setText("");

    }

    private void setLocations(final String location) {

        loader.showLoader();

        Location loc = RetroClient.getClient().create(Location.class);
        Call<LocationsResponse> call = loc.getLocations(session.getToken(),location,session.getcurrentcity() + "");
        call.enqueue(new Callback<LocationsResponse>() {
            @Override
            public void onResponse(Call<LocationsResponse> call, Response<LocationsResponse> response) {
                loader.hideLoader();

                if(response.code() == 200){

                    locationsdata = response.body().getMessage();
                    locationss.clear();
                    homelocationsadapter.clear();

                    int x = 0;

                    for(LocationsData l : locationsdata){
                        if(l.getLocationName().isEmpty()) {
                            locationss.add(l.getSuburb());
                            homelocationsadapter.add(l.getSuburb());
                        }else{
                            locationss.add(l.getLocationName().trim() + " - " + l.getSuburb());
                            homelocationsadapter.add(l.getLocationName().trim() + " - " + l.getSuburb());
                        }
                        x++;
                    }

                    //homelocationsadapterr.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<LocationsResponse> call, Throwable t) {
                loader.hideLoader();
            }
        });
    }

    private void saveMyDetails() {

        loader.showLoader("Updating your details.");


        String contactId = session.getUserID();
        String contactName = input_name.getText().toString();
        String contactNumber = input_number.getText().toString();
        String location1 = input_homelocation.getText().toString();
        String location2 = input_worklocation.getText().toString();
        String location3 = input_nativelocation.getText().toString();
        String contactLevelUnderstating = "3";
        String notification = "2";
        String isContactDetailsAdded = "" + cb_details_updated.isChecked();
        String comments = input_prof_details.getText().toString();
        String allowProvideSuggestion =  "" + cb_provide_suggestion.isChecked();

        UpdateDetailsRequest request = new UpdateDetailsRequest(contactId,contactName,contactNumber,location1,location2,location3,contactLevelUnderstating,notification,isContactDetailsAdded,comments,"2",allowProvideSuggestion);
        MyDetails m = RetroClient.getClient().create(MyDetails.class);
        Call<UpdetailsResponse> call = m.updateMyDetails(session.getToken(),request);
        call.enqueue(new Callback<UpdetailsResponse>() {
            @Override
            public void onResponse(Call<UpdetailsResponse> call, Response<UpdetailsResponse> response) {
                loader.hideLoader();

                if(response.code() == 200)
                Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<UpdetailsResponse> call, Throwable t) {

                loader.hideLoader();
                if(getActivity()==null)return;
                Toast.makeText(getActivity(), "" + RetroClient.CONNETION_ERROR, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getMyDetails() {

        loader.showLoader("Fetching your details.");

        MyDetails d = RetroClient.getClient().create(MyDetails.class);
        Call<MyDetailsResponse> call =  d.getMyDetails(session.getToken(),session.getUserID());
        call.enqueue(new Callback<MyDetailsResponse>() {
            @Override
            public void onResponse(Call<MyDetailsResponse> call, Response<MyDetailsResponse> response) {

                loader.hideLoader();

                if(response.code() == 200){

                    input_ref_by.setText(response.body().getMessage().getSource());
                    input_ref_by.setEnabled(false);
                    input_name.setText(response.body().getMessage().getContact());
                    input_name.setEnabled(false);
                    input_number.setText(response.body().getMessage().getContactNumber());
                    input_number.setEnabled(false);

                    input_homelocation.setText(response.body().getMessage().getLocation1());
                    input_worklocation.setText(response.body().getMessage().getLocation2());
                    input_nativelocation.setText(response.body().getMessage().getLocation3());
                    input_prof_details.setText(response.body().getMessage().getContactComments());

                    if(response.body().getMessage().isAllowProvideSuggestion()){
                        fab_view_requested_suggestions.setVisibility(VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyDetailsResponse> call, Throwable t) {
                loader.hideLoader();
                if(getActivity()==null)return;
                Toast.makeText(getActivity(), "" + RetroClient.CONNETION_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    public void logout(){
        session.Logout();

        Intent i = new Intent(getActivity(),LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.tb_ranking).setVisibility(VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().findViewById(R.id.tb_ranking).setVisibility(GONE);
    }

    public View.OnTouchListener getOnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (input_number.getRight() - input_number.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        checkContactsPermissions();
                        
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private void checkContactsPermissions() {

        if(ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS},
                    1);
        }else{
            getContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContact();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }

    }

    private void getContact() {

        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 7);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 7:
                if (resultCode == Activity.RESULT_OK) {

                    Uri uri;
                    Cursor cursor1, cursor2;
                    String TempNameHolder, TempNumberHolder, TempContactID, IDresult = "" ;
                    int IDresultHolder ;

                    uri = data.getData();

                    cursor1 = getActivity().getContentResolver().query(uri, null, null, null, null);

                    if (cursor1.moveToFirst()) {

                        TempNameHolder = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        TempContactID = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));

                        IDresult = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        IDresultHolder = Integer.valueOf(IDresult) ;

                        if (IDresultHolder == 1) {

                            cursor2 = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + TempContactID, null, null);

                            while (cursor2.moveToNext()) {

                                TempNumberHolder = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                if(TempNumberHolder.length() > 10)
                                    TempNumberHolder = TempNumberHolder.substring(TempNumberHolder.length() - 10,TempNumberHolder.length());

                                input_number.setText(TempNumberHolder);

                            }
                        }

                    }
                }
                break;
        }
    }


}
