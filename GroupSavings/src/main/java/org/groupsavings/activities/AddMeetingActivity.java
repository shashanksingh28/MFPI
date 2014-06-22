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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.constants.Intents;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.domain.Group;
import org.groupsavings.domain.GroupMeeting;
import org.groupsavings.domain.LoanAccount;
import org.groupsavings.domain.MeetingLoanAccTransactions;
import org.groupsavings.domain.MeetingSavingsAccTransaction;
import org.groupsavings.domain.Member;
import org.groupsavings.domain.SavingsAccount;
import org.groupsavings.fragments.MeetingDetailsFragment;
import org.groupsavings.fragments.MeetingLoansFragment;
import org.groupsavings.fragments.MeetingSavingsFragment;
import org.groupsavings.handlers.ExceptionHandler;
import org.groupsavings.handlers.UserSessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class AddMeetingActivity extends Activity implements ActionBar.TabListener{

    String groupId;
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

    public final int REQUEST_GET_NEW_LOANACCOUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        try{
            super.onCreate(savedInstanceState);

            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

            setContentView(R.layout.activity_add_meeting);

            //user session management starts
            session = new UserSessionManager(getApplicationContext());

            if(!session.isUserLoggedIn()) {
                //redirect to login activity
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }

            HashMap<String, String> user = session.getUserDetails();
            String name = user.get(UserSessionManager.KEY_NAME);
            Toast.makeText(getApplicationContext(), "User Login Status: " + session.isUserLoggedIn() + " Name: " + name, Toast.LENGTH_LONG).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
            }, 1800000);// session timeout of 30 minutes
            //user session management ends

            groupId = getIntent().getStringExtra(Intents.INTENT_EXTRA_GROUPID);
            dbHandler = new DatabaseHandler(getApplicationContext());
            group = dbHandler.getGroup(groupId);
            groupMembers = dbHandler.getActiveGroupMembers(groupId);

            groupMeeting = new GroupMeeting();
            groupMeeting.SavingTransactions = populateSavingTransactions();
            savingsFragment = new MeetingSavingsFragment(groupMeeting.SavingTransactions);

            groupMeeting.LoanTransactions = populateLoanTransactions();
            loansFragment = new MeetingLoansFragment(groupMeeting.LoanTransactions);

            // TODO meeting details part
            // groupMeeting.OtherDetails =
            // meetingDetailsFragment = new MeetingDetailsFragment();

            /*groupMembers = dbHandler.getAllMembers(groupId);
            transactions = populateMeetingTransactions(group, groupMembers);

            ListView lv_transactions = (ListView) findViewById(R.id.listview_meeting_transactions);
            transactionsAdapter = new MeetingTransactionsAdapter(this, android.R.layout.simple_list_item_1, transactions,false);
            lv_transactions.setAdapter(transactionsAdapter);

            loanAccounts = new ArrayList<LoanAccount>();
            lv_loanAccounts = (ListView) findViewById(R.id.lv_meeting_loans);
            loansAdapter = new MeetingLoanAdapter(this, android.R.layout.simple_list_item_1, loanAccounts);
            lv_loanAccounts.setAdapter(loansAdapter);*/

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

    // Pre-populate the saving transactions needed for the meeting
    private ArrayList<MeetingSavingsAccTransaction> populateSavingTransactions()
    {
        ArrayList<MeetingSavingsAccTransaction> transactions = new ArrayList<MeetingSavingsAccTransaction>();

        for (Member member : groupMembers)
        {
            SavingsAccount memberSavingsAccount = dbHandler.getMemberSavingAccount(member.Id, null);
            MeetingSavingsAccTransaction savingTransaction = new MeetingSavingsAccTransaction(group, member, memberSavingsAccount);

            transactions.add(savingTransaction);
        }
        return transactions;
    }

    // Pre-populate the loan transactions needed for the meeting
    private ArrayList<MeetingLoanAccTransactions> populateLoanTransactions()
    {
        ArrayList<MeetingLoanAccTransactions> transactions = new ArrayList<MeetingLoanAccTransactions>();
        for (Member member : groupMembers)
        {
            LoanAccount nonEmergencyLoan = dbHandler.getMemberNonEmergencyActiveLoan(member.Id,null);
            LoanAccount emergencyLoan = dbHandler.getMemberEmergencyActiveLoan(member.Id, null);

            if(nonEmergencyLoan != null || emergencyLoan != null)
            {
                MeetingLoanAccTransactions transaction = new MeetingLoanAccTransactions(group, member, nonEmergencyLoan, emergencyLoan);
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_meeting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*
        switch (item.getItemId()) {
            case R.id.button_save_meeting_details:
                dbHandler.saveMeetingDetails(groupId, transactions, loanAccounts);
                Toast.makeText(this, "Meeting Details Saved", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.bt_add_new_loan:
                Intent intent = new Intent(this,NewLoanActivity.class);
                int [] alreadyLoaned = new int[loanAccounts.size()];
                for(int i = 0; i<loanAccounts.size(); i++)
                {
                    alreadyLoaned[i]=loanAccounts.get(i).memberId;
                }
                intent.putExtra(GroupLandingActivity.INTENT_EXTRA_GROUP, groupId);
                intent.putExtra(GroupLandingActivity.INTENT_EXTRA_ALREADY_LOANED_MEMBER_IDS,alreadyLoaned);
                intent.putExtra(GroupLandingActivity.INTENT_EXTRA_ALREADY_LOANED_COUNT,loanAccounts.size());
                startActivityForResult(intent, REQUEST_GET_NEW_LOANACCOUNT);
                break;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        /*
        if (requestCode == REQUEST_GET_NEW_LOANACCOUNT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                try {
                    // Get the new loan object fetched via JSON
                    JSONObject jo = new JSONObject(data.getStringExtra(NewLoanActivity.INTENT_EXTRA_RETURN_LOAN_ACCOUNT_JSON));
                    LoanAccount la = SyncHelper.getLoanAccFromJson(jo);
                    // Put Member object in place of member id
                    for(Member member : groupMembers)
                    {
                        if(member.UID == la.memberId)
                        {
                            la.GroupMember = member;
                            la.memberId = member.UID;
                            break;
                        }
                    }
                    loanAccounts.add(la);
                    RefreshView();
                } catch (JSONException e) {
                    Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }*/
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        TAB_POSITION=tab.getPosition();
        mViewPager.setCurrentItem(tab.getPosition());
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
