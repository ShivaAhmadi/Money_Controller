package com.example.pasargad.moneycontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText inputEmail,inputPassword;
    Button btnLogin,btnSignUp;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null && !auth.getCurrentUser().isAnonymous()) {
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
            return;
        }

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);

        btnLogin=findViewById(R.id.btnLogin);
        btnSignUp=findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email=inputEmail.getText().toString();
                password=inputPassword.getText().toString();

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            finish();
                            Toast toast=Toast.makeText(getApplicationContext(),"   شما با موفقیت وارد شدید!   ",Toast.LENGTH_SHORT);
                            View view=toast.getView();
                            view.setBackgroundResource(R.drawable.toastbackground);
                            toast.show();
                        }
                        else {
                            Toast.makeText(LoginActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                finish();
            }
        });
    }
}
