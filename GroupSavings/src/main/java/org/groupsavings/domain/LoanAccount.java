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
        EMI = (int) (Principal * (1 + (PeriodInMonths *InterestPerAnnum)/1200))/ PeriodInMonths;
        return EMI;
    }

}
