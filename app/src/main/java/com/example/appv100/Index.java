package com.example.appv100;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Index extends AppCompatActivity {

    FirebaseAuth auth;
    Button buttonOut;
    TextView textView;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_index);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        buttonOut = findViewById(R.id.buttonOut);
        textView = findViewById(R.id.textView2);
        user = auth.getCurrentUser();

        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Log.class);
            startActivity(intent);
            finish();
        }
        else{
            textView.setText(user.getEmail());
        }

        buttonOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Index.this, Log.class);
                startActivity(intent);
                finish();
            }
        });
    }
}