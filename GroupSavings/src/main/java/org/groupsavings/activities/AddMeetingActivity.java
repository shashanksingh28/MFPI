package org.groupsavings.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.groupsavings.NewLoanDialog;
import org.groupsavings.R;
import org.groupsavings.constants.Intents;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.domain.Group;
import org.groupsavings.domain.GroupMeeting;
import org.groupsavings.domain.LoanAccount;
import org.groupsavings.domain.MeetingDetails;
import org.groupsavings.domain.MeetingLoanAccTransaction;
import org.groupsavings.domain.MeetingSavingsAccTransaction;
import org.groupsavings.domain.Member;
import org.groupsavings.domain.SavingsAccount;
import org.groupsavings.fragments.MeetingDetailsFragment;
import org.groupsavings.fragments.MeetingLoansFragment;
import org.groupsavings.fragments.MeetingSavingsFragment;
import org.groupsavings.handlers.UserSessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AddMeetingActivity extends Activity implements ActionBar.TabListener, View.OnClickListener{

    String groupId;
    Group group;
    DatabaseHandler dbHandler;
    ArrayList<Member> groupMembers;
    // Main object to be populated and reused in fragments
    public GroupMeeting groupMeeting;
    NewLoanDialog newLoanDialog;

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

            setContentView(R.layout.activity_add_meeting);
            groupId = getIntent().getStringExtra(Intents.INTENT_EXTRA_GROUPID);
            dbHandler = new DatabaseHandler(getApplicationContext());
            group = dbHandler.getGroup(groupId);
            groupMembers = dbHandler.getActiveGroupMembers(groupId);

            groupMeeting = new GroupMeeting();
            groupMeeting.GroupId = groupId;
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy, hh:mm aaa");
            groupMeeting.Date = sdf.format(new Date());

            groupMeeting.SavingTransactions = populateSavingTransactions();
            savingsFragment =new MeetingSavingsFragment(groupMeeting.SavingTransactions, false);

            groupMeeting.LoanTransactions = populateLoanTransactions();
            loansFragment = new MeetingLoansFragment(groupMeeting.LoanTransactions,groupMeeting.LoansCreated, false);

            groupMeeting.OtherDetails = populateMeetingDetails();
            meetingDetailsFragment = new MeetingDetailsFragment(groupMeeting.OtherDetails,false);

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
    private ArrayList<MeetingLoanAccTransaction> populateLoanTransactions()
    {
        ArrayList<MeetingLoanAccTransaction> transactions = new ArrayList<MeetingLoanAccTransaction>();
        for (Member member : groupMembers)
        {
            LoanAccount nonEmergencyLoan = dbHandler.getMemberNonEmergencyActiveLoan(member.Id,null);
            LoanAccount emergencyLoan = dbHandler.getMemberEmergencyActiveLoan(member.Id, null);

            if(nonEmergencyLoan != null )
            {
                MeetingLoanAccTransaction transaction = new MeetingLoanAccTransaction(group, member, nonEmergencyLoan);
                transaction.LoanAccount = nonEmergencyLoan;
                transaction.LoanAccTransaction.setOutstandingDue(transaction.LoanAccount.Outstanding);
                transactions.add(transaction);
            }

            if(emergencyLoan != null)
            {
                MeetingLoanAccTransaction transaction = new MeetingLoanAccTransaction(group, member, emergencyLoan);
                transaction.LoanAccount = emergencyLoan;
                transaction.LoanAccTransaction.setOutstandingDue(transaction.LoanAccount.Outstanding);
                transactions.add(transaction);
            }

        }
        return transactions;
    }

    private ArrayList<MeetingDetails> populateMeetingDetails()
    {
        ArrayList<MeetingDetails> details = new ArrayList<MeetingDetails>();

        for(Member member : groupMembers)
        {
            MeetingDetails detail = new MeetingDetails();
            detail.member = member;
            detail.MemberId = member.Id;
            detail.Fine = 0;
            detail.Attended = true;
            detail.FineReason = "";
            details.add(detail);
        }

        return  details;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_meeting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.button_save_meeting_details:
                UpdateAccounts(groupMeeting);
                UserSessionManager session = new UserSessionManager(getApplicationContext());
                HashMap<String, String> user = session.getUserDetails();
                groupMeeting.FieldOfficerId = user.get(UserSessionManager.KEY_USERNAME);

                dbHandler.addUpdateGroupMeeting(groupMeeting);
                Toast.makeText(this, "Meeting Details Saved", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.bt_add_new_loan:

                final String [] loanTypes = {"Normal","Emergency"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select type of Loan")
                        .setItems(loanTypes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                if(which > 0)
                                    CallNewLoanActivity(true);
                                else
                                    CallNewLoanActivity(false);
                            }
                        });

                builder.show();

        }
        return super.onOptionsItemSelected(item);
    }

    public void CallNewLoanActivity(boolean isEmergency) {

        ArrayList<LoanAccount> normalLoans = new ArrayList<LoanAccount>();
        ArrayList<LoanAccount> emergencyLoans = new ArrayList<LoanAccount>();

        for(LoanAccount account : groupMeeting.LoansCreated)
        {
            if(account.IsEmergency)
                emergencyLoans.add(account);
            else
                normalLoans.add(account);
        }

        ArrayList<String> alreadyLoaned = new ArrayList<String>();

        if(isEmergency)
        {
            for(LoanAccount loan : emergencyLoans)
            {
                alreadyLoaned.add(loan.MemberId);
            }
        }
        else
        {
            for(LoanAccount loan : normalLoans)
            {
                alreadyLoaned.add(loan.MemberId);
            }
        }

        ArrayList<Member> potentialLoanMembers = (ArrayList<Member>) groupMembers.clone();

        for(Member member : groupMembers)
        {
            for(String i : alreadyLoaned)
            {
                if(i.equals(member.Id)) potentialLoanMembers.remove(member);
            }

            for(MeetingLoanAccTransaction loanTransaction : groupMeeting.LoanTransactions)
            {
                if((loanTransaction.Member.Id).equals(member.Id) && loanTransaction.LoanAccount.IsEmergency == isEmergency)
                    potentialLoanMembers.remove(member);
            }
        }

        newLoanDialog = new NewLoanDialog(this,group,potentialLoanMembers,isEmergency);
        final LayoutInflater factory = getLayoutInflater();
        final View newLoanDialogView = factory.inflate(R.layout.activity_new_loan, null);
        if(isEmergency)
            newLoanDialog.setTitle("Create new Emergency Loan");
        else
            newLoanDialog.setTitle("Create new Loan");

        newLoanDialog.Initialize(newLoanDialogView);

        Button calcEmi = (Button) newLoanDialogView.findViewById(R.id.bt_calulate_emi);
        calcEmi.setOnClickListener(this);

        Button createLoan = (Button) newLoanDialogView.findViewById(R.id.bt_create_loan);
        createLoan.setOnClickListener(this);
        newLoanDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_calulate_emi:
                try{
                    newLoanDialog.newLoanAccount = newLoanDialog.getLoanDetailsFromView();
                }
                catch (Exception ex)
                {
                    Toast.makeText(this, "Please check if all numbers are valid", Toast.LENGTH_SHORT).show();
                    break;
                }

                newLoanDialog.populateDetails();
                break;

            case R.id.bt_create_loan:
                try
                {
                    if(newLoanDialog.members_spinner.getCount() == 0)
                    {
                        Toast.makeText(this,"No member selected", Toast.LENGTH_SHORT);
                    }

                    if(newLoanDialog.newLoanAccount == null)
                        newLoanDialog.newLoanAccount = newLoanDialog.getLoanDetailsFromView();

                    newLoanDialog.dismiss();

                    final LoanAccount newLoan = newLoanDialog.newLoanAccount;

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which)
                            {
                                case DialogInterface.BUTTON_POSITIVE:
                                    newLoan.Active = true;
                                    for (Member member : groupMembers)
                                    {
                                        if(newLoan.MemberId.equals(member.Id)){
                                            newLoan.Member = member;
                                            break;
                                        }
                                    }
                                    HashMap<String, String> user = session.getUserDetails();
                                    newLoan.CreatedBy = user.get(UserSessionManager.KEY_USERNAME);

                                    loansFragment.newloansAdapter.add(newLoan);
                                    //groupMeeting.LoansCreated.add(newLoan);

                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setMessage("Are you sure? EMI for "+newLoan.PeriodInMonths + " months will be "+newLoan.getEMI()).setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                    break;
                }
                catch (Exception ex)
                {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG);
                }

        }

    }

    // Function that does math checks
    private void UpdateAccounts(GroupMeeting groupMeeting)
    {
        if(groupMeeting == null) return;

        for (MeetingSavingsAccTransaction savingTrans : groupMeeting.SavingTransactions)
        {
            savingTrans.SavingsAccount.CompulsorySavings+=savingTrans.CompulsorySavingTransaction.Amount;
            savingTrans.CompulsorySavingTransaction.CurrentBalance = savingTrans.SavingsAccount.getTotalSavings();
            savingTrans.SavingsAccount.OptionalSavings+=savingTrans.OptionalSavingTransaction.Amount;
            savingTrans.OptionalSavingTransaction.CurrentBalance = savingTrans.SavingsAccount.getTotalSavings();
            savingTrans.SavingsAccount.OptionalSavings-=savingTrans.WithdrawOptionalSavingTransaction.Amount;
            savingTrans.WithdrawOptionalSavingTransaction.CurrentBalance = savingTrans.SavingsAccount.getTotalSavings();
        }

    }

    //-------------------------------------------------------------------------------------//
    //--------------- Do not change code below this. Related to ViewPager -----------------//

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
