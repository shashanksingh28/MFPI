package org.groupsavings.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.groupsavings.R;
import org.groupsavings.database.DatabaseHandler;
import org.groupsavings.handlers.MCrypt;
import org.groupsavings.handlers.UserSessionManager;

import java.sql.SQLException;

public class LoginActivity extends Activity {


    private boolean backPressedToExitOnce = false;
//    private Toast toast = null;

    EditText txtUsername, txtPassword;

    // User Session Manager Class
    UserSessionManager session;
    private boolean doubleBackToExitPressedOnce = false;

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
        try {
            if(username.trim().length() > 0 && password.trim().length() > 0) {
                if (
                        (username.equals("admin") && password.equals("admin"))
                        || (username.equals("testuser1") && password.equals("testuser1"))
                        || (username.equals("testuser2") && password.equals("testuser2"))
                   ) { //dummy values for demo/static functionality check - corresponding updates to be pushed from server to database
                    Toast.makeText(getApplicationContext(),"Logging in..",Toast.LENGTH_SHORT).show();
                    return true;
                }

                String encrypted = null;
                encrypted = MCrypt.bytesToHex((new MCrypt()).encrypt(password));
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                try {
                    if (username != null && password!=null) {
                        String sql = "Select * from FieldOfficers Where UserName='" + username + "' and PasswordHash='" + encrypted + "'";
                        if(db.checkIfExists(sql) != 0){
                            return true;
                        }
                    }
                } catch (Exception err) {
                    Toast.makeText(getApplicationContext(),err.getMessage(),Toast.LENGTH_LONG).show();
                    return false;
                }

             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static long back_pressed;
    private Toast toast;
    @Override
    public void onBackPressed()
    {


        if (back_pressed + 2000 > System.currentTimeMillis())
        {

            // need to cancel the toast here
            toast.cancel();

            // code for exit
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
        else
        {
            // ask user to press back button one more time to close app
            toast=  Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT);
            toast.show();
        }
        back_pressed = System.currentTimeMillis();
    }

}
