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

    public int Principal;
    public float InterestPerAnnum;
    public int PeriodInMonths;
    public int EMI;
    public String StartDate;
    public String EndDate;

    public int OutStanding;
    public String Reason;
    public boolean IsActive;


    public int getEMI()
    {
        int simpleInterest = (int) (Principal * InterestPerAnnum * PeriodInMonths / 1200);
        int total = Principal + simpleInterest;
        int quotient = total / PeriodInMonths;
        int remainder = total % PeriodInMonths;
        if(remainder > 0) quotient ++;

        EMI = quotient;
        return EMI;
    }

}
