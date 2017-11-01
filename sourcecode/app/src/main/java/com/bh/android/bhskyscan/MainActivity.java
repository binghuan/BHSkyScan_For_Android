package com.bh.android.bhskyscan;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements FlightListFragment.OnFragmentInteractionListener {

    private FlightListFragment mFlightListFragment = null;
    private FragmentTransaction mFragmentTransaction = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();


        Date nextMonday = MyUtils.getNextMonday();
        String outboundDateStr = MyUtils.getDateByFormatMMDDDayOFWeek(nextMonday);
        Date followingDay = MyUtils.getFollowingDay(nextMonday);
        String inboundDateStr = MyUtils.getDateByFormatMMDDDayOFWeek(followingDay);
        if (actionBar != null) {
            actionBar.setTitle("EDI-sky to LOND-sky");
            actionBar.setSubtitle(outboundDateStr + " - " + inboundDateStr);
        }


        FragmentManager mFragmentMgr = getSupportFragmentManager();
        mFragmentTransaction = mFragmentMgr.beginTransaction();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if (mFlightListFragment == null) {
                    mFlightListFragment = new FlightListFragment();
                    mFragmentTransaction.add(R.id.fragment, mFlightListFragment)
                            .commit();
                }
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
