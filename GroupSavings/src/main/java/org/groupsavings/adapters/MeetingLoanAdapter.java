package org.groupsavings.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.groupsavings.R;
import org.groupsavings.domain.LoanAccount;
import org.groupsavings.domain.MeetingSavingsAccTransaction;

import java.util.ArrayList;

/**
 * Created by shashank on 1/7/14.
 */
public class MeetingLoanAdapter  extends ArrayAdapter<LoanAccount> {

    Context context;
    ArrayList<LoanAccount> LoanAccounts;
    boolean readonly;

    public MeetingLoanAdapter(Context context, int textViewResourceId, ArrayList<LoanAccount> objects, boolean readonly) {
        super(context, textViewResourceId, objects);
        this.context = context;
        LoanAccounts = objects;
        this.readonly = readonly;
    }

    @Override
    public int getCount()
    {
        return LoanAccounts.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public LoanAccount getItem(int position) {
        return LoanAccounts.get(position);
    }

    public View getView(int i, View convert_view, ViewGroup viewGroup) {

        final LoanAccount la = LoanAccounts.get(i);

        try {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convert_view = inflater.inflate(R.layout.meeting_loan_row, viewGroup, false);

            TextView tv_memberName = (TextView) convert_view.findViewById(R.id.tv_meeting_loan_name);
            tv_memberName.setText(la.Member.toString());

            TextView tv_reason = (TextView) convert_view.findViewById(R.id.tv_meeting_loan_reason);
            tv_reason.setText(la.Reason);

            TextView tv_principal = (TextView) convert_view.findViewById(R.id.tv_meeting_loan_principal);
            tv_principal.setText(String.valueOf(la.Principal));

            TextView tv_interest = (TextView) convert_view.findViewById(R.id.tv_meeting_loan_interest);
            tv_interest.setText(String.valueOf(la.InterestRate));

            TextView tv_period = (TextView) convert_view.findViewById(R.id.tv_meeting_loan_months);
            tv_period.setText(String.valueOf(la.PeriodInMonths));

            TextView tv_emi = (TextView) convert_view.findViewById(R.id.tv_meeting_loan_emi);
            tv_emi.setText(String.valueOf(la.EMI));

            CheckBox ck_loan_isEmergency = (CheckBox) convert_view.findViewById(R.id.ck_meeting_loan_isEmergency);
            ck_loan_isEmergency.setChecked(la.IsEmergency);
            ck_loan_isEmergency.setEnabled(false);

        } catch (Exception ex) {
            Log.d("ERROR", ex.getMessage());
        }

        return convert_view;
    }

}
