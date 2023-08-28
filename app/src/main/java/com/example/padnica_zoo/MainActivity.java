package com.example.padnica_zoo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.padnica_zoo.utils.AssetsUtils;
import com.pandica_zoo.models.User;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get assets from db.json
        String json = AssetsUtils.readJsonFile(this, "db.json");

        Gson gson = new Gson();

        User[] users = gson.fromJson(json, User[].class);

        //login
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Perform login validation here
                if (username.equals("your_username") && password.equals("your_password")) {
                    // Login successful, you can navigate to the next activity
                } else {
                    // Login failed, show an error message
                }
            }
        });
    }
}
