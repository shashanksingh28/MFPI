package org.groupsavings;

/**
 * Created by shashank on 20/4/14.
 */
/*public class MeetingLoanAdapter extends ArrayAdapter<LoanAccount> {

    Context context;
    ArrayList<LoanAccount> loanAccounts;

    public MeetingLoanAdapter(Context context, int textViewResourceId, ArrayList<LoanAccount> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        loanAccounts = objects;
    }

    @Override
    public int getCount()
    {
        return loanAccounts.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public LoanAccount getItem(int position) {
        return loanAccounts.get(position);
    }

    @Override
    public View getView(int i, View convert_view, ViewGroup viewGroup) {

        final LoanAccount la = loanAccounts.get(i);

        try {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convert_view = inflater.inflate(R.layout.meeting_loan_row, viewGroup, false);

            TextView tv_memberName = (TextView) convert_view.findViewById(R.id.tv_meeting_loan_name);
            tv_memberName.setText(la.GroupMember.toString());

            TextView tv_reason = (TextView) convert_view.findViewById(R.id.tv_meeting_loan_reason);
            tv_reason.setText(la.Reason);

            TextView tv_principal = (TextView) convert_view.findViewById(R.id.tv_meeting_loan_principal);
            tv_principal.setText(String.valueOf(la.Principal));

            TextView tv_interest = (TextView) convert_view.findViewById(R.id.tv_meeting_loan_interest);
            tv_interest.setText(String.valueOf(la.InterestPerAnnum));

            TextView tv_period = (TextView) convert_view.findViewById(R.id.tv_meeting_loan_months);
            tv_period.setText(String.valueOf(la.PeriodInMonths));

            TextView tv_emi = (TextView) convert_view.findViewById(R.id.tv_meeting_loan_emi);
            tv_emi.setText(String.valueOf(la.EMI));

        } catch (Exception ex) {
            Log.d("ERROR", ex.getMessage());
        }

        return convert_view;
    }

}*/
