package org.groupsavings.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.handlers.ExceptionHandler;
import org.groupsavings.handlers.UserSessionManager;

import java.util.HashMap;

public class CrashActivity extends Activity {


    //  session management declarations start
    UserSessionManager session;
    private Handler handler = new Handler();
    //  session management declarations end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.activity_crash);
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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.crash, menu);
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

}
