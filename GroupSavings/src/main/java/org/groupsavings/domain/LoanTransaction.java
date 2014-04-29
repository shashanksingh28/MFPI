package org.groupsavings.domain;

/**
 * Created by shashank on 30/3/14.
 */
public class LoanTransaction
{
    public int Id;
    public int groupId;
    public long EMI;
    public int GroupMeetingId;
    public int GroupMemberLoanAccountId;
    public long Repayment;
    // This is private because outstanding according to db should not change
    public long OutstandingDue;
    public String Date;

    public void setOutstandingDue(long outstandingDue)
    {
        this.OutstandingDue = outstandingDue;
    }

    public long getUpdatedOutstanding()
    {
        return this.OutstandingDue - Repayment;
    }

    public long getAsIsOutstanding()
    {
        return this.OutstandingDue;
    }
}

