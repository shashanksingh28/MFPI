package org.groupsavings;

// Added for Sync testing

import org.groupsavings.constants.Columns;
import org.groupsavings.constants.Tables;
import org.groupsavings.database.DatabaseFetchHelper;
import org.groupsavings.database.DatabaseHandler;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.ContactsContract;
import android.widget.Toast;

// Added for Sync testing
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import org.groupsavings.domain.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.InputStream;
import java.util.ArrayList;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static org.apache.http.util.EntityUtils.*;



/**
 * Created by Shashank on 16/3/14.
 */
public class SyncHelper {

    /* Commenting this so that others can build without sync completed
     * Those testing sync uncomment and use
     */


    public static final String SERVER_URL="http://planindiatest.webatu.com/Version2/AndroidSync";
    public DatabaseHandler db_handler;
    private Context context;
    public DatabaseFetchHelper db_fetch;

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
            String responseEntity;

            // Groups Data Sync
            JSONArray allGroupsJSON = GetAllGroupsJSON(db_handler.getAllFOGroups(""));
            s = new StringEntity(allGroupsJSON.toString());
            s.setContentEncoding(http_JSON_header);
            HttpPost httppost = new HttpPost(SERVER_URL+"/SaveGroups.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);
            //String responseEn = EntityUtils.toString(response.getEntity());
            // final JSONArray jArray=new JSONArray(responseEn);
            SyncOutGroups(EntityUtils.toString(response.getEntity()));


/*
            JSONArray allmembersJSON = GetAllMembersJSON(db_handler.getGroupMembers());
            s = new StringEntity(allmembersJSON.toString());
            s.setContentEncoding(http_JSON_header);
            httppost = new HttpPost(SERVER_URL+"/SaveMembers.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);
            SyncOutMembers(EntityUtils.toString(response.getEntity()));


            JSONArray allsavingsJSON = GetAllSavingAccJSON(db_handler);
            s = new StringEntity(allsavingsJSON.toString());
            s.setContentEncoding(http_JSON_header);
            httppost = new HttpPost(SERVER_URL+"/SavingAccount.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);
            SyncOutSavingAccounts(EntityUtils.toString(response.getEntity()));

            JSONArray allsavingTransJSON = GetAllSavingTransJSON(db_handler.getAllSavingTrans());
            s = new StringEntity(allsavingTransJSON.toString());
            s.setContentEncoding(http_JSON_header);
            httppost = new HttpPost(SERVER_URL+"/SavingTransaction.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);
            SyncOutSavingTransactions(EntityUtils.toString(response.getEntity()));

            JSONArray allLoanTransJSON = GetAllLoanTransJSON(db_handler.getAllLoanTrans());
            s = new StringEntity(allLoanTransJSON.toString());
            s.setContentEncoding(http_JSON_header);
            httppost = new HttpPost(SERVER_URL+"/LoanTransaction.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);
            SyncOutLoanTransaction(EntityUtils.toString(response.getEntity()));

            JSONArray allLoanAccountJSON = GetAllLoanAccJSON(db_handler.getAllLoanAccount());
            s = new StringEntity(allLoanAccountJSON.toString());
            s.setContentEncoding(http_JSON_header);
            httppost = new HttpPost(SERVER_URL+"/LoanAccount.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);
            SyncOutLoanAccount(EntityUtils.toString(response.getEntity()));

            JSONArray allGrpMeetingJSON = GetAllGroupMeetingJSON(db_handler.());
            s = new StringEntity(allGrpMeetingJSON.toString());
            s.setContentEncoding(http_JSON_header);
            httppost = new HttpPost(SERVER_URL+"/GroupMeetings.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);
            SyncOutGroupMeetings(EntityUtils.toString(response.getEntity()));

            JSONArray allMeetingDetailsJSON = GetAllMeetingDetailsJSON(db_handler.());
            s = new StringEntity(allMeetingDetailsJSON.toString());
            s.setContentEncoding(http_JSON_header);
            httppost = new HttpPost(SERVER_URL+"/MeetingDetails.php");
            httppost.setEntity(s);
            response = httpclient.execute(httppost);
            SyncOutGroupMeetings(EntityUtils.toString(response.getEntity()));
*/
        }
        catch (Exception e) {
            //Toast.makeText(, e.getMessage(), Toast.LENGTH_LONG).show();
            returnMessage = e.getMessage();
        }
        //String test = new String(responseString);
        return returnMessage;
    }

    public void SyncOutGroups(String responseEntity)
    {
        try
        {
            final JSONArray jArray=new JSONArray(responseEntity);
            for(int i=0; i< jArray.length();i++)
            {
                JSONObject j = jArray.getJSONObject(i);
                Group g = new Group();
                g.Id 						=	   j.getString("Id");
                g.Name 					    =      j.getString("Name");
                g.President.Id 				=      j.getString("President");
                g.Secretary.Id 				=      j.getString("Secretary");
                g.Treasurer.Id 				=      j.getString("Treasurer");
                g.FieldOfficerId 			=      j.getString("FieldOfficerId");
                g.Active 					=      j.getBoolean("Active");
                g.MonthlyMeetingDate 		=      j.getInt("MonthlyMeetingDate");
                g.MonthlyCompulsoryAmount 	=      j.getLong("MonthlyCompulsoryAmount");
                g.Bank 					    =      j.getString("Bank");
                g.ClusterId 				=      j.getLong("ClusterId");
                g.CummulativeSavings 		=      j.getLong("CummulativeSavings");
                g.OtherIncome 				=      j.getLong("OtherIncome");
                g.OutstandingLoans 		    =      j.getLong("OutstandingLoans");
                g.DateOfFormation 			=      j.getString("DateOfFormation");
                g.NoOfSubgroups 			=      j.getInt("NoOfSubgroups");
                g.AddressLine1 		    	=      j.getString("AddressLine1");
                g.AddressLine2 			    =      j.getString("AddressLine2");
                g.City 					    =      j.getString("City");
                g.State 					=      j.getString("State");
                g.Country 					=      j.getString("Country");
                g.CreatedDate 				=      j.getString("CreatedDate");
                g.CreatedBy 				=      j.getString("CreatedBy");
                g.ModifiedDate 			    =      j.getString("ModifiedDate");
                g.ModifiedBy 				=      j.getString("ModifiedBy");
                g.NoOfActiveMembers		    =      j.getString("NoOfActiveMembers");

                db_handler.addUpdateGroup(g);
            }
        }
        catch (Exception e) {
            //Toast.makeText(, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
/*
    public void SyncOutMembers(String responseEntity)
    {
        try
        {
            final JSONArray jArray=new JSONArray(responseEntity);
            for(int i=0; i< jArray.length();i++)
            {
                JSONObject j = jArray.getJSONObject(i);
                Member m = new Member();

                m.Id                    =      j.getString("Id");
                m.GroupId               =      j.getString("GroupId");
                m.FirstName             =      j.getString("FirstName");
                m.LastName              =      j.getString("LastName");
                m.GuardianName          =      j.getString("GuardianName");
                m.Gender                =      j.getString("Gender");
                m.DOB                   =      j.getString("DOB");
                m.EmailId               =      j.getString("EmailId");
                m.Active                =      j.getBoolean("Active");
                m.ContactNumber         =      j.getString("ContactNumber");
                m.AddressLine1          =      j.getString("AddressLine1");
                m.AddressLine2          =      j.getString("AddressLine2");
                //m.State                 =      j.getString("State");
                m.Occupation            =      j.getString("Occupation");
                m.AnnualIncome          =      j.getLong("AnnualIncome");
                m.Education             =      j.getString("Education");
                m.Disability            =      j.getBoolean("Disability");
                m.NoOfFamilyMembers     =      j.getInt("NoOfFamilyMembers");
                m.Nominee               =      j.getString("Nominee");
                m.Insurance             =      j.getBoolean("Insurance");
                m.ExitDate              =      j.getString("ExitDate");
                m.ExitReason            =      j.getString("ExitReason");
                m.CreatedDate           =      j.getString("CreatedDate");
                m.CreatedBy             =      j.getString("CreatedBy");
                m.ModifiedDate          =      j.getString("ModifiedDate");
                m.ModifiedBy            =      j.getString("ModifiedBy");
                m.Passbook              =      j.getString("Passbook");
                m.EconomicCondition     =      j.getString("EconomicCondition");


                db.insertOrThrow(Tables.MEMBERS, null, m);
            }
        }
        catch (Exception e) {
            //Toast.makeText(, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void SyncOutSavingAccounts(String responseEntity)
    {
        try
        {
            final JSONArray jArray=new JSONArray(responseEntity);
            for(int i=0; i< jArray.length();i++)
            {
                JSONObject j = jArray.getJSONObject(i);
                SavingsAccount sa = new SavingsAccount();
                sa.Id = 						j.getString("Id");
                sa.MemberId = 					j.getString("MemberId");
                sa.GroupId = 					j.getString("GroupId");
                sa.CompulsorySavings =   		j.getLong("CompulsorySavings");
                sa.OptionalSavings = 			j.getLong("OptionalSavings");
                sa.InterestAccumulated = 		j.getLong("InterestAccumulated");
                sa.TotalSavings = 				j.getLong("TotalSavings");
                sa.CreatedDate = 				j.getString("CreatedDate");
                sa.CreatedBy = 			    	j.getString("CreatedBy");
                sa.Active = 					j.getBoolean("Active");


            }
        }
        catch (Exception e) {
            //Toast.makeText(, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void SyncOutSavingTransactions(String responseEntity)
    {
        try
        {
            final JSONArray jArray=new JSONArray(responseEntity);
            for(int i=0; i< jArray.length();i++)
            {
                JSONObject j = jArray.getJSONObject(i);
                SavingTransaction st = new SavingTransaction();
                st.GroupId 			= j.getString("GroupId");
                st.SavingAccountId 	= j.getString("SavingAccountId");
                st.MeetingId 			= j.getString("MeetingId");
                st.Type 				= j.getString("Type");
                st.Amount 				= j.getLong("Amount");
                st.CurrentBalance 		= j.getLong("CurrentBalance");
                st.DateTime 			= j.getString("DateTime");

                db_handler.addUpdateGroup(st);
            }
        }
        catch (Exception e) {
            //Toast.makeText(, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void SyncOutLoanAccount(String responseEntity)
    {
        try
        {
            final JSONArray jArray=new JSONArray(responseEntity);
            for(int i=0; i< jArray.length();i++)
            {
                JSONObject j = jArray.getJSONObject(i);
                LoanAccount la = new LoanAccount();

                la.Id               =     j.getString("Id");
                la.MemberId         =     j.getString("MemberId");
                la.GroupId          =     j.getString("GroupId");
                la.GroupMeetingId   =     j.getString("GroupMeetingId");
                la.Principal        =     j.getLong("Principal");
                la.InterestRate     =     j.getLong("InterestRate");
                la.PeriodInMonths   =     j.getLong("PeriodInMonths");
                la.EMI              =     j.getLong("EMI");
                la.Outstanding      =     j.getLong("Outstanding");
                la.Reason           =     j.getString("Reason");
                la.Guarantor        =     j.getString("Guarantor");
                la.IsEmergency      =     j.getString("IsEmergency");
                la.StartDate        =     j.getString("StartDate");
                la.EndDate          =     j.getString("EndDate");
                la.CreatedDate      =     j.getString("CreatedDate");
                la.CreatedBy        =     j.getString("CreatedBy");
                la.Active           =     j.getString("Active");


                db_handler.addUpdateGroup(st);
            }
        }
        catch (Exception e) {
            //Toast.makeText(, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void SyncOutLoanTransaction(String responseEntity)
    {
        try
        {
            final JSONArray jArray=new JSONArray(responseEntity);
            for(int i=0; i< jArray.length();i++)
            {
                JSONObject j = jArray.getJSONObject(i);
                LoanTransaction lt` = new LoanTransaction();
                lt.GroupId = j.getString("GroupId");
                lt.MeetingId = j.getString("MeetingId");
                lt.LoanAccountId = j.getString("LoanAccountId");
                lt.Repayment = j.getLong("Repayment");
                lt.Outstanding = j.getLong("Outstanding");
                lt.DateTime = j.getString("DateTime");

                db_handler.addUpdateGroup(st);
            }
        }
        catch (Exception e) {
            //Toast.makeText(, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void SyncOutGroupMeetings(String responseEntity)
    {
        try
        {
            final JSONArray jArray=new JSONArray(responseEntity);
            for(int i=0; i< jArray.length();i++)
            {
                JSONObject j = jArray.getJSONObject(i);
                GroupMeeting gm = new GroupMeeting();
                gm.Id = j.getString("Id");
                gm.GroupId = j.getString("GroupId");
                gm.Date = j.getString("Date");
                gm.FieldOfficerId = j.getString("FieldOfficerId");

                db_handler.addUpdateGroup(st);
            }
        }
        catch (Exception e) {
            //Toast.makeText(, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void SyncOutMeetingDetails(String responseEntity)
    {
        try
        {
            final JSONArray jArray=new JSONArray(responseEntity);
            for(int i=0; i< jArray.length();i++)
            {
                JSONObject j = jArray.getJSONObject(i);
                MeetingDetails md = new MeetingDetails();
                md.MeetingId = j.getString("MeetingId");
                md.MemberId = j.getString("MemberId");
                md.IsAbsent = j.getString("IsAbsent");
                md.Fine = j.getLong("Fine");

                db_handler.addUpdateGroup(st);
            }
        }
        catch (Exception e) {
            //Toast.makeText(, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
*/
    // Name Value pairs for All Groups
    public JSONObject getJsonGroup(Group group)
    {
        JSONObject collectJSON = new JSONObject();
        try {

            collectJSON.put(Columns.GROUP_Id, group.Id);
            collectJSON.put(Columns.GROUP_Name ,group.Name);
            collectJSON.put(Columns.GROUP_PresidentId, group.President);
            collectJSON.put(Columns.GROUP_SecretaryId,group.Secretary);
            collectJSON.put(Columns.GROUP_TreasurerId, group.Treasurer);
            collectJSON.put(Columns.GROUP_FieldOfficerId, group.FieldOfficerId);
            collectJSON.put(Columns.GROUP_Active,group.Active);
            collectJSON.put(Columns.GROUP_MonthlyCompulsoryAmount,group.MonthlyCompulsoryAmount);
            collectJSON.put(Columns.GROUP_MonthlyMeetingDate,group.MonthlyMeetingDate);
            collectJSON.put(Columns.GROUP_Bank,group.Bank);
            collectJSON.put(Columns.GROUP_CummulativeSavings,group.CummulativeSavings);
            collectJSON.put(Columns.GROUP_OtherIncome,group.OtherIncome);
            collectJSON.put(Columns.GROUP_OutstandingLoans,group.OutstandingLoans);
            collectJSON.put(Columns.GROUP_DateOfFormation,group.CreatedDate);
            collectJSON.put(Columns.GROUP_NoOfSubgroups,group.NoOfSubgroups);
            collectJSON.put(Columns.GROUP_AddressLine1,group.AddressLine1);
            collectJSON.put(Columns.GROUP_AddressLine2,group.AddressLine2);
            collectJSON.put(Columns.GROUP_City,group.City);
            collectJSON.put(Columns.GROUP_State,group.State);
            collectJSON.put(Columns.GROUP_Country,group.Country);
            collectJSON.put(Columns.GROUP_ClusterId,group.ClusterId);
            collectJSON.put(Columns.GROUP_CreatedDate,group.CreatedDate);
            collectJSON.put(Columns.GROUP_CreatedBy,group.CreatedBy);
            collectJSON.put(Columns.GROUP_ModifiedDate,group.ModifiedDate);
            collectJSON.put(Columns.GROUP_ModifiedBy,group.ModifiedBy);
            collectJSON.put(Columns.GROUP_NoOfActiveMembers,group.NoOfActiveMembers);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }

    /*
    // Name Value pairs for All Members
    public JSONObject getJsonMember(Member member) {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(Columns.MEMBER , member.Id);
            collectJSON.put(Columns.MEMBER_GroupID , member.GroupId);
            collectJSON.put(Columns.MEMBER_FirstName , member.FirstName);
            collectJSON.put(Columns.MEMBER_LastName , member.LastName);
            collectJSON.put(Columns.MEMBER_GuardianName , member.GuardianName);
            collectJSON.put(Columns.MEMBER_Gender , member.Gender);
            collectJSON.put(Columns.MEMBER_DOB , member.DOB);
            collectJSON.put(Columns.MEMBER_EmailId , member.EmailId);
            collectJSON.put(Columns.MEMBER_Active , member.Active);
            collectJSON.put(Columns.MEMBER_ContactNumber , member.ContactNumber);
            collectJSON.put(Columns.MEMBER_AddressLine1 , member.AddressLine1);
            collectJSON.put(Columns.MEMBER_AddressLine2 , member.AddressLine2);
            collectJSON.put(Columns.MEMBER_Occupation , member.Occupation);
            collectJSON.put(Columns.MEMBER_AnnualIncome , member.AnnualIncome);
            collectJSON.put(Columns.MEMBER_EconomicCondition , member.EconomicCondition);
            collectJSON.put(Columns.MEMBER_Education , member.Education);
            collectJSON.put(Columns.MEMBER_Disability , member.Disability);
            collectJSON.put(Columns.MEMBER_NoOfFamilyMembers , member.NoOfFamilyMembers);
            collectJSON.put(Columns.MEMBER_Nominee , member.Nominee);
            collectJSON.put(Columns.MEMBER_Passbook , member.Passbook);
            collectJSON.put(Columns.MEMBER_Insurance , member.Insurance);
            collectJSON.put(Columns.MEMBER_ExitDate , member.ExitDate);
            collectJSON.put(Columns.MEMBER_ExitReason , member.ExitReason);
            collectJSON.put(Columns.MEMBER_CreatedDate , member.CreatedDate);
            collectJSON.put(Columns.MEMBER_CreatedBy , member.CreatedBy);
            collectJSON.put(Columns.MEMBER_ModifiedDate , member.ModifiedDate);
            collectJSON.put(Columns.MEMBER_ModifiedBy , member.ModifiedBy);
            collectJSON.put(Columns.MEMBER_EconomicCondition ,member.EconomicCondition);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }

    // Name Value pairs for Saving Accounts
    public JSONObject getJsonSavingAcc(SavingsAccount savingAccount) {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(Columns.SAVINGACCOUNTS_Id , savingAccount.Id);
            collectJSON.put(Columns.SAVINGACCOUNTS_MemberId , savingAccount.MemberId);
            collectJSON.put(Columns.SAVINGACCOUNTS_GroupId , savingAccount.GroupId);
            collectJSON.put(Columns.SAVINGACCOUNTS_CompulsorySavings , savingAccount.CompulsorySavings);
            collectJSON.put(Columns.SAVINGACCOUNTS_OptionalSavings , savingAccount.OptionalSavings);
            collectJSON.put(Columns.SAVINGACCOUNTS_InterestAccumulated , savingAccount.InterestAccumulated);
            collectJSON.put(Columns.SAVINGACCOUNTS_CurrentBalance , savingAccount.TotalSavings);
            collectJSON.put(Columns.SAVINGACCOUNTS_Active , savingAccount.Active);
            collectJSON.put(Columns.SAVINGACCOUNTS_CreatedDate , savingAccount.CreatedDate);
            collectJSON.put(Columns.SAVINGACCOUNTS_CreatedBy , savingAccount.CreatedBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }

    // Name Value pairs for  Saving Transactions
    public JSONObject getJsonSavingTrans(SavingTransaction savingTransaction) {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(Columns.SAVINGACCTRANSACTIONS_GroupId , savingTransaction.GroupId);
            collectJSON.put(Columns.SAVINGACCTRANSACTIONS_MeetingId , savingTransaction.MeetingId);
            collectJSON.put(Columns.SAVINGACCTRANSACTIONS_SavingAccountId , savingTransaction.SavingAccountId);
            collectJSON.put(Columns.SAVINGACCTRANSACTIONS_Type , savingTransaction.Type);
            collectJSON.put(Columns.SAVINGACCTRANSACTIONS_Amount , savingTransaction.Amount);
            collectJSON.put(Columns.SAVINGACCTRANSACTIONS_CurrentBalance , savingTransaction.CurrentBalance);
            collectJSON.put(Columns.SAVINGACCTRANSACTIONS_DateTime , savingTransaction.DateTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }




    public static JSONObject getJsonLoanTrans(LoanTransaction lt)
    {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(Columns.LOANACCTRANSACTIONS_GroupId , lt.GroupId);
            collectJSON.put(Columns.LOANACCTRANSACTIONS_MeetingId , lt.MeetingId);
            collectJSON.put(Columns.LOANACCTRANSACTIONS_LoanAccountId , lt.LoanAccountId);
            collectJSON.put(Columns.LOANACCTRANSACTIONS_Repayment , lt.Repayment);
            collectJSON.put(Columns.LOANACCTRANSACTIONS_CurrentOutstanding , lt.Outstanding);
            collectJSON.put(Columns.LOANACCTRANSACTIONS_DateTime , lt.DateTime);

        }  catch (JSONException e) {
            e.printStackTrace();
        }
        return collectJSON;
    }

    public static JSONObject getJsonGrpMeeting(GroupMeeting gm)
    {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(Columns.GROUPMEETING_Id, gm.Id);
            collectJSON.put(Columns.GROUPMEETING_FieldOfficerId,gm.FieldOfficerId);
            collectJSON.put(Columns.GROUPMEETING_Date,gm.Date);
            collectJSON.put(Columns.GROUPMEETING_GroupId,gm.GroupId);

        }  catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }

    public static JSONObject getJsonMeetingDetails(MeetingDetails md)
    {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(Columns.MEETINGDETAILS_MeetingId,md.MeetingId);
            collectJSON.put(Columns.MEETINGDETAILS_MemberId,md.MemberId);
            collectJSON.put(Columns.MEETINGDETAILS_Fine,md.Fine);
            collectJSON.put(Columns.MEETINGDETAILS_IsAbsent,md.IsAbsent);

        }  catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }
*/
    public static JSONObject getJsonLoanAcc(LoanAccount la)
    {
        JSONObject collectJSON = new JSONObject();
        try {
            collectJSON.put(Columns.LOANACCOUNTS_Id , la.Id);
            collectJSON.put(Columns.LOANACCOUNTS_MemberId , la.MemberId);
            collectJSON.put(Columns.LOANACCOUNTS_GroupId , la.GroupId);
            collectJSON.put(Columns.LOANACCOUNTS_GroupMeetingId , la.GroupMeetingId);
            collectJSON.put(Columns.LOANACCOUNTS_PrincipalAmount , la.Principal);
            collectJSON.put(Columns.LOANACCOUNTS_InterestRate , la.InterestRate);
            collectJSON.put(Columns.LOANACCOUNTS_PeriodInMonths , la.PeriodInMonths);
            collectJSON.put(Columns.LOANACCOUNTS_EMI , la.EMI);
            collectJSON.put(Columns.LOANACCOUNTS_Outstanding , la.Outstanding);
            collectJSON.put(Columns.LOANACCOUNTS_Reason , la.Reason);
            collectJSON.put(Columns.LOANACCOUNTS_GUARANTOR , la.Guarantor);
            collectJSON.put(Columns.LOANACCOUNTS_IsEmergency , la.IsEmergency);
            collectJSON.put(Columns.LOANACCOUNTS_StartDate , la.StartDate);
            collectJSON.put(Columns.LOANACCOUNTS_EndDate , la.EndDate);
            collectJSON.put(Columns.LOANACCOUNTS_Active , la.Active);
            collectJSON.put(Columns.LOANACCOUNTS_CreatedDate , la.CreatedDate);
            collectJSON.put(Columns.LOANACCOUNTS_CreatedBy , la.CreatedBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return collectJSON;
    }


    public static LoanAccount getLoanAccFromJson(JSONObject jsonLA) throws JSONException {
        LoanAccount la = new LoanAccount();
        la.Id = jsonLA.getString(Columns.LOANACCOUNTS_Id);
        la.GroupId = jsonLA.getString(Columns.LOANACCOUNTS_GroupId);
        // Commented this as meeting is still not saved when this func is called
        // la.GroupMeetingId = jsonLA.getString(Columns.LOANACCOUNTS_GroupMeetingId);
        la.MemberId = jsonLA.getString(Columns.LOANACCOUNTS_MemberId);
        la.Principal = (float) jsonLA.getDouble(Columns.LOANACCOUNTS_PrincipalAmount);
        la.InterestRate = (float) jsonLA.getDouble(Columns.LOANACCOUNTS_InterestRate);
        la.PeriodInMonths = jsonLA.getInt(Columns.LOANACCOUNTS_PeriodInMonths);
        la.EMI = (float) jsonLA.getDouble(Columns.LOANACCOUNTS_EMI);
        la.StartDate = jsonLA.getString(Columns.LOANACCOUNTS_StartDate);
        la.EndDate = jsonLA.getString(Columns.LOANACCOUNTS_EndDate);
        la.Outstanding = (float) jsonLA.getDouble(Columns.LOANACCOUNTS_Outstanding);
        la.Reason = jsonLA.getString(Columns.LOANACCOUNTS_Reason);
        la.IsEmergency = jsonLA.getBoolean(Columns.LOANACCOUNTS_IsEmergency);
        la.Active = jsonLA.getBoolean(Columns.LOANACCOUNTS_Active);

        return la;
    }


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
/*
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

    public static JSONArray GetAllMeetingDetailsJSON(ArrayList<MeetingDetails> meetingDetails)
    {
        JSONArray jsonArray = new JSONArray();
        for (MeetingDetails meetingDetail : meetingDetails) {
            jsonArray.put(getJsonMeetingDetails(meetingDetail));
        }
        return jsonArray;
    }
*/
}
