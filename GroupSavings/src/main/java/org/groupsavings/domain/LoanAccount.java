package org.groupsavings.domain;

/**
 * Created by shashank on 19/4/14.
 */
public class LoanAccount {

    public int Id;
    public int groupId;
    public int groupMeetingId;
    public int memberId;
    public Member GroupMember;

    public long Principal;
    public float InterestPerAnnum;
    public int PeriodInMonths;
    public long OutStanding;
    public long EMI;

    public String StartDate;
    public String EndDate;
    public String Reason;
    public boolean IsActive;

    public long getEMI()
    {
        int simpleInterest = (int) (Principal * InterestPerAnnum * PeriodInMonths / 1200);
        long total = Principal + simpleInterest;
        int quotient = (int) total / PeriodInMonths;
        int remainder = (int) total % PeriodInMonths;
        if(remainder > 0) quotient ++;

        EMI = quotient;
        return EMI;
    }

    public long getInitialOutstanding()
    {
        long simpleInterest = (int) (Principal * InterestPerAnnum * PeriodInMonths / 1200);
        return Principal + simpleInterest;
    }

}
