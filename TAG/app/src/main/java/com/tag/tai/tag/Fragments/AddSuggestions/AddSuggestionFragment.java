package com.tag.tai.tag.Fragments.AddSuggestions;

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
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tag.tai.tag.Activities.MainActivity;
import com.tag.tai.tag.Common.Loader;
import com.tag.tai.tag.Common.ProcessError;
import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.Fragments.FindSugesstions.FindSuggestionsFragment;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Interfaces.Categories;
import com.tag.tai.tag.Services.Interfaces.FilterData;
import com.tag.tai.tag.Services.Interfaces.Location;
import com.tag.tai.tag.Services.Interfaces.Suggestions;
import com.tag.tai.tag.Services.Requests.AddSuggestion.SuggestionRequest;
import com.tag.tai.tag.Services.Requests.UpdateSuggestion.UpdateSuggestionRequest;
import com.tag.tai.tag.Services.Responses.AddSuggestion.AddSuggestionResponse;
import com.tag.tai.tag.Services.Responses.GetBusiness.BusinessData;
import com.tag.tai.tag.Services.Responses.GetBusiness.GetBusinessResponse;
import com.tag.tai.tag.Services.Responses.GetCities.CityData;
import com.tag.tai.tag.Services.Responses.GetCities.CityResponse;
import com.tag.tai.tag.Services.Responses.GetLocations.LocationsData;
import com.tag.tai.tag.Services.Responses.GetLocations.LocationsResponse;
import com.tag.tai.tag.Services.Responses.GetSuggestions.SuggestionData;
import com.tag.tai.tag.Services.Responses.MicroCat.MicroCatData;
import com.tag.tai.tag.Services.Responses.MicroCat.MicroCatResponse;
import com.tag.tai.tag.Services.Responses.RequestSuggestions.RequestSuggestionsData;
import com.tag.tai.tag.Services.Responses.SubCategories.SubCatData;
import com.tag.tai.tag.Services.Responses.SubCategories.SubCatResponse;
import com.tag.tai.tag.Services.RetroClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tag.tai.tag.Services.RetroClient.TAG;

/**
 * Created by Jam on 16-03-2018.
 */

public class AddSuggestionFragment extends Fragment {


    private boolean isOnEditMode = false;
    private boolean isBeingCopied = false;
    private SuggestionData editingSuggestionData;

    private boolean isRequestModeOn = false;
    private boolean isHomeRedirectOn = false;
    private RequestSuggestionsData requestData;


    private static final int LOCATIONREQUEST = 89;
    public static final int ISA_COPY = 0;
    public static final int ISAN_EDIT = 1;

    LinearLayout ll_holder_hangout, ll_holder_services, ll_holder_shopping;
    TextView tv_category;

    Switch switch_islocal, switch_ischain;

    CheckBox check_tag;


    TextInputLayout input_layout_subcat;
    EditText input_about, input_contact;
    Button btn_addsuggestion;

    RelativeLayout rl_formsholder;
    TextView tv_select_category;
    ImageView iv_success, iv_select;

    ImageView iv_cat_icon;

    SessionManager session;

    AutoCompleteTextView input_location;
    List<String> locations;
    ArrayList<LocationsData> locationsdata;
    ArrayAdapter<String> locationsadapter;

    AutoCompleteTextView input_businessname;
    List<String> filter_businessnames;
    ArrayList<BusinessData> filter_businessnames_data;
    ArrayAdapter<String> businessadapter;

    AutoCompleteTextView input_subcat;
    List<String> subcategories;
    ArrayList<MicroCatData> subcategoriesdata;
    ArrayAdapter<String> subcategoriesadapter;

    Spinner sp_microcat_groups;
    HashSet<String> microcategoriesgroup;
    ArrayAdapter<String> microcat_group_adapter;
    ArrayList<String> groups_microcategories;

    LinearLayout ll_sp_holder, ll_found_on_holder;

    String selected_category, selected_subcategory;

    ImageView iv_addlocation;

    PopupMenu hangoutspopup, servicespopup, shoppingpopup;
    ArrayList<SubCatData> hangoutdata, servicesdata, shoppingdata;

    final String SELECT_GROUP = "Select category group";

    Loader loader;
    int loadcount = 1;

