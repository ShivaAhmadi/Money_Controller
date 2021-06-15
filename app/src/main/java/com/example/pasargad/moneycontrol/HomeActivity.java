package com.example.pasargad.moneycontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.volcaniccoder.bottomify.BottomifyNavigationView;
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {


    DrawerLayout drawerLayout;
    ImageView btMenu;
    ReportFragment reportFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);
        btMenu.setVisibility(View.VISIBLE);
        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        BottomifyNavigationView bottomifyNavigationView=findViewById(R.id.bottomify_nav);


        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bottomifyNavigationView.setActiveNavigationIndex(0);
                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reportFragment.scrollTo(i);
                    }
                }, 1000);
            }
        };

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new HomeFragment(listener));
        transaction.commitNow();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        ((TextView) findViewById(R.id.username)).setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        ((Button) findViewById(R.id.logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast=Toast.makeText(getApplicationContext(),"  شما از حساب کاربری خارج شدید!  ",Toast.LENGTH_SHORT);
                View view1=toast.getView();
                view1.setBackgroundResource(R.drawable.toastbackground);
                toast.show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                finish();
            }
        });

        ((Button)findViewById(R.id.btnAbout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,AboutActivity.class));
            }
        });

        reportFragment = new ReportFragment();
        bottomifyNavigationView.setOnNavigationItemChangedListener(new OnNavigationItemChangeListener() {
            @Override
            public void onNavigationItemChanged(BottomifyNavigationView.NavigationItem navigationItem) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (navigationItem.getPosition()){
                    case 0:
                        transaction.replace(R.id.content, reportFragment);
                        transaction.commitNow();
                        break;
                    case 1:
                        transaction.replace(R.id.content,new RegisterFragment());
                        transaction.commitNow();
                        break;
                    case 2:
                        transaction.replace(R.id.content,new HomeFragment(listener));
                        transaction.commitNow();
                        break;
                }
            }
        });



    }
    public void yourMethod(View view) {
        Intent intent = new Intent(HomeActivity.this, EditActivity.class);
        startActivity(intent);
    }
}
