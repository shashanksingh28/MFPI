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
import org.groupsavings.activities.ViewMeetingActivity;
import org.groupsavings.constants.Intents;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.domain.GroupMeeting;

import java.util.ArrayList;

public class MeetingsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String ARG_PARAM1 = "GroupId";
    String groupId;

    Activity activity;
    DatabaseHandler dbHandler;
    ArrayList<GroupMeeting> meetings;
    ArrayAdapter<GroupMeeting> meetingsAdapter;

    public MeetingsFragment() {
        // Required empty public constructor
    }

    public static MeetingsFragment newInstance(String paramGroupId) {
        MeetingsFragment fragment = new MeetingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, paramGroupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            groupId = getArguments().getString(ARG_PARAM1);
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
            meetings = dbHandler.getAllGroupMeetings(groupId, null);

            ListView lv = (ListView) activity.findViewById(R.id.listview_meeting_dates);
            meetingsAdapter = new ArrayAdapter<GroupMeeting>(activity, android.R.layout.simple_list_item_1, meetings);
            lv.setAdapter(meetingsAdapter);
            lv.setOnItemClickListener(this);

        } catch (Exception ex) {
            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void Refresh() {
        try {
            meetings = dbHandler.getAllGroupMeetings(groupId, null);
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
        Intent intent = new Intent(getActivity(), ViewMeetingActivity.class);
        intent.putExtra(Intents.INTENT_EXTRA_GROUPID,groupId);
        intent.putExtra(Intents.INTENT_EXTRA_MEETINGID,meetings.get(i).Id);
        startActivity(intent);
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
                startMeetingActivity.putExtra(Intents.INTENT_EXTRA_GROUPID, groupId);
                startActivity(startMeetingActivity);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
