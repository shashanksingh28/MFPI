package org.groupsavings.domain;

import java.util.ArrayList;

/**
 * Created by shashank on 30/3/14.
 */
public class GroupMeeting {
    public String Id;
    public String GroupId;
    public String Date;
    public String FieldOfficerId;

    // Used in MeetingSavingsFragment
    public ArrayList<MeetingSavingsAccTransaction> SavingTransactions;

    // Used in MeetingLoansFragment
    public ArrayList<MeetingLoanAccTransactions> LoanTransactions;
    public ArrayList<LoanAccount> LoansCreated;

    // Used in MeetingDetailsFragment
    public ArrayList<MeetingDetails> OtherDetails;

    public GroupMeeting()
    {
        SavingTransactions = new ArrayList<MeetingSavingsAccTransaction>();
        LoanTransactions = new ArrayList<MeetingLoanAccTransactions>();
        LoansCreated = new ArrayList<LoanAccount>();
        OtherDetails = new ArrayList<MeetingDetails>();
    }

    @Override
    public String toString() {
        return Date;
    }
}
