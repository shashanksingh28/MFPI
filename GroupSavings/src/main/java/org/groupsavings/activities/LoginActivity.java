package org.groupsavings.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.handlers.UserSessionManager;

public class LoginActivity extends Activity {

    EditText txtUsername, txtPassword;

    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        // get Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        /*Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        try{
            switch(id)
            {
                case R.id.btnLogin:
                    // Get username, password from EditText
                    String username = txtUsername.getText().toString();
                    String password = txtPassword.getText().toString();

                    if(checkLogin(username, password))
                    {
                        // Creating user login session
                        // Statically storing name="Sample Name"
                        session.createUserLoginSession(username);

                        // Starting MainActivity
                        Intent i = new Intent(getApplicationContext(), GroupsGridActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                        finish();
                    }
                    else{
                        // username / password doesn't match&
                        Toast.makeText(getApplicationContext(),
                                "Username/Password is incorrect",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.action_settings:
                    return true;
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean checkLogin(String username, String password)
    {
        if(username.trim().length() > 0 && password.trim().length() > 0) {
            // For testing purpose username, password is checked with static data
            // username = admin
            // password = admin
            if (username.equals("admin") && password.equals("admin")) {
                return true;
            }
/********
 * Action:
 * replace static values with db check
 * If username = FieldOfficers.Id and password = FieldOfficers.PasswordHash
 *  return true
********/

        }
        return false;
    }
}
