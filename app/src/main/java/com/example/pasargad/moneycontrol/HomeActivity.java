package com.example.pasargad.moneycontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.volcaniccoder.bottomify.BottomifyNavigationView;
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new HomeFragment());
        transaction.commitNow();

        BottomifyNavigationView bottomifyNavigationView=findViewById(R.id.bottomify_nav);
        bottomifyNavigationView.setOnNavigationItemChangedListener(new OnNavigationItemChangeListener() {
            @Override
            public void onNavigationItemChanged(BottomifyNavigationView.NavigationItem navigationItem) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (navigationItem.getPosition()){
                    case 0:
                        transaction.replace(R.id.content,new ReportFragment());
                        transaction.commitNow();
                        break;
                    case 1:
                        transaction.replace(R.id.content,new RegisterFragment());
                        transaction.commitNow();
                        break;
                    case 2:
                        transaction.replace(R.id.content,new HomeFragment());
                        transaction.commitNow();
                        break;
                }
            }
        });

    }
}
