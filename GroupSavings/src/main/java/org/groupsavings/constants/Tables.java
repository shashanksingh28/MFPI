package org.groupsavings.constants;

import android.text.format.Time;

import org.groupsavings.domain.Group;
import org.groupsavings.domain.GroupMeeting;
import org.groupsavings.domain.Member;
import org.groupsavings.domain.SavingsAccount;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CREATEd by shashank on 14/6/14.
 */
public class Tables {

    public static final String GROUPS = "Groups";

    public static final String CREATE_TABLE_Groups = "CREATE TABLE " + GROUPS
            + " (" + Columns.GROUP_Id + " TEXT PRIMARY KEY,"
            + Columns.GROUP_Name + " TEXT,"
            + Columns.GROUP_PresidentId + " TEXT,"
            + Columns.GROUP_SecretaryId + " TEXT,"
            + Columns.GROUP_TreasurerId + " TEXT,"
            + Columns.GROUP_FieldOfficerId + " TEXT,"
            + Columns.GROUP_Active + " BOOLEAN DEFAULT 1,"
            + Columns.GROUP_MonthlyCompulsoryAmount + " INTEGER,"
            + Columns.GROUP_MonthlyMeetingDate + " INTEGER,"
            + Columns.GROUP_Bank + " TEXT,"
            + Columns.GROUP_ClusterId + " INTEGER,"
            + Columns.GROUP_CummulativeSavings + " LONG,"
            + Columns.GROUP_OtherIncome + " LONG,"
            + Columns.GROUP_OutstandingLoans + " LONG,"
            + Columns.GROUP_DateOfFormation + " TIMESTAMP,"
            + Columns.GROUP_NoOfSubgroups + " INTEGER,"
            + Columns.GROUP_AddressLine1 + " TEXT,"
            + Columns.GROUP_AddressLine2 + " TEXT,"
            + Columns.GROUP_City + " TEXT,"
            + Columns.GROUP_State + " TEXT,"
            + Columns.GROUP_Country + " TEXT,"
            + Columns.GROUP_CreatedDate + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + Columns.GROUP_CreatedBy + " TEXT,"
            + Columns.GROUP_ModifiedDate + " TIMESTAMP,"
            + Columns.GROUP_ModifiedBy + " TEXT"
            + ");";

    public static final String GROUPMEETINGS = "GroupMeetings";

    public static final String CREATE_TABLE_GROUPMEETINGS = "CREATE TABLE " + GROUPMEETINGS
            + " (" + Columns.GROUPMEETING_Id + " TEXT PRIMARY KEY,"
            + Columns.GROUPMEETING_GroupId + " TEXT,"
            + Columns.GROUPMEETING_Date + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + Columns.GROUPMEETING_FieldOfficerId + " TEXT"
            + ");";

    public static final String MEETINGDETAILS = "MeetingDetails";
    public static final String CREATE_TABLE_MEETINGDETAILS = "CREATE TABLE "+ MEETINGDETAILS
            + " (" + Columns.MEETINGDETAILS_MeetingId + " TEXT,"
            + Columns.MEETINGDETAILS_MemberId + " TEXT,"
            + Columns.MEETINGDETAILS_IsAbsent + " BOOLEAN,"
            + Columns.MEETINGDETAILS_Fine + " INTEGER"
            + ");";

    public static final String MEMBERS = "Members";
    
    public static final String CREATE_TABLE_MEMBERS = "CREATE TABLE " + MEMBERS
            + " (" + Columns.MEMBERS_Id + " TEXT PRIMARY KEY,"
            + Columns.MEMBERS_GroupId + " TEXT,"
            + Columns.MEMBERS_FirstName + " TEXT,"
            + Columns.MEMBERS_LastName + " TEXT,"
            + Columns.MEMBERS_GuardianName + " TEXT,"
            + Columns.MEMBERS_Gender + " TEXT,"
            + Columns.MEMBERS_DOB + " TEXT,"
            + Columns.MEMBERS_EmailId + " TEXT,"
            + Columns.MEMBERS_Active + " BOOLEAN DEFAULT 1,"
            + Columns.MEMBERS_ContactNumber + " TEXT,"
            + Columns.MEMBERS_AddressLine1 + " TEXT,"
            + Columns.MEMBERS_AddressLine2 + " TEXT,"
            + Columns.MEMBERS_Occupation + " TEXT,"
            + Columns.MEMBERS_AnnualIncome + " TEXT,"
            + Columns.MEMBERS_EconomicCondition + " TEXT,"
            + Columns.MEMBERS_Education + " TEXT,"
            + Columns.MEMBERS_Disability + " BOOLEAN,"
            + Columns.MEMBERS_NoOfFamilyMembers + " INTEGER,"
            + Columns.MEMBERS_Nominee + " TEXT,"
            + Columns.MEMBERS_Passbook + " TEXT,"
            + Columns.MEMBERS_Insurance + " BOOLEAN,"
            + Columns.MEMBERS_ExitDate + " TIMESTAMP,"
            + Columns.MEMBERS_ExitReason + " TEXT,"
            + Columns.MEMBERS_CreatedDate + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + Columns.MEMBERS_CreatedBy + " TEXT,"
            + Columns.MEMBERS_ModifiedDate + " TIMESTAMP,"
            + Columns.MEMBERS_ModifiedBy + " TEXT"
            + ");";

    public static final String SAVINGACCOUNTS = "SavingAccounts";

