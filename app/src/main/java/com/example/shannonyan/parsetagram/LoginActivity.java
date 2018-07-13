package com.example.shannonyan.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btSignIn;
    private Button btSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            setContentView(R.layout.activity_home);
        } else {
            etUsername = findViewById(R.id.etUsername);
            etPassword = findViewById(R.id.etPassword);
            btSignIn = findViewById(R.id.btSignIn);
            btSignUp = findViewById(R.id.btSignUp);

            btSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("on click", "on click");
                    final String username = etUsername.getText().toString();
                    final String password = etPassword.getText().toString();

                    login(username, password);
                }
            });

            btSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(i);
                    setContentView(R.layout.activity_sign_up2);
                }
            });
        }



    }

    private void login(String username, String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if( e == null){
                    Toast.makeText(getBaseContext(),"Login successful",Toast.LENGTH_SHORT).show();
                    final  Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(),"Login unsuccessful",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}
