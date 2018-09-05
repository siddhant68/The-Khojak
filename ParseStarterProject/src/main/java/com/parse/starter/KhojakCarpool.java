package com.parse.starter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;

public class KhojakCarpool extends AppCompatActivity {

    public void getStarted(View view) {

        ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        //check Internet connection
        if (internet || wifi) {
            Switch userTypeSwitch = (Switch) findViewById(R.id.user_type_switch);
            if (userTypeSwitch.isChecked()) {
                Intent driverLoginIntent = new Intent(getApplicationContext(), DriverLoginActivity.class);
                startActivity(driverLoginIntent);
                finish();
            } else {
                Intent riderLoginIntent = new Intent(getApplicationContext(), RiderLoginActivity.class);
                startActivity(riderLoginIntent);
                finish();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("No internet connection")
                    .setMessage("Please turn on mobile data or wifi")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //code for exit
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                    })
                    .show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khojak_carpool);
        getSupportActionBar().hide();

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
        if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().get("riderOrDriver") != null) {
            if (ParseUser.getCurrentUser().get("riderOrDriver").equals("rider")) {
                Intent riderActivityIntent = new Intent(getApplicationContext(), RiderActivity.class);
                startActivity(riderActivityIntent);
                finish();
            }
        }
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}

