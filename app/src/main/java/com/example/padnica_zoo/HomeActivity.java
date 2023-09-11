package com.example.padnica_zoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageButton;

import com.example.padnica_zoo.ui.slideshow.SlideshowFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.padnica_zoo.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private ImageButton homeImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        /*homeImageButton = findViewById(R.id.homeImageButton);
        homeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to home page (this is mainly for other pages to go to home)
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });*/

        SlideshowFragment slideshowFragment = new SlideshowFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Optional: If you want to add the transaction to the back stack
        transaction.addToBackStack(null);

        transaction.replace(R.id.slideshow_container, slideshowFragment);

        try {
            transaction.commit();
        } catch (Exception e) {
            // Handle any exceptions here
            e.printStackTrace();
        }

       /* SlideshowFragment slideshowFragment = new SlideshowFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.slideshow_container, slideshowFragment)
                .addToBackStack(null) // Optional: To add to the back stack
                .commit();*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}