package org.groupsavings.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.groupsavings.domain.Group;
import org.groupsavings.domain.GroupMeeting;
import org.groupsavings.domain.Member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by shashank on 4/3/14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String COLUMN_GROUP_UID = "UID";
    public static final String COLUMN_GROUP_NAME = "GroupName";
    public static final String COLUMN_GROUP_ADDRESS = "GroupAddress";
    public static final String COLUMN_GROUP_FO_ID = "FOId";
    public static final String COLUMN_GROUP_PRESIDENT_ID = "PresId";
    public static final String COLUMN_RECURRING_SAVING = "RecurringSaving";
    public static final String COLUMN_CREATED_DATETIME = "CreatedAt";
    public static final String COLUMN_CREATED_BY = "CreatedBy";
    // All Static variables
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GroupSavings";
    // Group Table Name
    private static final String TABLE_GROUP = "Groups";
    private static final String CREATE_TABLE_GROUP = "Create table " + TABLE_GROUP
            + " (" + COLUMN_GROUP_UID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_GROUP_NAME + " TEXT,"
            + COLUMN_GROUP_ADDRESS + " TEXT,"
            + COLUMN_GROUP_FO_ID + " INTEGER,"
            + COLUMN_GROUP_PRESIDENT_ID + " INTEGER,"
            + COLUMN_RECURRING_SAVING + " INTEGER,"
            + COLUMN_CREATED_DATETIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_CREATED_BY + " INTEGER"
            + ");";

    // Members Table Name
    private static final String TABLE_MEMBERS = "Members";

    private static final String COLUMN_MEMBER_UID = "UID";
    private static final String COLUMN_MEMBERGROUP_UID = "GroupUID";
    private static final String COLUMN_MEMBER_FIRSTNAME = "FirstName";
    private static final String COLUMN_MEMBER_LASTNAME = "LastName";
    private static final String COLUMN_MEMBER_CONTACT = "ContactInfo";
    private static final String COLUMN_MEMBER_IMAGE = "Image";

    private static final String CREATE_MEMBER_TABLE = "Create table "
            + TABLE_MEMBERS + " ("+ COLUMN_MEMBER_UID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_MEMBERGROUP_UID + " INTEGER,"
            + COLUMN_MEMBER_FIRSTNAME +" TEXT,"+ COLUMN_MEMBER_LASTNAME + " TEXT,"
            + COLUMN_MEMBER_CONTACT +" TEXT,"
            + COLUMN_MEMBER_IMAGE + " BLOB);";

    //Savings table
    private static final String TABLE_SAVINGSACCOUNT = "MemberSavingsAccount";

    private static final String COLUMN_SAVINGSACCOUNT_ID = "Id";
    private static final String COLUMN_SAVINGSACCOUNT_GROUPID= "GroupId";
    private static final String COLUMN_SAVINGSACCOUNT_MEMBERID = "MemberId";
    private static final String COLUMN_SAVINGSACCOUNT_TOTALSAVINGS = "TotalSavings";
    private static final String COLUMN_SAVINGSACCOUNT_INTERESTACCUMULATED = "InterestAccumulated";
    private static final String COLUMN_SAVINGSACCOUNT_CREATEDDATE = "CreatedDate";

    private static final String CREATE_SAVINGS_TABLE="Create table "+TABLE_SAVINGSACCOUNT
            + " (" + COLUMN_SAVINGSACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_SAVINGSACCOUNT_GROUPID + " INTEGER,"
            + COLUMN_SAVINGSACCOUNT_MEMBERID + " INTEGER,"
            + COLUMN_SAVINGSACCOUNT_TOTALSAVINGS + " INTEGER,"
            + COLUMN_SAVINGSACCOUNT_INTERESTACCUMULATED + " INTEGER,"
            + COLUMN_SAVINGSACCOUNT_CREATEDDATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            + ");";

    //Savings Transaction
    private static final String TABLE_SAVINGTRANSACTION = "MemberSavingTransaction";

    private static final String COLUMN_SAVINGTRANSACTION_ID = "Id";
    private static final String COLUMN_SAVINGTRANSACTION_MEETINGID = "MeetingId";
    private static final String COLUMN_SAVINGTRANSACTION_MEMBERSAVINGSACCOUNTID = "MemberSavingsId";
    private static final String COLUMN_SAVINGTRANSACTION_AMOUNT = "Amount";
    private static final String COLUMN_SAVINGTRANSACTION_DATETIME = "DateTime";

    private static final String CREATE_SAVINGTRANSACTION_TABLE="Create table "+TABLE_SAVINGTRANSACTION
            + " (" + COLUMN_SAVINGTRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_SAVINGTRANSACTION_MEETINGID + " INTEGER,"
            + COLUMN_SAVINGTRANSACTION_MEMBERSAVINGSACCOUNTID + " INTEGER,"
            + COLUMN_SAVINGTRANSACTION_AMOUNT + " INTEGER,"
            + COLUMN_SAVINGTRANSACTION_DATETIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            + ");";

    //Loans table
    private static final String TABLE_LOANSACCOUNT = "GroupSavingsAccount";

    private static final String COLUMN_LOANSACCOUNT_ID = "Id";
    private static final String COLUMN_LOANSACCOUNT_GROUPID= "GroupId";
    private static final String COLUMN_LOANSACCOUNT_MEMBERID = "MemberId";
    private static final String COLUMN_LOANSACCOUNT_PRINCIPAL = "Principal";
    private static final String COLUMN_LOANSACCOUNT_STARTDATE = "StartDate";
    private static final String COLUMN_LOANSACCOUNT_INSTALLMENT = "InstallmentAmount";
    private static final String COLUMN_LOANSACCOUNT_NOofINSTALLMENTS = "NoOfInstallments";
    private static final String COLUMN_LOANSACCOUNT_CREATEDDATE = "CreatedDate";

    private static final String CREATE_LOANS_TABLE="Create table "+TABLE_LOANSACCOUNT
            + " (" + COLUMN_LOANSACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_LOANSACCOUNT_GROUPID + " INTEGER,"
            + COLUMN_LOANSACCOUNT_MEMBERID + " INTEGER,"
            + COLUMN_LOANSACCOUNT_PRINCIPAL + " INTEGER,"
            + COLUMN_LOANSACCOUNT_STARTDATE + " TIMESTAMP,"
            + COLUMN_LOANSACCOUNT_INSTALLMENT + " INTEGER,"
            + COLUMN_LOANSACCOUNT_NOofINSTALLMENTS + " INTEGER,"
            + COLUMN_LOANSACCOUNT_CREATEDDATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            + ");";

    //Loans Transaction
    private static final String TABLE_LOANTRANSACTION = "MemberLoanTransaction";

    private static final String COLUMN_LOANTRANSACTION_ID = "Id";
    private static final String COLUMN_LOANTRANSACTION_MEETINGID= "GroupMeetingId";
    private static final String COLUMN_LOANTRANSACTION_MEMBERLOANACCOUNTID = "MemberLoanId";
    private static final String COLUMN_LOANTRANSACTION_REPAYED = "Repayment";
    private static final String COLUMN_LOANTRANSACTION_DATETIME = "DateTime";

    private static final String CREATE_LOANTRANSACTION_TABLE="Create table "+TABLE_LOANTRANSACTION
            + " (" + COLUMN_LOANTRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_LOANTRANSACTION_MEETINGID + " INTEGER,"
            + COLUMN_LOANTRANSACTION_MEMBERLOANACCOUNTID + " INTEGER,"
            + COLUMN_LOANTRANSACTION_REPAYED + " INTEGER,"
            + COLUMN_LOANTRANSACTION_DATETIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            + ");";

    // Group Meeting table
    private static final String TABLE_GROUPMEETINGS = "GroupMeetings";

    private static final String COLUMN_GROUPMEETING_ID = "Id";
    private static final String COLUMN_GROUPMEETING_GroupId = "GroupId";
    private static final String COLUMN_GROUPMEETING_DATE = "DateTime";

    private static final String CREATE_GROUPMEETING_TABLE = "Create table " + TABLE_GROUPMEETINGS
            + " (" + COLUMN_GROUPMEETING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_GROUPMEETING_GroupId + " INTEGER,"
            + COLUMN_GROUPMEETING_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            + ");";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createSchema(db);
    }

    public void dropSchema(SQLiteDatabase db)
    {

        if (db == null) db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOANTRANSACTION + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVINGTRANSACTION + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOANSACCOUNT + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVINGSACCOUNT + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPMEETINGS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP + ";");

    }

    public void createSchema(SQLiteDatabase db)
    {
        if (db == null) db = this.getWritableDatabase();

        dropSchema(db);

        db.execSQL(CREATE_TABLE_GROUP);
        db.execSQL(CREATE_MEMBER_TABLE);
        db.execSQL(CREATE_SAVINGS_TABLE);
        db.execSQL(CREATE_SAVINGTRANSACTION_TABLE);
        db.execSQL(CREATE_LOANS_TABLE);
        db.execSQL(CREATE_LOANTRANSACTION_TABLE);
        db.execSQL(CREATE_GROUPMEETING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {}

    public ArrayList<Member> getAllMembers(int groupUID)
    {
        ArrayList<Member> membersList = new ArrayList<Member>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEMBERS + " Where "+COLUMN_MEMBERGROUP_UID+"="+groupUID+";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Member member = new Member();
                member.UID = cursor.getInt(0);
                member.GroupUID = cursor.getInt(1);
                member.FirstName=cursor.getString(2);
                member.LastName=cursor.getString(3);
                member.ContactInfo=cursor.getString(4);
                member.TotalSavings = getMemberSavings(member.UID, db);
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

            long memberId= db.insertOrThrow(TABLE_MEMBERS, null, values);
            member.UID = (int) memberId;
            // First time while adding a member, create accounts
            createMemberAccounts(member,db);
        }
        else
        {
            values.put(COLUMN_MEMBER_FIRSTNAME, member.FirstName);
            values.put(COLUMN_MEMBER_LASTNAME, member.LastName);
            values.put(COLUMN_MEMBER_CONTACT, member.ContactInfo);

            db.update(TABLE_MEMBERS,values, COLUMN_MEMBER_UID +" = "+member.UID,null);
        }

        db.close();
    }

    private void createMemberAccounts(Member member, SQLiteDatabase db)
    {
        ContentValues saving_acc_values = new ContentValues();

        saving_acc_values.put(COLUMN_SAVINGSACCOUNT_GROUPID,member.GroupUID);
        saving_acc_values.put(COLUMN_SAVINGSACCOUNT_MEMBERID, member.UID);
        saving_acc_values.put(COLUMN_SAVINGSACCOUNT_TOTALSAVINGS, 0);

        db.insertOrThrow(TABLE_SAVINGSACCOUNT,null,saving_acc_values);

        ContentValues loan_acc_values = new ContentValues();

        loan_acc_values.put(COLUMN_LOANSACCOUNT_GROUPID,member.GroupUID);
        loan_acc_values.put(COLUMN_LOANSACCOUNT_MEMBERID, member.UID);
        loan_acc_values.put(COLUMN_LOANSACCOUNT_PRINCIPAL, 0);

        db.insertOrThrow(TABLE_LOANSACCOUNT,null,loan_acc_values);

    }

    public void deleteAllMembers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMBERS,null,null);
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

            db.insertOrThrow(TABLE_GROUP, null, values);
        }
        else
        {
            values.put(COLUMN_GROUP_NAME,group.GroupName);
            values.put(COLUMN_GROUP_ADDRESS,group.Address);
            values.put(COLUMN_GROUP_PRESIDENT_ID,group.PresidentId);
            values.put(COLUMN_GROUP_FO_ID,group.FOId);
            values.put(COLUMN_CREATED_BY,group.CreatedBy);
            values.put(COLUMN_RECURRING_SAVING,group.RecurringSavings);

            db.update(TABLE_GROUP, values, COLUMN_GROUP_UID + " = " + group.UID, null);
        }

        db.close();
    }

    public ArrayList<Group> getAllGroups()
    {
        ArrayList<Group> groupList = new ArrayList<Group>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_GROUP;

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
        String selectQuery = "SELECT  * FROM " + TABLE_GROUP + " Where " + COLUMN_GROUP_UID + "=" + groupUID;

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

    public void deleteAllGroups()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GROUP, null, null);
        db.close();
    }

    // Account methods
    public int getMemberSavings(int memberId, SQLiteDatabase db) {
        int savings = 0;
        String selectQuery = "SELECT " + COLUMN_SAVINGSACCOUNT_TOTALSAVINGS
                + " FROM " + TABLE_SAVINGSACCOUNT
                + " WHERE " + COLUMN_SAVINGSACCOUNT_MEMBERID + "=" + memberId;
        if (db == null) db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            savings = cursor.getInt(0);
        }
        return savings;
    }

    //Meeting methods
    public ArrayList<GroupMeeting> getGroupMeetings(int groupId, SQLiteDatabase db) throws ParseException {

        ArrayList<GroupMeeting> resultset = new ArrayList<GroupMeeting>();
        String selectQuery = "SELECT * FROM " + TABLE_GROUPMEETINGS
                + " WHERE " + COLUMN_GROUPMEETING_GroupId + "=" + groupId;
        if (db == null) db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            GroupMeeting gm = new GroupMeeting();
            gm.id = cursor.getInt(0);
            gm.groupId = cursor.getInt(1);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/YYYY", Locale.getDefault());
            gm.date = sdf.parse(cursor.getString(2));
            resultset.add(gm);
        }

        return resultset;
    }
}
