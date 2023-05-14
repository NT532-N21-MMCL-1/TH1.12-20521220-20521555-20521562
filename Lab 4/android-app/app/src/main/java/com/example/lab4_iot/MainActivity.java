package com.example.lab4_iot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Fragment[] fragments;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments = new Fragment[]{
                new HomeFragment(),
                new DashboardFragment(),
                new InsightsFragment(),
                new LogsFragment()
        };

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        frameLayout = findViewById(R.id.frame_container);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int position = item.getItemId();
            if (position == R.id.navigation_fragment_view) {
                // Hiển thị Fragment View
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, new HomeFragment())
                        .commit();
            } else {
                // Hiển thị Fragment tương ứng với MenuItem
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragments[position])
                        .commit();
            }
            return true;
        });
    }
}