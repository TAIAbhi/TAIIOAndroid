package com.tag.tai.tag.Fragments.HelpFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.Toast;

import com.tag.tai.tag.Activities.MainActivity;
import com.tag.tai.tag.Common.Loader;
import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.Fragments.AddSuggestions.AddSuggestionFragment;
import com.tag.tai.tag.Fragments.Feedback.FeedbackFragment;
import com.tag.tai.tag.Fragments.FindSugesstions.FindSuggestionsFragment;
import com.tag.tai.tag.Fragments.MyDetails.MyDetailsFragment;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Interfaces.Help;
import com.tag.tai.tag.Services.Responses.HelpDetails.HelpResponse;
import com.tag.tai.tag.Services.RetroClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 09-04-2018.
 */

public class HelpFragment extends Fragment {

    Loader loader;
    SessionManager session;

    ViewPager vp_pager;
    FragmentStatePagerAdapter pagerAdapter;

    HelpResponse helpResponse;
    ArrayList<String> helpData;
    int selectedHelpOption;

    Button btn_continue;

    public HelpFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help,container,false);

        loader = new Loader(getActivity(),(MainActivity)getActivity());
        session = new SessionManager(getActivity());

        selectedHelpOption = getArguments().getInt("option");

        vp_pager = v.findViewById(R.id.vp_pager);//new ViewPager(getActivity());
        helpData = new ArrayList<>();
        pagerAdapter = new HelpFragment.HelpAdapter(getChildFragmentManager());
        vp_pager.setAdapter(pagerAdapter);
        vp_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(vp_pager.getCurrentItem() == helpData.size() - 1){

                    if(selectedHelpOption == 1){
                        btn_continue.setText("View Suggestions");
                    }else if(selectedHelpOption == 2){
                        btn_continue.setText("Add Suggestions");
                    }else if(selectedHelpOption == 3){
                        btn_continue.setText("My Details");
                    }else if(selectedHelpOption == 4){
                        btn_continue.setText("Give Feedback");
                    }

                }else{
                    btn_continue.setText("CONTINUE");
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        btn_continue = v.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment redirectFragment = new FindSuggestionsFragment();

                if(vp_pager.getCurrentItem() + 1 < helpData.size()){

                    vp_pager.setCurrentItem(vp_pager.getCurrentItem() + 1);

                }else if(vp_pager.getCurrentItem() + 1 == helpData.size()){

                    if(selectedHelpOption == 1){

                        redirectFragment = new FindSuggestionsFragment();

                    }else if(selectedHelpOption == 2){

                        redirectFragment = new AddSuggestionFragment();

                    }else if(selectedHelpOption == 3){


                        redirectFragment = new MyDetailsFragment();

                    }else if(selectedHelpOption == 4){

                        redirectFragment = new FeedbackFragment();
                    }

                    getActivity().getSupportFragmentManager().popBackStack();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,redirectFragment).commit();

                }
            }
        });

        setUpHelpDetails();

        return v;
    }

    private void setUpHelpDetails() {
        loader.showLoader("Fetching help details.");

        final Help help = RetroClient.getClient().create(Help.class);
        Call<HelpResponse> call = help.getHelpDetails(session.getToken());
        call.enqueue(new Callback<HelpResponse>() {
            @Override
            public void onResponse(Call<HelpResponse> call, Response<HelpResponse> response) {
                loader.hideLoader();

                helpResponse = response.body();

                setUpCurrentHelpData();

            }

            @Override
            public void onFailure(Call<HelpResponse> call, Throwable t) {
                loader.hideLoader();
                if(getActivity()==null)return;
                Toast.makeText(getActivity(),RetroClient.CONNETION_ERROR, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setUpCurrentHelpData() {

        if(selectedHelpOption == 1){


            for(com.tag.tai.tag.Services.Responses.HelpDetails.HelpData h : helpResponse.getData()){
                if(h.getModuleName().equals("FSW")){
                    helpData.addAll(h.getuRLorText());
                }
            }

            for(com.tag.tai.tag.Services.Responses.HelpDetails.HelpData h : helpResponse.getData()){
                if(h.getModuleName().equals("FSH")){
                    helpData.addAll(h.getuRLorText());
                }
            }


        }else if(selectedHelpOption == 2){

            for(com.tag.tai.tag.Services.Responses.HelpDetails.HelpData h : helpResponse.getData()){
                if(h.getModuleName().equals("ASW")){
                    helpData.addAll(h.getuRLorText());
                }
            }

            for(com.tag.tai.tag.Services.Responses.HelpDetails.HelpData h : helpResponse.getData()){
                if(h.getModuleName().equals("ASH")){
                    helpData.addAll(h.getuRLorText());
                }
            }

        }else if(selectedHelpOption == 3){

            for(com.tag.tai.tag.Services.Responses.HelpDetails.HelpData h : helpResponse.getData()){
                if(h.getModuleName().equals("MDW")){
                    helpData.addAll(h.getuRLorText());
                }
            }

            for(com.tag.tai.tag.Services.Responses.HelpDetails.HelpData h : helpResponse.getData()){
                if(h.getModuleName().equals("MDH")){
                    helpData.addAll(h.getuRLorText());
                }
            }

        }else if(selectedHelpOption == 4){

            for(com.tag.tai.tag.Services.Responses.HelpDetails.HelpData h : helpResponse.getData()){
                if(h.getModuleName().equals("FBW")){
                    helpData.addAll(h.getuRLorText());
                }
            }

            for(com.tag.tai.tag.Services.Responses.HelpDetails.HelpData h : helpResponse.getData()){
                if(h.getModuleName().equals("FBH")){
                    helpData.addAll(h.getuRLorText());
                }
            }
        }

        pagerAdapter.notifyDataSetChanged();
    }

    private class HelpAdapter extends FragmentStatePagerAdapter{


        public HelpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment helpfrag = new HelpData();

            Bundle b = new Bundle();
            String s = helpData.get(position);

            if(s.contains("www.youtube.com")){
                b.putString("video",s);
                b.putBoolean("isVideo",true);
            }else{
                b.putString("text",s);
                b.putBoolean("isVideo",false);
            }

            helpfrag.setArguments(b);

            return helpfrag;
        }

        @Override
        public int getCount() {
            return helpData.size();
        }
    }
}
