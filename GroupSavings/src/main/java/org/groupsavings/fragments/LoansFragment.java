package org.groupsavings.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.groupsavings.R;
import org.groupsavings.activities.NewLoanActivity;
import org.groupsavings.constants.Intents;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.domain.GroupMeeting;

import java.util.ArrayList;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link LoansFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class LoansFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Group UID";
    Activity activity;
    DatabaseHandler dbHandler;
    ArrayList<GroupMeeting> meetings;
    ArrayAdapter<GroupMeeting> meetingsAdapter;
    String groupId;

    // TODO: Rename and change types and number of parameters
    public static LoansFragment newInstance(int param1) {
        LoansFragment fragment = new LoansFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    public LoansFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupId = getArguments().getString(ARG_PARAM1);
        }
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loans, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Button button = (Button) activity.findViewById(R.id.bt_add_new_loan);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_add_new_loan:
                Intent intent = new Intent(getActivity(),NewLoanActivity.class);
                intent.putExtra(Intents.INTENT_EXTRA_GROUPID, groupId);
                startActivity(intent);
                break;
        }
    }
}
