package org.groupsavings.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.groupsavings.domain.Group;
import org.groupsavings.domain.GroupMeeting;
import org.groupsavings.domain.LoanAccount;
import org.groupsavings.domain.MeetingTransaction;
import org.groupsavings.domain.Member;
import org.groupsavings.domain.SavingTransaction;
import org.groupsavings.domain.SavingsAccount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shashank on 4/3/14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GroupSavings";
    private static final String TABLE_GROUP = "Groups";
    public static final String COLUMN_GROUP_HashId = "HashId";
    public static final String COLUMN_GROUP_Name = "Name";
    public static final String COLUMN_GROUP_PresidentId = "PresidentId";
    public static final String COLUMN_GROUP_SecretaryId = "SecretaryId";
    public static final String COLUMN_GROUP_TreasurerId = "TreasurerId";
    public static final String COLUMN_GROUP_FieldOfficerId = "FieldOfficerId";
    public static final String COLUMN_GROUP_CreatedDate = "CreatedDate";
    public static final String COLUMN_GROUP_CreatedBy = "CreatedBy";
    public static final String COLUMN_GROUP_Active = "Active";
    public static final String COLUMN_GROUP_RecurringIndividualAmount = "RecurringIndividualAmount";
    public static final String COLUMN_GROUP_MonthlyMeetingDate = "MonthlyMeetingDate";
    public static final String COLUMN_GROUP_ClusterId = "ClusterId";
    public static final String COLUMN_GROUP_AddressLine1 = "AddressLine1";
    public static final String COLUMN_GROUP_AddressLine2 = "AddressLine2";
    public static final String COLUMN_GROUP_City = "City";
    public static final String COLUMN_GROUP_State = "State";
    public static final String COLUMN_GROUP_Country = "Country";
    public static final String COLUMN_GROUP_BankAccount = "BankAccount";
    private static final String CREATE_GROUP_TABLE = "Create table " + TABLE_GROUP
            + " (" + COLUMN_GROUP_HashId + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_GROUP_Name + " TEXT,"
            + COLUMN_GROUP_PresidentId + " TEXT,"
            + COLUMN_GROUP_SecretaryId + " TEXT,"
            + COLUMN_GROUP_TreasurerId + " TEXT,"
            + COLUMN_GROUP_FieldOfficerId + " TEXT,"
            + COLUMN_GROUP_CreatedDate + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_GROUP_CreatedBy + " TEXT,"
            + COLUMN_GROUP_Active + " BOOLEAN,"
            + COLUMN_GROUP_RecurringIndividualAmount + " INTEGER,"
            + COLUMN_GROUP_MonthlyMeetingDate + " TIMESTAMP,"
            + COLUMN_GROUP_ClusterId + " INTEGER,"
            + COLUMN_GROUP_AddressLine1 + " TEXT,"
            + COLUMN_GROUP_AddressLine2 + " TEXT,"
            + COLUMN_GROUP_City + " TEXT,"
            + COLUMN_GROUP_State + " TEXT,"
            + COLUMN_GROUP_Country + " TEXT,"
            + COLUMN_GROUP_BankAccount + " TEXT"
            + ");";

    private static final String TABLE_MEMBER = "Members";
    public static final String COLUMN_MEMBER_UID = "UID";
    public static final String COLUMN_MEMBER_GroupUID = "GroupUID";
    public static final String COLUMN_MEMBER_FirstName = "FirstName";
    public static final String COLUMN_MEMBER_LastName = "LastName";
    public static final String COLUMN_MEMBER_Sex = "Sex";
    public static final String COLUMN_MEMBER_DOB = "DOB";
    public static final String COLUMN_MEMBER_EmailId = "EmailId";
    public static final String COLUMN_MEMBER_Active = "Active";
    public static final String COLUMN_MEMBER_TotalSavings = "TotalSavings";
    public static final String COLUMN_MEMBER_TotalDebt = "TotalDebt";
    public static final String COLUMN_MEMBER_CreatedDate = "CreatedDate";
    public static final String COLUMN_MEMBER_CreatedBy = "CreatedBy";
    public static final String COLUMN_MEMBER_ContactNumber = "ContactNumber";
    public static final String COLUMN_MEMBER_AddressLine1 = "AddressLine1";
    public static final String COLUMN_MEMBER_AddressLine2 = "AddressLine2";
    public static final String COLUMN_MEMBER_City = "City";
    public static final String COLUMN_MEMBER_State = "State";
    public static final String COLUMN_MEMBER_Country = "Country";
    private static final String CREATE_MEMBER_TABLE = "Create table " + TABLE_MEMBER
            + " (" + COLUMN_MEMBER_UID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_MEMBER_GroupUID + " INTEGER,"
            + COLUMN_MEMBER_FirstName + " TEXT,"
            + COLUMN_MEMBER_LastName + " TEXT,"
            + COLUMN_MEMBER_Sex + " TEXT,"
            + COLUMN_MEMBER_DOB + " TEXT,"
            + COLUMN_MEMBER_EmailId + " TEXT,"
            + COLUMN_MEMBER_Active + " TEXT,"
            + COLUMN_MEMBER_TotalSavings + " INTEGER,"
            + COLUMN_MEMBER_TotalDebt + " INTEGER,"
            + COLUMN_MEMBER_CreatedDate + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_MEMBER_CreatedBy + " TEXT,"
            + COLUMN_MEMBER_ContactNumber + " TEXT,"
            + COLUMN_MEMBER_AddressLine1 + " TEXT,"
            + COLUMN_MEMBER_AddressLine2 + " TEXT,"
            + COLUMN_MEMBER_City + " TEXT,"
            + COLUMN_MEMBER_State + " TEXT,"
            + COLUMN_MEMBER_Country + " TEXT"
            + ");";

    public static final String TABLE_SAVINGSACCOUNT = "GroupMemberSavingAccount";
    public static final String COLUMN_SAVING_ACCOUNT_Id = "Id";
    public static final String COLUMN_SAVING_ACCOUNT_GroupId = "GroupId";
    public static final String COLUMN_SAVING_ACCOUNT_MemberId = "MemberId";
    public static final String COLUMN_SAVING_ACCOUNT_TotalSaving = "TotalSaving";
    public static final String COLUMN_SAVING_ACCOUNT_InterestAccumulated = "InterestAccumulated";
    public static final String COLUMN_SAVING_ACCOUNT_CreatedDate = "CreatedDate";
    private static final String CREATE_SAVINGS_TABLE = "Create table " + TABLE_SAVINGSACCOUNT
            + " (" + COLUMN_SAVING_ACCOUNT_Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_SAVING_ACCOUNT_GroupId + " INTEGER,"
            + COLUMN_SAVING_ACCOUNT_MemberId + " INTEGER,"
            + COLUMN_SAVING_ACCOUNT_TotalSaving + " INTEGER,"
            + COLUMN_SAVING_ACCOUNT_InterestAccumulated + " INTEGER,"
            + COLUMN_SAVING_ACCOUNT_CreatedDate + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            + ");";
    public static final String TABLE_SAVINGTRANSACTION = "GroupMemberSavingTransaction";
    public static final String COLUMN_SAVINGTRANSACTION_Id = "Id";
    public static final String COLUMN_SAVINGTRANSACTION_GroupMeetingId = "GroupMeetingId";
    public static final String COLUMN_SAVINGTRANSACTION_GroupMemberSavingId = "GroupMemberSavingId";
    public static final String COLUMN_SAVINGTRANSACTION_Amount = "Amount";
    public static final String COLUMN_SAVINGTRANSACTION_DateTime = "DateTime";
    public static final String COLUMN_SAVINGTRANSACTION_SignedBy = "SignedBy";
    private static final String CREATE_SAVINGTRANSACTION_TABLE = "Create table " + TABLE_SAVINGTRANSACTION
            + " (" + COLUMN_SAVINGTRANSACTION_Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_SAVINGTRANSACTION_GroupMeetingId + " INTEGER,"
            + COLUMN_SAVINGTRANSACTION_GroupMemberSavingId + " INTEGER,"
            + COLUMN_SAVINGTRANSACTION_Amount + " INTEGER,"
            + COLUMN_SAVINGTRANSACTION_DateTime + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_SAVINGTRANSACTION_SignedBy + " TEXT"
            + ");";
    public static final String TABLE_LOANTRANSACTION = "GroupMemberLoanTransaction";
    public static final String COLUMN_LOAN_TRANSACTION_Id = "Id";
    public static final String COLUMN_LOAN_TRANSACTION_GroupMeetingId = "GroupMeetingId";
    public static final String COLUMN_LOAN_TRANSACTION_GroupMemberLoanId = "GroupMemberLoanId";
    public static final String COLUMN_LOAN_TRANSACTION_Repayment = "Repayment";
    public static final String COLUMN_LOAN_TRANSACTION_DateTime = "DateTime";
    public static final String COLUMN_LOAN_TRANSACTION_SignedBy = "SignedBy";
    private static final String CREATE_LOANTRANSACTION_TABLE = "Create table " + TABLE_LOANTRANSACTION
            + " (" + COLUMN_LOAN_TRANSACTION_Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_LOAN_TRANSACTION_GroupMeetingId + " INTEGER,"
            + COLUMN_LOAN_TRANSACTION_GroupMemberLoanId + " INTEGER,"
            + COLUMN_LOAN_TRANSACTION_Repayment + " INTEGER,"
            + COLUMN_LOAN_TRANSACTION_DateTime + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_LOAN_TRANSACTION_SignedBy + " TEXT"
            + ");";
    public static final String TABLE_GROUPMEETINGS = "GroupMeetings";
    public static final String COLUMN_GROUPMEETING_ID = "Id";
    public static final String COLUMN_GROUPMEETING_GroupId = "GroupId";
    public static final String COLUMN_GROUPMEETING_DATE = "DateTime";
    private static final String CREATE_GROUPMEETING_TABLE = "Create table " + TABLE_GROUPMEETINGS
            + " (" + COLUMN_GROUPMEETING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_GROUPMEETING_GroupId + " INTEGER,"
            + COLUMN_GROUPMEETING_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            + ");";


    private static final String TABLE_LOANSACCOUNT = "GroupMemberLoanAccount";
    public static final String COLUMN_LOANACCOUNT_Id = "Id";
    public static final String COLUMN_LOANACCOUNT_GroupId = "GroupId";
    public static final String COLUMN_LOANACCOUNT_MemberId = "MemberId";
    public static final String COLUMN_LOANACCOUNT_PrincipalAmount = "PrincipalAmount";
    public static final String COLUMN_LOANACCOUNT_InterestRate = "InterestRate";
    public static final String COLUMN_LOANACCOUNT_StartDate = "StartDate";
    public static final String COLUMN_LOANACCOUNT_EndDate = "EndDate";
    public static final String COLUMN_LOANACCOUNT_Outstanding = "Outstanding";
    public static final String COLUMN_LOANACCOUNT_Reason = "Reason";
    public static final String COLUMN_LOANACCOUNT_InstallmentAmount = "InstallmentAmount";
    public static final String COLUMN_LOANACCOUNT_NoOfInstallments = "NoOfInstallments";
    public static final String COLUMN_LOANACCOUNT_CreatedDate = "CreatedDate";
    public static final String COLUMN_LOANACCOUNT_IsActive = "IsActive";
    private static final String CREATE_LOANS_TABLE = "Create table " + TABLE_LOANSACCOUNT
            + " (" + COLUMN_LOANACCOUNT_Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_LOANACCOUNT_GroupId + " INTEGER,"
            + COLUMN_LOANACCOUNT_MemberId + " INTEGER,"
            + COLUMN_LOANACCOUNT_PrincipalAmount + " INTEGER,"
            + COLUMN_LOANACCOUNT_InterestRate + " INTEGER,"
            + COLUMN_LOANACCOUNT_StartDate + " TIMESTAMP,"
            + COLUMN_LOANACCOUNT_EndDate + " TIMESTAMP,"
            + COLUMN_LOANACCOUNT_Outstanding + " INTEGER,"
            + COLUMN_LOANACCOUNT_Reason + " TEXT,"
            + COLUMN_LOANACCOUNT_InstallmentAmount + " INTEGER,"
            + COLUMN_LOANACCOUNT_NoOfInstallments + " INTEGER,"
            + COLUMN_LOANACCOUNT_IsActive +" BOOLEAN,"
            + COLUMN_LOANACCOUNT_CreatedDate + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            + ");";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createSchema(db);
    }

    private void dropSchema(SQLiteDatabase db) {
        if (db == null) db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOANTRANSACTION + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVINGTRANSACTION + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOANSACCOUNT + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVINGSACCOUNT + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPMEETINGS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP + ";");
    }

    public void createSchema(SQLiteDatabase db) {
        if (db == null) db = this.getWritableDatabase();

        dropSchema(db);

        db.execSQL(CREATE_GROUP_TABLE);
        db.execSQL(CREATE_MEMBER_TABLE);
        db.execSQL(CREATE_SAVINGS_TABLE);
        db.execSQL(CREATE_SAVINGTRANSACTION_TABLE);
        db.execSQL(CREATE_LOANS_TABLE);
        db.execSQL(CREATE_LOANTRANSACTION_TABLE);
        db.execSQL(CREATE_GROUPMEETING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
    }

    //------------------------ Group related functions ----------------------------//
    //-----------------------------------------------------------------------------//
    public void addUpdateGroup(Group group) {
        if (group == null) return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_GROUP_Name, group.GroupName);
        values.put(COLUMN_GROUP_AddressLine1, group.AddressLine1);
        values.put(COLUMN_GROUP_AddressLine2, group.AddressLine2);
        values.put(COLUMN_GROUP_PresidentId, group.PresidentId);
        values.put(COLUMN_GROUP_FieldOfficerId, group.FOId);
        values.put(COLUMN_GROUP_RecurringIndividualAmount, group.RecurringSavings);
        values.put(COLUMN_GROUP_BankAccount, group.BankAccount);

        if (group.UID == 0) {
            // TODO: get field officer Id from security
            values.put(COLUMN_GROUP_CreatedBy, group.CreatedBy);
            db.insertOrThrow(TABLE_GROUP, null, values);
        } else {
            db.update(TABLE_GROUP, values, COLUMN_GROUP_HashId + " = " + group.UID, null);
        }

        db.close();
    }

    public ArrayList<Group> getAllGroups() {
        ArrayList<Group> groupList = new ArrayList<Group>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_GROUP;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Group group = new Group();
                group.UID = cursor.getInt(0);
                group.GroupName = cursor.getString(1);
                group.CreatedAt = cursor.getString(6);
                group.CreatedBy = cursor.getInt(7);
                group.RecurringSavings = cursor.getInt(9);
                group.AddressLine1 = cursor.getString(12);
                group.AddressLine2 = cursor.getString(13);
                groupList.add(group);
            } while (cursor.moveToNext());
        }

        // return contact list
        return groupList;
    }

    public Group getGroup(int groupUID) {
        ArrayList<Group> groupList = new ArrayList<Group>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_GROUP + " Where " + COLUMN_GROUP_HashId + "=" + groupUID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Group group = null;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            group = new Group();
            group.UID = cursor.getInt(0);
            group.GroupName = cursor.getString(1);
            group.AddressLine1 = cursor.getString(12);
            group.AddressLine2 = cursor.getString(13);
            //group.FOId=Integer.parseInt(cursor.getString(3));
            //group.PresidentId = Integer.parseInt(cursor.getString(4));
            group.RecurringSavings = cursor.getInt(9);
            group.BankAccount = cursor.getString(17);
            group.CreatedAt = cursor.getString(6);
            //group.CreatedBy = Integer.parseInt(cursor.getString(7));
        }

        return group;
    }

    public void deleteAllGroups() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GROUP, null, null);
        db.close();
    }

    //------------------------ Member related functions ----------------------------//
    //------------------------------------------------------------------------------//
    public ArrayList<Member> getAllMembers(int groupUID) {
        ArrayList<Member> membersList = new ArrayList<Member>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEMBER
                + " Where " + COLUMN_MEMBER_GroupUID + "=" + groupUID + ";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Member member = new Member();
                member.UID = cursor.getInt(0);
                member.GroupUID = cursor.getInt(1);
                member.FirstName = cursor.getString(2);
                member.LastName = cursor.getString(3);
                member.ContactInfo = cursor.getString(12);
                member.DOB = cursor.getString(5);
                member.TotalSavings = getMemberSavings(member.UID, db);
                member.AddressLine1 = cursor.getString(13);
                member.AddressLine2 = cursor.getString(14);
                // Adding contact to list
                membersList.add(member);
            } while (cursor.moveToNext());
        }

        // return contact list
        return membersList;
    }

    public ArrayList<Member> getAllMembers() {
        ArrayList<Member> membersList = new ArrayList<Member>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEMBER + ";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Member member = new Member();
                member.UID = cursor.getInt(0);
                member.GroupUID = cursor.getInt(1);
                member.FirstName = cursor.getString(2);
                member.LastName = cursor.getString(3);
                member.ContactInfo = cursor.getString(12);
                member.DOB = cursor.getString(5);
                member.AddressLine1 = cursor.getString(13);
                member.AddressLine2 = cursor.getString(14);
                member.TotalSavings = getMemberSavings(member.UID, db);
                // Adding contact to list
                membersList.add(member);
            } while (cursor.moveToNext());
        }

        // return contact list
        return membersList;
    }

    public void addUpdateMember(Member member) {
        if (member == null) return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (member.UID == 0) {
            values.put(COLUMN_MEMBER_FirstName, member.FirstName);
            values.put(COLUMN_MEMBER_LastName, member.LastName);
            values.put(COLUMN_MEMBER_ContactNumber, member.ContactInfo);
            values.put(COLUMN_MEMBER_GroupUID, member.GroupUID);
            values.put(COLUMN_MEMBER_DOB, member.DOB);
            values.put(COLUMN_MEMBER_AddressLine1, member.AddressLine1);
            values.put(COLUMN_MEMBER_AddressLine2, member.AddressLine2);

            long memberId = db.insertOrThrow(TABLE_MEMBER, null, values);
            member.UID = (int) memberId;
            createMemberAccounts(member, db);
        } else {
            values.put(COLUMN_MEMBER_ContactNumber, member.ContactInfo);
            values.put(COLUMN_MEMBER_AddressLine1, member.AddressLine1);
            values.put(COLUMN_MEMBER_AddressLine2, member.AddressLine2);
            db.update(TABLE_MEMBER, values, COLUMN_MEMBER_UID + " = " + member.UID, null);
        }

        db.close();
    }

    private void createMemberAccounts(Member member, SQLiteDatabase db) {
        ContentValues saving_acc_values = new ContentValues();

        saving_acc_values.put(COLUMN_SAVING_ACCOUNT_GroupId, member.GroupUID);
        saving_acc_values.put(COLUMN_SAVING_ACCOUNT_MemberId, member.UID);
        saving_acc_values.put(COLUMN_SAVING_ACCOUNT_TotalSaving, 0);

        db.insertOrThrow(TABLE_SAVINGSACCOUNT, null, saving_acc_values);
    }

    private int getMemberSavings(int memberId, SQLiteDatabase db) {
        if (db == null) {
            db = this.getWritableDatabase();
        }

        String selectQuery = "SELECT " + COLUMN_SAVING_ACCOUNT_TotalSaving + " FROM " + TABLE_SAVINGSACCOUNT
                + " Where " + COLUMN_SAVING_ACCOUNT_MemberId + "=" + memberId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        int savings = 0;

        if (cursor.moveToFirst()) {
            savings = cursor.getInt(0);
        }

        return savings;
    }

    private SavingsAccount getMemberSavingAccount(int memberId, SQLiteDatabase db) {
        if (db == null) {
            db = this.getWritableDatabase();
        }

        String selectQuery = "SELECT * FROM " + TABLE_SAVINGSACCOUNT
                + " Where " + COLUMN_SAVING_ACCOUNT_MemberId + "=" + memberId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        SavingsAccount account = new SavingsAccount();

        if (cursor.moveToFirst()) {
            account.Id = cursor.getInt(0);
            account.groupId = cursor.getInt(1);
            account.memberId = cursor.getInt(2);
            account.TotalSavings = cursor.getInt(3);
        }

        return account;
    }

    public void deleteAllMembers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMBER, null, null);
        db.close();
    }

    //-------------------------- Meeting related functions ----------------------------//
    //-------------------------------------------------------------------------------//

    public void saveMeetingDetails(int groupId, ArrayList<MeetingTransaction> transactions) {
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date currentDate = new Date();
        long meetingId = createMeeting(groupId, currentDate, db);
        String dateTime = sdf.format(new Date());

        for (MeetingTransaction transaction : transactions) {
            // Get Savings account
            SavingsAccount savingsAccount = getMemberSavingAccount(transaction.member.UID, db);
            // calculate updated total savings
            int updatedTotalSavings = savingsAccount.TotalSavings + transaction.getTotalSavings();

            // insert transaction
            ContentValues savingtransaction = new ContentValues();
            savingtransaction.put(COLUMN_SAVINGTRANSACTION_GroupMemberSavingId, savingsAccount.Id);
            savingtransaction.put(COLUMN_SAVINGTRANSACTION_GroupMeetingId, meetingId);
            //savingtransaction.put(COLUM);
            savingtransaction.put(COLUMN_SAVINGTRANSACTION_Amount, transaction.getTotalSavings());
            savingtransaction.put(COLUMN_SAVINGTRANSACTION_DateTime, dateTime);
            db.insertOrThrow(TABLE_SAVINGTRANSACTION, null, savingtransaction);

            // update savings account
            ContentValues savingAccountUpdate = new ContentValues();
            savingAccountUpdate.put(COLUMN_SAVING_ACCOUNT_TotalSaving, updatedTotalSavings);
            db.update(TABLE_SAVINGSACCOUNT, savingAccountUpdate,
                    COLUMN_SAVING_ACCOUNT_Id + "=" + savingsAccount.Id, null);
        }
    }

    // Function that will create a meeting record and return meeting id
    private long createMeeting(int groupId, Date date, SQLiteDatabase db) {
        if (db == null) db = this.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String dateTime = sdf.format(date);

        ContentValues meetingValues = new ContentValues();
        meetingValues.put(COLUMN_GROUPMEETING_GroupId, groupId);
        meetingValues.put(COLUMN_GROUPMEETING_DATE, dateTime);

        return db.insertOrThrow(TABLE_GROUPMEETINGS, null, meetingValues);
    }

    public ArrayList<GroupMeeting> getAllGroupMeetings(int groupId, SQLiteDatabase db) throws ParseException {

        ArrayList<GroupMeeting> resultset = new ArrayList<GroupMeeting>();
        String selectQuery = "SELECT * FROM " + TABLE_GROUPMEETINGS
                + " WHERE " + COLUMN_GROUPMEETING_GroupId + "=" + groupId;
        if (db == null) db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                GroupMeeting gm = new GroupMeeting();
                gm.id = cursor.getInt(0);
                gm.groupId = cursor.getInt(1);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                gm.date = sdf.parse(cursor.getString(2));
                resultset.add(gm);
            } while (cursor.moveToNext());
        }

        return resultset;
    }

    //-------------------------- Transaction related functions ---------------//
    //-------------------------------------------------------------------------------//
    public void addUpdateLoanAccount(LoanAccount la)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues loanAccValues = new ContentValues();
        loanAccValues.put(COLUMN_LOANACCOUNT_GroupId,la.groupId);
        loanAccValues.put(COLUMN_LOANACCOUNT_MemberId, la.member.UID);
        loanAccValues.put(COLUMN_LOANACCOUNT_PrincipalAmount, la.Principal);
        loanAccValues.put(COLUMN_LOANACCOUNT_InterestRate, la.InterestPerAnnum);
        loanAccValues.put(COLUMN_LOANACCOUNT_NoOfInstallments, la.periodInMonths);
        loanAccValues.put(COLUMN_LOANACCOUNT_InstallmentAmount, la.getEMI());

        if(la.Id == 0)
        {
            db.insertOrThrow(TABLE_LOANSACCOUNT,null,loanAccValues);
        }
        else
        {

        }
    }


    public ArrayList<SavingsAccount> getAllSavingAccounts() {
        ArrayList<SavingsAccount> allAccounts = new ArrayList<SavingsAccount>();
        String selectQuery = "SELECT * FROM " + TABLE_SAVINGSACCOUNT + ";";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SavingsAccount sa = new SavingsAccount();
                sa.Id = cursor.getInt(0);
                sa.groupId = cursor.getInt(1);
                sa.memberId = cursor.getInt(2);
                sa.TotalSavings = cursor.getInt(3);
                allAccounts.add(sa);
            } while (cursor.moveToNext());
        }

        return allAccounts;
    }

    public ArrayList<SavingTransaction> getAllSavingTrans() {
        ArrayList<SavingTransaction> allSavingTrans = new ArrayList<SavingTransaction>();
        String selectQuery = "SELECT * FROM " + TABLE_SAVINGTRANSACTION + ";";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SavingTransaction st = new SavingTransaction();
                st.Id = cursor.getInt(0);
                st.grpMeetingId = cursor.getInt(1);
                st.memberSavingAccId = cursor.getInt(2);
                st.Amount = cursor.getInt(3);
                allSavingTrans.add(st);
            } while (cursor.moveToNext());
        }

        return allSavingTrans;
    }
}