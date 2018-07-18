package com.tag.tai.tag.Fragments.AddSubCategory;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.Fragments.AddLocation.LocationAdapter;
import com.tag.tai.tag.Fragments.AddLocation.LocationPojo;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Interfaces.Categories;
import com.tag.tai.tag.Services.Responses.MicroCat.MicroCatData;
import com.tag.tai.tag.Services.Responses.MicroCat.MicroCatResponse;
import com.tag.tai.tag.Services.RetroClient;

import java.util.ArrayList;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 20-03-2018.
 */

public class AddSubCatFragment extends Fragment implements SubCategorySelected{

    EditText et_category;
    TextView et_category_group;
    Button btn_addsubcat;

    String selected_subcat = "",selected_subcatgroup = "",selected_subcatid = "";

    SessionManager session;
    ArrayList<MicroCatData> allmicroCats, uniqueGroups;
    HashSet<String> uniquegrps;

    RecyclerView recyc_subcats;
    ArrayList<SubCatPojo> subcats;
    SubCatAdapter subCatAdapter;



    public AddSubCatFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragement_addsubcat,container,false);

        session = new SessionManager(getActivity());

        allmicroCats = new ArrayList<>();
        uniqueGroups = new ArrayList<>();
        uniquegrps = new HashSet<>();

        btn_addsubcat = v.findViewById(R.id.btn_addsubcat);
        btn_addsubcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pageValidation()){
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,
                            new Intent().putExtra("subcat",selected_subcat + " - " + selected_subcatgroup));

                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                }


            }
        });

        et_category = v.findViewById(R.id.et_category);
        et_category_group = v.findViewById(R.id.et_category_group);

        et_category.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                getSubCategories(et_category.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        et_category_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSubCategoryGroup("");
            }
        });

        loadSubCategories(getArguments().getString("subcatid"));

        recyc_subcats = v.findViewById(R.id.recyc_subcats);
        subcats = new ArrayList<>();
        subCatAdapter = new SubCatAdapter(subcats,this);

        recyc_subcats.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyc_subcats.setItemAnimator(new DefaultItemAnimator());
        recyc_subcats.setAdapter(subCatAdapter);

        return v;
    }

    private boolean pageValidation() {
        String val_cat = et_category.getText().toString();
        String val_cat_group = et_category_group.getText().toString();

        if(val_cat.isEmpty()){
            Toast.makeText(getActivity(), "Please fill in a category.", Toast.LENGTH_SHORT).show();
            getSubCategories("");
        }else if(val_cat_group.isEmpty()){
            Toast.makeText(getActivity(), "Please select a category group.", Toast.LENGTH_SHORT).show();
            getSubCategoryGroup("");
        }else if(selected_subcat.isEmpty()){
            selected_subcat = val_cat;
        }else if(selected_subcatgroup.isEmpty()){
            selected_subcatgroup = val_cat_group;
        }else{

            if(!selected_subcat.equals(val_cat)){
                selected_subcatid = "";
                selected_subcat = val_cat;
            }

            return true;
        }

        return false;
    }

    private void loadSubCategories(String subcatid) {

        Categories c = RetroClient.getClient().create(Categories.class);
        Call<MicroCatResponse> call = c.getSubCategory(session.getToken(),subcatid);
        call.enqueue(new Callback<MicroCatResponse>() {
            @Override
            public void onResponse(Call<MicroCatResponse> call, Response<MicroCatResponse> response) {

                allmicroCats = response.body().getMessage();
                Log.d("12345", "onResponse: microcats recd");

                for(MicroCatData m : allmicroCats){

                    String groupname = m.getName().split("-")[0].trim();
                    uniquegrps.add(groupname);

                }

                //new ArrayList<String>(uniquegrps);
            }

            @Override
            public void onFailure(Call<MicroCatResponse> call, Throwable t) {
                if(getActivity()==null)return;
                Toast.makeText(getActivity(), RetroClient.CONNETION_ERROR, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getSubCategoryGroup(String subcategorygroup) {
        subcats.clear();

        for(String s : uniquegrps){
            subcats.add(new SubCatPojo("","",s,"1"));
        }

        subCatAdapter.notifyDataSetChanged();
    }

    private void getSubCategories(String subcategory) {
        subcats.clear();

        for(MicroCatData m : allmicroCats) {

            if(m.getName().toLowerCase().contains(subcategory.toLowerCase())){
                subcats.add(new SubCatPojo(m.getMicroId(),m.getName(),m.getName(),"0"));
            }

        }

        subCatAdapter.notifyDataSetChanged();
    }


    @Override
    public void onSubCatSelected(String microid, String subcattext, String subcatgroup, String type) {

        if(type.equals("0")){

            selected_subcat = subcattext.split("-")[1].trim();
            selected_subcatgroup = subcattext.split("-")[0].trim();
            selected_subcatid = microid;

            et_category.setText(selected_subcat);
            et_category_group.setText(selected_subcatgroup);

            subcats.clear();
            subCatAdapter.notifyDataSetChanged();

        }else{

            selected_subcat = et_category.getText().toString();
            selected_subcatgroup = subcattext;
            selected_subcatid = microid;

            et_category.setText(selected_subcat);
            et_category_group.setText(selected_subcatgroup);

            subcats.clear();
            subCatAdapter.notifyDataSetChanged();

        }




    }
}
