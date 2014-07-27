package org.groupsavings.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.groupsavings.R;
import org.groupsavings.StringHelper;
import org.groupsavings.domain.MeetingLoanAccTransaction;

import java.util.ArrayList;

/**
 * Created by shashank on 6/7/14.
 */
public class MeetingLoanAccTransactionAdapter extends ArrayAdapter<MeetingLoanAccTransaction>
{
    Context context;
    ArrayList<MeetingLoanAccTransaction> LoanTransactions;
    boolean readonly;

    public MeetingLoanAccTransactionAdapter(Context context, int textViewResourceId, ArrayList<MeetingLoanAccTransaction> objects, boolean readonly) {
        super(context, textViewResourceId, objects);
        this.context = context;
        LoanTransactions = objects;
        this.readonly = readonly;
    }

    @Override
    public int getCount()
    {
        return LoanTransactions.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MeetingLoanAccTransaction getItem(int position) {
        return LoanTransactions.get(position);
    }

    @Override
    public View getView(int i, View convert_view, ViewGroup viewGroup) {

        final MeetingLoanAccTransaction transaction = LoanTransactions.get(i);

        try
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convert_view = inflater.inflate(R.layout.meeting_loantransaction_row, viewGroup, false);

            TextView tv_memberName = (TextView) convert_view.findViewById(R.id.tv_loanTrans_membername);
            tv_memberName.setText(transaction.Member.toString());

            TextView tv_loanReason = (TextView) convert_view.findViewById(R.id.tv_loanTrans_reason);
            if(!StringHelper.IsNullOrEmpty(transaction.LoanAccount.Reason))
            {
                tv_loanReason.setText(transaction.LoanAccount.Reason);
            }

            TextView tv_loanEMI = (TextView) convert_view.findViewById(R.id.tv_loanTrans_emi);
            tv_loanEMI.setText(String.valueOf(transaction.LoanAccount.EMI));


            final EditText et_loanRepayment = (EditText) convert_view.findViewById(R.id.et_loanTrans_repayment);
            et_loanRepayment.setText(String.valueOf(transaction.LoanAccTransaction.Repayment));
            if(readonly)
            {
                et_loanRepayment.setFocusable(false);
            }
            else
            {
                et_loanRepayment.setOnFocusChangeListener(new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if(!hasFocus)
                        {
                            // A kind of workaround since this is being called more than once weirdly
                            float prev = transaction.LoanAccTransaction.Repayment;
                            float curr = Float.valueOf(et_loanRepayment.getText().toString());
                            if(prev != curr)
                            {
                                transaction.LoanAccTransaction.Repayment = curr;
                                notifyDataSetChanged();
                            }
                        }
                    }
                });
            }

            TextView tv_loanOutStanding = (TextView) convert_view.findViewById(R.id.tv_loanTrans_outstanding);
            if(!readonly)
                tv_loanOutStanding.setText(String.valueOf(transaction.LoanAccTransaction.getUpdatedOutstanding()));
            else
                tv_loanOutStanding.setText(String.valueOf(transaction.LoanAccTransaction.getAsIsOutstanding()));

            CheckBox ck_isEmergency = (CheckBox) convert_view.findViewById(R.id.ck_loanTrans_isEmergency);
            ck_isEmergency.setChecked(transaction.LoanAccount.IsEmergency);
            ck_isEmergency.setEnabled(false);

        } catch (Exception ex) {
            Log.d("ERROR", ex.getMessage());
        }

        return convert_view;
    }
}
