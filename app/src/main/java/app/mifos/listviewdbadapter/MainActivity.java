package app.mifos.listviewdbadapter;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

    SqlHandler sqlHandler;
    ListView lvCustomList;
    EditText etName, etPhone;
    Button btnsubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCustomList = (ListView) findViewById(R.id.lv_custom_list);
        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        btnsubmit = (Button) findViewById(R.id.btn_submit);
        sqlHandler = new SqlHandler(this);
        showList();
        btnsubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String phoneNo = etPhone.getText().toString();
                String query = "INSERT INTO PHONE_CONTACTS(name,phone) values ('"
                        + name + "','" + phoneNo + "')";
                sqlHandler.executeQuery(query);
                showList();
                etName.setText("");
                etPhone.setText("");
            }
        });

        lvCustomList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView arg0, View arg1,int arg2, long arg3) {

                ContactListItems contactListItems = (ContactListItems)arg0.getItemAtPosition(arg2);
                String slno = contactListItems.getSlno();
                String delQuery = "DELETE FROM PHONE_CONTACTS WHERE slno='"+slno+"' ";
                sqlHandler.executeQuery(delQuery);
                showList();

                return false;
            }
        });
    }

    private void showList() {

        ArrayList<ContactListItems> contactList = new ArrayList<ContactListItems>();
        contactList.clear();
        String query = "SELECT * FROM PHONE_CONTACTS ";
        Cursor c1 = sqlHandler.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    ContactListItems contactListItems = new ContactListItems();
                    contactListItems.setSlno(c1.getString(c1
                            .getColumnIndex("slno")));
                    contactListItems.setName(c1.getString(c1
                            .getColumnIndex("name")));
                    contactListItems.setPhone(c1.getString(c1
                            .getColumnIndex("phone")));
                    contactList.add(contactListItems);
                } while (c1.moveToNext());
            }
        }
        c1.close();
        ContactListAdapter contactListAdapter = new ContactListAdapter(
                MainActivity.this, contactList);
        lvCustomList.setAdapter(contactListAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}