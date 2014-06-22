package org.groupsavings.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.groupsavings.R;
import org.groupsavings.domain.MeetingSavingsAccTransaction;

import java.util.ArrayList;

public class MeetingSavingsFragment extends Fragment {

    ArrayList<MeetingSavingsAccTransaction> SavingTransactions;

    public MeetingSavingsFragment() {
        // Required empty public constructor
    }

    public MeetingSavingsFragment(ArrayList<MeetingSavingsAccTransaction> transactions)
    {
        this.SavingTransactions = transactions;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meeting_savings, container, false);
    }


}