    LinearLayout ll_city_selector;
    ArrayList<CityData> allcities;
    ArrayList<String> cities = new ArrayList<>();
    ListPopupWindow citypopup;
    ArrayAdapter cityadapter;

    TextView tv_cityname;
    TextView tv_suggestion_by;

    String selected_locationId = "";

    public AddSuggestionFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragement_addsuggestions, container, false);

        ((MainActivity) getActivity()).bottomNavigationView.getMenu().findItem(R.id.menu_addsuggestions).setChecked(true);


        loader = new Loader(getActivity(), (MainActivity) getActivity());

        tv_category = v.findViewById(R.id.tv_category);
        iv_cat_icon = v.findViewById(R.id.iv_cat_icon);

        btn_addsuggestion = v.findViewById(R.id.btn_addsuggestion);
        btn_addsuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageValidation()) {

                    if (isOnEditMode && !isBeingCopied) {
                        updateSuggestion();
                    } else {
                        saveSuggestion();
                    }
                }
            }
        });

        ll_holder_hangout = v.findViewById(R.id.ll_holder_hangout);
        ll_holder_services = v.findViewById(R.id.ll_holder_services);
        ll_holder_shopping = v.findViewById(R.id.ll_holder_shopping);

        input_about = v.findViewById(R.id.input_about);
        input_businessname = v.findViewById(R.id.input_businessname);
        input_contact = v.findViewById(R.id.input_contact);

        input_contact.setCompoundDrawablesWithIntrinsicBounds(null, null, getActivity().getResources().getDrawable(R.drawable.contact_add), null);
        input_contact.setOnTouchListener(getOnTouchListener());

        ll_sp_holder = v.findViewById(R.id.ll_sp_holder);
        ll_found_on_holder = v.findViewById(R.id.ll_found_on_holder);

        session = new SessionManager(getActivity());

        rl_formsholder = v.findViewById(R.id.rl_formsholder);
        tv_select_category = v.findViewById(R.id.tv_select_category);
        iv_success = v.findViewById(R.id.tag_success);
        iv_select = v.findViewById(R.id.tag_select);

        switch_islocal = v.findViewById(R.id.switch_islocal);
        switch_ischain = v.findViewById(R.id.switch_ischain);
        check_tag = v.findViewById(R.id.check_tag);


        input_location = v.findViewById(R.id.input_location);
        locations = new ArrayList<>();
        locationsdata = new ArrayList();
        setLocations("");
        locationsadapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, new ArrayList<String>());
        input_location.setAdapter(locationsadapter);
        input_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "" + locationsdata.get(position).getLocationName(), Toast.LENGTH_SHORT).show();
            }
        });

        switch_islocal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                if (isChecked)
                    tv_cityname.setVisibility(View.GONE);
                else
                    tv_cityname.setVisibility(View.VISIBLE);

            }
        });


        int lastloc = session.getcurrentcity();
        if (lastloc == 2) {
            //input_location.setHint("Location (Pune only)");
        }

//        subcategory listener


        input_layout_subcat = v.findViewById(R.id.input_layout_subcat);
        input_subcat = v.findViewById(R.id.input_subcat);


        subcategories = new ArrayList<>();
        subcategoriesadapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, subcategories);
        input_subcat.setAdapter(subcategoriesadapter);


        sp_microcat_groups = v.findViewById(R.id.sp_subcat_groups);
        microcategoriesgroup = new HashSet<String>();
        subcategoriesdata = new ArrayList();

        groups_microcategories = new ArrayList<>();
        microcat_group_adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_ettext_clone, groups_microcategories);
        sp_microcat_groups.setAdapter(microcat_group_adapter);

        input_subcat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    //if categories go out of focus and temp_subcats dont have data which means
                    // - there are no available groups for the category entered by user
                    // in this case  we load all groups and sk user to select a group!
                    ll_sp_holder.setVisibility(View.VISIBLE);

                }
            }
        });

        input_subcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //this pertains to microcategories


                String selectedelement = "";
                int x = 0;

                for (String s : microcategoriesgroup) {

                    if (s.equals(input_subcat.getText().toString().split("©")[0].trim()))
                        selectedelement = s;

                    x++;
                }

                if (!selectedelement.equals("")) {
                    ll_sp_holder.setVisibility(View.VISIBLE);
//                    String tempsubcat = selectedelement;
//                    groups_microcategories.clear();
//                    groups_microcategories.add(tempsubcat);
//                    microcat_group_adapter.notifyDataSetChanged();

                    int pos = groups_microcategories.indexOf(selectedelement);
                    sp_microcat_groups.setSelection(pos);
                }

                if (input_subcat.getText().toString().split("©").length > 1)
                    input_subcat.setText(input_subcat.getText().toString().split("©")[1].trim());
                else
                    input_subcat.setText("");

            }
        });


        loadCategories(v);

