package org.groupsavings.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import org.groupsavings.constants.Columns;
import org.groupsavings.domain.*;

/**
 * Created by shashank on 21/6/14.
 * Class that will help in insert update statements by putting respective values in query
 * To be used by SyncHelper to update data from server
 */
public class DatabasePutHelper {

    void putGroupValues(Group group, ContentValues values) {
        values.put(Columns.GROUP_Name, group.Name);

        if(group.President != null)
            values.put(Columns.GROUP_PresidentId, group.President.Id);
        if(group.Secretary != null)
            values.put(Columns.GROUP_SecretaryId, group.Secretary.Id);
        if(group.Treasurer != null)
            values.put(Columns.GROUP_TreasurerId, group.Treasurer.Id);
        values.put(Columns.GROUP_MonthlyMeetingDate,group.MonthlyMeetingDate);
        values.put(Columns.GROUP_MonthlyCompulsoryAmount,group.MonthlyCompulsoryAmount);
        values.put(Columns.GROUP_FieldOfficerId, group.FieldOfficerId);
        values.put(Columns.GROUP_Bank, group.Bank);
        values.put(Columns.GROUP_ClusterId, group.ClusterId);
        values.put(Columns.GROUP_CummulativeSavings, group.CummulativeSavings);
        values.put(Columns.GROUP_OtherIncome, group.OtherIncome);
        values.put(Columns.GROUP_OutstandingLoans, group.OutstandingLoans);
        values.put(Columns.GROUP_DateOfFormation, group.DateOfFormation);
        values.put(Columns.GROUP_NoOfSubgroups, group.NoOfSubgroups);
        values.put(Columns.GROUP_AddressLine1, group.AddressLine1);
        values.put(Columns.GROUP_AddressLine2, group.AddressLine2);
        values.put(Columns.GROUP_City, group.City);
        values.put(Columns.GROUP_State, group.State);
        values.put(Columns.GROUP_Country, group.Country);
        values.put(Columns.GROUP_NoOfSubgroups, group.NoOfSubgroups);
    }

    void putMemberValues(Member member, ContentValues values) {
        values.put(Columns.MEMBERS_GroupId, member.GroupId);
        values.put(Columns.MEMBERS_FirstName, member.FirstName);
        values.put(Columns.MEMBERS_LastName, member.LastName);
        values.put(Columns.MEMBERS_GuardianName, member.GuardianName);
        values.put(Columns.MEMBERS_Gender, member.Gender);
        values.put(Columns.MEMBERS_DOB, member.DOB);
        values.put(Columns.MEMBERS_EmailId, member.EmailId);
        values.put(Columns.MEMBERS_Active, member.Active);
        values.put(Columns.MEMBERS_ContactNumber, member.ContactNumber);
        values.put(Columns.MEMBERS_AddressLine1, member.AddressLine1);
        values.put(Columns.MEMBERS_AddressLine2, member.AddressLine2);
        values.put(Columns.MEMBERS_Occupation, member.Occupation);
        values.put(Columns.MEMBERS_AnnualIncome, member.AnnualIncome);
        values.put(Columns.MEMBERS_EconomicCondition, member.EconomicCondition);
        values.put(Columns.MEMBERS_Education, member.Education);
        values.put(Columns.MEMBERS_Disability, member.Disability);
        values.put(Columns.MEMBERS_NoOfFamilyMembers, member.NoOfFamilyMembers);
        values.put(Columns.MEMBERS_Nominee, member.Nominee);
        values.put(Columns.MEMBERS_Passbook, member.Passbook);
        values.put(Columns.MEMBERS_Insurance, member.Insurance);
        values.put(Columns.MEMBERS_DOB, member.DOB);
        values.put(Columns.MEMBERS_ExitDate, member.ExitDate);
        values.put(Columns.MEMBERS_ExitReason, member.ExitReason);
    }

