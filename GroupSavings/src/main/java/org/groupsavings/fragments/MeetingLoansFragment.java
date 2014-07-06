package org.groupsavings.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.activities.AddMeetingActivity;
import org.groupsavings.adapters.MeetingLoanAccTransactionAdapter;
import org.groupsavings.adapters.MeetingLoanAdapter;
import org.groupsavings.domain.GroupMeeting;
import org.groupsavings.domain.LoanAccount;
import org.groupsavings.domain.MeetingLoanAccTransaction;

import java.util.ArrayList;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link MeetingLoansFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingLoansFragment extends Fragment {

    GroupMeeting meeting;
    boolean readOnly;
    ArrayList<MeetingLoanAccTransaction> LoanTransactions;
    ArrayList<LoanAccount> NewLoans;
    public MeetingLoanAdapter newloansAdapter;

    public MeetingLoansFragment() {
        // Required empty public constructor
    }

    public static MeetingLoansFragment newInstance(GroupMeeting groupMeeting) {
        MeetingLoansFragment fragment = new MeetingLoansFragment();

        if(groupMeeting == null) return null;
        Bundle args = new Bundle();
        args.putSerializable("meeting",groupMeeting);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public MeetingLoansFragment(ArrayList<MeetingLoanAccTransaction> loanTransactions, ArrayList<LoanAccount> newLoans, boolean readOnly)
    {
        LoanTransactions = loanTransactions;
        NewLoans = newLoans;
        this.readOnly = readOnly;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View returnView = inflater.inflate(R.layout.fragment_meeting_loans, container, false);

        if(LoanTransactions == null || NewLoans == null) return  returnView;

        try
        {
            ListView lv_newLoans = (ListView) returnView.findViewById(R.id.lv_meeting_newLoans);

            newloansAdapter =
                    new MeetingLoanAdapter(getActivity(),android.R.layout.simple_list_item_1,NewLoans,readOnly);
            lv_newLoans.setAdapter(newloansAdapter);

            ListView lv_loanAccTransactions = (ListView) returnView.findViewById(R.id.lv_meeting_loanTransactions);
            MeetingLoanAccTransactionAdapter loanAccTransactionAdapter =
                    new MeetingLoanAccTransactionAdapter(getActivity(), android.R.layout.simple_list_item_1, LoanTransactions, readOnly);
            lv_loanAccTransactions.setAdapter(loanAccTransactionAdapter);
        }
        catch(Exception ex)
        {
            Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }

        return returnView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("readOnly", readOnly);
        super.onSaveInstanceState(outState);
    }

}
