package org.groupsavings.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.activities.AddMeetingActivity;
import org.groupsavings.activities.GroupLandingActivity;
import org.groupsavings.domain.GroupMeeting;
import org.groupsavings.domain.MeetingTransaction;
import org.groupsavings.handlers.DatabaseHandler;

import java.util.ArrayList;

public class MeetingsFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String ARG_PARAM1 = "Group UID";
    Activity activity;
    DatabaseHandler dbHandler;
    ArrayList<GroupMeeting> meetings;
    ArrayAdapter<GroupMeeting> meetingsAdapter;
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
        setHasOptionsMenu(true);

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
            meetings = dbHandler.getAllGroupMeetings(groupUID, null);

            ListView lv = (ListView) activity.findViewById(R.id.listview_meeting_dates);
            meetingsAdapter = new ArrayAdapter<GroupMeeting>(activity, android.R.layout.simple_list_item_1, meetings);
            lv.setAdapter(meetingsAdapter);
            lv.setOnItemClickListener(this);

/*            Button addNewMeeting = (Button) activity.findViewById(R.id.button_add_meeting);
            addNewMeeting.setOnClickListener(this);*/

        } catch (Exception ex) {
            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void Refresh() {
        try {
            meetings = dbHandler.getAllGroupMeetings(groupUID, null);
            meetingsAdapter.clear();
            meetingsAdapter.addAll(meetings);
            meetingsAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Refresh();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        int meetingId = meetings.get(i).id;
        ArrayList<MeetingTransaction> readTrans = dbHandler.getMeetingTransactions(meetingId);
        Intent intent = new Intent(getActivity(), GroupLandingActivity.class);
        //intent.putExtra(GroupLandingActivity.INTENT_EXTRA_GROUP,groups.get(i).UID);
        //startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_meetings,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.button_add_meeting:
                Intent startMeetingActivity = new Intent(getActivity(), AddMeetingActivity.class);
                startMeetingActivity.putExtra(GroupLandingActivity.INTENT_EXTRA_GROUP, groupUID);
                startActivity(startMeetingActivity);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
