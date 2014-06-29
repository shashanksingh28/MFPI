package org.groupsavings.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.activities.NewLoanActivity;
import org.groupsavings.constants.Intents;
import org.groupsavings.domain.LoanAccount;
import org.groupsavings.domain.MeetingLoanAccTransactions;

import java.util.ArrayList;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link MeetingLoansFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingLoansFragment extends Fragment {

    ArrayList<MeetingLoanAccTransactions> LoanTransactions;
    ArrayList<LoanAccount> NewLoans;

    public MeetingLoansFragment() {
        // Required empty public constructor
    }

    public MeetingLoansFragment(ArrayList<MeetingLoanAccTransactions> transactions, ArrayList<LoanAccount> newLoans)
    {
        LoanTransactions = transactions;
        NewLoans = newLoans;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meeting_loans, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case R.id.bt_add_new_loan:

                Toast.makeText(getActivity(),"Clicked",Toast.LENGTH_LONG).show();
                /*Intent intent = new Intent(getActivity(),NewLoanActivity.class);

                for(int i = 0; i<loanAccounts.size(); i++)
                {
                    alreadyLoaned[i]=loanAccounts.get(i).memberId;
                }
                intent.putExtra(Intents.INTENT_EXTRA_GROUPID, getActivity());
                //intent.putExtra(GroupLandingActivity.INTENT_EXTRA_ALREADY_LOANED_MEMBER_IDS,alreadyLoaned);
                //intent.putExtra(GroupLandingActivity.INTENT_EXTRA_ALREADY_LOANED_COUNT,loanAccounts.size());
                startActivityForResult(intent, REQUEST_GET_NEW_LOANACCOUNT);
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }


}
