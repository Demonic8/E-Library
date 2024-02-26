package com.example.e_library;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class verifycode extends AppCompatActivity {

    private EditText codeEditText;
    private Button verifyCodeButton;
    private TextView resendVerificationCodeTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifycode);

        mAuth = FirebaseAuth.getInstance();
        codeEditText = findViewById(R.id.editTextCode);
        verifyCodeButton = findViewById(R.id.verifybtn);
        resendVerificationCodeTextView = findViewById(R.id.resendvcode);

        verifyCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = codeEditText.getText().toString().trim();

                if (TextUtils.isEmpty(code) || code.length() != 6) {
                    Toast.makeText(getApplicationContext(), "Please enter a 6-digit verification code", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = getIntent().getStringExtra("email");

                mAuth.verifyPasswordResetCode(code).addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(verifycode.this, confirmchange.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid verification code", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        resendVerificationCodeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getIntent().getStringExtra("email");

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Password reset email sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}