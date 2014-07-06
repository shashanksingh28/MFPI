package org.groupsavings.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.groupsavings.R;
import org.groupsavings.ViewHelper;
import org.groupsavings.activities.GroupLandingActivity;
import org.groupsavings.domain.Group;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.handlers.UserSessionManager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class GroupDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "GroupId";

    private String groupId;
    private Group group;
    private static int mmd_day;
    private static int mmd_month;
    private static int mmd_year;
    private static TextView tv_mmd;
    Activity act;
    DatabaseHandler db_handler;

    // Use this static factory method to instantiate
    public static GroupDetailsFragment newInstance(String groupId) {
        GroupDetailsFragment fragment = new GroupDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, groupId);
        fragment.setArguments(args);
        return fragment;
    }
    public GroupDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupId = getArguments().getString(ARG_PARAM1);
        }

        Calendar c = Calendar.getInstance();
        mmd_year = c.get(Calendar.YEAR)-20;
        mmd_month = c.get(Calendar.MONTH);
        mmd_day = c.get(Calendar.DAY_OF_MONTH);
        db_handler = new DatabaseHandler(getActivity());
        group = db_handler.getGroup(groupId);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_details, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        act=getActivity();
        Spinner spinnerDay = (Spinner) act.findViewById(R.id.group_mmd_date);

        List<String> list = new ArrayList<String>();
        for (int i = 1; i <= 31; ++i) {
            list.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(act,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);
        spinnerDay.setSelection(db_handler.getGroup(groupId).MonthlyMeetingDate);

        ViewHelper.populateGroupDetailsToView(getActivity().findViewById(R.id.layout_group_details), group);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.group_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.button_save_group_details:
                Group group = ViewHelper.fetchGroupDetailsFromView(getActivity().findViewById(R.id.layout_group_details));
                if(group != null)
                {
                    UserSessionManager session = new UserSessionManager(getActivity());
                    HashMap<String, String> user = session.getUserDetails();
                    group.ModifiedBy = user.get(UserSessionManager.KEY_USERNAME);
                    db_handler.addUpdateGroup(group);
                }
                HideKeypad();
                Toast.makeText(getActivity(), "Group Saved", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            // Use the current date as the default date in the picker
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, mmd_year, mmd_month, mmd_day);
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mmd_year  = year;
            mmd_month = monthOfYear+1;
            mmd_day = dayOfMonth;
            updateMMDDisplay();
        }
    }

    private static void updateMMDDisplay() {
        tv_mmd.setVisibility(View.VISIBLE);
        tv_mmd.setText(mmd_day+" of every month");
    }


    public void HideKeypad()
    {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
