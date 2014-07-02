package org.groupsavings.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.activities.NewLoanActivity;
import org.groupsavings.adapters.MeetingLoanAdapter;
import org.groupsavings.adapters.MeetingSavingsAccTransactionAdapter;
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
        try
        {
            ListView lv_newLoans = (ListView) getActivity().findViewById(R.id.lv_meeting_newLoans);

            MeetingLoanAdapter newloansAdapter =
                    new MeetingLoanAdapter(getActivity(),android.R.layout.simple_list_item_1,NewLoans,false);
            lv_newLoans.setAdapter(newloansAdapter);
        }
        catch(Exception ex)
        {
            Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
