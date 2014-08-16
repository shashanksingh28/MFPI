package org.groupsavings.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.groupsavings.StringHelper;
import org.groupsavings.constants.Columns;
import org.groupsavings.constants.Tables;
import org.groupsavings.domain.Group;
import org.groupsavings.domain.GroupMeeting;
import org.groupsavings.domain.LoanAccount;
import org.groupsavings.domain.LoanTransaction;
import org.groupsavings.domain.MeetingDetails;
import org.groupsavings.domain.MeetingLoanAccTransaction;
import org.groupsavings.domain.MeetingSavingsAccTransaction;
import org.groupsavings.domain.Member;
import org.groupsavings.domain.SavingTransaction;
import org.groupsavings.domain.SavingsAccount;

import java.util.ArrayList;
import java.util.Date;

import static org.groupsavings.constants.Tables.getTimestampUniqueId;
import static org.groupsavings.constants.Tables.getUniqueId;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GroupSavings";
    DatabaseFetchHelper fetchHelper;
    DatabasePutHelper putHelper;

    private static class Patch {
        public void apply(SQLiteDatabase db) {}

        public void revert(SQLiteDatabase db) {}
    }

    private static final Patch[] PATCHES = new Patch[] {
            new Patch() {
                public void apply(SQLiteDatabase db) {
                    db.execSQL(Tables.CREATE_TABLE_FIELDOFFICERS);
                    db.execSQL(Tables.CREATE_TABLE_Groups);
                    db.execSQL(Tables.CREATE_TABLE_GROUPMEETINGS);
                    db.execSQL(Tables.CREATE_TABLE_MEMBERS);
                    db.execSQL(Tables.CREATE_TABLE_MEETINGDETAILS);
                    db.execSQL(Tables.CREATE_TABLE_SAVINGACCOUNTS);
                    db.execSQL(Tables.CREATE_TABLE_SAVINGACCTRANSACTIONS);
                    db.execSQL(Tables.CREATE_TABLE_LOANACCOUNTS);
                    db.execSQL(Tables.CREATE_TABLE_LOANACCTRANSACTIONS);
                }

                public void revert(SQLiteDatabase db) {
                    //db.execSQL("drop table ...");
                }
            }
            , new Patch() {
        public void apply(SQLiteDatabase db) { /*...*/ }
        public void revert(SQLiteDatabase db) { /*...*/ }
    }
    };

    public DatabaseHandler(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        super(context, DATABASE_NAME, null, PATCHES.length); //1

        fetchHelper = new DatabaseFetchHelper();
        putHelper = new DatabasePutHelper();
    }

    public void onCreate(SQLiteDatabase db) {
//        createSchema(db);
        for (int i=0; i<PATCHES.length; i++) {
            PATCHES[i].apply(db);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i=oldVersion; i<newVersion; i++) {
            PATCHES[i].apply(db);
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i=oldVersion; i>newVersion; i++) {
            PATCHES[i-1].revert(db);
        }
    }

    // Use this only if needed for testing
    public void execQuery(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }

    public int checkIfExists(String sql) {

        String query = "Select Exists ( " + sql + ")";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return cursor.getInt(0);
        }
        return 0;
    }

    //Use only after database entries are available (user registration and sync in from server)
    //otherwise will cause the app to crash.
    public String getId(String name) {

        String query = "Select Id from FieldOfficers Where UserName='" + name + "'";

        try{
        if(checkIfExists(query) == 1) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                return cursor.getString(0);
            }
        }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return name;
    }


    // Use this only if data is corrupt and needs to be re-run
    public void recreateSchema()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE "+Tables.FIELDOFFICERS);
        db.execSQL("DROP TABLE "+Tables.LOANACCTRANSACTIONS);
        db.execSQL("DROP TABLE "+Tables.LOANACCOUNTS);
        db.execSQL("DROP TABLE "+Tables.SAVINGACCTRANSACTIONS);
        db.execSQL("DROP TABLE "+Tables.SAVINGACCOUNTS);
        db.execSQL("DROP TABLE "+Tables.MEETINGDETAILS);
        db.execSQL("DROP TABLE "+Tables.GROUPMEETINGS);
        db.execSQL("DROP TABLE "+Tables.MEMBERS);
        db.execSQL("DROP TABLE "+Tables.GROUPS);

        db.execSQL(Tables.CREATE_TABLE_FIELDOFFICERS);
        db.execSQL(Tables.CREATE_TABLE_Groups);
        db.execSQL(Tables.CREATE_TABLE_GROUPMEETINGS);
        db.execSQL(Tables.CREATE_TABLE_MEMBERS);
        db.execSQL(Tables.CREATE_TABLE_MEETINGDETAILS);
        db.execSQL(Tables.CREATE_TABLE_SAVINGACCOUNTS);
        db.execSQL(Tables.CREATE_TABLE_SAVINGACCTRANSACTIONS);
        db.execSQL(Tables.CREATE_TABLE_LOANACCOUNTS);
        db.execSQL(Tables.CREATE_TABLE_LOANACCTRANSACTIONS);
    }

    public void delete(String table, String whereClause)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table,whereClause,null);
    }

    public void addUpdateGroup(Group group) {
        if (group == null) return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        putHelper.putGroupValues(group, values);

        if (StringHelper.IsNullOrEmpty(group.Id))
        {
            group.Id = getUniqueId(group);
            values.put(Columns.GROUP_Id,group.Id);
            values.put(Columns.GROUP_CreatedBy, group.CreatedBy);
            db.insertOrThrow(Tables.GROUPS, null, values);
        }
        else
        {
            if(StringHelper.IsNullOrEmpty(group.ModifiedBy))
            {
                values.put(Columns.GROUP_ModifiedBy, group.ModifiedBy);
                values.put(Columns.GROUP_ModifiedDate, new Date().toString());
            }
            else
            {
                values.put(Columns.GROUP_ModifiedBy, group.ModifiedBy );
                values.put(Columns.GROUP_ModifiedDate, group.ModifiedDate);
            }

            db.update(Tables.GROUPS, values, Columns.GROUP_Id + " = '" + group.Id+"'", null);
        }

        db.close();
    }

    public void addSyncInGroup(Group group) {
        if (group == null) return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        putHelper.putGroupValues(group, values);

        if (getGroup(group.Id) == null)
        {
            values.put(Columns.GROUP_Id,group.Id);
            db.insertOrThrow(Tables.GROUPS, null, values);
        }
        else
        {
            db.update(Tables.GROUPS, values, Columns.GROUP_Id + " = '" + group.Id+"'", null);
        }
        db.close();
    }

    public ArrayList<Group> getAllFOGroups(String foUserName) {
        ArrayList<Group> groupList = new ArrayList<Group>();

        String selectQuery = "SELECT  * FROM " + Tables.GROUPS +
                " Where "+Columns.GROUP_FieldOfficerId + "='" + foUserName+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                groupList.add(fetchHelper.getGroupDetailsFromCursor(cursor,db));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return groupList;
    }

    public ArrayList<Group> getAllGroups() {
        ArrayList<Group> groupList = new ArrayList<Group>();

        String selectQuery = "SELECT  * FROM " + Tables.GROUPS;// +
                //" Where "+Columns.GROUP_FieldOfficerId + "='" + foUserName+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                groupList.add(fetchHelper.getGroupDetailsFromCursor(cursor,db));
            } while (cursor.moveToNext());
        }

        return groupList;
    }

    public Group getGroup(String groupId) {

        String selectQuery = "SELECT  * FROM " + Tables.GROUPS
                + " Where " + Columns.GROUP_Id + "='" + groupId +"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        Group group = null;
        if (cursor.moveToFirst())
        {
            group = fetchHelper.getGroupDetailsFromCursor(cursor,db);
        }
        return group;
    }

    public Long getGroupTotalSavings(String groupId , SQLiteDatabase db) {
        if(db == null) db = this.getWritableDatabase();
        String selectQuery = "SELECT  SUM("+Columns.SAVINGACCOUNTS_CurrentBalance+") FROM " + Tables.SAVINGACCOUNTS
                + " Where " + Columns.SAVINGACCOUNTS_GroupId + "='" + groupId + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        long totalSavings =0;
        if(cursor.moveToFirst())
        {
            totalSavings = cursor.getLong(0);
        }
        return totalSavings;
    }

    public Long getGroupTotalOutstanding(String groupId, SQLiteDatabase db) {
        if(db == null) db = this.getWritableDatabase();
        String selectQuery = "SELECT  SUM("+Columns.LOANACCOUNTS_Outstanding+") FROM " + Tables.LOANACCOUNTS
                + " Where " + Columns.LOANACCOUNTS_GroupId + "='" + groupId+"' AND "
                + Columns.LOANACCOUNTS_Active + "=1";

        Cursor cursor = db.rawQuery(selectQuery, null);
        long totalOutstanding = 0;
        if(cursor.moveToFirst())
        {
            totalOutstanding = cursor.getLong(0);
        }
        return totalOutstanding;
    }

    //------------------------ Members related functions ----------------------------//

    private void createMemberSavingAccount(Member member, SQLiteDatabase db) {
        ContentValues saving_acc_values = new ContentValues();
        saving_acc_values.put(Columns.SAVINGACCOUNTS_Id, getTimestampUniqueId());
        saving_acc_values.put(Columns.SAVINGACCOUNTS_GroupId, member.GroupId);
        saving_acc_values.put(Columns.SAVINGACCOUNTS_MemberId, member.Id);

        db.insertOrThrow(Tables.SAVINGACCOUNTS, null, saving_acc_values);
    }

    public void addUpdateMember(Member member) {
        if (member == null) return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        putHelper.putMemberValues(member,values);

        if (StringHelper.IsNullOrEmpty(member.Id))
        {
            member.Id = getUniqueId(member);
            values.put(Columns.MEMBERS_Id, member.Id);

            values.put(Columns.MEMBERS_CreatedBy,member.CreatedBy);
            db.insertOrThrow(Tables.MEMBERS, null, values);
            createMemberSavingAccount(member,db);
        }
        else
        {
            if(StringHelper.IsNullOrEmpty(member.ModifiedBy))
            {
                values.put(Columns.MEMBERS_ModifiedBy, member.ModifiedBy);
                values.put(Columns.MEMBERS_ModifiedDate, new Date().toString());
            }
            else
            {
                values.put(Columns.MEMBERS_ModifiedBy, member.ModifiedBy );
                values.put(Columns.MEMBERS_ModifiedDate, member.ModifiedDate);
            }
            db.update(Tables.MEMBERS, values, Columns.MEMBERS_Id + " ='" + member.Id+"'", null);
        }

        db.close();
    }

    public void addSyncInMember(Member member) {
        if (member == null) return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        putHelper.putMemberValues(member,values);

        if (!CheckMember(member.Id))
        {
            values.put(Columns.MEMBERS_Id, member.Id);
            db.insertOrThrow(Tables.MEMBERS, null, values);
            //createMemberSavingAccount(member,db);
        }
        else
        {
            db.update(Tables.MEMBERS, values, Columns.MEMBERS_Id + " ='" + member.Id+"'", null);
        }

        db.close();
    }

    public Boolean CheckMember(String MemberId) {
        String selectQuery = "SELECT  * FROM " + Tables.MEMBERS
                + " Where " + Columns.MEMBERS_Id + "='" + MemberId +"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        Member member = null;
        if (cursor.moveToFirst())
        {
            return true;
        }
        return false;
    }

    public ArrayList<Member> getAllMembers(String FieldOfficerId) {
        ArrayList<Member> membersList = new ArrayList<Member>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Tables.MEMBERS
                + " Where " + Columns.MEMBERS_GroupId + " IN ( SELECT " + Columns.GROUP_Id + " FROM " + Tables.GROUPS
        + " WHERE " + Columns.GROUP_FieldOfficerId + " = '" + FieldOfficerId + "');";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Member member = fetchHelper.getMemberFromCursor(cursor);
                member.CurrentSavings = getMemberSavings(member.Id, db);
                member.CurrentOutstanding = getMembersOutstanding(member.Id, db);
                membersList.add(member);
            } while (cursor.moveToNext());
        }

        return membersList;
    }

    public Member getMember(String Id, SQLiteDatabase db) {
        if (db == null) db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM "+ Tables.MEMBERS + " WHERE "
                        + Columns.MEMBERS_Id + "='" + Id + "'";

        Cursor cursor = db.rawQuery(selectQuery,null);
        Member member = null;
        if(cursor.moveToFirst())
        {
            member = fetchHelper.getMemberFromCursor(cursor);
        }
        cursor.close();
        return member;
    }

    private float getMemberSavings(String memberId, SQLiteDatabase db) {
        if (db == null) db = this.getWritableDatabase();

        String selectQuery = "SELECT " + Columns.SAVINGACCOUNTS_CurrentBalance + " FROM " + Tables.SAVINGACCOUNTS
                + " Where " + Columns.SAVINGACCOUNTS_MemberId + "='" + memberId + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        int savings = 0;
        if (cursor.moveToFirst()) {
            savings = cursor.getInt(0);
        }
        cursor.close();
        return savings;
    }

    private float getMembersOutstanding(String memberId, SQLiteDatabase db) {
        if (db == null) db = this.getWritableDatabase();

        String selectQuery = "SELECT SUM(" + Columns.LOANACCOUNTS_Outstanding + ") FROM " + Tables.LOANACCOUNTS
                + " Where " + Columns.LOANACCOUNTS_MemberId + "='" + memberId +"'"
                + " AND " + Columns.LOANACCOUNTS_Active + "=1";

        Cursor cursor = db.rawQuery(selectQuery, null);
        int outstanding = 0;
        if (cursor.moveToFirst()) {
            outstanding = cursor.getInt(0);
        }
        cursor.close();
        return outstanding;
    }

    public ArrayList<Member> getGroupMembers(String groupId) {
        ArrayList<Member> membersList = new ArrayList<Member>();

        String selectQuery = "SELECT  * FROM " + Tables.MEMBERS
                + " Where " + Columns.MEMBERS_GroupId + "='" + groupId + "';";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Member member = fetchHelper.getMemberFromCursor(cursor);
                member.CurrentSavings = getMemberSavings(member.Id, db);
                member.CurrentOutstanding = getMembersOutstanding(member.Id, db);
                membersList.add(member);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return membersList;
    }

    public ArrayList<Member> getActiveGroupMembers(String groupId) {
        ArrayList<Member> membersList = new ArrayList<Member>();

        String selectQuery = "SELECT  * FROM " + Tables.MEMBERS
                + " Where " + Columns.MEMBERS_GroupId + "='" + groupId + "' AND "
                + Columns.MEMBERS_Active +"=1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Member member = fetchHelper.getMemberFromCursor(cursor);
                member.CurrentSavings = getMemberSavings(member.Id, db);
                member.CurrentOutstanding = getMembersOutstanding(member.Id, db);
                membersList.add(member);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return membersList;
    }

    public SavingsAccount getMemberSavingAccount(String memberId, SQLiteDatabase db) {
        if (db == null) { db = this.getWritableDatabase(); }

        String selectQuery = "SELECT * FROM " + Tables.SAVINGACCOUNTS
                + " Where " + Columns.SAVINGACCOUNTS_MemberId + "='" + memberId +"'";

        SavingsAccount sa = null;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst())
        {
            sa = fetchHelper.getSavingAccountFromCursor(cursor);
        }
        cursor.close();
        return sa;
    }

    public LoanAccount getMemberNonEmergencyActiveLoan(String memberId, SQLiteDatabase db) {
        if (db == null) { db = this.getWritableDatabase(); }

        String selectQuery = "SELECT * FROM " + Tables.LOANACCOUNTS
                + " Where " + Columns.LOANACCOUNTS_MemberId + "='" + memberId +"' AND "
                + Columns.LOANACCOUNTS_IsEmergency +"=0 AND "
                + Columns.LOANACCOUNTS_Active + "=1";

        Cursor cursor = db.rawQuery(selectQuery, null);
        LoanAccount la = null;
        if(cursor.moveToFirst()) {
            la = fetchHelper.getLoanAccountFromCursor(cursor);
        }
        cursor.close();
        return la;
    }

    public LoanAccount getMemberEmergencyActiveLoan(String memberId, SQLiteDatabase db) {
        if (db == null) { db = this.getWritableDatabase(); }

        String selectQuery = "SELECT * FROM " + Tables.LOANACCOUNTS
                + " Where " + Columns.LOANACCOUNTS_MemberId + "='" + memberId +"' AND "
                + Columns.LOANACCOUNTS_IsEmergency +"=1 AND "
                + Columns.LOANACCOUNTS_Active + "=1";

        Cursor cursor = db.rawQuery(selectQuery, null);
        LoanAccount la = null;
        if(cursor.moveToFirst())
        {
            la = fetchHelper.getLoanAccountFromCursor(cursor);
        }
        cursor.close();
        return la;
    }

    //-------------------------- Meeting related functions ----------------------------//

    public ArrayList<GroupMeeting> getAllGroupMeetings(String groupId, SQLiteDatabase db) {

        ArrayList<GroupMeeting> groupMeetings = new ArrayList<GroupMeeting>();
        String selectQuery = "SELECT * FROM " + Tables.GROUPMEETINGS
                + " WHERE " + Columns.GROUPMEETING_GroupId + "='" + groupId+"'";
        if (db == null) db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                groupMeetings.add(fetchHelper.getBasicGroupMeetingFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return groupMeetings;
    }

    public GroupMeeting getGroupMeeting(Group group, String meetingId, SQLiteDatabase db)
    {
        if (db == null) { db = this.getWritableDatabase(); }

        GroupMeeting meeting = null;

        String selectFromMeeting = "SELECT * FROM "+Tables.GROUPMEETINGS
                + " Where " + Columns.GROUPMEETING_Id + "='" + meetingId +"'";

        Cursor cursor = db.rawQuery(selectFromMeeting,null);
        if(cursor.moveToFirst())
        {
            meeting = fetchHelper.getBasicGroupMeetingFromCursor(cursor);
        }
        cursor.close();

        if(meeting == null) return meeting;

        meeting.SavingTransactions = getMeetingSavingTransactions(group,meeting,db);
        meeting.LoanTransactions = getMeetingLoanTransactions(group,meeting,db);
        meeting.LoansCreated = getMeetingLoansCreated(meeting, db);
        meeting.OtherDetails = getMeetingDetails(meeting,db);
        return meeting;
    }

    private ArrayList<MeetingSavingsAccTransaction> getMeetingSavingTransactions(Group group, GroupMeeting meeting, SQLiteDatabase db)
    {
        if (db == null) { db = this.getWritableDatabase(); }

        ArrayList<MeetingSavingsAccTransaction> transactions = new ArrayList<MeetingSavingsAccTransaction>();

        String getMeetingMembers = " SELECT DISTINCT SA."+Columns.SAVINGACCOUNTS_MemberId
                + " FROM " + Tables.SAVINGACCTRANSACTIONS
                + " INNER JOIN "+ Tables.SAVINGACCOUNTS + " AS SA ON "
                    + Columns.SAVINGACCTRANSACTIONS_SavingAccountId + "=SA." + Columns.SAVINGACCOUNTS_Id
                + " WHERE " + Columns.SAVINGACCTRANSACTIONS_MeetingId + "='"+meeting.Id+"'";

        ArrayList<Member> meetingMembers = new ArrayList<Member>();

        Cursor membersCursor = db.rawQuery(getMeetingMembers,null);
        if (membersCursor.moveToFirst()) {
            do {
                meetingMembers.add(getMember(membersCursor.getString(0),db));
            } while (membersCursor.moveToNext());
        }
        membersCursor.close();

        for(Member member : meetingMembers)
        {
            SavingsAccount savingsAccount = getMemberSavingAccount(member.Id,db);
            MeetingSavingsAccTransaction transaction = new MeetingSavingsAccTransaction(group, member, savingsAccount);

            String compulsaryQuery = "Select "+Columns.SAVINGACCTRANSACTIONS_Amount+ " FROM "+Tables.SAVINGACCTRANSACTIONS
                    + " WHERE " + Columns.SAVINGACCTRANSACTIONS_MeetingId +"='" +meeting.Id +"'"
                    + " AND " + Columns.SAVINGACCTRANSACTIONS_SavingAccountId +"='" +savingsAccount.Id+"'"
                    + " AND "+Columns.SAVINGACCTRANSACTIONS_Type + "='C'";

            Cursor cursor = db.rawQuery(compulsaryQuery, null);
            if(cursor.moveToFirst())
            {
                transaction.CompulsorySavingTransaction.Amount = cursor.getFloat(0);
            }

            String optionalQuery = "Select "+Columns.SAVINGACCTRANSACTIONS_Amount+ " FROM "+Tables.SAVINGACCTRANSACTIONS
                    + " WHERE " + Columns.SAVINGACCTRANSACTIONS_MeetingId +"='" +meeting.Id +"'"
                    + " AND " + Columns.SAVINGACCTRANSACTIONS_SavingAccountId +"='" +savingsAccount.Id+"'"
                    + " AND "+Columns.SAVINGACCTRANSACTIONS_Type + "='O'";

            cursor = db.rawQuery(optionalQuery, null);
            if(cursor.moveToFirst())
            {
                transaction.OptionalSavingTransaction.Amount = cursor.getFloat(0);
            }

            String withdrawQuery = "Select "+Columns.SAVINGACCTRANSACTIONS_Amount+ " FROM "+Tables.SAVINGACCTRANSACTIONS
                    + " WHERE " + Columns.SAVINGACCTRANSACTIONS_MeetingId +"='" +meeting.Id +"'"
                    + " AND " + Columns.SAVINGACCTRANSACTIONS_SavingAccountId +"='" +savingsAccount.Id+"'"
                    + " AND "+Columns.SAVINGACCTRANSACTIONS_Type + "='W'";

            cursor = db.rawQuery(withdrawQuery, null);
            if(cursor.moveToFirst())
            {
                transaction.WithdrawOptionalSavingTransaction.Amount = cursor.getFloat(0);
            }

            transactions.add(transaction);

            cursor.close();
        }

        return transactions;
    }

    private ArrayList<MeetingLoanAccTransaction> getMeetingLoanTransactions(Group group, GroupMeeting meeting, SQLiteDatabase db) {

        if (db == null) { db = this.getWritableDatabase(); }

        ArrayList<MeetingLoanAccTransaction> transactions = new ArrayList<MeetingLoanAccTransaction>();

        String selectMeetingLoanTrans = "SELECT * FROM " + Tables.LOANACCTRANSACTIONS
                + " Where " + Columns.LOANACCTRANSACTIONS_MeetingId + "='" + meeting.Id + "'";

        Cursor loanTransCursor = db.rawQuery(selectMeetingLoanTrans, null);
        if (loanTransCursor.moveToFirst())
        {
            do{
                LoanTransaction transaction = fetchHelper.getLoanTransactionFromCursor(loanTransCursor);
                LoanAccount la = getLoanAccount(transaction.LoanAccountId, db);
                Member member = getMember(la.MemberId, db);
                MeetingLoanAccTransaction loanAccTransaction = new MeetingLoanAccTransaction(group, member, la);
                loanAccTransaction.LoanAccTransaction.Repayment = transaction.Repayment;
                loanAccTransaction.LoanAccTransaction.Outstanding = transaction.Outstanding;
                loanAccTransaction.LoanAccTransaction.MeetingId = transaction.MeetingId;
                loanAccTransaction.LoanAccTransaction.DateTime = transaction.DateTime;

                transactions.add(loanAccTransaction);

            }while (loanTransCursor.moveToNext());
        }
        loanTransCursor.close();

        return transactions;
    }

    private ArrayList<LoanAccount> getMeetingLoansCreated(GroupMeeting groupMeeting, SQLiteDatabase db)
    {
        if (db == null) { db = this.getWritableDatabase(); }

        ArrayList<LoanAccount> loanAccounts = new ArrayList<LoanAccount>();

        String getMeetingLoans = " SELECT * FROM " + Tables.LOANACCOUNTS
                + " WHERE " + Columns.LOANACCOUNTS_GroupMeetingId + "='"+groupMeeting.Id+"'";

        Cursor loanAccountsCursor = db.rawQuery(getMeetingLoans,null);
        if (loanAccountsCursor.moveToFirst()) {
            do {
                LoanAccount la = fetchHelper.getLoanAccountFromCursor(loanAccountsCursor);
                loanAccounts.add(la);
            } while (loanAccountsCursor.moveToNext());
        }
        loanAccountsCursor.close();

        // Because multiple cursors shouldnt be open at a time, doing this here
        for(LoanAccount la : loanAccounts)
        {
            la.Member = getMember(la.MemberId, db);
        }
        return loanAccounts;
    }

    private ArrayList<MeetingDetails> getMeetingDetails(GroupMeeting groupMeeting, SQLiteDatabase db)
    {
        if (db == null) { db = this.getWritableDatabase(); }

        ArrayList<MeetingDetails> details = new ArrayList<MeetingDetails>();

        String query = " SELECT * FROM " + Tables.MEETINGDETAILS
                + " WHERE " + Columns.MEETINGDETAILS_MeetingId +"='" + groupMeeting.Id +"'";

        Cursor detailsCursor = db.rawQuery(query, null);
        if(detailsCursor.moveToFirst()){
            do{
                MeetingDetails detail = fetchHelper.getMeetingDetailFromCursor(detailsCursor);
                detail.member = getMember(detail.MemberId,db);

                details.add(detail);
            }while (detailsCursor.moveToNext());
        }

        return  details;
    }

    public void addUpdateGroupMeeting(GroupMeeting grpMeeting)
    {
        if (grpMeeting == null) return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        putHelper.putGroupMeetingValues(grpMeeting, values);

        if (StringHelper.IsNullOrEmpty(grpMeeting.Id))
        {
            grpMeeting.Id = getUniqueId(grpMeeting);

            values.put(Columns.GROUPMEETING_Id,grpMeeting.Id);
            values.put(Columns.GROUPMEETING_GroupId, grpMeeting.GroupId);
            values.put(Columns.GROUPMEETING_Date, grpMeeting.Date);
            values.put(Columns.GROUPMEETING_FieldOfficerId, grpMeeting.FieldOfficerId);

            db.insertOrThrow(Tables.GROUPMEETINGS, null, values);

            addUpdateSavingTransactions(grpMeeting.SavingTransactions, grpMeeting, db);
            addUpdateLoanTransactions(grpMeeting.LoanTransactions, grpMeeting, db);
            addUpdateLoanAccounts(grpMeeting.LoansCreated,grpMeeting, db);
            addUpdateMeetingDetails(grpMeeting.OtherDetails, grpMeeting, db);

        }
        db.close();
    }

    private void addUpdateSavingTransactions(ArrayList<MeetingSavingsAccTransaction> meetingSavingsAccTransactions, GroupMeeting meeting, SQLiteDatabase db)
    {
        if (meetingSavingsAccTransactions == null) return;

        if(db == null) db = this.getWritableDatabase();
        ContentValues transactionValues = new ContentValues();

        float cummulativeGroupSavings = 0;

        for(MeetingSavingsAccTransaction msat : meetingSavingsAccTransactions)
        {
            SavingTransaction st = new SavingTransaction();
            st.GroupId = msat.Group.Id;
            st.SavingAccountId = msat.SavingsAccount.Id;
            st.MeetingId = meeting.Id;
            st.DateTime = meeting.Date;

            if(msat.CompulsorySavingTransaction.Amount!=0)
            {
                st.Amount = msat.CompulsorySavingTransaction.Amount;
                st.Type = msat.CompulsorySavingTransaction.Type;

                putHelper.putSavingTransactionValues(st, transactionValues);
                db.insertOrThrow(Tables.SAVINGACCTRANSACTIONS, null, transactionValues);
            }

            if(msat.OptionalSavingTransaction.Amount!=0)
            {
                st.Amount = msat.OptionalSavingTransaction.Amount;
                st.Type = msat.OptionalSavingTransaction.Type;

                putHelper.putSavingTransactionValues(st, transactionValues);
                db.insertOrThrow(Tables.SAVINGACCTRANSACTIONS, null, transactionValues);
            }

            if(msat.WithdrawOptionalSavingTransaction.Amount!=0)
            {
                st.Amount = msat.WithdrawOptionalSavingTransaction.Amount;
                st.Type = msat.WithdrawOptionalSavingTransaction.Type;

                putHelper.putSavingTransactionValues(st, transactionValues);
                db.insertOrThrow(Tables.SAVINGACCTRANSACTIONS, null, transactionValues);
            }

            SavingsAccount sa = msat.SavingsAccount;
            ContentValues accountValues = new ContentValues();
            accountValues.put(Columns.SAVINGACCOUNTS_OptionalSavings, sa.OptionalSavings );
            accountValues.put(Columns.SAVINGACCOUNTS_CompulsorySavings, sa.CompulsorySavings );
            accountValues.put(Columns.SAVINGACCOUNTS_CurrentBalance, sa.getTotalSavings());

            db.update(Tables.SAVINGACCOUNTS, accountValues, Columns.SAVINGACCOUNTS_Id + " ='" + sa.Id + "'", null);

            cummulativeGroupSavings += sa.getTotalSavings();
        }

        //Update group cummulative savings
        String updateQuery = "UPDATE " + Tables.GROUPS
                + " SET " + Columns.GROUP_CummulativeSavings + "="  + cummulativeGroupSavings
                + " WHERE " + Columns.GROUP_Id + "='" + meeting.GroupId +"'";

        db.execSQL(updateQuery);
    }

    private void addUpdateLoanTransactions(ArrayList<MeetingLoanAccTransaction> meetingLoanAccTransactions, GroupMeeting meeting, SQLiteDatabase db)
    {
        if (meetingLoanAccTransactions == null) return;

        if(db == null) db = this.getWritableDatabase();

        float outStandingGroupLoans = 0;

        for(MeetingLoanAccTransaction mlat : meetingLoanAccTransactions)
        {
            LoanTransaction lt = new LoanTransaction();
            lt.MeetingId = meeting.Id;
            lt.GroupId =meeting.GroupId;

            lt.LoanAccountId = mlat.LoanAccount.Id;
            lt.Repayment = mlat.LoanAccTransaction.Repayment;
            lt.Outstanding = mlat.LoanAccTransaction.getUpdatedOutstanding();
            lt.DateTime = mlat.LoanAccTransaction.DateTime;

            // Insert Loan Account Transaction
            ContentValues loanTransValues = new ContentValues();
            putHelper.putLoanTransactionValues(lt, loanTransValues);
            db.insertOrThrow(Tables.LOANACCTRANSACTIONS, null, loanTransValues);

            // Update Loan Account
            ContentValues loanAccUpdate = new ContentValues();
            loanAccUpdate.put(Columns.LOANACCOUNTS_Outstanding, lt.Outstanding);
            // If outstanding is less or equal to 0, close the account
            if( lt.Outstanding <= 0 )
            {
                loanAccUpdate.put(Columns.LOANACCOUNTS_Active, false);
            }

            loanAccUpdate.put(Columns.LOANACCOUNTS_ModifiedDate, String.valueOf(new Date()));
            loanAccUpdate.put(Columns.LOANACCOUNTS_ModifiedBy, meeting.FieldOfficerId);
            db.update(Tables.LOANACCOUNTS, loanAccUpdate,Columns.LOANACCOUNTS_Id+"='"+lt.LoanAccountId+"'",null);

            outStandingGroupLoans += lt.Outstanding;
        }

        //Update group cummulative savings
        String updateQuery = "UPDATE " + Tables.GROUPS
                + " SET " + Columns.GROUP_OutstandingLoans + "=" + outStandingGroupLoans
                + " WHERE " + Columns.GROUP_Id + "='" + meeting.GroupId +"'";

        db.execSQL(updateQuery);
    }

    public void addUpdateLoanAccounts(ArrayList<LoanAccount> loanAccounts, GroupMeeting meeting, SQLiteDatabase db)
    {
        if(db == null) db = this.getWritableDatabase();

        for (LoanAccount loanAccount: loanAccounts)
        {
            loanAccount.GroupMeetingId = meeting.Id;

            ContentValues loanValues = new ContentValues();

            putHelper.putLoanAccountValues(loanAccount, loanValues);
            // Todo insert or update logic here
            db.insertOrThrow(Tables.LOANACCOUNTS, null, loanValues);
        }
    }

    public void addUpdateMeetingDetails(ArrayList<MeetingDetails> otherDetails, GroupMeeting meeting, SQLiteDatabase db)
    {
        if(db == null) db = this.getWritableDatabase();

        float totalMeetingFines = 0;

        for(MeetingDetails detail : otherDetails)
        {
            detail.MeetingId = meeting.Id;

            ContentValues detailValues = new ContentValues();

            putHelper.putMeetingDetails(detail, detailValues);

            db.insertOrThrow(Tables.MEETINGDETAILS, null, detailValues);
            if(detail.Fine > 0) totalMeetingFines += detail.Fine;
        }

        if(totalMeetingFines > 0)
        {
            String updateQuery = "UPDATE " + Tables.GROUPS
                   + " SET " + Columns.GROUP_OtherIncome + "=" + Columns.GROUP_OtherIncome + " + " + totalMeetingFines
                   + " WHERE " + Columns.GROUP_Id + "='" + meeting.GroupId +"'";

            db.execSQL(updateQuery);
        }
    }

    public ArrayList<Member> getAllMembersWithNoActiveLoan(String groupId, boolean isEmergency)
    {

        ArrayList<Member> members = new ArrayList<Member>();

        String notInQuery = "( SELECT " + Columns.LOANACCOUNTS_MemberId + " FROM "+ Tables.LOANACCOUNTS
                + " Where " + Columns.LOANACCOUNTS_GroupId + "='" + groupId + "'"
                + " AND " + Columns.LOANACCOUNTS_Active + "=1";

        if(isEmergency)
        {
            notInQuery += " AND " + Columns.LOANACCOUNTS_IsEmergency +"=1";
        }

        notInQuery +=")";

        String selectMemberQuery = " SELECT " + Columns.MEMBERS_Id + " FROM " + Tables.MEMBERS
                + " Where " + Columns.MEMBERS_GroupId + "='" + groupId + "'"
                + " AND " + Columns.MEMBERS_Id + " NOT IN " + notInQuery;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectMemberQuery, null);

        if(cursor.moveToFirst())
        {
            do
            {
                members.add(getMember(cursor.getString(0),db));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return members;
    }

    private LoanAccount getLoanAccount(String loanAccId, SQLiteDatabase db)
    {
        if (db == null) { db = this.getWritableDatabase(); }

        String query = "SELECT * FROM "+Tables.LOANACCOUNTS
                +" WHERE " + Columns.LOANACCOUNTS_Id + "='" +loanAccId +"'";

        LoanAccount la = null;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst())
        {
            la = fetchHelper.getLoanAccountFromCursor(cursor);
        }
        cursor.close();
        return la;
    }

// Sync In Related Functions


    public ArrayList<SavingsAccount> getAllSavingAccounts(String FieldOfficerId) {
        ArrayList<SavingsAccount> savingsAccountsList = new ArrayList<SavingsAccount>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Tables.SAVINGACCOUNTS
                + " Where " + Columns.SAVINGACCOUNTS_GroupId + " IN ( SELECT " + Columns.GROUP_Id + " FROM " + Tables.GROUPS
                + " WHERE " + Columns.GROUP_FieldOfficerId + " = '" + FieldOfficerId + "');";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SavingsAccount savAccount = fetchHelper.getSavingAccountFromCursor(cursor);
                savingsAccountsList.add(savAccount);
            } while (cursor.moveToNext());
        }

        return savingsAccountsList;
    }


    public void addSyncInSavingAccounts(SavingsAccount savingsAccount) {
        if (savingsAccount == null) return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        putHelper.putSavingAccountValues(savingsAccount,values);

        if (!CheckSavingAccount(savingsAccount.Id))
        {
            values.put(Columns.SAVINGACCOUNTS_Id, savingsAccount.Id);
            db.insertOrThrow(Tables.SAVINGACCOUNTS, null, values);
            //createMemberSavingAccount(member,db);
        }
        else
        {
            db.update(Tables.SAVINGACCOUNTS, values, Columns.SAVINGACCOUNTS_Id + " ='" + savingsAccount.Id+"'", null);
        }

        db.close();
    }


    public Boolean CheckSavingAccount(String SavingAccountId)
    {
        String selectQuery = "SELECT  * FROM " + Tables.SAVINGACCOUNTS
                + " Where " + Columns.SAVINGACCOUNTS_Id + "='" + SavingAccountId +"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        Member member = null;
        if (cursor.moveToFirst())
        {
            return true;
        }
        return false;
    }

}
