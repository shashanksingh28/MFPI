package org.groupsavings.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.groupsavings.MeetingLoanAdapter;
import org.groupsavings.MeetingTransactionsAdapter;
import org.groupsavings.R;
import org.groupsavings.SyncHelper;
import org.groupsavings.domain.Group;
import org.groupsavings.domain.LoanAccount;
import org.groupsavings.domain.MeetingTransaction;
import org.groupsavings.domain.Member;
import org.groupsavings.handlers.DatabaseHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddMeetingActivity extends Activity implements View.OnClickListener {

    int groupId;
    Group group;
    DatabaseHandler dbHandler;
    ArrayList<Member> groupMembers;

    //Meeting Transactions and its adapter
    ArrayList<MeetingTransaction> transactions;
    MeetingTransactionsAdapter transactionsAdapter;

    //Loan Accounts and its adapter
    ArrayList<LoanAccount> loanAccounts;
    MeetingLoanAdapter loansAdapter;
    ListView lv_loanAccounts;

    public final int REQUEST_GET_NEW_LOANACCOUNT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        groupId = getIntent().getIntExtra(GroupLandingActivity.INTENT_EXTRA_GROUP, 0);
        dbHandler = new DatabaseHandler(getApplicationContext());
        group = dbHandler.getGroup(groupId);
        groupMembers = dbHandler.getAllMembers(groupId);
        transactions = populateMeetingTransactions(group, groupMembers);

        ListView lv_transactions = (ListView) findViewById(R.id.listview_meeting_transactions);
        transactionsAdapter = new MeetingTransactionsAdapter(this, android.R.layout.simple_list_item_1, transactions);
        lv_transactions.setAdapter(transactionsAdapter);

        loanAccounts = new ArrayList<LoanAccount>();
        lv_loanAccounts = (ListView) findViewById(R.id.lv_meeting_loans);
        loansAdapter = new MeetingLoanAdapter(this, android.R.layout.simple_list_item_1, loanAccounts);
        lv_loanAccounts.setAdapter(loansAdapter);

        Button bt_save_meeting = (Button) findViewById(R.id.button_save_meeting_details);
        bt_save_meeting.setOnClickListener(this);

        Button bt_add_loan = (Button) findViewById(R.id.bt_add_new_loan);
        bt_add_loan.setOnClickListener(this);

    }

    private ArrayList<MeetingTransaction> populateMeetingTransactions(Group group, ArrayList<Member> members) {
        ArrayList<MeetingTransaction> transactions = new ArrayList<MeetingTransaction>();

        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            MeetingTransaction transaction = new MeetingTransaction(groupId, member);
            transaction.SavingTransaction.groupCompulsorySavings = group.RecurringSavings;

            LoanAccount la = dbHandler.getMemberLoanAccount(member.UID);
            if (la != null)
            {
                transaction.LoanTransaction.GroupMemberLoanAccountId = la.Id;
                transaction.LoanTransaction.EMI = la.EMI;
                transaction.LoanTransaction.Repayment = la.EMI;
                transaction.LoanTransaction.setOutstandingDue(la.OutStanding);
            }
            transactions.add(transaction);
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save_meeting_details:
                dbHandler.saveMeetingDetails(groupId, transactions);
                dbHandler.addUpdateLoanAccounts(loanAccounts);
                Toast.makeText(this, "Meeting Details Saved", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.bt_add_new_loan:
                Intent intent = new Intent(this,NewLoanActivity.class);
                intent.putExtra(GroupLandingActivity.INTENT_EXTRA_GROUP, groupId);
                startActivityForResult(intent,REQUEST_GET_NEW_LOANACCOUNT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_GET_NEW_LOANACCOUNT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                try {
                    JSONObject jo = new JSONObject(data.getStringExtra(NewLoanActivity.INTENT_EXTRA_RETURN_LOAN_ACCOUNT_JSON));
                    LoanAccount la = SyncHelper.getLoanAccFromJson(jo);
                    // Put Member object in place of member id
                    for(Member member : groupMembers)
                    {
                        if(member.UID == la.memberId)
                        {
                            la.GroupMember = member;
                            break;
                        }
                    }
                    loanAccounts.add(la);
                    RefreshView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Do something with the contact here (bigger example below)
            }
        }
    }

    public void RefreshView()
    {
        loansAdapter = new MeetingLoanAdapter(this, android.R.layout.simple_list_item_1, loanAccounts);;
        lv_loanAccounts.setAdapter(loansAdapter);
    }
}
