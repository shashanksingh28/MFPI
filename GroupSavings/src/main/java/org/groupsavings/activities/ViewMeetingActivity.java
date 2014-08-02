package org.groupsavings.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.constants.Intents;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.domain.Group;
import org.groupsavings.domain.GroupMeeting;
import org.groupsavings.domain.Member;
import org.groupsavings.fragments.MeetingDetailsFragment;
import org.groupsavings.fragments.MeetingLoansFragment;
import org.groupsavings.fragments.MeetingSavingsFragment;
import org.groupsavings.handlers.UserSessionManager;

import java.util.ArrayList;
import java.util.Locale;

public class ViewMeetingActivity extends Activity implements ActionBar.TabListener{

    String groupId;
    String meetingId;
    Group group;
    DatabaseHandler dbHandler;
    ArrayList<Member> groupMembers;
    // Main object to be populated and reused in fragments
    GroupMeeting groupMeeting;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    int TAB_POSITION = 0;
    MeetingSavingsFragment savingsFragment;
    MeetingLoansFragment loansFragment;
    MeetingDetailsFragment meetingDetailsFragment;

    //  session management declarations start
    UserSessionManager session;
    private Handler handler = new Handler();
    //  session management declarations end


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try{
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_add_meeting);

            //user session management starts
            session = new UserSessionManager(getApplicationContext());

            if(!session.isUserLoggedIn()) {
                //redirect to login activity
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
            }, 1800000);// session timeout of 30 minutes
            //user session management ends

            groupId = getIntent().getStringExtra(Intents.INTENT_EXTRA_GROUPID);
            meetingId = getIntent().getStringExtra(Intents.INTENT_EXTRA_MEETINGID);

            dbHandler = new DatabaseHandler(getApplicationContext());
            group = dbHandler.getGroup(groupId);
            groupMeeting = dbHandler.getGroupMeeting(group,meetingId,null);

            savingsFragment = new MeetingSavingsFragment(groupMeeting.SavingTransactions, true);
            loansFragment = new MeetingLoansFragment(groupMeeting.LoanTransactions, groupMeeting.LoansCreated, true);

            // TODO meeting details part
            // groupMeeting.OtherDetails =
            meetingDetailsFragment = new MeetingDetailsFragment();

            final ActionBar actionBar = getActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            // When swiping between different sections, select the corresponding
            // tab. We can also use ActionBar.Tab#select() to do this if we have
            // a reference to the Tab.
            mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    actionBar.setSelectedNavigationItem(position);
                    invalidateOptionsMenu();
                }
            });

            // For each of the sections in the app, add a tab to the action bar.
            for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
                // Create a tab with text corresponding to the page title defined by
                // the adapter. Also specify this Activity object, which implements
                // the TabListener interface, as the callback (listener) for when
                // this tab is selected.
                actionBar.addTab(
                        actionBar.newTab()
                                .setText(mSectionsPagerAdapter.getPageTitle(i))
                                .setTabListener(this)
                );
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        TAB_POSITION=tab.getPosition();
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //user session management starts
        session = new UserSessionManager(getApplicationContext());

        if(!session.isUserLoggedIn()) {
            //redirect to login activity
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        }, 1800000);// session timeout of 30 minutes
        //user session management ends
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {  }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {  }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).]\
            switch (position) {
                case 0:
                    return savingsFragment;
                case 1:
                    return loansFragment;
                case 2:
                    return meetingDetailsFragment;
                default:
                    return savingsFragment;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_fragment_meeting_savings).toUpperCase(l);
                case 1:
                    return getString(R.string.title_fragment_meeting_loans).toUpperCase(l);
                case 2:
                    return getString(R.string.title_fragment_meeting_details).toUpperCase(l);
            }
            return null;
        }
    }

}
