package com.example.lab4_iot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    TextView titleScreen;
    Fragment homeFragment = new HomeFragment();
    Fragment dashboardFragment = new DashboardFragment();
    Fragment insightsFragment = new InsightsFragment();
    Fragment logsFragment = new LogsFragment();
    Fragment aiFragment = new RainPredictionFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleScreen = findViewById(R.id.titleScreen);
        titleScreen.setText("Welcome");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }

        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setItemIconTintList(null);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch(item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = homeFragment;
                        titleScreen.setText("Welcome");
                        break;
                    case R.id.navigation_dashboard:
                        selectedFragment = dashboardFragment;
                        titleScreen.setText("Dashboard & Control Lights");
                        break;
                    case R.id.navigation_ai:
                        selectedFragment = aiFragment;
                        titleScreen.setText("AI");
                        break;
                    case R.id.navigation_insights:
                        selectedFragment = new InsightsFragment();
                        titleScreen.setText("Visualize Data");
                        break;
                    case R.id.navigation_logs:
                        selectedFragment = logsFragment;
                        titleScreen.setText("System Logs");
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });
    }
}