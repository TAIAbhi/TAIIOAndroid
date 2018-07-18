package com.tag.tai.tag.Fragments.Mysuggestions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Interfaces.Categories;
import com.tag.tai.tag.Services.Interfaces.Suggestions;
import com.tag.tai.tag.Services.Responses.GetSuggestions.SuggestionData;
import com.tag.tai.tag.Services.Responses.GetSuggestions.SuggestionsResponse;
import com.tag.tai.tag.Services.Responses.SubCategories.SubCatData;
import com.tag.tai.tag.Services.Responses.SubCategories.SubCatResponse;
import com.tag.tai.tag.Services.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 18-03-2018.
 */

public class MySuggestionsFragment extends Fragment implements MySuggestionsClick{


    Spinner sp_hangouts,sp_shopping,sp_services;
    TextView tv_category;

    boolean firstLoad;

    RecyclerView recyc_mysuggestions;
    ArrayList<Eachsuggestion> allsuggestions;
    MySuggestionsAdapter suggestionsAdapter;

    RecyclerView recyc_allmysuggestions;
    ArrayList<EachSuggestionCategory> allcategories;
    CategoryAdapter categoryAdapter;

    LinearLayout ll_sel_filter,ll_filter_block;

    ArrayAdapter<String> dataAdapter,dataAdapterServices,shopAdapter;
    SessionManager session;

    public MySuggestionsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mysuggestions,container,false);


        tv_category = v.findViewById(R.id.tv_category);

        sp_hangouts = v.findViewById(R.id.sp_hangouts);
        sp_shopping = v.findViewById(R.id.sp_shopping);
        sp_services = v.findViewById(R.id.sp_services);

        session = new SessionManager(getActivity());

        ll_sel_filter = v.findViewById(R.id.ll_sel_filter);
        ll_filter_block = v.findViewById(R.id.ll_filter_block);

        ll_sel_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll_filter_block.getVisibility() == View.VISIBLE){
                    ll_filter_block.setVisibility(View.GONE);
                }else{
                    ll_filter_block.setVisibility(View.VISIBLE);
                }
            }
        });

        firstLoad = true;

        recyc_mysuggestions = v.findViewById(R.id.recyc_mysuggestions);
        allsuggestions = new ArrayList<>();
        suggestionsAdapter = new MySuggestionsAdapter(allsuggestions);

        recyc_mysuggestions.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyc_mysuggestions.setItemAnimator(new DefaultItemAnimator());
        recyc_mysuggestions.setAdapter(suggestionsAdapter);


        recyc_allmysuggestions = v.findViewById(R.id.recyc_allmysuggestions);
        allcategories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(allcategories,this,getActivity());

        recyc_allmysuggestions.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyc_allmysuggestions.setItemAnimator(new DefaultItemAnimator());
        recyc_allmysuggestions.setAdapter(categoryAdapter);



