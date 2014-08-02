package org.groupsavings.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import org.groupsavings.constants.Intents;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.domain.Group;
import org.groupsavings.handlers.UserSessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupsGridActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static final int REQUEST_ADD_GROUP = 0;
    DatabaseHandler db_handler;
    ArrayList<Group> groups;
    ArrayAdapter adapter_grid;
    private ProgressDialog pDialog;

    //  session management declarations start
        UserSessionManager session;
        private Handler handler = new Handler();
    //  session management declarations end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);

            //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

            setContentView(R.layout.activity_groups_grid);

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

            // Changes needed to fix table
            //db_handler.execQuery("DROP TABLE " + Tables.SAVINGACCTRANSACTIONS);
            // change the name to proper name and then execute other one
            //db_handler.execQuery(Tables.CREATE_TABLE_SAVINGACCTRANSACTIONS);

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
        // TODO get FOID
        String officerId="";
        groups = db_handler.getAllFOGroups(officerId);
        adapter_grid.clear();
        adapter_grid.addAll(groups);
        adapter_grid.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.groups_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.button_add_group:
                Intent intent = new Intent(getApplicationContext(),AddGroupActivity.class);
                startActivityForResult(intent,REQUEST_ADD_GROUP);
                return true;
            case R.id.button_syc:
                PushDataAsync asyncTask = new PushDataAsync();
                asyncTask.execute();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), GroupLandingActivity.class);
        intent.putExtra(Intents.INTENT_EXTRA_GROUPID,groups.get(i).Id);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
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
                // Disabling sync so that other people can build
                //return "";
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
            onResume();
        }
    }
}