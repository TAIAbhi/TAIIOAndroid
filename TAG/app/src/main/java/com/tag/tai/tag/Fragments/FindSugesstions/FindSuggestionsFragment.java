package com.tag.tai.tag.Fragments.FindSugesstions;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.tag.tai.tag.Activities.MainActivity;
import com.tag.tai.tag.Common.Loader;
import com.tag.tai.tag.Common.ProcessError;
import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.Fragments.AddSuggestions.AddSuggestionFragment;
import com.tag.tai.tag.Fragments.RequestSuggestions.RequestSuggestionsFragment;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Interfaces.Categories;
import com.tag.tai.tag.Services.Interfaces.FilterData;
import com.tag.tai.tag.Services.Interfaces.Location;
import com.tag.tai.tag.Services.Interfaces.RequestSuggestions;
import com.tag.tai.tag.Services.Interfaces.Suggestions;
import com.tag.tai.tag.Services.Requests.DeleteSuggestion.DeleteData;
import com.tag.tai.tag.Services.Responses.Categories.CategoryResponse;
import com.tag.tai.tag.Services.Responses.Categories.CategoryWithSubCategories;
import com.tag.tai.tag.Services.Responses.DeleteSuggestion.DeleteSuggestionResponse;
import com.tag.tai.tag.Services.Responses.GetAreas.AreaData;
import com.tag.tai.tag.Services.Responses.GetAreas.AreasResponse;
import com.tag.tai.tag.Services.Responses.GetBusiness.BusinessData;
import com.tag.tai.tag.Services.Responses.GetBusiness.GetBusinessResponse;
import com.tag.tai.tag.Services.Responses.GetCities.CityData;
import com.tag.tai.tag.Services.Responses.GetCities.CityResponse;
import com.tag.tai.tag.Services.Responses.GetContacts.ContactData;
import com.tag.tai.tag.Services.Responses.GetContacts.ContactResponse;
import com.tag.tai.tag.Services.Responses.GetLocations.LocationsData;
import com.tag.tai.tag.Services.Responses.GetLocations.LocationsResponse;
import com.tag.tai.tag.Services.Responses.GetSources.SourcesData;
import com.tag.tai.tag.Services.Responses.GetSources.SourcesResponse;
import com.tag.tai.tag.Services.Responses.GetSuggestions.SuggestionData;
import com.tag.tai.tag.Services.Responses.GetSuggestions.SuggestionsResponse;
import com.tag.tai.tag.Services.Responses.MicroCat.MicroCatData;
import com.tag.tai.tag.Services.Responses.MicroCat.MicroCatResponse;
import com.tag.tai.tag.Services.Responses.RequestSuggestions.RequestSuggestionsData;
import com.tag.tai.tag.Services.Responses.RequestSuggestions.RequestSuggestionsResponse;
import com.tag.tai.tag.Services.Responses.SubCategories.SubCatData;
import com.tag.tai.tag.Services.RetroClient;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.tag.tai.tag.Fragments.AddSuggestions.AddSuggestionFragment.ISA_COPY;
import static com.tag.tai.tag.Services.RetroClient.CONNETION_ERROR;
import static com.tag.tai.tag.Services.RetroClient.TAG;

/**
 * Created by Jam on 21-03-2018.
 */

public class FindSuggestionsFragment extends Fragment implements FindSuggestionsCalls {

    LinearLayout ll_holder_hangout, ll_holder_services, ll_holder_shopping;
    LinearLayout ll_my_suggestions, ll_all_suggestions;

    RecyclerView recyc_mysuggestions;
    ArrayList<SuggestionData> allsuggestions;
    ArrayList<SuggestionData> allsuggestionsresponse;
    SuggestionsListAdapter suggestionsAdapter;

    boolean isInRequestsMode = false;
    RecyclerView recyc_allrequestedsuggestions;
    ArrayList<RequestSuggestionsData> allrequests;
    RequestsAdapter requestsAdapter;

    ArrayList<SuggestionData> allsuggestiondata;
    ArrayList<SuggestionData> currentFilteredData;

    ArrayAdapter<String> dataAdapter, dataAdapterServices, shopAdapter;

    ImageView fab_add_suggestion;

    TextView tv_nosuggestion;

    SessionManager session;

    //filter components-----------------------------------------------------------------------------

    private final int FILTERDIALOGCODE = 88;

    ArrayList<String> filter_businessnames;
    ArrayList<String> filter_microcats;
    ArrayList<String> filter_location;
    ArrayList<String> filter_source;
    ArrayList<SourcesData> filter_source_data;
    ArrayList<String> filter_contact;
    ArrayList<ContactData> filter_contact_data;

    PopupMenu hangoutspopup, servicespopup, shoppingpopup;
    ArrayList<SubCatData> hangoutdata, servicesdata, shoppingdata;

    String selected_category = "1", selected_subcategory = "";
    int currentpageNumber, pageSize = 20, noOfRecord;
    int selectedMyAllCategory = 1;

    String selectedSourceID = null, selectedBusiness = null, selectedIsLocal = null, selectedLocation = null, selectedMicrocat = null, selectedContact = null;
    String selectedSourceName = null, selectedContactName = null, selectedAreaCode = null;

    //filter components end ------------------------------------------------------------------

    LinearLayout ll_city_selector;
    ArrayList<CityData> allcities;
    ArrayList<String> cities = new ArrayList<>();
    ListPopupWindow citypopup;
    ArrayAdapter cityadapter;

