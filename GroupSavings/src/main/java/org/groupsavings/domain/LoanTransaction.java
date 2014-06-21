package org.groupsavings.domain;

/**
 * Created by shashank on 30/3/14.
 */
public class LoanTransaction
{
    public String GroupId;
    public String MeetingId;
    public String LoanAccountId;
    public float Repayment;
    public float Outstanding;
    public String DateTime;

    LoanAccount LoanAccount;

    public void setOutstandingDue(long outstandingDue)
    {
        this.Outstanding = outstandingDue;
    }

    public float getUpdatedOutstanding()
    {
        return this.Outstanding - Repayment;
    }

    public float getAsIsOutstanding()
    {
        return this.Outstanding;
    }
}

