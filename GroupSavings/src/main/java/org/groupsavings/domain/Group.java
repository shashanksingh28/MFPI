package org.groupsavings.domain;

/**
 * Created by shashank on 8/3/14.
 */
public class Group {

    public int Id;
    public String Name;
    public Member President;
    public Member Secretary;
    public Member Treasurer;
    public String FieldOfficerId;
    public String Active;
    public String MonthlyMeetingDate;
    public String MonthlyCompulsoryAmount;
    public String Bank;
    public float ClusterId;
    public float CummulativeSavings;
    public float OtherIncome;
    public float OutstandingLoans;
    public String DateOfFormation;
    public String NoOfSubgroups;
    public String AddressLine1;
    public String AddressLine2;
    public String City;
    public String State;
    public String Country;
    public String CreatedDate;
    public String CreatedBy;
    public String ModifiedDate;
    public String ModifiedBy;
    public String NoOfActiveMembers;

    @Override
    public String toString()
    {
        return Name + "    Area : " + AddressLine2 + "    Savings : " + CummulativeSavings + "    Outstanding Loans : " + OutstandingLoans;
             //   + "    Members : "+NoOfMembers;
    }

}
