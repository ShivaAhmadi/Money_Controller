package com.example.pasargad.moneycontrol;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.okhttp.internal.framed.FramedConnection;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class PageAdapter extends FragmentPagerAdapter {

    int tabcount;
    private Context muContext;
   // private AdapterView.OnItemClickListener listener;
    ReportFragment reportFragment;

    public PageAdapter(@NonNull Context context, FragmentManager fm, int behavior) {
        super(fm, behavior);
        muContext=context;
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        reportFragment = new ReportFragment();

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //bottomifyNavigationView.setActiveNavigationIndex(0);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reportFragment.scrollTo(i);
                    }
                }, 1000);
            }
        };


        switch (position){
            case 0:
                HomeFragment homeFragment=new HomeFragment(listener);
                return homeFragment;
            case 1:
                RegisterFragment registerFragment=new RegisterFragment();
                return registerFragment;
            case 2:
                ReportFragment reportFragment=new ReportFragment();
                return reportFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
