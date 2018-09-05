package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class DriverLoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            loginButtonClicked(view);
        }
        return false;

    }

    public void loginButtonClicked(View view) {

        final Intent driverRequestsintent = new Intent(getApplicationContext(), DriverRequestsActivity.class);
        EditText usernameEditText = (EditText) findViewById(R.id.username_editText);
        EditText passwordEditText = (EditText) findViewById(R.id.password_editText);
        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {
            Toast.makeText(this, "A username and password are required", Toast.LENGTH_SHORT).show();
        } else {
            ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        startActivity(driverRequestsintent);
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        getSupportActionBar().hide();
        RelativeLayout backgroundRelativeLayout = (RelativeLayout) findViewById(R.id.driver_login_background);
        backgroundRelativeLayout.setOnClickListener(this);
        EditText passwordEditText = (EditText) findViewById(R.id.password_editText);
        passwordEditText.setOnKeyListener(this);
        EditText usernameEditText = (EditText) findViewById(R.id.username_editText);
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
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.driver_login_background) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }

    }
}

