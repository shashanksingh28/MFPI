package org.groupsavings.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.groupsavings.R;
import org.groupsavings.SyncHelper;
import org.groupsavings.domain.LoanAccount;
import org.groupsavings.domain.Member;
import org.groupsavings.handlers.DatabaseHandler;
import org.groupsavings.handlers.ExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewLoanActivity extends Activity implements View.OnClickListener {

    int groupUID;
    DatabaseHandler db_handler;
    ArrayList<Member> groupMembers;
    ArrayAdapter<Member> grpMembersAdapter;
    Spinner members_spinner;
    LoanAccount la;

    public static final String INTENT_EXTRA_RETURN_LOAN_ACCOUNT_JSON="LoanAccount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        groupUID = getIntent().getIntExtra(GroupLandingActivity.INTENT_EXTRA_GROUP, 0);
        db_handler = new DatabaseHandler(getApplicationContext());
        groupMembers = db_handler.getAllMembersWithNoActiveLoan(groupUID);
        setContentView(R.layout.activity_new_loan);

        members_spinner = (Spinner) findViewById(R.id.sp_loan_members);
        grpMembersAdapter = new ArrayAdapter<Member>(this,android.R.layout.simple_spinner_dropdown_item,groupMembers);
        members_spinner.setAdapter(grpMembersAdapter);

        Button calcEmi = (Button) findViewById(R.id.bt_calulate_emi);
        calcEmi.setOnClickListener(this);

        Button createLoan = (Button) findViewById(R.id.bt_create_loan);
        createLoan.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.bt_calulate_emi:
                la = getLoanDetailsFromView();

                TextView tv_calcEmi = (TextView) findViewById(R.id.tv_loan_emi);
                tv_calcEmi.setText(String.valueOf(la.getEMI()));

                TextView tv_calcOutstanding = (TextView) findViewById(R.id.tv_loan_totalOutstanding);
                tv_calcOutstanding.setText(String.valueOf(la.OutStanding));
                break;

            case R.id.bt_create_loan:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                la.IsActive = true;
                                // Pass loan account as a JSONObject to the activity that called it
                                Intent returnLoanAccount = new Intent();
                                returnLoanAccount.putExtra(INTENT_EXTRA_RETURN_LOAN_ACCOUNT_JSON, SyncHelper.getJsonLoanAcc(la).toString());
                                setResult(RESULT_OK,returnLoanAccount);
                                //db_handler.addUpdateLoanAccount(la);
                                finish();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                la = getLoanDetailsFromView();
                builder.setMessage("Are you sure? EMI for "+la.PeriodInMonths + " months will be "+la.getEMI()).setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                break;
        }
    }

    public LoanAccount getLoanDetailsFromView()
    {
        LoanAccount la = new LoanAccount();
        la.Id = 0;
        la.memberId = ((Member) members_spinner.getSelectedItem()).UID;
        la.groupId = groupUID;

        EditText et_amt = (EditText)findViewById(R.id.et_loan_amount);
        la.Principal = Integer.valueOf(et_amt.getText().toString());

        EditText et_int = (EditText)findViewById(R.id.et_loan_interetest_rate);
        la.InterestPerAnnum = Float.valueOf(et_int.getText().toString());

        EditText et_time = (EditText)findViewById(R.id.et_loan_months);
        la.PeriodInMonths = Integer.valueOf(et_time.getText().toString());

        SimpleDateFormat sdf = new SimpleDateFormat(GroupLandingActivity.DATE_FORMAT);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, la.PeriodInMonths);

        la.StartDate = sdf.format(new Date());
        la.EndDate = sdf.format(endDate.get(Calendar.DATE));

        la.EMI = la.getEMI();
        la.OutStanding = la.EMI * la.PeriodInMonths;

        EditText et_reason = (EditText) findViewById(R.id.et_loan_reason);
        la.Reason = et_reason.getText().toString();

        return la;
    }

}
