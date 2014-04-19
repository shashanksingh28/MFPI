package org.groupsavings;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.groupsavings.domain.MeetingTransaction;

import java.util.ArrayList;

/**
 * Created by shashank on 31/3/14.
 */
public class MeetingTransactionsAdapter extends ArrayAdapter<MeetingTransaction> {

    Context context;
    ArrayList<MeetingTransaction> transactions;

    public MeetingTransactionsAdapter(Context context, int textViewResourceId, ArrayList<MeetingTransaction> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        transactions = objects;
    }

    @Override
    public View getView(int i, View convert_view, ViewGroup viewGroup) {

        final MeetingTransaction transaction = transactions.get(i);

        try {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convert_view = inflater.inflate(R.layout.meeting_transaction_row, viewGroup, false);

            TextView tv_memberName = (TextView) convert_view.findViewById(R.id.textview_transaction_name);
            tv_memberName.setText(transaction.GroupMember.toString());

            TextView tv_grpCompSavings = (TextView) convert_view.findViewById(R.id.textview_transaction_compulsory_savings);
            tv_grpCompSavings.setText(String.valueOf(transaction.SavingTransaction.groupCompulsorySavings));

            EditText tv_optionalSavings = (EditText) convert_view.findViewById(R.id.edittext_transaction_optional_savings);
            //tv_optionalSavings.setTag(transaction.savingTransaction.optionalSavings);
            tv_optionalSavings.setText(String.valueOf(transaction.SavingTransaction.optionalSavings));
            tv_optionalSavings.addTextChangedListener(new OptionalSavingsTextWatcher(transaction));

            TextView tv_totalSavings = (TextView) convert_view.findViewById(R.id.textview_transaction_total_saving);
            tv_totalSavings.setText(String.valueOf(transaction.SavingTransaction.getTotalSavings()));
            //tv_totalSavings.setTag(transaction.hashCode());

            if(transaction.LoanTransaction.EMI > 0)
            {
                TextView tv_emi = (TextView) convert_view.findViewById(R.id.tv_transaction_loan_emi);
                tv_emi.setText(String.valueOf(transaction.LoanTransaction.EMI));

                EditText tv_repayment = (EditText) convert_view.findViewById(R.id.et_transaction_loan_repayment);
                tv_repayment.setText(String.valueOf(transaction.LoanTransaction.Repayment));
                tv_repayment.addTextChangedListener(new EMIRepaymentTextWatcher(transaction));

                TextView tv_outstanding = (TextView) convert_view.findViewById(R.id.tv_transaction_loan_outstanding);
                tv_outstanding.setText(String.valueOf(transaction.LoanTransaction.getUpdatedOutstanding()));
            }
        } catch (Exception ex) {
            Log.d("ERROR", ex.getMessage());
        }

        return convert_view;
    }
}

class OptionalSavingsTextWatcher implements TextWatcher {
    MeetingTransaction transToUpdate;

    public OptionalSavingsTextWatcher(MeetingTransaction trans) {
        this.transToUpdate = trans;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        try {
            int value = Integer.parseInt(editable.toString());
            Log.d("TRANS", "TextChanged for " + transToUpdate.GroupMember.toString() + " called with value :" + value);
            transToUpdate.SavingTransaction.optionalSavings = value;
        } catch (Exception ex) {
            // This will be happen in case string is empty or not valid int
        }
    }
}

class EMIRepaymentTextWatcher implements TextWatcher {
    MeetingTransaction transToUpdate;

    public EMIRepaymentTextWatcher(MeetingTransaction trans) {
        this.transToUpdate = trans;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        try {
            int value = Integer.parseInt(editable.toString());
            Log.d("TRANS", "TextChanged for " + transToUpdate.GroupMember.toString() + " called with value :" + value);
            transToUpdate.LoanTransaction.Repayment = value;
        } catch (Exception ex) {
            // This will be happen in case string is empty or not valid int
        }
    }
}