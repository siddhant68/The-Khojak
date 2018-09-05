package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class BusFinderLogin extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    Boolean signUpModeActive = true;
    TextView changeSignupModeTextView;
    EditText passwordEditText;
    EditText phoneNumberEditText;
    EditText usernameEditText;

    public void showBusFinderActivity() {

        Intent busFinderIntent = new Intent(getApplicationContext(), BusFinder.class);
        startActivity(busFinderIntent);
        finish();
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            signUp(view);
        }
        return false;

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.change_SignupMode_TextView) {

            Button signupButton = (Button) findViewById(R.id.rider_signup_Button);
            EditText phoneNumberEditText = (EditText) findViewById(R.id.phoneNumber_editText);
            if (signUpModeActive) {

                signUpModeActive = false;
                signupButton.setText("Login");
                changeSignupModeTextView.setText("Or, Signup");
                phoneNumberEditText.setVisibility(View.INVISIBLE);

            } else {

                signUpModeActive = true;
                signupButton.setText("Signup");
                changeSignupModeTextView.setText("Or, Login");
                phoneNumberEditText.setVisibility(View.VISIBLE);

            }

        } else if (view.getId() == R.id.rider_login_background) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }

    }

    public void signUp(View view) {

        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {

            Toast.makeText(this, "A username and password are required.", Toast.LENGTH_SHORT).show();

        } else {

            if (signUpModeActive) {

                ParseUser user = new ParseUser();

                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                user.put("riderOrDriver", "rider");
                user.put("phoneNumber", phoneNumberEditText.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {

                            Log.i("Signup", "Successful");
                            showBusFinderActivity();

                        } else {

                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            } else {

                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (user != null) {

                            Log.i("Signup", "Login successful");
                            showBusFinderActivity();

                        } else {

                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    }
                });


            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().get("riderOrDriver") != null) {
            if (ParseUser.getCurrentUser().get("riderOrDriver").equals("rider")) {
                Intent busFinderIntent = new Intent(getApplicationContext(), BusFinder.class);
                startActivity(busFinderIntent);
                finish();
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_finder_login);

        getSupportActionBar().hide();
        changeSignupModeTextView = (TextView) findViewById(R.id.change_SignupMode_TextView);
        changeSignupModeTextView.setOnClickListener(this);

        RelativeLayout backgroundRelativeLayout = (RelativeLayout) findViewById(R.id.bus_finder_login_background);
        backgroundRelativeLayout.setOnClickListener(this);

        passwordEditText = (EditText) findViewById(R.id.password_editText);
        passwordEditText.setOnKeyListener(this);

        phoneNumberEditText = (EditText) findViewById(R.id.phoneNumber_editText);

        usernameEditText = (EditText) findViewById(R.id.username_editText);
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

        usernameEditText.setFilters(new InputFilter[]{filter});

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}

