package com.tag.tai.tag.Fragments.AddSuggestions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tag.tai.tag.Common.Loader;
import com.tag.tai.tag.Common.LoaderControl;
import com.tag.tai.tag.Common.ProcessError;
import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Interfaces.Location;
import com.tag.tai.tag.Services.Requests.AddLocation.AddLocationRequest;
import com.tag.tai.tag.Services.Responses.AddLocation.AddLocationResponse;
import com.tag.tai.tag.Services.Responses.GetLocations.LocationsData;
import com.tag.tai.tag.Services.Responses.GetLocations.LocationsResponse;
import com.tag.tai.tag.Services.Responses.GetSuburbs.SuburbsData;
import com.tag.tai.tag.Services.Responses.GetSuburbs.SuburbsResponse;
import com.tag.tai.tag.Services.RetroClient;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 03-04-2018.
 */

public class AddLocationDialog extends DialogFragment{

    Spinner sp_suburb;
    ArrayList<String> allSuburbs;
    ArrayAdapter<String> suburbsAdapter;

    AutoCompleteTextView et_addlocation;
    TextView tv_location_note;
    ArrayList<String> allLocations;
    ArrayAdapter<String> locationsAdapter;

    SessionManager session;

    String selected_suburb = "";
    String selected_location = "";

    Button btn_addlocation;

    Loader loader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_addlocation,container,false);

        //loader = new Loader(getActivity(),this);

        sp_suburb = v.findViewById(R.id.sp_suburb);
        et_addlocation = v.findViewById(R.id.et_addlocation);
        tv_location_note = v.findViewById(R.id.tv_location_note);

        session = new SessionManager(getActivity());


        allSuburbs = new ArrayList<>();
        allSuburbs.add("Select a suburb");
        suburbsAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_ettext_clone,allSuburbs);
        sp_suburb.setAdapter(suburbsAdapter);
        sp_suburb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0){
                    selected_suburb = sp_suburb.getSelectedItem().toString();
                    et_addlocation.setVisibility(View.VISIBLE);
                    tv_location_note.setVisibility(View.VISIBLE);
                }else{
                    et_addlocation.setVisibility(View.GONE);
                    tv_location_note.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        allLocations = new ArrayList<>();
        locationsAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,allLocations);
        et_addlocation.setAdapter(locationsAdapter);

        et_addlocation.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                if(selected_location.isEmpty())
                    getLocations(et_addlocation.getText().toString());

                if(et_addlocation.getText().toString().isEmpty())
                    selected_location = "";
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        getLocations("");
        setSuburbs();


        btn_addlocation = v.findViewById(R.id.btn_addlocation);
        btn_addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selected_suburb.isEmpty() || selected_suburb.equals("Select a suburb")){
                    Toast.makeText(getActivity(), "Please select a suburb", Toast.LENGTH_SHORT).show();
                }else if(et_addlocation.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please fill in a location", Toast.LENGTH_SHORT).show();
                }else{
//                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,
//                            new Intent().putExtra("location",et_addlocation.getText().toString() + " - " + selected_suburb));
//
//                    AddLocationDialog.this.dismiss();

                    addLocation(selected_suburb,et_addlocation.getText().toString());
                }
            }
        });


        return v;
    }

    private void addLocation(final String selected_suburb, final String location) {

        AddLocationRequest request = new AddLocationRequest(selected_suburb,location,"" + session.getcurrentcity());

        Location loc = RetroClient.getClient().create(Location.class);
        Call<AddLocationResponse> call = loc.addLocation(session.getToken(),request);
        call.enqueue(new Callback<AddLocationResponse>() {
            @Override
            public void onResponse(Call<AddLocationResponse> call, Response<AddLocationResponse> response) {

                if(response.code()==200){

                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,
                            new Intent().putExtra("location",location + " - " + selected_suburb));

                    AddLocationDialog.this.dismiss();

                }else{
                        try {
                            ProcessError.processError(getActivity(),response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }

            }

            @Override
            public void onFailure(Call<AddLocationResponse> call, Throwable t) {
                Log.d(RetroClient.TAG, "onFailure: ");
            }
        });

    }


    private void getLocations(final String location) {

        allLocations.clear();
        locationsAdapter.notifyDataSetChanged();

        Location loc = RetroClient.getClient().create(Location.class);
        Call<LocationsResponse> call = loc.getLocations(session.getToken(),location,"" + session.getcurrentcity());
        call.enqueue(new Callback<LocationsResponse>() {
            @Override
            public void onResponse(Call<LocationsResponse> call, Response<LocationsResponse> response) {
                if(response.code() == 200){
                    allLocations.clear();
                    for(LocationsData l : response.body().getMessage()){
                        if(l.getSuburb().equals(selected_suburb))
                            allLocations.add(l.getLocationName());
                    }
                    locationsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<LocationsResponse> call, Throwable t) {
            }
        });
    }

    private void setSuburbs() {

            Location l = RetroClient.getClient().create(Location.class);
            Call<SuburbsResponse> call = l.getSuburbs(session.getToken(),"" + session.getcurrentcity());
            call.enqueue(new Callback<SuburbsResponse>() {
                @Override
                public void onResponse(Call<SuburbsResponse> call, Response<SuburbsResponse> response) {

                    if(response.code() == 200){

                        for(SuburbsData s : response.body().getMessage()){
                            allSuburbs.add(s.getSuburb());
                        }

                        suburbsAdapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onFailure(Call<SuburbsResponse> call, Throwable t) {

                }
            });



    }
}
