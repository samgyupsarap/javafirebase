package com.example.appv100;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Log extends AppCompatActivity {

    TextView textview;
    EditText emailLog, passLog;
    Button buttonLog;
    FirebaseAuth mAuth;
    ProgressBar progressLog;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Log.this, Index.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailLog = findViewById(R.id.emailLog);
        passLog = findViewById(R.id.passwordLog);
        buttonLog = findViewById(R.id.buttonLog);
        mAuth = FirebaseAuth.getInstance();
        progressLog= findViewById(R.id.progressLog);



        textview = (TextView)findViewById(R.id.login);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Log.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressLog.setVisibility(View.VISIBLE);
                String email, password;
                email = emailLog.getText().toString();
                password = passLog.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Log.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Log.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressLog.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    Toast.makeText(Log.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Log.this, Index.class);
                                    startActivity(intent);
                                    finish();
                                } else {

                                    Toast.makeText(Log.this, "Login failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}