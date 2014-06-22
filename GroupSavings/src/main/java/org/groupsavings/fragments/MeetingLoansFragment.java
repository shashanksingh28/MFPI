package org.groupsavings.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.groupsavings.R;
import org.groupsavings.domain.MeetingLoanAccTransactions;

import java.util.ArrayList;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link MeetingLoansFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingLoansFragment extends Fragment {

    ArrayList<MeetingLoanAccTransactions> LoanTransactions;

    public MeetingLoansFragment() {
        // Required empty public constructor
    }

    public MeetingLoansFragment(ArrayList<MeetingLoanAccTransactions> transactions)
    {
        LoanTransactions = transactions;
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


}