    ArrayList<AreaData> allareas;
    ArrayList<String> areas_title = new ArrayList<>();


    CardView cv_request_suggestions_option;
    TextView tv_request_suggestion, tv_view_requests;
    AreaData selectedCity, selectedSubArea;

    Loader loader;
    int loadcount = 1;
    int maxLoadCount = 6;

    boolean fromHomePage = false;
    private Bundle args;

    public FindSuggestionsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
        if (args != null) {
            selectedCity = getArguments().getParcelable("city");
            selectedSubArea = getArguments().getParcelable("subArea");
            if (selectedSubArea != null)
                selectedAreaCode = selectedSubArea.getDdValue();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_findsuggestions, container, false);

        loader = new Loader(getActivity(), (MainActivity) getActivity());

        cv_request_suggestions_option = v.findViewById(R.id.cv_request_suggestions_option);
        tv_request_suggestion = v.findViewById(R.id.tv_request_suggestion);
        tv_view_requests = v.findViewById(R.id.tv_view_requests);

        ll_holder_hangout = v.findViewById(R.id.ll_holder_hangout);
        ll_holder_services = v.findViewById(R.id.ll_holder_services);
        ll_holder_shopping = v.findViewById(R.id.ll_holder_shopping);

        ll_my_suggestions = v.findViewById(R.id.ll_my_suggestions);
        ll_all_suggestions = v.findViewById(R.id.ll_all_suggestions);

        session = new SessionManager(getActivity());

        fromHomePage = false;

        //city selector
        ll_city_selector = v.findViewById(R.id.ll_city_selector);
        allcities = new ArrayList<>();
        citypopup = new ListPopupWindow(getActivity());
        cityadapter = new ArrayAdapter(getActivity(), R.layout.popup_dropdown_item, new ArrayList());
        citypopup.setAdapter(cityadapter);
        citypopup.setHeight(ListPopupWindow.WRAP_CONTENT);
        citypopup.setWidth(ListPopupWindow.WRAP_CONTENT);
        citypopup.setModal(true);
        citypopup.setAnchorView(ll_city_selector);
        citypopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                citypopup.dismiss();
                ((TextView) ll_city_selector.findViewById(R.id.tv_cityname)).setText(allareas.get(position).getDdText());
                getAreaByPosition(position);
            }
        });
        //dropdown
        ll_city_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citypopup.show();
            }
        });
        allsuggestiondata = new ArrayList<>();
        currentFilteredData = new ArrayList<>();
        //-------------------------fetching locations

        //filter functions ------------------------------------------------------------------
        filter_businessnames = new ArrayList<>();
        filter_microcats = new ArrayList<>();
        filter_location = new ArrayList<>();
        filter_source = new ArrayList<>();
        filter_source_data = new ArrayList<>();
        filter_contact = new ArrayList<>();
        filter_contact_data = new ArrayList<>();


        getActivity().findViewById(R.id.tb_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment d = new FilterDialog();
                Bundle b = new Bundle();

                //sending dropdown data to dialog
                b.putString("selected_category", selected_category);

                b.putStringArrayList("filter_businessnames", filter_businessnames);
                b.putStringArrayList("filter_microcats", filter_microcats);
                b.putStringArrayList("filter_location", filter_location);
                b.putStringArrayList("filter_source", filter_source);
                b.putStringArrayList("filter_contact", filter_contact);
                b.putParcelableArrayList("filter_allareas", allareas);
/*
                selected_subcategory,
                        selectedContact,
                        selectedSourceID,
                        selectedBusiness,
                        selectedIsLocal,
                        selectedLocation,
                        selectedMicrocat,
*/


                b.putString("selectedContactName", selectedContactName);
                b.putString("selectedSourceName", selectedSourceName);
                b.putString("selectedBusiness", selectedBusiness);
                b.putString("selectedLocation", selectedLocation);
                b.putString("selectedIsLocal", selectedIsLocal);

                d.setArguments(b);
                d.setTargetFragment(FindSuggestionsFragment.this, FILTERDIALOGCODE);
                d.show(getActivity().getSupportFragmentManager(), "filter");
            }
        });

        //filter functions end------------------------------------------------------------------

        recyc_mysuggestions = v.findViewById(R.id.recyc_allmysuggestions);
        allsuggestions = new ArrayList<>();
        allsuggestionsresponse = new ArrayList<>();
        suggestionsAdapter = new SuggestionsListAdapter(allsuggestions, getActivity(), this);

        recyc_mysuggestions.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyc_mysuggestions.setItemAnimator(new DefaultItemAnimator());
        recyc_mysuggestions.setAdapter(suggestionsAdapter);

        //-------------

        ll_holder_hangout.setBackground(getResources().getDrawable(R.drawable.blue_curve_bg));
        if (getArguments() != null && getArguments().getBoolean("isFromNotification")) {
            selectedLocation = getArguments().getString("LocationId");
            selected_category = getArguments().getString("CatId");
            selected_subcategory = getArguments().getString("SubCatId");
            selectedMicrocat = getArguments().getString("MCId") == null ? "" : getArguments().getString("MCId");
            selectedMicrocat = selectedMicrocat.equals("0") ? "" : selectedMicrocat;
            getSuggestionsByFilter(selected_category, selected_subcategory, selectedContact, selectedSourceID, selectedBusiness, selectedIsLocal, selectedLocation, selectedMicrocat, 1, selectedAreaCode);

        } else if (getArguments() != null && getArguments().getBoolean("showRequestedSuggestions")) {
            getRequestedSuggestions();
        } else if (getArguments() != null && getArguments().getBoolean("fromHome")) {

            fromHomePage = true;

            selected_category = "" + getArguments().getInt("homeCategory");
            selected_subcategory = "" + getArguments().getInt("homeSubCategory");

            setSelectedColor(Integer.parseInt(selected_category));

            //getSuggestionsByFilter(selected_category,selected_subcategory,selectedContact,selectedSourceID,selectedBusiness,selectedIsLocal,selectedLocation,selectedMicrocat,1,selectedAreaCode);
        } else {
            //getSuggestionsByFilter(selected_category,selected_subcategory,selectedContact,selectedSourceID,selectedBusiness,selectedIsLocal,selectedLocation,selectedMicrocat,1,selectedAreaCode);
        }


        tv_nosuggestion = v.findViewById(R.id.tv_nosuggestion);
        fab_add_suggestion = v.findViewById(R.id.fab_add_suggestion);
        fab_add_suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId(R.id.menu_addsuggestions);

                //selected_subcategory = "1";

                addRequestSuggestions();

