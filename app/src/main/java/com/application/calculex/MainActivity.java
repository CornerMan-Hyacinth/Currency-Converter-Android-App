package com.application.calculex;

import android.os.Bundle;

import com.application.calculex.ui.main.ViewPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.application.calculex.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        ViewPager2 viewPager = binding.viewPager;
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabs = binding.tabs;
        FloatingActionButton fab = binding.fab;

//      tabs - FIAT & CRYPTO
        new TabLayoutMediator(tabs, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull Tab tab, int position) {
                if(position == 0){
                    tab.setText("FIAT").setIcon(R.drawable.tab_layout_fiat);
                }
                else if(position == 1){
                    tab.setText("CRYPTO").setIcon(R.drawable.tab_layout_coin);
                }
            }
        }).attach();

//        Tab tab1 = tabs.newTab().setText("FIAT").setIcon(R.drawable.tab_layout_fiat);
//        tabs.addTab(tab1);
//        Tab tab2 = tabs.newTab().setText("STOCK").setIcon(R.drawable.tab_layout_stock);
//        tabs.addTab(tab2);
//        Tab tab3 = tabs.newTab().setText("CRYPTO").setIcon(R.drawable.tab_layout_coin);
//        tabs.addTab(tab3);
//
//        tabs.setupWithViewPager(viewPager);

        fab.setOnClickListener(view -> Snackbar.make(view, "Got Questions?\nEmail me at hycorner462@gmail.com", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }
}