//        StateListDrawable switch_islocal_handler = new StateListDrawable();
//        switch_islocal_handler.addState(new int[]{android.R.attr.state_checked},new BitmapDrawable(getActivity().getResources(), BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.isachain_enabled)));
//        switch_islocal_handler.addState(new int[]{},new BitmapDrawable(getActivity().getResources(), BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.isachain_enabled)));
//        switch_ischain.setTrackDrawable(switch_islocal_handler);

        tv_cityname = v.findViewById(R.id.tv_cityname);
        tv_suggestion_by = v.findViewById(R.id.tv_suggestion_by);


        iv_addlocation = v.findViewById(R.id.iv_addlocation);
        iv_addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment d = new AddLocationDialog();
                d.setTargetFragment(AddSuggestionFragment.this, LOCATIONREQUEST);
                d.show(getActivity().getSupportFragmentManager(), "location");

            }
        });


        if (getArguments() != null) {

            if (getArguments().getString("edit") != null && getArguments().getString("edit").equals("true")) {

                editingSuggestionData = getArguments().getParcelable("suggestion");

                if (getArguments().getInt("copyOrEdit") == ISA_COPY) isBeingCopied = true;

                if (isBeingCopied) ll_found_on_holder.setVisibility(View.VISIBLE);
                if (isBeingCopied)
                    tv_suggestion_by.setText("Suggestion by - " + session.getContactName());
                if (isBeingCopied) tv_suggestion_by.setVisibility(View.VISIBLE);

                setupEditPage();
            } else if (getArguments().getString("isARequest") != null && getArguments().getString("isARequest").equals("true")) {

                requestData = getArguments().getParcelable("requestedSuggestion");
                setUpRequestedSuggestion();
            } else if (getArguments().getBoolean("fromHome") && getArguments().getBoolean("addSuggestion")) {

                isHomeRedirectOn = true;
                setUpHomeScreenReddirect("" + getArguments().getInt("homeCategory"), "" + getArguments().getInt("homeSubCategory"), getArguments().getString("homeSubCategoryText"));

            }
        }

        filter_businessnames = new ArrayList<>();
        filter_businessnames_data = new ArrayList<>();
        businessadapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, new ArrayList<String>());
        input_businessname.setAdapter(businessadapter);

        input_businessname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                input_contact.setText(filter_businessnames_data.get(filter_businessnames.indexOf(input_businessname.getText().toString())).getBusinessContact());

                boolean isLocal = Boolean.valueOf(filter_businessnames_data.get(filter_businessnames.indexOf(input_businessname.getText().toString())).getIsLocal());
                boolean isSingle = Boolean.valueOf(filter_businessnames_data.get(filter_businessnames.indexOf(input_businessname.getText().toString())).getIsachain());

                switch_islocal.setChecked(isLocal);
                switch_ischain.setChecked(isSingle);

                input_location.setText(filter_businessnames_data.get(filter_businessnames.indexOf(input_businessname.getText().toString())).getLocation());
            }
        });


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

                ((TextView) ll_city_selector.findViewById(R.id.tv_citynamee)).setText(allcities.get(position).getCityName());
                tv_cityname.setText(allcities.get(position).getCityName());
                session.setcurrentcity(Integer.parseInt(allcities.get(position).getCityId()));
                citypopup.dismiss();

                if (isOnEditMode)
                    editingSuggestionData.setCityId(allcities.get(position).getCityId());

                setLocations("");

            }
        });

        ll_city_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citypopup.show();
            }
        });

        loadServingCities();

        //end of city selector

        return v;

    } //end of on create

    public View.OnTouchListener getOnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (input_contact.getRight() - input_contact.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        checkContactsPermissions();
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private void checkContactsPermissions() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS},
                    1);
        } else {
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

            case LOCATIONREQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    input_location.setText(data.getStringExtra("location"));
                }
            case 7:
                if (resultCode == Activity.RESULT_OK) {

                    Uri uri;
                    Cursor cursor1, cursor2;
                    String TempNameHolder, TempNumberHolder, TempContactID, IDresult = "";
                    int IDresultHolder;

                    uri = data.getData();

                    cursor1 = getActivity().getContentResolver().query(uri, null, null, null, null);

                    if (cursor1.moveToFirst()) {

                        TempNameHolder = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        TempContactID = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));

                        IDresult = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        IDresultHolder = Integer.valueOf(IDresult);

                        if (IDresultHolder == 1) {

                            cursor2 = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + TempContactID, null, null);

                            while (cursor2.moveToNext()) {

                                TempNumberHolder = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                if (TempNumberHolder.length() > 10)
                                    TempNumberHolder = TempNumberHolder.substring(TempNumberHolder.length() - 10, TempNumberHolder.length());

                                input_contact.setText(TempNumberHolder);

                            }
                        }

                    }
                }
                break;
        }
    }

    private void updateSuggestion() {
        loader.showLoader();

        String suggestionId = editingSuggestionData.getSuggestionId();
        String contactId = session.getUserID();
        String catId = selected_category;
        String subCategoryId = selected_subcategory;
        String microcategory = sp_microcat_groups.getSelectedItem() + " © " + input_subcat.getText().toString();
        String businessName = input_businessname.getText().toString();
        String citiLevelBusiness = "" + switch_islocal.isChecked();
        String businessContact = input_contact.getText().toString();
        String location = input_location.getText().toString();
        String comments = input_about.getText().toString();
        String isAChain = "" + switch_ischain.isChecked();


        UpdateSuggestionRequest r = new UpdateSuggestionRequest(suggestionId, contactId, catId,
                subCategoryId, microcategory, businessName,
                citiLevelBusiness, businessContact, location,
                comments, isAChain, session.getcurrentcity() + "", "2");

        Suggestions s = RetroClient.getClient().create(Suggestions.class);
        Call<AddSuggestionResponse> call = s.updatesuggestion(session.getToken(), r);
        call.enqueue(new Callback<AddSuggestionResponse>() {
            @Override
            public void onResponse(Call<AddSuggestionResponse> call, Response<AddSuggestionResponse> response) {
                loader.hideLoader();

                if (response.code() == 200) {
                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    if (response.body().getAction().toLowerCase().equals("success")) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new FindSuggestionsFragment()).commit();
                    }

                } else {
                    //Toast.makeText(getActivity(), RetroClient.CONNETION_ERROR, Toast.LENGTH_SHORT).show();
                    try {
                        ProcessError.processError(getActivity(), response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddSuggestionResponse> call, Throwable t) {
                loader.hideLoader();

                if (getActivity() == null) return;
                Toast.makeText(getActivity(), "" + RetroClient.CONNETION_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBusinesses() {
        //businessnamse
        final FilterData filterapi = RetroClient.getClient().create(FilterData.class);
        Call<GetBusinessResponse> call = filterapi.getBusinesses(session.getToken(), selected_category, selected_subcategory, "" + session.getcurrentcity());
        call.enqueue(new Callback<GetBusinessResponse>() {
            @Override
            public void onResponse(Call<GetBusinessResponse> call, Response<GetBusinessResponse> response) {

                filter_businessnames.clear();
                filter_businessnames_data.clear();
                businessadapter.clear();

                for (BusinessData d : response.body().getData()) {
                    businessadapter.add(d.getBusinessName());
                    filter_businessnames.add(d.getBusinessName());
                    filter_businessnames_data.add(d);
                }

                //businessadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GetBusinessResponse> call, Throwable t) {

            }
        });

    }

    private void loadServingCities() {

        FilterData f = RetroClient.getClient().create(FilterData.class);
        Call<CityResponse> call = f.getCities(session.getToken());
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.code() == 200) {
                    allcities.addAll(response.body().getData());

                    for (CityData c : allcities) {
                        cityadapter.add(c.getCityName());
                        cities.add(c.getCityName());

                        if (session.getcurrentcity() == Integer.parseInt(c.getCityId())) {
                            ((TextView) ll_city_selector.findViewById(R.id.tv_citynamee)).setText(c.getCityName());
                            tv_cityname.setText(c.getCityName());
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

    private void setupEditPage() {

        isOnEditMode = true;

        //((BottomNavigationView)getActivity().findViewById(R.id.bottom_nav)).setSelectedItemId(R.id.menu_addsuggestions);
        rl_formsholder.setVisibility(View.VISIBLE);
        tv_select_category.setVisibility(View.GONE);
        iv_select.setVisibility(View.GONE);

        SuggestionData s = getArguments().getParcelable("suggestion");

        input_businessname.setText(s.getBusinessName());
        input_location.setText(s.getLocation());
        input_contact.setText(s.getBusinessContact().split(",")[0]);


        input_about.setText(s.getContactComment());

        if (isBeingCopied) {

        }

        if (s.getCitiLevelBusiness().equals("true")) {
            switch_islocal.setChecked(true);
        }

        if (s.getIsAChain() != null && s.getIsAChain().equals("true")) {
            switch_ischain.setChecked(true);
        }

        if (getArguments().getString("isfromtag").equalsIgnoreCase("true"))
            check_tag.setChecked(true);


        selected_category = s.getCategoryId();
        selected_subcategory = s.getSubCategoryId();
        tv_category.setText(s.getSubCategory());


        setSelectedColor(Integer.parseInt(selected_category));

        if (selected_category.equals("1")) {
            ll_sp_holder.setVisibility(View.GONE);
            input_layout_subcat.setVisibility(View.GONE);
        } else {
            ll_sp_holder.setVisibility(View.VISIBLE);
            input_layout_subcat.setVisibility(View.VISIBLE);
        }

        loadMicroCategories(s.getSubCategoryId());

        if (isBeingCopied) {
            btn_addsuggestion.setText("ADD SUGGESTION");
        } else {
            btn_addsuggestion.setText("UPDATE SUGGESTION");
        }

    }

    private void setUpHomeScreenReddirect(String category, String subcategory, String subcategoryname) {

        //((BottomNavigationView)getActivity().findViewById(R.id.bottom_nav)).setSelectedItemId(R.id.menu_addsuggestions);
        rl_formsholder.setVisibility(View.VISIBLE);
        tv_select_category.setVisibility(View.GONE);
        iv_select.setVisibility(View.GONE);


        selected_category = category;
        selected_subcategory = subcategory;
        tv_category.setText(subcategoryname);

        setSelectedColor(Integer.parseInt(selected_category));

        if (selected_category.equals("1")) {
            ll_sp_holder.setVisibility(View.GONE);
            input_layout_subcat.setVisibility(View.GONE);
        } else {
            ll_sp_holder.setVisibility(View.VISIBLE);
            input_layout_subcat.setVisibility(View.VISIBLE);
        }

        loadMicroCategories(subcategory);
        loadBusinesses();

    }


    private void setUpRequestedSuggestion() {

        isRequestModeOn = true;

        //((BottomNavigationView)getActivity().findViewById(R.id.bottom_nav)).setSelectedItemId(R.id.menu_addsuggestions);
        rl_formsholder.setVisibility(View.VISIBLE);
        tv_select_category.setVisibility(View.GONE);
        iv_select.setVisibility(View.GONE);

        RequestSuggestionsData r = requestData;

        input_location.setText(r.getLocation());

//        if(s.getCitiLevelBusiness().equals("true")){
//            switch_islocal.setChecked(true);
//        }
//
//        if(s.getIsAChain()!=null && s.getIsAChain().equals("true")){
//            switch_ischain.setChecked(true);
//        }

        selected_category = r.getCategoryId();
        selected_subcategory = r.getSubCategoryId();
        tv_category.setText(r.getSubCategory());

        if (selected_category != null)
            setSelectedColor(Integer.parseInt(selected_category));

        if (selected_category != null && selected_category.equals("1")) {
            ll_sp_holder.setVisibility(View.GONE);
            input_layout_subcat.setVisibility(View.GONE);
        } else {
            ll_sp_holder.setVisibility(View.VISIBLE);
            input_layout_subcat.setVisibility(View.VISIBLE);
        }

        loadMicroCategories(r.getSubCategoryId());

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
        Call<SubCatResponse> call = c.getCategory(session.getToken(), "1");
        call.enqueue(new Callback<SubCatResponse>() {
            @Override
            public void onResponse(Call<SubCatResponse> call, Response<SubCatResponse> response) {
                int x = 0;

                if (loadcount > 2) {
                    loader.hideLoader();
                } else {
                    loadcount++;
                }

                if (response.code() == 200) {

                    for (SubCatData s : response.body().getMessage()) {
                        hangoutdata.add(s);
                        hangoutspopup.getMenu().add(Integer.parseInt(s.getCatId()), Integer.parseInt(s.getSubCatId()), x, s.getName());
                        x++;
                    }

                } else {

                }

            }

            @Override
            public void onFailure(Call<SubCatResponse> call, Throwable t) {
                if (loadcount > 2) {
                    loader.hideLoader();
                } else {
                    loadcount++;
                }
            }
        });

        call = c.getCategory(session.getToken(), "2");
        call.enqueue(new Callback<SubCatResponse>() {
            @Override
            public void onResponse(Call<SubCatResponse> call, Response<SubCatResponse> response) {

                if (loadcount > 2) {
                    loader.hideLoader();
                } else {
                    loadcount++;
                }

                if (response.code() == 200) {
                    int x = 0;
                    for (SubCatData s : response.body().getMessage()) {
                        servicesdata.add(s);
                        servicespopup.getMenu().add(Integer.parseInt(s.getCatId()), Integer.parseInt(s.getSubCatId()), x, s.getName());
                        x++;
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<SubCatResponse> call, Throwable t) {
                if (loadcount > 2) {
                    loader.hideLoader();
                } else {
                    loadcount++;
                }
            }
        });

        call = c.getCategory(session.getToken(), "3");
        call.enqueue(new Callback<SubCatResponse>() {
            @Override
            public void onResponse(Call<SubCatResponse> call, Response<SubCatResponse> response) {

                if (loadcount > 2) {
                    loader.hideLoader();
                } else {
                    loadcount++;
                }

                if (response.code() == 200) {
                    int x = 0;
                    for (SubCatData s : response.body().getMessage()) {
                        shoppingdata.add(s);
                        shoppingpopup.getMenu().add(Integer.parseInt(s.getCatId()), Integer.parseInt(s.getSubCatId()), x, s.getName());
                        x++;
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<SubCatResponse> call, Throwable t) {
                if (loadcount > 2) {
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
                tv_category.setText(item.toString());
                selected_category = "" + item.getGroupId();
                selected_subcategory = "" + item.getItemId();

                ll_sp_holder.setVisibility(View.GONE);
                input_layout_subcat.setVisibility(View.GONE);

                setSelectedColor(1);

                rl_formsholder.setVisibility(View.VISIBLE);
                tv_select_category.setVisibility(View.GONE);
                iv_select.setVisibility(View.GONE);

                if (hangoutdata.get(item.getOrder()).getIsLocal().equals("true"))
                    setLocalSwitch(true);
                else
                    setLocalSwitch(false);


                loadBusinesses();

                return true;
            }
        });

        servicespopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                tv_category.setText(item.toString());
                selected_category = "" + item.getGroupId();
                selected_subcategory = "" + item.getItemId();

                ll_sp_holder.setVisibility(View.VISIBLE);
                input_layout_subcat.setVisibility(View.VISIBLE);


                setSelectedColor(2);
                loadMicroCategories(selected_subcategory);

                rl_formsholder.setVisibility(View.VISIBLE);
                tv_select_category.setVisibility(View.GONE);
                iv_select.setVisibility(View.GONE);

                if (servicesdata.get(item.getOrder()).getIsLocal().equals("true"))
                    setLocalSwitch(true);
                else
                    setLocalSwitch(false);

                loadBusinesses();

                return true;
            }
        });

        shoppingpopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                tv_category.setText(item.toString());
                selected_category = "" + item.getGroupId();
                selected_subcategory = "" + item.getItemId();

                ll_sp_holder.setVisibility(View.VISIBLE);
                input_layout_subcat.setVisibility(View.VISIBLE);


                setSelectedColor(3);
                loadMicroCategories(selected_subcategory);

                rl_formsholder.setVisibility(View.VISIBLE);
                tv_select_category.setVisibility(View.GONE);
                iv_select.setVisibility(View.GONE);

                if (shoppingdata.get(item.getOrder()).getIsLocal().equals("true"))
                    setLocalSwitch(true);
                else
                    setLocalSwitch(false);

                loadBusinesses();

                return true;
            }
        });

    }

    private void setLocalSwitch(boolean isLocal) {

        Log.d(TAG, "setLocalSwitch: " + isLocal);

        switch_islocal.setChecked(isLocal);

        if (isLocal) {
            tv_cityname.setVisibility(View.GONE);
        } else {
            tv_cityname.setVisibility(View.VISIBLE);
        }

    }

    private void saveSuggestion() {

        loader.showLoader("Saving details.");

        String contactId = session.getUserID();
        String catId = selected_category;
        String subCategoryId = selected_subcategory;
        String microcategory = sp_microcat_groups.getSelectedItem() + " © " + input_subcat.getText().toString();
        microcategory = microcategory.contains("null") ? "0" : microcategory;
        String businessName = input_businessname.getText().toString();
        String citiLevelBusiness = "" + switch_islocal.isChecked();
        String businessContact = input_contact.getText().toString();
        String location = input_location.getText().toString();
        String comments = input_about.getText().toString();
        String isAChain = "" + switch_ischain.isChecked();
        String locationId = selected_locationId;

        SuggestionRequest r = new SuggestionRequest(contactId, catId, subCategoryId, microcategory, businessName, citiLevelBusiness
                , businessContact, location, comments, isAChain, session.getcurrentcity() + "", "2");

        Suggestions s = RetroClient.getClient().create(Suggestions.class);
        Call<AddSuggestionResponse> call = s.savesuggestion(session.getToken(), r);
        call.enqueue(new Callback<AddSuggestionResponse>() {
            @Override
            public void onResponse(Call<AddSuggestionResponse> call, Response<AddSuggestionResponse> response) {

                loader.hideLoader();

                if (response.code() == 200) {

                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    if (response.body().getAction().toLowerCase().equals("success"))
                        onAddSuggestionSuccessful();

                } else {
                    try {
                        ProcessError.processError(getActivity(), response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddSuggestionResponse> call, Throwable t) {
                loader.hideLoader();

                if (getActivity() == null) return;
                Toast.makeText(getActivity(), "" + RetroClient.CONNETION_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void onAddSuggestionSuccessful() {

        selected_category = "";
        selected_subcategory = "";
        //sp_microcat_groups.getSelectedItem() + " © " +
        input_subcat.setText("");
        input_businessname.setText("");
        switch_islocal.setChecked(false);
        input_contact.setText("");
        input_location.setText("");
        input_about.setText("");
        switch_ischain.setChecked(false);

        rl_formsholder.setVisibility(View.GONE);

        tv_select_category.setVisibility(View.VISIBLE);
        tv_select_category.setText("Suggestion addded successfully");
        iv_success.setVisibility(View.VISIBLE);


        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {

                if (getActivity() == null) return;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        iv_select.setVisibility(View.VISIBLE);
                        iv_success.setVisibility(View.GONE);
                        tv_select_category.setText("Please select a category");

                    }
                });
            }
        }, 2000);
    }

    private boolean pageValidation() {

        String microcategory = input_subcat.getText().toString();
        int microcategoryGroup = sp_microcat_groups.getSelectedItemPosition();
        String businessName = input_businessname.getText().toString();
        String businessContact = input_contact.getText().toString();
        String location = input_location.getText().toString();
        String comments = input_about.getText().toString();


        if (businessName.isEmpty()) {
            Toast.makeText(getActivity(), "Please add a business name", Toast.LENGTH_SHORT).show();
        } else if (location.isEmpty()) {
            Toast.makeText(getActivity(), "Please add a Location", Toast.LENGTH_SHORT).show();
        } else if (microcategoryGroup == 0) {
            Toast.makeText(getActivity(), "Please select a category group", Toast.LENGTH_SHORT).show();
        } else if (selected_category != null && !selected_category.equals("1") && microcategory.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in a sub catgory", Toast.LENGTH_SHORT).show();
        } else if (comments.isEmpty()) {
            Toast.makeText(getActivity(), "Please add a description.", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }

        return false;
    }


    private void setLocations(final String location) {

        loader.showLoader();

        Location loc = RetroClient.getClient().create(Location.class);

        String curr_city;
        if (isOnEditMode) {
            curr_city = editingSuggestionData.getCityId();
        } else {
            curr_city = session.getcurrentcity() + "";
        }

        Call<LocationsResponse> call = loc.getLocations(session.getToken(), location, curr_city);
        call.enqueue(new Callback<LocationsResponse>() {
            @Override
            public void onResponse(Call<LocationsResponse> call, Response<LocationsResponse> response) {
                loader.hideLoader();

                if (response.code() == 200) {

                    locationsdata = response.body().getMessage();
                    //locations = new String[locationsdata.size()];
                    locations.clear();
                    locationsadapter.clear();

                    int x = 0;

                    for (LocationsData l : locationsdata) {

                        if (l.getLocationName().trim().isEmpty()) {
                            locations.add(l.getSuburb());
                            locationsadapter.add(l.getSuburb());
                        } else {
                            locationsadapter.add(l.getLocationName().trim() + " - " + l.getSuburb());
                            locations.add(l.getLocationName().trim() + " - " + l.getSuburb());
                        }

                        x++;
                    }

                    //locationsadapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<LocationsResponse> call, Throwable t) {
                loader.hideLoader();
            }
        });
    }


    private void loadMicroCategories(String subcatid) {

        loader.showLoader();

        Categories c = RetroClient.getClient().create(Categories.class);
        Call<MicroCatResponse> call = c.getSubCategory(session.getToken(), subcatid);
        call.enqueue(new Callback<MicroCatResponse>() {
            @Override
            public void onResponse(Call<MicroCatResponse> call, Response<MicroCatResponse> response) {

                if (getActivity() == null) return;

                subcategoriesdata.clear();
                subcategoriesdata.addAll(response.body().getMessage());
                subcategories.clear();
                microcategoriesgroup.clear();
                groups_microcategories.clear();

                int x = 0;

                for (MicroCatData m : subcategoriesdata) {
                    subcategories.add(m.getName().trim());
                    String groupname = m.getName().split("©")[0].trim();
                    microcategoriesgroup.add(groupname);

                    x++;
                }

                subcategoriesadapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, subcategories);
                input_subcat.setAdapter(subcategoriesadapter);

                groups_microcategories.add("Choose category group");

                for (String s : microcategoriesgroup) {
                    groups_microcategories.add(s);
                }

                microcat_group_adapter.notifyDataSetChanged();

                if (isOnEditMode) {

                    if (input_subcat.getText().toString().split("©").length > 1)
                        input_subcat.setText(editingSuggestionData.getMicrocategory().split("©")[1].trim());

                    int pos = groups_microcategories.indexOf(editingSuggestionData.getMicrocategory().split("©")[0].trim());
                    sp_microcat_groups.setSelection(pos);


                } else {
                    //ll_sp_holder.setVisibility(View.GONE);
                }

                loader.hideLoader();

                //new ArrayList<String>(uniquegrps);
            }

            @Override
            public void onFailure(Call<MicroCatResponse> call, Throwable t) {
                loader.hideLoader();
            }
        });
    }

    private void setSelectedColor(int index) {

        ll_holder_hangout.setBackground(getResources().getDrawable(R.drawable.black_curve_bg));
        ll_holder_services.setBackground(getResources().getDrawable(R.drawable.black_curve_bg));
        ll_holder_shopping.setBackground(getResources().getDrawable(R.drawable.black_curve_bg));

        if (index == 1) {
            ll_holder_hangout.setBackground(getResources().getDrawable(R.drawable.blue_curve_bg));
            Glide.with(getActivity()).load(R.drawable.hangout_b).into(iv_cat_icon);

        } else if (index == 2) {
            ll_holder_services.setBackground(getResources().getDrawable(R.drawable.blue_curve_bg));
            Glide.with(getActivity()).load(R.drawable.service_b).into(iv_cat_icon);

        } else if (index == 3) {
            ll_holder_shopping.setBackground(getResources().getDrawable(R.drawable.blue_curve_bg));
            Glide.with(getActivity()).load(R.drawable.shopping_b).into(iv_cat_icon);

        }
    }

}
