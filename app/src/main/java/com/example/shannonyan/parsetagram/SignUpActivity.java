package com.example.shannonyan.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private Button btSignUp;
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btSignUp = findViewById(R.id.btSignUp);




        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser user = new ParseUser();
                // Set core properties
                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());

                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getBaseContext(),"New account created", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getBaseContext(),"Error in creating an account", Toast.LENGTH_SHORT).show();
                        }

                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
                });




            }
        });
    }




}
