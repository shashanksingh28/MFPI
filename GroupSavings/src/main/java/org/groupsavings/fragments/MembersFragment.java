package org.groupsavings.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.StringHelper;
import org.groupsavings.ViewHelper;
import org.groupsavings.activities.AddMemberActivity;
import org.groupsavings.constants.Intents;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.domain.Member;
import org.groupsavings.handlers.UserSessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class MembersFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String ARG_PARAM1 = "GroupID";
    Activity activity;
    DatabaseHandler dbHandler;
    ArrayList<Member> members;
    ArrayAdapter<Member> membersAdapter;
    View detailsContainer;
    String groupId;

    public MembersFragment() {
        // Required empty public constructor
    }

    // Always use this factory method to instantiate
    public static MembersFragment newInstance(String groupId) {
        MembersFragment fragment = new MembersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            groupId = getArguments().getString(ARG_PARAM1);
        }

        activity = getActivity();
        dbHandler = new DatabaseHandler(activity.getApplicationContext());
        members = dbHandler.getGroupMembers(groupId);
        setHasOptionsMenu(true);
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

        ListView lv = (ListView) activity.findViewById(R.id.listview_member_names);
        membersAdapter = new ArrayAdapter<Member>(activity,android.R.layout.simple_list_item_1,members);
        lv.setAdapter(membersAdapter);
        lv.setOnItemClickListener(this);
    }

    public void Refresh()
    {
        members = dbHandler.getGroupMembers(groupId);
        membersAdapter.clear();
        membersAdapter.addAll(members);
        membersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Refresh();
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
@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.member_screen, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.button_add_member:
                Intent intent = new Intent(getActivity(),AddMemberActivity.class);
                intent.putExtra(Intents.INTENT_EXTRA_GROUPID, groupId);
                startActivity(intent);
                Refresh();
                break;
            case R.id.button_save_member:
                // Get memberId of Member that is displayed
                String memberId = null;
                Member updatedMember = new Member();

                TextView memberIdText = (TextView) getActivity().findViewById(R.id.layout_member_uid);
                if (memberIdText != null && memberIdText.getText() != null)
                {
                    String uid_string = memberIdText.getText().toString();
                    if (uid_string != null && !uid_string.isEmpty()){
                        memberId = uid_string;
                    }
                }

                // Get Member object first because not all fields will be editable and thus not all will
                // be fetched via viewHelper.
                if(!StringHelper.IsNullOrEmpty(memberId))
                    updatedMember = dbHandler.getMember(memberId, null);

                ViewHelper.fetchMemberDetailsFromView(detailsContainer, updatedMember);

                updatedMember.GroupId = groupId;
                if(StringHelper.IsNullOrEmpty(updatedMember.Id))
                {
                    Toast.makeText(getActivity(),"Please choose a member to update",Toast.LENGTH_SHORT).show();
                    break;
                }
                UserSessionManager session = new UserSessionManager(getActivity());
                HashMap<String, String> user = session.getUserDetails();
                updatedMember.ModifiedBy = user.get(UserSessionManager.KEY_USERNAME);

                dbHandler.addUpdateMember(updatedMember);
                HideKeypad();
                Toast.makeText(getActivity(),"Details saved",Toast.LENGTH_SHORT).show();
                ViewHelper.populateMemberDetailsToView(detailsContainer, new Member());
                Refresh();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
