package org.groupsavings.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.ViewHelper;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.domain.Group;
import org.groupsavings.handlers.UserSessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddGroupActivity extends Activity {

    private int mmd_day;
    private int mmd_month;
    private int mmd_year;
    private TextView tv_mmd;
    DatabaseHandler db_handler;

    //  session management declarations start
    UserSessionManager session;
    private Handler handler = new Handler();
    //  session management declarations end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            //user session management starts
            session = new UserSessionManager(getApplicationContext());

            if(!session.isUserLoggedIn()) {
                //redirect to login activity
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }

            HashMap<String, String> user = session.getUserDetails();
            String name = user.get(UserSessionManager.KEY_USERNAME);
            //String UserId = db_handler.getId(name);
            Toast.makeText(getApplicationContext(), "User Login Status: " + session.isUserLoggedIn() + " Name: " + name, Toast.LENGTH_LONG).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
            }, 1800000);// session timeout of 30 minutes
            //user session management ends

            setContentView(R.layout.activity_add_group);

            final Spinner spinnerDay = (Spinner) findViewById(R.id.group_mmd_date);

            List<String> list = new ArrayList<String>();
            for (int i = 1; i <= 31; ++i) {
                list.add(String.valueOf(i));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDay.setAdapter(adapter);

            //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

            /*Calendar c = Calendar.getInstance();
            mmd_year = c.get(Calendar.YEAR)-20;
            mmd_month = c.get(Calendar.MONTH);
            mmd_day = c.get(Calendar.DAY_OF_MONTH);

            ImageButton ib = (ImageButton) findViewById(R.id.imgbtn_pick_mmd);
            ib.setOnClickListener(this);
            tv_mmd = (TextView) findViewById(R.id.tv_group_mmd);*/

            db_handler = new DatabaseHandler(getApplicationContext());

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
                HashMap<String, String> user = session.getUserDetails();
                group.CreatedBy = user.get(UserSessionManager.KEY_USERNAME);
                group.FieldOfficerId = group.CreatedBy;

                db_handler.addUpdateGroup(group);
                Toast.makeText(this,"Group Saved",Toast.LENGTH_SHORT).show();
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // TODO get FOID
        //user session management starts
        session = new UserSessionManager(getApplicationContext());

        if(!session.isUserLoggedIn()) {
            //redirect to login activity
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }

        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(UserSessionManager.KEY_USERNAME);
        //String UserId = db_handler.getId(name);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isUserLoggedIn() + " Name: " + name, Toast.LENGTH_LONG).show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        }, 1800000);// session timeout of 30 minutes
        //user session management ends
    }


}
