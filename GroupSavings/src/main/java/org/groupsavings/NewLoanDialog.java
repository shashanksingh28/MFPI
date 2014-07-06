package org.groupsavings;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.groupsavings.constants.Constants;
import org.groupsavings.constants.Tables;
import org.groupsavings.domain.Group;
import org.groupsavings.domain.LoanAccount;
import org.groupsavings.domain.Member;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by shashank on 6/7/14.
 */
public class NewLoanDialog extends Dialog {

    Context context;
    public LoanAccount newLoanAccount;
    Spinner members_spinner;

    ArrayList<Member> groupMembers;
    ArrayAdapter<Member> grpMembersAdapter;
    Group group;
    boolean isEmergency;

    public NewLoanDialog(Context context, Group group, ArrayList<Member> members, boolean isEmergency) {
        super(context);
        this.context = context;
        groupMembers = members;
        this.group = group;
        this.isEmergency = isEmergency;
    }

    public void Initialize(View view)
    {
        setContentView(view);

        members_spinner = (Spinner) view.findViewById(R.id.sp_loan_members);
        grpMembersAdapter = new ArrayAdapter<Member>(context,android.R.layout.simple_spinner_dropdown_item,groupMembers);
        members_spinner.setAdapter(grpMembersAdapter);

    }

    public LoanAccount getLoanDetailsFromView()
    {
        LoanAccount la = new LoanAccount();
        la.Id = Tables.getTimestampUniqueId();
        la.MemberId = ((Member) members_spinner.getSelectedItem()).Id;
        la.GroupId = group.Id;

        EditText et_amt = (EditText)findViewById(R.id.et_loan_amount);
        la.Principal = Integer.valueOf(et_amt.getText().toString());

        EditText et_int = (EditText)findViewById(R.id.et_loan_interetest_rate);
        la.InterestRate = Float.valueOf(et_int.getText().toString());

        EditText et_time = (EditText)findViewById(R.id.et_loan_months);
        la.PeriodInMonths = Integer.valueOf(et_time.getText().toString());

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, la.PeriodInMonths);

        la.StartDate = sdf.format(new Date());
        la.EndDate = sdf.format(endDate.get(Calendar.DATE));

        la.EMI = la.getEMI();
        la.Outstanding = la.getInitialOutstanding();

        EditText et_reason = (EditText) findViewById(R.id.et_loan_reason);
        la.Reason = et_reason.getText().toString();

        la.IsEmergency = isEmergency;

        return la;
    }

    public void populateDetails()
    {
        TextView tv_calcEmi = (TextView) findViewById(R.id.tv_loan_emi);
        tv_calcEmi.setText(String.valueOf(newLoanAccount.getEMI()));

        TextView tv_calcOutstanding = (TextView) findViewById(R.id.tv_loan_totalOutstanding);
        tv_calcOutstanding.setText(String.valueOf(newLoanAccount.Outstanding));
    }
}
