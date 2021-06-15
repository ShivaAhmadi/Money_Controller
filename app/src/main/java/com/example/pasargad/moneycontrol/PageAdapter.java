package com.example.pasargad.moneycontrol;

import android.content.Context;
import android.widget.AdapterView;

import com.squareup.okhttp.internal.framed.FramedConnection;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    int tabcount;
    private Context muContext;
    private AdapterView.OnItemClickListener Listener;

    public PageAdapter(@NonNull Context context, FragmentManager fm, int behavior) {
        super(fm, behavior);
        muContext=context;
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                HomeFragment homeFragment=new HomeFragment(Listener);
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
