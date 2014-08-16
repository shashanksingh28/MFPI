package org.groupsavings.constants;

/**
 * Created by shashank on 15/6/14.
 */
public class Columns {

    //--------------- Group Table -------------------------//
    public static final String GROUP_Id = "Id";
    public static final String GROUP_Name = "Name";
    public static final String GROUP_PresidentId = "PresidentId";
    public static final String GROUP_SecretaryId = "SecretaryId";
    public static final String GROUP_TreasurerId = "TreasurerId";
    public static final String GROUP_FieldOfficerId = "FieldOfficerId";
    public static final String GROUP_Active = "Active";
    public static final String GROUP_MonthlyCompulsoryAmount = "MonthlyCompulsoryAmount";
    public static final String GROUP_MonthlyMeetingDate = "MonthlyMeetingDate";
    public static final String GROUP_Bank = "Bank";
    public static final String GROUP_CummulativeSavings = "CummulativeSavings";
    public static final String GROUP_OtherIncome = "OtherIncome";
    public static final String GROUP_OutstandingLoans = "OutstandingLoans";
    public static final String GROUP_DateOfFormation = "DateOfFormation";
    public static final String GROUP_NoOfSubgroups = "NoOfSubgroups";
    public static final String GROUP_AddressLine1 = "AddressLine1";
    public static final String GROUP_AddressLine2 = "AddressLine2";
    public static final String GROUP_City = "City";
    public static final String GROUP_State = "State";
    public static final String GROUP_Country = "Country";
    public static final String GROUP_ClusterId = "ClusterId";
    public static final String GROUP_CreatedDate = "CreatedDate";
    public static final String GROUP_CreatedBy = "CreatedBy";
    public static final String GROUP_ModifiedDate = "ModifiedDate";
    public static final String GROUP_ModifiedBy = "ModifiedBy";
    public static final String GROUP_NoOfActiveMembers = "NoOfActiveMembers";

    //---------------- Member Table ---------------------------//
    public static final String MEMBERS_Id = "Id";
    public static final String MEMBERS_GroupId = "GroupId";
    public static final String MEMBERS_FirstName = "FirstName";
    public static final String MEMBERS_LastName = "LastName";
    public static final String MEMBERS_GuardianName = "GuardianName";
    public static final String MEMBERS_Gender = "Gender";
    public static final String MEMBERS_DOB = "DOB";
    public static final String MEMBERS_EmailId = "EmailId";
    public static final String MEMBERS_Active = "Active";
    public static final String MEMBERS_ContactNumber = "ContactNumber";
    public static final String MEMBERS_AddressLine1 = "AddressLine1";
    public static final String MEMBERS_AddressLine2 = "AddressLine2";
    public static final String MEMBERS_Occupation = "Occupation";
    public static final String MEMBERS_AnnualIncome = "AnnualIncome";
    public static final String MEMBERS_EconomicCondition = "EconomicCondition";
    public static final String MEMBERS_Education = "Education";
    public static final String MEMBERS_Disability = "Disability";
    public static final String MEMBERS_NoOfFamilyMembers = "NoOfFamilyMembers";
    public static final String MEMBERS_Nominee = "Nominee";
    public static final String MEMBERS_Passbook = "Passbook";
    public static final String MEMBERS_Insurance = "Insurance";
    public static final String MEMBERS_ExitDate = "ExitDate";
    public static final String MEMBERS_ExitReason = "ExitReason";
    public static final String MEMBERS_CreatedDate = "CreatedDate";
    public static final String MEMBERS_CreatedBy = "CreatedBy";
    public static final String MEMBERS_ModifiedDate = "ModifiedDate";
    public static final String MEMBERS_ModifiedBy = "ModifiedBy";
    public static final String MEMBERS_CurrentSavings = "CurrentSavings";
    public static final String MEMBERS_CurrentOutstanding = "CurrentOutstanding";

    //------------------ Group Meetings Table ---------------------//
    public static final String GROUPMEETING_Id = "Id";
    public static final String GROUPMEETING_GroupId = "GroupId";
    public static final String GROUPMEETING_Date = "Date";
    public static final String GROUPMEETING_FieldOfficerId = "FieldOfficerId";

    //------------------ MeetingDetails Table ---------------------//
    public static final String MEETINGDETAILS_MeetingId = "MeetingId";
    public static final String MEETINGDETAILS_MemberId = "MemberId";
    public static final String MEETINGDETAILS_Attended = "Attended";
    public static final String MEETINGDETAILS_Fine = "Fine";
    public static final String MEETINGDETAILS_FineReason = "FineReason";

