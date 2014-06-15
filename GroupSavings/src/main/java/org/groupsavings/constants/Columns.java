package org.groupsavings.constants;

/**
 * Created by shashank on 14/6/14.
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

    //---------------- Member Table ---------------------------//
    public static final String MEMBER_ID = "Id";
    public static final String MEMBER_GroupID = "GroupId";
    public static final String MEMBER_FirstName = "FirstName";
    public static final String MEMBER_LastName = "LastName";
    public static final String MEMBER_GuardianName = "GuardianName";
    public static final String MEMBER_Gender = "Gender";
    public static final String MEMBER_DOB = "DOB";
    public static final String MEMBER_EmailId = "EmailId";
    public static final String MEMBER_Active = "Active";
    public static final String MEMBER_ContactNumber = "ContactNumber";
    public static final String MEMBER_AddressLine1 = "AddressLine1";
    public static final String MEMBER_AddressLine2 = "AddressLine2";
    public static final String MEMBER_Occupation = "Occupation";
    public static final String MEMBER_AnnualIncome = "AnnualIncome";
    public static final String MEMBER_Education = "Education";
    public static final String MEMBER_Disability = "Disability";
    public static final String MEMBER_NoOfFamilyMembers = "NoOfFamilyMembers";
    public static final String MEMBER_Nominee = "Nominee";
    public static final String MEMBER_Passbook = "Passbook";
    public static final String MEMBER_Insurance = "Insurance";
    public static final String MEMBER_ExitDate = "ExitDate";
    public static final String MEMBER_ExitReason = "ExitReason";
    public static final String MEMBER_CreatedDate = "CreatedDate";
    public static final String MEMBER_CreatedBy = "CreatedBy";
    public static final String MEMBER_ModifiedDate = "ModifiedDate";
    public static final String MEMBER_ModifiedBy = "ModifiedBy";

    //------------------ Group Meetings Table ---------------------//
    public static final String GROUPMEETING_Id = "Id";
    public static final String GROUPMEETING_GroupId = "GroupId";
    public static final String GROUPMEETING_Date = "Date";
    public static final String GROUPMEETING_FieldOfficerId = "FieldOfficerId";

    //------------------ MeetingDetails Table ---------------------//
    public static final String MEETINGDETAILS_MeetingId = "MeetingId";
    public static final String MEETINGDETAILS_MemberId = "MemberId";
    public static final String MEETINGDETAILS_IsAbsent = "IsAbsent";
    public static final String MEETINGDETAILS_Fine = "Fine";

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
    public static final String FIELDOFFICERS_PasswordHash = "PasswordHash";

}
