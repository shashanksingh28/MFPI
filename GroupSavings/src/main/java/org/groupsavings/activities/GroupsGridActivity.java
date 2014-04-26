package org.groupsavings.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.SyncHelper;
import org.groupsavings.domain.Group;
import org.groupsavings.handlers.DatabaseHandler;

import java.util.ArrayList;

public class GroupsGridActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static final int REQUEST_ADD_GROUP = 0;
    DatabaseHandler db_handler;
    ArrayList<Group> groups;
    ArrayAdapter adapter_grid;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_groups_grid);
            db_handler = new DatabaseHandler(getApplicationContext());
            db_handler.createSchema(null);

            groups = new ArrayList<Group>();
            Button addGroupButton =(Button) findViewById(R.id.button_add_group);
            addGroupButton.setOnClickListener(this);

            Button syncGroup = (Button) findViewById(R.id.button_syc);
            syncGroup.setOnClickListener(this);

            ListView gv = (ListView) findViewById(R.id.layout_groups_grid);
            adapter_grid = new ArrayAdapter(this,android.R.layout.simple_list_item_1,groups);
            gv.setAdapter(adapter_grid);
            gv.setOnItemClickListener(this);
        }
        catch (Exception ex)
        {
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        groups = db_handler.getAllGroups();
        adapter_grid.clear();
        adapter_grid.addAll(groups);
        adapter_grid.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.groups_grid, menu);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), GroupLandingActivity.class);
        intent.putExtra(GroupLandingActivity.INTENT_EXTRA_GROUP,groups.get(i).UID);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_add_group:
                Intent intent = new Intent(getApplicationContext(),AddGroupActivity.class);
                startActivityForResult(intent,REQUEST_ADD_GROUP);
                break;
            case R.id.button_syc:
                PushDataAsync asyncTask = new PushDataAsync();
                asyncTask.execute();
                break;
        }
    }

    public class PushDataAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(GroupsGridActivity.this, "Wait", "Synchronizing..");
        }

        @Override
        protected String doInBackground(String... strings) {
            SyncHelper syncHelper = new SyncHelper(getApplicationContext());
            try{
                return syncHelper.synchronize();
            }
            catch(Exception ex)
            {
                return ex.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            //Toast.makeText(getApplicationContext(), requestString,Toast.LENGTH_LONG).show();
            String message = result == null || result == "" ? "Synchronized" : result;
            Toast.makeText(GroupsGridActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }
}