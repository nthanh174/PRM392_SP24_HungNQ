package com.example.shopf.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.shopf.Adapters.ViewPagerAdapter;
import com.example.shopf.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {
    private ViewPager vpHome;
    private BottomNavigationView bnvHome;
    private FloatingActionButton floatingActionButton;
    private BottomAppBar babHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Enable EdgeToEdge
        EdgeToEdge.enable(this);

        // Apply WindowInsets to adjust layout for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Extract system bar insets
            Insets systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Apply these insets as padding to the view
            v.setPadding(systemInsets.left, systemInsets.top, systemInsets.right, systemInsets.bottom);

            // Return insets (unchanged) as the listener requires
            return insets;
        });


        // Bind views
        vpHome = findViewById(R.id.vpHome);
        bnvHome = findViewById(R.id.bnvHome);
        floatingActionButton = findViewById(R.id.fabCart);
        babHome = findViewById(R.id.babHome);

        // Setup ViewPager with adapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpHome.setAdapter(viewPagerAdapter);

        // ViewPager page change listener
        vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                // Update BottomNavigationView when ViewPager page changes
                bnvHome.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

// BottomNavigationView item click listener
        bnvHome.setOnNavigationItemSelectedListener(item -> {
            // Handle BottomNavigationView item clicks
            if (item.getItemId() == R.id.mnHome) {
                vpHome.setCurrentItem(0);
                return true;
            } else if (item.getItemId() == R.id.mnfood) {
                vpHome.setCurrentItem(1);
                return true;
            } else if (item.getItemId() == R.id.mnAccount) {
                vpHome.setCurrentItem(2);
                return true;
            } else if (item.getItemId() == R.id.mnSetting) {
                vpHome.setCurrentItem(3);
                return true;
            } else {
                return false;
            }
        });


        // Handle FloatingActionButton click
        floatingActionButton.setOnClickListener(v -> {
            // Start CartActivity when FloatingActionButton is clicked
            startActivity(new Intent(HomeActivity.this, CartActivity.class));
        });

        // Handle BottomAppBar click
        babHome.setNavigationOnClickListener(v -> {
            // Handle BottomAppBar navigation click (change as per your requirement)
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
        });
    }
}
