package com.tag.tai.tag.Activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.tag.tai.tag.Common.BottomNavigationViewHelper;
import com.tag.tai.tag.Common.LoaderControl;
import com.tag.tai.tag.Common.SessionManager;
import com.tag.tai.tag.Common.Utils;
import com.tag.tai.tag.FireBase.Message;
import com.tag.tai.tag.Fragments.AddSuggestions.AddSuggestionFragment;
import com.tag.tai.tag.Fragments.Feedback.FeedbackFragment;
import com.tag.tai.tag.Fragments.FindSugesstions.FindSuggestionsFragment;
import com.tag.tai.tag.Fragments.HelpFragment.HelpFragment;
import com.tag.tai.tag.Fragments.Home.HomeFragment;
import com.tag.tai.tag.Fragments.MyDetails.MyDetailsFragment;
import com.tag.tai.tag.Fragments.Notification.NotificationFragment;
import com.tag.tai.tag.Fragments.Rankings.RankingFragment;
import com.tag.tai.tag.R;
import com.tag.tai.tag.Services.Interfaces.Notifications;
import com.tag.tai.tag.Services.Requests.Notification.SaveRegistrationId;
import com.tag.tai.tag.Services.Responses.GetNotificationResponse.NotificationResponse;
import com.tag.tai.tag.Services.Responses.saveRegId.saveRegId;
import com.tag.tai.tag.Services.RetroClient;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LoaderControl {

    public BottomNavigationView bottomNavigationView;
    boolean isBackPressedOnce = false;
    SessionManager session;

    ImageView tb_help, tb_ranking;
    PopupMenu help_pop;

    RelativeLayout tb_notification, rl_badge_holder;
    TextView tv_badge_count;
    public ImageView homeButtonLogo;

    LinearLayout ll_loader;

    FusedLocationProviderClient fusedLocationProviderClient;
    Location lastLocation;
    public String lastLocationText = "Mumbai";
    FragmentManager fragmentManager = getSupportFragmentManager();

    BroadcastReceiver notificationUpdateReciever;

    public static final int LOAD_AREAS = 110;
    public static final int LOAD_SUGGESTIONS = 111;
    public static final int LOAD_HOME_SUGGESTIONS = 112;
    public static final int LOAD_HOME_AREAS = 113;
    boolean isFetched = false;


    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        session = new SessionManager(this);
        session.setcurrentcity(1);

        ll_loader = findViewById(R.id.ll_loader);
        Glide.with(this).load(R.drawable.loader).into((ImageView) findViewById(R.id.iv_loadericon));

        homeButtonLogo = findViewById(R.id.iv_header_logo);

        homeButtonLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setVisibility(View.GONE);
                String tag = HomeFragment.class.getName();
                Fragment h = fragmentManager.findFragmentByTag(tag);
                FragmentTransaction transaction = fragmentManager
                        .beginTransaction();
                /*if (h != null) {
                    transaction.replace(R.id.container, h, tag)
                            .commit();
                } else {
                }*/
                transaction
                        .add(R.id.container, new HomeFragment(), tag)
                        .commit();

            }
        });

        tb_help = findViewById(R.id.tb_help);
        tb_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help_pop.show();
            }
        });

        help_pop = new PopupMenu(this, tb_help);

        help_pop.getMenu().add(0, 1, 1, "View Suggestions");
        help_pop.getMenu().add(0, 2, 2, "Add Suggestion");
        help_pop.getMenu().add(0, 3, 3, "My Details");
        help_pop.getMenu().add(0, 4, 4, "Feedback");
        help_pop.getMenu().add(0, 5, 5, "Concept Video");
        help_pop.getMenu().add(0, 6, 6, "FAQ");

        help_pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Bundle b = new Bundle();
                b.putInt("option", item.getItemId());

                if (item.getItemId() == 5 || item.getItemId() == 6) {

                    Intent i = new Intent(MainActivity.this, Help.class);
                    i.putExtra("itemid", item.getItemId());
                    startActivity(i);

//                    IntroductionFragment introductionFragment = new IntroductionFragment();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.container,introductionFragment).addToBackStack("intro").commit();

                } else {
                    HelpFragment helpFragment = new HelpFragment();
                    helpFragment.setArguments(b);
                    fragmentManager.beginTransaction().replace(R.id.container, helpFragment).addToBackStack("help").commit();
                }

                return false;
            }
        });


        tb_ranking = findViewById(R.id.tb_ranking);
        tb_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.container, new RankingFragment()).addToBackStack("ranking").commit();
            }
        });

        notificationUpdateReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(RetroClient.TAG, "onReceive: ony hr redcivrtt");
                //Toast.makeText(context, "Test " + getIntent().getStringExtra("added"), Toast.LENGTH_SHORT).show();
                checkNotificationCount();
            }
        };


        rl_badge_holder = findViewById(R.id.rl_badge_holder);
        tv_badge_count = findViewById(R.id.tv_badge_count);
        tb_notification = findViewById(R.id.tb_notification);
        tb_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent i = new Intent(MainActivity.this,NotificationActivity.class);
                //startActivity(i);

                fragmentManager.beginTransaction().replace(R.id.container, new NotificationFragment()).addToBackStack(null).commit();

            }
        });

        bottomNavigationView = findViewById(R.id.bottom_nav);
        BottomNavigationViewHelper.removeShiftMode(MainActivity.this, bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.menu_addsuggestions:
                        selectedFragment = new AddSuggestionFragment();
                        break;
                    case R.id.menu_feedback:
                        selectedFragment = new FeedbackFragment();
                        break;
                    case R.id.menu_findsuggestions: {
                        selectedFragment = new FindSuggestionsFragment();
                        break;
                    }
                    case R.id.menu_mydetails:
                        selectedFragment = new MyDetailsFragment();
                        break;
                }

                String tag = selectedFragment.getClass().getName();
                Fragment fragment = fragmentManager.findFragmentByTag(tag);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                if (fragment == null)
                    transaction.add(R.id.container, selectedFragment, tag);
                else
                    transaction.replace(R.id.container, fragment, tag);
                transaction.commit();
                return true;
            }
        });

        //adding home fragment
        fragmentManager.beginTransaction()
                .add(R.id.container, new HomeFragment(), HomeFragment.class.getName())
                .commit();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(RetroClient.TAG, "onCreate: " + token);
        if (token != null) {
            saveRegistrationId(token);
        }

        //checkPermissionForLocation();
        checkNotificationCount();

    }

    public void checkNotificationCount() {


        Notifications n = RetroClient.getClient().create(Notifications.class);
        Call<NotificationResponse> call = n.getNotifications(session.getToken(), "1", "b480e7a958e6f13a");
        //Call<NotificationResponse> call = n.getNotifications(session.getToken(),session.getUserID(), Settings.Secure.getS
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getData().size() > 0) {
                        tv_badge_count.setText("" + response.body().getData().size());
                        rl_badge_holder.setVisibility(View.VISIBLE);
                    } else {
                        //tv_badge_count.setText("" + response.body().getData().size());
                        rl_badge_holder.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

            }
        });

    }

    public void checkPermissionForLocation(int location_purpose) {

        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //permission not granted

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    location_purpose);
        } else {
            getCurrentLocation(location_purpose);

        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation(final int location_purpose) {
        if (location_purpose == LOAD_HOME_AREAS && !isFetched) {
            isFetched = true;
            Toast.makeText(this, R.string.fetching_location, Toast.LENGTH_LONG).show();
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        fusedLocationProviderClient.getLocationAvailability().addOnSuccessListener(new OnSuccessListener<LocationAvailability>() {
            @Override
            public void onSuccess(LocationAvailability locationAvailability) {
                locationAvailability.isLocationAvailable();
            }
        });

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                if (location != null) {
                    lastLocation = location;
                    Log.d(RetroClient.TAG, "onSuccess: " + lastLocation.getLatitude());
                    Log.d(RetroClient.TAG, "onSuccess: " + lastLocation.getLongitude());

                    Geocoder g = new Geocoder(MainActivity.this, Locale.getDefault());
                    List<Address> addressList;

                    try {

//                            addressList =  g.getFromLocation(19.2093305,73.0645624,1);
                        addressList = g.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1);
//                            Log.d(RetroClient.TAG, "onSuccess: " + addressList.get(0).getSubLocality().split(" ")[0] );

                        //Toast.makeText(MainActivity.this, "Fetching location", Toast.LENGTH_SHORT).show();

                        String location_split = addressList.get(0).getSubLocality().replace(" West", "").replace(" East", "");

                        session.setlastknownlocation(location_split);

                        Fragment f = fragmentManager.findFragmentByTag(new FindSuggestionsFragment().getClass().getName());
                        Fragment home = fragmentManager.findFragmentByTag(new HomeFragment().getClass().getName());
                        if (f != null || home != null) {
                            if (location_purpose == LOAD_SUGGESTIONS)
                                ((FindSuggestionsFragment) f).setSuggestionsByCurrentLocation(location_split);
                            else if (location_purpose == LOAD_AREAS)
                                ((FindSuggestionsFragment) f).setAreasByCurrentLocation(location_split, "" + lastLocation.getLatitude(), "" + lastLocation.getLongitude());
                            else if (location_purpose == LOAD_HOME_SUGGESTIONS)
                                ((HomeFragment) home).setTest(location_split, "" + lastLocation.getLatitude(), "" + lastLocation.getLongitude());
                            else if (location_purpose == LOAD_HOME_AREAS)
                                ((HomeFragment) home).setAreasByCurrentLocation(addressList.get(0).getAddressLine(0), location_split, "" + lastLocation.getLatitude(), "" + lastLocation.getLongitude());

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    if (lastLocation.getLongitude() > 18.75) {
                        lastLocationText = "Mumbai";
                        session.setcurrentcity(1);
                    } else {
                        session.setcurrentcity(2);
                        lastLocationText = "Pune";
                    }

                } else {
                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    boolean gps_enabled = false;
                    boolean network_enabled = false;

                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                    if (!gps_enabled && !network_enabled) {
                        Utils.displayPromptForEnablingGPS(MainActivity.this);
                    } else {
                        Toast.makeText(MainActivity.this, "Couldn't fetch location at the moment.", Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(MainActivity.this, "Couldn't fetch location. Please turn on your GPS.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case LOAD_AREAS:
                case LOAD_HOME_AREAS:
                case LOAD_SUGGESTIONS: {
                    getCurrentLocation(requestCode);
                    return;
                }
            }
        } else {
            if (requestCode == LOAD_HOME_AREAS && !isFetched) {
                isFetched = true;
                Toast.makeText(this, R.string.fetching_data, Toast.LENGTH_LONG).show();
            }
            session.setcurrentcity(1);
            Fragment f = fragmentManager.findFragmentByTag(new FindSuggestionsFragment().getClass().getName());
            Fragment home = fragmentManager.findFragmentByTag(new HomeFragment().getClass().getName());
            if (f != null || home != null) {
                String lati = null, longi = null;
                if (requestCode == LOAD_SUGGESTIONS)
                    ((FindSuggestionsFragment) f).setSuggestionsByCurrentLocation(null);
                else if (requestCode == LOAD_AREAS)
                    ((FindSuggestionsFragment) f).setAreasByCurrentLocation(null, lati, longi);
                else if (requestCode == LOAD_HOME_SUGGESTIONS)
                    ((HomeFragment) home).setTest(null, "", "");
                else if (requestCode == LOAD_HOME_AREAS)
                    ((HomeFragment) home).setAreasByCurrentLocation(null, null, lati, longi);
            }
        }
    }

    private void saveRegistrationId(String token) {

        SaveRegistrationId data = new SaveRegistrationId(session.getUserID(), Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID), "2", token);
        Notifications n = RetroClient.getClient().create(Notifications.class);
        Call<saveRegId> call = n.saveRegId(session.getToken(), data);

        call.enqueue(new Callback<saveRegId>() {
            @Override
            public void onResponse(Call<saveRegId> call, Response<saveRegId> response) {
                //Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<saveRegId> call, Throwable t) {

            }
        });
    }


    @Override
    public void onBackPressed() {


        MyDetailsFragment frag = (MyDetailsFragment) fragmentManager.findFragmentByTag(new MyDetailsFragment().getClass().getName());
        if (frag != null && frag.isVisible()) {
            if (frag.isAddingNewContact) {
                bottomNavigationView.setSelectedItemId(R.id.menu_mydetails);
            }
            //Toast.makeText(this, "is visible", Toast.LENGTH_SHORT).show();
        }

        if (fragmentManager.getBackStackEntryCount() < 1) {

            if (isBackPressedOnce) {

                session.setlastsplashsreentime(Calendar.getInstance().getTimeInMillis());
                Log.d("12345", "Exiting at " + session.getlastsplashsreentime());

                super.onBackPressed();

            } else {

                isBackPressedOnce = true;

                //Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isBackPressedOnce = false;
                    }
                }, 1000);
            }

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showLoader() {
        bottomNavigationView.setVisibility(View.GONE);
        ll_loader.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoader() {
        ll_loader.setVisibility(View.GONE);
        if (homeButtonLogo.isEnabled())
            bottomNavigationView.setVisibility(View.VISIBLE);
    }

    public void enableHomeFragmentIcons() {
        findViewById(R.id.tb_filter).setVisibility(View.GONE);
        findViewById(R.id.tb_ranking).setVisibility(View.GONE);
    }

    public void setBottomNavigationView(int i) {

        switch (i) {

            case 1:
                bottomNavigationView.setSelectedItemId(R.id.menu_findsuggestions);
            case 2:
                bottomNavigationView.setSelectedItemId(R.id.menu_addsuggestions);
            case 3:
                bottomNavigationView.setSelectedItemId(R.id.menu_mydetails);
            case 4:
                bottomNavigationView.setSelectedItemId(R.id.menu_feedback);
            default:

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(notificationUpdateReciever, new IntentFilter(Message.NOTIFICATION_UPDATED));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(notificationUpdateReciever);
    }
}

