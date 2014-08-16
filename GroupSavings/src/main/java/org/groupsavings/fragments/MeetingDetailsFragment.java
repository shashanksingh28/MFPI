package org.groupsavings.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.adapters.MeetingDetailsAdapter;
import org.groupsavings.adapters.MeetingLoanAdapter;
import org.groupsavings.domain.GroupMeeting;
import org.groupsavings.domain.LoanAccount;
import org.groupsavings.domain.MeetingDetails;
import org.groupsavings.domain.MeetingLoanAccTransaction;

import java.util.ArrayList;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link MeetingDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MeetingDetailsFragment extends Fragment {

    boolean readOnly;
    ArrayList<MeetingDetails> meetingDetails;
    public MeetingDetailsAdapter meetingDetailsAdapter;

    public static MeetingDetailsFragment newInstance(GroupMeeting groupMeeting) {
        MeetingDetailsFragment fragment = new MeetingDetailsFragment();

        if(groupMeeting == null) return null;
        Bundle args = new Bundle();
        args.putSerializable("meeting", groupMeeting);
        fragment.setArguments(args);
        return fragment;
    }
    public MeetingDetailsFragment() {
        // Required empty public constructor
    }

    public MeetingDetailsFragment(ArrayList<MeetingDetails> details, boolean readOnly)
    {
        this.readOnly = readOnly;
        this.meetingDetails = details;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View returnView = inflater.inflate(R.layout.fragment_meeting_details, container, false);

        try
        {
            ListView lv_meetingDetails = (ListView) returnView.findViewById(R.id.lv_meeting_details);
            meetingDetailsAdapter = new MeetingDetailsAdapter(getActivity(),android.R.layout.simple_list_item_1, meetingDetails, readOnly);
            lv_meetingDetails.setAdapter(meetingDetailsAdapter);
        }
        catch (Exception ex)
        {
            Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

        return returnView;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("readOnly", readOnly);
        super.onSaveInstanceState(outState);
    }

}
