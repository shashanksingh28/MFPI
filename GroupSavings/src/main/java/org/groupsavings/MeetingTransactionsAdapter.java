package org.groupsavings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.groupsavings.domain.MeetingTransaction;

import java.util.ArrayList;

/**
 * Created by shashank on 31/3/14.
 */
public class MeetingTransactionsAdapter extends BaseAdapter implements View.OnFocusChangeListener {

    Context context;
    ArrayList<MeetingTransaction> transactions;

    public MeetingTransactionsAdapter(Context context, ArrayList<MeetingTransaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int i) {
        return transactions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return transactions.get(i).member.UID;
    }

    @Override
    public View getView(int i, View convert_view, ViewGroup viewGroup) {

        final MeetingTransaction transaction = transactions.get(i);

        if (convert_view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convert_view = inflater.inflate(R.layout.meeting_transaction_row, null);
        }

        TextView memberName = (TextView) convert_view.findViewById(R.id.textview_transaction_name);
        if (memberName != null) {
            memberName.setText(transaction.member.toString());
        }

        TextView grpCompSavings = (TextView) convert_view.findViewById(R.id.textview_transaction_compulsory_savings);
        if (grpCompSavings != null) {
            grpCompSavings.setText(String.valueOf(transaction.groupCompulsorySavings));
        }

        final EditText optionalSavings = (EditText) convert_view.findViewById(R.id.textview_transaction_optional_savings);
        if (optionalSavings != null) {
            optionalSavings.setText(String.valueOf(transaction.optionalSavings));
        }

        TextView totalSavings = (TextView) convert_view.findViewById(R.id.textview_transaction_total_saving);
        totalSavings.setText(String.valueOf(transaction.groupCompulsorySavings + transaction.optionalSavings));

        //we need to update adapter once we finish with editing
        convert_view.setOnFocusChangeListener(this);

        return convert_view;
    }

    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            final int position = v.getId();
            final EditText optSavings = (EditText) v;
            MeetingTransaction transaction = transactions.get(position);
            transaction.optionalSavings = Integer.valueOf(optSavings.getText().toString());
            transaction.totalSavings = transaction.groupCompulsorySavings + transaction.optionalSavings;
        }
    }
}