//                if(!selected_subcategory.isEmpty()){
//
//                        addRequestSuggestions();
//
//                }else{
//                    Toast.makeText(getActivity(), "Please select a category from the list.", Toast.LENGTH_SHORT).show();
//                }

            }
        });

        tv_request_suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRequestSuggestions();
            }
        });

        tv_view_requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRequestedSuggestions();
            }
        });

        //request suggestions base

        recyc_allrequestedsuggestions = v.findViewById(R.id.recyc_allrequestedsuggestions);
        allrequests = new ArrayList<>();
        requestsAdapter = new RequestsAdapter(allrequests, this, getActivity());

        recyc_allrequestedsuggestions.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyc_allrequestedsuggestions.setItemAnimator(new DefaultItemAnimator());
        recyc_allrequestedsuggestions.setAdapter(requestsAdapter);


        loadCategories(v); //loads categories in top 3 dropdowns
        loadFilterElements(); //loads data to use as auto fills in filter dialog
        setClicksForAllOrMySuggestions(); // sets clicks for top two tabs
        getAreasByLocation();

        return v;

    } //end of onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String cityId = null, areaCode = null;
        String category = null, subcategory = null;

        //showing selected area
        //sub-area
        if (selectedSubArea != null && !selectedSubArea.getDdValue().isEmpty()) {
            setSelectedAreaName(selectedSubArea.getDdText());
            cityId = selectedSubArea.getCityId();
            if (!TextUtils.isEmpty(selectedSubArea.getDdValue())) {
                areaCode = selectedSubArea.getDdValue();
                selectedAreaCode = selectedSubArea.getDdValue();
            }
        }
        //city
        else if (selectedCity != null) {
            setSelectedAreaName(selectedCity.getDdText());
            cityId = selectedCity.getCityId();
        }

        //setting subCategory and category
        if (args != null) {
            if (args.getInt("homeCategory") != 0)
                category = "" + args.getInt("homeCategory");
            if (args.getInt("homeSubCategory") != 0)
                subcategory = "" + args.getInt("homeSubCategory");
        }

        //showing categories
        if (cityId != null)
            session.setcurrentcity(Integer.parseInt(cityId));

        getSuggestionsByFilter(category, subcategory, null,
                null, null, null, null, null, 1, areaCode);
    }

    private void getAreaByPosition(int position) {

        if (allareas.get(position).getDdValue().equals("near")) {
            getNearMeData();
        } else if (allareas.get(position).getFilterType().equals("A")) {
            //if  areacode
            getDataWithAreaCode(position, allareas.get(position));
        } else if (allareas.get(position).getFilterType().equals("C")) {
            //if cities
            getDataWithCity(position, allareas.get(position));
        }

    }

    private void getDataWithCity(int position, AreaData ad) {

        selectedAreaCode = null;
        session.setcurrentcity(Integer.parseInt(ad.getDdValue()));
        ((TextView) ll_city_selector.findViewById(R.id.tv_cityname)).setText(ad.getDdText());

        getSuggestionsByFilter(selected_category,
                selected_subcategory,
                selectedContact,
                selectedSourceID,
                selectedBusiness,
                selectedIsLocal,
                selectedLocation,
                selectedMicrocat,
                1,
                selectedAreaCode);

    }

    private void getDataWithAreaCode(int position, AreaData ad) {

        session.setcurrentcity(Integer.parseInt(ad.getCityId()));
        selectedAreaCode = ad.getDdValue();
        ((TextView) ll_city_selector.findViewById(R.id.tv_cityname)).setText(ad.getDdText());

        getSuggestionsByFilter(selected_category,
                selected_subcategory,
                selectedContact,
                selectedSourceID,
                selectedBusiness,
                selectedIsLocal,
                selectedLocation,
                selectedMicrocat,
                1,
                selectedAreaCode);

    }

    //setting selected area
    private void setSelectedAreaName(String selectedAreaName) {
        ((TextView) ll_city_selector.findViewById(R.id.tv_cityname)).setText(selectedAreaName);
    }

    private void getNearMeData() {
        ((MainActivity) getActivity()).checkPermissionForLocation(MainActivity.LOAD_SUGGESTIONS);
    }

    private void getAreasByLocation() {
        ((MainActivity) getActivity()).checkPermissionForLocation(MainActivity.LOAD_AREAS);
    }


    private void loadServingAreas(String suburb, String lat_lon, String address, String cityid) {

        FilterData filter = RetroClient.getClient().create(FilterData.class);
        Call<AreasResponse> call = filter.getAreas(session.getToken(), session.getUserID(), suburb, lat_lon, address, cityid);
        call.enqueue(new Callback<AreasResponse>() {
            @Override
            public void onResponse(Call<AreasResponse> call, Response<AreasResponse> response) {

                if (response.code() == 200) {

                    allareas = new ArrayList<>();
                    cityadapter.clear();
                    areas_title.clear();

                    AreaData ad = new AreaData(0, "near", "Near me", "default", false);

                    allareas.add(ad);
                    allareas.addAll(response.body().getData());

                    AreaData selectedAreadata = null;

                    for (AreaData a : allareas) {
                        cityadapter.add(a.getDdText());
                        areas_title.add(a.getDdText());

                        if (a.isSelected()) {
                            //((TextView)ll_city_selector.findViewById(R.id.tv_cityname)).setText(a.getDdText());
                            selectedAreadata = a;
                        }
                    }

                    if (selectedAreadata != null && (selectedCity == null && selectedSubArea == null)) {

                        if (selectedAreadata.getDdValue().equals("near")) {

                            getNearMeData();

                        } else if (selectedAreadata.getFilterType().equals("A")) {

                            session.setcurrentcity(1);
                            selectedAreaCode = selectedAreadata.getDdValue();
                            ((TextView) ll_city_selector.findViewById(R.id.tv_cityname)).setText(selectedAreadata.getDdText());

                        } else if (selectedAreadata.getFilterType().equals("C")) {
                            session.setcurrentcity(Integer.parseInt(selectedAreadata.getDdValue()));
                            ((TextView) ll_city_selector.findViewById(R.id.tv_cityname)).setText(selectedAreadata.getDdText());
                        }
                        getSuggestionsByFilter(selected_category,
                                selected_subcategory,
                                selectedContact,
                                selectedSourceID,
                                selectedBusiness,
                                selectedIsLocal,
                                selectedLocation,
                                selectedMicrocat,
                                1,
                                selectedAreaCode);
                    }

                    cityadapter.notifyDataSetChanged();
                    citypopup.setWidth(480);


                } else {
                    loader.hideLoader();
                }

            }

            @Override
            public void onFailure(Call<AreasResponse> call, Throwable t) {
                Log.d(RetroClient.TAG, "onFailure: test");
            }
        });

    }

    private void addRequestSuggestions() {

        Bundle b = new Bundle();
        b.putString("selected_category", selected_category);
        b.putString("selected_subcategory", selected_subcategory);

        RequestSuggestionsFragment reqFrag = new RequestSuggestionsFragment();
        reqFrag.setArguments(b);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, reqFrag).addToBackStack(reqFrag.getClass().getSimpleName()).commit();

    }

    private void loadServingCities() {

        FilterData f = RetroClient.getClient().create(FilterData.class);
        Call<CityResponse> call = f.getCities(session.getToken());
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {

                if (response.code() == 200) {

                    if (!session.getlastknownlocation().isEmpty()) {
                        cityadapter.add("Near me - " + session.getlastknownlocation());
                        cities.add(session.getlastknownlocation());
                    }

                    allcities.addAll(response.body().getData());

                    for (CityData c : allcities) {
                        cityadapter.add(c.getCityName());
                        cities.add(c.getCityName());

                        if (session.getcurrentcity() == Integer.parseInt(c.getCityId())) {
                            ((TextView) ll_city_selector.findViewById(R.id.tv_cityname)).setText(c.getCityName());
                        }
                    }

                    cityadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });
    }

    private void setClicksForAllOrMySuggestions() {

        ll_all_suggestions.setBackground(getResources().getDrawable(R.drawable.allsuggestions_bg_selected));

        ll_my_suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMyAllCategory = 0;
                selectedContact = session.getUserID();

                ll_my_suggestions.setBackground(getResources().getDrawable(R.drawable.mysuggestion_bg_selected));
                ll_all_suggestions.setBackground(getResources().getDrawable(R.drawable.allsuggestions_bg_unselected));

                getSuggestionsByFilter(selected_category,
                        selected_subcategory,
                        selectedContact,
                        selectedSourceID,
                        selectedBusiness,
                        selectedIsLocal,
                        selectedLocation,
                        selectedMicrocat,
                        1, selectedAreaCode);

                Log.d(TAG, "onClick: ");
            }
        });

        ll_all_suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMyAllCategory = 1;
                selectedContact = "";

                ll_all_suggestions.setBackground(getResources().getDrawable(R.drawable.allsuggestions_bg_selected));
                ll_my_suggestions.setBackground(getResources().getDrawable(R.drawable.mysuggestion_bg_unselected));

                getSuggestionsByFilter(selected_category,
                        selected_subcategory,
                        selectedContact,
                        selectedSourceID,
                        selectedBusiness,
                        selectedIsLocal,
                        selectedLocation,
                        selectedMicrocat,
                        1, selectedAreaCode);
            }
        });
    }

    private void loadFilterElements() {
        loader.showLoader();

        //businessnamse
        final FilterData filterapi = RetroClient.getClient().create(FilterData.class);
        Call<GetBusinessResponse> call = filterapi.getBusinesses(session.getToken(), selected_category, selected_subcategory, session.getcurrentcity() + "");
        call.enqueue(new Callback<GetBusinessResponse>() {
            @Override
            public void onResponse(Call<GetBusinessResponse> call, Response<GetBusinessResponse> response) {

                if (response.code() == 200) {

                    filter_businessnames.clear();

                    for (BusinessData d : response.body().getData()) {
                        filter_businessnames.add(d.getBusinessName());
                    }

                    if (loadcount > maxLoadCount) {
                        loader.hideLoader();
                    } else {
                        loadcount++;
                    }

                }
            }

            @Override
            public void onFailure(Call<GetBusinessResponse> call, Throwable t) {
                if (loadcount > maxLoadCount) {
                    loader.hideLoader();
                } else {
                    loadcount++;
                }
            }
        });


        //microcats

        Categories c = RetroClient.getClient().create(Categories.class);
        Call<MicroCatResponse> callmicro = c.getSubCategory(session.getToken(), selected_subcategory);
        callmicro.enqueue(new Callback<MicroCatResponse>() {
            @Override
            public void onResponse(Call<MicroCatResponse> call, Response<MicroCatResponse> response) {

                if (response.code() == 200) {


                    if (loadcount > maxLoadCount) {
                        loader.hideLoader();
                    } else {
                        loadcount++;
                    }

                    filter_microcats.clear();
                    for (MicroCatData m : response.body().getMessage()) {
                        filter_microcats.add(m.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<MicroCatResponse> call, Throwable t) {
                if (loadcount > maxLoadCount) {
                    loader.hideLoader();
                } else {
                    loadcount++;
                }
            }
        });


        //locations
        Location loc = RetroClient.getClient().create(Location.class);
        Call<LocationsResponse> callloc = loc.getLocations(session.getToken(), "", "" + session.getcurrentcity());
        callloc.enqueue(new Callback<LocationsResponse>() {
            @Override
            public void onResponse(Call<LocationsResponse> call, Response<LocationsResponse> response) {

                if (response.code() == 200) {

                    if (loadcount > maxLoadCount) {
                        loader.hideLoader();
                    } else {
                        loadcount++;
                    }

                    if (response.code() == 200) {

                        filter_location.clear();

                        for (LocationsData l : response.body().getMessage()) {
                            filter_location.add(l.getLocSuburb() + " - " + l.getSuburb());
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<LocationsResponse> call, Throwable t) {
                if (loadcount > maxLoadCount) {
                    loader.hideLoader();
                } else {
                    loadcount++;
                }
            }
        });


        //if call if being made on change of dropdown
        if (maxLoadCount != 6) return;


        //sources
        Call<SourcesResponse> caller = filterapi.getSources(session.getToken());
        caller.enqueue(new Callback<SourcesResponse>() {
            @Override
            public void onResponse(Call<SourcesResponse> call, Response<SourcesResponse> response) {

                if (response.code() == 200) {

                    filter_source_data.clear();
                    filter_source.clear();

                    if (loadcount > maxLoadCount) {
                        loader.hideLoader();
                    } else {
                        loadcount++;
                    }

                    filter_source_data.addAll(response.body().getData());

                    for (SourcesData s : response.body().getData()) {
                        filter_source.add(s.getSource() + " - " + s.getContactNumber());
                    }

                }
            }

            @Override
            public void onFailure(Call<SourcesResponse> call, Throwable t) {
                if (loadcount > maxLoadCount) {
                    loader.hideLoader();
                } else {
                    loadcount++;
                }
            }
        });

        //contact
        Call<ContactResponse> call_con = filterapi.getContacts(session.getToken());
        call_con.enqueue(new Callback<ContactResponse>() {
            @Override
            public void onResponse(Call<ContactResponse> call, Response<ContactResponse> response) {

                if (response.code() == 200) {

                    filter_contact_data.clear();
                    filter_contact.clear();

                    if (loadcount > maxLoadCount) {
                        loader.hideLoader();
                    } else {
                        loadcount++;
                    }

                    filter_contact_data.addAll(response.body().getData());

                    for (ContactData c : response.body().getData()) {
                        filter_contact.add(c.getContact() + " - " + c.getContactNumber());
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactResponse> call, Throwable t) {
                if (loadcount > maxLoadCount) {
                    loader.hideLoader();
                } else {
                    loadcount++;
                }
            }
        });

    } //end of filter elements

    private void getSuggestionsByFilter(String categoryId, String subCategoryId, String contactId,
                                        String sourceId, String businessName, String isLocal,
                                        String location, String microcategory,
                                        final int pageNumber, String areaShortCode) {
        //loader.showLoader("Fetching Suggestions");
        loader.showLoader();
        currentpageNumber = pageNumber;

        Suggestions suggestions = RetroClient.getClient().create(Suggestions.class);
        Call<SuggestionsResponse> call =
                suggestions.getallsuggestionsbyfilterandcount(
                        session.getToken(), categoryId, subCategoryId,
                        contactId, sourceId, businessName,
                        isLocal, location, microcategory, "" + session.getcurrentcity(), "" + pageNumber, areaShortCode);

        call.enqueue(new Callback<SuggestionsResponse>() {
            @Override
            public void onResponse(Call<SuggestionsResponse> call, Response<SuggestionsResponse> response) {
                loader.hideLoader();

                if (response.code() == 200) {

                    if (response.body().getPageInfo() != null) {

                        tv_nosuggestion.setVisibility(GONE);

                        noOfRecord = response.body().getPageInfo().getNoOfRecord();

                        if (response.body().getPageInfo().getPageNumber() == 1)
                            allsuggestionsresponse.clear();

                        allsuggestionsresponse.addAll(response.body().getData());
                        setSuggestions(allsuggestionsresponse);

                    } else {

                        tv_nosuggestion.setVisibility(View.VISIBLE);

                        noOfRecord = 0;

                        allsuggestions.clear();
                        suggestionsAdapter.notifyDataSetChanged();
                    }

                } else {

                    try {
                        ProcessError.processError(getActivity(), response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SuggestionsResponse> call, Throwable t) {
                loader.hideLoader();
                if (getActivity() != null)
                    Toast.makeText(getActivity(), "" + CONNETION_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSuggestions(ArrayList<SuggestionData> allsuggestiondatalist) {

        allsuggestions.clear();

        for (SuggestionData s : allsuggestiondatalist) {

            if (s.getMicrocategoryId() == null) s.setMicrocategoryId("");
            if (s.getMicrocategory() == null) s.setMicrocategory("");

            allsuggestions.add(s);
        }

        suggestionsAdapter.notifyDataSetChanged();


    }

//    private void setSuggestions(ArrayList<SuggestionData> suggestions) {
//
//        allcategories.clear();
//
//        ArrayList<String> availableCategories = new ArrayList<>();
//        //unique categories - currently empty
//
//        for(SuggestionData d : suggestions){ //looping all data
//
//            if(d.getMicrocategoryId() == null)d.setMicrocategoryId("");
//            if(d.getMicrocategory() == null)d.setMicrocategory("");
//
//            String catid =  d.getCategoryId() + " - " + d.getSubCategoryId() + " - " + d.getMicrocategoryId();
//            //category id combo -forming unique id
//
//            if(availableCategories.indexOf(catid) < 0){
//                availableCategories.add(catid);
//                //adding uniqe ids in array
//
//                ArrayList<SuggestionData> allSuggestions_ = new ArrayList<>();
//
//                for(SuggestionData sd : suggestions){
//
//                    if(sd.getMicrocategory() == null)sd.setMicrocategory("");
//                    if(sd.getMicrocategoryId() == null)sd.setMicrocategoryId("");
//
//                    if(sd.getCategoryId().equals(d.getCategoryId()) && sd.getSubCategoryId().equals(d.getSubCategoryId()) && sd.getMicrocategoryId().equals(d.getMicrocategoryId())){
//                        allSuggestions_.add(sd);
//                    }
//                }
//
//                allcategories.add(new EachSuggestionCategory(d.getCategoryId(),d.getSubCategoryId(),d.getMicrocategoryId(),d.getCategory(),d.getSubCategory(),d.getMicrocategory(),allSuggestions_));
//            }
//        }
//
//        categoryAdapter.notifyDataSetChanged();
//    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.tb_filter).setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().findViewById(R.id.tb_filter).setVisibility(GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILTERDIALOGCODE) {

            if (resultCode == 0) {

                String sourceId_ = "";
                selectedSourceName = data.getStringExtra("source");
                for (SourcesData s : filter_source_data) {
                    if (!data.getStringExtra("source").isEmpty()
                            && data.getStringExtra("source").split("-").length > 1
                            && s.getContactNumber() != null
                            && s.getContactNumber().equals(data.getStringExtra("source").split("-")[1].trim())) {
                        sourceId_ = s.getSourceId();
                        selectedSourceID = sourceId_;
                    }
                }


                String contact = "";
                selectedContactName = data.getStringExtra("contact");
                for (ContactData c : filter_contact_data) {
                    if (!data.getStringExtra("contact").isEmpty() && data.getStringExtra("contact").split("-").length > 1 && c.getContactNumber().equals(data.getStringExtra("contact").split("-")[1].trim())) {
                        contact = c.getContactId();
                        selectedContact = contact;
                    }
                }

                if (selectedMyAllCategory == 0) {
                    selectedContact = session.getUserID();
                }

                selectedBusiness = data.getStringExtra("business");
                selectedIsLocal = data.getStringExtra("islocal");
                selectedLocation = data.getStringExtra("location");
                selectedMicrocat = data.getStringExtra("microcat");

                if (data.getStringExtra("reset_clicked").equalsIgnoreCase("true")) {
                    selectedIsLocal = "";
                }

                AreaData areaData = data.getParcelableExtra("area_data");
                if (areaData.getDdValue().equals("near")) {

                    getNearMeData();

                } else if (areaData.getFilterType().equals("A")) {

                    session.setcurrentcity(Integer.parseInt(areaData.getCityId()));
                    selectedAreaCode = areaData.getDdValue();
                    ((TextView) ll_city_selector.findViewById(R.id.tv_cityname)).setText(areaData.getDdText());

                } else if (areaData.getFilterType().equals("C")) {
                    selectedAreaCode = "";
                    session.setcurrentcity(Integer.parseInt(areaData.getDdValue()));
                    ((TextView) ll_city_selector.findViewById(R.id.tv_cityname)).setText(areaData.getDdText());
                }


                getSuggestionsByFilter(selected_category,
                        selected_subcategory,
                        selectedContact,
                        selectedSourceID,
                        selectedBusiness,
                        selectedIsLocal,
                        selectedLocation,
                        selectedMicrocat,
                        1, selectedAreaCode);

            } else if (resultCode == 1) {

                getSuggestionsByFilter(selected_category, selected_subcategory, selectedContact, "", "", "", "", "", 1, selectedAreaCode);

            }
        }
    }


    private void setSelectedColor(int index) {

        ll_holder_hangout.setBackground(getResources().getDrawable(R.drawable.black_curve_bg));
        ll_holder_services.setBackground(getResources().getDrawable(R.drawable.black_curve_bg));
        ll_holder_shopping.setBackground(getResources().getDrawable(R.drawable.black_curve_bg));

        if (index == 1) {
            ll_holder_hangout.setBackground(getResources().getDrawable(R.drawable.blue_curve_bg));
        } else if (index == 2) {
            ll_holder_services.setBackground(getResources().getDrawable(R.drawable.blue_curve_bg));
        } else if (index == 3) {
            ll_holder_shopping.setBackground(getResources().getDrawable(R.drawable.blue_curve_bg));
        }

    }

    @Override
    public void onEditSuggestionClicked(final String suggestionId, final int isaCopyOrEdit) {

        if (isaCopyOrEdit == ISA_COPY) {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            pushToEditPage(suggestionId, isaCopyOrEdit, "true");
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            pushToEditPage(suggestionId, isaCopyOrEdit, "false");

                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Have you visited this place using Tag about it?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        } else {
            pushToEditPage(suggestionId, isaCopyOrEdit, "false");
        }
    }

    private void pushToEditPage(String suggestionId, int isaCopyOrEdit, String isFromTag) {
        for (SuggestionData s : allsuggestions) {

            if (s.getSuggestionId() != null && s.getSuggestionId().equals(suggestionId)) {

                Bundle b = new Bundle();
                b.putString("edit", "true");
                b.putString("isfromtag", isFromTag);
                b.putInt("copyOrEdit", isaCopyOrEdit);
                b.putParcelable("suggestion", s);

                Fragment f = new AddSuggestionFragment();
                f.setArguments(b);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, f).addToBackStack("edit").commit();

            }
        }
    }

    @Override
    public void onEndOfRecycler() {

        if (noOfRecord > (currentpageNumber * 20)) {
            currentpageNumber++;
            getSuggestionsByFilter(selected_category, selected_subcategory, selectedContact, "", "", "", "", "", currentpageNumber, selectedAreaCode);
        }

    }

    @Override
    public void onAddRequestClicked(String requestedSuggestionsId) {

        for (RequestSuggestionsData r : allrequests) {

            if (r.getUid().equals(requestedSuggestionsId)) {

                Bundle b = new Bundle();
                b.putString("isARequest", "true");
                b.putParcelable("requestedSuggestion", r);

                Fragment f = new AddSuggestionFragment();
                f.setArguments(b);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, f).addToBackStack("request").commit();

            }
        }

    }

    @Override
    public void onDeleteSuggestionClicked(final String suggestionId, final int position) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you really want to delete this suggestion?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        loader.showLoader();

                        Suggestions suggestions = RetroClient.getClient().create(Suggestions.class);
                        Call<DeleteSuggestionResponse> call = suggestions.deleteSuggestion(session.getToken(), new DeleteData(suggestionId, "delete"));
                        call.enqueue(new Callback<DeleteSuggestionResponse>() {
                            @Override
                            public void onResponse(Call<DeleteSuggestionResponse> call, Response<DeleteSuggestionResponse> response) {
                                loader.hideLoader();

                                if (response.code() == 200) {

                                    allsuggestions.remove(position);
                                    suggestionsAdapter.notifyItemRemoved(position);

                                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<DeleteSuggestionResponse> call, Throwable t) {
                                loader.hideLoader();

                                Toast.makeText(getActivity(), RetroClient.CONNETION_ERROR, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();


    }

    private void loadCategories(View v) {

        loader.showLoader();

        //PopupMenu hangoutspopup,servicespopup,shoppingpopup;
        hangoutspopup = new PopupMenu(getActivity(), ll_holder_hangout);
        servicespopup = new PopupMenu(getActivity(), ll_holder_services);
        shoppingpopup = new PopupMenu(getActivity(), ll_holder_shopping);

        hangoutdata = new ArrayList<>();
        servicesdata = new ArrayList<>();
        shoppingdata = new ArrayList<>();

        Categories c = RetroClient.getClient().create(Categories.class);
        Call<CategoryResponse> call = c.getCategories(session.getToken(), "1");
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                if (loadcount > maxLoadCount) {
                    loader.hideLoader();
                } else {
                    loadcount++;
                }

                if (response.code() == 200) {

                    int x = 0;
                    String subCategoryName = "";
                    //hangouts
                    hangoutdata.add(new SubCatData("", "1", null, null, null, null));
                    hangoutspopup.getMenu().add(1, -1, 0, "Hangouts");
                    //services
                    servicesdata.add(new SubCatData("", "2", null, null, null, null));
                    servicespopup.getMenu().add(2, -1, 0, "Services");
                    //shopping
                    shoppingdata.add(new SubCatData("", "3", null, null, null, null));
                    shoppingpopup.getMenu().add(3, -1, 0, "Shopping");

                    for (CategoryWithSubCategories category : response.body().getData()) {
                        x = 0;
                        for (SubCatData subCategory : category.getSubCategories()) {
                            subCategoryName = getString(R.string.category_name_count, subCategory.getName(),
                                    subCategory.getSubCatCount());
                            x++;
                            switch (subCategory.getCatId()) {
                                //hangouts
                                case "1":
                                    hangoutdata.add(subCategory);
                                    hangoutspopup.getMenu().add(Integer.parseInt(subCategory.getCatId()),
                                            Integer.parseInt(subCategory.getSubCatId()), x,
                                            subCategoryName);
                                    break;
                                //services
                                case "2":
                                    servicesdata.add(subCategory);
                                    servicespopup.getMenu().add(Integer.parseInt(subCategory.getCatId()),
                                            Integer.parseInt(subCategory.getSubCatId()), x,
                                            subCategoryName);
                                    break;
                                //shopping
                                case "3":
                                    shoppingdata.add(subCategory);
                                    shoppingpopup.getMenu().add(Integer.parseInt(subCategory.getCatId()),
                                            Integer.parseInt(subCategory.getSubCatId()), x,
                                            subCategoryName);
                                    break;
                            }
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                if (loadcount > maxLoadCount) {
                    loader.hideLoader();
                } else {
                    loadcount++;
                }
            }
        });

        ll_holder_hangout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangoutspopup.show();
            }
        });

        ll_holder_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                servicespopup.show();
            }
        });

        ll_holder_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingpopup.show();
            }
        });

        //--------------------------------

        hangoutspopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                selected_category = "" + item.getGroupId();

                setSelectedColor(1);

                selected_subcategory = "";
                if (item.getItemId() != -1) selected_subcategory = item.getItemId() + "";

                initiatefilterLoad();

                if (isInRequestsMode) {
                    getRequestedSuggestions();
                } else {
                    getSuggestionsByFilter(selected_category, selected_subcategory,
                            selectedContact, "", "", "", "", "", 1, selectedAreaCode);
                }


                return true;
            }
        });

        servicespopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                selected_category = "" + item.getGroupId();

                setSelectedColor(2);

                selected_subcategory = "";
                if (item.getItemId() != -1) selected_subcategory = item.getItemId() + "";

                initiatefilterLoad();

                if (isInRequestsMode) {
                    getRequestedSuggestions();
                } else {
                    getSuggestionsByFilter(selected_category, selected_subcategory,
                            selectedContact, "", "", "", "", "", 1, selectedAreaCode);
                }

                return true;
            }
        });

        shoppingpopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                selected_category = "" + item.getGroupId();

                setSelectedColor(3);

                selected_subcategory = "";
                if (item.getItemId() != -1) selected_subcategory = item.getItemId() + "";

                initiatefilterLoad();

                if (isInRequestsMode) {
                    getRequestedSuggestions();
                } else {
                    getSuggestionsByFilter(selected_category, selected_subcategory,
                            selectedContact, "", "", "", "", "", 1, selectedAreaCode);
                }

                return true;
            }
        });
    }

    public void initiatefilterLoad() {

        loadcount = 1;
        maxLoadCount = 3;
        loadFilterElements();

    }

    private void getRequestedSuggestions() {

        loader.showLoader();

        isInRequestsMode = true;
        cv_request_suggestions_option.setVisibility(GONE);

        //Toast.makeText(getActivity(), "Requested Suggestions", Toast.LENGTH_SHORT).show();

        String requests_sub_category = selected_subcategory.isEmpty() ? "1" : selected_subcategory;

        RequestSuggestions r = RetroClient.getClient().create(RequestSuggestions.class);
        Call<RequestSuggestionsResponse> call = r.getRequestedSuggestions(session.getToken(), selected_category, requests_sub_category, session.getcurrentcity() + "");
        call.enqueue(new Callback<RequestSuggestionsResponse>() {
            @Override
            public void onResponse(Call<RequestSuggestionsResponse> call, Response<RequestSuggestionsResponse> response) {
                loader.hideLoader();

                if (response.code() == 200) {
                    allrequests.clear();

                    recyc_mysuggestions.setVisibility(GONE);
                    tv_nosuggestion.setVisibility(GONE);

                    if (response.body().getData().size() > 0) {

                        recyc_allrequestedsuggestions.setVisibility(VISIBLE);
                        allrequests.addAll(response.body().getData());
                        requestsAdapter.notifyDataSetChanged();
                    } else {
                        tv_nosuggestion.setText("No Requests found for this Category");
                        tv_nosuggestion.setVisibility(VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestSuggestionsResponse> call, Throwable t) {
                loader.hideLoader();
            }
        });

    }

    public void setSuggestionsByCurrentLocation(String location) {
        Log.d(RetroClient.TAG, "test: " + location);

        if (fromHomePage) return;

        if (location != null) {
            selectedAreaCode = "";
            selectedLocation = location;

            getSuggestionsByFilter(selected_category, selected_subcategory, selectedContact, selectedSourceID, selectedBusiness, selectedIsLocal, selectedLocation, selectedMicrocat, 1, selectedAreaCode);
        } else {
            getSuggestionsByFilter(selected_category, selected_subcategory, selectedContact, selectedSourceID, selectedBusiness, selectedIsLocal, selectedLocation, selectedMicrocat, 1, selectedAreaCode);
        }


    }

    public void setAreasByCurrentLocation(String location, String lat, String lon) {

        if (location.isEmpty()) {
            loadServingAreas("", "", "", "" + session.getcurrentcity());
        } else {
            String[] l = location.split(" ");
            if (l.length > 1) {
                l[l.length - 1] = "";
            }

            StringBuilder loc = new StringBuilder();
            for (String s : l) {
                loc.append(s);
            }

            loadServingAreas(loc.toString(), lat + "," + lon, loc.toString(), "" + session.getcurrentcity());
        }


    }


}
