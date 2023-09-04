package com.example.padnica_zoo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import com.google.gson.Gson;
import com.pandica_zoo.models.Notification;
import com.pandica_zoo.models.Package;
import com.pandica_zoo.models.UserList;
import com.pandica_zoo.utils.AssetsUtils;
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

        UserList userList = gson.fromJson(json, UserList.class);
        List<User> users = userList.getUsers();
        //login
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // Authentication
                for (User user : users) {
                    if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                        // Save the logged user in SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("logged", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", user.getUsername());
                        editor.apply();

                        // Login successful, navigate to the Home activity
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
                // Failed log in
                SharedPreferences sharedPreferences = getSharedPreferences("logged", Context.MODE_PRIVATE);
                String usernameOfLogged = sharedPreferences.getString("username", "");
                if(usernameOfLogged==null){
                    Toast.makeText(getApplicationContext(), "Incorrect username or password!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