    void putSavingTransactionValues(SavingTransaction savingTransaction,ContentValues values)
    {
        values.put(Columns.SAVINGACCTRANSACTIONS_GroupId,savingTransaction.GroupId);
        values.put(Columns.SAVINGACCTRANSACTIONS_Type, savingTransaction.Type);
        values.put(Columns.SAVINGACCTRANSACTIONS_Amount, savingTransaction.Amount);
        values.put(Columns.SAVINGACCTRANSACTIONS_CurrentBalance, savingTransaction.CurrentBalance);
        values.put(Columns.SAVINGACCTRANSACTIONS_DateTime,savingTransaction.DateTime);
        values.put(Columns.SAVINGACCTRANSACTIONS_MeetingId, savingTransaction.MeetingId);
        values.put(Columns.SAVINGACCTRANSACTIONS_SavingAccountId, savingTransaction.SavingAccountId);
    }

    void putLoanTransactionValues(LoanTransaction loanTransaction,ContentValues values)
    {
        values.put(Columns.LOANACCTRANSACTIONS_GroupId,loanTransaction.GroupId);
        values.put(Columns.LOANACCTRANSACTIONS_MeetingId, loanTransaction.MeetingId);
        values.put(Columns.LOANACCTRANSACTIONS_LoanAccountId, loanTransaction.LoanAccountId);
        values.put(Columns.LOANACCTRANSACTIONS_Repayment, loanTransaction.Repayment);
        values.put(Columns.LOANACCTRANSACTIONS_CurrentOutstanding,loanTransaction.getUpdatedOutstanding());
        values.put(Columns.LOANACCTRANSACTIONS_DateTime,loanTransaction.DateTime);

    }

    void putSavingAccountValues(SavingsAccount savingsAccount, ContentValues values)
    {
        values.put(Columns.SAVINGACCOUNTS_CompulsorySavings, savingsAccount.GroupId);
        values.put(Columns.SAVINGACCOUNTS_GroupId,savingsAccount.GroupId);
        values.put(Columns.SAVINGACCOUNTS_Id, savingsAccount.Id);
        values.put(Columns.SAVINGACCOUNTS_OptionalSavings, savingsAccount.OptionalSavings);
        values.put(Columns.SAVINGACCOUNTS_MemberId, savingsAccount.MemberId);
    }

    void putGroupMeetingValues(GroupMeeting groupMeeting, ContentValues values)
    {
        values.put(Columns.GROUPMEETING_Id,groupMeeting.Id);
        values.put(Columns.GROUPMEETING_Date,groupMeeting.Date);
        values.put(Columns.GROUPMEETING_FieldOfficerId,groupMeeting.FieldOfficerId);
        values.put(Columns.GROUPMEETING_GroupId,groupMeeting.GroupId);
    }

    void putLoanAccountValues(LoanAccount la, ContentValues values)
    {
        values.put(Columns.LOANACCOUNTS_Id, la.Id);
        values.put(Columns.LOANACCOUNTS_MemberId, la.MemberId);
        values.put(Columns.LOANACCOUNTS_GroupId, la.GroupId);
        values.put(Columns.LOANACCOUNTS_GroupMeetingId, la.GroupMeetingId);
        values.put(Columns.LOANACCOUNTS_PrincipalAmount, la.Principal);
        values.put(Columns.LOANACCOUNTS_InterestRate, la.InterestRate);
        values.put(Columns.LOANACCOUNTS_PeriodInMonths, la.PeriodInMonths);
        values.put(Columns.LOANACCOUNTS_EMI, la.EMI);
        values.put(Columns.LOANACCOUNTS_Outstanding, la.Outstanding);
        values.put(Columns.LOANACCOUNTS_Reason, la.Reason);
        values.put(Columns.LOANACCOUNTS_GUARANTOR, la.Guarantor);
        values.put(Columns.LOANACCOUNTS_IsEmergency, la.IsEmergency);
        values.put(Columns.LOANACCOUNTS_StartDate, la.StartDate);
        values.put(Columns.LOANACCOUNTS_EndDate, la.EndDate);
        values.put(Columns.LOANACCOUNTS_CreatedDate, la.CreatedDate);
        values.put(Columns.LOANACCOUNTS_CreatedBy, la.CreatedBy);
        values.put(Columns.LOANACCOUNTS_Active, la.Active);
    }


}
