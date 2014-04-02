package org.groupsavings;

import org.groupsavings.domain.Group;
import org.groupsavings.domain.Member;
import org.groupsavings.domain.SavingTransaction;
import org.groupsavings.domain.SavingsAccount;
import org.groupsavings.handlers.DatabaseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shashank on 16/3/14.
 */
public class SyncHelper {

    // Name Value pairs for All Groups
    public static JSONObject getJsonGroup(Group group)
    {
        JSONObject collectJSON = new JSONObject();
        try {

            collectJSON.put(DatabaseHandler.COLUMN_GROUP_HashId, group.UID);
            collectJSON.put(DatabaseHandler.COLUMN_GROUP_Name, group.GroupName);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_PresidentId,group.PresidentId);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_SecretaryId ,);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_TreasurerId ,);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_FieldOfficerId ,);
            collectJSON.put(DatabaseHandler.COLUMN_GROUP_CreatedDate, group.CreatedAt);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_CreatedBy,);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_Active ,);
            collectJSON.put(DatabaseHandler.COLUMN_GROUP_RecurringIndividualAmount, group.RecurringSavings);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_MonthlyMeetingDate ,);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_ClusterId ,);
            collectJSON.put(DatabaseHandler.COLUMN_GROUP_AddressLine1, group.Address);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_AddressLine2,);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_City ,);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_State ,);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_Country ,);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }

    // Name Value pairs for All Members
    public static JSONObject getJsonMember(Member member) {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_UID, member.UID);
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_GroupUID, member.GroupUID);
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_FirstName, member.FirstName);
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_LastName, member.LastName);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_Sex,);
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_Age, member.age);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_EmailId,);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_Active ,);
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_TotalSavings, member.TotalSavings);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_TotalDebt ,);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_CreatedDate, member.);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_CreatedBy ,);
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_ContactNumber, member.ContactInfo);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_AddressLine1 ,);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_AddressLine2 ,);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_City ,);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_State ,);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_Country ,);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }

    // Name Value pairs for Saving Accounts
    public static JSONObject getJsonSavingAcc(SavingsAccount savingAccount) {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(DatabaseHandler.COLUMN_SAVING_ACCOUNT_Id, savingAccount.Id);
            collectJSON.put(DatabaseHandler.COLUMN_SAVING_ACCOUNT_GroupId, savingAccount.groupId);
            collectJSON.put(DatabaseHandler.COLUMN_SAVING_ACCOUNT_MemberId, savingAccount.memberId);
            collectJSON.put(DatabaseHandler.COLUMN_SAVING_ACCOUNT_TotalSaving, savingAccount.TotalSavings);
            //collectJSON.put(DatabaseHandler.COLUMN_SAVING_ACCOUNT_InterestAccumulated ,);
            //collectJSON.put(DatabaseHandler.COLUMN_SAVING_ACCOUNT_CreatedDate, );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }

    // Name Value pairs for  Saving Transactions
    public static JSONObject getJsonSavingTrans(SavingTransaction savingTransaction) {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(DatabaseHandler.COLUMN_SAVINGTRANSACTION_Id, savingTransaction.Id);
            collectJSON.put(DatabaseHandler.COLUMN_SAVINGTRANSACTION_GroupMeetingId, savingTransaction.grpMeetingId);
            collectJSON.put(DatabaseHandler.COLUMN_SAVINGTRANSACTION_GroupMemberSavingId, savingTransaction.memberSavingAccId);
            collectJSON.put(DatabaseHandler.COLUMN_SAVINGTRANSACTION_Amount, savingTransaction.Amount);
            //collectJSON.put(DatabaseHandler.COLUMN_SAVINGTRANSACTION_DateTime ,);
            //collectJSON.put(DatabaseHandler.COLUMN_SAVINGTRANSACTION_SignedBy ,);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }	

/* Commenting out for POC

	// Name Value pairs for Loan Accounts
	public static JSONObject getJsonLoanAcc(LoanAccount loanAccount)
    {
        JSONObject collectJSON = new JSONObject();
        try {
			collectJSON.put(COLUMN_LOANACCOUNT_Id ,);
			collectJSON.put(COLUMN_LOANACCOUNT_GroupId ,);
			collectJSON.put(COLUMN_LOANACCOUNT_MemberId ,);
			collectJSON.put(COLUMN_LOANACCOUNT_PrincipalAmount ,);
			collectJSON.put(COLUMN_LOANACCOUNT_InterestRate ,);
			collectJSON.put(COLUMN_LOANACCOUNT_StartDate ,);
			collectJSON.put(COLUMN_LOANACCOUNT_EndDate ,);
			collectJSON.put(COLUMN_LOANACCOUNT_Outstanding ,);
			collectJSON.put(COLUMN_LOANACCOUNT_Reason ,);
			collectJSON.put(COLUMN_LOANACCOUNT_InstallmentAmount ,);
			collectJSON.put(COLUMN_LOANACCOUNT_NoOfInstallments ,);
			collectJSON.put(COLUMN_LOANACCOUNT_CreatedDate ,);		
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }	
	
	// Name Value pairs for Loan Transactions
	public static JSONObject getJsonLoanTrans(LoanTransaction loanTransaction)
    {
        JSONObject collectJSON = new JSONObject();
        try {
			collectJSON.put(COLUMN_LOAN_TRANSACTION_Id ,);
			collectJSON.put(COLUMN_LOAN_TRANSACTION_GroupId ,);
			collectJSON.put(COLUMN_LOAN_TRANSACTION_GroupMemberLoanId ,);
			collectJSON.put(COLUMN_LOAN_TRANSACTION_Repayment ,);
			collectJSON.put(COLUMN_LOAN_TRANSACTION_DateTime ,);
			collectJSON.put(COLUMN_LOAN_TRANSACTION_SignedBy ,);			
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }	

*/

    // All Groups
    public static JSONArray GetAllGroupsJSON(ArrayList<Group> groups)
    {
        JSONArray jsonArray = new JSONArray();
        for (Group group : groups) {
            jsonArray.put(getJsonGroup(group));
        }
        return jsonArray;
    }

    // All Members
    public static JSONArray GetAllMembersJSON(ArrayList<Member> members) {
        JSONArray jsonArray = new JSONArray();
        for (Member member : members) {
            jsonArray.put(getJsonMember(member));
        }
        return jsonArray;
    }

    // All Saving Transactions
    public static JSONArray GetAllSavingTransJSON(ArrayList<SavingTransaction> savingTransactions) {
        JSONArray jsonArray = new JSONArray();
        for (SavingTransaction savingTransaction : savingTransactions) {
            jsonArray.put(getJsonSavingTrans(savingTransaction));
        }
        return jsonArray;
    }

    // All Saving Account
    public static JSONArray GetAllSavingAccJSON(ArrayList<SavingsAccount> savingAccounts) {
        JSONArray jsonArray = new JSONArray();
        for (SavingsAccount savingAccount : savingAccounts) {
            jsonArray.put(getJsonSavingAcc(savingAccount));
        }
        return jsonArray;
    }


/* Commenting out for POC
	
	// All Loan Transaction
	public static JSONArray GetAllLoanTransJSON(ArrayList<LoanTransaction> loanTransactions)
    {
        JSONArray jsonArray = new JSONArray();
        for (LoanTransaction loanTransaction : loanTransactions) {
            jsonArray.put(getJsonLoanTrans(loanTransaction));
        }
        return jsonArray;
    }
	
	// All Loan Accounts
	public static JSONArray GetAllLoanAccJSON(ArrayList<LoanAccount> loanAccounts)
    {
        JSONArray jsonArray = new JSONArray();
        for (LoanAccount loanAccount : loanAccounts) {
            jsonArray.put(getJsonLoanAcc(loanAccount));
        }
        return jsonArray;
    }
	*/
}