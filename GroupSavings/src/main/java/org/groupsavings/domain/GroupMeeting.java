package org.groupsavings.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shashank on 30/3/14.
 */
public class GroupMeeting {
    public String Id;
    public String GroupId;
    public String Date;
    public String FieldOfficerId;

    ArrayList<SavingTransaction> SavingTransactions;
    ArrayList<LoanAccount> LoanAccountsCreated;
    ArrayList<LoanTransaction> LoanTransactions;
    ArrayList<MeetingDetails> MeetingDetails;


    @Override
    public String toString() {
        return Date;
    }
}
