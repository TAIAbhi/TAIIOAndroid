package com.tag.tai.tag.Fragments.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tag.tai.tag.Activities.LoginActivity;
import com.tag.tai.tag.Activities.MainActivity;
import com.tag.tai.tag.Common.Loader;
import com.tag.tai.tag.Common.ProcessError;
import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.Fragments.Introduction.IntroductionFragment;
import com.tag.tai.tag.Fragments.TestFragment.TestFrag;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Interfaces.Login;
import com.tag.tai.tag.Services.Requests.Login.LoginRequest;
import com.tag.tai.tag.Services.Responses.Login.LoginData;
import com.tag.tai.tag.Services.Responses.Login.LoginResponse;
import com.tag.tai.tag.Services.RetroClient;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jam on 15-03-2018.
 */

public class LoginFragment extends Fragment{

    ImageView iv_logo;
    EditText et_username,et_password;
    Button btn_login;

    SessionManager session;

    Loader loader;

    TextView tv_eula;
    int login_error_count = 0;

    public LoginFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login,container,false);

        session = new SessionManager(getActivity());

        loader = new Loader(getActivity(),(LoginActivity)getActivity());

        tv_eula = v.findViewById(R.id.tv_eula);
        String udata="User License Agrement.";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        tv_eula.setText(content);


        tv_eula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://tagaboutit.com/Login/EULA"));
                startActivity(browserIntent);
            }
        });

        iv_logo = v.findViewById(R.id.iv_logo);
        et_username = v.findViewById(R.id.et_username);
        et_password = v.findViewById(R.id.et_password);
        btn_login = v.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateCreds())
                    checklogin(et_username.getText().toString(),et_password.getText().toString());
            }
        });

        et_username.setText("9867587106");
        et_password.setText("Abhirox");

//        et_username.setText("8080580607");
//        et_password.setText("admintai1");

//        et_password.setText(session.getlastlogincreds());
//        et_password.setText("");

        Glide.with(getActivity()).load(R.drawable.splash).into(iv_logo);

        return v;
    }


    private boolean validateCreds() {

        String username = et_username.getText().toString();
        String pass = et_password.getText().toString();

        if(username.isEmpty()){
            Toast.makeText(getActivity(), "Please fill in the username", Toast.LENGTH_SHORT).show();
        }else if(pass.isEmpty()){
            Toast.makeText(getActivity(), "Please fill in the password", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }

        return false;
    }


    private void checklogin(final String userid, String password) {
        loader.showLoader("Verifying credentials");

        LoginRequest r = new LoginRequest(userid,password);

        Login log = RetroClient.getClient().create(Login.class);
        Call<LoginResponse> logincall = log.checkLogin(r);
        logincall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                loader.hideLoader();

                if(response.code() == 200){

                    session.setlastlogincreds(userid);

                    LoginData d = response.body().getLoginDetails();

                    session.setToken(response.body().getAuthToken());
                    session.setUserLoggedIn(d.getContactName(),d.getContactId(),
                            userid,d.getRole(),d.getSourceImage(),d.getSourceTypeText(),d.getSourceName());
//
//                    Intent i = new Intent(getActivity(), MainActivity.class);
//                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(i);

                    if(d.getSkipVideo().equals("true")){

                        Intent i = new Intent(getActivity(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);

                    }else{

                        Bundle b = new  Bundle();
                        b.putInt("activity_reference",0);
                        IntroductionFragment i = new IntroductionFragment();
                        i.setArguments(b);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,i).commit();
                    }

                }else{

                    checkLoginError();

                        try {

                        ProcessError.processError(getActivity(),response.errorBody().string());

//                        Map<String,String> m = new Gson().fromJson(response.errorBody().string(),Map.class);
//                        Toast.makeText(getActivity(), "" + m.get("message"), Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loader.hideLoader();

                checkLoginError();

                if(getActivity()==null)return;
                Toast.makeText(getActivity(), "" + RetroClient.CONNETION_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkLoginError() {

        if(login_error_count > 1){
            new AlertDialog.Builder(getActivity()).setMessage("If you are having trouble logging in, please email info.tagaboutit@gmail.com or contact the source who shared the invite to join our community.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }

        login_error_count++;
    }
}
