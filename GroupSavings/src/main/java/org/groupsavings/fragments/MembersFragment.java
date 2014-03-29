package org.groupsavings.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.ViewHelper;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.domain.Member;


import java.util.ArrayList;

public class MembersFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    Activity activity;
    DatabaseHandler dbHandler;
    ArrayList<Member> members;
    ArrayAdapter<Member> membersAdapter;
    View detailsContainer;
    int groupUID;

    public static final String ARG_PARAM1 = "Group UID";

    // Always use this factory method to instantiate
    public static MembersFragment newInstance(int groupUID) {
        MembersFragment fragment = new MembersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, groupUID);
        fragment.setArguments(args);
        return fragment;
    }

    public MembersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            groupUID = getArguments().getInt(ARG_PARAM1);
        }

        activity = getActivity();
        dbHandler = new DatabaseHandler(activity.getApplicationContext());
        members = dbHandler.getAllMembers(groupUID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_members, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        detailsContainer = activity.findViewById(R.id.layout_member_details_container);
        Button addMemberButton = (Button) activity.findViewById(R.id.button_add_member);
        addMemberButton.setOnClickListener(this);
        Button saveMemberButton = (Button) activity.findViewById(R.id.button_save_member);
        saveMemberButton.setOnClickListener(this);

        ListView lv = (ListView) activity.findViewById(R.id.listview_member_names);
        membersAdapter = new ArrayAdapter<Member>(activity,android.R.layout.simple_list_item_1,members);
        lv.setAdapter(membersAdapter);
        lv.setOnItemClickListener(this);
    }

    public void Refresh()
    {
        members = dbHandler.getAllMembers(groupUID);
        membersAdapter.clear();
        membersAdapter.addAll(members);
        membersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_add_member:
                Member newMember = new Member();
                ViewHelper.populateMemberDetailsToView(activity.findViewById(R.id.layout_member_details_container), newMember);
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                Toast.makeText(activity, "Add Member details and click on Save", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_save_member:
                Member updatedMember = ViewHelper.fetchMemberDetailsFromView(getActivity().findViewById(R.id.layout_member_details_container));
                updatedMember.GroupUID = groupUID;
                dbHandler.addUpdateMember(updatedMember);
                HideKeypad();
                Toast.makeText(getActivity(),"Details saved",Toast.LENGTH_SHORT).show();
                Refresh();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try
        {
            ViewHelper.populateMemberDetailsToView(detailsContainer, members.get(i));
        }
        catch (Exception ex)
        {
            Log.d("Exception", ex.getMessage());
        }
    }

    public void HideKeypad()
    {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
