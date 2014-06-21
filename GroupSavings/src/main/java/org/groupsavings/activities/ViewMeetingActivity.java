package org.groupsavings.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import org.groupsavings.MeetingLoanAdapter;
import org.groupsavings.MeetingTransactionsAdapter;
import org.groupsavings.R;
import org.groupsavings.domain.Group;
import org.groupsavings.domain.LoanAccount;
import org.groupsavings.domain.MeetingTransaction;
import org.groupsavings.domain.Member;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.handlers.ExceptionHandler;
import org.groupsavings.handlers.UserSessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewMeetingActivity extends Activity {

    int grpMeetingId;
    Group group;
    DatabaseHandler dbHandler;
    ArrayList<Member> groupMembers;

    //  session management declarations start
    UserSessionManager session;
    private Handler handler = new Handler();
    //  session management declarations end


    //Meeting Transactions and its adapter
    ArrayList<MeetingTransaction> transactions;
    MeetingTransactionsAdapter transactionsAdapter;

    //Loan Accounts and its adapter
    ArrayList<LoanAccount> loanAccounts;
    MeetingLoanAdapter loansAdapter;
    ListView lv_loanAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);

            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

            setContentView(R.layout.activity_view_meeting);
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

            grpMeetingId = getIntent().getIntExtra(GroupLandingActivity.INTENT_EXTRA_MEETINGID, 0);
            dbHandler = new DatabaseHandler(getApplicationContext());
            //group = dbHandler.getGroup(groupId);
            //groupMembers = dbHandler.getAllMembers(groupId);
            transactions = dbHandler.getMeetingTransactions(grpMeetingId);

            ListView lv_transactions = (ListView) findViewById(R.id.listview_meeting_transactions);
            transactionsAdapter = new MeetingTransactionsAdapter(this, android.R.layout.simple_list_item_1, transactions,true);
            lv_transactions.setAdapter(transactionsAdapter);

            loanAccounts = dbHandler.getMeetingLoans(grpMeetingId);
            lv_loanAccounts = (ListView) findViewById(R.id.lv_meeting_loans);
            loansAdapter = new MeetingLoanAdapter(this, android.R.layout.simple_list_item_1, loanAccounts);
            lv_loanAccounts.setAdapter(loansAdapter);

            /*Button bt_save_meeting = (Button) findViewById(R.id.button_save_meeting_details);
            bt_save_meeting.setOnClickListener(this);

            Button bt_add_loan = (Button) findViewById(R.id.bt_add_new_loan);
            bt_add_loan.setOnClickListener(this);*/
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_meeting, menu);
        return true;
    }

}
