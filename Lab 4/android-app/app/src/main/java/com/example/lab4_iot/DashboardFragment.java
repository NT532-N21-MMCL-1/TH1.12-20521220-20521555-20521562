package com.example.lab4_iot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

public class DashboardFragment extends Fragment {
    private String TAG = "mqtt check";
    private ImageButton light1, light2;

    private static final String BROKER_URL = "tcp://172.31.9.11:1883";
    private static final String CLIENT_ID = "client_android";
    private MqttHandler mqttHandler;

    private Timer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ViewPager viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        light1 = view.findViewById(R.id.btnLight1);
        light2 = view.findViewById(R.id.btnLight2);

        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new TemperatureRoomFragment(), "Temperature");
        adapter.addFragment(new HumidityRoomFragment(), "Humidity");
        adapter.addFragment(new LightRoomFragment(), "Light");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        initMQTT();

        return view;
    }

    void initMQTT(){
        mqttHandler = new MqttHandler();
        mqttHandler.connect(BROKER_URL,CLIENT_ID);

        light1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mqttHandler.publish("mmcl/nhom12/led/n1", "clicked");
            }
        });

        light2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mqttHandler.publish("mmcl/nhom12/led/n2", "clicked");
            }
        });
    }
}