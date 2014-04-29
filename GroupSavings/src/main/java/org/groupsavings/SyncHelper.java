package org.groupsavings;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.ContactsContract;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.groupsavings.domain.Group;
import org.groupsavings.domain.GroupMeeting;
import org.groupsavings.domain.LoanAccount;
import org.groupsavings.domain.LoanTransaction;
import org.groupsavings.domain.Member;
import org.groupsavings.domain.SavingTransaction;
import org.groupsavings.domain.SavingsAccount;
import org.groupsavings.handlers.DatabaseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Shashank on 16/3/14.
 */
public class SyncHelper {

    public static final String SERVER_URL="http://planindiatest.webatu.com/AndroidSync";
    public DatabaseHandler db_handler;
    private Context context;

    public SyncHelper(Context context) {
        this.context = context;
        db_handler = new DatabaseHandler(context);
    }

    // If network connectivity unavailable, gracefully return
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Function that pushes objects to server.
    public String synchronize()
    {
        if(!isNetworkAvailable()) return "Cannot reach Server. Try after connectivity is available.";

        String returnMessage = "";
        //byte[] responseString = new byte[4056];
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            BasicHeader http_JSON_header = new BasicHeader(HTTP.CONTENT_TYPE, "application/json");
            HttpResponse response;
            StringEntity s;

            JSONArray allGroupsJSON = GetAllGroupsJSON(db_handler.getAllGroups());
            s = new StringEntity(allGroupsJSON.toString());
            s.setContentEncoding(http_JSON_header);
            HttpPost httppost = new HttpPost(SERVER_URL+"/SaveGroup.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);

            JSONArray allmembersJSON = GetAllMembersJSON(db_handler.getAllMembers());
            s = new StringEntity(allmembersJSON.toString());
            s.setContentEncoding(http_JSON_header);
            httppost = new HttpPost(SERVER_URL+"/SaveMembers.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);

            JSONArray allsavingsJSON = GetAllSavingAccJSON(db_handler.getAllSavingAccounts());
            s = new StringEntity(allsavingsJSON.toString());
            s.setContentEncoding(http_JSON_header);
            httppost = new HttpPost(SERVER_URL+"/SavingAccount.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);

            JSONArray allsavingTransJSON = GetAllSavingTransJSON(db_handler.getAllSavingTrans());
            s = new StringEntity(allsavingTransJSON.toString());
            s.setContentEncoding(http_JSON_header);
            httppost = new HttpPost(SERVER_URL+"/SavingTransaction.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);

            JSONArray allLoanTransJSON = GetAllLoanTransJSON(db_handler.getAllLoanTrans());
            s = new StringEntity(allLoanTransJSON.toString());
            s.setContentEncoding(http_JSON_header);
            httppost = new HttpPost(SERVER_URL+"/LoanTransaction.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);

            JSONArray allLoanAccountJSON = GetAllLoanAccJSON(db_handler.getAllLoanAccount());
            s = new StringEntity(allLoanAccountJSON.toString());
            s.setContentEncoding(http_JSON_header);
            httppost = new HttpPost(SERVER_URL+"/LoanAccount.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);

            JSONArray allGrpMeetingJSON = GetAllGroupMeetingJSON(db_handler.getAllMeetingDetails());
            s = new StringEntity(allGrpMeetingJSON.toString());
            s.setContentEncoding(http_JSON_header);
            httppost = new HttpPost(SERVER_URL+"/GroupMeetings.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);
            if(response!=null){
                InputStream in = response.getEntity().getContent();
                //responseString = new byte[100000];
                //in.read(responseString);
            }
        }
        catch (Exception e) {
            //Toast.makeText(, e.getMessage(), Toast.LENGTH_LONG).show();
            returnMessage = e.getMessage();
        }
        //String test = new String(responseString);
        return returnMessage;
    }

    // Name Value pairs for All Groups
    public JSONObject getJsonGroup(Group group)
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
            collectJSON.put(DatabaseHandler.COLUMN_GROUP_BankAccount, group.BankAccount);
            collectJSON.put(DatabaseHandler.COLUMN_GROUP_NoOfSubgroups, group.NoOfSubgroups);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_CreatedBy,);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_Active ,);
            collectJSON.put(DatabaseHandler.COLUMN_GROUP_RecurringIndividualAmount, group.RecurringSavings);
            collectJSON.put(DatabaseHandler.COLUMN_GROUP_MonthlyMeetingDate, group.MonthlyMeetingDate);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_ClusterId ,);
            collectJSON.put(DatabaseHandler.COLUMN_GROUP_AddressLine1, group.AddressLine1);
            collectJSON.put(DatabaseHandler.COLUMN_GROUP_AddressLine2, group.AddressLine2);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_City ,);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_State ,);
            //collectJSON.put(DatabaseHandler.COLUMN_GROUP_Country ,);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }

    // Name Value pairs for All Members
    public JSONObject getJsonMember(Member member) {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_UID, member.UID);
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_GroupUID, member.GroupUID);
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_FirstName, member.FirstName);
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_LastName, member.LastName);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_Sex,);
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_DOB, member.DOB);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_EmailId,);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_Active ,);
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_TotalSavings, member.TotalSavings);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_TotalDebt ,);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_CreatedDate, member.);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_CreatedBy ,);
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_ContactNumber, member.ContactInfo);
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_AddressLine1 , member.AddressLine1);
            collectJSON.put(DatabaseHandler.COLUMN_MEMBER_AddressLine2 , member.AddressLine2);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_City ,);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_State ,);
            //collectJSON.put(DatabaseHandler.COLUMN_MEMBER_Country ,);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }

    // Name Value pairs for Saving Accounts
    public JSONObject getJsonSavingAcc(SavingsAccount savingAccount) {
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
    public JSONObject getJsonSavingTrans(SavingTransaction savingTransaction) {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(DatabaseHandler.COLUMN_SAVINGTRANSACTION_Id, savingTransaction.Id);
            collectJSON.put(DatabaseHandler.COLUMN_SAVINGTRANSACTION_GroupId, savingTransaction.groupId);
            collectJSON.put(DatabaseHandler.COLUMN_SAVINGTRANSACTION_GroupMeetingId, savingTransaction.grpMeetingId);
            collectJSON.put(DatabaseHandler.COLUMN_SAVINGTRANSACTION_GroupMemberSavingId, savingTransaction.memberSavingAccId);
            collectJSON.put(DatabaseHandler.COLUMN_SAVINGTRANSACTION_OptionalSavings, savingTransaction.optionalSavings);
            // Don't use getTotalSaving as it needs groupCompulsory savings which isn't populated directly via db
            collectJSON.put(DatabaseHandler.COLUMN_SAVINGTRANSACTION_TransactionTotalSaving, savingTransaction.transactionTotalSaving);
            collectJSON.put(DatabaseHandler.COLUMN_SAVINGTRANSACTION_DateTime, savingTransaction.timeStamp);
            //collectJSON.put(DatabaseHandler.COLUMN_SAVINGTRANSACTION_SignedBy ,);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }

    // Name Value pairs for Loan Accounts
    public static JSONObject getJsonLoanAcc(LoanAccount la)
    {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(DatabaseHandler.COLUMN_LOANACCOUNT_Id, la.Id);
            collectJSON.put(DatabaseHandler.COLUMN_LOANACCOUNT_GroupId, la.groupId);
            collectJSON.put(DatabaseHandler.COLUMN_LOANACCOUNT_GroupMeetingId, la.groupMeetingId);
            collectJSON.put(DatabaseHandler.COLUMN_LOANACCOUNT_MemberId, la.memberId);
            collectJSON.put(DatabaseHandler.COLUMN_LOANACCOUNT_PrincipalAmount, la.Principal);
            collectJSON.put(DatabaseHandler.COLUMN_LOANACCOUNT_InterestRate, la.InterestPerAnnum);
            collectJSON.put(DatabaseHandler.COLUMN_LOANACCOUNT_NoOfInstallments, la.PeriodInMonths);
            collectJSON.put(DatabaseHandler.COLUMN_LOANACCOUNT_InstallmentAmount, la.getEMI());
            collectJSON.put(DatabaseHandler.COLUMN_LOANACCOUNT_StartDate, la.StartDate);
            collectJSON.put(DatabaseHandler.COLUMN_LOANACCOUNT_EndDate, la.EndDate);
            collectJSON.put(DatabaseHandler.COLUMN_LOANACCOUNT_Outstanding, la.OutStanding);
            collectJSON.put(DatabaseHandler.COLUMN_LOANACCOUNT_Reason, la.Reason);
            collectJSON.put(DatabaseHandler.COLUMN_LOANACCOUNT_IsActive, la.IsActive);
            //collectJSON.put(DatabaseHandler.COLUMN_LOANACCOUNT_CreatedDate ,);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }

    public static JSONObject getJsonLoanTrans(LoanTransaction lt)
    {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(DatabaseHandler.COLUMN_LOAN_TRANSACTION_Id, lt.Id);
            collectJSON.put(DatabaseHandler.COLUMN_LOAN_TRANSACTION_GroupId, lt.groupId);
            collectJSON.put(DatabaseHandler.COLUMN_LOAN_TRANSACTION_GroupMeetingId, lt.GroupMeetingId);
            collectJSON.put(DatabaseHandler.COLUMN_LOAN_TRANSACTION_GroupMemberLoanId, lt.GroupMemberLoanAccountId);
            collectJSON.put(DatabaseHandler.COLUMN_LOAN_TRANSACTION_Repayment, lt.Repayment);
            collectJSON.put(DatabaseHandler.COLUMN_LOAN_TRANSACTION_OutstandingLeft, lt.OutstandingDue);
            collectJSON.put(DatabaseHandler.COLUMN_LOAN_TRANSACTION_DateTime, lt.Date);

        }  catch (JSONException e) {
            e.printStackTrace();
        }
        return collectJSON;
    }
    public static JSONObject getJsonGrpMeeting(GroupMeeting gm)
    {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(DatabaseHandler.COLUMN_GROUPMEETING_ID, gm.id);
            collectJSON.put(DatabaseHandler.COLUMN_GROUPMEETING_GroupId, gm.groupId);
            collectJSON.put(DatabaseHandler.COLUMN_GROUPMEETING_DATE,gm.date);

        }  catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }

    public static LoanAccount getLoanAccFromJson(JSONObject jsonLA) throws JSONException {
        LoanAccount la = new LoanAccount();
        la.Id = jsonLA.getInt(DatabaseHandler.COLUMN_LOANACCOUNT_Id);
        la.groupId = jsonLA.getInt(DatabaseHandler.COLUMN_LOANACCOUNT_GroupId);
        la.groupMeetingId = jsonLA.getInt(DatabaseHandler.COLUMN_LOANACCOUNT_GroupMeetingId);
        la.memberId = jsonLA.getInt(DatabaseHandler.COLUMN_LOANACCOUNT_MemberId);
        la.Principal = jsonLA.getInt(DatabaseHandler.COLUMN_LOANACCOUNT_PrincipalAmount);
        la.InterestPerAnnum = jsonLA.getInt(DatabaseHandler.COLUMN_LOANACCOUNT_InterestRate);
        la.PeriodInMonths = jsonLA.getInt(DatabaseHandler.COLUMN_LOANACCOUNT_NoOfInstallments);
        la.EMI = jsonLA.getInt(DatabaseHandler.COLUMN_LOANACCOUNT_InstallmentAmount);
        la.StartDate = jsonLA.getString(DatabaseHandler.COLUMN_LOANACCOUNT_StartDate);
        la.EndDate = jsonLA.getString(DatabaseHandler.COLUMN_LOANACCOUNT_EndDate);
        la.OutStanding = jsonLA.getInt(DatabaseHandler.COLUMN_LOANACCOUNT_Outstanding);
        la.Reason = jsonLA.getString(DatabaseHandler.COLUMN_LOANACCOUNT_Reason);
        la.IsActive = jsonLA.getBoolean(DatabaseHandler.COLUMN_LOANACCOUNT_IsActive);

        return la;
    }


    /* Commenting out for POC
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
// Coverts data into JSON Array for all tables
    // All Groups
    public JSONArray GetAllGroupsJSON(ArrayList<Group> groups)
    {
        JSONArray jsonArray = new JSONArray();
        for (Group group : groups) {
            jsonArray.put(getJsonGroup(group));
        }
        return jsonArray;
    }

    // All Members
    public JSONArray GetAllMembersJSON(ArrayList<Member> members) {
        JSONArray jsonArray = new JSONArray();
        for (Member member : members) {
            jsonArray.put(getJsonMember(member));
        }
        return jsonArray;
    }

    // All Saving Transactions
    public JSONArray GetAllSavingTransJSON(ArrayList<SavingTransaction> savingTransactions) {
        JSONArray jsonArray = new JSONArray();
        for (SavingTransaction savingTransaction : savingTransactions) {
            jsonArray.put(getJsonSavingTrans(savingTransaction));
        }
        return jsonArray;
    }

    // All Saving Account
    public JSONArray GetAllSavingAccJSON(ArrayList<SavingsAccount> savingAccounts) {
        JSONArray jsonArray = new JSONArray();
        for (SavingsAccount savingAccount : savingAccounts) {
            jsonArray.put(getJsonSavingAcc(savingAccount));
        }
        return jsonArray;
    }


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

    public static JSONArray GetAllGroupMeetingJSON(ArrayList<GroupMeeting> grpMeetings)
    {
        JSONArray jsonArray = new JSONArray();
        for (GroupMeeting grpMeeting : grpMeetings) {
            jsonArray.put(getJsonGrpMeeting(grpMeeting));
        }
        return jsonArray;
    }

}