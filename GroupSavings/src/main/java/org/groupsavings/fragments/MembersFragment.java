package org.groupsavings.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.groupsavings.R;
import org.groupsavings.ViewHelper;
import org.groupsavings.activities.AddMemberActivity;
import org.groupsavings.activities.GroupLandingActivity;
import org.groupsavings.domain.Member;
import org.groupsavings.handlers.DatabaseHandler;

import java.util.ArrayList;

public class MembersFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String ARG_PARAM1 = "Group UID";
    Activity activity;
    DatabaseHandler dbHandler;
    ArrayList<Member> members;
    ArrayAdapter<Member> membersAdapter;
    View detailsContainer;
    int groupUID;

    public MembersFragment() {
        // Required empty public constructor
    }

    // Always use this factory method to instantiate
    public static MembersFragment newInstance(int groupUID) {
        MembersFragment fragment = new MembersFragment();
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
        members = dbHandler.getAllMembers(groupUID);
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
        /*Button addMemberButton = (Button) activity.findViewById(R.id.button_add_member);
        addMemberButton.setOnClickListener(this);
        Button saveMemberButton = (Button) activity.findViewById(R.id.button_save_member);
        saveMemberButton.setOnClickListener(this);*/

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
    public void onResume() {
        super.onResume();
        Refresh();
    }

    @Override
    public void onClick(View view) {
        /*switch (view.getId())
        {
            case R.id.button_add_member:
                Intent intent = new Intent(getActivity(),AddMemberActivity.class);
                intent.putExtra(GroupLandingActivity.INTENT_EXTRA_GROUP, groupUID);
                startActivity(intent);
                Refresh();
                break;
            case R.id.button_save_member:
                Member updatedMember = ViewHelper.fetchMemberDetailsFromView(detailsContainer);
                updatedMember.GroupUID = groupUID;
                dbHandler.addUpdateMember(updatedMember);
                HideKeypad();
                Toast.makeText(getActivity(),"Details saved",Toast.LENGTH_SHORT).show();
                ViewHelper.populateMemberDetailsToView(detailsContainer, new Member());
                Refresh();
                break;
            default:
                break;
        }*/
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
                intent.putExtra(GroupLandingActivity.INTENT_EXTRA_GROUP, groupUID);
                startActivity(intent);
                Refresh();
                break;
            case R.id.button_save_member:
                Member updatedMember = ViewHelper.fetchMemberDetailsFromView(detailsContainer);
                updatedMember.GroupUID = groupUID;
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
