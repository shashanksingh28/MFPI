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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.constants.Intents;
import org.groupsavings.domain.Member;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.handlers.ExceptionHandler;
import org.groupsavings.handlers.UserSessionManager;

import java.util.Calendar;
import java.util.HashMap;

public class AddMemberActivity extends Activity implements View.OnClickListener {

    private int dob_day;
    private int dob_month;
    private int dob_year;
    private TextView tv_dob;
    DatabaseHandler db_handler;
    private String groupId;

//  session management declarations start
    UserSessionManager session;
    private Handler handler = new Handler();
//  session management declarations end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);

            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

            groupId = getIntent().getStringExtra(Intents.INTENT_EXTRA_GROUPID);
            setContentView(R.layout.activity_add_member);
            //user session management starts
            session = new UserSessionManager(getApplicationContext());

            if(!session.isUserLoggedIn()) {
                //redirect to login activity
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }

            HashMap<String, String> user = session.getUserDetails();
            String name = user.get(UserSessionManager.KEY_NAME);
            Toast.makeText(getApplicationContext(), "User Login Status: " + session.isUserLoggedIn() + " Name: " + name, Toast.LENGTH_LONG).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
            }, 1800000);// session timeout of 30 minutes
            //user session management ends

            db_handler = new DatabaseHandler(getApplicationContext());
            Calendar c = Calendar.getInstance();
            dob_year = c.get(Calendar.YEAR);
            dob_month = c.get(Calendar.MONTH);
            dob_day = c.get(Calendar.DAY_OF_MONTH);

            ImageButton ib = (ImageButton) findViewById(R.id.imgbtn_pick_dob);
            ib.setOnClickListener(this);
            tv_dob = (TextView) findViewById(R.id.tv_member_dob);

        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_member, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId())
        {
            case R.id.action_settings:
                return true;

            case R.id.button_save_new_member:
                Member newMember = getMemberFromView();
                newMember.GroupId = groupId;
                String validationString = ValidateMember(newMember);
                if(validationString.equals("")){
                    db_handler.addUpdateMember(newMember);
                    finish();
                }
                else
                {
                    Toast.makeText(this,validationString,Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.imgbtn_pick_dob:
                DialogFragment dialogFragment = new StartDatePicker();
                dialogFragment.show(getFragmentManager(), "Date of Birth");
                break;
        }

    }

    class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            // Use the current date as the default date in the picker
            DatePickerDialog dialog = new DatePickerDialog(AddMemberActivity.this, this, dob_year, dob_month, dob_day);
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            dob_year = year;
            dob_month = monthOfYear;
            dob_day = dayOfMonth;
            updateDOBDisplay();
        }
    }

    private void updateDOBDisplay() {
        tv_dob.setVisibility(View.VISIBLE);
        tv_dob.setText(dob_day+"/"+(dob_month+1)+"/"+dob_year);
    }

    private Member getMemberFromView()
    {
        Member newMember = new Member();

        EditText firstNameEditor=(EditText) findViewById(R.id.edit_member_firstname);
        if(firstNameEditor.getText()!= null) newMember.FirstName = firstNameEditor.getText().toString();

        EditText lastNameEditor=(EditText) findViewById(R.id.edit_member_lastname);
        if(lastNameEditor.getText()!= null) newMember.LastName = lastNameEditor.getText().toString();

        EditText contactEditor=(EditText) findViewById(R.id.edit_member_contact);
        if(contactEditor.getText() != null) newMember.ContactNumber = contactEditor.getText().toString();

        EditText emailEditor=(EditText) findViewById(R.id.edit_member_email);
        if(emailEditor.getText() != null) newMember.EmailId = emailEditor.getText().toString();

        TextView tv_dob = (TextView) findViewById(R.id.tv_member_dob);
        if(tv_dob.getText() != null) newMember.DOB = tv_dob.getText().toString();

        EditText add1Editor=(EditText) findViewById(R.id.edit_member_addressline1);
        if(add1Editor.getText() != null) newMember.AddressLine1 = add1Editor.getText().toString();

        EditText add2Editor=(EditText) findViewById(R.id.edit_member_addressline2);
        if(add2Editor.getText() != null) newMember.AddressLine2 = add2Editor.getText().toString();

        EditText passBookEditor = (EditText) findViewById(R.id.edit_pass_book_no);
        if(passBookEditor.getText() != null) newMember.Passbook = passBookEditor.getText().toString();

        EditText guardianNameEditor = (EditText) findViewById(R.id.edit_guardian_name);
        if(guardianNameEditor.getText() != null) newMember.GuardianName = guardianNameEditor.getText().toString();

        EditText economicConditionEditor = (EditText) findViewById(R.id.edit_economic_condition);
        if(economicConditionEditor.getText() != null) newMember.EconomicCondition = economicConditionEditor.getText().toString();

        EditText educationEditor = (EditText) findViewById(R.id.edit_education_qualification);
        if(educationEditor.getText() != null) newMember.Education = educationEditor.getText().toString();

        EditText occupationEditor = (EditText) findViewById(R.id.edit_member_occupation);
        if(occupationEditor.getText() != null) newMember.Occupation = occupationEditor.getText().toString();

        EditText noOfFamilyMembersEditor = (EditText) findViewById(R.id.edit_count_family_members);
        if(noOfFamilyMembersEditor.getText() != null)
        {
            String noffamilymembers = noOfFamilyMembersEditor.getText().toString();
            if(noffamilymembers != null && !noffamilymembers.isEmpty())
                newMember.NoOfFamilyMembers = Integer.parseInt(noffamilymembers);
        }
        return newMember;
    }

    public String ValidateMember(Member member)
    {

        if(member.FirstName == null || member.FirstName.length() == 0)
            return "Please enter a valid First Name";

        return "";
    }
}
