package org.groupsavings;

/**
 * Created by shashank on 31/3/14.
 */
/*
public class MeetingTransactionsAdapter extends ArrayAdapter<MeetingTransaction> {

    Context context;
    ArrayList<MeetingTransaction> transactions;
    boolean readonly;

    public MeetingTransactionsAdapter(Context context, int textViewResourceId, ArrayList<MeetingTransaction> objects, boolean readonly) {
        super(context, textViewResourceId, objects);
        this.context = context;
        transactions = objects;
        this.readonly = readonly;
    }

    @Override
    public View getView(int i, View convert_view, ViewGroup viewGroup) {

        final MeetingTransaction transaction = transactions.get(i);

        try {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convert_view = inflater.inflate(R.layout.meeting_transaction_row, viewGroup, false);

            TextView tv_memberName = (TextView) convert_view.findViewById(R.id.tv_transaction_name);
            tv_memberName.setText(transaction.GroupMember.toString());

            final EditText et_grpCompSavings = (EditText) convert_view.findViewById(R.id.et_transaction_compulsory_savings);
            et_grpCompSavings.setText(String.valueOf(transaction.SavingTransaction.groupCompulsorySavings));
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
                            int prev = transaction.SavingTransaction.groupCompulsorySavings;
                            int curr = Integer.valueOf(et_grpCompSavings.getText().toString());
                            if(prev != curr)
                            {
                                transaction.SavingTransaction.groupCompulsorySavings = curr;
                                notifyDataSetChanged();
                            }
                        }
                    }
                });
            }

            TextView tv_totalSavings = (TextView) convert_view.findViewById(R.id.tv_transaction_total_saving);
            tv_totalSavings.setText(String.valueOf(transaction.SavingTransaction.getTotalSavings()));

            final EditText et_optionalSavings = (EditText) convert_view.findViewById(R.id.et_transaction_optional_savings);
            et_optionalSavings.setText(String.valueOf(transaction.SavingTransaction.optionalSavings));
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
                            int prev = transaction.SavingTransaction.optionalSavings;
                            int curr =  Integer.valueOf(et_optionalSavings.getText().toString());
                            if(prev != curr) {
                                transaction.SavingTransaction.optionalSavings = curr;
                                notifyDataSetChanged();
                            }
                        }
                    }});
            }


            if(transaction.LoanTransaction.EMI > 0)
            {
                TextView tv_emi = (TextView) convert_view.findViewById(R.id.tv_transaction_loan_emi);
                tv_emi.setText(String.valueOf(transaction.LoanTransaction.EMI));

                final EditText et_repayment = (EditText) convert_view.findViewById(R.id.et_transaction_loan_repayment);
                et_repayment.setText(String.valueOf(transaction.LoanTransaction.Repayment));
                if(readonly)
                {
                    et_repayment.setFocusable(false);
                }
                else
                {
                    et_repayment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {
                            if(!hasFocus)
                            {
                                // A kind of workaround since this is being called more than once weirdly
                                long prev = transaction.LoanTransaction.Repayment;
                                long curr = Long.valueOf(et_repayment.getText().toString());
                                if(prev != curr)
                                {
                                    transaction.LoanTransaction.Repayment = curr;
                                    notifyDataSetChanged();
                                }
                            }
                        }
                    });
                }
                TextView tv_outstanding = (TextView) convert_view.findViewById(R.id.tv_transaction_loan_outstanding);
                if(readonly)
                {
                    tv_outstanding.setText(String.valueOf(transaction.LoanTransaction.getAsIsOutstanding()));
                }
                else
                {
                    tv_outstanding.setText(String.valueOf(transaction.LoanTransaction.getUpdatedOutstanding()));
                }

            }
            else
            {
                EditText tv_repayment = (EditText) convert_view.findViewById(R.id.et_transaction_loan_repayment);
                tv_repayment.setVisibility(View.INVISIBLE);
            }
        } catch (Exception ex) {
            Log.d("ERROR", ex.getMessage());
        }

        return convert_view;
    }
}*/
