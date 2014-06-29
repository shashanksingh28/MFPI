package org.groupsavings.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.groupsavings.constants.Columns;
import org.groupsavings.constants.Tables;
import org.groupsavings.domain.Group;
import org.groupsavings.domain.GroupMeeting;
import org.groupsavings.domain.LoanAccount;
import org.groupsavings.domain.Member;
import org.groupsavings.domain.SavingsAccount;

/**
 * Created by shashank on 21/6/14.
 * Class that will help populate domain objects from their table cursors
 * This class will populate only the fields present in the table and
 * NOT the derived fields.
 * Also to be used by SyncHelper to fetch data.
 */

public class DatabaseFetchHelper {

    Group getGroupDetailsFromCursor(Cursor cursor, SQLiteDatabase db) {
        Group group = new Group();
        group.Id = cursor.getString(0);
        group.Name = cursor.getString(1);
        group.President = getBasicMember(cursor.getString(2), db);
        group.Secretary = getBasicMember(cursor.getString(3), db);
        group.Treasurer = getBasicMember(cursor.getString(4), db);
        group.FieldOfficerId = cursor.getString(5);
        group.Active = (cursor.getInt(6) == 1);
        group.MonthlyCompulsoryAmount = cursor.getFloat(7);
        group.MonthlyMeetingDate = cursor.getInt(8);
        group.Bank = cursor.getString(9);
        group.ClusterId = cursor.getInt(10);
        group.CummulativeSavings = cursor.getFloat(11);
        group.OtherIncome = cursor.getFloat(12);
        group.OutstandingLoans = cursor.getFloat(13);
        group.DateOfFormation = cursor.getString(14);
        group.NoOfSubgroups = cursor.getInt(15);
        group.AddressLine1 = cursor.getString(16);
        group.AddressLine2 = cursor.getString(17);
        group.City = cursor.getString(18);
        group.State = cursor.getString(19);
        group.Country = cursor.getString(20);
        group.CreatedDate = cursor.getString(21);
        group.CreatedBy = cursor.getString(22);
        group.ModifiedDate = cursor.getString(23);
        group.ModifiedBy = cursor.getString(24);

        return group;
    }

    GroupMeeting getBasicGroupMeetingFromCursor(Cursor cursor) {
        GroupMeeting groupMeeting = new GroupMeeting();
        groupMeeting.Id = cursor.getString(0);
        groupMeeting.GroupId = cursor.getString(1);
        groupMeeting.Date = cursor.getString(2);
        groupMeeting.FieldOfficerId = cursor.getString(3);

        return groupMeeting;
    }

    private Member getBasicMember(String memberId, SQLiteDatabase db) {

        String selectQuery = "SELECT  * FROM " + Tables.MEMBERS
                + " Where " + Columns.MEMBERS_Id + "='" + memberId + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        Member member = null;
        if (cursor.moveToFirst()) {
            member = new Member();
            member.Id = cursor.getString(0);
            member.GroupId = cursor.getString(1);
            member.FirstName = cursor.getString(2);
            member.LastName = cursor.getString(3);
        }

        return member;
    }

    Member getMemberFromCursor(Cursor cursor) {

        Member member = new Member();
        member.Id = cursor.getString(0);
        member.GroupId = cursor.getString(1);
        member.FirstName = cursor.getString(2);
        member.LastName = cursor.getString(3);
        member.GuardianName = cursor.getString(4);
        member.Gender = cursor.getString(5);
        member.DOB = cursor.getString(6);
        member.EmailId = cursor.getString(7);
        member.Active = cursor.getInt(8) == 1;
        member.ContactNumber = cursor.getString(9);
        member.AddressLine1 = cursor.getString(10);
        member.AddressLine2 = cursor.getString(11);
        member.Occupation = cursor.getString(12);
        member.AnnualIncome = cursor.getLong(13);
        member.EconomicCondition = cursor.getString(14);
        member.Education = cursor.getString(15);
        member.Disability = cursor.getInt(16) == 1;
        member.NoOfFamilyMembers = cursor.getInt(17);
        member.Nominee = cursor.getString(18);
        member.Passbook = cursor.getString(19);
        member.Insurance = cursor.getInt(20) == 1;
        member.ExitDate = cursor.getString(21);
        member.ExitReason = cursor.getString(22);
        member.CreatedDate = cursor.getString(23);
        member.CreatedBy = cursor.getString(24);
        member.ModifiedDate = cursor.getString(25);
        member.ModifiedBy = cursor.getString(26);

        return member;
    }

    SavingsAccount getSavingAccountFromCursor(Cursor cursor) {

        SavingsAccount account = new SavingsAccount();

        account.Id = cursor.getString(0);
        account.GroupId = cursor.getString(1);
        account.MemberId = cursor.getString(2);
        account.CompulsorySavings = cursor.getFloat(3);
        account.OptionalSavings = cursor.getFloat(4);
        account.InterestAccumulated = cursor.getFloat(5);
        account.CreatedDate = cursor.getString(7);
        account.CreatedBy = cursor.getString(8);
        account.Active = cursor.getInt(9) == 1;

        return account;
    }

    LoanAccount getLoanAccountFromCursor(Cursor cursor){
        LoanAccount la = null;

        if(cursor.moveToFirst()){
            la = new LoanAccount();
            la.Id = cursor.getString(0);
            la.MemberId = cursor.getString(1);
            la.GroupId = cursor.getString(2);
            la.GroupMeetingId = cursor.getString(3);
            la.Principal = cursor.getLong(4);
            la.InterestRate = cursor.getFloat(5);
            la.PeriodInMonths = cursor.getInt(6);
            la.EMI = cursor.getLong(7);
            la.Outstanding = cursor.getLong(8);
            la.Reason = cursor.getString(9);
            la.Guarantor = cursor.getString(10);
            la.IsEmergency = cursor.getInt(11) == 1;
            la.StartDate = cursor.getString(12);
            la.EndDate = cursor.getString(13);
            la.CreatedDate = cursor.getString(14);
            la.CreatedBy = cursor.getString(15);
            la.Active = cursor.getInt(16) == 1;
        }
        return la;
    }
}
