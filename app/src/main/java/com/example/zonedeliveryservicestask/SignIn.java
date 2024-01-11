package com.example.zonedeliveryservicestask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private TextInputLayout userName, enterPassword;
    private TextInputEditText userEmail, userPassword;
    private Button loginBtn;
    private TextView newUserRegister;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userName = findViewById(R.id.EnterUserName);
        userEmail = findViewById(R.id.EnterUserEmail);
        enterPassword = findViewById(R.id.EnterPassword);
        userPassword = findViewById(R.id.UserPassword);
        loginBtn = findViewById(R.id.LoginButton);
        newUserRegister = findViewById(R.id.idNewUser);
        progressBar = findViewById(R.id.ProgressBar);
        mAuth = FirebaseAuth.getInstance();


        newUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SignIn.this, Register.class);
                startActivity(i);

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();

                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    Toast.makeText(SignIn.this, "Please enter the credentionals", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password). addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignIn.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignIn.this, "Please enter valid user credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}