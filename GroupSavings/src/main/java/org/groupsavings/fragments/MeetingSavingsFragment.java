package org.groupsavings.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.adapters.MeetingSavingsAccTransactionAdapter;
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

    @Override
    public void onStart()
    {
        super.onStart();
        try
        {
            ListView lv_savingTrans = (ListView) getActivity().findViewById(R.id.lv_meeting_savingTransactions);
            MeetingSavingsAccTransactionAdapter savingTransAdapter =
                    new MeetingSavingsAccTransactionAdapter(getActivity(),android.R.layout.simple_list_item_1,SavingTransactions,false);
            lv_savingTrans.setAdapter(savingTransAdapter);
        }
        catch(Exception ex)
        {
            Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}