    //------------------ SavingAccounts Table ---------------------//
    public static final String SAVINGACCOUNTS_Id = "Id";
    public static final String SAVINGACCOUNTS_MemberId = "MemberId";
    public static final String SAVINGACCOUNTS_GroupId = "GroupId";
    public static final String SAVINGACCOUNTS_CompulsorySavings = "CompulsorySavings";
    public static final String SAVINGACCOUNTS_OptionalSavings = "OptionalSavings";
    public static final String SAVINGACCOUNTS_InterestAccumulated = "InterestAccumulated";
    public static final String SAVINGACCOUNTS_CurrentBalance = "CurrentBalance";
    public static final String SAVINGACCOUNTS_Active = "Active";
    public static final String SAVINGACCOUNTS_CreatedDate = "CreatedDate";
    public static final String SAVINGACCOUNTS_CreatedBy = "CreatedBy";
    public static final String SAVINGACCOUNTS_ModifiedDate = "ModifiedDate";
    public static final String SAVINGACCOUNTS_ModifiedBy = "ModifiedBy";

    //------------------- SAVINGACCTRANSACTIONSs Table ----------------//
    public static final String SAVINGACCTRANSACTIONS_GroupId = "GroupId";
    public static final String SAVINGACCTRANSACTIONS_MeetingId = "MeetingId";
    public static final String SAVINGACCTRANSACTIONS_SavingAccountId = "SavingAccountId";
    public static final String SAVINGACCTRANSACTIONS_Type = "Type";
    public static final String SAVINGACCTRANSACTIONS_Amount = "Amount";
    public static final String SAVINGACCTRANSACTIONS_CurrentBalance = "CurrentBalance";
    public static final String SAVINGACCTRANSACTIONS_DateTime = "DateTime";

    public static final char SAVINGACCTRANSACTIONS_COMPULSORY ='C';
    public static final char SAVINGACCTRANSACTIONS_OPTIONAL ='O';
    public static final char SAVINGACCTRANSACTIONS_WITHDRAWAL ='W';

    //------------------- LoanAccounts Table ----------------//
    public static final String LOANACCOUNTS_Id = "Id";
    public static final String LOANACCOUNTS_MemberId = "MemberId";
    public static final String LOANACCOUNTS_GroupId = "GroupId";
    public static final String LOANACCOUNTS_GroupMeetingId = "GroupMeetingId";
    public static final String LOANACCOUNTS_PrincipalAmount = "PrincipalAmount";
    public static final String LOANACCOUNTS_InterestRate = "InterestRate";
    public static final String LOANACCOUNTS_PeriodInMonths = "PeriodInMonths";
    public static final String LOANACCOUNTS_EMI = "EMI";
    public static final String LOANACCOUNTS_Outstanding = "Outstanding";
    public static final String LOANACCOUNTS_Reason = "Reason";
    public static final String LOANACCOUNTS_GUARANTOR = "Guarantor";
    public static final String LOANACCOUNTS_IsEmergency = "IsEmergency";
    public static final String LOANACCOUNTS_StartDate = "StartDate";
    public static final String LOANACCOUNTS_EndDate = "EndDate";
    public static final String LOANACCOUNTS_Active = "Active";
    public static final String LOANACCOUNTS_CreatedDate = "CreatedDate";
    public static final String LOANACCOUNTS_CreatedBy = "CreatedBy";
    public static final String LOANACCOUNTS_ModifiedDate = "ModifiedDate";
    public static final String LOANACCOUNTS_ModifiedBy = "ModifiedBy";

    //------------------- LoanTransactions Table ----------------//
    public static final String LOANACCTRANSACTIONS_GroupId = "GroupId";
    public static final String LOANACCTRANSACTIONS_MeetingId = "MeetingId";
    public static final String LOANACCTRANSACTIONS_LoanAccountId = "GroupMemberLoanId";
    public static final String LOANACCTRANSACTIONS_Repayment = "Repayment";
    public static final String LOANACCTRANSACTIONS_CurrentOutstanding = "CurrentOutstanding";
    public static final String LOANACCTRANSACTIONS_DateTime = "DateTime";

    //-------------------- Field Officers Table ------------------//
    public static final String FIELDOFFICERS_Id = "Id";
    public static final String FIELDOFFICERS_Name = "Name";
    public static final String FIELDOFFICERS_UserName = "UserName";
    public static final String FIELDOFFICERS_PasswordHash = "PasswordHash";
}
