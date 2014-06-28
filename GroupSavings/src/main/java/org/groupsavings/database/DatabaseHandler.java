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
import org.groupsavings.domain.MeetingSavingsAccTransaction;
import org.groupsavings.domain.Member;
import org.groupsavings.domain.SavingTransaction;
import org.groupsavings.domain.SavingsAccount;

import java.text.SimpleDateFormat;
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
                    db.execSQL(Tables.CREATE_TABLE_LOANTRANSACTIONS);
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
    public void execQuery(String query)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }

    //------------------------ Groups related functions ----------------------------//

    // For debugging purposes
    public void TruncateGroups() {
        String query = " DELETE FROM TABLE GROUPS";
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Tables.GROUPS,null,null);
    }

    public void addUpdateGroup(Group group) {
        if (group == null) return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        putHelper.putGroupValues(group, values);

        if (StringHelper.IsNullOrEmpty(group.Id))
        {
            String id = Tables.getUniqueId(group);
            group.Id = getUniqueId(group);
            values.put(Columns.GROUP_Id,group.Id);
            // TODO: get field officer Id from security
            values.put(Columns.GROUP_CreatedBy, group.CreatedBy);
            db.insertOrThrow(Tables.GROUPS, null, values);
        }
        else
        {
            if(StringHelper.IsNullOrEmpty(group.ModifiedBy))
            {
                //TODO: get field officer Id from security
                //values.put(Columns.GROUP_ModifiedBy, group.ModifiedBy);
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

    public ArrayList<Group> getAllFOGroups(String fieldOfficerId) {
        ArrayList<Group> groupList = new ArrayList<Group>();

        String selectQuery = "SELECT  * FROM " + Tables.GROUPS;// +
                //" Where "+Columns.GROUP_Id + "='" + fieldOfficerId+"'";

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

    public int getActiveMembers(String groupId, SQLiteDatabase db) {
        if(db == null) db = this.getWritableDatabase();
        String selectQuery = "SELECT  COUNT("+Columns.MEMBERS_Active+") FROM "
                + Tables.MEMBERS + " Where " + Columns.MEMBERS_GroupId +"='" + groupId +"' AND "
                + Columns.MEMBERS_Active + "=1";

        Cursor cursor = db.rawQuery(selectQuery, null);
        int totalMembers = 0;
        if(cursor.moveToFirst())
        {
            totalMembers = cursor.getInt(0);
        }
        return totalMembers;
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
            // TODO: get field officer Id from security
            //values.put(Columns.MEMBERS_CreatedBy,member.CreatedBy);
            db.insertOrThrow(Tables.MEMBERS, null, values);
            createMemberSavingAccount(member,db);
        }
        else
        {
            if(StringHelper.IsNullOrEmpty(member.ModifiedBy))
            {
                // TODO: get field officer Id from security
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

    public Member getMember(String Id, SQLiteDatabase db)
    {
        if (db == null) db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM "+ Tables.MEMBERS + " WHERE "
                        + Columns.MEMBERS_Id + "='" + Id + "'";

        Cursor cursor = db.rawQuery(selectQuery,null);
        Member member = null;
        if(cursor.moveToFirst())
        {
            member = fetchHelper.getMemberFromCursor(cursor);
        }

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

        return outstanding;
    }

    public ArrayList<Member> getGroupMembers(String groupId) {
        ArrayList<Member> membersList = new ArrayList<Member>();
        // Select All Query
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

        return membersList;
    }

    public ArrayList<Member> getActiveGroupMembers(String groupId) {
        ArrayList<Member> membersList = new ArrayList<Member>();
        // Select All Query
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

        return sa;
    }

    public LoanAccount getMemberNonEmergencyActiveLoan(String memberId, SQLiteDatabase db)
    {
        if (db == null) { db = this.getWritableDatabase(); }

        String selectQuery = "SELECT * FROM " + Tables.LOANACCOUNTS
                + " Where " + Columns.LOANACCOUNTS_MemberId + "='" + memberId +"' AND "
                + Columns.LOANACCOUNTS_IsEmergency +"=0 AND "
                + Columns.LOANACCOUNTS_Active + "=1";

        Cursor cursor = db.rawQuery(selectQuery, null);

        return fetchHelper.getLoanAccountFromCursor(cursor);
    }

    public LoanAccount getMemberEmergencyActiveLoan(String memberId, SQLiteDatabase db)
    {
        if (db == null) { db = this.getWritableDatabase(); }

        String selectQuery = "SELECT * FROM " + Tables.LOANACCOUNTS
                + " Where " + Columns.LOANACCOUNTS_MemberId + "='" + memberId +"' AND "
                + Columns.LOANACCOUNTS_IsEmergency +"=1 AND "
                + Columns.LOANACCOUNTS_Active + "=1";

        Cursor cursor = db.rawQuery(selectQuery, null);

        return fetchHelper.getLoanAccountFromCursor(cursor);
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
                groupMeetings.add(fetchHelper.getGroupMeetingFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        return groupMeetings;
    }

    // Function that will create a meeting record and return meeting id
    private String createMeeting(String groupId, Date date, SQLiteDatabase db) {
        if (db == null) db = this.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String dateTime = sdf.format(date);

        ContentValues meetingValues = new ContentValues();
        String Id = getTimestampUniqueId();
        meetingValues.put(Columns.GROUPMEETING_Id, Id);
        meetingValues.put(Columns.GROUPMEETING_GroupId, groupId);
        meetingValues.put(Columns.GROUPMEETING_Date, dateTime);
        //TODO fetch FOID from session
        //meetingValues.put(Columns.GROUPMEETING_FieldOfficerId,"");

        db.insertOrThrow(Tables.GROUPMEETINGS, null, meetingValues);

        return Id;
    }


// Added for Saving Accounts and Saving Transactions
    public void addUpdateMeetingDetails(GroupMeeting grpMeeting) {
        if (grpMeeting == null) return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        putHelper.putGroupMeetingValues(grpMeeting, values);

        if (StringHelper.IsNullOrEmpty(grpMeeting.Id))
        {
            grpMeeting.Id = getUniqueId(grpMeeting);
            values.put(Columns.GROUPMEETING_Id,grpMeeting.Id);
            // TODO: get field officer Id from security
            db.insertOrThrow(Tables.GROUPMEETINGS, null, values);

            addUpdateSavingTransactions(grpMeeting.SavingTransactions,grpMeeting.Id, grpMeeting.Date);

        }
        db.close();
    }

    public void addUpdateSavingTransactions(ArrayList<MeetingSavingsAccTransaction> meetingSavingsAccTransactions, String grpMeetingId, String grpMeetingDate) {
        if (meetingSavingsAccTransactions == null) return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues transactionValues = new ContentValues();

        for(MeetingSavingsAccTransaction msat : meetingSavingsAccTransactions)
        {
            SavingTransaction st = new SavingTransaction();
            st.GroupId = msat.Group.Id;
            st.SavingAccountId = msat.SavingsAccount.Id;
            st.MeetingId = grpMeetingId;
            st.DateTime = grpMeetingDate;

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
        }

        db.close();
    }




    /*public ArrayList<MemberMeetingTransactions> getMeetingTransactions(int grpMeetingId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String savingQuery = " SELECT * FROM "+TABLE_SAVINGTRANSACTION
                + " WHERE "+Columns.SAVINGTRANSACTION_GroupMeetingId+"="+grpMeetingId;

        Cursor cr_savings = db.rawQuery(savingQuery,null);

        ArrayList<MemberMeetingTransactions> transactions = new ArrayList<MemberMeetingTransactions>();

        if (cr_savings.moveToFirst()) {
            do {
                // Get member id
                int memberId = getSavingsAccountMemberId(cr_savings.getInt(3),db);
                // Get Member
                Member member = getBasicMember(memberId,db);
                MemberMeetingTransactions tran = new MemberMeetingTransactions(member.GroupUID,member);
                tran.SavingTransaction.optionalSavings = cr_savings.getInt(4);
                tran.SavingTransaction.transactionTotalSaving = cr_savings.getInt(5);
                tran.SavingTransaction.groupCompulsorySavings = cr_savings.getInt(5) - cr_savings.getInt(4);
                transactions.add(tran);
            } while (cr_savings.moveToNext());
        }

        String loanQuery = " SELECT * FROM "+TABLE_LOANTRANSACTION
                + " WHERE "+Columns.SAVINGTRANSACTION_GroupMeetingId+"="+grpMeetingId;

        Cursor cr_loans = db.rawQuery(loanQuery,null);

        if (cr_loans.moveToFirst()) {
            do {
                // Get member id
                int memberId = getLoanAccountMemberId(cr_loans.getInt(3), db);
                // Get Member
                for(MemberMeetingTransactions trans : transactions)
                {
                    if(trans.GroupMember.UID == memberId)
                    {
                        LoanAccount la = getLoanAccount(cr_loans.getInt(3),db);
                        trans.LoanTransaction.EMI = la.EMI;
                        trans.LoanTransaction.Repayment = cr_loans.getInt(4);
                        trans.LoanTransaction.setOutstandingDue(cr_loans.getInt(5));
                    }
                }
            } while (cr_loans.moveToNext());
        }

        return transactions;
    }

    public ArrayList<LoanAccount> getMeetingLoans(int grpMeetingId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+TABLE_LOANSACCOUNT
                + " WHERE "+Columns.LOANACCOUNT_GroupMeetingId +"="+grpMeetingId;

        Cursor cursor = db.rawQuery(selectQuery,null);
        ArrayList<LoanAccount> loanAccounts = new ArrayList<LoanAccount>();
        if(cursor.moveToFirst())
        {
            do{
                LoanAccount la = getLoanAccount(cursor.getInt(0),db);
                loanAccounts.add(la);
            }while(cursor.moveToNext());
        }

        return loanAccounts;
    }

    public void saveMeetingDetails(int groupId, ArrayList<MemberMeetingTransactions> transactions, ArrayList<LoanAccount> loanAccounts) {
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date currentDate = new Date();
        long meetingId = createMeeting(groupId, currentDate, db);
        String dateTime = sdf.format(new Date());

        for (MemberMeetingTransactions transaction : transactions) {
            // Get Savings account
            SavingsAccount savingsAccount = getMemberSavingAccount(transaction.GroupMember.UID, db);
            // calculate updated total savings
            int updatedTotalSavings = savingsAccount.TotalSavings + transaction.SavingTransaction.getTotalSavings();

            // insert transaction
            ContentValues savingtransaction = new ContentValues();
            savingtransaction.put(Columns.SAVINGTRANSACTION_GroupMemberSavingId, savingsAccount.Id);
            savingtransaction.put(Columns.SAVINGTRANSACTION_GroupId,groupId);
            savingtransaction.put(Columns.SAVINGTRANSACTION_GroupMeetingId, meetingId);
            savingtransaction.put(Columns.SAVINGTRANSACTION_OptionalSavings, transaction.SavingTransaction.optionalSavings);
            savingtransaction.put(Columns.SAVINGTRANSACTION_TransactionTotalSaving, transaction.SavingTransaction.getTotalSavings());
            savingtransaction.put(Columns.SAVINGTRANSACTION_DateTime, dateTime);
            db.insertOrThrow(TABLE_SAVINGTRANSACTION, null, savingtransaction);

            // update savings account
            ContentValues savingAccountUpdate = new ContentValues();
            savingAccountUpdate.put(Columns.SAVING_ACCOUNT_TotalSaving, updatedTotalSavings);
            db.update(TABLE_SAVINGSACCOUNT, savingAccountUpdate,
                    Columns.SAVING_ACCOUNT_Id + "=" + savingsAccount.Id, null);

            ContentValues loanTransaction = new ContentValues();
            if(transaction.LoanTransaction.EMI > 0)
            {
                // insert transaction
                loanTransaction.put(Columns.LOAN_TRANSACTION_GroupMeetingId, meetingId);
                loanTransaction.put(Columns.LOAN_TRANSACTION_GroupId,groupId);
                loanTransaction.put(Columns.LOAN_TRANSACTION_GroupMemberLoanId, transaction.LoanTransaction.GroupMemberLoanAccountId);
                loanTransaction.put(Columns.LOAN_TRANSACTION_Repayment, transaction.LoanTransaction.Repayment);
                loanTransaction.put(Columns.LOAN_TRANSACTION_OutstandingLeft, transaction.LoanTransaction.getUpdatedOutstanding());

                db.insertOrThrow(TABLE_LOANTRANSACTION, null, loanTransaction);

                //update loans account
                ContentValues loanAccountUpdate = new ContentValues();
                loanAccountUpdate.put(Columns.LOANACCOUNT_Outstanding, transaction.LoanTransaction.getUpdatedOutstanding());
                // make account inactive if no more left to pay
                if(transaction.LoanTransaction.getUpdatedOutstanding() <= 0)
                {
                    loanAccountUpdate.put(Columns.LOANACCOUNT_IsActive,false);
                }
                db.update(TABLE_LOANSACCOUNT,loanAccountUpdate,
                        Columns.LOANACCOUNT_Id+"="+transaction.LoanTransaction.GroupMemberLoanAccountId,null);
            }
        }

        for(LoanAccount la : loanAccounts) {

            ContentValues loanAccValues = new ContentValues();
            loanAccValues.put(Columns.LOANACCOUNT_GroupId, la.groupId);
            loanAccValues.put(Columns.LOANACCOUNT_GroupMeetingId, meetingId);
            loanAccValues.put(Columns.LOANACCOUNT_MemberId, la.memberId);
            loanAccValues.put(Columns.LOANACCOUNT_PrincipalAmount, la.Principal);
            loanAccValues.put(Columns.LOANACCOUNT_InterestRate, la.InterestPerAnnum);
            loanAccValues.put(Columns.LOANACCOUNT_NoOfInstallments, la.PeriodInMonths);
            loanAccValues.put(Columns.LOANACCOUNT_InstallmentAmount, la.getEMI());
            loanAccValues.put(Columns.LOANACCOUNT_StartDate, la.StartDate);
            loanAccValues.put(Columns.LOANACCOUNT_EndDate, la.EndDate);
            loanAccValues.put(Columns.LOANACCOUNT_Outstanding, la.getInitialOutstanding());
            loanAccValues.put(Columns.LOANACCOUNT_IsActive, la.IsActive);

            if (la.Id == 0) {
                db.insertOrThrow(TABLE_LOANSACCOUNT, null, loanAccValues);
            } else {
                db.update(TABLE_LOANSACCOUNT, loanAccValues, Columns.LOANACCOUNT_Id + "=" + la.Id, null);
            }
        }
    }/*

    //-------------------------- Transaction related functions ---------------//
    /*
    public LoanAccount getMemberActiveLoanAccount(int memberId) {
        String selectQuery = "SELECT * FROM " + TABLE_LOANSACCOUNT +
                " WHERE "+Columns.LOANACCOUNT_MemberId+"="+memberId +
                " AND "+Columns.LOANACCOUNT_IsActive+"=1";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        LoanAccount la = null;
        if(cursor.moveToFirst()){
            la = new LoanAccount();
            la.Id = cursor.getInt(0);
            la.groupId = cursor.getInt(1);
            la.groupMeetingId = cursor.getInt(2);
            la.memberId = cursor.getInt(3);
            la.Principal = cursor.getInt(4);
            la.InterestPerAnnum = cursor.getFloat(5);
            la.PeriodInMonths = cursor.getInt(6);
            la.EMI = cursor.getInt(7);
            la.StartDate = cursor.getString(8);
            la.EndDate = cursor.getString(9);
            la.OutStanding = cursor.getInt(10);
            la.Reason = cursor.getString(11);
            la.IsActive = cursor.getInt(12) == 1;
        }

        return la;
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
                st.groupId = cursor.getInt(1);
                st.grpMeetingId = cursor.getInt(2);
                st.memberSavingAccId = cursor.getInt(3);
                st.optionalSavings = cursor.getInt(4);
                st.transactionTotalSaving = cursor.getInt(5);
                st.timeStamp = cursor.getString(6);
                allSavingTrans.add(st);
            } while (cursor.moveToNext());
        }

        return allSavingTrans;
    }

    public ArrayList<LoanTransaction> getAllLoanTrans(){

        ArrayList<LoanTransaction> allLoanTrans = new ArrayList<LoanTransaction>();
        String selectQuery = "SELECT * FROM " + TABLE_LOANTRANSACTION + ";";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                LoanTransaction lt = new LoanTransaction();
                lt.Id = cursor.getInt(0);
                lt.groupId = cursor.getInt(1);
                lt.OutstandingDue= cursor.getInt(5);
                lt.GroupMeetingId = cursor.getInt(2);
                lt.GroupMemberLoanAccountId = cursor.getInt(3);
                lt.Repayment = cursor.getInt(4);
                allLoanTrans.add(lt);
            } while (cursor.moveToNext());
        }
        return allLoanTrans;
    }

    public ArrayList<GroupMeeting> getAllMeetingDetails(){
        ArrayList<GroupMeeting> allMeetingDetails = new ArrayList<GroupMeeting>();
        String selectQuery = "SELECT * FROM " + TABLE_GROUPMEETINGS + ";";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                GroupMeeting gm = new GroupMeeting();
                gm.id = cursor.getInt(0);
                gm.groupId = cursor.getInt(1);
                gm.date = cursor.getString(2);
                allMeetingDetails.add(gm);
            } while (cursor.moveToNext());
        }
        return allMeetingDetails;
    }*/

}
