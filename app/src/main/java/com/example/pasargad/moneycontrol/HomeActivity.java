package com.example.pasargad.moneycontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class HomeActivity extends BaseActivity {


    DrawerLayout drawerLayout;
    ImageView btMenu;
    ReportFragment reportFragment;
    TabLayout tabLayout;
    TabItem tabItem1,tabItem2,tabItem3;
    ViewPager viewPager;
    PageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        tabLayout=(TabLayout) findViewById(R.id.tablayout1);
        tabLayout.addTab(tabLayout.newTab().setText("صفحه اصلی"));
        tabLayout.addTab(tabLayout.newTab().setText("ثبت تراکنش"));
        tabLayout.addTab(tabLayout.newTab().setText("گزارش"));

        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);
        btMenu.setVisibility(View.VISIBLE);
        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        ((TextView) findViewById(R.id.username)).setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        ((Button) findViewById(R.id.logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(HomeActivity.this,LoginActivity.class));


                Toast toast=Toast.makeText(getApplicationContext(),"  شما از حساب کاربری خارج شدید!  ",Toast.LENGTH_SHORT);
                View view1=toast.getView();
                view1.setBackgroundResource(R.drawable.toastbackground);
                toast.show();
                finish();



            }
        });

        ((Button)findViewById(R.id.btnAbout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,AboutActivity.class));
            }
        });


        viewPager=(ViewPager) findViewById(R.id.viewPager);
        final PageAdapter adapter = new PageAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        reportFragment = new ReportFragment();
    }
    public void yourMethod(View view) {
        Intent intent = new Intent(HomeActivity.this, EditActivity.class);
        startActivity(intent);
    }
}
