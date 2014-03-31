package org.groupsavings.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.groupsavings.MeetingTransactionsAdapter;
import org.groupsavings.R;
import org.groupsavings.domain.Group;
import org.groupsavings.domain.MeetingTransaction;
import org.groupsavings.domain.Member;
import org.groupsavings.handlers.DatabaseHandler;

import java.util.ArrayList;

public class AddMeetingActivity extends Activity {

    int groupId;
    Group group;
    ArrayList<Member> groupMembers;
    ArrayList<MeetingTransaction> transactions;
    MeetingTransactionsAdapter transactionsAdapter;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        groupId = getIntent().getIntExtra(GroupLandingActivity.INTENT_EXTRA_GROUP, 0);
        dbHandler = new DatabaseHandler(getApplicationContext());
        group = dbHandler.getGroup(groupId);
        groupMembers = dbHandler.getAllMembers(groupId);
        transactions = populateMeetingTransactions(group, groupMembers);

        ListView meetingTransactions = (ListView) findViewById(R.id.listview_meeting_transactions);
        transactionsAdapter = new MeetingTransactionsAdapter(this, transactions);
        meetingTransactions.setAdapter(transactionsAdapter);

    }

    private ArrayList<MeetingTransaction> populateMeetingTransactions(Group group, ArrayList<Member> members) {
        ArrayList<MeetingTransaction> transactions = new ArrayList<MeetingTransaction>();

        for (int i = 0; i < members.size(); i++) {
            MeetingTransaction transaction = new MeetingTransaction(members.get(i));
            transaction.groupCompulsorySavings = group.RecurringSavings;
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

}
