package org.groupsavings.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.groupsavings.R;
import org.groupsavings.domain.Member;
import org.groupsavings.handlers.DatabaseHandler;

import java.util.Calendar;

public class AddMemberActivity extends Activity implements View.OnClickListener {

    private int dob_day;
    private int dob_month;
    private int dob_year;
    private TextView tv_dob;
    DatabaseHandler db_handler;
    private int groupUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupUID = getIntent().getIntExtra(GroupLandingActivity.INTENT_EXTRA_GROUP, 0);
        setContentView(R.layout.activity_add_member);
        db_handler = new DatabaseHandler(getApplicationContext());
        Calendar c = Calendar.getInstance();
        dob_year = c.get(Calendar.YEAR)-20;
        dob_month = c.get(Calendar.MONTH);
        dob_day = c.get(Calendar.DAY_OF_MONTH);

        ImageButton ib = (ImageButton) findViewById(R.id.imgbtn_pick_dob);
        ib.setOnClickListener(this);
        tv_dob = (TextView) findViewById(R.id.tv_member_dob);

        Button saveButton = (Button) findViewById(R.id.button_save_new_member);
        saveButton.setOnClickListener(this);
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
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
            case R.id.button_save_new_member:
                Member newMember = getMemberFromView();
                newMember.GroupUID = groupUID;
                db_handler.addUpdateMember(newMember);
                finish();
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
            dob_month = monthOfYear+1;
            dob_day = dayOfMonth;
            updateDOBDisplay();
        }
    }

    private void updateDOBDisplay() {
        tv_dob.setVisibility(View.VISIBLE);
        tv_dob.setText(dob_day+"/"+dob_month+"/"+dob_year);
    }

    private Member getMemberFromView()
    {
        Member newMember = new Member();

        EditText firstNameEditor=(EditText) findViewById(R.id.edit_member_firstname);
        if(firstNameEditor.getText()!= null) newMember.FirstName = firstNameEditor.getText().toString();

        EditText lastNameEditor=(EditText) findViewById(R.id.edit_member_lastname);
        if(lastNameEditor.getText()!= null) newMember.LastName = lastNameEditor.getText().toString();

        EditText contactEditor=(EditText) findViewById(R.id.edit_member_contact);
        if(contactEditor.getText() != null) newMember.ContactInfo = contactEditor.getText().toString();

        EditText emailEditor=(EditText) findViewById(R.id.edit_member_email);
        if(emailEditor.getText() != null) newMember.Email = emailEditor.getText().toString();

        TextView tv_dob = (TextView) findViewById(R.id.tv_member_dob);
        if(tv_dob.getText() != null) newMember.DOB = tv_dob.getText().toString();

        EditText add1Editor=(EditText) findViewById(R.id.edit_member_addressline1);
        if(add1Editor.getText() != null) newMember.AddressLine1 = add1Editor.getText().toString();

        EditText add2Editor=(EditText) findViewById(R.id.edit_member_addressline2);
        if(add2Editor.getText() != null) newMember.AddressLine2 = add2Editor.getText().toString();

        return newMember;
    }

    public void HideKeypad()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
