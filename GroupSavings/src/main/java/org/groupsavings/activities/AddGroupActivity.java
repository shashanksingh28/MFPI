package org.groupsavings.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.ViewHelper;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.domain.Group;

import java.util.Calendar;

public class AddGroupActivity extends Activity implements View.OnClickListener {

    private int mmd_day;
    private int mmd_month;
    private int mmd_year;
    private TextView tv_mmd;
    DatabaseHandler db_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_group);

            //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

            Calendar c = Calendar.getInstance();
            mmd_year = c.get(Calendar.YEAR)-20;
            mmd_month = c.get(Calendar.MONTH);
            mmd_day = c.get(Calendar.DAY_OF_MONTH);

            ImageButton ib = (ImageButton) findViewById(R.id.imgbtn_pick_mmd);
            ib.setOnClickListener(this);
            tv_mmd = (TextView) findViewById(R.id.tv_group_mmd);


            db_handler = new DatabaseHandler(getApplicationContext());

            Button saveGroupButton = (Button) findViewById(R.id.button_save_group);
            if(saveGroupButton != null) saveGroupButton.setOnClickListener(this);

            /* Commenting this part as no group Members exist at the time of group creation */
            /*
            groupMembers = db_handler.getGroupMembers();

            president_spinner = (Spinner) findViewById(R.id.sp_group_president);
            grpMembersAdapter = new ArrayAdapter<Member>(this,android.R.layout.simple_spinner_dropdown_item,groupMembers);
            president_spinner.setAdapter(grpMembersAdapter);

            secretary_spinner = (Spinner) findViewById(R.id.sp_group_secretary);
            grpMembersAdapter = new ArrayAdapter<Member>(this,android.R.layout.simple_spinner_dropdown_item,groupMembers);
            secretary_spinner.setAdapter(grpMembersAdapter);

            treasurer_spinner = (Spinner) findViewById(R.id.sp_group_treasurer);
            grpMembersAdapter = new ArrayAdapter<Member>(this,android.R.layout.simple_spinner_dropdown_item,groupMembers);
            treasurer_spinner.setAdapter(grpMembersAdapter);
            */
        }
        catch (Exception ex)
        {
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case R.id.action_settings:
                return true;
            case R.id.button_save_group:
                Group group = ViewHelper.fetchGroupDetailsFromView(findViewById(R.id.layout_group_details));

                if(group.Name == null || group.Name.equals(""))
                {
                    Toast.makeText(this,"Please enter a group name.",Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(group.MonthlyCompulsoryAmount == 0)
                {
                    Toast.makeText(this,"Please enter a group savings value",Toast.LENGTH_SHORT).show();
                    return true;
                }

                db_handler.addUpdateGroup(group);
                Toast.makeText(this,"Group Saved",Toast.LENGTH_SHORT).show();
                finish();

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
        }
    }


    class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            // Use the current date as the default date in the picker
            DatePickerDialog dialog = new DatePickerDialog(AddGroupActivity.this, this, mmd_year, mmd_month, mmd_day);
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mmd_year  = year;
            mmd_month = monthOfYear;
            mmd_day = dayOfMonth;
            updateMMDDisplay();
        }
    }

    private void updateMMDDisplay() {
        tv_mmd.setVisibility(View.VISIBLE);
        tv_mmd.setText(mmd_day);
        //+" of every month"
    }
}
