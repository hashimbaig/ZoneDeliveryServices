package com.example.zonedeliveryservicestask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textfield.TextInputEditText;

public class Register extends AppCompatActivity {

    private TextInputLayout userName,enterPassword,  confirmPassword;
    private TextInputEditText  userEmail,  userPassword,userConfirmPassword;
    private TextView alreadyUser;
    private Button btnsignIn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.UserName);
        userEmail = findViewById(R.id.Email);
        enterPassword = findViewById(R.id.Password);
        userPassword = findViewById(R.id.EnterPassword);
        confirmPassword = findViewById(R.id.ConfirmPassword);
        userConfirmPassword = findViewById(R.id.EnterConfirmPassword);
        alreadyUser = findViewById(R.id.AlreadyUser);
        btnsignIn = findViewById(R.id.BtnRegister);
        progressBar = findViewById(R.id.PBLoading);
        mAuth = FirebaseAuth.getInstance();

        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, SignIn.class);
                startActivity(i);
            }
        });

        btnsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();
                String passwordConfirm = userConfirmPassword.getText().toString();

                if (!password.equals(passwordConfirm)){
                    Toast.makeText(Register.this, "Please check both having same password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(passwordConfirm)) {
                    Toast.makeText(Register.this, "Please enter your credentials", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Register.this, "User Registered", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Register.this, SignIn.class);
                                startActivity(i);
                                finish();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Register.this, "Not User Registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
