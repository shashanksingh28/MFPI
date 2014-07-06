package org.groupsavings.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.activities.AddMeetingActivity;
import org.groupsavings.activities.GroupLandingActivity;
import org.groupsavings.activities.ViewMeetingActivity;
import org.groupsavings.adapters.MeetingSavingsAccTransactionAdapter;
import org.groupsavings.domain.GroupMeeting;
import org.groupsavings.domain.MeetingSavingsAccTransaction;

import java.util.ArrayList;

public class MeetingSavingsFragment extends Fragment {

    GroupMeeting meeting;
    boolean readOnly;
    ArrayList<MeetingSavingsAccTransaction> SavingTransactions;

    public MeetingSavingsFragment() {
        // Required empty public constructor
    }

    public static MeetingSavingsFragment newInstance(GroupMeeting groupMeeting, boolean readOnly) {
        MeetingSavingsFragment fragment = new MeetingSavingsFragment();

        if(groupMeeting == null) return null;
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View returnView = inflater.inflate(R.layout.fragment_meeting_savings, container, false);

        if(SavingTransactions == null) return  returnView;

        try
        {
            ListView lv_savingTrans = (ListView) returnView.findViewById(R.id.lv_meeting_savingTransactions);
            MeetingSavingsAccTransactionAdapter savingTransAdapter =
                    new MeetingSavingsAccTransactionAdapter(getActivity(),android.R.layout.simple_list_item_1,SavingTransactions,false);
            lv_savingTrans.setAdapter(savingTransAdapter);
        }
        catch(Exception ex)
        {
            Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }

        return returnView;
    }

    public MeetingSavingsFragment(ArrayList<MeetingSavingsAccTransaction> transactions, boolean readOnly)
    {
        this.SavingTransactions = transactions;
        this.readOnly = readOnly;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("readOnly", readOnly);
        super.onSaveInstanceState(outState);
    }

}
