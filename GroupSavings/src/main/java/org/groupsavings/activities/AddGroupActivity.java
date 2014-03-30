package org.groupsavings.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.ViewHelper;
import org.groupsavings.handlers.DatabaseHandler;
import org.groupsavings.domain.*;


public class AddGroupActivity extends Activity implements View.OnClickListener {

    DatabaseHandler db_handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

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
}