//        setRecos();

        //setSuggestionsCategories();
        setSuggestionsByCategories();

        setCategories();

        return v;
    }

    private void setSuggestionsByCategories() {

        Suggestions s = RetroClient.getClient().create(Suggestions.class);
        Call<SuggestionsResponse> call = s.getusersuggestions(session.getToken(),session.getUserID());
        call.enqueue(new Callback<SuggestionsResponse>() {
            @Override
            public void onResponse(Call<SuggestionsResponse> call, Response<SuggestionsResponse> response) {

                if (response.code() == 200){
                    setSuggestions(response.body());
                }

            }

            @Override
            public void onFailure(Call<SuggestionsResponse> call, Throwable t) {
                if(getActivity()==null)return;
                Toast.makeText(getActivity(), "" + RetroClient.CONNETION_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setSuggestions(SuggestionsResponse response) {

        ArrayList<String> availableCategories = new ArrayList<>();
        //unique categories - currently empty

        for(SuggestionData d : response.getData()){ //looping all data

            if(d.getMicrocategoryId() == null)d.setMicrocategoryId("");
            String catid =  d.getCategoryId() + " - " + d.getSubCategoryId() + " - " + d.getMicrocategoryId();
            //category id combo -forming unique id

            if(availableCategories.indexOf(catid) < 0){
                availableCategories.add(catid);
                //adding uniqe ids in array

                ArrayList<Eachsuggestion> allSuggestions_ = new ArrayList<>();

                for(SuggestionData sd : response.getData()){
                    //temp hack
                    if(sd.getMicrocategoryId() == null)sd.setMicrocategoryId("");
                    //looping all data - checking if data is from same category and microcat
                    if(sd.getCategoryId().equals(d.getCategoryId()) && sd.getSubCategoryId().equals(d.getSubCategoryId()) && sd.getMicrocategoryId().equals(d.getMicrocategoryId())){

                        allSuggestions_.add(new Eachsuggestion(
                                sd.getSuggestionId(),
                                sd.getBusinessName(),
                                sd.getLocation(),
                                sd.getContactNumber(),
                                sd.getComments(),
                                sd.getSourceName()));
                    }
                }

                allcategories.add(new EachSuggestionCategory(d.getCategoryId(),d.getSubCategoryId(),d.getCategory(),d.getSubCategory(),allSuggestions_));
            }
        }

        categoryAdapter.notifyDataSetChanged();
    }

    private void setSuggestionsCategories() {

        Eachsuggestion e = new Eachsuggestion("0","Zaffran","Byculla (E)","998700000","Excellent Biryani", "Jamian");
        allsuggestions.add(e);
        e = new Eachsuggestion("0","Hopipolla","Lower Parel","998700000","Excellent Beer", "Darren");
        allsuggestions.add(e);
        e = new Eachsuggestion("0","La Folie Lab","Lower Parel","998700000","Excellent Chocolate", "Alia");
        allsuggestions.add(e);
        e = new Eachsuggestion("0","MainLand China","Saki Naka","998700000","Excellent Noodles", "Alia");
        allsuggestions.add(e);

        EachSuggestionCategory cat = new EachSuggestionCategory("0","0","Hangouts","Restaurants",allsuggestions);
        allcategories.add(cat);

        cat = new EachSuggestionCategory("0","0","Services","Automobiles",allsuggestions);
        allcategories.add(cat);

        categoryAdapter.notifyDataSetChanged();
    }

    private void setRecos() {

        Eachsuggestion e = new Eachsuggestion("0","Zaffran","Byculla (E)","998700000","Excellent Biryani", "Jamian");
        allsuggestions.add(e);
        e = new Eachsuggestion("0","Hopipolla","Lower Parel","998700000","Excellent Beer", "Darren");
        allsuggestions.add(e);
        e = new Eachsuggestion("0","La Folie Lab","Lower Parel","998700000","Excellent Chocolate", "Alia");
        allsuggestions.add(e);
        e = new Eachsuggestion("0","MainLand China","Saki Naka","998700000","Excellent Noodles", "Alia");
        allsuggestions.add(e);

        suggestionsAdapter.notifyDataSetChanged();
    }

    private void setCategories() {

        final List<String> hangouts = new ArrayList<>();
        hangouts.add("Hangout");
        sp_hangouts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0) {
                    tv_category.setText("HANGOUTS - " + hangouts.get(position));
                }else {
                    tv_category.setText("HANGOUTS");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dataAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,hangouts);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //dataAdapter.setDropDownViewResource(R.layout.spinner_text);

        sp_hangouts.setAdapter(dataAdapter);
        setDataForCategories("1",hangouts,dataAdapter);

//        -------------------------


        final List<String> services = new ArrayList<>();
        services.add("Services");

        sp_services.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0){
                    tv_category.setText("SERVICES - " + services.get(position));
                }else {
                    tv_category.setText("SERVICES");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dataAdapterServices = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,services);
        dataAdapterServices.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_services.setAdapter(dataAdapterServices);
        setDataForCategories("2",services,dataAdapterServices);


//        ----------------------

        final List<String> shops = new ArrayList<>();
        shops.add("Shopping");

        sp_shopping.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position != 0){
                    tv_category.setText("SHOPPING - " + shops.get(position));
                }else{
                    tv_category.setText("SHOPPING");
                }

                if(firstLoad){
                    tv_category.setText("ADD SUGGESTIONS");
                    firstLoad = false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        shopAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_text,shops);
        shopAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_shopping.setAdapter(shopAdapter);
        setDataForCategories("3",shops,shopAdapter);
    }
    private void setDataForCategories(String categoryId, final List<String> array, final ArrayAdapter<String> adapter){

        Categories c = RetroClient.getClient().create(Categories.class);
        Call<SubCatResponse> call = c.getCategory(session.getToken(),categoryId);
        call.enqueue(new Callback<SubCatResponse>() {
            @Override
            public void onResponse(Call<SubCatResponse> call, Response<SubCatResponse> response) {

                for (SubCatData subcat : response.body().getMessage()){
                    array.add(subcat.getName());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SubCatResponse> call, Throwable t) {
                if(getActivity()==null)return;
                Toast.makeText(getActivity(), "" + RetroClient.CONNETION_ERROR, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onEditClicked(int position,String suggestionId) {
        Toast.makeText(getActivity(), suggestionId, Toast.LENGTH_SHORT).show();
    }
}
