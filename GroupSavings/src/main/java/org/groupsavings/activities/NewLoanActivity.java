package org.groupsavings.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.groupsavings.R;
import org.groupsavings.domain.Member;
import org.groupsavings.handlers.DatabaseHandler;

import java.util.ArrayList;

public class NewLoanActivity extends Activity {

    int groupUID;
    DatabaseHandler db_handler;
    ArrayList<Member> groupMembers;
    ArrayAdapter<Member> grpMembersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupUID = getIntent().getIntExtra(GroupLandingActivity.INTENT_EXTRA_GROUP, 0);
        db_handler = new DatabaseHandler(getApplicationContext());
        groupMembers = db_handler.getAllMembers(groupUID);
        setContentView(R.layout.activity_new_loan);

        Spinner members_spinner = (Spinner) findViewById(R.id.sp_loan_members);
        grpMembersAdapter = new ArrayAdapter<Member>(this,android.R.layout.simple_spinner_dropdown_item,groupMembers);
        members_spinner.setAdapter(grpMembersAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_loan, menu);
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
