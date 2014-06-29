package org.groupsavings.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.domain.MeetingSavingsAccTransaction;

import java.util.ArrayList;

/**
 * Created by shashank on 22/6/14.
 * Adapter that will map each MeetingSavingsAccTransaction to a row in the UI
 */
public class MeetingSavingsAccTransactionAdapter extends ArrayAdapter<MeetingSavingsAccTransaction>
{
    Context context;
    ArrayList<MeetingSavingsAccTransaction> SavingTransactions;
    boolean readonly;

    public MeetingSavingsAccTransactionAdapter(Context context, int textViewResourceId, ArrayList<MeetingSavingsAccTransaction> objects, boolean readonly) {
        super(context, textViewResourceId, objects);
        this.context = context;
        SavingTransactions = objects;
        this.readonly = readonly;
    }

    @Override
    public int getCount()
    {
        return SavingTransactions.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MeetingSavingsAccTransaction getItem(int position) {
        return SavingTransactions.get(position);
    }

    @Override
    public View getView(int i, View convert_view, ViewGroup viewGroup) {

        final MeetingSavingsAccTransaction transaction = SavingTransactions.get(i);

        try {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convert_view = inflater.inflate(R.layout.meeting_savingtransaction_row, viewGroup, false);

            TextView tv_memberName = (TextView) convert_view.findViewById(R.id.tv_savingTrans_membername);
            tv_memberName.setText(transaction.Member.toString());

            final EditText et_grpCompSavings = (EditText) convert_view.findViewById(R.id.et_savingTrans_compulsorysavings);
            et_grpCompSavings.setText(String.valueOf(transaction.CompulsorySavingTransaction.Amount));
            if(readonly)
            {
                et_grpCompSavings.setFocusable(false);
            }
            else
            {
                et_grpCompSavings.setOnFocusChangeListener(new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if(!hasFocus)
                        {
                            // A kind of workaround since this is being called more than once weirdly
                            float prev = transaction.CompulsorySavingTransaction.Amount;
                            float curr = Float.valueOf(et_grpCompSavings.getText().toString());
                            if(prev != curr)
                            {
                                transaction.CompulsorySavingTransaction.Amount = curr;
                                notifyDataSetChanged();
                            }
                        }
                    }
                });
            }

            TextView tv_totalSavings = (TextView) convert_view.findViewById(R.id.tv_savingTrans_totalsaving);
            tv_totalSavings.setText(String.valueOf(transaction.getTotalSavings()));

            final EditText et_optionalSavings = (EditText) convert_view.findViewById(R.id.et_savingTrans_optionalsavings);
            et_optionalSavings.setText(String.valueOf(transaction.OptionalSavingTransaction.Amount));
            if(readonly)
            {
                et_optionalSavings.setFocusable(false);
            }
            else
            {
                et_optionalSavings.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus)
                        {
                            // A kind of workaround since this is being called more than once weirdly
                            float prev = transaction.OptionalSavingTransaction.Amount;
                            float curr =  Float.valueOf(et_optionalSavings.getText().toString());
                            if(prev != curr) {
                                transaction.OptionalSavingTransaction.Amount = curr;
                                notifyDataSetChanged();
                            }
                        }
                    }});
            }

            final EditText et_optionalWithdrawal = (EditText) convert_view.findViewById(R.id.et_savingTrans_optionalWithdrawal);
            et_optionalWithdrawal.setText(String.valueOf(transaction.WithdrawOptionalSavingTransaction.Amount));
            if(readonly)
            {
                et_optionalWithdrawal.setFocusable(false);
            }
            else
            {
                et_optionalWithdrawal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                        {
                            // A kind of workaround since this is being called more than once weirdly
                            float prev = transaction.WithdrawOptionalSavingTransaction.Amount;
                            float curr =  Float.valueOf(et_optionalWithdrawal.getText().toString());
                            if(prev != curr) {
                                if(curr >= transaction.SavingsAccount.OptionalSavings)
                                {
                                    Toast.makeText(context,"Not enough optional savings",Toast.LENGTH_LONG).show();
                                    curr = transaction.SavingsAccount.OptionalSavings;
                                }
                                transaction.WithdrawOptionalSavingTransaction.Amount = curr;
                                notifyDataSetChanged();
                            }
                        }
                    }});
            }

        } catch (Exception ex) {
            Log.d("ERROR", ex.getMessage());
        }

        return convert_view;
    }
}
