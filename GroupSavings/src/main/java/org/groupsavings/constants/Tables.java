package org.groupsavings.constants;

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
            + Columns.GROUP_Active + " BOOLEAN,"
            + Columns.GROUP_MonthlyCompulsoryAmount + " INTEGER,"
            + Columns.GROUP_MonthlyMeetingDate + " TIMESTAMP,"
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
    
    public static final String DROP_TABLE_Groups = "Drop Table "+GROUPS +";";
    

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
            + " (" + Columns.MEMBER_ID + " TEXT PRIMARY KEY,"
            + Columns.MEMBER_GroupID + " TEXT,"
            + Columns.MEMBER_FirstName + " TEXT,"
            + Columns.MEMBER_LastName + " TEXT,"
            + Columns.MEMBER_GuardianName + " TEXT,"
            + Columns.MEMBER_Gender + " TEXT,"
            + Columns.MEMBER_DOB + " TEXT,"
            + Columns.MEMBER_EmailId + " TEXT,"
            + Columns.MEMBER_Active + " BOOLEAN,"
            + Columns.MEMBER_ContactNumber + " TEXT,"
            + Columns.MEMBER_AddressLine1 + " TEXT,"
            + Columns.MEMBER_AddressLine2 + " TEXT,"
            + Columns.MEMBER_Occupation + " TEXT,"
            + Columns.MEMBER_AnnualIncome + " TEXT,"
            + Columns.MEMBER_EconomicCondition + " TEXT,"
            + Columns.MEMBER_Education + " TEXT,"
            + Columns.MEMBER_Disability + " BOOLEAN,"
            + Columns.MEMBER_NoOfFamilyMembers + " INTEGER,"
            + Columns.MEMBER_Nominee + " TEXT,"
            + Columns.MEMBER_Passbook + " TEXT,"
            + Columns.MEMBER_Insurance + " BOOLEAN,"
            + Columns.MEMBER_ExitDate + " TIMESTAMP,"
            + Columns.MEMBER_ExitReason + " TEXT,"
            + Columns.MEMBER_CreatedDate + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + Columns.MEMBER_CreatedBy + " TEXT,"
            + Columns.MEMBER_ModifiedDate + " TIMESTAMP,"
            + Columns.MEMBER_ModifiedBy + " TEXT"
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
            + Columns.SAVINGACCOUNTS_Active + " BOOLEAN"
            + ");";

    public static final String SAVINGACCTRANSACTIONSS = "SAVINGACCTRANSACTIONSs";

    public static final String CREATE_TABLE_SAVINGACCTRANSACTIONS = "CREATE TABLE " + SAVINGACCTRANSACTIONSS
            + " (" + Columns.SAVINGACCTRANSACTIONS_GroupId + " TEXT,"
            + Columns.SAVINGACCTRANSACTIONS_MeetingId + " TEXT,"
            + Columns.SAVINGACCTRANSACTIONS_SavingAccountId + "TEXT,"
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
            + Columns.LOANACCOUNTS_Active + " BOOLEAN"
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
            + Columns.FIELDOFFICERS_PasswordHash + " TEXT"
            + ");";

}
