package com.tag.tai.tag.Fragments.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.tag.tai.tag.Activities.MainActivity;
import com.tag.tai.tag.Common.GestureListener;
import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.Fragments.AddSuggestions.AddSuggestionFragment;
import com.tag.tai.tag.Fragments.Feedback.FeedbackFragment;
import com.tag.tai.tag.Fragments.FindSugesstions.FindSuggestionsFragment;
import com.tag.tai.tag.Fragments.MyDetails.MyDetailsFragment;
import com.tag.tai.tag.Fragments.Rankings.RankingFragment;
import com.tag.tai.tag.Fragments.RequestSuggestions.RequestSuggestionsFragment;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Interfaces.FilterData;
import com.tag.tai.tag.Services.Interfaces.Suggestions;
import com.tag.tai.tag.Services.Responses.CategoryCountResponse.CategoryCountResponse;
import com.tag.tai.tag.Services.Responses.CategoryCountResponse.CatergoryObject;
import com.tag.tai.tag.Services.Responses.GetAreas.AreaData;
import com.tag.tai.tag.Services.Responses.GetAreas.AreasResponse;
import com.tag.tai.tag.Services.RetroClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements SwipeCallback {

    private LinearLayout hangouts;
    private GestureDetector hangoutsdetector;

    private LinearLayout services;
    private GestureDetector servicesdetector;

    private LinearLayout shopping;
    private GestureDetector shoppingdetector;

    SessionManager session;
    TextView tv_servicescount, tv_hangoutscount, tv_shoppingcount;

    AreaListAdapter cityadapter, subAreasAdapter;
    LinearLayout ll_city_selector;
    LinearLayout ll_sub_area_selector;
    ListPopupWindow citypopup;
    ListPopupWindow subAreaPopup;

    ArrayList<AreaData> allareas;
    ArrayList<AreaData> cities = new ArrayList<>();
    ArrayList<AreaData> subAreas = new ArrayList<>();

    String selectedAreaCode, selectedCity;

    final static int DIALOG_FRAGMENT = 110;

    //popups
    ArrayList<CatergoryObject> hangoutsdata, servicesdata, shoppingdata;
    ArrayList<String> hangoutlist, serviceslist, shoppinglist;
    ListPopupWindow hangoutspopup, servicespopup, shopppingpopup;
    ArrayAdapter hangoutsadapter, servicesadapter, shoppingadapter;

    GestureListener.Direction redirectDirection = null;

    public HomeFragment() {
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).bottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ((MainActivity) getActivity()).bottomNavigationView.setVisibility(View.GONE);

        session = new SessionManager(getActivity());

        tv_servicescount = v.findViewById(R.id.tv_servicescount);
        tv_hangoutscount = v.findViewById(R.id.tv_hangoutscount);
        tv_shoppingcount = v.findViewById(R.id.tv_shoppingcount);

        hangoutsdetector = new GestureDetector(getActivity(), new GestureListener(GestureListener.ItemCode.HANGOUTS, this));
        hangouts = v.findViewById(R.id.hangouts);
        hangouts.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return hangoutsdetector.onTouchEvent(event);
            }
        });

        servicesdetector = new GestureDetector(getActivity(), new GestureListener(GestureListener.ItemCode.SERVICES, this));
        services = v.findViewById(R.id.services);
        services.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return servicesdetector.onTouchEvent(event);
            }
        });

        shoppingdetector = new GestureDetector(getActivity(), new GestureListener(GestureListener.ItemCode.SHOPPINNG, this));
        shopping = v.findViewById(R.id.shopping);
        shopping.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return shoppingdetector.onTouchEvent(event);
            }
        });


        v.findViewById(R.id.mydetails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyDetailsFragment()).commit();
            }
        });

        v.findViewById(R.id.feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new FeedbackFragment()).commit();
            }
        });

        v.findViewById(R.id.ranking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new RankingFragment()).commit();
            }
        });


        //dropdown lists---------------------------------------------------------------------

        hangoutsdata = new ArrayList<>();
        servicesdata = new ArrayList<>();
        shoppingdata = new ArrayList<>();
        hangoutlist = new ArrayList<>();
        serviceslist = new ArrayList<>();
        shoppinglist = new ArrayList<>();
        hangoutspopup = new ListPopupWindow(getActivity());
        servicespopup = new ListPopupWindow(getActivity());
        shopppingpopup = new ListPopupWindow(getActivity());
        hangoutsadapter = new ArrayAdapter(getActivity(), R.layout.popup_dropdown_item, new ArrayList());
        servicesadapter = new ArrayAdapter(getActivity(), R.layout.popup_dropdown_item, new ArrayList());
        shoppingadapter = new ArrayAdapter(getActivity(), R.layout.popup_dropdown_item, new ArrayList());

        hangoutspopup.setAdapter(hangoutsadapter);
        servicespopup.setAdapter(servicesadapter);
        shopppingpopup.setAdapter(shoppingadapter);

        hangoutspopup.setHeight(ListPopupWindow.WRAP_CONTENT);
        hangoutspopup.setWidth(400);
        hangoutspopup.setModal(true);
        hangoutspopup.setAnchorView(hangouts);
        hangoutspopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String categoryName = hangoutsdata.get(position).getCategoryName();
                //Toast.makeText(getActivity(), "" + categoryName, Toast.LENGTH_SHORT).show();
                hangoutspopup.dismiss();

                redirectToPageByDirection(hangoutsdata.get(position));

            }
        });

        servicespopup.setHeight(ListPopupWindow.WRAP_CONTENT);
        servicespopup.setWidth(600);
        servicespopup.setModal(true);
        servicespopup.setAnchorView(services);
        servicespopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String categoryName = servicesdata.get(position).getCategoryName();
                //Toast.makeText(getActivity(), "" + categoryName, Toast.LENGTH_SHORT).show();
                servicespopup.dismiss();

                redirectToPageByDirection(servicesdata.get(position));
            }
        });

        shopppingpopup.setHeight(ListPopupWindow.WRAP_CONTENT);
        shopppingpopup.setWidth(700);
        shopppingpopup.setModal(true);
        shopppingpopup.setAnchorView(shopping);
        shopppingpopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String categoryName = shoppingdata.get(position).getCategoryName();
                //Toast.makeText(getActivity(), "" + categoryName, Toast.LENGTH_SHORT).show();

                shopppingpopup.dismiss();

                redirectToPageByDirection(shoppingdata.get(position));
            }
        });

        //end dropdown lists---------------------------------------------------------------------


        //--------------------cities
        //city selector
        ll_city_selector = v.findViewById(R.id.ll_city_selector);
        cities = new ArrayList<>();
        citypopup = new ListPopupWindow(getActivity());
        cityadapter = new AreaListAdapter(getActivity(), cities);
        citypopup.setAdapter(cityadapter);
        citypopup.setHeight(ListPopupWindow.WRAP_CONTENT);
        citypopup.setWidth(ListPopupWindow.WRAP_CONTENT);
        citypopup.setModal(true);
        citypopup.setAnchorView(ll_city_selector);
        citypopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                citypopup.dismiss();
                ((TextView) ll_city_selector.findViewById(R.id.tv_cityname)).setText(cities.get(position).getDdValue());
                getCityByPosition(position);
            }
        });

        ll_city_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citypopup.show();
            }
        });


        //--------------------cities

        //--------------------sub-areas
        //sub-areas selector
        ll_sub_area_selector = v.findViewById(R.id.ll_sub_area_selector);
        subAreaPopup = new ListPopupWindow(getActivity());
        subAreasAdapter = new AreaListAdapter(getActivity(), subAreas);
        subAreaPopup.setAdapter(subAreasAdapter);
        subAreaPopup.setHeight(ListPopupWindow.WRAP_CONTENT);
        subAreaPopup.setWidth(ListPopupWindow.WRAP_CONTENT);
        subAreaPopup.setModal(true);
        subAreaPopup.setAnchorView(ll_sub_area_selector);
        subAreaPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subAreaPopup.dismiss();
                ((TextView) ll_sub_area_selector.findViewById(R.id.tv_sub_area_name)).setText(subAreas.get(position).getDdValue());
                getSubAreaByPosition(position);
            }
        });

        ll_sub_area_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subAreaPopup.show();
            }
        });


        //--------------------sub-areas

        ((MainActivity) getActivity()).checkPermissionForLocation(MainActivity.LOAD_HOME_AREAS);
        getCategoryCounts();


        return v;
    }


    public void setTest(String a, String b, String c) {
    }

    //cities--------------------------------------------


    private void loadServingAreas(String suburb, String lat_lon, String address, String cityid) {

        FilterData filter = RetroClient.getClient().create(FilterData.class);
        Call<AreasResponse> call = filter.getAreas(session.getToken(), session.getUserID(), suburb, lat_lon, address, cityid);
        call.enqueue(new Callback<AreasResponse>() {
            @Override
            public void onResponse(Call<AreasResponse> call, Response<AreasResponse> response) {

                if (response.code() == 200) {

                    allareas = new ArrayList<>();
                    cities.clear();
                    subAreas.clear();

                    AreaData nearByArea = new AreaData(0, "near", "Near me", "default", false);

                    allareas.add(nearByArea);
                    allareas.addAll(response.body().getData());

                    AreaData selectedAreaData = null;

                    for (AreaData areaData : allareas) {
                        if (areaData.getFilterType().equalsIgnoreCase("C"))
                            cities.add(areaData);
                        else
                            subAreas.add(areaData);

                        if (areaData.isSelected()) {
                            selectedAreaData = areaData;
                        }
                    }

                    if (selectedAreaData != null) {

                        if (selectedAreaData.getDdValue().equals("near")) {
                            getNearMeData();
                        } else if (selectedAreaData.getFilterType().equals("A")) {
                            session.setcurrentcity(1);
                            selectedAreaCode = selectedAreaData.getDdValue();
                            ((TextView) ll_sub_area_selector.findViewById(R.id.tv_sub_area_name)).setText(selectedAreaData.getDdText());
                        } else if (selectedAreaData.getFilterType().equals("C")) {
                            session.setcurrentcity(Integer.parseInt(selectedAreaData.getDdValue()));
                            ((TextView) ll_city_selector.findViewById(R.id.tv_cityname)).setText(selectedAreaData.getDdText());
                        }
                    }

                    cityadapter.notifyDataSetChanged();
                    subAreasAdapter.notifyDataSetChanged();
                    citypopup.setWidth(480);
                    subAreaPopup.setWidth(480);
                } else {

                }
            }

            @Override
            public void onFailure(Call<AreasResponse> call, Throwable t) {
                Log.d(RetroClient.TAG, "onFailure: test");
            }
        });

    }


    //if cities
    private void getCityByPosition(int position) {
        AreaData area = cities.get(position);
        cityadapter.setSelectedArea(position);
        subAreasAdapter.getFilter().filter(area.getCityId());
        if (area.getDdValue().equals("near")) {
            getNearMeData();
        } else {
            getDataWithCity(position, area);
        }

    }

    //get are with area code
    private void getSubAreaByPosition(int position) {
        AreaData area = subAreas.get(position);
        subAreasAdapter.setSelectedArea(position);
        if (area.getDdValue().equals("near")) {
            getNearMeData();
        } else {
            getDataWithAreaCode(position, area);
        }

    }

    private void getDataWithCity(int position, AreaData area) {
        selectedAreaCode = "";
        session.setcurrentcity(Integer.parseInt(area.getDdValue()));
        ((TextView) ll_city_selector.findViewById(R.id.tv_cityname)).setText(area.getDdText());
        Toast.makeText(getActivity(), "" + area.getDdText(), Toast.LENGTH_SHORT).show();
    }

    //with area code
    private void getDataWithAreaCode(int position, AreaData area) {
        session.setcurrentcity(1);
        selectedAreaCode = area.getDdValue();
        ((TextView) ll_sub_area_selector.findViewById(R.id.tv_sub_area_name)).setText(area.getDdText());
        Toast.makeText(getActivity(), "" + area.getDdText(), Toast.LENGTH_SHORT).show();
    }

    private void getNearMeData() {
        ((MainActivity) getActivity()).checkPermissionForLocation(MainActivity.LOAD_HOME_SUGGESTIONS);
    }

    public void setAreasByCurrentLocation(String address, String location_split, String lat, String lon) {
        //Toast.makeText(getActivity(), "" + location_split, Toast.LENGTH_SHORT).show();

        loadServingAreas(location_split, lat + "," + lon, address, "" + session.getcurrentcity());

    }

    //cities--------------------------------------------


    private void getCategoryCounts() {

        Suggestions s = RetroClient.getClient().create(Suggestions.class);
        Call<CategoryCountResponse> call = s.getCategoryCount(session.getToken(), session.getUserMobile(), "", "", "" + session.getcurrentcity());
        call.enqueue(new Callback<CategoryCountResponse>() {
            @Override
            public void onResponse(Call<CategoryCountResponse> call, Response<CategoryCountResponse> response) {
                //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                //Log.d(RetroClient.TAG, "onResponse: what" + response.body().getData().getCategoryCountData().get(0).getCategoryName());

                if (response.code() == 200) {

                    //------------------------------------------------------

                    tv_hangoutscount.setText("" + response.body().getData().getSectionCountData().get(0).getSuggCount());
                    tv_servicescount.setText("" + response.body().getData().getSectionCountData().get(1).getSuggCount());
                    tv_shoppingcount.setText("" + response.body().getData().getSectionCountData().get(2).getSuggCount());


                    //------------------------------------------------------


                    for (CatergoryObject c : response.body().getData().getCategoryCountData()) {
                        if (c.getCatId() == 1) {
                            hangoutlist.add(c.getCategoryName());
                            hangoutsdata.add(c);
                            hangoutsadapter.add(c.getCategoryName());
                        } else if (c.getCatId() == 2) {
                            serviceslist.add(c.getCategoryName());
                            servicesdata.add(c);
                            servicesadapter.add(c.getCategoryName());
                        } else if (c.getCatId() == 3) {
                            shoppinglist.add(c.getCategoryName());
                            shoppingdata.add(c);
                            shoppingadapter.add(c.getCategoryName());
                        }
                    }

                    hangoutsadapter.notifyDataSetChanged();
                    servicesadapter.notifyDataSetChanged();
                    shoppingadapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<CategoryCountResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Fail " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirectToPageByDirection(CatergoryObject catergoryObject) {

        FindSuggestionsFragment findSuggestionsFragment = new FindSuggestionsFragment();
        AddSuggestionFragment addSuggestionFragment = new AddSuggestionFragment();
        Bundle b = new Bundle();

        switch (redirectDirection) {
            case BOTTOM: {
                b.putBoolean("fromHome", true);
                b.putInt("homeCategory", catergoryObject.getCatId());
                b.putInt("homeSubCategory", catergoryObject.getSubCatId());
                findSuggestionsFragment.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, findSuggestionsFragment, findSuggestionsFragment.getClass().getName()).commit();
                break;

            }
            case TOP: {

                b.putBoolean("fromHome", true);
                b.putBoolean("addSuggestion", true);
                b.putInt("homeCategory", catergoryObject.getCatId());
                b.putInt("homeSubCategory", catergoryObject.getSubCatId());
                b.putString("homeSubCategoryText", catergoryObject.getCategoryName());

                addSuggestionFragment.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, addSuggestionFragment, addSuggestionFragment.getClass().getName()).commit();
                break;

            }
        }

    }


    @Override
    public void swipedItem(GestureListener.Direction direction, GestureListener.ItemCode itemcode) {
        //Toast.makeText(getActivity(), "" + itemcode.name() + " "  + direction.name(), Toast.LENGTH_SHORT).show();

        FindSuggestionsFragment findSuggestionsFragment = new FindSuggestionsFragment();
        AddSuggestionFragment addSuggestionFragment = new AddSuggestionFragment();
        RequestSuggestionsFragment requestSuggestionsFragment = new RequestSuggestionsFragment();
        Bundle b = new Bundle();

        redirectDirection = direction;


        switch (direction) {
            case BOTTOM: {

//                if(itemcode.ordinal() == 0){
//                    hangoutspopup.show();
//                }else if(itemcode.ordinal() == 1){
//                    servicespopup.show();
//                }else if(itemcode.ordinal() == 2){
//                    shopppingpopup.show();
//                }


                showDialogWithItems(itemcode.ordinal() + 1, 3);

                break;

//                b.putBoolean("fromHome",true);
//                b.putInt("homeCategory",itemcode.ordinal() + 1);
//                findSuggestionsFragment.setArguments(b);
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,findSuggestionsFragment,findSuggestionsFragment.getClass().getName()).commit();
//                break;
            }
            case TOP: {

                if (itemcode.ordinal() == 0) {
                    hangoutspopup.show();
                } else if (itemcode.ordinal() == 1) {
                    servicespopup.show();
                } else if (itemcode.ordinal() == 2) {
                    shopppingpopup.show();
                }

                break;
//                addSuggestionFragment.setArguments(b);
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,addSuggestionFragment,addSuggestionFragment.getClass().getName()).commit();
//                break;
            }
            case RIGHT: {

//                if(itemcode.ordinal() == 0){
//                    hangoutspopup.show();
//                }else if(itemcode.ordinal() == 1){
//                    servicespopup.show();
//                }else if(itemcode.ordinal() == 2){
//                    shopppingpopup.show();
//                }
//              break;

                b.putBoolean("showRequestedSuggestions", true);
                addSuggestionFragment.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, addSuggestionFragment, addSuggestionFragment.getClass().getName()).commit();
                break;
            }
            case LEFT: {

                b.putString("selected_category", itemcode.ordinal() + 1 + "");
                b.putString("selected_subcategory", "");
                requestSuggestionsFragment.setArguments(b);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, requestSuggestionsFragment, requestSuggestionsFragment.getClass().getName()).commit();
                break;
            }
            case TAPPED: {

                b.putBoolean("fromHome", true);
                b.putInt("homeCategory", itemcode.ordinal() + 1);
                findSuggestionsFragment.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, findSuggestionsFragment, findSuggestionsFragment.getClass().getName()).commit();
                break;
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case DIALOG_FRAGMENT: {

                if (resultCode == Activity.RESULT_OK) {

                    Log.d(RetroClient.TAG, "onActivityResult: " + data.getStringExtra("itemname"));


                } else if (resultCode == Activity.RESULT_CANCELED) {

                }

                break;
            }
        }

    }

    private void showDialogWithItems(int categoryId, int intent) {

        String[] items;

        if (categoryId == 1) {

            items = new String[hangoutlist.size()];
            int index = 0;
            for (String s : hangoutlist) {
                items[index] = s;
                Log.d(RetroClient.TAG, "showDialogWithItems: " + s + " " + index++);

            }

        } else if (categoryId == 2) {

            items = new String[serviceslist.size()];
            int index = 0;
            for (String s : serviceslist) {
                items[index] = s;
                Log.d(RetroClient.TAG, "showDialogWithItems: " + s + " " + index++);
            }

        } else {

            items = new String[shoppinglist.size()];
            int index = 0;
            for (String s : shoppinglist) {
                items[index] = s;
                Log.d(RetroClient.TAG, "showDialogWithItems: " + s + " " + index++);
            }
        }

        DialogList d = DialogList.newInstance(items, categoryId, intent);
        d.setTargetFragment(HomeFragment.this, DIALOG_FRAGMENT);
        d.show(getActivity().getSupportFragmentManager(), "itemsdialog");


    }


}
