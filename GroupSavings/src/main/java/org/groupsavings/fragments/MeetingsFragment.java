package org.groupsavings.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.domain.GroupMeeting;

import java.util.ArrayList;

public class MeetingsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String ARG_PARAM1 = "Group UID";
    Activity activity;
    DatabaseHandler dbHandler;
    ArrayList<GroupMeeting> meetings;
    ArrayAdapter<GroupMeeting> meetingsAdapter;
    View meetingsListView;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meetings, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        try {
            activity = getActivity();
            dbHandler = new DatabaseHandler(activity.getApplicationContext());
            meetings = dbHandler.getGroupMeetings(groupUID, null);

            ListView lv = (ListView) activity.findViewById(R.id.listview_meeting_dates);
            meetingsAdapter = new ArrayAdapter<GroupMeeting>(activity, android.R.layout.simple_list_item_1, meetings);
            lv.setAdapter(meetingsAdapter);
            lv.setOnItemClickListener(this);
        } catch (Exception ex) {
            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Call MeetingActivity with meeting date parameter
    }
}
