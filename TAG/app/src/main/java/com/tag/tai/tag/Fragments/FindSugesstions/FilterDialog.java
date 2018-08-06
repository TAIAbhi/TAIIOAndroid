package com.tag.tai.tag.Fragments.FindSugesstions;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Responses.GetAreas.AreaData;
import com.tag.tai.tag.Services.Responses.GetCities.CityData;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * Created by Jam on 03-04-2018.
 */

public class FilterDialog extends DialogFragment {


    ArrayList<String> filter_businessnames;
    ArrayList<String> filter_microcats;
    ArrayList<String> filter_location;
    ArrayList<String> filter_source;
    ArrayList<String> filter_contact;
    ArrayList<AreaData> filter_areadata;

    ArrayAdapter<String> business_adapter;
    ArrayAdapter<String> microcat_adapter;
    ArrayAdapter<String> location_adapter;
    ArrayAdapter<String> source_adapter;
    ArrayAdapter<String> contact_adapter;

    AutoCompleteTextView et_filter_by_source,et_filter_by_contact,et_filter_by_location,et_filter_by_microcat,et_filter_by_business;

    Button iv_refresh,iv_search;
    Switch switch_local;
    boolean reset_clicked = false;

    SessionManager session;


    LinearLayout ll_city_selector;
    TextView tv_cityname;
    ArrayList<CityData> allcities;
    ArrayList<String> cities = new ArrayList<>();
    ListPopupWindow citypopup;
    ArrayAdapter cityadapter;

    ArrayList<String> areas_title = new ArrayList<>();
    AreaData selected_areadata;

    public FilterDialog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_filter,container,false);

        session = new SessionManager(getActivity());

        iv_refresh = v.findViewById(R.id.btn_refresh);
        iv_search = v.findViewById(R.id.btn_search);

        filter_businessnames = getArguments().getStringArrayList("filter_businessnames");
        filter_microcats = getArguments().getStringArrayList("filter_microcats");
        filter_location = getArguments().getStringArrayList("filter_location");
        filter_source = getArguments().getStringArrayList("filter_source");
        filter_contact = getArguments().getStringArrayList("filter_contact");

        et_filter_by_business = v.findViewById(R.id.et_filter_by_business);
        et_filter_by_microcat = v.findViewById(R.id.et_filter_by_microcat);
        et_filter_by_location = v.findViewById(R.id.et_filter_by_location);
        et_filter_by_source = v.findViewById(R.id.et_filter_by_source);
        et_filter_by_contact = v.findViewById(R.id.et_filter_by_contact);


        switch_local = v.findViewById(R.id.switch_local);
        switch_local.setChecked(true);

        business_adapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_text,filter_businessnames);
        microcat_adapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_text,filter_microcats);
        location_adapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_text,filter_location);
        source_adapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_text,filter_source);
        contact_adapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_text,filter_contact);

        et_filter_by_business.setAdapter(business_adapter);
        et_filter_by_microcat.setAdapter(microcat_adapter);
        et_filter_by_location.setAdapter(location_adapter);
        et_filter_by_source.setAdapter(source_adapter);
        et_filter_by_contact.setAdapter(contact_adapter);

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.putExtra("business",et_filter_by_business.getText().toString());
                i.putExtra("microcat",et_filter_by_microcat.getText().toString());
                i.putExtra("location",et_filter_by_location.getText().toString());
                i.putExtra("islocal",switch_local.isChecked() + "");
                i.putExtra("source",et_filter_by_source.getText().toString());
                i.putExtra("contact",et_filter_by_contact.getText().toString());
                i.putExtra("reset_clicked","" + reset_clicked);
                i.putExtra("area_data",selected_areadata);

                getTargetFragment().onActivityResult(getTargetRequestCode(),0,i);

                FilterDialog.this.dismiss();

            }
        });

        switch_local.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                reset_clicked = false;
            }
        });

        iv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_filter_by_business.setText("");
                et_filter_by_microcat.setText("");
                et_filter_by_location.setText("");
                et_filter_by_source.setText("");
                et_filter_by_contact.setText("");

                reset_clicked = true;

                //Intent i = new Intent();
                //getTargetFragment().onActivityResult(getTargetRequestCode(),1,i);

                //FilterDialog.this.dismiss();
            }
        });

        //if(!session.getRole().equals("2") || !session.getRole().equals("3")){
        if(false){
            et_filter_by_source.setVisibility(GONE);
        }

        String selected_category = getArguments().getString("selected_category");
        if(selected_category.equals("1")){
            et_filter_by_microcat.setVisibility(GONE);
        }

//        et_filter_by_business.setText("");
//        et_filter_by_microcat.setText("");
//        et_filter_by_location.setText("");
//        et_filter_by_source.setText("");
//        et_filter_by_contact.setText("");


        String selectedBusiness = getArguments().getString("selectedBusiness");
        String selectedLocation = getArguments().getString("selectedLocation");
        String selectedIsLocal = getArguments().getString("selectedIsLocal");
        String selectedContactName = getArguments().getString("selectedContactName");
        String selectedSourceName = getArguments().getString("selectedSourceName");


        et_filter_by_location.setText(selectedLocation);
        et_filter_by_business.setText(selectedBusiness);
        et_filter_by_source.setText(selectedSourceName);
        et_filter_by_contact.setText(selectedContactName);

        if (selectedIsLocal != null && selectedIsLocal.equalsIgnoreCase("true")) {
            switch_local.setChecked(true);
        }else{
            switch_local.setChecked(false);
        }


        //setting cities data
        //city selector
        ll_city_selector = v.findViewById(R.id.ll_city_selector);
        tv_cityname = v.findViewById(R.id.tv_cityname);
        allcities = new ArrayList<>();
        filter_areadata = new ArrayList<>();
        citypopup = new ListPopupWindow(getActivity());
        cityadapter = new ArrayAdapter(getActivity(),R.layout.popup_dropdown_item,new ArrayList());
        citypopup.setAdapter(cityadapter);
        citypopup.setHeight(android.widget.ListPopupWindow.WRAP_CONTENT);
        citypopup.setWidth(android.widget.ListPopupWindow.WRAP_CONTENT);
        citypopup.setModal(true);
        citypopup.setAnchorView(ll_city_selector);
        citypopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                citypopup.dismiss();
                selected_areadata = filter_areadata.get(position);
                tv_cityname.setText(selected_areadata.getDdText());

            }
        });

        ArrayList<AreaData> areas = getArguments().getParcelableArrayList("filter_allareas");
        if(areas != null)
        filter_areadata.addAll(areas);
        for(AreaData a : filter_areadata){
            cityadapter.add(a.getDdText());
            areas_title.add(a.getDdText());

            if(a.getDdValue().equals("1")){
                selected_areadata = a;
                tv_cityname.setText(a.getDdText());

            }

        }

        cityadapter.notifyDataSetChanged();
        citypopup.setWidth(480);


//        tv_cityname.setText(selectedArea.getDdText());
//        selected_areadata = selectedArea;

        ll_city_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citypopup.show();
            }
        });

        return v;
    }
}