    public static final String CREATE_TABLE_SAVINGACCOUNTS = "CREATE TABLE " + SAVINGACCOUNTS
            + " (" + Columns.SAVINGACCOUNTS_Id + " TEXT PRIMARY KEY,"
            + Columns.SAVINGACCOUNTS_GroupId + " TEXT,"
            + Columns.SAVINGACCOUNTS_MemberId + " TEXT,"
            + Columns.SAVINGACCOUNTS_CompulsorySavings + " INTEGER,"
            + Columns.SAVINGACCOUNTS_OptionalSavings + " INTEGER,"
            + Columns.SAVINGACCOUNTS_InterestAccumulated + " INTEGER,"
            + Columns.SAVINGACCOUNTS_CurrentBalance + " INTEGER,"
            + Columns.SAVINGACCOUNTS_CreatedDate + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + Columns.SAVINGACCOUNTS_CreatedBy + " TEXT,"
            + Columns.SAVINGACCOUNTS_Active + " BOOLEAN DEFAULT 1"
            + ");";

    public static final String SAVINGACCTRANSACTIONS = "SavingAccTransactions";

    public static final String CREATE_TABLE_SAVINGACCTRANSACTIONS = "CREATE TABLE " + SAVINGACCTRANSACTIONS
            + " (" + Columns.SAVINGACCTRANSACTIONS_GroupId + " TEXT,"
            + Columns.SAVINGACCTRANSACTIONS_MeetingId + " TEXT,"
            + Columns.SAVINGACCTRANSACTIONS_SavingAccountId + " TEXT,"
            + Columns.SAVINGACCTRANSACTIONS_Type + " TEXT,"
            + Columns.SAVINGACCTRANSACTIONS_Amount + " INTEGER,"
            + Columns.SAVINGACCTRANSACTIONS_CurrentBalance + " INTEGER,"
            + Columns.SAVINGACCTRANSACTIONS_DateTime + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            + ");";

    public static final String LOANACCOUNTS = "LoanAccounts";

    public static final String CREATE_TABLE_LOANACCOUNTS = "CREATE TABLE " + LOANACCOUNTS
            + " (" + Columns.LOANACCOUNTS_Id + " TEXT PRIMARY KEY,"
            + Columns.LOANACCOUNTS_MemberId + " TEXT,"
            + Columns.LOANACCOUNTS_GroupId + " TEXT,"
            + Columns.LOANACCOUNTS_GroupMeetingId + " TEXT,"
            + Columns.LOANACCOUNTS_PrincipalAmount + " INTEGER,"
            + Columns.LOANACCOUNTS_InterestRate + " INTEGER,"
            + Columns.LOANACCOUNTS_PeriodInMonths + " INTEGER,"
            + Columns.LOANACCOUNTS_EMI + " INTEGER,"
            + Columns.LOANACCOUNTS_Outstanding + " INTEGER,"
            + Columns.LOANACCOUNTS_Reason + " TEXT,"
            + Columns.LOANACCOUNTS_GUARANTOR + " TEXT,"
            + Columns.LOANACCOUNTS_IsEmergency + " BOOLEAN,"
            + Columns.LOANACCOUNTS_StartDate + " TEXT,"
            + Columns.LOANACCOUNTS_EndDate + " TEXT,"
            + Columns.LOANACCOUNTS_CreatedDate + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + Columns.LOANACCOUNTS_CreatedBy + " TEXT,"
            + Columns.LOANACCOUNTS_Active + " BOOLEAN DEFAULT 1"
            + ");";

    public static final String LOANACCTRANSACTIONS = "LoanAccTransactions";

    public static final String CREATE_TABLE_LOANTRANSACTIONS = "CREATE TABLE " + LOANACCTRANSACTIONS
            + " (" + Columns.LOANACCTRANSACTIONS_GroupId + " TEXT,"
            + Columns.LOANACCTRANSACTIONS_MeetingId + " TEXT,"
            + Columns.LOANACCTRANSACTIONS_LoanAccountId + " TEXT,"
            + Columns.LOANACCTRANSACTIONS_Repayment + " INTEGER,"
            + Columns.LOANACCTRANSACTIONS_CurrentOutstanding + " INTEGER,"
            + Columns.LOANACCTRANSACTIONS_DateTime + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            + ");";

    public static final String FIELDOFFICERS = "FieldOfficers";

    public static final String CREATE_TABLE_FIELDOFFICERS = "CREATE TABLE " + FIELDOFFICERS
            + " (" + Columns.FIELDOFFICERS_Id + " INTEGER,"
            + Columns.FIELDOFFICERS_Name + " TEXT,"
            + Columns.FIELDOFFICERS_UserName + " TEXT UNIQUE,"
            + Columns.FIELDOFFICERS_PasswordHash + " TEXT"
            + ");";

    //----------------------- Unique Id related functions -------------------------//

    public static String getUniqueId(Group group) {
        Time now = new Time();
        now.setToNow();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss");
        return group.Name + "_" + sdf.format(new Date());
    }

    public static String getUniqueId(Member member) {
        return member.FirstName +"_"+ member.GuardianName +"_"+ member.LastName +"_"+ member.GroupId;
    }

    public static String getUniqueId(GroupMeeting groupMeeting) {
        return getTimestampUniqueId();
    }

    public static String getUniqueId(SavingsAccount savingsAccount) {
        return getTimestampUniqueId();
    }

    // Timestamp be used for accounts and meetings
    public static String getTimestampUniqueId() {
        return String.valueOf(System.currentTimeMillis());
    }

}
