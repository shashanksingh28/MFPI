package org.groupsavings.domain;

/**
 * Created by shashank on 30/3/14.
 */
public class LoanTransaction
{
    public int Id;
    public int EMI;
    public int GroupMeetingId;
    public int GroupMemberLoanAccountId;
    public int Repayment;
    // This is private because outstanding according to db should not change
    private int OutstandingDue;
    public String Date;

    public void setOutstandingDue(int outstandingDue)
    {
        this.OutstandingDue = outstandingDue;
    }

    public int getUpdatedOutstanding()
    {
        return this.OutstandingDue - Repayment;
    }
}
