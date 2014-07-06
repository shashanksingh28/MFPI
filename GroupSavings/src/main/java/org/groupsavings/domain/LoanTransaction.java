package org.groupsavings.domain;

import java.io.Serializable;

/**
 * Created by shashank on 30/3/14.
 */
public class LoanTransaction implements Serializable
{
    public String GroupId;
    public String MeetingId;
    public String LoanAccountId;
    public float Repayment;
    public float Outstanding;
    public String DateTime;

    LoanAccount LoanAccount;

    public void setOutstandingDue(float outstandingDue)
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

