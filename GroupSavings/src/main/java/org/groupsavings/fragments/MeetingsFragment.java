package org.groupsavings.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.groupsavings.R;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.domain.GroupMeeting;
import org.groupsavings.domain.Member;

import java.util.ArrayList;

public class MeetingsFragment extends Fragment {

    private static final String ARG_PARAM1 = "Group UID";
    Activity activity;
    DatabaseHandler dbHandler;
    ArrayList<GroupMeeting> meetings;
    ArrayAdapter<Member> meetingsAdapter;
    //View detailsContainer;
    int groupUID;

    public MeetingsFragment() {
        // Required empty public constructor
    }

    public static MeetingsFragment newInstance(int groupUID) {
        MeetingsFragment fragment = new MeetingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, groupUID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupUID = getArguments().getInt(ARG_PARAM1);
        }

        activity = getActivity();
        dbHandler = new DatabaseHandler(activity.getApplicationContext());
        //meetings = dbHandler.getAllMembers(groupUID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meetings, container, false);
    }

}
