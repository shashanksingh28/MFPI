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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.ViewHelper;
import org.groupsavings.domain.Group;
import org.groupsavings.database.DatabaseHandler;

import java.util.Calendar;


public class GroupDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "groupUID";


    private int groupUID;
    private Group group;
    private int mmd_day;
    private int mmd_month;
    private int mmd_year;
    private TextView tv_mmd;
    DatabaseHandler db_handler;

    // Use this static factory method to instantiate
    public static GroupDetailsFragment newInstance(int groupId) {
        GroupDetailsFragment fragment = new GroupDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, groupId);
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
            groupUID = getArguments().getInt(ARG_PARAM1);
        }

        Calendar c = Calendar.getInstance();
        mmd_year = c.get(Calendar.YEAR)-20;
        mmd_month = c.get(Calendar.MONTH);
        mmd_day = c.get(Calendar.DAY_OF_MONTH);
        db_handler = new DatabaseHandler(getActivity());
        group = db_handler.getGroup(groupUID);
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
        Button saveGroupButton = (Button) getActivity().findViewById(R.id.button_save_group_details);
        if(saveGroupButton != null){
            saveGroupButton.setOnClickListener(this);
        }
        ImageButton ib = (ImageButton) getActivity().findViewById(R.id.imgbtn_pick_mmd);
        ib.setOnClickListener(this);
        tv_mmd = (TextView) getActivity().findViewById(R.id.tv_group_mmd);

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
                    db_handler.addUpdateGroup(group);
                }
                HideKeypad();
                Toast.makeText(getActivity(), "Group Saved", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.imgbtn_pick_mmd:
                DialogFragment dialogFragment = new StartDatePicker();
                dialogFragment.show(getFragmentManager(), "Monthly Meeting Date");
                break;

            /*case R.id.button_save_group_details:
                Group group = ViewHelper.fetchGroupDetailsFromView(getActivity().findViewById(R.id.layout_group_details));
                if(group != null)
                {
                    db_handler.addUpdateGroup(group);
                }
                HideKeypad();
                Toast.makeText(getActivity(), "Group Saved", Toast.LENGTH_SHORT).show();
                break;*/
        }
    }

    class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
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

    private void updateMMDDisplay() {
        tv_mmd.setVisibility(View.VISIBLE);
        tv_mmd.setText(mmd_day+" of every month");
    }


    public void HideKeypad()
    {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
