package org.groupsavings.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.ViewHelper;
import org.groupsavings.handlers.DatabaseHandler;
import org.groupsavings.domain.*;

import java.util.Calendar;

public class AddGroupActivity extends Activity implements View.OnClickListener {

    private int mmd_day;
    private int mmd_month;
    private int mmd_year;
    private TextView tv_mmd;
    DatabaseHandler db_handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

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
        if (id == R.id.action_settings) {
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
            case R.id.button_save_group:
                Group group = ViewHelper.fetchGroupDetailsFromView(findViewById(R.id.layout_group_details));
                if(group != null)
                {
                    db_handler.addUpdateGroup(group);
                }
                Toast.makeText(getApplicationContext(),"Group Saved",Toast.LENGTH_SHORT).show();
                finish();
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
        tv_mmd.setText(mmd_day+"/"+mmd_month+"/"+mmd_year);
    }
}
