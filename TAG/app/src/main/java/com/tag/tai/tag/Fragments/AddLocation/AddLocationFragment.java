package com.tag.tai.tag.Fragments.AddLocation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Interfaces.Location;
import com.tag.tai.tag.Services.Responses.GetLocations.LocationsData;
import com.tag.tai.tag.Services.Responses.GetLocations.LocationsResponse;
import com.tag.tai.tag.Services.Responses.GetSuburbs.SuburbsData;
import com.tag.tai.tag.Services.Responses.GetSuburbs.SuburbsResponse;
import com.tag.tai.tag.Services.RetroClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 19-03-2018.
 */

public class AddLocationFragment extends Fragment implements LocationSelected{

    RecyclerView recyc_location;
    ArrayList<LocationPojo> locations;
    LocationAdapter locationAdapter;

    Button btn_addlocation;

    String selected_suburb = "",selected_location = "";
    EditText et_suburb,et_location;

    ArrayList<String> allSuburbs;

    SessionManager session;

    public AddLocationFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_location,container,false);

        session = new SessionManager(getActivity());

        btn_addlocation = v.findViewById(R.id.btn_addlocation);
        btn_addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,
                        new Intent().putExtra("location",selected_suburb + " - " + selected_location));

                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        recyc_location = v.findViewById(R.id.recyc_location);
        locations = new ArrayList<>();
        locationAdapter = new LocationAdapter(locations,this);

        recyc_location.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyc_location.setItemAnimator(new DefaultItemAnimator());
        recyc_location.setAdapter(locationAdapter);

        allSuburbs = new ArrayList<>();
        getAllSuburbs();

        et_suburb = v.findViewById(R.id.et_suburb);
        et_location = v.findViewById(R.id.et_location);
        et_suburb.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("12345", "afterTextChanged: " + et_suburb.getText().toString());

                displaySuburbs(et_suburb.getText().toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


        et_location.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                if(selected_location.isEmpty())
                getLocations(et_location.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        et_suburb.requestFocus();

        return v;
    }

    private void getLocations(final String location) {

        LocationPojo l;
        selected_location = "";
        locations.clear();
        locationAdapter.notifyDataSetChanged();


        Location loc = RetroClient.getClient().create(Location.class);
        Call<LocationsResponse> call = loc.getLocations(session.getToken(),location,"" +session.getcurrentcity());
        call.enqueue(new Callback<LocationsResponse>() {
            @Override
            public void onResponse(Call<LocationsResponse> call, Response<LocationsResponse> response) {
                if(response.code() == 200){

                    locations.clear();

                    for(LocationsData l : response.body().getMessage()){

                        if(l.getSuburb().equals(selected_suburb))
                        locations.add(new LocationPojo(l.getSuburb(),l.getLocationName(),l.getLocationId(),"1"));
                    }

                    locationAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<LocationsResponse> call, Throwable t) {
            }
        });



    }

    private void displaySuburbs(String suburb) {

        LocationPojo l;
        locations.clear();

        for(String s : allSuburbs){
            String str = s.toLowerCase();
            if(str.contains(suburb.toLowerCase())){
                l = new LocationPojo(s,"","","0");
                locations.add(l);
            }
        }

        locationAdapter.notifyDataSetChanged();

    }

    private void getAllSuburbs() {

        Location l = RetroClient.getClient().create(Location.class);
        Call<SuburbsResponse> call = l.getSuburbs(session.getToken(),"" + session.getcurrentcity());
        call.enqueue(new Callback<SuburbsResponse>() {
            @Override
            public void onResponse(Call<SuburbsResponse> call, Response<SuburbsResponse> response) {

                if(response.code() == 200){

                    for(SuburbsData s : response.body().getMessage()){
                        allSuburbs.add(s.getSuburb());
                    }
                }
            }

            @Override
            public void onFailure(Call<SuburbsResponse> call, Throwable t) {
            }
        });

    }

    @Override
    public void onLocationSelected(String locationid, String locationtype, String locationame) {

        if(locationtype.equals("0")){
            et_suburb.setText(locationame);
            selected_suburb = locationame;
            locations.clear();
            locationAdapter.notifyDataSetChanged();
            et_location.requestFocus();
            getLocations("");
        }else{
            selected_location = locationame;
            et_location.setText(locationame);
            locations.clear();
            locationAdapter.notifyDataSetChanged();
        }

    }
}
