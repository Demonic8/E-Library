package com.example.e_library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login extends AppCompatActivity {

    TextInputEditText EditTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView register, forgetpword, errorText;

    private static final String EMAIL_REGEX = "[a-zA-Z0-9._-]+@[a-z0-9._-]+\\.[a-z0-9._-]+";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        EditTextEmail = findViewById(R.id.Email);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_Login);
        progressBar = findViewById(R.id.progressBar);
        register = findViewById(R.id.registerNow);
        forgetpword = findViewById(R.id.forgetpassword);
        errorText = findViewById(R.id.errorText);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });
        forgetpword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), forgotpassword.class);
                startActivity(intent);
                finish();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(EditTextEmail.getText().toString());
                password = String.valueOf(editTextPassword.getText().toString());

                if (TextUtils.isEmpty(email)) {
                    errorText.setText("Please Type Your Email Address");
                    errorText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    errorText.setText("Please Type Your Password");
                    errorText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (!isEmailValid(email)) {
                    errorText.setText("Invalid Email Address");
                    errorText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(getApplicationContext(), Home.class);
                                    intent.putExtra("displayName", mAuth.getCurrentUser().getDisplayName());
                                    startActivity(intent);
                                    finish();
                                } else {
                                    errorText.setText("Incorrect Email or Password");
                                    errorText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                                }
                            }
                        });
            }
        });
    }

    // Method to validate email address format
    private boolean isEmailValid(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
