package org.groupsavings.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shashank on 30/3/14.
 */
public class GroupMeeting implements Serializable{

    public String Id;
    public String GroupId;
    public String Date;
    public String FieldOfficerId;
    private static final long serialVersionUID = -7060210544600464481L;

    // Used in MeetingSavingsFragment
    public ArrayList<MeetingSavingsAccTransaction> SavingTransactions;

    // Used in MeetingLoansFragment
    public ArrayList<MeetingLoanAccTransaction> LoanTransactions;
    public ArrayList<LoanAccount> LoansCreated;

    // Used in MeetingDetailsFragment
    public ArrayList<MeetingDetails> OtherDetails;

    public GroupMeeting()
    {
        SavingTransactions = new ArrayList<MeetingSavingsAccTransaction>();
        LoanTransactions = new ArrayList<MeetingLoanAccTransaction>();
        LoansCreated = new ArrayList<LoanAccount>();
        OtherDetails = new ArrayList<MeetingDetails>();
    }

    @Override
    public String toString() {
        return Date;
    }
}
