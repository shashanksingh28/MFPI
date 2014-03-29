package org.groupsavings.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;

import org.groupsavings.domain.*;

import java.util.ArrayList;

/**
 * Created by shashank on 4/3/14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "communityfinance";
    // Members Table Name
    private static final String MEMBERS_TABLE_NAME = "Members";
    // Member Column names
    private static final String COLUMN_MEMBER_UID = "UID";
    private static final String COLUMN_MEMBERGROUP_UID = "GroupUID";
    private static final String COLUMN_MEMBER_FIRSTNAME = "FirstName";
    private static final String COLUMN_MEMBER_LASTNAME = "LastName";
    private static final String COLUMN_MEMBER_CONTACT = "ContactInfo";
    private static final String COLUMN_MEMBER_IMAGE = "Image";

    // Create Member Table
    private static final String CREATE_MEMBER_TABLE = "Create table "
            + MEMBERS_TABLE_NAME + " ("+ COLUMN_MEMBER_UID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_MEMBERGROUP_UID + " INTEGER,"
            + COLUMN_MEMBER_FIRSTNAME +" TEXT,"+ COLUMN_MEMBER_LASTNAME + " TEXT,"
            + COLUMN_MEMBER_CONTACT +" TEXT,"
            + COLUMN_MEMBER_IMAGE + " BLOB);";

    // Group Table Name
    private static final String GROUP_TABLE_NAME = "Groups";

    // Members Column names
    public static final String COLUMN_GROUP_UID = "UID";
    public static final String COLUMN_GROUP_NAME = "GroupName";
    public static final String COLUMN_GROUP_ADDRESS = "GroupAddress";
    public static final String COLUMN_GROUP_FO_ID = "FOId";
    public static final String COLUMN_GROUP_PRESIDENT_ID = "PresId";
    public static final String COLUMN_RECURRING_SAVING = "RecurringSaving";
    public static final String COLUMN_CREATED_DATETIME = "CreatedAt";
    public static final String COLUMN_CREATED_BY = "CreatedBy";

    private static final String CREATE_GROUP_TABLE="Create table "+GROUP_TABLE_NAME
            + " (" + COLUMN_GROUP_UID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_GROUP_NAME + " TEXT,"
            + COLUMN_GROUP_ADDRESS + " TEXT,"
            + COLUMN_GROUP_FO_ID + " INTEGER,"
            + COLUMN_GROUP_PRESIDENT_ID + " INTEGER,"
            + COLUMN_RECURRING_SAVING + " INTEGER,"
            + COLUMN_CREATED_DATETIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_CREATED_BY + " INTEGER"
            + ");";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + MEMBERS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + GROUP_TABLE_NAME + ";");
        // Create tables again
        db.execSQL(CREATE_MEMBER_TABLE);
        db.execSQL(CREATE_GROUP_TABLE);

    }

    public void recreateSchema()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + MEMBERS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + GROUP_TABLE_NAME + ";");
        // Create tables again
        db.execSQL(CREATE_MEMBER_TABLE);
        db.execSQL(CREATE_GROUP_TABLE);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {}

    public ArrayList<Member> getAllMembers(int groupUID)
    {
        ArrayList<Member> membersList = new ArrayList<Member>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MEMBERS_TABLE_NAME + " Where "+COLUMN_MEMBERGROUP_UID+"="+groupUID+";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Member member = new Member();
                member.UID=(Integer.parseInt(cursor.getString(0)));
                member.GroupUID = Integer.parseInt(cursor.getString(1));
                member.FirstName=cursor.getString(2);
                member.LastName=cursor.getString(3);
                member.ContactInfo=cursor.getString(4);
                // Adding contact to list
                membersList.add(member);
            } while (cursor.moveToNext());
        }

        // return contact list
        return membersList;
    }

    public void addUpdateMember(Member member)
    {
        if(member == null) return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(member.UID == 0)
        {
            values.put(COLUMN_MEMBER_FIRSTNAME, member.FirstName);
            values.put(COLUMN_MEMBER_LASTNAME, member.LastName);
            values.put(COLUMN_MEMBER_CONTACT, member.ContactInfo);
            values.put(COLUMN_MEMBERGROUP_UID, member.GroupUID);

            db.insertOrThrow(MEMBERS_TABLE_NAME, null, values);
        }
        else
        {
            values.put(COLUMN_MEMBER_FIRSTNAME, member.FirstName);
            values.put(COLUMN_MEMBER_LASTNAME, member.LastName);
            values.put(COLUMN_MEMBER_CONTACT, member.ContactInfo);

            db.update(MEMBERS_TABLE_NAME,values, COLUMN_MEMBER_UID +" = "+member.UID,null);
        }

        db.close();
    }

    public void deleteAllMembers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MEMBERS_TABLE_NAME,null,null);
        db.close();
    }

    public void deleteAllGroups()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GROUP_TABLE_NAME,null,null);
        db.close();
    }

    public void addUpdateGroup(Group group)
    {
        if(group == null) return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(group.UID == 0)
        {
            values.put(COLUMN_GROUP_NAME,group.GroupName);
            values.put(COLUMN_GROUP_ADDRESS,group.Address);
            values.put(COLUMN_GROUP_PRESIDENT_ID,group.PresidentId);
            values.put(COLUMN_GROUP_FO_ID,group.FOId);
            values.put(COLUMN_RECURRING_SAVING,group.RecurringSavings);
            values.put(COLUMN_CREATED_BY,group.CreatedBy);

            db.insertOrThrow(GROUP_TABLE_NAME,null,values);
        }
        else
        {
            values.put(COLUMN_GROUP_NAME,group.GroupName);
            values.put(COLUMN_GROUP_ADDRESS,group.Address);
            values.put(COLUMN_GROUP_PRESIDENT_ID,group.PresidentId);
            values.put(COLUMN_GROUP_FO_ID,group.FOId);
            values.put(COLUMN_CREATED_BY,group.CreatedBy);
            values.put(COLUMN_RECURRING_SAVING,group.RecurringSavings);

            db.update(GROUP_TABLE_NAME,values,COLUMN_GROUP_UID +" = "+group.UID,null);
        }

        db.close();
    }

    public ArrayList<Group> getAllGroups()
    {
        ArrayList<Group> groupList = new ArrayList<Group>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + GROUP_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Group group = new Group();
                group.UID=Integer.parseInt(cursor.getString(0));
                group.GroupName=cursor.getString(1);
                group.Address=cursor.getString(2);
                //group.FOId=Integer.parseInt(cursor.getString(3));
                //group.PresidentId = Integer.parseInt(cursor.getString(4));
                group.RecurringSavings = Integer.parseInt(cursor.getString(5));
                group.CreatedAt = cursor.getString(6);
                group.CreatedBy = Integer.parseInt(cursor.getString(7));
                // Adding contact to list
                groupList.add(group);
            } while (cursor.moveToNext());
        }

        // return contact list
        return groupList;
    }

    public Group getGroup(int groupUID)
    {
        ArrayList<Group> groupList = new ArrayList<Group>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + GROUP_TABLE_NAME + " Where "+COLUMN_GROUP_UID+"="+groupUID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Group group = null;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            group = new Group();
            group.UID=Integer.parseInt(cursor.getString(0));
            group.GroupName=cursor.getString(1);
            group.Address=cursor.getString(2);
            //group.FOId=Integer.parseInt(cursor.getString(3));
            //group.PresidentId = Integer.parseInt(cursor.getString(4));
            group.RecurringSavings = Integer.parseInt(cursor.getString(5));
            group.CreatedAt = cursor.getString(6);
            //group.CreatedBy = Integer.parseInt(cursor.getString(7));
        }

        return group;
    }
}
