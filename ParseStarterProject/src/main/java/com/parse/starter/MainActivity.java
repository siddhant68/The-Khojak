/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class MainActivity extends AppCompatActivity {

    Button khojakCarpoolButton;
    Button busFinderButton;
    Button nfcButton;
    Intent nfcIntent;
    Intent khojakCarpoolIntent;
    Intent busFinderLoginIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        khojakCarpoolButton = (Button) findViewById(R.id.carpool_button);
        busFinderButton = (Button) findViewById(R.id.bus_finder_button);
        nfcButton = (Button) findViewById(R.id.nfc_button);

        khojakCarpoolIntent = new Intent(getApplicationContext(), KhojakCarpool.class);
        busFinderLoginIntent = new Intent(getApplicationContext(), BusFinderLogin.class);
        nfcIntent = new Intent(getApplicationContext(), NFCActivity.class);
        khojakCarpoolButton.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        startActivity(khojakCarpoolIntent);
                    }
                }
        );
        busFinderButton.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        startActivity(busFinderLoginIntent);
                    }
                }
        );
        nfcButton.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        startActivity(nfcIntent);
                    }
                }
        );
    }
}