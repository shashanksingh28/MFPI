package org.groupsavings.domain;

/**
 * Created by shashank on 19/4/14.
 */
public class LoanAccount {

    public int Id;
    public int groupId;
    public Member member;

    public int Principal;
    public float InterestPerAnnum;
    public int periodInMonths;
    int EMI;

    public String StartDate;
    public String EndDate;
    public boolean IsActive;

    public int getEMI()
    {
        EMI = (int) (Principal * (1 + (periodInMonths*InterestPerAnnum)/1200))/periodInMonths;
        return EMI;
    }

}
